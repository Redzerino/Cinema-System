// Name: Sarah Crotty
// Date: 04/04/2026
// Purpose: GUI panel for managing seat reservations

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SeatReservePanel
{
    private SeatReserveDAO seatReserveDAO = new SeatReserveDAO();
    private JTable seatReserveTable;

    // Creates and returns the seat reserve panel UI
    public JPanel createPanel()
    {
        JPanel panel = new JPanel(new BorderLayout());

        // Info label to notify user about the 15-minute auto-expiry behaviour
        JLabel autoRefreshLabel = new JLabel("Auto-expires reservations after 15 mins", SwingConstants.CENTER);
        autoRefreshLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        autoRefreshLabel.setForeground(Color.GRAY);
        panel.add(autoRefreshLabel, BorderLayout.NORTH);

        // Table setup for displaying seat reservations
        String[] columns = {"ID", "Showtime ID", "Seat ID", "User ID", "Guest Session", "Reserve At", "Expire At", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        seatReserveTable = new JTable(model);
        GUIHelper.styleTable(seatReserveTable);
        JScrollPane scrollPane = new JScrollPane(seatReserveTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Input fields for reservation details
        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel showtimeLabel = new JLabel("Showtime ID:");
        JTextField showtimeField = new JTextField();
        JLabel seatLabel = new JLabel("Seat ID:");
        JTextField seatField = new JTextField();
        JLabel userLabel = new JLabel("User ID (leave blank for guest):");
        JTextField userField = new JTextField();
        JLabel sessionLabel = new JLabel("Guest Session ID:");
        JTextField sessionField = new JTextField();
        JLabel statusLabel = new JLabel("Status:");
        JComboBox<String> statusBox = new JComboBox<>(new String[]{"Active", "Expired", "Completed"});

        inputPanel.add(showtimeLabel); inputPanel.add(showtimeField);
        inputPanel.add(seatLabel); inputPanel.add(seatField);
        inputPanel.add(userLabel); inputPanel.add(userField);
        inputPanel.add(sessionLabel); inputPanel.add(sessionField);
        inputPanel.add(statusLabel); inputPanel.add(statusBox);

        // Buttons for reservation actions
        JButton addButton = GUIHelper.createButton("Add Reservation", GUIHelper.addGreen);
        JButton updateButton = GUIHelper.createButton("Update Reservation", GUIHelper.updateBlue);
        JButton deleteButton = GUIHelper.createButton("Delete Reservation", GUIHelper.deleteRed);
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

        // Load initial reservation data into table
        loadSeatReserves(model);

        // Table click event to populate input fields
        seatReserveTable.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                int row = seatReserveTable.getSelectedRow();
                showtimeField.setText(model.getValueAt(row, 1).toString());
                seatField.setText(model.getValueAt(row, 2).toString());
                // Handle null user ID for guest reservations
                userField.setText(model.getValueAt(row, 3) != null ? model.getValueAt(row, 3).toString() : "");
                sessionField.setText(model.getValueAt(row, 4) != null ? model.getValueAt(row, 4).toString() : "");
                statusBox.setSelectedItem(model.getValueAt(row, 7).toString());
            }
        });

        // Add reservation action
        addButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    int showtimeID = Integer.parseInt(showtimeField.getText().trim());
                    int seatID = Integer.parseInt(seatField.getText().trim());

                    // Default userID to 0 if field is blank (guest reservation)
                    int userID = 0;
                    if(!userField.getText().trim().isEmpty())
                    {
                        userID = Integer.parseInt(userField.getText().trim());
                    }
                    String session = sessionField.getText().trim();
                    String status = statusBox.getSelectedItem().toString();

                    if(!GUIHelper.showtimeExists(showtimeID))
                    {
                        JOptionPane.showMessageDialog(panel, "Showtime ID " + showtimeID + " does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if(!GUIHelper.seatExists(seatID))
                    {
                        JOptionPane.showMessageDialog(panel, "Seat ID " + seatID + " does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Only validate user ID if one was provided
                    if(userID != 0 && !GUIHelper.userExists(userID))
                    {
                        JOptionPane.showMessageDialog(panel, "User ID " + userID + " does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    seatReserveDAO.addReservation(showtimeID, seatID, userID, session, status);
                    loadSeatReserves(model);
                    showtimeField.setText(""); seatField.setText("");
                    userField.setText(""); sessionField.setText("");
                    JOptionPane.showMessageDialog(panel, "Reservation added! Expires in 15 minutes.", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
                catch(NumberFormatException ex)
                {
                    JOptionPane.showMessageDialog(panel, "IDs must be numbers!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Update reservation action (status only)
        updateButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                int row = seatReserveTable.getSelectedRow();
                if(row == -1)
                {
                    JOptionPane.showMessageDialog(panel, "Please select a reservation to update!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Check if the status has actually changed
                if(statusBox.getSelectedItem().toString().equals(model.getValueAt(row, 7).toString()))
                {
                    JOptionPane.showMessageDialog(panel, "No changes were made!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int id = Integer.parseInt(model.getValueAt(row, 0).toString());
                String status = statusBox.getSelectedItem().toString();
                seatReserveDAO.updateReservation(id, status, model.getValueAt(row, 6).toString());
                loadSeatReserves(model);
                JOptionPane.showMessageDialog(panel, "Reservation updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Delete reservation action (permanent delete)
        deleteButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                int row = seatReserveTable.getSelectedRow();
                if(row == -1)
                {
                    JOptionPane.showMessageDialog(panel, "Please select a reservation to delete!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int confirm = JOptionPane.showConfirmDialog(panel, "Are you sure?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if(confirm == JOptionPane.YES_OPTION)
                {
                    int id = Integer.parseInt(model.getValueAt(row, 0).toString());
                    seatReserveDAO.deleteReservation(id);
                    loadSeatReserves(model);
                    JOptionPane.showMessageDialog(panel, "Reservation deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // Refresh table data
        refreshButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                loadSeatReserves(model);
            }
        });

        return panel;
    }

    // Loads seat reservation data into the table
    private void loadSeatReserves(DefaultTableModel model)
    {
        model.setRowCount(0);
        ArrayList<String[]> reserves = seatReserveDAO.getAllReservations();
        for(String[] r : reserves)
        {
            model.addRow(r);
        }
    }
}
