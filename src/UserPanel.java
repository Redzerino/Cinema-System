// Name: Sarah Crotty
// Date: 24/03/2026
// Purpose: GUI panel for managing users

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class UserPanel
{
    private UserDAO userDAO = new UserDAO();
    private JTable userTable;

    // Creates and returns the user panel UI
    public JPanel createPanel()
    {
        JPanel panel = new JPanel(new BorderLayout());

        // Table setup for displaying users
        String[] columns = {"ID", "Email", "First Name", "Last Name", "Role", "Active"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        userTable = new JTable(model);
        GUIHelper.styleTable(userTable);
        JScrollPane scrollPane = new JScrollPane(userTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Input fields for user details
        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();
        JLabel firstLabel = new JLabel("First Name:");
        JTextField firstField = new JTextField();
        JLabel lastLabel = new JLabel("Last Name:");
        JTextField lastField = new JTextField();
        JLabel roleLabel = new JLabel("Role:");
        JComboBox<String> roleBox = new JComboBox<>(new String[]{"Customer", "Staff", "Admin"});
        JLabel activeLabel = new JLabel("Active:");
        JComboBox<String> activeBox = new JComboBox<>(new String[]{"true", "false"});

        inputPanel.add(emailLabel); inputPanel.add(emailField);
        inputPanel.add(passLabel); inputPanel.add(passField);
        inputPanel.add(firstLabel); inputPanel.add(firstField);
        inputPanel.add(lastLabel); inputPanel.add(lastField);
        inputPanel.add(roleLabel); inputPanel.add(roleBox);
        inputPanel.add(activeLabel); inputPanel.add(activeBox);

        // Buttons for user actions
        JButton addButton = GUIHelper.createButton("Add User", GUIHelper.addGreen);
        JButton updateButton = GUIHelper.createButton("Update User", GUIHelper.updateBlue);
        JButton deactivateButton = GUIHelper.createButton("Deactivate User", GUIHelper.deleteRed);
        JButton refreshButton = GUIHelper.createButton("Refresh", GUIHelper.refreshGray);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deactivateButton);
        buttonPanel.add(refreshButton);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(inputPanel, BorderLayout.CENTER);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);
        panel.add(southPanel, BorderLayout.SOUTH);

        // Load initial user data into table
        loadUsers(model);

        // Table click event to populate input fields
        userTable.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                int row = userTable.getSelectedRow();
                emailField.setText(model.getValueAt(row, 1).toString());
                firstField.setText(model.getValueAt(row, 2).toString());
                lastField.setText(model.getValueAt(row, 3).toString());
                roleBox.setSelectedItem(model.getValueAt(row, 4).toString());
                activeBox.setSelectedItem(model.getValueAt(row, 5).toString());
            }
        });

        // Add user action
        addButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                String email = emailField.getText().trim();
                String pass = new String(passField.getPassword()).trim();
                String first = firstField.getText().trim();
                String last = lastField.getText().trim();
                String role = roleBox.getSelectedItem().toString();
                boolean isActive = Boolean.parseBoolean(activeBox.getSelectedItem().toString());

                if(email.isEmpty() || pass.isEmpty() || first.isEmpty() || last.isEmpty())
                {
                    JOptionPane.showMessageDialog(panel, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Basic email format validation
                if(!email.contains("@") || !email.contains("."))
                {
                    JOptionPane.showMessageDialog(panel, "Please enter a valid email address!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Password length validation
                if(pass.length() < 6)
                {
                    JOptionPane.showMessageDialog(panel, "Password must be at least 6 characters!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Prevent duplicate email addresses
                if(GUIHelper.duplicateEmail(email))
                {
                    JOptionPane.showMessageDialog(panel, "A user with this email already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                userDAO.addUser(email, pass, first, last, role, isActive);
                loadUsers(model);
                emailField.setText(""); passField.setText("");
                firstField.setText(""); lastField.setText("");
                JOptionPane.showMessageDialog(panel, "User added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Update user action
        updateButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                int row = userTable.getSelectedRow();
                if(row == -1)
                {
                    JOptionPane.showMessageDialog(panel, "Please select a user to update!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Check if any values have actually changed
                if(emailField.getText().trim().equals(model.getValueAt(row, 1).toString()) &&
                    firstField.getText().trim().equals(model.getValueAt(row, 2).toString()) &&
                    lastField.getText().trim().equals(model.getValueAt(row, 3).toString()) &&
                    roleBox.getSelectedItem().toString().equals(model.getValueAt(row, 4).toString()) &&
                    activeBox.getSelectedItem().toString().equals(model.getValueAt(row, 5).toString()))
                    {
                        JOptionPane.showMessageDialog(panel, "No changes were made!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                String email = emailField.getText().trim();
                String first = firstField.getText().trim();
                String last = lastField.getText().trim();
                String role = roleBox.getSelectedItem().toString();
                boolean isActive = Boolean.parseBoolean(activeBox.getSelectedItem().toString());

                if(email.isEmpty() || first.isEmpty() || last.isEmpty())
                {
                    JOptionPane.showMessageDialog(panel, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Basic email format validation
                if(!email.contains("@") || !email.contains("."))
                {
                    JOptionPane.showMessageDialog(panel, "Please enter a valid email address!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int id = Integer.parseInt(model.getValueAt(row, 0).toString());
                userDAO.updateUser(id, email, first, last, role, isActive);
                loadUsers(model);
                JOptionPane.showMessageDialog(panel, "User updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Deactivate user action (soft delete - sets isActive to false)
        deactivateButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                int row = userTable.getSelectedRow();
                if(row == -1)
                {
                    JOptionPane.showMessageDialog(panel, "Please select a user to deactivate!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int confirm = JOptionPane.showConfirmDialog(panel, "Are you sure you want to deactivate this user?", "Confirm Deactivate", JOptionPane.YES_NO_OPTION);
                if(confirm == JOptionPane.YES_OPTION)
                {
                    int id = Integer.parseInt(model.getValueAt(row, 0).toString());
                    userDAO.deleteUser(id);
                    loadUsers(model);
                    JOptionPane.showMessageDialog(panel, "User deactivated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // Refresh table data
        refreshButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                loadUsers(model);
            }
        });

        return panel;
    }

    // Loads user data into the table
    private void loadUsers(DefaultTableModel model)
    {
        model.setRowCount(0);
        ArrayList<String[]> users = userDAO.getAllUsers();
        for(String[] u : users)
        {
            model.addRow(u);
        }
    }
}
