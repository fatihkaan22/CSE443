package dev.fatih.cse443hw1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class Log {
  private ObservableList<String> logList;

  public Log(ObservableList<String> logList) {
    this.logList = logList;
  }

  public Log() {
    this.logList = FXCollections.observableArrayList();
  }

  public void add(String s) {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    sb.append(LocalTime.now());
    sb.append("] ");
    sb.append(s);
    logList.add(0, sb.toString());
  }

  public ObservableList<String> getList() {
    return logList;
  }
}
