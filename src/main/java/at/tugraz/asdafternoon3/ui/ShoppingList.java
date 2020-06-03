package at.tugraz.asdafternoon3.ui;

import at.tugraz.asdafternoon3.FlatApplication;
import at.tugraz.asdafternoon3.businesslogic.FlatDAO;
import at.tugraz.asdafternoon3.businesslogic.ShoppingListItemDAO;
import at.tugraz.asdafternoon3.data.Flat;
import at.tugraz.asdafternoon3.data.ShoppingListItem;
import at.tugraz.asdafternoon3.database.DatabaseConnection;
import at.tugraz.asdafternoon3.ui.table.ShoppingListTableModel;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ShoppingList {
    private JPanel contentPane;
    private JTable shoppingTable;
    private JTextField tfItem;
    private JButton addButton;
    private JButton removeButton;
    private JCheckBox cbHide;
    private JButton backButton;
    private JLabel lItem;
    private JLabel lHeader;

    private final Flat activeFlat;
    private final ShoppingListTableModel tableModel;

    public ShoppingList(Flat flat) {
        initLocalizations();
        this.activeFlat = flat;

        List<ShoppingListItem> items = new ArrayList<>();
        try {
            items = DatabaseConnection.getInstance().createDao(FlatDAO.class).getShoppingList(flat);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getContentPane(), "Shopping List could not be found.", "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println(e);
        }

        tableModel = new ShoppingListTableModel(items);
        shoppingTable.setModel(tableModel);

        addButton.addActionListener(e -> {
            ShoppingListItem item = createItem();
            if (item != null) {
                tableModel.addItem(item);
            }
        });

        removeButton.addActionListener(e -> {
            int rowIndex = shoppingTable.getSelectedRow();
            ShoppingListItem item = tableModel.getItemAtIndex(rowIndex);

            try {
                ShoppingListItemDAO creator = DatabaseConnection.getInstance().createDao(ShoppingListItemDAO.class);
                creator.delete(item);

                tableModel.removeItem(rowIndex);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(contentPane, "Could not remove item");
            }
        });

        backButton.addActionListener(e -> {
            FlatApplication.get().setContentPane(new FlatOverview(activeFlat).getContentPane());
        });

        tfItem.addActionListener(e -> {
            ShoppingListItem item = createItem();
            if (item != null) {
                tableModel.addItem(item);
            }
        });

        cbHide.addActionListener(e -> {
            if (cbHide.isSelected()) {
                // Filter
                shoppingTable.setRowSorter(tableModel.getCompletedSorter());
            } else {
                // Remove filter
                shoppingTable.setRowSorter(null);
            }
        });
    }

    private void initLocalizations() {
        lHeader.setText(Localization.getInstance().getCurrent().getString("shoppinglist.header"));
        lItem.setText(Localization.getInstance().getCurrent().getString("shoppinglist.item"));
        cbHide.setText(Localization.getInstance().getCurrent().getString("shoppinglist.hidecompleted"));
        addButton.setText(Localization.getInstance().getCurrent().getString("shoppinglist.button.add"));
        removeButton.setText(Localization.getInstance().getCurrent().getString("shoppinglist.button.remove"));
        backButton.setText(Localization.getInstance().getCurrent().getString("frame.button.back"));
    }

    private ShoppingListItem createItem() {
        ShoppingListItem item = new ShoppingListItem(tfItem.getText(), activeFlat);
        ShoppingListItem result = null;

        try {
            ShoppingListItemDAO creator = DatabaseConnection.getInstance().createDao(ShoppingListItemDAO.class);

            if (!creator.validate(item)) {
                JOptionPane.showMessageDialog(contentPane, "Item data is not valid");
            } else {
                result = creator.create(item);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(contentPane, "Could not create item");
            System.err.println(e);
        }

        tfItem.setText("");
        return result;
    }

    public JPanel getContentPane() {
        return contentPane;
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.setBackground(new Color(-14078925));
        shoppingTable = new JTable();
        shoppingTable.setBackground(new Color(-14078925));
        shoppingTable.setForeground(new Color(-2103318));
        shoppingTable.setGridColor(new Color(-12816512));
        contentPane.add(shoppingTable, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBackground(new Color(-14078925));
        contentPane.add(panel1, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        tfItem = new JTextField();
        tfItem.setBackground(new Color(-12632257));
        tfItem.setForeground(new Color(-2103318));
        panel1.add(tfItem, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        addButton = new JButton();
        addButton.setBackground(new Color(-12816512));
        addButton.setForeground(new Color(-2103318));
        addButton.setText("Add");
        panel1.add(addButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        removeButton = new JButton();
        removeButton.setBackground(new Color(-12816512));
        removeButton.setForeground(new Color(-2103318));
        removeButton.setText("Remove");
        panel1.add(removeButton, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        lItem = new JLabel();
        lItem.setForeground(new Color(-4145152));
        lItem.setText("Item");
        panel1.add(lItem, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        panel2.setBackground(new Color(-14078925));
        contentPane.add(panel2, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        lHeader = new JLabel();
        Font lHeaderFont = this.$$$getFont$$$(null, -1, 22, lHeader.getFont());
        if (lHeaderFont != null) lHeader.setFont(lHeaderFont);
        lHeader.setForeground(new Color(-4145152));
        lHeader.setText("Shopping List");
        panel2.add(lHeader, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbHide = new JCheckBox();
        cbHide.setBackground(new Color(-14078925));
        cbHide.setForeground(new Color(-4145152));
        cbHide.setText("Hide Completed");
        panel2.add(cbHide, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        backButton = new JButton();
        backButton.setBackground(new Color(-12816512));
        backButton.setForeground(new Color(-2103318));
        backButton.setText("< Back");
        panel2.add(backButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel2.add(spacer1, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

}
