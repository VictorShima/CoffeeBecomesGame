<?php

//TODO: Create $input
//TODO: Sent to Simulator and retrieve $output

// DEBUG: $output will be generated here
$output = array(
	'totalTime' => 1,
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
				'color' => 'FF0000',
				'weaponLeft' => 'MissileLauncher',
				'weaponRight' => 'MissileLauncher',
				'weaponCenter' => ''
			)
		),
		array(
			'time' => 0,
			'name' => 'createPlayer',
			'attrs' => array(
				'id' => 2,
				'teamId' => 2,
				'x' => 700,
				'y' => 150,
				'angle' => 270,
				'color' => '0000FF',
				'weaponLeft' => 'MissileLauncher',
				'weaponRight' => 'MissileLauncher',
				'weaponCenter' => ''
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
			'name' => 'startMove',
			'attrs' => array(
				'id' => 1,
				'angle' => 90,
				'speed' => 10
			)
		),
		array(
			'time' => 2.5,
			'name' => 'endMove',
			'attrs' => array(
				'id' => 1
			)
		)
	)
);
		
		
?>
<!doctype html>
<html>
	<head>
		<title>Visual Simulator - MechEvo</title>
		<script type="application/javascript" src="//code.jquery.com/jquery-1.9.1.js"></script>
		<script type="application/javascript" src="mechevo-visual.js"></script>
	</head>
	<body>
	
		<script type="application/javascript">
			$(function(){
			
				var report = JSON.parse('<?= json_encode($output); ?>');
				
				//console.log(report);
				
				var vs = new VS;
				console.log("VS: ", vs);
				console.log("VS: ", report);
				vs.init("Battle", report);
			
			});
		</script>
		
		<div id="Battle" style="background-color: #AAA;"></div>
			
	
		<?= '<pre>'.json_encode($output).'</pre>'; ?>
		
	</body>
</html>


