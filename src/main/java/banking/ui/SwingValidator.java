/**
 *
 * @author Larson, J. 2022
 */
package banking.ui;

import java.awt.Component;
import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;

public class SwingValidator {
    private final Component parentComponent;
    
    public SwingValidator(Component parent)
    {
        this.parentComponent = parent;
    }

private void showErrorDialog(String message)
{
    JOptionPane.showMessageDialog(parentComponent, message,
            "Invalid Entry", JOptionPane.ERROR_MESSAGE);    
}

public void noSuchRecordFound()
{
    showErrorDialog("No such Customer Record found.");
}

public void needOpenAccount()
{
    showErrorDialog("Navigate to an existing account first.");
}

public boolean hasValues(JTextComponent c, String fieldName)
{
    if (c.getText().isEmpty())
    {
        showErrorDialog(fieldName + " cannot be empty.");        
        return false;
    }
    else
    {
        return true;
    }
}

public boolean hasDoubleValues (JTextComponent c, String fieldName)
{
    try
    {
        Double.parseDouble(c.getText());
        return true;
    }
    catch (NumberFormatException e)
    {
        showErrorDialog(fieldName + " needs a valid number.");
        return false;
    }
}

public boolean hasIntValues (JTextComponent c, String fieldName)
{
    try
    {
        Integer.parseInt(c.getText());
        return true;
    }
    catch (NumberFormatException e)
    {
        showErrorDialog(fieldName + " needs a valid number.");
        return false;
    }
}

public boolean yearWithinRange (JTextComponent c, String fieldName)
{    
    int testRange = Integer.parseInt(c.getText());
    if (testRange < 1 || testRange > 12)
    {
        showErrorDialog(fieldName + " can only be 1 through 12.");
        return false;
    }
    else
    {
        return true;
    }    
}

public boolean preventScrollBeforeSearch(JTextComponent c1, JTextComponent c2)
{   
    if (c1.getText().isEmpty() || c2.getText().isEmpty())
    {
        showErrorDialog("Fields cannot be empty.");
        return false;
    }
    else if (c1.getText().isEmpty() == false && c2.getText().isEmpty() == false)
    {
        String message = "Click Search\\Apply\\Add Button";
        showErrorDialog(message);
        return false;
    }    
    else
    {
        return true;
    }
}

    
public int newSearch(String message)
{    
    int choice = JOptionPane.showConfirmDialog(parentComponent, message);            
    return choice;
}
 
    
    
    
}   
