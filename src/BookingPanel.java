// Name: Sarah Crotty
// Date: 27/03/2026
// Purpose: GUI panel for managing bookings

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class BookingPanel
{
    private BookingDAO bookingDAO = new BookingDAO();
    private JTable bookingTable;

    // Creates and returns the booking panel UI
    public JPanel createPanel()
    {
        JPanel panel = new JPanel(new BorderLayout());

        // Table setup for displaying bookings
        String[] columns = {"ID", "Reference", "Guest Name", "Guest Email", "Movie", "Start Time", "Total", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        bookingTable = new JTable(model);
        GUIHelper.styleTable(bookingTable);
        JScrollPane scrollPane = new JScrollPane(bookingTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Input fields for booking details
        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel refLabel = new JLabel("Booking Reference:");
        JTextField refField = new JTextField();
        refField.setEditable(false);
        refField.setBackground(new Color(220, 220, 220));
        JLabel nameLabel = new JLabel("Guest Name:");
        JTextField nameField = new JTextField();
        JLabel emailLabel = new JLabel("Guest Email:");
        JTextField emailField = new JTextField();
        JLabel showtimeLabel = new JLabel("Showtime ID:");
        JTextField showtimeField = new JTextField();
        JLabel totalLabel = new JLabel("Total Amount:");
        JTextField totalField = new JTextField();
        JLabel statusLabel = new JLabel("Status:");
        JComboBox<String> statusBox = new JComboBox<>(new String[]{"Pending", "Confirmed", "Cancelled"});

        inputPanel.add(refLabel); inputPanel.add(refField);
        inputPanel.add(nameLabel); inputPanel.add(nameField);
        inputPanel.add(emailLabel); inputPanel.add(emailField);
        inputPanel.add(showtimeLabel); inputPanel.add(showtimeField);
        inputPanel.add(totalLabel); inputPanel.add(totalField);
        inputPanel.add(statusLabel); inputPanel.add(statusBox);

        // Buttons for booking actions
        JButton addButton = GUIHelper.createButton("Add Booking", GUIHelper.addGreen);
        JButton updateButton = GUIHelper.createButton("Update Booking", GUIHelper.updateBlue);
        JButton cancelButton = GUIHelper.createButton("Cancel Booking", GUIHelper.deleteRed);
        JButton refreshButton = GUIHelper.createButton("Refresh", GUIHelper.refreshGray);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(refreshButton);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(inputPanel, BorderLayout.CENTER);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);
        panel.add(southPanel, BorderLayout.SOUTH);

        // Load initial booking data into table
        loadBookings(model);

        // Table click event to populate input fields
        bookingTable.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                int row = bookingTable.getSelectedRow();
                refField.setText(model.getValueAt(row, 1).toString());
                nameField.setText(model.getValueAt(row, 2).toString());
                emailField.setText(model.getValueAt(row, 3).toString());
                totalField.setText(model.getValueAt(row, 6).toString());
                statusBox.setSelectedItem(model.getValueAt(row, 7).toString());
            }
        });

        // Add booking action
        addButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    String ref = refField.getText().trim();
                    String name = nameField.getText().trim();
                    String email = emailField.getText().trim();
                    int showtimeID = Integer.parseInt(showtimeField.getText().trim());
                    double total = Double.parseDouble(totalField.getText().trim());
                    String status = statusBox.getSelectedItem().toString();

                    if(name.isEmpty() || email.isEmpty())
                    {
                        JOptionPane.showMessageDialog(panel, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if(!email.contains("@") || !email.contains("."))
                    {
                        JOptionPane.showMessageDialog(panel, "Please enter a valid email address!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if(total <= 0)
                    {
                        JOptionPane.showMessageDialog(panel, "Total amount must be greater than 0!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if(!GUIHelper.showtimeExists(showtimeID))
                    {
                        JOptionPane.showMessageDialog(panel, "Showtime ID " + showtimeID + " does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if(GUIHelper.duplicateBookingReference(ref))
                    {
                        JOptionPane.showMessageDialog(panel, "Booking reference " + ref + " already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    bookingDAO.addBooking(ref, email, name, showtimeID, total, status);
                    loadBookings(model);
                    nameField.setText(""); emailField.setText("");
                    showtimeField.setText(""); totalField.setText("");
                    JOptionPane.showMessageDialog(panel, "Booking added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
                catch(NumberFormatException ex)
                {
                    JOptionPane.showMessageDialog(panel, "Showtime ID must be a number and Total must be a decimal!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Update booking action
        updateButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                int row = bookingTable.getSelectedRow();
                if(row == -1)
                {
                    JOptionPane.showMessageDialog(panel, "Please select a booking to update!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(nameField.getText().trim().equals(model.getValueAt(row, 2).toString()) &&
                    emailField.getText().trim().equals(model.getValueAt(row, 3).toString()) &&
                    totalField.getText().trim().equals(model.getValueAt(row, 6).toString()) &&
                    statusBox.getSelectedItem().toString().equals(model.getValueAt(row, 7).toString()))
                {
                    JOptionPane.showMessageDialog(panel, "No changes were made!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try
                {
                    int id = Integer.parseInt(model.getValueAt(row, 0).toString());
                    String name = nameField.getText().trim();
                    String email = emailField.getText().trim();
                    double total = Double.parseDouble(totalField.getText().trim());
                    String status = statusBox.getSelectedItem().toString();

                    if(name.isEmpty() || email.isEmpty())
                    {
                        JOptionPane.showMessageDialog(panel, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if(!email.contains("@") || !email.contains("."))
                    {
                        JOptionPane.showMessageDialog(panel, "Please enter a valid email address!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if(total <= 0)
                    {
                        JOptionPane.showMessageDialog(panel, "Total amount must be greater than 0!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    bookingDAO.updateBooking(id, name, email, total, status);
                    loadBookings(model);
                    JOptionPane.showMessageDialog(panel, "Booking updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
                catch(NumberFormatException ex)
                {
                    JOptionPane.showMessageDialog(panel, "Total must be a decimal number!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Cancel booking action (sets status to cancelled)
        cancelButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                int row = bookingTable.getSelectedRow();
                if(row == -1)
                {
                    JOptionPane.showMessageDialog(panel, "Please select a booking to cancel!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int confirm = JOptionPane.showConfirmDialog(panel, "Are you sure you want to cancel this booking?", "Confirm Cancel", JOptionPane.YES_NO_OPTION);
                if(confirm == JOptionPane.YES_OPTION)
                {
                    int id = Integer.parseInt(model.getValueAt(row, 0).toString());
                    bookingDAO.deleteBooking(id);
                    loadBookings(model);
                    JOptionPane.showMessageDialog(panel, "Booking cancelled successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // Refresh table data
        refreshButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                loadBookings(model);
            }
        });

        return panel;
    }

    // Loads booking data into the table
    private void loadBookings(DefaultTableModel model)
    {
        model.setRowCount(0);
        ArrayList<String[]> bookings = bookingDAO.getAllBookings();
        for(String[] b : bookings)
        {
            model.addRow(b);
        }
    }
}