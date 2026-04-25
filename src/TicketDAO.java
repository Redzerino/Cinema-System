// Name: Sarah Crotty
// Date: 24/02/2026
// Purpose: Ticket CRUD

import java.sql.*;
import java.util.ArrayList;

public class TicketDAO
{
    // Inserts a new ticket record into the database
    public void addTicket(int seatBookingID, String qrCode)
    {
        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO tickets (seatBookingID, qrCode) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, seatBookingID);
            ps.setString(2, qrCode);
            ps.executeUpdate();
            System.out.println("Ticket added successfully");
        }
        catch(Exception e) { e.printStackTrace(); }
    }

    // Returns all ticket records from the database
    public ArrayList<String[]> getAllTickets()
    {
        ArrayList<String[]> tickets = new ArrayList<>();
        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM tickets";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next())
            {
                String[] row = {
                    String.valueOf(rs.getInt("ticketID")),
                    String.valueOf(rs.getInt("seatBookingID")),
                    rs.getString("qrCode"),
                    rs.getString("issuedAt")
                };
                tickets.add(row);
            }
        }
        catch(Exception e) { e.printStackTrace(); }
        return tickets;
    }

    // Updates the QR code of an existing ticket by ID
    public void updateTicket(int ticketID, String qrCode)
    {
        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "UPDATE tickets SET qrCode=? WHERE ticketID=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, qrCode);
            ps.setInt(2, ticketID);
            ps.executeUpdate();
            System.out.println("Ticket updated successfully");
        }
        catch(Exception e) { e.printStackTrace(); }
    }

    // Permanently deletes a ticket record by ID
    public void deleteTicket(int ticketID)
    {
        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "DELETE FROM tickets WHERE ticketID=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, ticketID);
            ps.executeUpdate();
            System.out.println("Ticket deleted successfully");
        }
        catch(Exception e) { e.printStackTrace(); }
    }
}
