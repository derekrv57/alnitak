<select name="date" class="form-control d-inline" style="max-width:150px">
<?php
	$date=$_GET['date'];
	echo '<option value="'.$date.'" >'.$date.'</option>';
	include 'connection.php';
	$sql = "SELECT starDate FROM stars ORDER BY starDate DESC;";
	$result = $conn->query($sql);
	if ($result->num_rows > 0) {
	  while($row = $result->fetch_assoc()) {
	    $date=$row['starDate'];
	    echo '<option value="'.$date.'" >'.$date.'</option>';
	  }
	} else {
	  echo "0 results";
	}
	$conn->close();
?>
</select>