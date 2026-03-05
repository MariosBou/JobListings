import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TablePanelWrapper {
    private JPanel panel;
    private JTable table;
    private DefaultTableModel model;

    public TablePanelWrapper(JPanel panel, JTable table, DefaultTableModel model) {
        this.panel = panel;
        this.table = table;
        this.model = model;
    }

    public JPanel getPanel() {
        return panel;
    }

    public JTable getTable() {
        return table;
    }

    public DefaultTableModel getModel() {
        return model;
    }

    public void setModel(DefaultTableModel model) {
        this.model = model;
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    public void setTable(JTable table) {
        this.table = table;
    }
}
