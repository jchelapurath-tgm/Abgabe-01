<?php
require("connect.php");


try {
   $conn = new PDO("mysql:host=$servername;dbname=$db", $username, $password);
   $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
   }
catch(PDOException $e)
   {
     /*Hier gelangt der User hin, wenn die Verbindung mit der Datenbank nicht funktioniert hat.*/
   $inhalt = "Connection failed: " . $e->getMessage();
   $handle = fopen("errors.txt", "w");
   fwrite ($handle, $inhalt);
   fclose ($handle);

   }



   if (isset($_POST['loeschen'])) {
     if (!isset($_POST['pid'])) {
       /*Wenn der User keinen Passagier zum Löschen ausgewählt hat landet er hier.*/
       $meldung = 'Bitte markieren Sie den zu l&ouml;schenden Datensatz!' ;
       $handle = fopen("errors.txt", "w");
       fwrite ($handle, $meldung);
       fclose ($handle);
     } else {
       /*Hier wird die Error Datei wieder initialisiert, da es keinen Error gegeben hat.*/
       $handle = fopen("errors.txt", "w");
       fwrite ($handle, "");
       fclose ($handle);

       // Markierten Datensatz l�schen
       $sql = 'DELETE FROM passengers WHERE id=' . $_POST['pid'];
       $conn->query($sql);

     }
   }

if(isset($_POST['flugnr'])){
  /*Hier gelangt man rein, wenn der User die Flugnummer ausgewählt und auf suchen gedrückt hat.
  Dann ist die $_POST variable isset true*/
  $flightnr = $_POST['flugnr'];
  /*Hier schreibe ich mir die Flugnummer in eine Datei, um sie bei einem Reload der Webseite wieder ohne Userinput auslesen lassen zu können.*/
  $handle = fopen("flughilfe.txt", "w");
  fwrite ($handle, $flightnr);
  fclose ($handle);
}else{
  /*Hier komme ich rein wenn der User die Flugnummer nicht gerade gesucht hat. Bedeutet nach dem Seitenreload, der gezwungener Maßen nach dem Löschen
  eines Benutzers erfolgt, wird hier die Flugnummer ausgelesen, damit er es selber nicht noch einmal auswählen muss.*/
  $handle = fopen("flughilfe.txt", "r");
  while( $inhalt = fgets($handle, 4096)){
    $flightnr = $inhalt;
  }
}
?>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Flugticket - Manager</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
  </head>
  <body>

<!-- $_SERVER['PHP_SELF'] wird verwendet um dem Server zu sagen, dass er im PHP Code von dieser Datei das findet was er sucht.-->
<form action="<?=$_SERVER['PHP_SELF']?>" method="post">
    <div class='container'style='margin-top: 25px'>
     <div class='row text-center'>
       <div class='col-sm-12'>
         <h2>Flug auswählen</h2>
         <hr>
       </div>
     </div>
       <div class='row'>
         <div class='col-sm-6 text-right'>Bitte wählen Sie Ihre Flugnummer aus: &nbsp;</div>
         <div class='col-sm-6'>
           <select name='flugnr'>
             <?php
             $sql = "SELECT DISTINCT flightnr FROM flights ORDER BY 1";

             foreach($conn->query($sql) as $row) {
               if(isset($flightnr) && $flightnr == $row['flightnr']){
                 echo "<option selected>" . $row['flightnr'] . "</option>";
               }else{
               echo "<option>" . $row['flightnr'] . "</option>";
             }
             }

             ?>
           </select>
         </div>
       </div>
       <br>
       <div class='row text-center'>
         <div class='col-sm-12 text-center'>
           <input type='submit' value='Suchen'>
           <input type='hidden' value='1' name='flug' />
         </div>
       </div>
</form>
       </div>

<br>


<div class="container-fluid text-center"style='margin-top: 25px'>
  <div class="row text-center">
    <div class="col-sm-12">
      <h2>Daten zu Ihrem Flug (Flugnummer: <?php if(isset($flightnr)){echo $flightnr;}?>)</h2>
      <hr>
    </div>
  </div>


