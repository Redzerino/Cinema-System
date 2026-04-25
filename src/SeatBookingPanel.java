// Name: Sarah Crotty
// Date: 29/03/2026
// Purpose: GUI panel for managing seat bookings

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SeatBookingPanel
{
    private SeatBookingDAO seatBookingDAO = new SeatBookingDAO();
    private JTable seatBookingTable;

    // Creates and returns the seat booking panel UI
    public JPanel createPanel()
    {
        JPanel panel = new JPanel(new BorderLayout());

        // Table setup for displaying seat bookings
        String[] columns = {"ID", "Booking ID", "Showtime ID", "Seat ID", "Price Paid"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        seatBookingTable = new JTable(model);
        GUIHelper.styleTable(seatBookingTable);
        JScrollPane scrollPane = new JScrollPane(seatBookingTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Input fields for seat booking details
        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel bookingLabel = new JLabel("Booking ID:");
        JTextField bookingField = new JTextField();
        JLabel showtimeLabel = new JLabel("Showtime ID:");
        JTextField showtimeField = new JTextField();
        JLabel seatLabel = new JLabel("Seat ID:");
        JTextField seatField = new JTextField();
        JLabel priceLabel = new JLabel("Price Paid:");
        JTextField priceField = new JTextField();

        inputPanel.add(bookingLabel); inputPanel.add(bookingField);
        inputPanel.add(showtimeLabel); inputPanel.add(showtimeField);
        inputPanel.add(seatLabel); inputPanel.add(seatField);
        inputPanel.add(priceLabel); inputPanel.add(priceField);

        // Buttons for seat booking actions
        JButton addButton = GUIHelper.createButton("Add Seat Booking", GUIHelper.addGreen);
        JButton updateButton = GUIHelper.createButton("Update Seat Booking", GUIHelper.updateBlue);
        JButton deleteButton = GUIHelper.createButton("Delete Seat Booking", GUIHelper.deleteRed);
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

        // Load initial seat booking data into table
        loadSeatBookings(model);

        // Table click event to populate input fields
        seatBookingTable.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                int row = seatBookingTable.getSelectedRow();
                bookingField.setText(model.getValueAt(row, 1).toString());
                showtimeField.setText(model.getValueAt(row, 2).toString());
                seatField.setText(model.getValueAt(row, 3).toString());
                priceField.setText(model.getValueAt(row, 4).toString());
            }
        });

        // Add seat booking action
        addButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    int bookingID = Integer.parseInt(bookingField.getText().trim());
                    int showtimeID = Integer.parseInt(showtimeField.getText().trim());
                    int seatID = Integer.parseInt(seatField.getText().trim());
                    double price = Double.parseDouble(priceField.getText().trim());

                    // Price must be a positive value
                    if(price <= 0)
                    {
                        JOptionPane.showMessageDialog(panel, "Price must be greater than 0!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if(!GUIHelper.bookingExists(bookingID))
                    {
                        JOptionPane.showMessageDialog(panel, "Booking ID " + bookingID + " does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
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

                    // Broken seats cannot be booked
                    if(GUIHelper.isSeatBroken(seatID))
                    {
                        JOptionPane.showMessageDialog(panel, "Seat ID " + seatID + " is broken and cannot be booked!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Prevent double-booking a seat for the same showtime
                    if(GUIHelper.isSeatAlreadyBooked(seatID, showtimeID))
                    {
                        JOptionPane.showMessageDialog(panel, "Seat ID " + seatID + " is already booked for this showtime!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    seatBookingDAO.addSeatBooking(bookingID, showtimeID, seatID, price);
                    loadSeatBookings(model);
                    bookingField.setText(""); showtimeField.setText("");
                    seatField.setText(""); priceField.setText("");
                    JOptionPane.showMessageDialog(panel, "Seat Booking added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
                catch(NumberFormatException ex)
                {
                    JOptionPane.showMessageDialog(panel, "IDs must be numbers and Price must be a decimal!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Update seat booking action
        updateButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                int row = seatBookingTable.getSelectedRow();
                if(row == -1)
                {
                    JOptionPane.showMessageDialog(panel, "Please select a seat booking to update!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Check if any values have actually changed
                if(bookingField.getText().trim().equals(model.getValueAt(row, 1).toString()) &&
                    showtimeField.getText().trim().equals(model.getValueAt(row, 2).toString()) &&
                    seatField.getText().trim().equals(model.getValueAt(row, 3).toString()) &&
                    priceField.getText().trim().equals(model.getValueAt(row, 4).toString()))
                    {
                        JOptionPane.showMessageDialog(panel, "No changes were made!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                try
                {
                    int id = Integer.parseInt(model.getValueAt(row, 0).toString());
                    int bookingID = Integer.parseInt(bookingField.getText().trim());
                    int showtimeID = Integer.parseInt(showtimeField.getText().trim());
                    int seatID = Integer.parseInt(seatField.getText().trim());
                    double price = Double.parseDouble(priceField.getText().trim());

                    // Price must be a positive value
                    if(price <= 0)
                    {
                        JOptionPane.showMessageDialog(panel, "Price must be greater than 0!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if(!GUIHelper.bookingExists(bookingID))
                    {
                        JOptionPane.showMessageDialog(panel, "Booking ID " + bookingID + " does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
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

                    // Broken seats cannot be booked
                    if(GUIHelper.isSeatBroken(seatID))
                    {
                        JOptionPane.showMessageDialog(panel, "Seat ID " + seatID + " is broken and cannot be booked!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Prevent double-booking a seat for the same showtime
                    if(GUIHelper.isSeatAlreadyBooked(seatID, showtimeID))
                    {
                        JOptionPane.showMessageDialog(panel, "Seat ID " + seatID + " is already booked for this showtime!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    seatBookingDAO.updateSeatBooking(id, bookingID, showtimeID, seatID, price);
                    loadSeatBookings(model);
                    JOptionPane.showMessageDialog(panel, "Seat Booking updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
                catch(NumberFormatException ex)
                {
                    JOptionPane.showMessageDialog(panel, "IDs must be numbers and Price must be a decimal!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Delete seat booking action (permanent delete)
        deleteButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                int row = seatBookingTable.getSelectedRow();
                if(row == -1)
                {
                    JOptionPane.showMessageDialog(panel, "Please select a seat booking to delete!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int confirm = JOptionPane.showConfirmDialog(panel, "Are you sure?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if(confirm == JOptionPane.YES_OPTION)
                {
                    int id = Integer.parseInt(model.getValueAt(row, 0).toString());
                    seatBookingDAO.deleteSeatBooking(id);
                    loadSeatBookings(model);
                    JOptionPane.showMessageDialog(panel, "Seat Booking deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // Refresh table data
        refreshButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                loadSeatBookings(model);
            }
        });

        return panel;
    }

    // Loads seat booking data into the table
    private void loadSeatBookings(DefaultTableModel model)
    {
        model.setRowCount(0);
        ArrayList<String[]> seatBookings = seatBookingDAO.getAllSeatBookings();
        for(String[] sb : seatBookings)
        {
            model.addRow(sb);
        }
    }
}
