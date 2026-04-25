// Name: Sarah Crotty
// Date: 27/03/2026
// Purpose: GUI panel for managing movies

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MoviePanel
{
    private MovieDAO movieDAO = new MovieDAO();
    private JTable movieTable;

    // Creates and returns the movie management panel UI
    public JPanel createPanel()
    {
        JPanel panel = new JPanel(new BorderLayout());

        // Table setup for displaying movie records
        String[] columns = {"ID", "Title", "Description", "Genre", "Duration", "Age Rating", "Language", "Release Date", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        movieTable = new JTable(model);
        GUIHelper.styleTable(movieTable);
        JScrollPane scrollPane = new JScrollPane(movieTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Input form for movie details
        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Title:");
        JTextField titleField = new JTextField();
        JLabel descLabel = new JLabel("Description:");
        JTextField descField = new JTextField();
        JLabel genreLabel = new JLabel("Genre:");
        JComboBox<String> genreBox = new JComboBox<>(new String[]{"Action", "Comedy", "Drama", "Horror", "Sci-Fi", "Family", "Thriller", "Romance", "Animation"});
        JLabel durationLabel = new JLabel("Duration (mins):");
        JTextField durationField = new JTextField();
        JLabel ageLabel = new JLabel("Age Rating:");
        JComboBox<String> ageBox = new JComboBox<>(new String[]{"G", "PG", "12A", "15", "18"});
        JLabel langLabel = new JLabel("Language:");
        JTextField langField = new JTextField();
        JLabel dateLabel = new JLabel("Release Date (YYYY-MM-DD):");
        JTextField dateField = new JTextField();
        JLabel statusLabel = new JLabel("Status:");
        JComboBox<String> statusBox = new JComboBox<>(new String[]{"Now Showing", "Coming Soon", "Inactive"});

        inputPanel.add(titleLabel); inputPanel.add(titleField);
        inputPanel.add(descLabel); inputPanel.add(descField);
        inputPanel.add(genreLabel); inputPanel.add(genreBox);
        inputPanel.add(durationLabel); inputPanel.add(durationField);
        inputPanel.add(ageLabel); inputPanel.add(ageBox);
        inputPanel.add(langLabel); inputPanel.add(langField);
        inputPanel.add(dateLabel); inputPanel.add(dateField);
        inputPanel.add(statusLabel); inputPanel.add(statusBox);

        // Buttons for CRUD operations
        JButton addButton = GUIHelper.createButton("Add Movie", GUIHelper.addGreen);
        JButton updateButton = GUIHelper.createButton("Update Movie", GUIHelper.updateBlue);
        JButton deleteButton = GUIHelper.createButton("Retire Movie", GUIHelper.deleteRed);
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

        // Load initial movie data
        loadMovies(model);

        // Populates fields when a row is selected
        movieTable.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                int row = movieTable.getSelectedRow();
                titleField.setText(model.getValueAt(row, 1).toString());
                descField.setText(model.getValueAt(row, 2).toString());
                genreBox.setSelectedItem(model.getValueAt(row, 3).toString());
                durationField.setText(model.getValueAt(row, 4).toString());
                ageBox.setSelectedItem(model.getValueAt(row, 5).toString());
                langField.setText(model.getValueAt(row, 6).toString());
                dateField.setText(model.getValueAt(row, 7).toString());
                statusBox.setSelectedItem(model.getValueAt(row, 8).toString());
            }
        });

        // Adds a new movie record
        addButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    String title = titleField.getText().trim();
                    String desc = descField.getText().trim();
                    String genre = genreBox.getSelectedItem().toString();
                    int duration = Integer.parseInt(durationField.getText().trim());
                    String age = ageBox.getSelectedItem().toString();
                    String lang = langField.getText().trim();
                    String date = dateField.getText().trim();
                    String status = statusBox.getSelectedItem().toString();

                    if(title.isEmpty() || lang.isEmpty() || date.isEmpty())
                    {
                        JOptionPane.showMessageDialog(panel, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if(duration <= 0)
                    {
                        JOptionPane.showMessageDialog(panel, "Duration must be greater than 0!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if(!date.matches("\\d{4}-\\d{2}-\\d{2}"))
                    {
                        JOptionPane.showMessageDialog(panel, "Release date must be in format YYYY-MM-DD!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if(GUIHelper.duplicateMovie(title, date))
                    {
                        JOptionPane.showMessageDialog(panel, "A movie with this title and release date already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    movieDAO.addMovie(title, desc, genre, duration, age, lang, date, status);
                    loadMovies(model);
                    titleField.setText(""); descField.setText("");
                    durationField.setText(""); langField.setText(""); dateField.setText("");
                    JOptionPane.showMessageDialog(panel, "Movie added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
                catch(NumberFormatException ex)
                {
                    JOptionPane.showMessageDialog(panel, "Duration must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Updates selected movie record
        updateButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                int row = movieTable.getSelectedRow();
                if(row == -1)
                {
                    JOptionPane.showMessageDialog(panel, "Please select a movie to update!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try
                {
                    int id = Integer.parseInt(model.getValueAt(row, 0).toString());
                    String title = titleField.getText().trim();
                    String desc = descField.getText().trim();
                    String genre = genreBox.getSelectedItem().toString();
                    int duration = Integer.parseInt(durationField.getText().trim());
                    String age = ageBox.getSelectedItem().toString();
                    String lang = langField.getText().trim();
                    String date = dateField.getText().trim();
                    String status = statusBox.getSelectedItem().toString();

                    movieDAO.updateMovie(id, title, desc, genre, duration, age, lang, date, status);
                    loadMovies(model);
                    JOptionPane.showMessageDialog(panel, "Movie updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
                catch(NumberFormatException ex)
                {
                    JOptionPane.showMessageDialog(panel, "Duration must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Retires (soft deletes) a movie
        deleteButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                int row = movieTable.getSelectedRow();
                if(row == -1)
                {
                    JOptionPane.showMessageDialog(panel, "Please select a movie to retire!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(panel, "Are you sure you want to retire this movie?", "Confirm Retire", JOptionPane.YES_NO_OPTION);
                if(confirm == JOptionPane.YES_OPTION)
                {
                    int id = Integer.parseInt(model.getValueAt(row, 0).toString());

                    if(GUIHelper.hasUpcomingShowtime(id))
                    {
                        JOptionPane.showMessageDialog(panel, "Cannot retire a movie with upcoming showtimes!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    movieDAO.deleteMovie(id);
                    loadMovies(model);
                    JOptionPane.showMessageDialog(panel, "Movie retired successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // Refreshes table data
        refreshButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                loadMovies(model);
            }
        });

        return panel;
    }

    // Loads movies into the table
    private void loadMovies(DefaultTableModel model)
    {
        model.setRowCount(0);
        ArrayList<Movie> movies = movieDAO.getAllMovies();

        for(Movie m : movies)
        {
            model.addRow(new Object[]{
                m.getMovieID(), m.getTitle(), m.getDescription(),
                m.getGenre(), m.getDuration(), m.getAgeRating(),
                m.getLanguage(), m.getReleaseDate(), m.getStatus()
            });
        }
    }
}