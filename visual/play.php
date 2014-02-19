<?php

//TODO: Create $input
//TODO: Sent to Simulator and retrieve $output

// DEBUG: $output will be generated here
$output = include("battle2.php");

if (isset($_POST['battle'])) {
	$battle = trim($_POST['battle'],"\n\r");
}

		
		
?>
<!doctype html>
<html>
	<head>
		<title>Visual Simulator - MechEvo</title>
		<script type="application/javascript" src="//code.jquery.com/jquery-1.9.1.js"></script>
		<script type="application/javascript" src="mechevo-visual.js"></script>
	</head>
	<body>
	
		<?php if (isset($battle)) : ?>
	
			<script type="application/javascript">
				var vs = new VS;
				$(function(){
			
					<?php /*var report = JSON.parse('<?= json_encode($output); ?>'); */ ?>
					var report = JSON.parse('<?= $battle; ?>');
				
					console.log("VS: ", vs);
					console.log("Report: ", report);
					vs.init("Battle", report);
			
				});
			</script>
		
			<div id="Battle" style="height:600px; width:800px; background-color:#FEE; overflow:visible;"></div>
			
		<?php endif; ?>
		
		<hr />
		<form action="" method="post">
			<textarea name="battle" style="width:800px; height:500px;"></textarea>
			<br />
			<input type="submit" value="Simulate" />
		</form>
			
			
	
		
	</body>
</html>


