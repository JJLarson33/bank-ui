/**
 *
 * @author Larson, J. 2022
 */
package banking.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import banking.business.*;
        
public class AccountsDB {

    private static Connection getConnection() throws SQLException {
        String dbUrl = "jdbc:sqlite:accounts.sqlite";
        Connection connection = DriverManager.getConnection(dbUrl);
        return connection;
    }

// creation of new SavingAccounts row (auto-generating/incrementing a new accountID)
public static boolean createNewAccountDB()
{
   try (Connection connection = getConnection();
       PreparedStatement ps = connection.prepareStatement("INSERT INTO SavingAccounts (Balance, InterestRate) VALUES (0.0, 0.10)"))
    {
        ps.executeUpdate();
        return true;
    }
    catch (SQLException e)
    {
        System.err.println(e);
        System.out.println("First Null in createNewAccountDB");
        return false;
    }     
}

// creation of new Customers row (uses a given accountId)
// auto-generates/increments customerId and is assigned the given accountId
public static boolean createNewCustomerDB(int acId)
{
    try (Connection connection = getConnection();
         PreparedStatement ps = connection.prepareStatement("INSERT INTO Customers (FirstName, LastName, Address, PhoneNumber, AccountID)"
                 + " VALUES (null, null, null, null, ?)"))
    {
        ps.setInt(1, acId);
        ps.executeUpdate();         
        return true;           
    }   
    catch (SQLException e)
    {
        System.err.println(e);
        System.out.println("First Null in createNewCustomerDB");
        return false;
    }    
}

// UPDATES only for customer row and only for firstname, lastname, address, and phone number
public static boolean updateCustomerDB(String fN, String lN, String address, String phoneNum, int cId)
{
    try (Connection connection = getConnection();
         PreparedStatement ps = connection.prepareStatement("UPDATE Customers SET FirstName = ?, LastName = ?, Address = ?, PhoneNumber = ? "
                 + "WHERE CustomerID = ?"))
    {
        ps.setString(1, fN);
        ps.setString(2, lN);
        ps.setString(3, address);
        ps.setString(4, phoneNum);
        ps.setInt(5, cId);
        ps.executeUpdate();        
        return true;
    }
    catch (SQLException e)
    {
        System.err.println(e);
        System.out.println("First Null in updateCustomerDB");
        return false;
    }
}

// expects a firstname and lastname for action to return a result
public static Customer searchCustomerDB(String fN, String lN)
{               
    try (Connection connection = getConnection();
         PreparedStatement ps = connection.prepareStatement("SELECT * FROM Customers WHERE FirstName = ? AND LastName = ? "))
    {   
        ps.setString(1, fN);
        ps.setString(2, lN);
        ResultSet rs = ps.executeQuery();
                
        if(rs.next())
        {
            int custId    = rs.getInt("CustomerID");
            String firstN = rs.getString("FirstName");
            String lastN  = rs.getString("LastName");
            String addy   = rs.getString("Address");
            String pNum   = rs.getString("PhoneNumber");            
            int acctId    = rs.getInt("AccountID");
            
            Customer c = new Customer(custId, firstN, lastN, addy, pNum, acctId);
            rs.close();
            return c;
        }       
        else
        {
            rs.close();
            System.out.println("First Null in searchCustomerDB");
            return null;
        }
    }
    catch (SQLException e)
    {
        System.err.println(e);
        System.out.println("Second Null in searchCustomerDB");
        return null;
    }    
}


// part of Prev/Next methods in BankAppFrame
// Prev will send a customerId that has been subtracted by 1, and Next add by 1, a way of shuffling through the database
public static Customer scrollCustomerDB(int custIdRef)
{
    try (Connection connection = getConnection();
         PreparedStatement ps = connection.prepareStatement("SELECT * FROM Customers WHERE CustomerID = ? "))
    {   
        ps.setInt(1, custIdRef);
        Customer c;
        try (ResultSet rs = ps.executeQuery()) {
            int newCustId = rs.getInt("CustomerID");
            String firstN = rs.getString("FirstName");
            String lastN  = rs.getString("LastName");
            String addy   = rs.getString("Address");
            String pNum   = rs.getString("PhoneNumber");
            int acctId    = rs.getInt("AccountID");            
            c = new Customer(newCustId, firstN, lastN, addy, pNum, acctId);
        }
        return c;
        
    }
    catch (SQLException e)
    {
        System.err.println(e);
        System.out.println("Only Null in scrollCustomerDB");
        return null;
    }    
} 

// using SQL to always grab the last (newest) SavingAccounts (in database) row
// created before a customer obj in opening account for the sake of having an auto generated accountId to assign to the new customer
public static SavingAccount findLastAccountIdDB()
{
    try (Connection connection = getConnection();
         PreparedStatement ps = connection.prepareStatement("SELECT * FROM SavingAccounts ORDER BY AccountID DESC LIMIT 1"))        
    {
        try (ResultSet rs = ps.executeQuery()) 
        {           
            int acctId = rs.getInt("AccountID");
            double bal = rs.getDouble("Balance");
            double iR  = rs.getDouble("InterestRate");
            
            SavingAccount sa = new SavingAccount(acctId, bal, iR);
            rs.close();
            return sa;
        }        
    }
    catch (SQLException e)
    {
        System.err.println(e);        
        return null;
    }
}

// SQL to always grab the last row in customers table in the database
// used to load variable CustIdLast in BankAppFrame to keep track of the 'end' of the customer table
// primarily used with Prev button when scrolling 'UP' from row one, when the scrollCustIdRef is set to 0
public static Customer findLastCustIdDB()
{
    try (Connection connection = getConnection();
         PreparedStatement ps = connection.prepareStatement("SELECT * FROM Customers ORDER BY CustomerID DESC LIMIT 1"))        
    {
        try (ResultSet rs = ps.executeQuery()) 
        {
            int custId    = rs.getInt("CustomerID");
            String firstN = rs.getString("FirstName");
            String lastN  = rs.getString("LastName");
            String addy   = rs.getString("Address");
            String pNum   = rs.getString("PhoneNumber");            
            int acctId    = rs.getInt("AccountID");
            
            Customer c = new Customer(custId, firstN, lastN, addy, pNum, acctId);            
            rs.close();
            return c;
        }        
    }
    catch (SQLException e)
    {
        System.err.println(e);        
        return null;
    }    
}

// SQL to always grab the first row in customers table in database
// primarily used with Next button when scrolling 'DOWN' from the CustIdLast variable position (last row)
// or when scrollCustIdRef is set to 0
public static Customer findFirstCustIdDB()
{
    try (Connection connection = getConnection();
         PreparedStatement ps = connection.prepareStatement("SELECT * FROM Customers ORDER BY CustomerID ASC LIMIT 1"))        
    {
        try (ResultSet rs = ps.executeQuery()) 
        {
            int custId    = rs.getInt("CustomerID");
            String firstN = rs.getString("FirstName");
            String lastN  = rs.getString("LastName");
            String addy   = rs.getString("Address");
            String pNum   = rs.getString("PhoneNumber");            
            int acctId    = rs.getInt("AccountID");            
            
            Customer c = new Customer(custId, firstN, lastN, addy, pNum, acctId);            
            rs.close();
            return c;
        }        
    }
    catch (SQLException e)
    {
        System.err.println(e);        
        return null;
    }    
}

// Uses a accountId reference to obtain data from that associated row in the database
// and create a SavingAccount object with it to be handed to the calling method
public static SavingAccount getAccountObj(int acctId)
{
    try (Connection connection = getConnection();
         PreparedStatement ps = connection.prepareStatement("SELECT * FROM SavingAccounts WHERE AccountID = ? "))
    {
        ps.setInt(1, acctId);
        ResultSet rs = ps.executeQuery();
        if(rs.next())
        {
            double bal = rs.getDouble("Balance");
            double intR = rs.getDouble("InterestRate");            
            
            SavingAccount a = new SavingAccount(acctId, bal, intR);
            return a;
        }
        else
        {
            rs.close();
            System.out.println("First Null in getAccountObj");
            return null;
        }
    }
    catch (SQLException e)
    {
        System.err.println(e);
        System.out.println("Second Null in getAccountsObj");
        return null;
    }    
}

// SQL to update an existing account balance
// Expects a new balance total and an associated accountId 
public static boolean updateAccountBalDB(String bal, int acctId)
{
    try (Connection connection = getConnection();
         PreparedStatement ps = connection.prepareStatement("UPDATE SavingAccounts SET Balance = ? WHERE AccountID = ?"))
    {
        ps.setString(1, bal);
        ps.setInt(2, acctId);
        ps.executeUpdate();        
        return true;
    }
    catch (SQLException e)
    {
        System.err.println(e);
        System.out.println("First Null in updateAccountBalDB");
        return false;
    }
}

// SQL to change or update the interest rate for whatever reason might be needed
// Expects a new interest rate number and the associated accountId
public static boolean updateAccountIrateDB(String iRate, int acctId)
{
    try (Connection connection = getConnection();
         PreparedStatement ps = connection.prepareStatement("UPDATE SavingAccounts SET InterestRate = ? WHERE AccountID = ?"))
    {
        ps.setString(1, iRate);
        ps.setInt(2, acctId);
        ps.executeUpdate();        
        return true;
    }
    catch (SQLException e)
    {
        System.err.println(e);
        System.out.println("First Null in updateAccountIrateDB");
        return false;
    }
}




}           





