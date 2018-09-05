<?php
//id login 
require "init.php";
$user_id="201341305";
$User_Number=$_POST["User_Number"];
$sum=0;
$sumweek=0;
for($i=1; $i<=31; $i++){
	if($i>=10)
	$date=date("Y-m-$i");

	else
    $date=date("Y-m-0$i");	
	
	$squl_query="SELECT List_Date,Card_Name,List_Place,List_Price from List where User_No='$User_Number' AND List_Date LIKE '$date%';";

	mysqli_select_db($con,$user_id);
	$result=mysqli_query($con,$squl_query);

	while($row = mysqli_fetch_array($result)){
	$sum +=$row["List_Price"];
	}
	$sumweek+=$sum;
		if($i %7==0){
			echo "<1>";
			echo $sumweek;
			echo "<br/>";
			$sumweek=0;
		}
		
		else if($i ==31){
			echo "<1>";
			echo $sumweek;
			echo "<br/>";
		}
		$sum=0;
}
?>