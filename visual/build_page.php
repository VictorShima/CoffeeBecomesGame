<script type="application/javascript">
$(function(){


	var options = {
	
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
				attrs: [],
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
	 * Add all the Condition and Action options.
	 */
	var categories = [
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
	for (var k = 0; k < categories.length; ++k) {
		var category = categories[k];
		for (var i = 0; i < options[category.key].length; ++i) {
			var option = category.shell.clone();
			option.removeAttr('data-shell');
			option.attr('data-code', options[category.key][i].code);
			option.text(options[category.key][i].name);
			option.css('display', 'inline-block');
			category.container.append(option);
		}
	}
		
	
		
	/**
	 * Switch the main window between the 2 modes.
	 */
	$('.build-switch-column').on('click', 'a', function(evt) {
		$('.build-main-column').children('[data-window]').hide();
		$('.build-main-column').children('[data-window="'+$(evt.target).attr('data-window')+'"]').show();
		evt.preventDefault();
	});


});
</script>


<div class="page-column-container"><!--

	--><div class="page-column build-switch-column">
		<a href="#" data-action="change-window" data-window="equipment">Equipment Window</a>
		<a href="#" data-action="change-window" data-window="intelligence">Intelligence Window</a>
	</div><!--
	
	--><div class="page-column build-main-column">
	
		<div class="page-column-container" style="display:none;" data-window="equipment"><!--
			--><div class="page-column build-equipment-column">
				EQUIPMENT
			</div><!--
		--></div>
		
		<div class="page-column-container" style="display:none;" data-window="intelligence"><!--
			--><div class="page-column build-algorithm-column">
				INTELLIGENCE ALGORITHM
			</div><!--
			--><div class="page-column build-options-column">
				INTELLIGENCE OPTIONS
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

