package testPrograms;

import org.junit.jupiter.api.Test;
import testPrograms.BankAccount;
import testPrograms.NotEnoughFundsException;
import static org.junit.jupiter.api.Assertions.*;

public class Activity2 {

    @Test
    void notEnoughFunds() {
        // Create an object for BankAccount class
        BankAccount account = new BankAccount(19);
 
        // Assertion for exception
        assertThrows(NotEnoughFundsException.class, () -> account.withdraw(20),
                "Balance must be greater than amount of withdrawal");
    }
 
    @Test
    void enoughFunds() {
        // Create an object for BankAccount class
        BankAccount account = new BankAccount(100);
    
        // Assertion for no exceptions
        assertDoesNotThrow(() -> account.withdraw(100));
    }
}