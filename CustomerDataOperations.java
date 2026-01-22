package ATM;

import java.util.ArrayList;

public class CustomerDataOperations {

    private int Acc_No;
    private String Account_User_Name;
    private int Account_Pin;
    private int Account_Balance;
    private int Transaction_ID = 0;

    CustomerDataOperations(int Accno, String Username, int Pin, int AccountBalance) {
        this.Acc_No = Accno;
        this.Account_User_Name = Username;
        this.Account_Pin = Pin;
        this.Account_Balance = AccountBalance;
    }

    CustomerDataOperations() {}

    public int getUserAccNo() {
        return Acc_No;
    }

    public String getUserName() {
        return Account_User_Name;
    }

    public synchronized int getUserBalance() {
        return Account_Balance;
    }

    public synchronized void setUserbalance(int Updatedbalance) {
        Account_Balance = Updatedbalance;
    }

    public synchronized int IncrementTransactionID() {
        Transaction_ID++;
        return Transaction_ID;
    }

    public static void ShowUserdetails(ArrayList<CustomerDataOperations> UserDetails) {
        System.out.printf("%-10s %-15s %-15s\n", "ID", "Name", "Balance");
        System.out.println("-----------------------------------------------------------");
        for (CustomerDataOperations c : UserDetails) {
            System.out.printf("%-10d %-15s %-15d\n", c.Acc_No, c.Account_User_Name, c.Account_Balance);
        }
        System.out.println();
    }

    public static boolean UserAccountValidation(int accno, int pin, ArrayList<CustomerDataOperations> UserDetails) {
        for (CustomerDataOperations details : UserDetails) {
            if (details.Acc_No == accno && details.Account_Pin == pin) {
                System.out.println("Logged-in Successfully");
                return true;
            }
        }
        System.out.println("Invalid Credentials");
        return false;
    }

    public static CustomerDataOperations getUserObjusingAccountNumber(int accountnumber, ArrayList<CustomerDataOperations> UserDetails) {
        CustomerDataOperations UserObj = null;
        for (CustomerDataOperations data : UserDetails) {
            if (data.Acc_No == accountnumber) {
                UserObj = data;
            }
        }
        if (UserObj != null) {
            return UserObj;
        } else {
            System.out.println("Invalid Account Number");
            return null;
        }
    }
}
