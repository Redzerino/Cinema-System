// Name: Sarah Crotty
// Date: 28/02/2026
// Purpose: seat class

public class Seat
{
    // Fields representing seat data
    private int seatID;
    private int screenID;
    private String seatRow;
    private int seatNumber;
    private String seatType;
    private boolean isWorking;

    // Returns the seat ID
    public int getSeatID()
    {
        return seatID;
    }

    // Sets the seat ID
    public void setSeatID(int seatID)
    {
        this.seatID = seatID;
    }

    // Returns the screen ID this seat belongs to
    public int getScreenID()
    {
        return screenID;
    }

    // Sets the screen ID this seat belongs to
    public void setScreenID(int screenID)
    {
        this.screenID = screenID;
    }

    // Returns the row letter of the seat (e.g. A, B, C)
    public String getSeatRow()
    {
        return seatRow;
    }

    // Sets the row letter of the seat
    public void setSeatRow(String seatRow)
    {
        this.seatRow = seatRow;
    }

    // Returns the number of the seat within its row
    public int getSeatNumber()
    {
        return seatNumber;
    }

    // Sets the number of the seat within its row
    public void setSeatNumber(int seatNumber)
    {
        this.seatNumber = seatNumber;
    }

    // Returns the seat type (e.g. Standard, VIP)
    public String getSeatType()
    {
        return seatType;
    }

    // Sets the seat type
    public void setSeatType(String seatType)
    {
        this.seatType = seatType;
    }

    // Returns whether the seat is currently working/available
    public boolean isWorking()
    {
        return isWorking;
    }

    // Sets whether the seat is currently working/available
    public void setWorking(boolean isWorking)
    {
        this.isWorking = isWorking;
    }
}
