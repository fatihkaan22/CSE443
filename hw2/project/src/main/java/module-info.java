module dev.fatih.cse443hw2 {
  requires javafx.controls;
  requires javafx.fxml;


  opens dev.fatih to javafx.fxml;
  exports dev.fatih;
  exports dev.fatih.model;
  opens dev.fatih.model to javafx.fxml;
  exports dev.fatih.model.hero;
  opens dev.fatih.model.hero to javafx.fxml;
}