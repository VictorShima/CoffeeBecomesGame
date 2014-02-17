


/**
 * Global Class that holds the Visual Simulator System Deluxe Collector's Edition Packet.
 */
var VS = function() {

	var VS = this;
	
	this.canvas = null; // canvas object
	this.playState = "PAUSE"; // ( PLAY | PAUSE )
	this.playHandle = null; // will contain the play handle given by setInterval
	this.currentTime = 0; // current time in play
	this.totalTime = 0; // time when to end the simulator
	this.firstEventIndex = 0; // index of the first event trying to insert
	this.report = null; // later will hold the event report
	this.map = null; // will contain the solid for the map (not really a solid)
	//TODO: possivelmente introduzir um sistema de Z tiers (overlays)
	this.solids = {}; // will contain a assoc list of all solids in play
	this.events = []; // will contain the list of currently processing events
	
	this.options = {
		fps : 45, // frame per second
		canvasWidth : 0, // width of canvas
		canvasHeight : 0 // height of canvas
	};

	/**
	 * Solids holds the information of all solid visible elements.
	 *
	 *  - asset : string // name of the asset
	 *  - animation : {time,name,end:("loop"|"erase")} // animation data
	 *  - effects : {time,name,end:("once"|"loop"|"erase")} // effect data
	 *  - components : [{x,y,position:("relative"|"fixed"),asset,animation,effects}, ...]
	 *  + draw( canvasContext, solid ) -> void
	 */
	this.SolidClass = function (name, id, x, y, angle, asset, attrs) {

		this.name = name;
		this.id = id;
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.asset = asset;
		this.attrs = attrs;
	
		this.animation = null;
		this.effects = [];
		this.components = [];

		// update( deltaTime )
		this.update = function (dtime) {
		
			// update the animation timer
			if (this.animation != null) {
				//console.log("AnimationInfo: VS.index['assets']["+this.asset+"].animations["+this.animation.name+"]");
				var animationInfo = VS.index['assets'][this.asset].animations[this.animation.name];
				if (!animationInfo) {
					console.error("[Error - UpdateSolid] : Animation '" + this.animation.name + "' does not exist.");
				}
				this.animation.time += dtime;
				if (this.animation.time > (animationInfo.frameDuration * animationInfo.numTiles)) {
					switch (this.animation.end) {
						case "loop" :
							this.animation.time -= (animationInfo.frameDuration * animationInfo.numTiles);
							break;
						case "erase" :
							delete VS.solids[this.id];
							break;
					}
				}
			}
			
			// update the effects timer
			for (var i = this.effects.length - 1; i >= 0; --i) {
				var effectInfo = VS.index['effects'][this.effects[i].name];
				this.effects[i].time += dtime;
				if (this.effects[i].time > effectInfo.totalDuration) {
					switch (this.effects[i].end) {
						case "once" :
							this.effects.splice(i, 1);
							break;
						case "loop" :
							this.effects[i].time -= effectInfo.totalDuration;
							break;
						case "erase" :
							delete VS.solids[this.id];
							break;
					}
				}
			}
			
		}

		// draw( ctx, solid, animData )
		// TODO: draw main asset and component assets
		this.draw = function () {
			var ctx = VS.canvas;
			
			// draw the main asset
			ctx.translate(this.x, this.y);
			ctx.save();
			ctx.rotate(this.angle * Math.PI / 180);
			if (VS.index['assets'][this.asset]) {
				VS.index['assets'][this.asset].draw(this, this.animation, this.effects);
			}
			else {
				console.error("[Error - DrawSolid] : Asset '" + this.asset + "' does not exist.");
			}
			
			// draw the relative component assets
			for (var i = 0; i < this.components.length; ++i) {
				if (this.components[i].position == 'relative') {
					ctx.save();
					ctx.translate(this.components[i].x, this.components[i].y);
					if (VS.index['assets'][this.components[i].asset]) {
						VS.index['assets'][this.components[i].asset].draw(this, this.components[i].animation, this.components[i].effects);
					}
					else {
						console.error("[Error - DrawComponent] : Asset '" + this.components[i].asset + "' does not exist.");
					}
					ctx.restore();
				}
			}
			ctx.restore();
			
			// draw the fixed component assets
			for (var i = 0; i < this.components.length; ++i) {
				if (this.components[i].position == 'fixed') {
					ctx.save();
					ctx.translate(this.components[i].x, this.components[i].y);
					if (VS.index['assets'][this.components[i].asset]) {
						VS.index['assets'][this.components[i].asset].draw(this, this.components[i].animation, this.components[i].effects);
					}
					else {
						console.error("[Error - DrawComponent] : Asset '" + this.components[i].asset + "' does not exist.");
					}
					ctx.restore();
				}
			}
				
		};
	};



	/**
	 * Event Class holds the base for all Events. Immutable.
	 *
	 *  - name : string
	 *  - permanent : boolean
	 *  + begin( absoluteTime, eventAttributes[] ) -> void
	 *  + update( deltaTime, eventAttributes[] ) -> void
	 */
	this.EventClass = function (name, permanent, beginFunction, updateFunction) {
	
		this.name = name;
		this.permanent = permanent;
		
		// begin( absoluteTime, eventData )
		this.begin = beginFunction;
	
		// update( deltaTime, eventData )
		this.update = updateFunction;
	};



	/**
	 * Asset Class holds the information about the asset. Immutable.
	 *
	 *  - draw( solid ) -> void
	 */
	this.AssetClass = function (name, src, tileWidth, tileHeight, animations) {

		this.name = name;
		this.image = src; //somehow use src to get image
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.animations = animations; // TODO: still ignored

		// draw( solid )
		this.draw = function (solid, animationData, effects) {
			//console.log( "[DrawAsset] " + this.name, this, solid );
			var ctx = VS.canvas;
			
			// For now generate color from src name
			var hash = 0, char;
			for (var i = 0; i < this.image.length; i++) {
				char = this.image.charCodeAt(i);
				hash = char + (hash << 6) + (hash << 16) - hash;
			}
			hash = Math.abs(hash);
			var prim1 = (hash % 3);
			var prim2 = Math.round(hash / 3 % 3);
			var light = Math.round(hash / 9 % 3);
			var red = Math.round(hash / 27 % 120) + (prim1 == 0 ? 40 : 0 ) + (prim2 == 0 ? 35 : 0 ) + (light * 30);
			var green = Math.round(hash / 3240 % 120) + (prim1 == 1 ? 40 : 0 ) + (prim2 == 0 ? 35 : 0 ) + (light * 30);
			var blue = Math.round(hash / 388800 % 120) + (prim1 == 2 ? 40 : 0 ) + (prim2 == 0 ? 35 : 0 ) + (light * 30);
			//console.log("COLOR: " + this.image + " -> rgb("+red+","+green+","+blue+")");
			// End of the color generator
			
			// draw only coloured rectangles
			ctx.fillStyle = "rgb("+red+","+green+","+blue+")";
			ctx.fillRect(-this.tileWidth/2, -this.tileHeight/2, this.tileWidth, this.tileHeight);
			
			// execute the associated effects
			for (var i = 0; i < effects.length; ++i) {
				ctx.save();
				VS.index['effects'][effects[i].name].affect(solid, this, effects[i]);
				ctx.restore();
			}
			
			
		};
	};



	/**
	 * Animation Class holds the information about an animation.
	 */
	this.AnimationClass = function (name, tileRow, numTiles, frameDuration) {
		this.name = name;
		this.tileRow = tileRow;
		this.numTiles = numTiles;
		this.frameDuration = frameDuration;
	};



	/**
	 * Effect Class holds the implementation of a visual effect on an asset.
	 *
	 *  - affect( solid, efdata{time,name} ) -> void
	 */
	this.EffectClass = function (name, totalDuration, affectFunction) {
		this.name = name;
		this.totalDuration = totalDuration;
		this.affect = affectFunction;
	}
	
	
	
	/**
	 * The Index that holds all static methods.
	 */
	this.generateIndex = function () {
		return {
			'events' : {
			
				/**
				 * Creates a Player on the field.
				 */
				'createPlayer' : new VS.EventClass('createPlayer', false,
					function(atime, ev) {
						// create player
						VS.solids[ev.id] = new VS.SolidClass("player", ev.id,
								ev.x, ev.y, ev.angle,
								"mech", ev);
						VS.solids[ev.id].attrs['health'] = ev.hp;
						VS.solids[ev.id].attrs['maxHealth'] = ev.hp;
						// create the weaponry for player
						var weapons = {
							'Left' : { 'x':-25, 'y':-10 },
							'Right' : { 'x':25, 'y':-10 },
							'Center' : { 'x':0, 'y':25 }
						};
						for ( var k in weapons ) {
							if ( ev['weapon'+k] != "" ) {
								VS.solids[ev.id].components.push({
									x: weapons[k].x,
									y: weapons[k].y,
									position: 'relative',
									asset: 'weapon-' + ev['weapon'+k],
									animation: null,
									effects: []
								});
							}
						}
						// create the HP bar
						VS.solids[ev.id].components.push({
							x: 0,
							y: -45,
							position: 'fixed',
							asset: 'bar-health',
							animation: null,
							effects: [{
								time: 0,
								name: 'health',
								end: "loop"
							}]
						});
					},
					function(dtime, ev) { }
				),
				
				/**
				 * Creates a Projectile on the field.
				 */
				'createProjectile' : new VS.EventClass('createProjectile', false,
					function(atime, ev) {
						// create projectile
						VS.solids[ev.id] = new VS.SolidClass("projectile", ev.id,
								ev.x, ev.y, ev.angle,
								"projectile-" + ev.type, ev);
					},
					function(dtime, ev) { }
				),
				
				
				/**
				 * Erases a Projectile from the field. Then produces an explosion.
				 */
				'eraseProjectile' : new VS.EventClass('eraseProjectile', false,
					function(atime, ev) {
						VS.solids[ev.id].asset = 'explosion-projectile';
						VS.solids[ev.id].animation = {
							time: 0,
							name: 'explosion',
							end: 'erase'
						};
					},
					function(dtime, ev) {
						// explosion for x time, then delete
					}
				),
				
				/**
				 * Creates an Obstacle on the field.
				 */
				'createObstacle' : new VS.EventClass('createObstacle', false,
					function(atime, ev) {
						VS.solids[ev.id] = new VS.SolidClass("obstacle", ev.id, 
								ev.x, ev.y, 0,
								"obstacle", ev);
					},
					function(dtime, evdata) { }
				),
				
				/**
				 * Issues that a Solid moves in a place with given angle and speed.
				 */
				'startMoving' : new VS.EventClass('startMoving', true,
					function(atime, ev) { },
					function(dtime, ev) {
						var radAngle = ev.angle * Math.PI / 180;
						VS.solids[ev.id].x += Math.cos(radAngle) * ev.speed * dtime;
						VS.solids[ev.id].y -= Math.sin(radAngle) * ev.speed * dtime;
					}
				),
				
				/**
				 * Removes a previous 'startMoving' event the Solid had.
				 */
				'endMoving' : new VS.EventClass('endMoving', false,
					function(atime, ev) {
						for ( var i = 0; i < VS.events.length; ++i ) {
							if ( VS.events[i].attrs.id == ev.id && VS.events[i].name == 'startMoving' ) {
								VS.events.splice(i, 1);
								break;
							}
						}
					},
					function(dtime, ev) { }
				),
				
				
				/**
				 * Issues that a Solid rotates in place with given angular speed
				 */
				'startTurning' : new VS.EventClass('startTurning', true,
					function(atime, ev) { },
					function(dtime, ev) {
						VS.solids[ev.id].angle -= ev.angspeed * dtime;
					}
				),
				
				/**
				 * Removes a previous 'startRotate' event the Solid had.
				 */
				'endTurning' : new VS.EventClass('endTurning', false,
					function(atime, ev) {
						for ( var i = 0; i < VS.events.length; ++i ) {
							if ( VS.events[i].attrs.id == ev.id && VS.events[i].name == 'startTurning' ) {
								VS.events.splice(i, 1);
								break;
							}
						}
					},
					function(dtime, ev) { }
				),
				
				/**
				 * Modifies the HP of a Solid.
				 */
				'modifyHealth' : new VS.EventClass('modifyHealth', false,
					function(atime, ev) {
						VS.solids[ev.id].attrs['health'] += ev.value;
					},
					function(dtime, ev) { }
				),
				
			},
			
			'assets' : {
				'map' : new VS.AssetClass('map', 'map.png', VS.options.canvasWidth, VS.options.canvasHeight, { }),
				
				'mech' : new VS.AssetClass('mech', 'mech.png', 50, 50, {
					'idle' : new VS.AnimationClass('idle', 0, 1, 10),
					'moving' : new VS.AnimationClass('moving', 1, 5, 0.1),
				}),
				
				'obstacle' : new VS.AssetClass('obstacle', 'obstacle.png', 70, 70, { }),
				
				'weapon-MissileLauncher' : new VS.AssetClass('weapon-MissileLauncher', 'mlauncher.png', 20, 40, {}),
				
				'weapon-MineSetter' : new VS.AssetClass('weapon-MineSetter', 'minesetter.png', 30, 10, {}),
				
				'projectile-Missile' : new VS.AssetClass('projectile-Missile', 'missile.png', 5, 15, {}),
				
				'projectile-Mine' : new VS.AssetClass('projectile-Mine', 'mine.png', 10, 10, {}),
				
				'explosion-projectile' : new VS.AssetClass('explosion-projectile', 'explproj.png', 30, 30, {
					'explosion' : new VS.AnimationClass('explosion', 0, 1, 1),
				}),
				
				'bar-health' : new VS.AssetClass('bar-health', 'hpbar.png', 50, 5, {}),
			},
			
			'effects' : {
				'damage' : new VS.EffectClass('damage', 1, function(solid, asset, efdata){}),
				
				'health' : new VS.EffectClass('health', 2, function(solid, asset, efdata){
					var ctx = VS.canvas;
					var hpRatio = (solid.attrs['health'] / solid.attrs['maxHealth']);
					
					// build health number
					ctx.fillStyle = "rgb(" + 
							Math.round(128 - hpRatio * 128) + "," + 
							Math.round(hpRatio * 128) + 
							",0)";
					ctx.font = "normal normal bold 12px arial";
					ctx.textAlign = "center";
					ctx.fillText(solid.attrs['health'], 0, -5);
					
					// build health bars (have to use 2 rects instead of gradient)
					ctx.fillStyle = "rgba(0,255,0,0.7)";
					ctx.fillRect(-asset.tileWidth/2,
							-asset.tileHeight/2,
							asset.tileWidth * hpRatio,
							asset.tileHeight);
					ctx.fillStyle = "rgba(255,0,0,0.7)";
					ctx.fillRect(-asset.tileWidth/2 + asset.tileWidth * hpRatio,
							-asset.tileHeight/2,
							asset.tileWidth * (1 - hpRatio),
							asset.tileHeight);
				}),
			},
		};
	};
	
	
	
	/**
	 * Initializes the simulator with the input report.
	 */
	this.init = function (containerId, report) {
	
		// set global simulator variables
		VS.totalTime = report.totalTime + 3; // plus 3 seconds to finish all animations
		VS.options.canvasWidth = report.mapWidth;
		VS.options.canvasHeight = report.mapHeight;
		
		// initialize the canvas
		var canvasElement = $("<canvas width='" + this.options.canvasWidth + 
				"' height='" + this.options.canvasHeight + "'></canvas>");
		VS.canvas = canvasElement.get(0).getContext("2d");
		canvasElement.appendTo($('#'+containerId));
		
		// build the initial objects
		VS.index = VS.generateIndex();
		VS.map = new VS.SolidClass("map", 0, 0, 0, 0, "map", {});
		
		// import the event report
		// TODO: I am assuming already it is in: ORDER BY time ASC
		VS.report = report.events;
		VS.report.sort(function(a,b){ return a.time - b.time; });
		VS.insertEvents(0);
		
		/*/ DEBUG test draw image
		var img = new Image();
		img.onload = function() {
			 VS.canvas.drawImage(img, 0, 0);
		}
		img.src = 'images/mech.png';*/
		
		// wait some time and then play it
		window.setTimeout(VS.play, 1000);
	};
	
	
	
	/**
	 * Insert the events until the specified time.
	 */
	this.insertEvents = function (time) {
		for (var i = VS.firstEventIndex; i < VS.report.length && VS.report[i].time <= time; ++i) {
			console.log( "[InsertEvent] " + VS.report[i].time + " -> " + VS.report[i].name );
			if (VS.index['events'][VS.report[i].name]) {
				VS.index['events'][VS.report[i].name].begin(time, VS.report[i].attrs);
				if (VS.index['events'][VS.report[i].name].permanent) {
					VS.events.push(VS.report[i]);
				}
			}
			else {
				console.error("[Error - InsertEvent] : Event '" + VS.report[i].name + "' does not exist.");
			}
			VS.firstEventIndex = i + 1;
		}
	};
	
	
	
	/**
	 * Update all events for a specific duration.
	 * TODO: on recently created events, give them an appropriate shorter duration
	 */
	this.updateState = function (dtime) {
		// update solid timers for animations
		for (var k in VS.solids) {
			VS.solids[k].update(dtime);
		}	
		// update events
		for (var i = 0; i < VS.events.length; ++i) {
			//console.log("[Update] " + VS.events[i].name + " -> " + dtime);
			VS.index['events'][VS.events[i].name].update(
				Math.min(dtime, VS.currentTime - VS.events[i].time),
				VS.events[i].attrs
			);
		}
	};
	
	
	
	/**
	 * Runs the game in a loop
	 */
	this.play = function () {
		VS.playState = "PLAY";
		VS.playHandle = window.setInterval(function() {
			if (VS.playState == "PLAY" && VS.currentTime < VS.totalTime) {
			
				// update the state
				var dtime = 1000/VS.options.fps/1000;
				//console.log( "[Play] " + VS.currentTime + " + " + dtime + " = " + (VS.currentTime + dtime) );
				VS.currentTime += dtime;
				VS.insertEvents(VS.currentTime);
				VS.updateState(dtime);
								
				// draw the state
				var ctx = VS.canvas;
				ctx.save();
				ctx.translate(
						VS.index['assets'][VS.map.asset].tileWidth/2,
						VS.index['assets'][VS.map.asset].tileHeight/2); 
				VS.map.draw();
				ctx.restore();
				for ( var i in VS.solids ) {
					ctx.save();
					VS.solids[i].draw();
					ctx.restore();
				}
			}
			else {
				window.clearInterval(VS.playHandler);
				VS.playState = "PAUSE";
			}
		}, 1000/VS.options.fps);
	};
		
	
};




//var event = new VS.EventClass("timdsakdsa", function(hi){return hi});
//console.log("EVENT: ", event);

//var asset = new AssetClass("ddas", "src.png", 50, 50, {});
//console.log("ASSET: ", asset);






