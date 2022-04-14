package viewmodel;

import java.awt.Color;
import java.awt.Component;
import java.time.LocalDateTime;

import javax.swing.AbstractCellEditor;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.github.lgooddatepicker.components.DatePickerSettings.DateArea;
import com.github.lgooddatepicker.components.TimePickerSettings.TimeArea;
import com.github.lgooddatepicker.zinternaltools.InternalUtilities;
import com.privatejgoodies.forms.layout.ConstantSize;

@SuppressWarnings("serial")
public class DateTimeTableEditor extends AbstractCellEditor
implements TableCellEditor, TableCellRenderer{

	/**
	 * autoAdjustMinimumTableRowHeight, This indicates whether the minimum table row height (for all
	 * rows) should be modified when an editor or render is displayed. The row height is only adjusted
	 * if it is below the minimum value needed to display the DateTimePicker component.
	 */
	private boolean autoAdjustMinimumTableRowHeight = true;

	/**
	 * clickCountToEdit, An integer specifying the number of clicks needed to start editing. Even if
	 * clickCountToEdit is defined as zero, it will not initiate until a click occurs.
	 */
	public int clickCountToEdit = 1;

	/**
	 * matchTableBackgroundColor, This indicates whether this table editor should set the picker text
	 * area background color to match the background color of the table. The default value is true.
	 */
	private boolean matchTableBackgroundColor = true;

	/**
	 * matchTableSelectionBackgroundColor, This indicates whether this table editor should set the
	 * picker text area background color to match the background color of the table selection (when
	 * selected). The default value is true.
	 */
	private boolean matchTableSelectionBackgroundColor = true;

	/** borderFocusedCell, This holds the border that is used when a cell has focus. */
	private Border borderFocusedCell;

	/** borderUnfocusedCell, This holds the border that is used when a cell does not have focus. */
	private Border borderUnfocusedCell;

	/** dateTimepicker, This holds the DateTimePicker instance. */
	private DateTimePicker dateTimePicker;

	/** minimumRowHeight, This holds the minimum row height needed to display the DateTimePicker. */
	private int minimumRowHeightInPixels;

	/** Constructor, default. */
	public DateTimeTableEditor() {
		this(true, true, true);
	}

	public DateTimeTableEditor(
			boolean autoAdjustMinimumTableRowHeight,
			boolean matchTableBackgroundColor,
			boolean matchTableSelectionBackgroundColor) {
		// Save the constructor parameters.
		this.autoAdjustMinimumTableRowHeight = autoAdjustMinimumTableRowHeight;
		this.matchTableBackgroundColor = matchTableBackgroundColor;
		this.matchTableSelectionBackgroundColor = matchTableSelectionBackgroundColor;
		// Create the borders that should be used for focused and unfocused cells.
		JLabel exampleDefaultRenderer =
				(JLabel)
				new DefaultTableCellRenderer()
		.getTableCellRendererComponent(new JTable(), "", true, true, 0, 0);
		borderFocusedCell = exampleDefaultRenderer.getBorder();
		borderUnfocusedCell = new EmptyBorder(1, 1, 1, 1);
		// Create and set up the DateTimePicker.
		dateTimePicker = new DateTimePicker();
		dateTimePicker.timePicker.setEnableArrowKeys(false);
		dateTimePicker.setBorder(borderUnfocusedCell);
		dateTimePicker.setGapSize(2, ConstantSize.PIXEL);
		dateTimePicker.setBackground(Color.white);
		dateTimePicker.datePicker.setBackground(Color.white);
		dateTimePicker.timePicker.setBackground(Color.white);
		dateTimePicker.datePicker.getComponentDateTextField().setBorder(null);
		dateTimePicker.timePicker.getComponentTimeTextField().setBorder(null);
		// Adjust any needed picker settings.
		DatePickerSettings dateSettings = dateTimePicker.datePicker.getSettings();
		dateSettings.setGapBeforeButtonPixels(0);
		dateSettings.setSizeTextFieldMinimumWidthDefaultOverride(false);
		dateSettings.setSizeTextFieldMinimumWidth(20);
		dateSettings.setColor(DateArea.BackgroundMonthAndYearMenuLabels, Color.DARK_GRAY);
	    dateSettings.setColor(DateArea.BackgroundTodayLabel, Color.DARK_GRAY);
	    dateSettings.setColor(DateArea.BackgroundClearLabel, Color.DARK_GRAY);
	    dateSettings.setColor(DateArea.BackgroundCalendarPanelLabelsOnHover, Color.ORANGE);
	    dateSettings.setColor(DateArea.DatePickerTextValidDate, Color.ORANGE);
		TimePickerSettings timeSettings = dateTimePicker.timePicker.getSettings();
		timeSettings.setGapBeforeButtonPixels(0);
		timeSettings.setSizeTextFieldMinimumWidthDefaultOverride(false);
		timeSettings.setSizeTextFieldMinimumWidth(20);
		timeSettings.setColor(TimeArea.TimePickerTextValidTime, Color.ORANGE);
		// Calculate and store the minimum row height needed to display the DateTimePicker.
		minimumRowHeightInPixels = (dateTimePicker.getPreferredSize().height + 1);
	}

	public void setCellEditorValue(Object value) {
	    dateTimePicker.clear();
	    if (value == null) {
	      return;
	    }
	    if (value instanceof LocalDateTime) {
	      LocalDateTime nativeValue = (LocalDateTime) value;
	      dateTimePicker.setDateTimePermissive(nativeValue);
	    } else {
	      String text = value.toString();
	      String shorterText = InternalUtilities.safeSubstring(text, 0, 100);
	      dateTimePicker.datePicker.setText(shorterText);
	    }
	  }

	  private void zAdjustTableRowHeightIfNeeded(JTable table) {
	    if (!autoAdjustMinimumTableRowHeight) {
	      return;
	    }
	    if ((table.getRowHeight() < minimumRowHeightInPixels)) {
	      table.setRowHeight(minimumRowHeightInPixels);
	    }
	  }
	
	@Override
	public Object getCellEditorValue() {
		// TODO Auto-generated method stub
		return dateTimePicker.getDateTimePermissive();
	}
	
	

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		// TODO Auto-generated method stub
		// Save the supplied value to the DateTimePicker.
	    setCellEditorValue(value);
	    // Draw the appropriate background colors to indicate a selected or unselected state.
	    if (isSelected) {
	      if (matchTableSelectionBackgroundColor) {
	        Color selectionBackground = table.getSelectionBackground();
	        dateTimePicker.setBackground(selectionBackground);
	        dateTimePicker.datePicker.setBackground(selectionBackground);
	        dateTimePicker.timePicker.setBackground(selectionBackground);
	        dateTimePicker.datePicker.getComponentDateTextField().setBackground(selectionBackground);
	        dateTimePicker.timePicker.getComponentTimeTextField().setBackground(selectionBackground);
	      } else {
	        dateTimePicker.datePicker.zDrawTextFieldIndicators();
	        dateTimePicker.timePicker.zDrawTextFieldIndicators();
	      }
	    }
	    if (!isSelected) {
	      if (matchTableBackgroundColor) {
	        Color tableBackground = table.getBackground();
	        dateTimePicker.setBackground(tableBackground);
	        dateTimePicker.datePicker.setBackground(tableBackground);
	        dateTimePicker.timePicker.setBackground(tableBackground);
	        dateTimePicker.datePicker.getComponentDateTextField().setBackground(tableBackground);
	        dateTimePicker.timePicker.getComponentTimeTextField().setBackground(tableBackground);
	      } else {
	        dateTimePicker.datePicker.zDrawTextFieldIndicators();
	        dateTimePicker.timePicker.zDrawTextFieldIndicators();
	      }
	    }
	    // Draw the appropriate borders to indicate a focused or unfocused state.
	    if (hasFocus) {
	      dateTimePicker.setBorder(borderFocusedCell);
	    } else {
	      dateTimePicker.setBorder(borderUnfocusedCell);
	    }
	    // If needed, adjust the minimum row height for the table.
	    zAdjustTableRowHeightIfNeeded(table);
	    // This fixes a bug where the picker text could "move around" during a table resize event.
	    dateTimePicker.datePicker.getComponentDateTextField().setScrollOffset(0);
	    dateTimePicker.timePicker.getComponentTimeTextField().setScrollOffset(0);
	    // Return the DateTimePicker component.
	    return dateTimePicker;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		// TODO Auto-generated method stub
		// Save the supplied value to the DateTimePicker.
	    setCellEditorValue(value);
	    // If needed, adjust the minimum row height for the table.
	    zAdjustTableRowHeightIfNeeded(table);
	    // This fixes a bug where the picker text could "move around" during a table resize event.
	    dateTimePicker.datePicker.getComponentDateTextField().setScrollOffset(0);
	    dateTimePicker.timePicker.getComponentTimeTextField().setScrollOffset(0);
	    // Return the DateTimepicker component.
	    return dateTimePicker;
	}

}
