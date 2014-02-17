<?php
return array(
	'totalTime' => 5 - 3,
	'mapWidth' => 800,
	'mapHeight' => 600,
	'events' => array(
		array(
			'time' => 0,
			'name' => 'createPlayer',
			'attrs' => array(
				'id' => 1,
				'teamId' => 1,
				'x' => 75,
				'y' => 525,
				'angle' => 45,
				'hp' => 100,
				'color' => '#FF0000',
				'weaponLeft' => 'MissileLauncher',
				'weaponRight' => '',
				'weaponCenter' => 'MineSetter',
			)
		),
		array(
			'time' => 0,
			'name' => 'createPlayer',
			'attrs' => array(
				'id' => 2,
				'teamId' => 2,
				'x' => 725,
				'y' => 75,
				'angle' => 225,
				'hp' => 100,
				'color' => '#0000FF',
				'weaponLeft' => 'MissileLauncher',
				'weaponRight' => 'MissileLauncher',
				'weaponCenter' => 'MineSetter',
			)
		),
		array(
			'time' => 0,
			'name' => 'createObstacle',
			'attrs' => array(
				'id' => 3,
				'x' => 100,
				'y' => 120,
			)
		),
		array(
			'time' => 0,
			'name' => 'createObstacle',
			'attrs' => array(
				'id' => 4,
				'x' => 240,
				'y' => 380,
			)
		),
		array(
			'time' => 0,
			'name' => 'createObstacle',
			'attrs' => array(
				'id' => 5,
				'x' => 410,
				'y' => 470,
			)
		),
		array(
			'time' => 0,
			'name' => 'createObstacle',
			'attrs' => array(
				'id' => 6,
				'x' => 380,
				'y' => 150,
			)
		),
		array(
			'time' => 0,
			'name' => 'createObstacle',
			'attrs' => array(
				'id' => 7,
				'x' => 600,
				'y' => 220,
			)
		),
		
		//
		// mech 1
		//
		array(
			'time' => 0,
			'name' => 'startTurning',
			'attrs' => array(
				'id' => 1,
				'angspeed' => 60,
			)
		),
		array(
			'time' => 0.7,
			'name' => 'endTurning',
			'attrs' => array(
				'id' => 1,
			)
		),
		array(
			'time' => 0.7,
			'name' => 'startMoving',
			'attrs' => array(
				'id' => 1,
				'angle' => 90 - 3.666666666666654,
				'speed' => 100,
			)
		),
		array(
			'time' => 3.5,
			'name' => 'endMoving',
			'attrs' => array(
				'id' => 1,
			)
		),
		array(
			'time' => 3.5,
			'name' => 'startTurning',
			'attrs' => array(
				'id' => 1,
				'angspeed' => -60,
			)
		),
		array(
			'time' => 4.4,
			'name' => 'endTurning',
			'attrs' => array(
				'id' => 1,
			)
		),
		array(
			'time' => 4.4,
			'name' => 'startMoving',
			'attrs' => array(
				'id' => 1,
				'angle' => 90 - 50.9999999999995,
				'speed' => 100,
			)
		),
		array(
			'time' => 4.7,
			'name' => 'endMoving',
			'attrs' => array(
				'id' => 1,
			)
		),
		
		
		//
		// mech 2
		//
		array(
			'time' => 0,
			'name' => 'startTurning',
			'attrs' => array(
				'id' => 2,
				'angspeed' => -60,
			)
		),
		array(
			'time' => 0.35,
			'name' => 'endTurning',
			'attrs' => array(
				'id' => 2,
			)
		),
		array(
			'time' => 0.35,
			'name' => 'startMoving',
			'attrs' => array(
				'id' => 2,
				'angle' => 90 - 242.33333333333346,
				'speed' => 100,
			)
		),
		array(
			'time' => 1.6,
			'name' => 'endMoving',
			'attrs' => array(
				'id' => 2,
			)
		),
		array(
			'time' => 1.6,
			'name' => 'createProjectile',
			'attrs' => array(
				'id' => 8,
				'x' => 606.4194431554481 + cos(245.00000000000014 * 3.1415 / 180) * 25 - cos((245.00000000000014 + 90) * 3.1415 / 180) * 35,
				'y' => 137.16820816412346 + sin(245.00000000000014 * 3.1415 / 180) * 25 - sin((245.00000000000014 + 90) * 3.1415 / 180) * 35,
				'angle' => 245.00000000000014,
				'type' => 'Missile',
			)
		),
		array(
			'time' => 1.6,
			'name' => 'startMoving',
			'attrs' => array(
				'id' => 8,
				'angle' => 90 - 245.00000000000014,
				'speed' => 500,
			)
		),
		array(
			'time' => 2.6,
			'name' => 'endMoving',
			'attrs' => array(
				'id' => 8,
			)
		),
		array(
			'time' => 2.6,
			'name' => 'eraseProjectile',
			'attrs' => array(
				'id' => 8,
			)
		),
		array(
			'time' => 2.6,
			'name' => 'modifyHealth',
			'attrs' => array(
				'id' => 1,
				'value' => -40,
			)
		),
		
		
	),
);
		
		
		
