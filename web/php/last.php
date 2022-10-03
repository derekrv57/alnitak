<?php
	include 'connection.php';
	$sql = "SELECT MAX(starDate)  FROM stars";
	$result = $conn->query($sql);
	if ($result->num_rows > 0) {
	  while($row = $result->fetch_assoc()) {
	    $date=$row['MAX(starDate)'];
	  }
	} else {
	  echo "0 results";
	}
	$conn->close();
	header('Location: ../iframe.php?date='.$date);
?>