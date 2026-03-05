import com.mysql.cj.xdevapi.JsonParser;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class IntegerOnly extends JTextField {

    public IntegerOnly() {
        super();
    }

    public boolean CheckLong() {
        try {
            Long.parseLong(super.getText());
            return true;
        } catch (NumberFormatException e) {
            super.setBorder(new LineBorder(Color.RED, 3));
            return false;
        }
    }

    public boolean CheckInteger() {
        try {
            Integer.parseInt(super.getText());
            return true;
        } catch (NumberFormatException e) {
            super.setBorder(new LineBorder(Color.RED, 3));
            return false;
        }
    }

    public boolean CheckDouble() {
        try {
            Double.parseDouble(super.getText());
            return true;
        } catch (NumberFormatException e) {
            super.setBorder(new LineBorder(Color.RED, 3));
            return false;
        }
    }

    public boolean CheckDate() {
        String dateStr = super.getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            // Parse the date string
            LocalDate date = LocalDate.parse(dateStr, formatter);

            // Split the date string to get day, month, and year
            String[] parts = dateStr.split("-");
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int day = Integer.parseInt(parts[2]);

            // Check year validity
            if (year < 2000) {
                super.setBorder(new LineBorder(Color.RED, 3));
                return false;
            }

            // Check month validity
            if (month < 1 || month > 12) {
                super.setBorder(new LineBorder(Color.RED, 3));
                return false;
            }

            // Check day validity based on month and year
            switch (month) {
                case 2: // February
                    if (day < 1 || day > 28) {
                        super.setBorder(new LineBorder(Color.RED, 3));
                        return false;
                    }
                    break;
                case 4: case 6: case 9: case 11: // April, June, September, November
                    if (day < 1 || day > 30) {
                        super.setBorder(new LineBorder(Color.RED, 3));
                        return false;
                    }
                    break;
                default: // All other months
                    if (day < 1 || day > 31) {
                        super.setBorder(new LineBorder(Color.RED, 3));
                        return false;
                    }
            }
            return true;
        } catch (DateTimeParseException | NumberFormatException e) {
            // If parsing fails, or number format is wrong, the date is invalid
            super.setBorder(new LineBorder(Color.RED, 3));
            return false;
        }
    }

}
