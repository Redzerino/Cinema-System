// Name: Sarah Crotty
// Date: 01/04/2026
// Purpose: GUI panel for managing seats

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SeatPanel
{
    private SeatDAO seatDAO = new SeatDAO();
    private JTable seatTable;

    // Creates and returns the seat panel UI
    public JPanel createPanel()
    {
        JPanel panel = new JPanel(new BorderLayout());

        // Table setup for displaying seats
        String[] columns = {"ID", "Screen ID", "Row", "Number", "Type", "Working"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        seatTable = new JTable(model);
        GUIHelper.styleTable(seatTable);
        JScrollPane scrollPane = new JScrollPane(seatTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Input fields for seat details
        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel screenLabel = new JLabel("Screen ID:");
        JTextField screenField = new JTextField();
        JLabel rowLabel = new JLabel("Seat Row:");
        JTextField rowField = new JTextField();
        JLabel numberLabel = new JLabel("Seat Number:");
        JTextField numberField = new JTextField();
        JLabel typeLabel = new JLabel("Seat Type:");
        JComboBox<String> typeBox = new JComboBox<>(new String[]{"Standard", "VIP"});
        JLabel workingLabel = new JLabel("Is Working:");
        JComboBox<String> workingBox = new JComboBox<>(new String[]{"true", "false"});

        inputPanel.add(screenLabel); inputPanel.add(screenField);
        inputPanel.add(rowLabel); inputPanel.add(rowField);
        inputPanel.add(numberLabel); inputPanel.add(numberField);
        inputPanel.add(typeLabel); inputPanel.add(typeBox);
        inputPanel.add(workingLabel); inputPanel.add(workingBox);

        // Buttons for seat actions
        JButton addButton = GUIHelper.createButton("Add Seat", GUIHelper.addGreen);
        JButton updateButton = GUIHelper.createButton("Update Seat", GUIHelper.updateBlue);
        JButton deleteButton = GUIHelper.createButton("Delete Seat", GUIHelper.deleteRed);
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

        // Load initial seat data into table
        loadSeats(model);

        // Table click event to populate input fields
        seatTable.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                int row = seatTable.getSelectedRow();
                screenField.setText(model.getValueAt(row, 1).toString());
                rowField.setText(model.getValueAt(row, 2).toString());
                numberField.setText(model.getValueAt(row, 3).toString());
                typeBox.setSelectedItem(model.getValueAt(row, 4).toString());
                workingBox.setSelectedItem(model.getValueAt(row, 5).toString());
            }
        });

        // Add seat action
        addButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    int screenID = Integer.parseInt(screenField.getText().trim());
                    String seatRow = rowField.getText().trim();
                    int seatNumber = Integer.parseInt(numberField.getText().trim());
                    String type = typeBox.getSelectedItem().toString();
                    boolean isWorking = Boolean.parseBoolean(workingBox.getSelectedItem().toString());

                    if(seatRow.isEmpty())
                    {
                        JOptionPane.showMessageDialog(panel, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Row must be a single letter e.g. A, B, C
                    if(seatRow.length() != 1 || !Character.isLetter(seatRow.charAt(0)))
                    {
                        JOptionPane.showMessageDialog(panel, "Seat row must be a single letter (e.g. A, B, C)!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if(!GUIHelper.screenExists(screenID))
                    {
                        JOptionPane.showMessageDialog(panel, "Screen ID " + screenID + " does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Prevent duplicate seat in the same screen
                    if(GUIHelper.duplicateSeat(screenID, seatRow, seatNumber))
                    {
                        JOptionPane.showMessageDialog(panel, "Seat " + seatRow + seatNumber + " already exists in this screen!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    seatDAO.addSeat(screenID, seatRow, seatNumber, type, isWorking);
                    loadSeats(model);
                    screenField.setText(""); rowField.setText(""); numberField.setText("");
                    JOptionPane.showMessageDialog(panel, "Seat added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
                catch(NumberFormatException ex)
                {
                    JOptionPane.showMessageDialog(panel, "Screen ID and Seat Number must be numbers!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Update seat action
        updateButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                int row = seatTable.getSelectedRow();
                if(row == -1)
                {
                    JOptionPane.showMessageDialog(panel, "Please select a seat to update!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Check if any values have actually changed
                if(screenField.getText().trim().equals(model.getValueAt(row, 1).toString()) &&
                    rowField.getText().trim().equals(model.getValueAt(row, 2).toString()) &&
                    numberField.getText().trim().equals(model.getValueAt(row, 3).toString()) &&
                    typeBox.getSelectedItem().toString().equals(model.getValueAt(row, 4).toString()) &&
                    workingBox.getSelectedItem().toString().equals(model.getValueAt(row, 5).toString()))
                    {
                        JOptionPane.showMessageDialog(panel, "No changes were made!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                try
                {
                    int id = Integer.parseInt(model.getValueAt(row, 0).toString());
                    int screenID = Integer.parseInt(screenField.getText().trim());
                    String seatRow = rowField.getText().trim();
                    int seatNumber = Integer.parseInt(numberField.getText().trim());
                    String type = typeBox.getSelectedItem().toString();
                    boolean isWorking = Boolean.parseBoolean(workingBox.getSelectedItem().toString());

                    if(seatRow.isEmpty())
                    {
                        JOptionPane.showMessageDialog(panel, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Row must be a single letter e.g. A, B, C
                    if(seatRow.length() != 1 || !Character.isLetter(seatRow.charAt(0)))
                    {
                        JOptionPane.showMessageDialog(panel, "Seat row must be a single letter (e.g. A, B, C)!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if(!GUIHelper.screenExists(screenID))
                    {
                        JOptionPane.showMessageDialog(panel, "Screen ID " + screenID + " does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    seatDAO.updateSeat(id, screenID, seatRow, seatNumber, type, isWorking);
                    loadSeats(model);
                    JOptionPane.showMessageDialog(panel, "Seat updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
                catch(NumberFormatException ex)
                {
                    JOptionPane.showMessageDialog(panel, "Screen ID and Seat Number must be numbers!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Delete seat action (soft delete - sets isWorking to false)
        deleteButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                int row = seatTable.getSelectedRow();
                if(row == -1)
                {
                    JOptionPane.showMessageDialog(panel, "Please select a seat to delete!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int confirm = JOptionPane.showConfirmDialog(panel, "Are you sure?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if(confirm == JOptionPane.YES_OPTION)
                {
                    int id = Integer.parseInt(model.getValueAt(row, 0).toString());
                    seatDAO.deleteSeat(id);
                    loadSeats(model);
                    JOptionPane.showMessageDialog(panel, "Seat deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // Refresh table data
        refreshButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                loadSeats(model);
            }
        });

        return panel;
    }

    // Loads seat data into the table
    private void loadSeats(DefaultTableModel model)
    {
        model.setRowCount(0);
        ArrayList<String[]> seats = seatDAO.getAllSeats();
        for(String[] s : seats)
        {
            model.addRow(s);
        }
    }
}
