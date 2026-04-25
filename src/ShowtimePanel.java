// Name: Sarah Crotty
// Date: 05/04/2026
// Purpose: GUI panel for managing showtimes

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ShowtimePanel
{
    private ShowtimeDAO showtimeDAO = new ShowtimeDAO();
    private JTable showtimeTable;

    // Creates and returns the showtime panel UI
    public JPanel createPanel()
    {
        JPanel panel = new JPanel(new BorderLayout());

        // Table setup for displaying showtimes
        String[] columns = {"ID", "Movie ID", "Screen ID", "Start Time", "End Time", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        showtimeTable = new JTable(model);
        GUIHelper.styleTable(showtimeTable);
        JScrollPane scrollPane = new JScrollPane(showtimeTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Input fields for showtime details
        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel movieIDLabel = new JLabel("Movie ID:");
        JTextField movieIDField = new JTextField();
        JLabel screenIDLabel = new JLabel("Screen ID:");
        JTextField screenIDField = new JTextField();
        JLabel startLabel = new JLabel("Start Time (YYYY-MM-DD HH:MM:SS):");
        JTextField startField = new JTextField();
        JLabel endLabel = new JLabel("End Time (auto calculated):");
        JTextField endField = new JTextField();
        JLabel statusLabel = new JLabel("Status:");
        JComboBox<String> statusBox = new JComboBox<>(new String[]{"Scheduled", "Cancelled", "Completed"});

        // Button to auto-calculate end time based on movie duration
        JLabel calcLabel = new JLabel("");
        JButton calcButton = new JButton("Calculate End Time");
        calcButton.setBackground(new Color(13, 13, 43));
        calcButton.setForeground(Color.WHITE);
        calcButton.setFocusPainted(false);
        calcButton.setBorderPainted(false);

        inputPanel.add(movieIDLabel); inputPanel.add(movieIDField);
        inputPanel.add(screenIDLabel); inputPanel.add(screenIDField);
        inputPanel.add(startLabel); inputPanel.add(startField);
        inputPanel.add(endLabel); inputPanel.add(endField);
        inputPanel.add(statusLabel); inputPanel.add(statusBox);
        inputPanel.add(calcLabel); inputPanel.add(calcButton);

        // Buttons for showtime actions
        JButton addButton = GUIHelper.createButton("Add Showtime", GUIHelper.addGreen);
        JButton updateButton = GUIHelper.createButton("Update Showtime", GUIHelper.updateBlue);
        JButton deleteButton = GUIHelper.createButton("Delete Showtime", GUIHelper.deleteRed);
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

        // Load initial showtime data into table
        loadShowtimes(model);

        // Table click event to populate input fields
        showtimeTable.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                int row = showtimeTable.getSelectedRow();
                movieIDField.setText(model.getValueAt(row, 1).toString());
                screenIDField.setText(model.getValueAt(row, 2).toString());
                startField.setText(model.getValueAt(row, 3).toString());
                endField.setText(model.getValueAt(row, 4).toString());
                statusBox.setSelectedItem(model.getValueAt(row, 5).toString());
            }
        });

        // Calculate end time action using movie duration from database
        calcButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    int movieID = Integer.parseInt(movieIDField.getText().trim());
                    String start = startField.getText().trim();

                    // Validate movie exists before calculating
                    if(!GUIHelper.movieExists(movieID))
                    {
                        JOptionPane.showMessageDialog(panel, "Movie ID " + movieID + " does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if(start.isEmpty())
                    {
                        JOptionPane.showMessageDialog(panel, "Please enter a start time first!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Validate start time format
                    if(!start.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}"))
                    {
                        JOptionPane.showMessageDialog(panel, "Start time must be in format YYYY-MM-DD HH:MM:SS!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    int duration = GUIHelper.getMovieDuration(movieID);

                    if(duration == 0)
                    {
                        JOptionPane.showMessageDialog(panel, "Could not get movie duration!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Add movie duration in minutes to get the end time
                    java.time.LocalDateTime startTime = java.time.LocalDateTime.parse(start,
                        java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                    java.time.LocalDateTime endTime = startTime.plusMinutes(duration);

                    String endFormatted = endTime.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    endField.setText(endFormatted);

                    JOptionPane.showMessageDialog(panel, "End time calculated based on " + duration + " minute runtime!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
                catch(NumberFormatException ex)
                {
                    JOptionPane.showMessageDialog(panel, "Movie ID must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Add showtime action
        addButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    int movieID = Integer.parseInt(movieIDField.getText().trim());
                    int screenID = Integer.parseInt(screenIDField.getText().trim());
                    String start = startField.getText().trim();
                    String end = endField.getText().trim();
                    String status = statusBox.getSelectedItem().toString();

                    if(start.isEmpty() || end.isEmpty())
                    {
                        JOptionPane.showMessageDialog(panel, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Validate start time format
                    if(!start.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}"))
                    {
                        JOptionPane.showMessageDialog(panel, "Start time must be in format YYYY-MM-DD HH:MM:SS!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Ensure end time is after start time
                    if(end.compareTo(start) <= 0)
                    {
                        JOptionPane.showMessageDialog(panel, "End time must be after start time!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if(!GUIHelper.movieExists(movieID))
                    {
                        JOptionPane.showMessageDialog(panel, "Movie ID " + movieID + " does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if(!GUIHelper.screenExists(screenID))
                    {
                        JOptionPane.showMessageDialog(panel, "Screen ID " + screenID + " does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Prevent duplicate showtimes for the same movie, screen and start time
                    if(GUIHelper.duplicateShowtime(movieID, screenID, start))
                    {
                        JOptionPane.showMessageDialog(panel, "This showtime already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Prevent showtimes being added for retired movies
                    if(GUIHelper.isMovieRetired(movieID))
                    {
                        JOptionPane.showMessageDialog(panel, "Cannot create a showtime for a retired movie!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    showtimeDAO.addShowtime(movieID, screenID, start, end, status);
                    loadShowtimes(model);
                    movieIDField.setText(""); screenIDField.setText("");
                    startField.setText(""); endField.setText("");
                    JOptionPane.showMessageDialog(panel, "Showtime added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
                catch(NumberFormatException ex)
                {
                    JOptionPane.showMessageDialog(panel, "Movie ID and Screen ID must be numbers!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Update showtime action
        updateButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                int row = showtimeTable.getSelectedRow();
                if(row == -1)
                {
                    JOptionPane.showMessageDialog(panel, "Please select a showtime to update!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Check if any values have actually changed
                if(movieIDField.getText().trim().equals(model.getValueAt(row, 1).toString()) &&
                    screenIDField.getText().trim().equals(model.getValueAt(row, 2).toString()) &&
                    startField.getText().trim().equals(model.getValueAt(row, 3).toString()) &&
                    endField.getText().trim().equals(model.getValueAt(row, 4).toString()) &&
                    statusBox.getSelectedItem().toString().equals(model.getValueAt(row, 5).toString()))
                    {
                        JOptionPane.showMessageDialog(panel, "No changes were made!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                try
                {
                    int id = Integer.parseInt(model.getValueAt(row, 0).toString());
                    int movieID = Integer.parseInt(movieIDField.getText().trim());
                    int screenID = Integer.parseInt(screenIDField.getText().trim());
                    String start = startField.getText().trim();
                    String end = endField.getText().trim();
                    String status = statusBox.getSelectedItem().toString();

                    // Ensure end time is after start time
                    if(end.compareTo(start) <= 0)
                    {
                        JOptionPane.showMessageDialog(panel, "End time must be after start time!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if(!GUIHelper.movieExists(movieID))
                    {
                        JOptionPane.showMessageDialog(panel, "Movie ID " + movieID + " does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if(!GUIHelper.screenExists(screenID))
                    {
                        JOptionPane.showMessageDialog(panel, "Screen ID " + screenID + " does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if(GUIHelper.isMovieRetired(movieID))
                    {
                        JOptionPane.showMessageDialog(panel, "Cannot create a showtime for a retired movie!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    showtimeDAO.updateShowtime(id, movieID, screenID, start, end, status);
                    loadShowtimes(model);
                    JOptionPane.showMessageDialog(panel, "Showtime updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
                catch(NumberFormatException ex)
                {
                    JOptionPane.showMessageDialog(panel, "Movie ID and Screen ID must be numbers!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Delete showtime action (soft delete - sets status to Cancelled)
        deleteButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                int row = showtimeTable.getSelectedRow();
                if(row == -1)
                {
                    JOptionPane.showMessageDialog(panel, "Please select a showtime to delete!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int confirm = JOptionPane.showConfirmDialog(panel, "Are you sure?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if(confirm == JOptionPane.YES_OPTION)
                {
                    int id = Integer.parseInt(model.getValueAt(row, 0).toString());
                    showtimeDAO.deleteShowtime(id);
                    loadShowtimes(model);
                    JOptionPane.showMessageDialog(panel, "Showtime deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // Refresh table data
        refreshButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                loadShowtimes(model);
            }
        });

        return panel;
    }

    // Loads showtime data into the table
    private void loadShowtimes(DefaultTableModel model)
    {
        model.setRowCount(0);
        ArrayList<String[]> showtimes = showtimeDAO.getAllShowtimes();
        for(String[] s : showtimes)
        {
            model.addRow(s);
        }
    }
}
