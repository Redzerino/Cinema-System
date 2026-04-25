//Name: Sarah Crotty
//Date: 08/04/2026
//Purpose: Displays bookings report with filtering data and totals

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ReportPanel
{
    public JPanel createPanel()
    {
        JPanel panel = new JPanel(new BorderLayout());

        //title label setup//
        JLabel titleLabel = new JLabel("Sales & Bookings Report", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(new Color(13, 13, 43));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panel.add(titleLabel, BorderLayout.NORTH);

        //table setup//
        String[] columns = {"Booking ID", "Reference", "Guest Name", "Guest Email", "Movie", "Showtime", "Screen", "Total Amount", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable reportTable = new JTable(model);
        GUIHelper.styleTable(reportTable);
        JScrollPane scrollPane = new JScrollPane(reportTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        //summary panel setup//
        JPanel summaryPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        summaryPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        summaryPanel.setBackground(new Color(240, 240, 255));

        JLabel totalBookingsLabel = new JLabel("Total Bookings: 0", SwingConstants.CENTER);
        totalBookingsLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel totalRevenueLabel = new JLabel("Total Revenue: €0.00", SwingConstants.CENTER);
        totalRevenueLabel.setFont(new Font("Arial", Font.BOLD, 14));
        totalRevenueLabel.setForeground(new Color(46, 139, 87));

        JLabel confirmedLabel = new JLabel("Confirmed: 0", SwingConstants.CENTER);
        confirmedLabel.setFont(new Font("Arial", Font.BOLD, 14));
        confirmedLabel.setForeground(new Color(30, 100, 200));

        summaryPanel.add(totalBookingsLabel);
        summaryPanel.add(totalRevenueLabel);
        summaryPanel.add(confirmedLabel);

        //button panel setup//
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton allBookingsButton = GUIHelper.createButton("All Bookings", new Color(13, 13, 43));
        JButton confirmedButton = GUIHelper.createButton("Confirmed Only", new Color(46, 139, 87));
        JButton cancelledButton = GUIHelper.createButton("Cancelled Only", new Color(180, 30, 30));
        JButton pendingButton = GUIHelper.createButton("Pending Only", new Color(200, 140, 0));
        JButton refreshButton = GUIHelper.createButton("Refresh", new Color(100, 100, 100));

        buttonPanel.add(allBookingsButton);
        buttonPanel.add(confirmedButton);
        buttonPanel.add(cancelledButton);
        buttonPanel.add(pendingButton);
        buttonPanel.add(refreshButton);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(summaryPanel, BorderLayout.NORTH);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);
        panel.add(southPanel, BorderLayout.SOUTH);

        //load default report//
        loadReport(model, "ALL", totalBookingsLabel, totalRevenueLabel, confirmedLabel);

        //all bookings filter action//
        allBookingsButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                loadReport(model, "ALL", totalBookingsLabel, totalRevenueLabel, confirmedLabel);
            }
        });

        //confirmed filter action//
        confirmedButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                loadReport(model, "Confirmed", totalBookingsLabel, totalRevenueLabel, confirmedLabel);
            }
        });

        //cancelled filter action//
        cancelledButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                loadReport(model, "Cancelled", totalBookingsLabel, totalRevenueLabel, confirmedLabel);
            }
        });

        //pending filter action//
        pendingButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                loadReport(model, "Pending", totalBookingsLabel, totalRevenueLabel, confirmedLabel);
            }
        });

        //refresh action//
        refreshButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                loadReport(model, "ALL", totalBookingsLabel, totalRevenueLabel, confirmedLabel);
            }
        });

        return panel;
    }

    //loads report data and calculates totals//
    private void loadReport(DefaultTableModel model, String filter,
                            JLabel totalBookingsLabel, JLabel totalRevenueLabel, JLabel confirmedLabel)
    {
        model.setRowCount(0);

        //variables//
        double totalRevenue = 0;
        int totalBookings = 0;
        int confirmedCount = 0;

        try
        {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT b.bookingID, b.bookingReference, b.guestName, b.guestEmail, " +
                        "m.title, s.startTime, sc.screenType, b.totalAmount, b.status " +
                        "FROM bookings b " +
                        "INNER JOIN showtimes s ON b.showtimeID = s.showtimeID " +
                        "INNER JOIN movies m ON s.movieID = m.movieID " +
                        "INNER JOIN screens sc ON s.screenID = sc.screenID";

            if(!filter.equals("ALL"))
            {
                sql += " WHERE b.status = '" + filter + "'";
            }

            sql += " ORDER BY b.bookingID DESC";

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next())
            {
                String[] row =
                {
                    String.valueOf(rs.getInt("bookingID")),
                    rs.getString("bookingReference"),
                    rs.getString("guestName"),
                    rs.getString("guestEmail"),
                    rs.getString("title"),
                    rs.getString("startTime"),
                    rs.getString("screenType"),
                    String.format("€%.2f", rs.getDouble("totalAmount")),
                    rs.getString("status")
                };

                model.addRow(row);

                totalRevenue += rs.getDouble("totalAmount");
                totalBookings++;

                if(rs.getString("status").equals("Confirmed"))
                {
                    confirmedCount++;
                }
            }

            totalBookingsLabel.setText("Total Bookings: " + totalBookings);
            totalRevenueLabel.setText("Total Revenue: " + String.format("€%.2f", totalRevenue));
            confirmedLabel.setText("Confirmed: " + confirmedCount);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}