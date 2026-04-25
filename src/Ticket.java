// Name: Sarah Crotty
// Date: 24/02/2026
// Purpose: Ticket class

public class Ticket
{
    // Fields representing ticket data
    private int ticketID;
    private int seatBookingID;
    private String qrCode;
    private String issuedAt;

    // Returns the ticket ID
    public int getTicketID()
    {
        return ticketID;
    }

    // Sets the ticket ID
    public void setTicketID(int ticketID)
    {
        this.ticketID = ticketID;
    }

    // Returns the seat booking ID linked to this ticket
    public int getSeatBookingID()
    {
        return seatBookingID;
    }

    // Sets the seat booking ID linked to this ticket
    public void setSeatBookingID(int seatBookingID)
    {
        this.seatBookingID = seatBookingID;
    }

    // Returns the QR code string for this ticket
    public String getQrCode()
    {
        return qrCode;
    }

    // Sets the QR code string for this ticket
    public void setQrCode(String qrCode)
    {
        this.qrCode = qrCode;
    }

    // Returns the date and time the ticket was issued
    public String getIssuedAt()
    {
        return issuedAt;
    }

    // Sets the date and time the ticket was issued
    public void setIssuedAt(String issuedAt)
    {
        this.issuedAt = issuedAt;
    }
}
