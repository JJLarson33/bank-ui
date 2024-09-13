/**
 *
 * @author Larson, J.
 */
package banking.business;


public class SavingAccount extends Account {
    
    private double interestRate;
        
    public SavingAccount() {
      this(0, 0.0, 0.0);
    }
    
    public SavingAccount(int acctId, double bal, double i)
    {
        super(acctId, bal);
        interestRate = i;
    }   

    @Override
    public double getBalance() {
        return super.getBalance();
    }

    @Override
    public int getAccountId() {
        return super.getAccountId();
    }   
    
    public double getInterestRate()
    {
        return interestRate;
    }   
    
    @Override
    public String toString()
    {
        return "Savings Account\n" + super.toString() + "\nInterest Rate: " + interestRate + "%";
    }
}
