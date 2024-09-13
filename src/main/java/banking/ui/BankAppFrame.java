/**
 *
 * @author Larson, J. 2022
 */
package banking.ui;

import java.awt.*;
import javax.swing.*;
import java.text.NumberFormat;
import banking.business.*;
import static banking.DB.AccountsDB.*;


public class BankAppFrame extends JFrame {

    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField addressField;
    private JTextField pNumField;
    private JTextField acctNumField;
    private JTextField balanceField;
    private JTextField updateBalField;    
    private JTextField accIntRateField;
    
    private JTextField monthIntRateField;
    private JTextField calcIntRateField;
    private JButton applySearch;
    private JButton applyCustUpdate;
    private JButton applyAddCust;
    private JButton calcMonth;
    private JButton increaseBal;
    private JButton decreaseBal;
    private JButton okNewIntRate;
    
    // variables used as trackers mainly for the prev/next functionality of the app
    // as well as keeping track of the current 'displayed/opened' account
    // ensures add/update customer, and the balance functions only operate on the current open account 
    private int scrollCustIdRef = 0;    
    private int custIdLast = 0;
    private int scrollAcctIdRef = 0;
    
    public BankAppFrame(){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());            
        } catch (ClassNotFoundException | InstantiationException |
                 IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println(e);
        }
        initComponents();
    }

    private void initComponents(){
        setTitle("Banking United Inc.");
        setLocationByPlatform(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // textfield components

        firstNameField = new JTextField();
        lastNameField = new JTextField();
        addressField = new JTextField();
        pNumField = new JTextField();
        acctNumField = new JTextField();
        balanceField = new JTextField();
        updateBalField = new JTextField();
        accIntRateField = new JTextField();

        // dimensions of main textfields

        Dimension dim0 = new Dimension(150,20);    
        firstNameField.setPreferredSize(dim0);
        firstNameField.setMinimumSize(dim0);
        firstNameField.setEditable(false);
        lastNameField.setPreferredSize(dim0);
        lastNameField.setMinimumSize(dim0);    
        lastNameField.setEditable(false);
        addressField.setPreferredSize(dim0);
        addressField.setMinimumSize(dim0);
        addressField.setEditable(false);
        pNumField.setPreferredSize(dim0);
        pNumField.setMinimumSize(dim0);
        pNumField.setEditable(false);
        acctNumField.setPreferredSize(dim0);
        acctNumField.setMinimumSize(dim0);
        acctNumField.setEditable(false);
        balanceField.setPreferredSize(dim0);
        balanceField.setMinimumSize(dim0);
        balanceField.setEditable(false);
        updateBalField.setPreferredSize(dim0);
        updateBalField.setMinimumSize(dim0);
        updateBalField.setEditable(false);
        Dimension dim00 = new Dimension(50,20);
        accIntRateField.setPreferredSize(dim00);
        accIntRateField.setMinimumSize(dim00);
        accIntRateField.setEditable(false);

        // dimensions of monthIntRate field
        monthIntRateField = new JTextField();
        Dimension dim1 = new Dimension(50,20);
        monthIntRateField.setPreferredSize(dim1);
        monthIntRateField.setMinimumSize(dim1);
        monthIntRateField.setEditable(false);

        // dimensions of calcIntRate field
        calcIntRateField = new JTextField();
        Dimension dim2 = new Dimension(100,20);
        calcIntRateField.setPreferredSize(dim2);
        calcIntRateField.setMinimumSize(dim2);
        calcIntRateField.setEditable(false);

        // button components

        JButton searchCustomer = new JButton("Search Customer");
        searchCustomer.addActionListener(e -> searchCustomerPrompt());

        JButton prevCustomer = new JButton("Previous Customer");
        prevCustomer.addActionListener(e -> prevCustomer());

        JButton nextCustomer = new JButton("Next Customer");
        nextCustomer.addActionListener(e -> nextCustomer());

        JButton addCustomer = new JButton("Add Customer");
        addCustomer.addActionListener(e -> addCustomerPrompt());

        JButton updateCustomer = new JButton("Update Customer");
        updateCustomer.addActionListener(e -> updateCustomerPrompt());

        JButton openAccount = new JButton("Open Account");
        openAccount.addActionListener(e -> openAccountPrompt());

        JButton deposit = new JButton("Deposit");
        deposit.addActionListener(e -> increaseBalPrompt());
        JButton withdraw = new JButton("Withdraw");
        withdraw.addActionListener(e -> decreaseBalPrompt());
           
        JButton calcInterest = new JButton("Calculate Interest");
        calcInterest.addActionListener(e -> calcIntPrompt());

        applySearch = new JButton("Apply Search");
        applySearch.addActionListener(e-> searchCustomer());

        applyCustUpdate = new JButton("Update Customer Details");
        applyCustUpdate.addActionListener(e -> updateCustomer());

        applyAddCust = new JButton("Add Customer");
        applyAddCust.addActionListener(e -> addCustomer());
        
        increaseBal = new JButton("Add Amount");
        increaseBal.addActionListener(e -> increaseBal());
        
        decreaseBal = new JButton("Subtract Amount");
        decreaseBal.addActionListener(e -> decreaseBal());
        
        calcMonth = new JButton("Calc Monthly");
        calcMonth.addActionListener(e -> calcMonthInt());
        
        JButton changeIntRate = new JButton("Change Interest");
        changeIntRate.addActionListener(e -> changeIntPrompt());
        
        okNewIntRate = new JButton("Update Int Rate");
        okNewIntRate.addActionListener(e -> applyNewRate());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());

        mainPanel.add(new JLabel("Customer First Name"), getConstraints(0,0));
        mainPanel.add(firstNameField, getConstraints(1,0));
        mainPanel.add(new JLabel("Customer Last Name"), getConstraints(0,1));
        mainPanel.add(lastNameField, getConstraints(1,1));
        mainPanel.add(new JLabel("Address"), getConstraints(0,2));
        mainPanel.add(addressField, getConstraints(1,2));
        mainPanel.add(new JLabel("Phone Number"), getConstraints(0,3));
        mainPanel.add(pNumField, getConstraints(1,3));
        mainPanel.add(new JLabel("Account Number"), getConstraints(0,4));
        mainPanel.add(acctNumField, getConstraints(1,4));
        mainPanel.add(new JLabel("Balance"), getConstraints(0,5));
        mainPanel.add(balanceField, getConstraints(1,5));
        mainPanel.add(new JLabel("Withdraw/Deposit"), getConstraints(0,6));
        mainPanel.add(updateBalField, getConstraints(1,6));
        mainPanel.add(new JLabel("Current Interest Rate"), getConstraints(0,10));
        mainPanel.add(accIntRateField, getConstraints(1,10));
        
        mainPanel.add(searchCustomer, getConstraints(2,0));
        mainPanel.add(prevCustomer, getConstraints(2,1));
        mainPanel.add(nextCustomer, getConstraints(2,2));
        mainPanel.add(addCustomer, getConstraints(2,3));
        mainPanel.add(updateCustomer, getConstraints(2,4));
        mainPanel.add(openAccount, getConstraints(2,5));
        mainPanel.add(deposit, getConstraints(2,6));
        mainPanel.add(withdraw, getConstraints(2,7));
        mainPanel.add(calcInterest, getConstraints(2,8));
        mainPanel.add(applySearch, getConstraints(1,7));
        applySearch.setVisible(false);
        mainPanel.add(applyCustUpdate, getConstraints(0,7));
        applyCustUpdate.setVisible(false);
        mainPanel.add(applyAddCust, getConstraints(1,8));
        applyAddCust.setVisible(false);
        mainPanel.add(increaseBal, getConstraints(0,8));
        increaseBal.setVisible(false);
        mainPanel.add(decreaseBal, getConstraints(0,9));
        decreaseBal.setVisible(false);
        mainPanel.add(changeIntRate, getConstraints(2,9));
        mainPanel.add(okNewIntRate, getConstraints(1,9));
        okNewIntRate.setVisible(false);
        
        // bottom panel

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));    

        bottomPanel.add(new JLabel("Interest Month"));
        bottomPanel.add(monthIntRateField);
        bottomPanel.add(calcMonth);
        calcMonth.setVisible(false);
        bottomPanel.add(new JLabel("Calculated Interest"));
        bottomPanel.add(calcIntRateField);

        // align all panels
        add(mainPanel, BorderLayout.NORTH);   
        add(bottomPanel, BorderLayout.SOUTH);

        setSize(450,375);
        Customer c = findLastCustIdDB();
        setCustIdLast(c);
        System.out.println(scrollCustIdRef);
        System.out.println(custIdLast);
    }    

    // handling a GridBagConstraints object
    private GridBagConstraints getConstraints(int x, int y) {
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5, 5, 0, 5);
        c.gridx = x;
        c.gridy = y;
        return c;
    }

    // just a way to prime/set the tracker of where the end of customer table
    // by using an customer object created from a SQL pull
    private void setCustIdLast(Customer c)
    {
        custIdLast = c.getCustomerId();
    }

    // prompt for Search Customer button, selecting Yes will unlock the customer name textfields
    // for manipulation and expects 'Apply Search' to be clicked before anything else should be done
    private void searchCustomerPrompt()
    {
        SwingValidator sv0 = new SwingValidator(this);
        String msg = "Search for specific Customer?";
        int choice = sv0.newSearch(msg);
        if (choice == 0)
        {
            firstNameField.setEditable(true);
            lastNameField.setEditable(true);
            applySearch.setVisible(true);
            resetBlankFields();
        }
    }    

    // Trigger function to the Apply Search button, returns some result
    // then locks the fields again, if a good result is returned, all relevant account info is displayed
    // and that account is 'open' for manipulation to Add Customer (to existing account) or Update Customer (customer details only)
    // or Deposit/Withdrawal/Calculate or Change Interest
    private void searchCustomer()
    {    
        resetInterestFields();
        SwingValidator sv = new SwingValidator(this);
        if (sv.hasValues(firstNameField, "Customer First Name") && sv.hasValues(lastNameField, "Customer Last Name"))
        {           
            String fN = firstNameField.getText();
            String lN = lastNameField.getText();

            Customer c = searchCustomerDB(fN, lN);
            if (c != null)
            {
                SavingAccount sa = getAccountObj(c.getAccountId());           

                displayAllFields(c, sa);                
                scrollCustIdRef = c.getCustomerId();
                scrollAcctIdRef = sa.getAccountId();

                firstNameField.setEditable(false);
                lastNameField.setEditable(false);
                applySearch.setVisible(false);
            }
            else if (c == null)
            {
                SwingValidator sv0 = new SwingValidator(this);
                sv0.noSuchRecordFound();
            }
        }               
    }   
    
    // Scrolls 'UP' through the Customer Table, calls AccountDB methods findLastCustIdDB() if the scrollCustIdRef is 0 or row 1
    // Otherwise if scrollCustIdRef is any other row #, then it calls AccountDB method scrollCustomerDB(),
    // handing over a customerId that is subtracted by 1
    // if a good result is returned, all relevant account info is displayed
    // and that account is 'open' for manipulation to Add Customer (to existing account) or Update Customer (customer details only)
    // or Deposit/Withdrawal/Calculate or Change Interest
    private void prevCustomer() 
    {
        resetInterestFields();
        if (firstNameField.isEditable() == false && lastNameField.isEditable() == false && addressField.isEditable() == false
                && pNumField.isEditable() == false && updateBalField.isEditable() == false)
        {        
            if (scrollCustIdRef == 1 || scrollCustIdRef == 0)
            {
                Customer c = findLastCustIdDB();          
                SavingAccount sa = getAccountObj(c.getAccountId());            

                displayAllFields(c, sa);               
                scrollCustIdRef = c.getCustomerId();
                scrollAcctIdRef = sa.getAccountId();

                System.out.println("Prevcust" + scrollCustIdRef);
                System.out.println("Prevcust" + custIdLast);
            }            
            else
            {
                Customer c = scrollCustomerDB(scrollCustIdRef-1);
                SavingAccount sa = getAccountObj(c.getAccountId());            

                displayAllFields(c, sa);               
                scrollCustIdRef = c.getCustomerId();
                scrollAcctIdRef = sa.getAccountId();

                System.out.println("PrevcustELSE" + scrollCustIdRef);
                System.out.println("PrevcustELSE" + custIdLast);
            }
        }
        else 
        {
            SwingValidator sv = new SwingValidator(this);
            sv.preventScrollBeforeSearch(firstNameField, lastNameField);
        }        
    }
    
    // Scrolls 'DOWN' through the Customer Table, calls AccountDB methods findFirstCustIdDB() 
    // if the scrollCustIdRef is 0 or the same as custIdLast(last row)
    // Otherwise if scrollCustIdRef is any other row #, then it calls AccountDB method scrollCustomerDB(),
    // handing over a customerId that is incremented by 1
    // if a good result is returned, all relevant account info is displayed
    // and that account is 'open' for manipulation to Add Customer (to existing account) or Update Customer (customer details only)
    // or Deposit/Withdrawal/Calculate or Change Interest
    private void nextCustomer()
    {
        resetInterestFields();
        if (firstNameField.isEditable() == false && lastNameField.isEditable() == false && addressField.isEditable() == false
                && pNumField.isEditable() == false && updateBalField.isEditable() == false)              
        {
            if (scrollCustIdRef == custIdLast || scrollCustIdRef == 0)
            {
                Customer c = findFirstCustIdDB();
                SavingAccount sa = getAccountObj(c.getAccountId());

                displayAllFields(c, sa);               
                scrollCustIdRef = c.getCustomerId();
                scrollAcctIdRef = sa.getAccountId();

                System.out.println("Nextcust" + scrollCustIdRef);
                System.out.println("Nextcust" + custIdLast);
            }
            else           
            {
                Customer c = scrollCustomerDB(scrollCustIdRef+1);
                SavingAccount sa = getAccountObj(c.getAccountId());

                displayAllFields(c, sa);                            
                scrollCustIdRef = c.getCustomerId();
                scrollAcctIdRef = sa.getAccountId();

                System.out.println("NextcustELSE" + scrollCustIdRef);
                System.out.println("NextcustELSE" + custIdLast);
            }            
        }
        else
        {
            SwingValidator sv = new SwingValidator(this);
            sv.preventScrollBeforeSearch(firstNameField, lastNameField);
        }
    }

    // Prompt for starting a new account - First by generating a new SavingAccount row and accountId, creating a SavingAccount obj from that result
    // then using that accountId to assign to a newly created customer row that is 
    // blank except for an auto generated customerId and the assigned accountId
    // The relevent trackers are updated to indicate this is the current open account, and the user is allowed to add in new customer details before 
    // clicking Update Customer Details
    // User can use deposit to update the balance, or change the interest rate with another button
    private void openAccountPrompt() 
    {
        SwingValidator sv0 = new SwingValidator(this);
        String msg = "Open New Account?";
        int choice = sv0.newSearch(msg);
        if (choice == 0)
        {
           resetBlankFields();
           scrollCustIdRef = 0;
           scrollAcctIdRef = 0;
           createNewAccountDB();

           SavingAccount sa = findLastAccountIdDB();
           scrollAcctIdRef = sa.getAccountId();

           createNewCustomerDB(scrollAcctIdRef);

           Customer c = findLastCustIdDB();
           setCustIdLast(c);
           scrollCustIdRef = c.getCustomerId();
           displayAllFields(c, sa);                     

           firstNameField.setEditable(true);
           lastNameField.setEditable(true);
           addressField.setEditable(true);
           pNumField.setEditable(true);
           applyCustUpdate.setVisible(true);           
        }
    }
    // prompt to ask if user is certain they wish to intiate an update to customer details
    // makes Update Customer Details button visible
    private void updateCustomerPrompt()
    {
        if(scrollCustIdRef == 0)
        {
            SwingValidator sv = new SwingValidator(this);
            sv.needOpenAccount();
        }
        else
        {
            SwingValidator sv0 = new SwingValidator(this);
            String msg = "Update Customer Details?";
            int choice = sv0.newSearch(msg);
            if (choice == 0)
            {
                firstNameField.setEditable(true);
                lastNameField.setEditable(true);
                addressField.setEditable(true);
                pNumField.setEditable(true);
                applyCustUpdate.setVisible(true);            
            }
        }
    }
    
    // calls updateCustomerDB() of AccountsDB to use SQL and update a row in the customers table
    // hides Update Customer Details button after clicked
    private void updateCustomer()
    {
       SwingValidator sv = new SwingValidator(this);
       if (sv.hasValues(firstNameField, "Customer First Name") && sv.hasValues(lastNameField, "Customer Last Name")
               && sv.hasValues(addressField, "Address") && sv.hasValues(pNumField, "Phone Number"))
       {                          
            String fN = firstNameField.getText();
            String lN = lastNameField.getText();
            String addy = addressField.getText();
            String pNum = pNumField.getText();

            updateCustomerDB(fN, lN, addy, pNum, scrollCustIdRef);

            firstNameField.setEditable(false);
            lastNameField.setEditable(false);
            addressField.setEditable(false);
            pNumField.setEditable(false);
            applyCustUpdate.setVisible(false);

            Customer updatedC = scrollCustomerDB(scrollCustIdRef);                        
            SavingAccount updatedSa = getAccountObj(scrollAcctIdRef);
            displayAllFields(updatedC, updatedSa);                      
       }
    }

    // prompt to ask if user is certain they wish to intiate adding a new customer row associated with the existing accountId displayed
    // also changes appropiate customerId tracker to reflect the new account as opened while using AccountId tracker to keep account info consistent
    // Makes the Add Customer button visible and prepares a blank customer row/object for addCustomer() to update
    private void addCustomerPrompt()
    {
        if(scrollCustIdRef == 0)
        {
            SwingValidator sv = new SwingValidator(this);
            sv.needOpenAccount();
        }
        else
        {
            SwingValidator sv0 = new SwingValidator(this);
            String msg = "Add Customer to this Account?";
            int choice = sv0.newSearch(msg);
            if (choice == 0)
            {
                resetCustomerFields();
                firstNameField.setEditable(true);
                lastNameField.setEditable(true);
                addressField.setEditable(true);
                pNumField.setEditable(true);
                applyAddCust.setVisible(true);
                createNewCustomerDB(scrollAcctIdRef);            
                Customer c = findLastCustIdDB();
                scrollCustIdRef = c.getCustomerId();               
                setCustIdLast(c);
                SavingAccount sa = getAccountObj(scrollAcctIdRef);            
                displayAllFields(c, sa);
            }
        }
    }
   
    // calls updateCustomerDB() from AccountsDB when Update Customer Details is clicked to use SQL to update
    // any necessary changes to a row
    // hides Add Customer button
    private void addCustomer()
    {           
        SwingValidator sv = new SwingValidator(this);
        if (sv.hasValues(firstNameField, "Customer First Name") && sv.hasValues(lastNameField, "Customer Last Name")
                && sv.hasValues(addressField, "Address") && sv.hasValues(pNumField, "Phone Number"))
        {   
            String fN = firstNameField.getText();
            String lN = lastNameField.getText();
            String addy = addressField.getText();
            String pNum = pNumField.getText();

            updateCustomerDB(fN, lN, addy, pNum, scrollCustIdRef);

            firstNameField.setEditable(false);
            lastNameField.setEditable(false);
            addressField.setEditable(false);
            pNumField.setEditable(false);
            applyAddCust.setVisible(false);

            Customer updatedC = scrollCustomerDB(scrollCustIdRef);                            
            SavingAccount updatedSa = getAccountObj(scrollAcctIdRef);
            displayAllFields(updatedC, updatedSa);
        }                                      
    }

    // prompt for initating a deposit, unlocks Add Amount button and deposit/withdraw textfield    
    private void increaseBalPrompt()
    {
        if(scrollCustIdRef == 0)
        {
            SwingValidator sv = new SwingValidator(this);
            sv.needOpenAccount();
        }
        else
        {
            SwingValidator sv0 = new SwingValidator(this);
            String msg = "Increase balance?";
            int choice = sv0.newSearch(msg);
            if (choice == 0)
            {
                increaseBal.setVisible(true);
                updateBalField.setEditable(true);                
                updateBalField.setText(null);
            }
        }
    }    
       
    // does the relevant math for obtaining new balance after a deposit, hides Add Amount button
    // and clears the deposit/withdraw field as well as shows new balance 
    private void increaseBal()
    {
       SwingValidator sv = new SwingValidator(this);
       if (sv.hasDoubleValues(updateBalField, "Balance"))
       {              
            double newAmt = Double.parseDouble(updateBalField.getText());
            SavingAccount sa = getAccountObj(scrollAcctIdRef);
            double oldAmt = sa.getBalance();            
            double bal = oldAmt + newAmt;
            String newBal = Double.toString(bal);            
            
            updateAccountBalDB(newBal, scrollAcctIdRef);
                        
            increaseBal.setVisible(false);
            updateBalField.setEditable(false);
            updateBalField.setText(null);

            Customer updatedC = scrollCustomerDB(scrollCustIdRef);                        
            SavingAccount updatedSa = getAccountObj(scrollAcctIdRef);
            displayAllFields(updatedC, updatedSa);                      
       }
    }
    
    // prompt for initating a withdrawal, makes visible the Subtract Amount button and unlocks the deposit/withdraw textfield
    private void decreaseBalPrompt()
    {
        if(scrollCustIdRef == 0)
        {
            SwingValidator sv = new SwingValidator(this);
            sv.needOpenAccount();
        }
        else
        {
            SwingValidator sv0 = new SwingValidator(this);
            String msg = "Decrease balance?";
            int choice = sv0.newSearch(msg);
            if (choice == 0)
            {
                decreaseBal.setVisible(true);
                updateBalField.setEditable(true);                
                updateBalField.setText(null);
            }
        }
    }

    // does the relevant math for obtaining new balance after a withdrawal, hides Subtract Amount button
    // and clears the deposit/withdraw field as well as shows new balance
    private void decreaseBal()
    {
       SwingValidator sv = new SwingValidator(this);
       if (sv.hasDoubleValues(updateBalField, "Balance"))
       {              
            double newAmt = Double.parseDouble(updateBalField.getText());
            SavingAccount sa = getAccountObj(scrollAcctIdRef);
            double oldAmt = sa.getBalance();            
            double bal = oldAmt - newAmt;
            String newBal = Double.toString(bal);
            
            updateAccountBalDB(newBal, scrollAcctIdRef);
                        
            decreaseBal.setVisible(false);
            updateBalField.setEditable(false);
            updateBalField.setText(null);
            
            Customer updatedC = scrollCustomerDB(scrollCustIdRef);                        
            SavingAccount updatedSa = getAccountObj(scrollAcctIdRef);
            displayAllFields(updatedC, updatedSa);                      
       }
    }
    
    // prompt for calculating monthly interest gained
    // allows months field to accept input and makes visible the calculate button 
    // displays results in Calculated Interest textfield
    private void calcIntPrompt()
    {
        if(scrollCustIdRef == 0)
        {
            SwingValidator sv = new SwingValidator(this);
            sv.needOpenAccount();
        }
        else
        {
            SwingValidator sv0 = new SwingValidator(this);
            String msg = "Calculate Monthly Savings?";
            int choice = sv0.newSearch(msg);
            if (choice == 0)
            {
                monthIntRateField.setEditable(true);
                calcMonth.setVisible(true);
            }
        }
    }
    
    // allows user to enter number of months in a year from 1 to 12 to calculate monthly gains for the year
    private void calcMonthInt()
    {
        SwingValidator sv0 = new SwingValidator(this);
            if (sv0.yearWithinRange(monthIntRateField, "Month") == true && sv0.hasIntValues(monthIntRateField, "Month"))
            {
                int months = Integer.parseInt(monthIntRateField.getText());
                SavingAccount sa = getAccountObj(scrollAcctIdRef);
                double bal = sa.getBalance();
                double intRate = sa.getInterestRate();
                double calcMontlyIntRate = (bal * intRate)/12;
                double calcMonthlyGain = calcMontlyIntRate * months;
                String newMonthRate = NumberFormat.getCurrencyInstance().format(calcMonthlyGain);
                calcIntRateField.setText(newMonthRate);
                monthIntRateField.setEditable(false);
                calcMonth.setVisible(false);
            }
    }
    
    // prompt to change the account interest rate
    private void changeIntPrompt()
    {
        if(scrollCustIdRef == 0)
        {
            SwingValidator sv = new SwingValidator(this);
            sv.needOpenAccount();
        }
        else
        {
            SwingValidator sv0 = new SwingValidator(this);
            String msg = "Change Account Interest Rate?";
            int choice = sv0.newSearch(msg);
            if (choice == 0)
            {
                accIntRateField.setEditable(true);
                okNewIntRate.setVisible(true);
            }
        }
    }
    
    // applies interest rate change and close related textfield and hides related button
    private void applyNewRate()
    {
        SwingValidator sv = new SwingValidator(this);
            if (sv.hasDoubleValues(accIntRateField, "Interest Rate"))
            {                                            
                String newIr = accIntRateField.getText();
                updateAccountIrateDB(newIr, scrollAcctIdRef);
                
                Customer updatedC = scrollCustomerDB(scrollCustIdRef);                        
                SavingAccount updatedSa = getAccountObj(scrollAcctIdRef);
                displayAllFields(updatedC, updatedSa);
                
                accIntRateField.setEditable(false);
                okNewIntRate.setVisible(false);
            }
    }
    
    // method to display all account details
    private void displayAllFields(Customer c, SavingAccount sa)
    {
        firstNameField.setText(c.getFirstName());
        lastNameField.setText(c.getLastName());
        addressField.setText(c.getAddress());
        pNumField.setText(c.getPhoneNum());
        acctNumField.setText(Integer.toString(sa.getAccountId()));       
        double b = (sa.getBalance());
        String bal = NumberFormat.getCurrencyInstance().format(b);
        balanceField.setText(bal);
        double ir = (sa.getInterestRate());
        String iRate = NumberFormat.getPercentInstance().format(ir);
        accIntRateField.setText(iRate);       
    }

    // method to reset all fields (usually before search or opening account)
    private void resetBlankFields()
    {
        firstNameField.setText(null);
        lastNameField.setText(null);
        addressField.setText(null);
        pNumField.setText(null);
        acctNumField.setText(null);
        balanceField.setText(null);
        updateBalField.setText(null);   
        monthIntRateField.setText(null);
        calcIntRateField.setText(null);
        accIntRateField.setText(null);
    }
    
    // method to reset customer fields
    private void resetCustomerFields()
    {
        firstNameField.setText(null);
        lastNameField.setText(null);
        addressField.setText(null);
        pNumField.setText(null);
    }
    
    // clearing the interest fields for calculating monthly gains
    private void resetInterestFields()
    {
        calcIntRateField.setText(null);
        monthIntRateField.setText(null);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            BankAppFrame frame = new BankAppFrame();
            frame.setVisible(true);
        });
    }     
    
}
