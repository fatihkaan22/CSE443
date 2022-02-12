package common;

import javax.swing.*;
import java.time.LocalTime;

public class Log {
  DefaultListModel<String> logList;

  public Log() {
    this.logList = new DefaultListModel<>();
  }

  public void add(String s) {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    sb.append(LocalTime.now());
    sb.append("] ");
    sb.append(s);
    logList.add(0, sb.toString());
  }

  public DefaultListModel<String> getList() {
    return logList;
  }
}
