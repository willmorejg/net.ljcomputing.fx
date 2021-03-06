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

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Alert that is shown when a file is saved successfully.
 * 
 * @author James G. Willmore
 *
 */
public class FileSaveSuccessfulAlert {

  /** The file save successful message. */
  private final static String SUCCESSUL_MESSAGE = "File saved successfully";

  /**
   * Show.
   *
   * @param fileName the file name
   */
  public void show(final String fileName) {
    final Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Saved");
    alert.setContentText(SUCCESSUL_MESSAGE + " : " + fileName);
    alert.setResizable(true);
    alert.showAndWait();
  }
}
