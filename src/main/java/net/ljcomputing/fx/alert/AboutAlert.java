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
 * The About Alert.
 * 
 * @author James G. Willmore
 *
 */
public class AboutAlert {
  /** The about message. */
  private transient final String aboutMessage;
  
  /**
   * Instantiates a new about alert.
   *
   * @param aboutMessage the about message
   */
  public AboutAlert(final String aboutMessage) {
    this.aboutMessage = aboutMessage;
  }

  /**
   * Show the alert.
   */
  public void show() {
    final Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Information Dialog");
    alert.setHeaderText(null);
    alert.setContentText(aboutMessage);
    alert.showAndWait();
  }
}
