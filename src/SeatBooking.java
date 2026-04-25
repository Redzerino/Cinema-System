// Name: Sarah Crotty
// Date: 28/02/2026
// Purpose: class for seat booking linking a booking, showtime, and seat

public class SeatBooking
{
    // Fields representing seat booking data
    private int seatBookingID;
    private int bookingID;
    private int showtimeID;
    private int seatID;
    private double pricePaid;

    // Returns the seat booking ID
    public int getSeatBookingID()
    {
        return seatBookingID;
    }

    // Sets the seat booking ID
    public void setSeatBookingID(int seatBookingID)
    {
        this.seatBookingID = seatBookingID;
    }

    // Returns the booking ID linked to this seat booking
    public int getBookingID()
    {
        return bookingID;
    }

    // Sets the booking ID linked to this seat booking
    public void setBookingID(int bookingID)
    {
        this.bookingID = bookingID;
    }

    // Returns the showtime ID for this seat booking
    public int getShowtimeID()
    {
        return showtimeID;
    }

    // Sets the showtime ID for this seat booking
    public void setShowtimeID(int showtimeID)
    {
        this.showtimeID = showtimeID;
    }

    // Returns the seat ID for this seat booking
    public int getSeatID()
    {
        return seatID;
    }

    // Sets the seat ID for this seat booking
    public void setSeatID(int seatID)
    {
        this.seatID = seatID;
    }

    // Returns the price paid for this seat booking
    public double getPricePaid()
    {
        return pricePaid;
    }

    // Sets the price paid for this seat booking
    public void setPricePaid(double pricePaid)
    {
        this.pricePaid = pricePaid;
    }
}
