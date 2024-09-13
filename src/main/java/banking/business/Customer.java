/**
 *
 * @author Larson, J. 2022
 */
package banking.business;

public class Customer {
    
    public int customerId;
    public String firstName;
    public String lastName;
    public String address;
    public String phoneNum;
    public int accountId;
    
    public Customer(){
        this(0, "","","","", 0);
    }
    
    public Customer(int cId, String fn, String ln, String addr, String pnum, int aId)
    {
        customerId = cId;
        firstName = fn;
        lastName = ln;
        address = addr;
        phoneNum = pnum;
        accountId = aId;        
    }   
    
    public int getCustomerId()
    {
        return customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public int getAccountId()
    {
        return accountId;
    }
    
    @Override
    public String toString()
    {
        return "Name: " + firstName + " " + lastName + "\nAddress: " + address +
                "\nContact #: " + phoneNum;
    }    
    
}
