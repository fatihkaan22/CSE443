module dev.fatih.cse443hw1 {
  requires javafx.controls;
  requires javafx.fxml;


  opens dev.fatih.cse443hw1 to javafx.fxml;
  exports dev.fatih.cse443hw1;
}