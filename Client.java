//Smit Prajapati
package com.company;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client
{

    public static void main(String args[]) throws Exception
    {

        Socket s = new Socket("127.0.0.1",9999);
        OutputStreamWriter os = new OutputStreamWriter(s.getOutputStream());
        PrintWriter out = new PrintWriter(os);

        BufferedReader bf = new BufferedReader(new InputStreamReader(s.getInputStream()));

        int temp1=0;
        do {
            System.out.println("******************** Welcome to Smart Bank ********************* \n1. Login as User \n2. Register as User \n3. Forgot Password \n4. Login as Admin \n5. Exit");
            Scanner sc = new Scanner(System.in);
            String in = sc.next();
            out.println(in);out.flush();

            String bool4 = bf.readLine();
            if("false".equals(bool4))
            {
                System.out.println("error");
                continue;
            }

            temp1=Integer.parseInt(in);

            if (temp1 == 1) {
                System.out.println("Please enter your UserName: ");
                String user_name = sc.next();
                out.println(user_name);out.flush();
                System.out.println("Please enter your Password: ");
                String password_user = sc.next();
                out.println(password_user);out.flush();

                String bool =bf.readLine();

                if ("true".equals(bool)) {

                    int choice;
                    do {
                        String temp = bf.readLine();
                        System.out.println("*********************************************************\n" +
                                "Welcome " + temp +
                                "\n*********************************************************");

                        System.out.println("1. Account details \n2. Deposit the amount \n3. Withdraw the amount \n4. History \n5. Request for delete account\n6. Search with date \n7. Pay \n8. Loan Policy \n9. Exit ");
                        System.out.println("Enter your choice: ");
                        choice = sc.nextInt();
                        out.println(choice);
                        out.flush();

                        if (choice > 9)
                            System.out.println("It is not a valid choice, Please enter number from 1 to 9.");

                        switch (choice) {
                            case 1:
                                System.out.println("Account Details:");
                                System.out.println("Name: " + bf.readLine());
                                System.out.println("Account ID: " +bf.readLine());
                                System.out.println("Type of Account: " + bf.readLine());
                                System.out.println("Amount: " + bf.readLine());
                                if("true".equals(bf.readLine()))
                                {
                                    System.out.println("Loan is borrowed from the bank");
                                    System.out.println("Loan Amount: " + bf.readLine());
                                    System.out.println("Loan Duration: " + bf.readLine()  + "months");
                                    System.out.println("EMI: " + bf.readLine());
                                    System.out.println("Loan Type: " + bf.readLine());


                                }
                                break;

                            case 2:

                                    String deposit_amount;

                                    System.out.println("Please enter the amount you want to enter: ");
                                     deposit_amount = sc.next(); //////////////////////////
                                    out.println(deposit_amount);
                                    out.flush();

                                    String bool3 = bf.readLine();
                                    if("true".equals(bool3))
                                    {
                                        System.out.println(deposit_amount + " are added to your bank account successfully");

                                        System.out.println("Your Current bank Balance is: " + bf.readLine());
                                    }
                                    else
                                    {
                                        System.out.println("Incorrect Amount, Please enter valid number.");
                                    }
                                    break;


                            case 3:

                                System.out.println("Your current Bank Balance is: " + bf.readLine());
                                String Withdraw_amount;


                                    System.out.println("Please enter the amount you want to Withdraw: ");
                                     Withdraw_amount = sc.next();
                                    out.println(Withdraw_amount);
                                    out.flush();

                                    String bool2 = bf.readLine();
                                    if("true".equals(bool2))
                                    {
                                        System.out.println("Please Take " + Withdraw_amount);

                                        System.out.println("Your current Bank balance is: " + bf.readLine());
                                    }
                                    else
                                    {
                                        System.out.println("Invalid amount, Please enter valid amount");
                                    }

                                 break;

                            case 4:

                                String his = bf.readLine();
                                String[] partsofstring = his.split(",");
                                for(String n : partsofstring)
                                {
                                    System.out.println(n);
                                }

                                break;


                            case 5:
                                System.out.println("Are you sure about deleting your account \n1. Yes \n2. No");
                                int c = sc.nextInt();

                                if(c==1)
                                {
                                    out.println("1");out.flush();
                                    System.out.println("Request has been sent to admin...");
                                }

                                break;


                            case 6:
                                System.out.println("starting date(in yyyy-mm-dd): ");
                                String start_date = sc.next();out.println(start_date);out.flush();
                                System.out.println("ending date(in yyyy-mm-dd): ");
                                String end_date = sc.next();out.println(end_date);out.flush();

                                String[] partofhistory = bf.readLine().split(",");
                                for(String n : partofhistory)
                                {
                                    System.out.println(n);
                                }

                                break;

                            case 7:
                                System.out.println("Welcome to our pay plan");
                                System.out.println("your current bank balance is: " + bf.readLine());

                                System.out.println("please enter current account id to pay that account ");
                                System.out.print("Account ID: ");
                                String pay_account_id = sc.next();
                                out.println(pay_account_id);out.flush();


                                if("true".equals(bf.readLine()))
                                {
                                    String pay_uname = bf.readLine();
                                    System.out.println("You are Paying to " + pay_uname);
                                    System.out.println("1. Yes \n2. No");
                                    int choise1 = sc.nextInt();
                                    out.println(choise1);out.flush();
                                    if(choise1==2)break;
                                    System.out.println("please enter amount you want to pay ");
                                    int pay_amount = sc.nextInt();
                                    out.println(pay_amount);out.flush();
                                    if("true".equals(bf.readLine()))
                                    {System.out.println("invalid amount");break;}
                                    System.out.println("You are paying " + pay_amount + " to " + pay_uname);
                                    System.out.println("1. YES \n2. NO");
                                    int choise2 = sc.nextInt();
                                    out.println(choise2);out.flush();
                                    if(choise2==2)break;

                                    System.out.println("your current account balance is: " + bf.readLine());

                                }
                                else
                                {
                                    System.out.println("There is no user with this account id,please try again");
                                }

                                break;

                            case 8:
                                System.out.println("Welcome to our loan system");
                                System.out.println("Your current bank account is: " + bf.readLine());
                                System.out.println("Please enter your MONTHLY gross income: ");
                                int monthly_income = sc.nextInt();out.println(monthly_income);out.flush();
                                System.out.println("Please enter your MONTHLY Debt Payments: ");
                                int monthly_dept = sc.nextInt();out.println(monthly_dept);out.flush();
                                long DTI = Math.round( 100* ((double) monthly_dept/(double)monthly_income) );   //dept to income ratio
                                System.out.println("Your Dept-To-Income(DTI) Ratio is: " + DTI + " %");                       //36% dti ia ideal
                                if(DTI < 40)
                                {
                                    System.out.println("You are eligible for our loan policy");
                                    System.out.println("Please enter loan type: ");
                                    System.out.println("1. Personal Loan \n2. Home Loan \n3. car Loan \n4. Gold Loan");
                                    int loan_type = sc.nextInt();out.println(loan_type);out.flush();
                                    switch (loan_type){
                                        case 1:
                                            int multiplier = 35;
                                            double annual_interest = 10;
                                            double monthly_interest = annual_interest/12 /100 ;
                                            //System.out.println(monthly_interest);
                                            System.out.println("You can get loan of 0 to " + monthly_income*multiplier);
                                            System.out.println("Please enter amount that you want for loan(<" + monthly_income*multiplier + ") :");
                                            int loan_amount = sc.nextInt();//out.println(loan_amount);out.flush();
                                            if(loan_amount >monthly_income*multiplier){ System.out.println("Incorrect amount, Please enter valid amount");break;}
                                            System.out.println("we can provide you ₹" +  loan_amount + " at "+annual_interest + "% annual interest.");
                                            int agree,month;long EMI;
                                            do {
                                                System.out.println("Select time period to return money");
                                                month = sc.nextInt();
                                                double value = Math.pow(1+monthly_interest,month);
                                                EMI = Math.round(  (loan_amount * monthly_interest * value) / (value-1)  );
                                                System.out.println("if you borrow ₹"+loan_amount+" at 10.5% annual interest for a period of "+month+" months, then EMI: " + EMI);
                                                System.out.println("1. Yes, I want this loan and i am agree with this EMI \n2. Change Time period \n3. No, I don't want any loan");
                                                agree = sc.nextInt();
                                            }while (agree ==2);

                                            long DTI1 = Math.round( 100* ((double) EMI/(double)monthly_income) );   //dept to income ratio

                                            if(DTI1 > 40){
                                                System.out.println("Your DTI ratio is high in this EMI,Change amount or time");out.println("false");out.flush();
                                            }
                                            else if(agree==1)
                                            {
                                                out.println("true");out.flush();

                                                out.println(loan_amount);out.flush();
                                                out.println(month);out.flush();
                                                out.println(EMI);out.flush();
                                            }
                                            else
                                            {out.println("false");out.flush();}
                                            break;
                                        default:
                                            System.out.println("Please go to your nearest bank,and bring all needed documents");
                                    }

                                }
                                else
                                {
                                    System.out.println("You are not eligible for out bank loan plan, your DTI(debt to income) ratio is too high.");
                                }



                        }
                    } while (choice != 9);
                } else {
                    System.out.println("Sorry there is no account for this password and User Name");
                    System.out.println("Try again or open new account");
                }

            } else if(temp1==2){    //Register for user


                System.out.print("Please enter your UserName: ");
                String username = sc.next();
                out.println(username);out.flush();

                String bool1 = bf.readLine();

                if ("true".equals(bool1))
                {
                    System.out.println("Already exists UserName, Please change user name");

                }
                else
                {

                    System.out.print("Please enter your Password: ");
                    String password = sc.next();
                    out.println(password);out.flush();

                    System.out.println("***** Your account has been created successfully ***** \nPlease Enter Your valid bank details");



                    System.out.print("Please enter your Name: ");
                    Scanner inp = new Scanner(System.in);
                    String name;
                    name= inp.nextLine();
                    out.println(name);out.flush();



                System.out.println("Choose Your Account Type \n1. Saving Account \n2. Salary Account \n3. Fixed deposit account \n4. Recurring deposit account \n5. NRI accounts");
                String account_type = sc.next();
                out.println(account_type);
                out.flush();

                    String account_id = bf.readLine();

                    System.out.println("If you forgot your password this question answer will unlock your account");
                    System.out.println("Enter your childhood name: ");
                    String answer = sc.next();out.println(answer);out.flush();
                     System.out.println("Your account id is " + account_id);
                    System.out.println("Your Account is Activate now");

                }

            }
            else if (temp1==3)
            {
                System.out.println("Please enter your username: ");
                String usename_fpassword = sc.next();out.println(usename_fpassword);out.flush();

                if("true".equals(bf.readLine()))
                {
                    System.out.println("Enter your childhood name: ");
                    String answer_fpassword = sc.next();out.println(answer_fpassword);out.flush();
                    if("true".equals(bf.readLine()))
                    {
                        System.out.println("change password: ");
                         out.println(sc.next());out.flush();
                    }
                    else
                        System.out.println("wrong answer!!");
                }
                else
                {
                    System.out.println("there is no user with this username");
                }

            }
            else if (temp1==4)
            {
                System.out.println("UserName: ");
                String a_username = sc.next();
                out.println(a_username);out.flush();
                System.out.println("Password: ");
                String a_password = sc.next();
                out.println(a_password);out.flush();

                if("true".equals(bf.readLine()))
                {
                    int a_choice;
                    do {
                        System.out.println("Admin account is here...");
                        System.out.println("1. Show all requests \n2. Delete First user \n3. Exit");
                        a_choice = sc.nextInt();
                        out.println(a_choice);out.flush();
                        switch (a_choice) {

                            case 1:
                                System.out.println("Requests for delete account: " + bf.readLine());
                                break;
                            case 2:
                                System.out.println(bf.readLine() + " wants to delete Account");
                                System.out.println("Delete account: \n1. Yes \n2. No");
                                int k = sc.nextInt();
                                out.println(k);out.flush();

                                break;
                        }
                    }
                    while (a_choice !=3);

                }
                else{
                    System.out.println("incorrect");

                }


            }


        }
        while (temp1 != 5);


    }
}
