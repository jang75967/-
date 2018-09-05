<?php
//id login 
require "init.php";
$date=date("Y-m-d");
$user_id="201341305";
$User_Number=$_POST["User_Number"];
$sql_query="SELECT Goal_Today from Goal where Goal_Date LIKE '$date%' AND User_No='$User_Number';";
mysqli_select_db($con,$user_id);
$result=mysqli_query($con,$sql_query);

$row = mysqli_fetch_array($result);


echo "<br/>";
echo $row["Goal_Today"]; 


?> 