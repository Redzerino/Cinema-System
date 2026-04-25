// Name: Sarah Crotty
// Date: 29/03/2026
// Purpose: GUI panel for managing payments 

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class PaymentPanel
{
    private PaymentDAO paymentDAO = new PaymentDAO();
    private JTable paymentTable;

    // Creates and returns the payment management panel UI
    public JPanel createPanel()
    {
        JPanel panel = new JPanel(new BorderLayout());

        // Table setup for displaying payments
        String[] columns = {"ID", "Booking ID", "Amount", "Method", "Reference", "Status", "Paid At"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        paymentTable = new JTable(model);
        GUIHelper.styleTable(paymentTable);
        JScrollPane scrollPane = new JScrollPane(paymentTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Input fields for payment details
        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel bookingLabel = new JLabel("Booking ID:");
        JTextField bookingField = new JTextField();
        JLabel amountLabel = new JLabel("Amount:");
        JTextField amountField = new JTextField();
        JLabel methodLabel = new JLabel("Payment Method:");
        JComboBox<String> methodBox = new JComboBox<>(new String[]{"Credit Card", "Debit Card", "PayPal", "Cash"});
        JLabel refLabel = new JLabel("Reference:");
        JTextField refField = new JTextField();
        JLabel statusLabel = new JLabel("Status:");
        JComboBox<String> statusBox = new JComboBox<>(new String[]{"Pending", "Success", "Failed"});

        inputPanel.add(bookingLabel); inputPanel.add(bookingField);
        inputPanel.add(amountLabel); inputPanel.add(amountField);
        inputPanel.add(methodLabel); inputPanel.add(methodBox);
        inputPanel.add(refLabel); inputPanel.add(refField);
        inputPanel.add(statusLabel); inputPanel.add(statusBox);

        // Action buttons for CRUD operations
        JButton addButton = GUIHelper.createButton("Add Payment", GUIHelper.addGreen);
        JButton updateButton = GUIHelper.createButton("Update Payment", GUIHelper.updateBlue);
        JButton deleteButton = GUIHelper.createButton("Delete Payment", GUIHelper.deleteRed);
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

        // Load initial payment data
        loadPayments(model);

        // Populates fields when a payment row is selected
        paymentTable.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                int row = paymentTable.getSelectedRow();
                bookingField.setText(model.getValueAt(row, 1).toString());
                amountField.setText(model.getValueAt(row, 2).toString());
                methodBox.setSelectedItem(model.getValueAt(row, 3).toString());
                refField.setText(model.getValueAt(row, 4).toString());
                statusBox.setSelectedItem(model.getValueAt(row, 5).toString());
            }
        });

        // Adds a new payment record
        addButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    int bookingID = Integer.parseInt(bookingField.getText().trim());
                    double amount = Double.parseDouble(amountField.getText().trim());
                    String method = methodBox.getSelectedItem().toString();
                    String ref = refField.getText().trim();
                    String status = statusBox.getSelectedItem().toString();

                    if(ref.isEmpty())
                    {
                        JOptionPane.showMessageDialog(panel, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if(amount <= 0)
                    {
                        JOptionPane.showMessageDialog(panel, "Amount must be greater than 0!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    paymentDAO.addPayment(bookingID, amount, method, ref, status);
                    loadPayments(model);

                    bookingField.setText("");
                    amountField.setText("");
                    refField.setText("");

                    JOptionPane.showMessageDialog(panel, "Payment added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
                catch(NumberFormatException ex)
                {
                    JOptionPane.showMessageDialog(panel, "Booking ID must be a number and Amount must be a decimal!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Updates selected payment
        updateButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                int row = paymentTable.getSelectedRow();
                if(row == -1)
                {
                    JOptionPane.showMessageDialog(panel, "Please select a payment to update!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try
                {
                    int id = Integer.parseInt(model.getValueAt(row, 0).toString());
                    double amount = Double.parseDouble(amountField.getText().trim());
                    String method = methodBox.getSelectedItem().toString();
                    String ref = refField.getText().trim();
                    String status = statusBox.getSelectedItem().toString();

                    paymentDAO.updatePayment(id, amount, method, ref, status);
                    loadPayments(model);

                    JOptionPane.showMessageDialog(panel, "Payment updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
                catch(NumberFormatException ex)
                {
                    JOptionPane.showMessageDialog(panel, "Amount must be a decimal number!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Deletes selected payment
        deleteButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                int row = paymentTable.getSelectedRow();
                if(row == -1)
                {
                    JOptionPane.showMessageDialog(panel, "Please select a payment to delete!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(panel, "Are you sure?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if(confirm == JOptionPane.YES_OPTION)
                {
                    int id = Integer.parseInt(model.getValueAt(row, 0).toString());
                    paymentDAO.deletePayment(id);
                    loadPayments(model);

                    JOptionPane.showMessageDialog(panel, "Payment deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // Refresh table data
        refreshButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                loadPayments(model);
            }
        });

        return panel;
    }

    // Loads payments into table
    private void loadPayments(DefaultTableModel model)
    {
        model.setRowCount(0);
        ArrayList<String[]> payments = paymentDAO.getAllPayments();

        for(String[] p : payments)
        {
            model.addRow(p);
        }
    }
}