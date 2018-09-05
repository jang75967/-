<?php
//id login 
require "init.php";
$user_id="201341305";
$User_Number=$_POST["User_Number"];
$Card_Name=$_POST["Card_Name"];
$List_Place=$_POST["List_Place"];
$List_Price=$_POST["List_Price"];

mysqli_set_charset($con, 'utf8');
$squl_query="INSERT INTO List (User_No,Card_Name,List_Place,List_Price) values('$User_Number','$Card_Name','$List_Place','$List_Price');";
mysqli_select_db($con,$user_id);
mysqli_query($con,$squl_query);
?>