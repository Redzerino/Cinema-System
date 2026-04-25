// Name: Sarah Crotty
// Date: 26/02/2026
// Purpose:class representing a temporary seat reservation

public class SeatReserve
{
    // Fields representing seat reservation data
    private int reserveID;
    private int showtimeID;
    private int seatID;
    private int userID;
    private String guestSessionID;
    private String reserveAt;
    private String expireAt;
    private String status;

    // Returns the reservation ID
    public int getReserveID()
    {
        return reserveID;
    }

    // Sets the reservation ID
    public void setReserveID(int reserveID)
    {
        this.reserveID = reserveID;
    }

    // Returns the showtime ID for this reservation
    public int getShowtimeID()
    {
        return showtimeID;
    }

    // Sets the showtime ID for this reservation
    public void setShowtimeID(int showtimeID)
    {
        this.showtimeID = showtimeID;
    }

    // Returns the seat ID being reserved
    public int getSeatID()
    {
        return seatID;
    }

    // Sets the seat ID being reserved
    public void setSeatID(int seatID)
    {
        this.seatID = seatID;
    }

    // Returns the user ID associated with this reservation (0 if guest)
    public int getUserID()
    {
        return userID;
    }

    // Sets the user ID associated with this reservation
    public void setUserID(int userID)
    {
        this.userID = userID;
    }

    // Returns the guest session ID for non-logged-in reservations
    public String getGuestSessionID()
    {
        return guestSessionID;
    }

    // Sets the guest session ID
    public void setGuestSessionID(String guestSessionID)
    {
        this.guestSessionID = guestSessionID;
    }

    // Returns the timestamp when the reservation was made
    public String getReserveAt()
    {
        return reserveAt;
    }

    // Sets the timestamp when the reservation was made
    public void setReserveAt(String reserveAt)
    {
        this.reserveAt = reserveAt;
    }

    // Returns the timestamp when the reservation expires
    public String getExpireAt()
    {
        return expireAt;
    }

    // Sets the timestamp when the reservation expires
    public void setExpireAt(String expireAt)
    {
        this.expireAt = expireAt;
    }

    // Returns the current status of the reservation (e.g. Active, Expired, Completed)
    public String getStatus()
    {
        return status;
    }

    // Sets the current status of the reservation
    public void setStatus(String status)
    {
        this.status = status;
    }
}
