/**
 *
 * @author Larson, J. 2022
 */
package banking.business;

public abstract class Account {
    
    private int accountId;
    private double balance;
    
    public Account()
    {
        this(0,0.0);
    }
    
    public Account(int acctId, double bal)
    {
        this.accountId = acctId;
        this.balance = bal;        
    }         

    public int getAccountId() {
        return accountId;
    }

    public double getBalance() {
        return balance;
    }   
    
    @Override
    public String toString()
    {
        return "Account #: " + accountId + "\nBalance: $" + balance;
    }        
}
