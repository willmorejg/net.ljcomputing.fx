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

package net.ljcomputing.fx.control.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

import javafx.scene.layout.StackPane;

/**
 * A JavaFX time control.
 * 
 * @author James G. Willmore
 *
 */
public class TimeControl extends StackPane {

  /** The spinner. */
  private final TimeSpinner spinner;

  /**
   * Instantiates a new time control.
   */
  public TimeControl() {
    this(LocalTime.now());
  }
  
  /**
   * Instantiates a new time control.
   *
   * @param date the date
   */
  public TimeControl(final Date date) {
    this();
    setLocalTime(date);
  }

  /**
   * Instantiates a new time control.
   *
   * @param time the time
   */
  public TimeControl(final LocalTime time) {
    super();
    spinner = new TimeSpinner();
    setLocalTime(time);
    getChildren().add(spinner);
    setWidth(350);
    setHeight(120);
  }
  
  /**
   * Sets the local time.
   *
   * @param date the new local time
   */
  public void setLocalTime(final Date date) {
    LocalTime ldt = null;
    
    if(date != null) {
      ldt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toLocalTime();  
    }

    setLocalTime(ldt);
  }

  /**
   * Sets the local time.
   *
   * @param time the new local time
   */
  public void setLocalTime(final LocalTime time) {
    spinner.setLocalTime(time);
  }
  
  /**
   * Gets the current local time value.
   *
   * @return the local time
   */
  public LocalTime getLocalTime() {
    return spinner.getValue();
  }
  
  /**
   * Gets the local time as date.
   *
   * @return the local time as date
   */
  public Date getLocalTimeAsDate() {
    return getLocalTime() == null
        ? null : Date.from(getLocalTime().atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant());
  }
  
  /**
   * Spinner property.
   *
   * @return the spinner
   */
  public TimeSpinner spinnerProperty() {
    return spinner;
  }
}
