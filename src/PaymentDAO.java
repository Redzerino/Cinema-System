// Name: Sarah Crotty
// Date: 01/03/2026
// Purpose: CRUD for Payment

import java.sql.*;
import java.util.ArrayList;

public class PaymentDAO
{
    // Adds a new payment record
    public void addPayment(int bookingID, double amount, String paymentMethod, String reference, String status)
    {
        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO payments (bookingID, amount, paymentMethod, reference, status) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, bookingID);
            ps.setDouble(2, amount);
            ps.setString(3, paymentMethod);
            ps.setString(4, reference);
            ps.setString(5, status);
            ps.executeUpdate();
            System.out.println("Payment added successfully");
        }
        catch(Exception e) { e.printStackTrace(); }
    }

    // Retrieves all payment records
    public ArrayList<String[]> getAllPayments()
    {
        ArrayList<String[]> payments = new ArrayList<>();

        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM payments";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next())
            {
                String[] row = {
                    String.valueOf(rs.getInt("paymentID")),
                    String.valueOf(rs.getInt("bookingID")),
                    String.valueOf(rs.getDouble("amount")),
                    rs.getString("paymentMethod"),
                    rs.getString("reference"),
                    rs.getString("status"),
                    rs.getString("paidAt")
                };
                payments.add(row);
            }
        }
        catch(Exception e) { e.printStackTrace(); }

        return payments;
    }

    // Updates an existing payment record
    public void updatePayment(int paymentID, double amount, String paymentMethod, String reference, String status)
    {
        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "UPDATE payments SET amount=?, paymentMethod=?, reference=?, status=? WHERE paymentID=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDouble(1, amount);
            ps.setString(2, paymentMethod);
            ps.setString(3, reference);
            ps.setString(4, status);
            ps.setInt(5, paymentID);
            ps.executeUpdate();
            System.out.println("Payment updated successfully");
        }
        catch(Exception e) { e.printStackTrace(); }
    }

    // Permanently deletes a payment record
    public void deletePayment(int paymentID)
    {
        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "DELETE FROM payments WHERE paymentID=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, paymentID);
            ps.executeUpdate();
            System.out.println("Payment deleted successfully");
        }
        catch(Exception e) { e.printStackTrace(); }
    }
}