<?php

  if(isset($flightnr)){
   /*Hier schreibe ich mir die Flugnummer in eine Datei, um sie bei einem Reload der Webseite wieder auszulesen.*/
   $handle = fopen("flughilfe.txt", "w");
   fwrite ($handle, $flightnr);
   fclose ($handle);

   $sql = "SELECT * FROM flights WHERE flightnr = $flightnr";
   echo "<div class='row'><div class='col-sm-2'>Airline</div><div class='col-sm-2'>Abflugszeit</div><div class='col-sm-2'>Abflugsflughafen</div><div class='col-sm-2'>Ankunftszeit</div><div class='col-sm-2'>Ankunftsflughafen</div><div class='col-sm-2'>Flugzeugart</div></div><br>";
   foreach($conn->query($sql) as $row) {
     $output = "<div class='row'><div class='col-sm-2' style='border-right:1px solid black'>". $row['airline'] ."</div>";
     $output .= "<div class='col-sm-2' style='border-right:1px solid black'>". $row['departure_time'] ."</div>";
     $output .= "<div class='col-sm-2' style='border-right:1px solid black'>". $row['departure_airport'] ."</div>";
     $output .= "<div class='col-sm-2' style='border-right:1px solid black'>". $row['destination_time'] ."</div>";
     $output .= "<div class='col-sm-2' style='border-right:1px solid black'>". $row['destination_airport']."</div>";
     $output .= "<div class='col-sm-2'>". $row['planetype'] ."</div></div>";
     echo $output;
   }
   echo "</div>";
}

?>
<div class='container text-center'style='margin-top: 25px'>
 <div class='row text-center'>
   <div class='col-sm-12'>
     <h2>Personen auf diesem Flug <?php
     if( isset($flightnr)){
       $sql = "SELECT maxseats FROM planes INNER JOIN flights ON planes.id = flights.planetype WHERE flights.flightnr = $flightnr";
       foreach($conn->query($sql) as $row){
         $gesamtAnzahl = $row['maxseats'];
       }
       $sql = "SELECT count(id) as 'Anzahl' FROM passengers WHERE flightnr = $flightnr";
       foreach ($conn->query($sql) as $row) {
         $personenAnzahl = $row['Anzahl'];
       }
       $freiePlätze = $gesamtAnzahl-$personenAnzahl;

       if( isset($personenAnzahl)){
         echo "(Gesamtplätze: " . $gesamtAnzahl . ") (Freie Plätze: " . $freiePlätze . ")";
       }
     }
     ?></h2>
     <hr>

   </div>
 </div>
 <form action="<?=$_SERVER['PHP_SELF']?>" method="post">
   <?php
     if(isset($flightnr)){

       $handle = fopen("errors.txt", "r");
       while( $error = fgets($handle, 4096)){
         echo "<h5 style='color: red;'>" . $error . "</h5>";
       }

     $sql = "SELECT * FROM passengers WHERE flightnr = $flightnr ORDER BY 6,7";
     echo "<div class='row'><div class='col-sm-offset-1 col-sm-2'>Reihe</div><div class='col-sm-2'>Sitz</div><div class='col-sm-3'>Nachname</div><div class='col-sm-2'>Vorname</div></div><br>";
     foreach($conn->query($sql) as $row) {

       echo "<div class='row'><div class='col-sm-1'><input type='radio' name='pid' value='" . $row['id'] . "'></div><div class='col-sm-2'>" . $row['rownr'] . "</div><div class='col-sm-2'>" . $row['seatposition'] . "</div><div class='col-sm-3'>" . $row['lastname'] . "</div><div class='col-sm-2'>" . $row['firstname'] . "</div><div class='col-sm-2'><input type='submit' id='" . $flightnr . "' name='loeschen' value='L&ouml;schen'></div></div>";
     }
   }
   ?>
   <br>
   </div>
</form>
  </body>
</html>
