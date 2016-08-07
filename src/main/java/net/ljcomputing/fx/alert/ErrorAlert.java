/**
           Copyright 2016, James G. Willmore

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package net.ljcomputing.fx.alert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 * The Error Alert.
 * 
 * @author James G. Willmore
 *
 */
public class ErrorAlert {

  /** The logger. */
  private static final Logger LOGGER = LoggerFactory
      .getLogger(ErrorAlert.class);

  /**
   * Show the alert.
   *
   * @param exception the exception
   */
  public void show(final Exception exception) {
    LOGGER.error("An exception occured during processing", exception);

    final Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText("An exception occured during processing");
    alert.setContentText(exception.toString());

    final Label label = new Label("The exception stacktrace was:");

    final TextArea textArea = new TextArea(exceptionToString(exception));
    textArea.setEditable(false);
    textArea.setWrapText(true);

    textArea.setMaxWidth(Double.MAX_VALUE);
    textArea.setMaxHeight(Double.MAX_VALUE);
    GridPane.setVgrow(textArea, Priority.ALWAYS);
    GridPane.setHgrow(textArea, Priority.ALWAYS);

    final GridPane expContent = new GridPane();
    expContent.setMaxHeight(Double.MAX_VALUE);
    expContent.setMaxWidth(Double.MAX_VALUE);
    expContent.add(label, 0, 0);
    expContent.add(textArea, 0, 1);

    alert.getDialogPane().setContent(expContent);
    alert.showAndWait();
  }

  /**
   * Exception to string.
   *
   * @param exception the exception
   * @return the string
   */
  private String exceptionToString(final Exception exception) {
    final StringWriter stringWriter = new StringWriter();
    final PrintWriter printWriter = new PrintWriter(stringWriter);
    exception.printStackTrace(printWriter);
    return stringWriter.toString();
  }
}
