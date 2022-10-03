<?php 
	if(isset($_GET['date'])) {
	    $getDate = $_GET['date'];
	    $url = '"iframe.php?date='.$getDate.'"';
	}
	else{
		$url = 'php/last.php';
	}
?>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<script type="text/javascript" src="js/main.js"></script>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<link href="https://fonts.googleapis.com/css?family=Montserrat:300,400,600|Open+Sans" rel="stylesheet">
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css">
	<link rel="stylesheet" href="estilos.css">
	<script src="https://unpkg.com/sweetalert2@9.5.3/dist/sweetalert2.all.min.js"></script>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css" integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn" crossorigin="anonymous">
	<link rel="stylesheet" type="text/css" href="css/estilos.css">
	<link rel="icon" type="image/png" href="img/png/Colorway=2-Color White alt.png">
	<title>Alnitak</title>
</head>
<body onresize="resize()" onload="resize()">
	<div class="py-4 px-5">
		<form method="GET" style="display:block" id="frmDate" onchange='document.getElementById("frmDate").submit();'>
			<img src="img/png/Colorway=2-Color White alt.png" id="logo">
            <?php include 'php/dates.php';?>
		</form>
	</div>
	<center>
		<iframe id="map" src=<?php echo $url; ?>></iframe>
	</center>
</body>
</html>
<style type="text/css">
	body {
	  overflow: hidden; /* Hide scrollbars */
	}
</style>