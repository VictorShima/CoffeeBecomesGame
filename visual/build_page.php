<script type="application/javascript">
$(function(){


	var options = {
	
		// contem todo o equipamento
		'sideweapons': [
			{
				code: 'minigun',
				name: 'Minigun',
				desc: 'Minigun things.'
			},
			{
				code: 'rocketlauncher',
				name: 'Rocket Launcher',
				desc: 'Sends rockets.'
			}
		],
		'offhandweapons': [
			{
				code: 'minesetter',
				name: 'Mine Setter',
				desc: 'Sets mines.'
			}
		],
	
		// contem todas as condicoes
		'conditions': [
			{
				code: 'EnemySighted',
				name: 'Enemy Sighted',
				attrs: {
					'closest': 'Closest',
					'furthest': 'Furthest'
				},
				desc: 'Descrição.'
			},
			{
				code: 'True',
				name: 'Default',
				attrs: {},
				desc: 'Descrição mais pormenorizada.'
			},
		],
		
		// contem todas as accoes
		'actions': [
			{
				code: 'MoveToEnemy',
				name: 'Move to Enemy',
				attrs: {
					'move': 'Move',
					'sprint': 'Sprint'
				},
				desc: 'Descrição.'
			},
			{
				code: 'Idle',
				name: 'Idle',
				attrs: [],
				desc: 'Descrição mais pormenorizada.'
			},
		],
		
	};
	/**
	 * Return an option data by its codename, null if not found.
	 */
	var fetchOptionByCode = function (category, code) {
		if (options[category]) {
			for (var i = 0; i < options[category].length; ++i) {
				if (options[category][i].code == code) {
					return options[category][i];
				}
			}
		}
		return null;
	};
				
/**
 - **EnemySighted (closest, furthest)** : selects the closest/furthest visible enemy
 - **True** : always true
 - **EnemyDisappears** : selects the enemy that disappeared from vision (for this to work the target needs to be also a Position)
 - **ReceivedDamage (frontal, back, side)** : true if just received damage
 - **WeaponReady (left, right, central)** : true if a weapon is off cooldown
 - **DistanceToEnemy (distance)** : true if the nearest enemy is nearer than distance
*/

/**	
 - **MoveToEnemy (move, sprint)** : move/sprint to target or to closest enemy
 - **Attack (left, right, center)** : attack with selected weapon
 - **FaceOpponent** : turn to target or to closest enemy
 - **Dash (left, right)** : sideways dash
 - **Sprint (forward, backward)** : faster movement
 - **Turn (*various angles mod 45º*)** : turn in angle
 - **ApproachObstacle (move, sprint)** : move to nearest obstacle in a direct line
 - **MoveInLine (move forward, move back, sprint forward, sprint back)** : move in a direct line
 - **Scout360º** : turn a full 360º, will use the Turn Class in the Simulator
*/


	/**
	 * Switch the main window between the 2 modes.
	 */
	$('.build-switch-column').on('click', 'a[data-action="change-window"]', function(evt) {
		$('.build-main-column').children('[data-window]').hide();
		$('.build-main-column').children('[data-window="'+$(evt.target).attr('data-window')+'"]').show();
		evt.preventDefault();
	});
	
	
	/**
	 * Activate the load button.
	 */
	$('[data-action="load-premade"]').on('click', function(evt) {
		console.log("Clicked the LOAD Button.");
	});
	
	
	/**
	 * Activate the battle button.
	 */
	$('[data-action="test-battle"]').on('click', function(evt) {
		console.log("Clicked the BATTLE Button.");
	});
	
	
	/**
	 * Activate the export button.
	 * Output format should be:
	 *   {
	 *     "map" : { "width":double, "height":double },
	 *     "obstacles" : [ { "x":double, "y":double, "radius":double }, ... ],
	 *     "players" : [ {
	 *       "teamId":int, 
	 *       "weapons":[string,string,string],
	 *       "color":string,
	 *       "x":double,
	 *       "y":double,
	 *       "angle":double,
	 *       "algorithm": [ { 
	 *         "conditions": [ { "name":string, "param":[string,...] }, ... ],
	 *         "actions": [ { "name":string, "param":[string,...] }, ... ]
	 *       }]
	 *     }]
	 *   }
	 */
	$('[data-action="export-json"]').on('click', function(evt) {
		// extract the weapons
		var extractWeapon = function(slot) {
			var slotBox = $('.mech-blueprint .mech-'+slot+'-slot');
			return slotBox.children().length > 0
					? slotBox.find('.weapon-option').attr('data-code')
					: '';
		};
		var weapons = [
			extractWeapon('left'),
			extractWeapon('right'),
			extractWeapon('off')
		];
		
		// extract the algorithm
		var algorithm = [];
		var categories = [
			{ item:'condition', slot:'conditions' },
			{ item:'action', slot:'actions' }
		];
		
		for (var cat = 0; cat < categories.length; ++cat) {
			$('.ruleset').find('[data-item="ruleset-rule"]').each(function(ruleIndex, ruleElement){
				var entry = { conditions:[], actions:[] };
			
				// get the conditions and actions from the rule
				$(ruleElement).find('[data-item="ruleset-'+categories[cat].item+'"]').each(function(itemIndex, itemElement){
					var slotElement = $(itemElement).find('[data-slot="'+categories[cat].slot+'"]');
					if (slotElement.children().length > 0) {
						entry[categories[cat].slot].push({
							name: slotElement.find('[data-code]').attr('data-code'),
							param: [$(itemElement).find('[data-key="attribute"]').val()]
						});
					}
				});
				
				// add the entry if valid
				if (entry.conditions.length > 0 && entry.actions.length > 0) {
					algorithm.push(entry);
				}
				//console.log("ENTRY: ", entry);
			});
		}
			
		// output with initial values I already hardcoded and the extracted ones
		var output = {
			map: { width: 800, height: 600 },
			obstacles: [
				{ x: 100, y:120, radius: 35 },
				{ x: 240, y:380, radius: 35 },
				{ x: 410, y:470, radius: 35 },
				{ x: 380, y:150, radius: 35 },
				{ x: 600, y:220, radius: 35 }
			],
			players: [
				{
					teamId: 0,
					weapons: weapons,
					color: "#FF0000",
					x: 75,
					y: 525,
					angle: 45,
					algorithm: algorithm
				},
				{
					teamId: 1,
					weapons: weapons,
					color: "#0000FF",
					x: 725,
					y: 75,
					angle: 225,
					algorithm: algorithm
				}
			]
		};
	
		console.log("Battle with 2 equal AIs exported:");
		console.log("Debug: ", output);
		console.log(JSON.stringify(output));
	});


	/**
	 * Add all the options.
	 * With droppable events
	 */
	var categories = [
		{
			'key' : 'sideweapons',
			'container' : $('[data-container="sideweapon"]'),
			'shell' : $('[data-shell="sideweapon"]'),
		},
		{
			'key' : 'offhandweapons',
			'container' : $('[data-container="offhandweapon"]'),
			'shell' : $('[data-shell="offhandweapon"]'),
		},
		{
			'key' : 'conditions',
			'container' : $('[data-container="condition"]'),
			'shell' : $('[data-shell="condition"]'),
		},
		{
			'key' : 'actions',
			'container' : $('[data-container="action"]'),
			'shell' : $('[data-shell="action"]'),
		}
	];
	var optionMethods = {
		/**
		 * Enable dragging on an option.
		 * @param el element The element to add dragging to
		 * @param scope string The type of element
		 * @return element The input element
		 */
		enableDrag : function(el) {
			el.draggable({
				containment: $('.build-main-column'),
				helper: 'clone',
				revert: "invalid",
				scope: el.attr('data-item'),
				snap: '[data-slot="' + el.attr('data-item') + '"]',
				snapMode: 'inner',
				snapTolerance: 5,
				zIndex: 100,
			});
			return el;
		},
		
		/**
		 * Enable dropping for an option slot.
		 * @param el element The element to add dragging to
		 * @param scope string The type of element
		 * @return element The input element
		 */
		enableDrop : function(el) {
			el.droppable({
				accept: '[data-item="' + el.attr('data-slot') + '"]',
				activeClass: 'slot-accept',
				scope: el.attr('data-slot'),
				drop: function(event, ui) {
					$(event.target).empty();
					$(event.target).append(ui.draggable.clone());
				}
			});
			return el;
		}
	};
		
	for (var k = 0; k < categories.length; ++k) {
		var category = categories[k];
		
		// add the options
		for (var i = 0; i < options[category.key].length; ++i) {
			var option = category.shell.clone();
			
			// make fine for an option element
			option.removeAttr('data-shell');
			option.attr('data-code', options[category.key][i].code);
			option.attr('data-item', category.key);
			option.append(options[category.key][i].name);
			option.css('display', 'inline-block');
			
			// add the dragging function
			optionMethods.enableDrag(option);
			
			category.container.append(option);
		}
		
		// enable the slots
		optionMethods.enableDrop($('[data-slot="'+category.key+'"]'));
			
	}
	
	
	/**
	 * Add all the support for the RuleSet.
	 */
	var ruleset = $(".ruleset");
	var rulesetMethods = {
		/**
		 * Insert a Rule, Condition or Action entry.
		 * @param type string Can be: "rule"|"condition"|"action"
		 * @param after element The DOM element after which to insert, if it is a
		 *   container then add as first item
		 */
		insert: function(type, after) {
			console.log("CALL insert " + type + " after ", after);
			
			// create its own copy of shell
			var shell = ruleset.find('[data-shell="ruleset-' + type + '"]').clone();
			shell.attr('data-item', shell.attr('data-shell'))
					.removeAttr('data-shell').css('display', 'block');
					
			// edit some setting on children
			shell.find('[data-shell]').each(function(idx, el){
				$(el).attr('data-item', $(el).attr('data-shell'))
						.removeAttr('data-shell');
			});
			shell.find('[data-slot="conditions"],[data-slot="actions"]').each(function(idx, el){
				el = $(el);
				optionMethods.enableDrop(el);
				var oldDropMethod = el.droppable('option', 'drop');
				el.droppable('option', 'drop', function(event, ui) {
					oldDropMethod(event,ui);
					
					var target = $(event.target);
					var attributes = fetchOptionByCode(
							target.attr('data-slot'),
							ui.draggable.attr('data-code')).attrs;
					var selectBox = target.parent().find("select");
					selectBox.empty();
					// add the attributes to the select box
					for (var key in attributes) {
						selectBox.append($("<option>", {
							value: key,
							text: attributes[key]
						}));
					}
					if (selectBox.children().length > 0) {
						selectBox.css('visibility', 'visible');
					}
					else {
						selectBox.css('visibility', 'hidden');
					}
					console.log("SELECTBOX", selectBox, attributes);
				});
			});
			
			// insert it into dom tree
			if (after.attr('data-container') == "ruleset-"+type) {
				shell.prependTo(after);
			}
			else {
				shell.insertAfter(after);
			}
		},
		
		/**
		 * Insert a Rule, Condition or Action entry.
		 * @param type string Can be: "rule"|"condition"|"action"
		 * @param after element The DOM element to remove
		 */
		remove: function(type, element) {
			console.log("CALL remove " + type);
			var container = element.parent('[data-container="ruleset-'+type+'"]');
			element.remove();
			// now add a new one if that was the last of the container
			if (container.find('[data-item="ruleset-'+type+'"]').length == 0) {
				rulesetMethods.insert(type, container);
			}
		}
	};
	ruleset.on('click', '[data-action="insert"]', function(evt) {
		var target = $(evt.target);
		console.log("RuleSet INSERT " + target.attr("data-param"));
		console.log("RuleSet INSERT target ", target);
		console.log("RuleSet INSERT parent ", target.parent('[data-item="'+target.attr("data-param")+'"]'));
		rulesetMethods.insert(target.attr("data-param"),
				(target.attr("data-param") == "rule")
				? target.prev()
				: target.parent('[data-item="ruleset-'+target.attr("data-param")+'"]'));
	});
	ruleset.on('click', '[data-action="remove"]', function(evt) {
		console.log("RuleSet REMOVE " + $(evt.target).attr("data-param"));
		var target = $(evt.target);
		rulesetMethods.remove(
				target.attr("data-param"), 
				target.parent('[data-item="ruleset-'+target.attr("data-param")+'"]'));
	});
	
	
	
	/**
	 * Add Support for the color selection. 
	 */
	$('[data-container="color"]').on('click', '[data-color]', function(ev){
		console.log("Clicked Color");
		$('[data-container="color"]').find('.color-box-selected').removeClass('color-box-selected');
		$(ev.target).addClass('color-box-selected');
	});
	

});
</script>


