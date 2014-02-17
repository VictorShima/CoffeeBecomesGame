<?php

//TODO: Create $input
//TODO: Sent to Simulator and retrieve $output

// DEBUG: $output will be generated here
$output = include("battle2.php");
		
		
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
			var vs = new VS;
			$(function(){
			
				var report = JSON.parse('<?= json_encode($output); ?>');
				
				//console.log(report);
				
				console.log("VS: ", vs);
				console.log("Report: ", report);
				vs.init("Battle", report);
			
			});
		</script>
		
		<div id="Battle"></div>
			
	
		
	</body>
</html>


