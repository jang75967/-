<?php
//id login 
require "init.php";
$user_id="201341305";
$User_Number="ffffffff-c106-e961-ffff-ffff99d603a9";
$sum=0;
for($i=0; $i<30; $i++){
	if($i>=10)
	$date=date("Y-m-$i");

	else
    $date=date("Y-m-0$i");	
	
	$squl_query="SELECT List_Date,Card_Name,List_Place,List_Price from List where User_No='$User_Number' AND List_Date LIKE '$date%';";

mysqli_select_db($con,$user_id);
$result=mysqli_query($con,$squl_query);

while($row = mysqli_fetch_array($result)){
echo $row["List_Price"];
$sum +=$row["List_Price"];
}
echo "<1>";
echo $sum;
echo "<br/>";
$sum=0;
}
?>