$cenas = array(
	'totalTime' => 0,
	'mapWidth' => 800,
	'mapHeight' => 600,
	'events' => array(
		array(
			'time' => 0.5,
			'name' => 'startMoving',
			'attrs' => array(
				'id' => 1,
				'angle' => 45,
				'speed' => 100,
			)
		),
		array(
			'time' => 2.0,
			'name' => 'endMoving',
			'attrs' => array(
				'id' => 1,
			)
		),
		array(
			'time' => 2.0,
			'name' => 'startTurning',
			'attrs' => array(
				'id' => 1,
				'angspeed' => 60,
			)
		),
		array(
			'time' => 2.5,
			'name' => 'endTurning',
			'attrs' => array(
				'id' => 1,
			)
		),
		array(
			'time' => 2.5,
			'name' => 'startMoving',
			'attrs' => array(
				'id' => 1,
				'angle' => 75,
				'speed' => 100,
			)
		),
		array(
			'time' => 3.0,
			'name' => 'createProjectile',
			'attrs' => array(
				'id' => 4,
				'x' => 219.58212286666915 + cos(15.66666666666685 * 3.1415 / 180) * 0 - cos((15.66666666666685 + 90) * 3.1415 / 180) * -30,
				'y' => 393.49118967136644 + sin(15.66666666666685 * 3.1415 / 180) * 0 - sin((15.66666666666685 + 90) * 3.1415 / 180) * -30,
				'angle' => 15.66666666666685,
				'type' => 'Mine',
			)
		),
		array(
			'time' => 3.5,
			'name' => 'endMoving',
			'attrs' => array(
				'id' => 1,
			)
		),
		array(
			'time' => 3.5,
			'name' => 'startTurning',
			'attrs' => array(
				'id' => 1,
				'angspeed' => -60,
			)
		),
		array(
			'time' => 4.35,
			'name' => 'endTurning',
			'attrs' => array(
				'id' => 1,
			)
		),
		array(
			'time' => 4.5,
			'name' => 'createProjectile',
			'attrs' => array(
				'id' => 5,
				'x' => 231.6603449714534 + cos(65.66666666666637 * 3.1415 / 180) * 25 - cos((65.66666666666637 + 90) * 3.1415 / 180) * 35,
				'y' => 348.41465111120937 + sin(65.66666666666637 * 3.1415 / 180) * 25 - sin((65.66666666666637 + 90) * 3.1415 / 180) * 35,
				'angle' => 65.66666666666637,
				'type' => 'Missile',
			)
		),
		array(
			'time' => 4.5,
			'name' => 'startMoving',
			'attrs' => array(
				'id' => 5,
				'angle' => 90 - 65.66666666666637,
				'speed' => 260,
			)
		),
		array(
			'time' => 6,
			'name' => 'endMoving',
			'attrs' => array(
				'id' => 5,
			)
		),
		array(
			'time' => 6,
			'name' => 'eraseProjectile',
			'attrs' => array(
				'id' => 5,
			)
		),
		array(
			'time' => 6,
			'name' => 'modifyHealth',
			'attrs' => array(
				'id' => 2,
				'value' => -50,
			)
		),
	)
);
?>
