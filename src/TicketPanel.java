// Name: Sarah Crotty
// Date: 25/03/2026
// Purpose: GUI panel for managing tickets

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class TicketPanel
{
    private TicketDAO ticketDAO = new TicketDAO();
    private JTable ticketTable;

    // Creates and returns the ticket panel UI
    public JPanel createPanel()
    {
        JPanel panel = new JPanel(new BorderLayout());

        // Table setup for displaying tickets
        String[] columns = {"ID", "Seat Booking ID", "QR Code", "Issued At"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        ticketTable = new JTable(model);
        GUIHelper.styleTable(ticketTable);
        JScrollPane scrollPane = new JScrollPane(ticketTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Input fields for ticket details
        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel seatBookingLabel = new JLabel("Seat Booking ID:");
        JTextField seatBookingField = new JTextField();
        JLabel qrLabel = new JLabel("QR Code:");
        JTextField qrField = new JTextField();

        inputPanel.add(seatBookingLabel); inputPanel.add(seatBookingField);
        inputPanel.add(qrLabel); inputPanel.add(qrField);

        // Buttons for ticket actions
        JButton addButton = GUIHelper.createButton("Add Ticket", GUIHelper.addGreen);
        JButton updateButton = GUIHelper.createButton("Update Ticket", GUIHelper.updateBlue);
        JButton deleteButton = GUIHelper.createButton("Delete Ticket", GUIHelper.deleteRed);
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

        // Load initial ticket data into table
        loadTickets(model);

        // Table click event to populate input fields
        ticketTable.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                int row = ticketTable.getSelectedRow();
                seatBookingField.setText(model.getValueAt(row, 1).toString());
                qrField.setText(model.getValueAt(row, 2).toString());
            }
        });

        // Add ticket action
        addButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    int seatBookingID = Integer.parseInt(seatBookingField.getText().trim());
                    String qrCode = qrField.getText().trim();

                    if(qrCode.isEmpty())
                    {
                        JOptionPane.showMessageDialog(panel, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Validate seat booking exists before adding
                    if(!GUIHelper.seatBookingExists(seatBookingID))
                    {
                        JOptionPane.showMessageDialog(panel, "Seat Booking ID " + seatBookingID + " does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Prevent duplicate tickets for the same seat booking
                    if(GUIHelper.duplicateTicket(seatBookingID))
                    {
                        JOptionPane.showMessageDialog(panel, "A ticket for seat booking " + seatBookingID + " already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    ticketDAO.addTicket(seatBookingID, qrCode);
                    loadTickets(model);
                    seatBookingField.setText(""); qrField.setText("");
                    JOptionPane.showMessageDialog(panel, "Ticket added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
                catch(NumberFormatException ex)
                {
                    JOptionPane.showMessageDialog(panel, "Seat Booking ID must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Update ticket action (QR code only)
        updateButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                int row = ticketTable.getSelectedRow();
                if(row == -1)
                {
                    JOptionPane.showMessageDialog(panel, "Please select a ticket to update!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Check if the QR code has actually changed
                if(qrField.getText().trim().equals(model.getValueAt(row, 2).toString()))
                {
                    JOptionPane.showMessageDialog(panel, "No changes were made!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String qrCode = qrField.getText().trim();
                if(qrCode.isEmpty())
                {
                    JOptionPane.showMessageDialog(panel, "QR Code cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int id = Integer.parseInt(model.getValueAt(row, 0).toString());
                ticketDAO.updateTicket(id, qrCode);
                loadTickets(model);
                JOptionPane.showMessageDialog(panel, "Ticket updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Delete ticket action (permanent delete)
        deleteButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                int row = ticketTable.getSelectedRow();
                if(row == -1)
                {
                    JOptionPane.showMessageDialog(panel, "Please select a ticket to delete!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int confirm = JOptionPane.showConfirmDialog(panel, "Are you sure?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if(confirm == JOptionPane.YES_OPTION)
                {
                    int id = Integer.parseInt(model.getValueAt(row, 0).toString());
                    ticketDAO.deleteTicket(id);
                    loadTickets(model);
                    JOptionPane.showMessageDialog(panel, "Ticket deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // Refresh table data
        refreshButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                loadTickets(model);
            }
        });

        return panel;
    }

    // Loads ticket data into the table
    private void loadTickets(DefaultTableModel model)
    {
        model.setRowCount(0);
        ArrayList<String[]> tickets = ticketDAO.getAllTickets();
        for(String[] t : tickets)
        {
            model.addRow(t);
        }
    }
}
