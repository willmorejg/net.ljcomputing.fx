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
import java.time.format.DateTimeFormatter;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.InputEvent;
import javafx.util.StringConverter;

/**
 * A time spinner.
 * <p>
 * Original code: http://stackoverflow.com/questions/32613619/how-to-make-a-timespinner-in-javafx/32617768#32617768
 * 
 * @author James G. Willmore
 *
 */
public class TimeSpinner extends Spinner<LocalTime> implements Comparable<Spinner<LocalTime>> {
  
  /** The formatter. */
  private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm:ss");
  
  /**
   * Mode represents the unit that is currently being edited.
   * For convenience expose methods for incrementing and decrementing that
   * unit, and for selecting the appropriate portion in a spinner's editor.
   */
  enum Mode {

    HOURS {

      /**
       * @see net.ljcomputing.fx.control.time.TimeSpinner.Mode#increment(java.time.LocalTime, int)
       */
      @Override
      LocalTime increment(LocalTime time, int steps) {
        return time.plusHours(steps);
      }

      /**
       * @see net.ljcomputing.fx.control.time.TimeSpinner.Mode#select(net.ljcomputing.fx.control.time.TimeSpinner)
       */
      @Override
      void select(TimeSpinner spinner) {
        int index = spinner.getEditor().getText().indexOf(':');
        spinner.getEditor().selectRange(0, index);
      }
    },
    MINUTES {

      /**
       * @see net.ljcomputing.fx.control.time.TimeSpinner.Mode#increment(java.time.LocalTime, int)
       */
      @Override
      LocalTime increment(LocalTime time, int steps) {
        return time.plusMinutes(steps);
      }

      /**
       * @see net.ljcomputing.fx.control.time.TimeSpinner.Mode#select(net.ljcomputing.fx.control.time.TimeSpinner)
       */
      @Override
      void select(TimeSpinner spinner) {
        int hrIndex = spinner.getEditor().getText().indexOf(':');
        int minIndex = spinner.getEditor().getText().indexOf(':', hrIndex + 1);
        spinner.getEditor().selectRange(hrIndex + 1, minIndex);
      }
    },
    SECONDS {

      /**
       * @see net.ljcomputing.fx.control.time.TimeSpinner.Mode#increment(java.time.LocalTime, int)
       */
      @Override
      LocalTime increment(LocalTime time, int steps) {
        return time.plusSeconds(steps);
      }

      /**
       * @see net.ljcomputing.fx.control.time.TimeSpinner.Mode#select(net.ljcomputing.fx.control.time.TimeSpinner)
       */
      @Override
      void select(TimeSpinner spinner) {
        int index = spinner.getEditor().getText().lastIndexOf(':');
        spinner.getEditor().selectRange(index + 1,
            spinner.getEditor().getText().length());
      }
    };

    /**
     * Increment.
     *
     * @param time the time
     * @param steps the steps
     * @return the local time
     */
    abstract LocalTime increment(LocalTime time, int steps);

    /**
     * Select.
     *
     * @param spinner the spinner
     */
    abstract void select(TimeSpinner spinner);

    /**
     * Decrement.
     *
     * @param time the time
     * @param steps the steps
     * @return the local time
     */
    LocalTime decrement(LocalTime time, int steps) {
      return increment(time, -steps);
    }
  }

  // Property containing the current editing mode:

  /** The mode. */
  private final ObjectProperty<Mode> mode = new SimpleObjectProperty<>(
      Mode.HOURS);

  /**
   * Mode property.
   *
   * @return the object property
   */
      public ObjectProperty<Mode> modeProperty() {
    return mode;
  }

  /**
   * Gets the mode.
   *
   * @return the mode
   */
  public final Mode getMode() {
    return modeProperty().get();
  }

  /**
   * Sets the mode.
   *
   * @param mode the new mode
   */
  public final void setMode(Mode mode) {
    modeProperty().set(mode);
  }

