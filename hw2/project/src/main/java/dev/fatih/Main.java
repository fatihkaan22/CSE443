package dev.fatih;

import dev.fatih.common.Util;
import dev.fatih.model.hero.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

  public static List<HeroFactory> factories;

  @Override
  public void start(Stage stage) throws IOException {

    factories = new ArrayList<>();
    factories.add(new AtlantisFactory());
    factories.add(new ValhallaFactory());
    factories.add(new UnderwildFactory());

    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("app.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), Util.WIDTH, Util.HEIGHT);
    stage.setTitle(Util.TITLE);
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}