<div class="page-column-container"><!--

	--><div class="page-column build-switch-column">
	
		<a href="#" data-action="change-window" data-window="equipment">Equipment Window</a>
		<a href="#" data-action="change-window" data-window="intelligence">Intelligence Window</a>
		
		<hr />
		<h3>Test in Battle</h3>
		<div class="soft-form">
			<select name="premade">
				<option value="troll">vs Troll</option>
				<option value="ninja">vs Ninja</option>
				<option value="durao">vs Durão</option>
			</select>
			<a href="#" data-action="test-battle">Battle</a>
		</div>
		
		<hr />
		<h3>Load Premade</h3>
		<div class="soft-form">
			<select name="premade">
				<option value="basic">Basic</option>
				<option value="runandhit">Run &amp; Hit</option>
				<option value="chase">Chase</option>
			</select>
			<a href="#" data-action="load-premade">Load Premade</a>
		</div>
		
		<hr />
		<h3># EXPORT #</h3>
		<div class="soft-form">
			<a href="#" data-action="export-json">Export in JSON</a>
		</div>
	</div><!--
	
	--><div class="page-column build-main-column">
	
	
		<!--
			Equipment Window
		-->
	
		<div class="page-column-container" style="display:none;" data-window="equipment"><!--
			--><div class="page-column build-armament-column">
				<h3>SIDE WEAPON</h3>
				<div class="sideweapon-container" data-container="sideweapon"><!--
					--><div class="weapon-option" style="display:none;" data-shell="sideweapon" data-item="sideweapon"><img /></div><!--
				--></div>
				
				<h3>OFFHAND WEAPON</h3>
				<div class="offhandweapon-container" data-container="offhandweapon"><!--
					--><div class="weapon-option" style="display:none;" data-shell="offhandweapon" data-item="offhandweapon"><img /></div><!--
				--></div>
					
			</div><!--
			--><div class="page-column build-hangar-column"><!--
				--><div class="page-column build-color-column">
					<h3>COLOR</h3>
					<div class="color-container" data-container="color">
						<div class="color-box" data-color="#FF0000" style="background-color:#FF0000;"></div>
						<div class="color-box" data-color="#00FF00" style="background-color:#00FF00;"></div>
						<div class="color-box" data-color="#0000FF" style="background-color:#0000FF;"></div>
					</div>
				</div><!--
				--><div class="page-column build-mech-column">
					<h3>MECH</h3>
					<div class="mech-blueprint">
						<div class="mech-image"></div>
						<div class="mech-left-slot" data-slot="sideweapons">left</div>
						<div class="mech-right-slot" data-slot="sideweapons">right</div>
						<div class="mech-off-slot" data-slot="offhandweapons">off</div>
					</div>
				</div><!--
			--></div><!--
		--></div>
		
		
		<!--
			Intelligence Window
		-->
		
		<div class="page-column-container" style="display:none;" data-window="intelligence"><!--
		--><div class="page-column build-algorithm-column ruleset">
				
				<div class="ruleset-rule-container" data-container="ruleset-rule">
					<div class="ruleset-rule-entry" data-shell="ruleset-rule" style="display:none;">
					
						<div class="ruleset-order-row"><!--
						--><a class="ruleset-order-button" href="#" data-action="raise" data-param="rule"></a><!--
					--></div>
						
						
						<div class="ruleset-condition-container" data-container="ruleset-condition">
							<div class="ruleset-condition-row" data-shell="ruleset-condition"><!--
							--><a class="ruleset-condition-remove" href="#" data-action="remove" data-param="condition"></a><!--
							--><div class="ruleset-condition-entry">
									<div class="ruleset-condition-slot" data-slot="conditions"></div><!--
								--><select data-key="attribute" style="visibility:hidden;"></select><!--
							--></div><!--
							--><a class="ruleset-condition-insert" href="#" data-action="insert" data-param="condition"></a><!--
						--></div>
						</div>
					
						
						<div class="ruleset-action-container" data-container="ruleset-action">
							<div class="ruleset-action-row" data-shell="ruleset-action"><!--
							--><a class="ruleset-action-remove" href="#" data-action="remove" data-param="action"></a><!--
							--><div class="ruleset-action-entry">
									<div class="ruleset-action-slot" data-slot="actions"></div><!--
								--><select data-key="attribute" style="visibility:hidden;"></select><!--
							--></div><!--
							--><a class="ruleset-action-insert" href="#" data-action="insert" data-param="action"></a><!--
						--></div>
						</div>
					
						<div class="ruleset-order-row"><!--
						--><a class="ruleset-order-button" href="#" data-action="lower" data-param="rule"></a><!--
					--></div>
					
					</div>
					
					<a class="ruleset-rule-insert" href="#" data-action="insert" data-param="rule">ADD ENTRY</a>
				</div>
					
				
			</div><!--
			
		--><div class="page-column build-options-column">
				<h3>Conditions</h3>
				<div class="condition-container" data-container="condition"><!--
				--><div class="condition-option" style="display:none;" data-shell="condition"></div><!--
			--></div>
				<h3>Actions</h3>
				<div class="action-container" data-container="action"><!--
				--><div class="action-option" style="display:none;" data-shell="action"></div><!--
			--></div>
			</div><!--
	--></div>
		
	</div><!--
	
--></div>

