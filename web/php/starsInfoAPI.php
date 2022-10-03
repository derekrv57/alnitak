<?php
include 'connection.php';
$id  = $_POST['ID'];
if ($id=="") {
    $id = $_GET['id'];
}
$sql = "SELECT * FROM stars WHERE id = '$id'";
$result = $conn->query($sql);
$rows = array();
while ($row = $result->fetch_assoc()) {
    $rows[] = $row;
}

echo json_encode($rows);