  /**
   * Instantiates a new time spinner.
   *
   * @param time the time
   */
  public TimeSpinner(LocalTime time) {
    setEditable(true);

    // Create a StringConverter for converting between the text in the
    // editor and the actual value:

    

    StringConverter<LocalTime> localTimeConverter = new StringConverter<LocalTime>() {

      @Override
      public String toString(LocalTime time) {
        if(null != time) {
          return formatter.format(time);
        }
        
        return null;
      }

      @Override
      public LocalTime fromString(String string) {
        String[] tokens = string.split(":");
        int hours = getIntField(tokens, 0);
        int minutes = getIntField(tokens, 1);
        int seconds = getIntField(tokens, 2);
        int totalSeconds = (hours * 60 + minutes) * 60 + seconds;
        return LocalTime.of((totalSeconds / 3600) % 24,
            (totalSeconds / 60) % 60, seconds % 60);
      }

      private int getIntField(String[] tokens, int index) {
        if (tokens.length <= index || tokens[index].isEmpty()) {
          return 0;
        }
        return Integer.parseInt(tokens[index]);
      }

    };

    // The textFormatter both manages the text <-> LocalTime conversion,
    // and vetoes any edits that are not valid. We just make sure we have
    // two colons and only digits in between:

    TextFormatter<LocalTime> textFormatter = new TextFormatter<LocalTime>(
        localTimeConverter, LocalTime.now(), c -> {
          String newText = c.getControlNewText();

          if (newText.matches("[0-9]{0,2}:[0-9]{0,2}:[0-9]{0,2}")) {
            return c;
          }

          return null;
        });

    // The spinner value factory defines increment and decrement by
    // delegating to the current editing mode:

    SpinnerValueFactory<LocalTime> valueFactory = new SpinnerValueFactory<LocalTime>() {

      {

        setConverter(localTimeConverter);
        setValue(time);
      }

      @Override
      public void decrement(int steps) {
        setValue(mode.get().decrement(getValue(), steps));
        mode.get().select(TimeSpinner.this);
      }

      @Override
      public void increment(int steps) {
        setValue(mode.get().increment(getValue(), steps));
        mode.get().select(TimeSpinner.this);
      }

    };

    this.setValueFactory(valueFactory);
    this.getEditor().setTextFormatter(textFormatter);

    // Update the mode when the user interacts with the editor.
    // This is a bit of a hack, e.g. calling spinner.getEditor().positionCaret()
    // could result in incorrect state. Directly observing the caretPostion
    // didn't work well though; getting that to work properly might be
    // a better approach in the long run.
    this.getEditor().addEventHandler(InputEvent.ANY, e -> {
      int caretPos = this.getEditor().getCaretPosition();
      int hrIndex = this.getEditor().getText().indexOf(':');
      int minIndex = this.getEditor().getText().indexOf(':', hrIndex + 1);
      if (caretPos <= hrIndex) {
        mode.set(Mode.HOURS);
      } else if (caretPos <= minIndex) {
        mode.set(Mode.MINUTES);
      } else {
        mode.set(Mode.SECONDS);
      }
    });

    // When the mode changes, select the new portion:
    mode.addListener((obs, oldMode, newMode) -> newMode.select(this));

  }

  /**
   * Instantiates a new time spinner.
   */
  public TimeSpinner() {
    this(LocalTime.now());
  }

  /**
   * Sets the local time.
   *
   * @param time the new local time
   */
  public void setLocalTime(LocalTime time) {
    this.getValueFactory().setValue(time);
  }
  
  /**
   * Checks if this Spinner's date time is equal to the given date time.
   *
   * @param otherLocalTime the other local time
   * @return true, if is equal
   */
  public boolean isEqual(final LocalDateTime otherLocalTime) {
    return compareDateTime(otherLocalTime) == 0;
  }  
  
  /**
   * Checks if this Spinner's date time is after the given date time.
   *
   * @param otherLocalTime the other local time
   * @return true, if is after
   */
  public boolean isAfter(final LocalDateTime otherLocalTime) {
    return compareDateTime(otherLocalTime) > 0;
  }  
  
  /**
   * Compare this Spinner's date time to the given date time.
   *
   * @param otherLocalTime the other local time
   * @return the int
   */
  private int compareDateTime(final LocalDateTime otherLocalTime) {
    final LocalDateTime meDt = getValue().atDate(LocalDate.now());
    return meDt.isAfter(otherLocalTime) ? 1 : meDt.isBefore(otherLocalTime) ? -1 : 0;
  }

  /**
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public int compareTo(final Spinner<LocalTime> other) {
    final LocalDateTime otherDt = other.getValue().atDate(LocalDate.now());
    return isAfter(otherDt) ? 1 : isEqual(otherDt) ? 0 : -1;
  }
}