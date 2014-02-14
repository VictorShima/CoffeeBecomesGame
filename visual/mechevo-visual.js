


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
	
	this.options = {
		fps : 10, // frame per second
		canvasWidth : 0, // width of canvas
		canvasHeight : 0 // height of canvas
	};

	/**
	 * Solids holds the information of all solid visible elements.
	 *
	 *  - draw( canvasContext, solid, animData{time,title} ) -> void
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
		this.events = [];

		// update( deltaTime )
		this.update = function (dtime) {
			for (var i = 0; i < this.events.length; ++i) {
				VS.index['events'][this.events[i].name].update(dtime, this, this.events[i]);
			}
		}

		// draw( ctx, solid, animData )
		this.draw = function (ctx, solid, animData) {
			//TODO: draw
		};
	};



	/**
	 * Event Class holds the base for all Events. Immutable.
	 *
	 *  - update( deltaTime, solid, eventData{time,title,attrs[]} ) -> void
	 */
	this.EventClass = function (name, updateFunction) {
	
		this.name = name;
	
		// update( deltaTime, solid, eventData )
		this.update = updateFunction;
	};



	/**
	 * Asset Class holds the information about the asset. Immutable.
	 *
	 *  - draw( canvasContext, solid, animData{time,title} ) -> void
	 */
	this.AssetClass = function (name, src, tileWidth, tileHeight, animations) {

		this.name = name;
		this.image = src; //somehow use src to get image
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.animations = animations;

		// draw( ctx, solid, animData )
		this.draw = function (ctx, solid, animData) {
			//TODO: draw
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
	 * Effect Class holds the implementation of a visual effect.
	 *
	 *  - affect( ctx ) -> void
	 */
	this.EffectClass = function (name, totalDuration, affectFunction) {
		this.name = name;
		this.totalDuration = totalDuration;
		this.affect = affectFunction;
	}
	
	
	
	/**
	 * The Index that holds all static methods.
	 */
	this.index = {
		'events' : {
			'createPlayer' : new VS.EventClass('createPlayer', function(dtime, solid, evdata) {
				
			}),
			'startMove' : new VS.EventClass('startMove', function(dtime, solid, evdata) {
			}),
		},
		'assets' : {
			'mech' : new VS.AssetClass('mech', 'mech.png', 50, 50, { }),
			'obstable' : new VS.AssetClass('obstacle', 'obstacle.png', 70, 70, { }),
		},
		'effects' : {
			'damage' : new VS.EffectClass('damage', 1, function(){}),
		},
	};
	
	
	
	/**
	 * Initializes the simulator with the input report.
	 */
	this.init = function (containerId, report) {
	
		// set global simulator variables
		VS.totalTime = report.totalTime;
		VS.options.canvasWidth = report.mapWidth;
		VS.options.canvasHeight = report.mapHeight;
		
		// initialize the canvas
		var canvasElement = $("<canvas width='" + this.options.canvasWidth + 
				"' height='" + this.options.canvasHeight + "'></canvas>");
		VS.canvas = canvasElement.get(0).getContext("2d");
		canvasElement.appendTo($('#'+containerId));
		
		// import the event report
		// TODO: I am assuming already it is in: ORDER BY time ASC
		VS.report = report.events;
		
		// build the initial map
		VS.insertEvents(0);
		
		// wait some time and then play it
		window.setTimeout(VS.play(), 1000);
	};
	
	
	
	/**
	 * Insert the events until the specified time.
	 */
	this.insertEvents = function (time) {
		for ( var i = VS.firstEventIndex; i < VS.report && VS.report[i].time <= time; ++i ) {
			console.log( "[E] " + VS.report[i].time + " -> " + VS.report[i].name );
			VS.firstEventIndex = i + 1;
		}
	};
	
	
	
	/**
	 * Update all events for a specific duration.
	 * TODO: on recently created events, give them an appropriate shorter duration
	 */
	this.updateState = function (time) {
		// for each solid, update
	};
	
	
	
	/**
	 * Runs the game in a loop
	 */
	this.play = function () {
		VS.playHandle = window.setInterval(function() {
			if (VS.playState = "PLAY" && VS.currentTime < VS.totalTime) {
				var dtime = 1000/VS.options.fps;
				console.log( "[Run] " + VS.currentTime + " + " + dtime + " = " + (VS.currentTime + dtime) );
				VS.currentTime += dtime;
				VS.insertEvents(VS.currentTime);
				VS.updateState(dtime);
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






