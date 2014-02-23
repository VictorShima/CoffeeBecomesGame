<?php
header('content-type', 'text/html; charset=utf-8');
session_name("MEVO");
session_start();

// set the page
if (!isset($_GET['page'])) {
	$_GET['page'] = '';
}

?>
<!doctype html>
<html>

<head>
	<title>Coffee Becomes Game</title>
	
	<script type="application/javascript" src="jquery-1.9.1.js"></script>
	<script type="application/javascript" src="jquery-ui-1.10.4.min.js"></script>
	<script type="application/javascript" src="mechevo-visual.js"></script>
	
	<link type="text/css" rel="stylesheet" href="jquery-ui-1.10.4.min.css" />
	<link type="text/css" rel="stylesheet" href="reset.css" />
	<link type="text/css" rel="stylesheet" href="style.css" />
</head>

<body>

	<div class="page-container">
	
		<div class="page-header">
			<h1>Mechas become Coffee</h1>
		</div>
		
		<ul class="page-nav"><!--
			--><li><a href="index.php?page=about">Learn</a></li><!--
			--><li><a href="index.php?page=build">Build</a></li><!--
			--><li><a href="index.php?page=fight">Fight</a></li><!--
		--></ul>
		
		<div class="page-content">
			<?php
				switch ($_GET['page']) {
					case 'about' : 
						include 'about_page.php';
						break;
					case 'build' :
						include 'build_page.php';
						break;
					case 'fight' : 
						include 'fight_page.php';
						break;
					default :
						include 'about_page.php';
						break;
				}
			?>
		</div>
	
	</div>
	
</body>

</html>






