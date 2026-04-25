// Name: Sarah Crotty
// Date: 28/02/2026
// Purpose: Booking class to store and manage booking details

public class Booking
{
    // Variables
    private int bookingID;
    private String bookingReference;
    private int userID;
    private String guestEmail;
    private String guestName;
    private int showtimeID;
    private double totalAmount;
    private String status;
    private String createdAt;

    // Gets the booking ID
    public int getBookingID()
    {
        return bookingID;
    }

    // Sets the booking ID
    public void setBookingID(int bookingID)
    {
        this.bookingID = bookingID;
    }

    // Gets the booking reference
    public String getBookingReference()
    {
        return bookingReference;
    }

    // Sets the booking reference
    public void setBookingReference(String bookingReference)
    {
        this.bookingReference = bookingReference;
    }

    // Gets the user ID
    public int getUserID()
    {
        return userID;
    }

    // Sets the user ID
    public void setUserID(int userID)
    {
        this.userID = userID;
    }

    // Gets the guest email
    public String getGuestEmail()
    {
        return guestEmail;
    }

    // Sets the guest email
    public void setGuestEmail(String guestEmail)
    {
        this.guestEmail = guestEmail;
    }

    // Gets the guest name
    public String getGuestName()
    {
        return guestName;
    }

    // Sets the guest name
    public void setGuestName(String guestName)
    {
        this.guestName = guestName;
    }

    // Gets the showtime ID
    public int getShowtimeID()
    {
        return showtimeID;
    }

    // Sets the showtime ID
    public void setShowtimeID(int showtimeID)
    {
        this.showtimeID = showtimeID;
    }

    // Gets the total amount
    public double getTotalAmount()
    {
        return totalAmount;
    }

    // Sets the total amount
    public void setTotalAmount(double totalAmount)
    {
        this.totalAmount = totalAmount;
    }

    // Gets the booking status
    public String getStatus()
    {
        return status;
    }

    // Sets the booking status
    public void setStatus(String status)
    {
        this.status = status;
    }

    // Gets the created date/time
    public String getCreatedAt()
    {
        return createdAt;
    }

    // Sets the created date/time
    public void setCreatedAt(String createdAt)
    {
        this.createdAt = createdAt;
    }
}