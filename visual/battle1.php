<?php
return array(
	'totalTime' => 6.0,
	'mapWidth' => 800,
	'mapHeight' => 600,
	'events' => array(
		array(
			'time' => 0,
			'name' => 'createPlayer',
			'attrs' => array(
				'id' => 1,
				'teamId' => 1,
				'x' => 100,
				'y' => 550,
				'angle' => 45,
				'hp' => 100,
				'color' => '#FF0000',
				'weaponLeft' => 'MissileLauncher',
				'weaponRight' => 'MissileLauncher',
				'weaponCenter' => 'MineSetter',
			)
		),
		array(
			'time' => 0,
			'name' => 'createPlayer',
			'attrs' => array(
				'id' => 2,
				'teamId' => 2,
				'x' => 650,
				'y' => 200,
				'angle' => 225,
				'hp' => 100,
				'color' => '#0000FF',
				'weaponLeft' => 'MissileLauncher',
				'weaponRight' => '',
				'weaponCenter' => '',
			)
		),
		array(
			'time' => 0,
			'name' => 'createObstacle',
			'attrs' => array(
				'id' => 3,
				'x' => 400,
				'y' => 400,
			)
		),
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
