// Name: Sarah Crotty
// Date: 27/03/2026
// Purpose: GUI panel for managing cinema screens

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ScreenPanel
{
    private ScreenDAO screenDAO = new ScreenDAO();
    private JTable screenTable;

    // Creates and returns the screen panel UI
    public JPanel createPanel()
    {
        JPanel panel = new JPanel(new BorderLayout());

        // Table setup for displaying screens
        String[] columns = {"ID", "Screen Type", "Capacity"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        screenTable = new JTable(model);
        GUIHelper.styleTable(screenTable);
        JScrollPane scrollPane = new JScrollPane(screenTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Input fields for screen details
        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel typeLabel = new JLabel("Screen Type:");
        JComboBox<String> typeBox = new JComboBox<>(new String[]{"Standard", "IMAX", "4DX", "VIP"});
        JLabel capacityLabel = new JLabel("Capacity:");
        JTextField capacityField = new JTextField();

        inputPanel.add(typeLabel); inputPanel.add(typeBox);
        inputPanel.add(capacityLabel); inputPanel.add(capacityField);

        // Buttons for screen actions
        JButton addButton = GUIHelper.createButton("Add Screen", GUIHelper.addGreen);
        JButton updateButton = GUIHelper.createButton("Update Screen", GUIHelper.updateBlue);
        JButton deleteButton = GUIHelper.createButton("Delete Screen", GUIHelper.deleteRed);
        JButton refreshButton = GUIHelper.createButton("Refresh", GUIHelper.refreshGray);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(inputPanel, BorderLayout.CENTER);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);
        panel.add(southPanel, BorderLayout.SOUTH);

        // Load initial screen data into table
        loadScreens(model);

        // Table click event to populate input fields
        screenTable.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                int row = screenTable.getSelectedRow();
                typeBox.setSelectedItem(model.getValueAt(row, 1).toString());
                capacityField.setText(model.getValueAt(row, 2).toString());
            }
        });

        // Add screen action
        addButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    String type = typeBox.getSelectedItem().toString();
                    int capacity = Integer.parseInt(capacityField.getText().trim());

                    // Capacity must be a positive number
                    if(capacity <= 0)
                    {
                        JOptionPane.showMessageDialog(panel, "Capacity must be greater than 0!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    screenDAO.addScreen(type, capacity);
                    loadScreens(model);
                    capacityField.setText("");
                    JOptionPane.showMessageDialog(panel, "Screen added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
                catch(NumberFormatException ex)
                {
                    JOptionPane.showMessageDialog(panel, "Capacity must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Update screen action
        updateButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                int row = screenTable.getSelectedRow();
                if(row == -1)
                {
                    JOptionPane.showMessageDialog(panel, "Please select a screen to update!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Check if any values have actually changed
                if(typeBox.getSelectedItem().toString().equals(model.getValueAt(row, 1).toString()) &&
                    capacityField.getText().trim().equals(model.getValueAt(row, 2).toString()))
                    {
                        JOptionPane.showMessageDialog(panel, "No changes were made!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                try
                {
                    int id = Integer.parseInt(model.getValueAt(row, 0).toString());
                    String type = typeBox.getSelectedItem().toString();
                    int capacity = Integer.parseInt(capacityField.getText().trim());

                    // Capacity must be a positive number
                    if(capacity <= 0)
                    {
                        JOptionPane.showMessageDialog(panel, "Capacity must be greater than 0!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    screenDAO.updateScreen(id, type, capacity);
                    loadScreens(model);
                    JOptionPane.showMessageDialog(panel, "Screen updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
                catch(NumberFormatException ex)
                {
                    JOptionPane.showMessageDialog(panel, "Capacity must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Delete screen action
        deleteButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                int row = screenTable.getSelectedRow();
                if(row == -1)
                {
                    JOptionPane.showMessageDialog(panel, "Please select a screen to delete!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int confirm = JOptionPane.showConfirmDialog(panel, "Are you sure?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if(confirm == JOptionPane.YES_OPTION)
                {
                    int id = Integer.parseInt(model.getValueAt(row, 0).toString());
                    screenDAO.deleteScreen(id);
                    loadScreens(model);
                    JOptionPane.showMessageDialog(panel, "Screen deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // Refresh table data
        refreshButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                loadScreens(model);
            }
        });

        return panel;
    }

    // Loads screen data into the table
    private void loadScreens(DefaultTableModel model)
    {
        model.setRowCount(0);
        ArrayList<String[]> screens = screenDAO.getAllScreens();
        for(String[] s : screens)
        {
            model.addRow(s);
        }
    }
}
