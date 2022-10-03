<!DOCTYPE html>
<html>
<head>
	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta http-equiv="X-UA-Compatible" content="ie=edge">
		<link href="https://fonts.googleapis.com/css?family=Montserrat:300,400,600|Open+Sans" rel="stylesheet">
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css">
		<link rel="stylesheet" href="estilos.css">
		<script src="https://unpkg.com/sweetalert2@9.5.3/dist/sweetalert2.all.min.js"></script>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css" integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn" crossorigin="anonymous">
		<title></title>
	</head>
<body>
	<script>
		// La función se llama cuando se hace click en el canvas
		function clickMe(id) {
			var info;
			var oParam = {
				"ID": id
			};
			$.ajax({
				type: "post",
				url: "php/starsInfoAPI.php",
				data: oParam,
				success: function(response) {
					const res = JSON.parse(response);
					console.table(res);
					info = res[0].info
					//Muestra una alerta
					Swal.fire({

						title: res[0].nombre,
						//text: res[0].info,
						html: Array.from(new Set(res[0].info.split(','))).toString(),
						confirmButtonText: "Close",
					});
				}
			});
		}

		function parseSVG(s) {
			var div = document.createElementNS('http://www.w3.org/1999/xhtml', 'div');
			div.innerHTML = '<svg xmlns="http://www.w3.org/2000/svg">' + s + '</svg>';
			var frag = document.createDocumentFragment();
			while (div.firstChild.firstChild)
				frag.appendChild(div.firstChild.firstChild);
			return frag;
		}

		function printStar(x, y, size, id) {

			document.getElementById('panel').appendChild(parseSVG(
				"<circle cx='" + x + "' cy='" + y + "' r='" + size + "' fill='url(#rad1)'  onclick=\"clickMe('" + id + "');\" />"
			));
		}
	</script>
	<?php
	if(isset($_GET['date'])) {
	    $getDate = $_GET['date'];
	}
	if ($getDate == null) {
		header('Location: php/last.php');
		exit(0);
	}
	?>
	
	<!-- Create the SVG pane. -->
	<style>
		svg {

            <?php
                include 'php/connection.php';
                $sql = "SELECT MIN(x), MIN(y), MAX(x), MAX(y) FROM stars WHERE starDate =   '" . $getDate . "'";
                $result = $conn->query($sql);
                if ($result->num_rows > 0) {
                    $i = 0;
                    while ($row = $result->fetch_assoc()) {
                        $x=$row['MIN(x)'];
                        $y=$row['MIN(y)'];
                        $w=$row['MAX(x)']+100;
                        $h=$row['MAX(y)']+100;
                    }
                }
                $conn->close();
            ?>
			height: <?php echo $h; ?>px;
			width: <?php echo $w; ?>px;
			margin-top: 60px;
		}

		body {
			background-color: black;
		}

		/* Estilos para motores Webkit y blink (Chrome, Safari, Opera... )*/
		body::-webkit-scrollbar {
			-webkit-appearance: none;
		}

		body::-webkit-scrollbar:vertical {
			width: 10px;
		}

		body::-webkit-scrollbar-button:increment,
		body::-webkit-scrollbar-button {
			display: none;
		}

		body::-webkit-scrollbar:horizontal {
			height: 10px;
		}

		body::-webkit-scrollbar-thumb {
			margin: 4px;
			background-color: white;
			border-radius: 20px;
			border: 2px solid black;
		}

		body::-webkit-scrollbar-track {
			border-radius: 10px;
		}
	</style>
	<svg style="background-color:black" id="panel">
		<defs>
			<radialGradient id="rad1" >
			<stop offset="10%" stop-color="white" />
      <stop offset="95%" stop-color="black" />
 			</radialGradient>
		</defs>
		<!-- Create the circle. -->

	</svg>

	<?php
	include 'php/connection.php';
    $sql = "SELECT MIN(x), MIN(y) FROM stars WHERE starDate =   '" . $getDate . "'";
    $result = $conn->query($sql);
    if ($result->num_rows > 0) {
        $i = 0;
        while ($row = $result->fetch_assoc()) {
            $x=$row['MAX(x)'];
            $y=$row['MIN(y)'];
        }
    }
	$sql = "SELECT * FROM stars WHERE starDate =  '" . $getDate . "'";
	$result = $conn->query($sql);
	if ($result->num_rows > 0) {
		$i = 0;
		echo "<script>";
		while ($row = $result->fetch_assoc()) {
			print("printStar(" . $row["x"] . "," . $row["y"] . "," . $row["size"] . ",'" . $row["id"] . "');");
		}
		echo "</script>";
	} else {
		header('Location: php/last.php');
	}
	$conn->close();
	?>
</body>


<style>
	body {
		overflow: scroll;
	}
</style>
<script>
	$(window).mousedown(function(m) {
		curDown = true;
		curYPos = m.pageY;
		curXPos = m.pageX;
	});
	$(function() {
		var curDown = false,
			curYPos = 0,
			curXPos = 0;

		$(window).mousemove(function(m) {
			if (curDown) {
				window.scrollBy(curXPos - m.pageX, curYPos - m.pageY)
			}
		});

		$(window).mousedown(function(m) {
			curYPos = m.pageY;
			curXPos = m.pageX;
			curDown = true;
		});

		$(window).mouseup(function() {
			curDown = false;
		});
	})
</script>

</html>