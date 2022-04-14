package viewmodel;

import java.awt.Color;
import java.awt.Component;
import java.time.LocalDate;

import javax.swing.AbstractCellEditor;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DatePickerSettings.DateArea;
import com.github.lgooddatepicker.zinternaltools.InternalUtilities;

@SuppressWarnings("serial")
public class DateTableEditor extends AbstractCellEditor
implements TableCellEditor, TableCellRenderer{

	/**
	 * autoAdjustMinimumTableRowHeight, This indicates whether the minimum table row height (for all
	 * rows) should be modified when an editor or render is displayed. The row height is only adjusted
	 * if it is below the minimum value needed to display the date picker component.
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

	/** datepicker, This holds the date picker instance. */
	private DatePicker datePicker;

	/** minimumRowHeight, This holds the minimum row height needed to display the date picker. */
	private int minimumRowHeightInPixels;

	/** Constructor, default. */
	public DateTableEditor() {
		this(true, true, true);
	}

	public DateTableEditor(
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
		// Create and set up the date picker.
		datePicker = new DatePicker();
//		Border emptyBorder = BorderFactory.createEmptyBorder();
//		datePicker.getComponentToggleCalendarButton().setBorder(emptyBorder);
		datePicker.setBorder(borderUnfocusedCell);
		datePicker.getComponentDateTextField().setBorder(null);
		// Adjust any needed date picker settings.
		DatePickerSettings settings = datePicker.getSettings();
		settings.setGapBeforeButtonPixels(0);
		settings.setSizeTextFieldMinimumWidthDefaultOverride(false);
		settings.setSizeTextFieldMinimumWidth(20);
		settings.setColor(DateArea.BackgroundMonthAndYearMenuLabels, Color.DARK_GRAY);
		settings.setColor(DateArea.BackgroundTodayLabel, Color.DARK_GRAY);
		settings.setColor(DateArea.BackgroundClearLabel, Color.DARK_GRAY);
		settings.setColor(DateArea.BackgroundCalendarPanelLabelsOnHover, Color.ORANGE);
		settings.setColor(DateArea.DatePickerTextValidDate, Color.ORANGE);
		// Calculate and store the minimum row height needed to display the date picker.
		minimumRowHeightInPixels = (datePicker.getPreferredSize().height + 1);
	}

	@Override
	public Object getCellEditorValue() {
		// TODO Auto-generated method stub
		return datePicker.getDate();
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		// Save the supplied value to the date picker.
		setCellEditorValue(value);
		// Draw the appropriate background colors to indicate a selected or unselected state.
		if (isSelected) {
			if (matchTableSelectionBackgroundColor) {
				datePicker.getComponentDateTextField().setBackground(table.getSelectionBackground());
				datePicker.setBackground(table.getSelectionBackground());
			} else {
				datePicker.zDrawTextFieldIndicators();
			}
		}
		if (!isSelected) {
			if (matchTableBackgroundColor) {
				datePicker.getComponentDateTextField().setBackground(table.getBackground());
				datePicker.setBackground(table.getBackground());
			} else {
				datePicker.zDrawTextFieldIndicators();
			}
		}
		// Draw the appropriate borders to indicate a focused or unfocused state.
		if (hasFocus) {
			datePicker.setBorder(borderFocusedCell);
		} else {
			datePicker.setBorder(borderUnfocusedCell);
		}
		// If needed, adjust the minimum row height for the table.
		zAdjustTableRowHeightIfNeeded(table);
		// This fixes a bug where the date text could "move around" during a table resize event.
		datePicker.getComponentDateTextField().setScrollOffset(0);
		// Return the date picker component.
		return datePicker;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		// Save the supplied value to the date picker.
		setCellEditorValue(value);
		// If needed, adjust the minimum row height for the table.
		zAdjustTableRowHeightIfNeeded(table);
		// This fixes a bug where the date text could "move around" during a table resize event.
		datePicker.getComponentDateTextField().setScrollOffset(0);
		// Return the date picker component.
		return datePicker;
	}

	public void setCellEditorValue(Object value) {
		datePicker.clear();
		if (value == null) {
			return;
		}
		if (value instanceof LocalDate) {
			LocalDate nativeValue = (LocalDate) value;
			datePicker.setDate(nativeValue);
		} else {
			String text = value.toString();
			String shorterText = InternalUtilities.safeSubstring(text, 0, 100);
			datePicker.setText(shorterText);
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

}
