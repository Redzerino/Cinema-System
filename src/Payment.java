// Name: Sarah Crotty
// Date: 01/03/2026
// Purpose: Payment class

public class Payment
{
    // payment fields
    private int paymentID;
    private int bookingID;
    private double amount;
    private String paymentMethod;
    private String reference;
    private String status;
    private String paidAt;

    // Gets payment ID
    public int getPaymentID()
    {
        return paymentID;
    }

    // Sets payment ID
    public void setPaymentID(int paymentID)
    {
        this.paymentID = paymentID;
    }

    // Gets booking ID
    public int getBookingID()
    {
        return bookingID;
    }

    // Sets booking ID
    public void setBookingID(int bookingID)
    {
        this.bookingID = bookingID;
    }

    // Gets payment amount
    public double getAmount()
    {
        return amount;
    }

    // Sets payment amount
    public void setAmount(double amount)
    {
        this.amount = amount;
    }

    // Gets payment method
    public String getPaymentMethod()
    {
        return paymentMethod;
    }

    // Sets payment method
    public void setPaymentMethod(String paymentMethod)
    {
        this.paymentMethod = paymentMethod;
    }

    // Gets payment reference
    public String getReference()
    {
        return reference;
    }

    // Sets payment reference
    public void setReference(String reference)
    {
        this.reference = reference;
    }

    // Gets payment status
    public String getStatus()
    {
        return status;
    }

    // Sets payment status
    public void setStatus(String status)
    {
        this.status = status;
    }

    // Gets payment timestamp
    public String getPaidAt()
    {
        return paidAt;
    }

    // Sets payment timestamp
    public void setPaidAt(String paidAt)
    {
        this.paidAt = paidAt;
    }
}