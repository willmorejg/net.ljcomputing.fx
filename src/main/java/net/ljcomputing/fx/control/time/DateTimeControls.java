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

import javafx.scene.control.DatePicker;

/**
 * Date and time controls.
 * 
 * @author James G. Willmore
 *
 */
public class DateTimeControls {

  /** The from date selector. */
  private DatePicker dateSelector;
  
  /** The from time control. */
  private TimeControl timeControl;
  
  /**
   * Instantiates a new date time controls.
   *
   * @param dateSelector the date selector
   * @param timeControl the time control
   */
  public DateTimeControls(DatePicker dateSelector, TimeControl timeControl) {
    this.dateSelector = dateSelector;
    this.timeControl = timeControl;
  }
  
  /**
   * Gets the currently selected local date time from the given date / time controls.
   *
   * @return the local date time
   */
  public LocalDateTime getLocalDateTime() {
    LocalDate localDate = dateSelector.getValue();
    LocalTime localTime = timeControl.getLocalTime();
    
    return LocalDateTime.of(localDate, localTime);
  }
}
