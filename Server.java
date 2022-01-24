//Smit Prajapati
package com.company;

import java.net.*;
import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;


public class Server
{

    public static class bank_details{
        String Name;
        String Password;
        String Type;
        String Account_ID;
        String answer_foorgot_password;
        boolean loan_running;
        int Amount;
        ArrayList<String> details = new ArrayList<>();
        ArrayList<String> Date = new ArrayList<>();

        public bank_details(){
            //details.add("0 initial amount");
            Amount=0;
            loan_running = false;
        }
    }

    public static class loan_details{
        String Loan_type;
        int Loan_amount;
        int Loan_Duration;
        double Loan_interest;
        long Loan_EMI;
    }

    public static void main(String args[]) throws Exception
    {

        System.out.println("Server is started !!");      //start socket
        ServerSocket ss = new ServerSocket(9999);
        System.out.println("waiting for clients...");

        Socket s =ss.accept();
        System.out.println("client is joined");

        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));

        OutputStreamWriter os = new OutputStreamWriter(s.getOutputStream());
        PrintWriter out = new PrintWriter(os);



        HashMap<String,bank_details> map = new HashMap<>();        //data structures
        HashMap<String,loan_details> loan_map = new HashMap<>();
        Queue<String> delete_account = new LinkedList<>();


        String filePath_password = "password.txt";          //ReadFile
        String filePath_details = "details.txt";
        String filePath_history = "history.txt";
        String filePath_deleteaccount = "delete.txt";
        String filePath_loan = "loan_details.txt";

        BufferedReader br1 = null;
        BufferedReader br2 = null;
        BufferedReader br3 = null;
        BufferedReader br4 = null;
        BufferedReader br5 = null;


        try {


            File file1 = new File(filePath_password);
            File file2 = new File(filePath_details);
            File file3 = new File(filePath_history);
            File file4 = new File(filePath_deleteaccount);
            File file5 = new File(filePath_loan);

            br1 = new BufferedReader(new FileReader(file1));
            br2 = new BufferedReader(new FileReader(file2));
            br3 = new BufferedReader(new FileReader(file3));
            br4 = new BufferedReader(new FileReader(file4));
            br5 = new BufferedReader(new FileReader(file5));

            String line1 = null;
            String line2 = null;
            String line3 = null;
            String line4 = null;
            String line5 = null;


            while ((line1 = br1.readLine()) != null) {
                line2 = br2.readLine();
                line3 = br3.readLine();
                bank_details temp_variable = new bank_details();

                String[] parts1 = line1.split(":");
                String[] parts2 = line2.split(":");
                String[] parts3 = line3.split(":");


                String u_username = parts1[0].trim();
                temp_variable.Password = parts1[1].trim();


                temp_variable.Name = parts2[1].trim();
                temp_variable.Type = parts2[2].trim();
                temp_variable.Amount = parseInt(parts2[3].trim());
                temp_variable.Account_ID = (parts2[4].trim());
                temp_variable.answer_foorgot_password = parts2[5].trim();
                temp_variable.loan_running = parseBoolean(parts2[6].trim());

                int arry_size = parseInt(parts3[1].trim());
                ArrayList<String> dummy_details = new ArrayList<>();   //For History
                ArrayList<String> dummy_date = new ArrayList<>();      //For Search
                int i;
                for(i=2; i<(2+ arry_size); i++)
                {
                    dummy_details.add(parts3[i].trim());
                }
                System.out.println(dummy_date);
                for(;i<parts3.length;i++)
                {
                    dummy_date.add(parts3[i].trim());
                }

                temp_variable.details =dummy_details;
                temp_variable.Date = dummy_date;
                if (!u_username.equals(""))
                    map.put(u_username, temp_variable);


            }
            while ((line4 = br4.readLine()) != null )
            {
                delete_account.add(line4);
            }
            while ((line5 = br5.readLine()) != null )
            {
                String[] part = line5.split(":");
                loan_details temp_loan_details = new loan_details();
                temp_loan_details.Loan_amount = parseInt(part[1].trim());
                temp_loan_details.Loan_Duration = parseInt(part[2].trim());
                temp_loan_details.Loan_EMI = parseInt(part[3].trim());
                temp_loan_details.Loan_interest = Double.parseDouble(part[4].trim());
                temp_loan_details.Loan_type = part[5].trim();

                if(!part[0].trim().equals(""))
                    loan_map.put(part[0].trim(),temp_loan_details);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }



        int temp1=0;
        do {
            System.out.println("******************** Welcome to Smart Bank ********************* \nLogin as User-> 1 \nRegister as User -> 2 \nForgot Password ->3 \nLogin as Admin -> 4 \nExit -> 5");
            Scanner sc = new Scanner(System.in);
            String in = br.readLine();
            try
            {
                parseInt(in);
                temp1 = parseInt(in);
                out.println("true");
                out.flush();

            }
            catch (NumberFormatException e)
            {
                System.out.println("Please enter valid number.");
                out.println("false");
                out.flush();
                continue;
            }

            if (temp1 == 1) {   //Login for already registered user

                String user_name = br.readLine();
                String password_user = br.readLine();

                if (map.containsKey(user_name) && map.get(user_name).Password.equals(password_user)) {
                    out.println("true");out.flush();

                    bank_details user_info;
                    user_info = map.get(user_name);

                    int choice;
                    do {
                        out.println(user_info.Name);
                        out.flush();
                        //System.out.println("Welcome " + user_info.Name);

                        //System.out.println("1. Account details \n 2. Deposit the amount \n 3. Withdraw the amount \n 4.History \n 5.Request for delete account\n 6.Search with date \n 7.Exit ");
                        //System.out.println("Enter your choice: ");
                        choice =Integer.parseInt(br.readLine());

                        switch (choice) {
                            case 1:  //Details
                                System.out.println("Account Details:");
                                out.println(user_info.Name);out.flush();
                                out.println(user_info.Account_ID);out.flush();
                                out.println(user_info.Type);out.flush();
                                out.println(user_info.Amount);out.flush();

                                if(user_info.loan_running)
                                {
                                    out.println("true");out.flush();
                                    out.println(loan_map.get(user_name).Loan_amount);out.flush();
                                    out.println(loan_map.get(user_name).Loan_Duration);out.flush();
                                    out.println(loan_map.get(user_name).Loan_EMI);out.flush();
                                    out.println(loan_map.get(user_name).Loan_type);out.flush();
                                }
                                else
                                {out.println("false");out.flush();}

                                break;

                            case 2: // Deposit amount

                                int deposit_amount=0;
                                String input = br.readLine(); //enter amount to deposit
                                    try
                                    {
                                        parseInt(input);
                                        deposit_amount = parseInt(input);
                                        out.println("true");
                                        out.flush();

                                        user_info.Amount += deposit_amount;
                                        out.println(user_info.Amount);out.flush();

                                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH-mm-ss");
                                        String mytime = LocalTime.now().format(dtf);
                                        LocalDate mydate = LocalDate.now();
                                        user_info.details.add(mydate + " " + mytime + " " + deposit_amount + "+");
                                        String mydate_string = mydate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                                        user_info.Date.add(mydate_string);


                                    }
                                    catch (NumberFormatException e)
                                    {
                                        out.println("false");out.flush();
                                        //System.out.println("Incorrect Amount, Please enter valid number.");
                                    }

                                break;

                            case 3: // Withdraw Amount
                                out.println(user_info.Amount);out.flush();
                                int Withdraw_amount;
                                String Withdraw_string;

                                    //System.out.println("Please enter the amount you want to Withdraw: ");
                                    Withdraw_string = br.readLine();
                                    try
                                    {
                                        parseInt(Withdraw_string);
                                        Withdraw_amount = parseInt(Withdraw_string);
                                        if(Withdraw_amount >= user_info.Amount)
                                        {
                                            out.println("false");out.flush();
                                            //System.out.println("Invalid amount, Please enter valid amount");
                                        }
                                        else
                                        {
                                            out.println("true");out.flush();
                                            user_info.Amount -= Withdraw_amount;

                                            out.println(user_info.Amount);out.flush();

                                            DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("HH-mm-ss");
                                            String mytimeW = LocalTime.now().format(dtf1);
                                            LocalDate mydateW = LocalDate.now();
                                            String mydateW_string = mydateW.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                                            user_info.Date.add(mydateW_string);
                                            user_info.details.add(mydateW + " " + mytimeW + " " + Withdraw_amount + "-");

                                        }
                                    }
                                    catch (NumberFormatException e)
                                    {
                                        out.println("false");out.flush();
                                        //System.out.println("Incorrect Amount, Please enter valid number.");
                                    }


                                break;

                            case 4: //History (logs)
                                ArrayList<String> user_details = new ArrayList<>(user_info.details);

                                out.println(user_details);out.flush();

                                for(String dump_veri : user_details)
                                    System.out.println(dump_veri);

                                break;

                            case 5: //Request Delete
                                //System.out.println("Are you sure about deleting your account \n Yes -> 1 \n No ->2");
                                String c = br.readLine();
                                if(c.equals("1"))
                                    delete_account.add(user_name);
                                break;

                            case 6://Search History

                                String start_date = br.readLine();
                                String end_date = br.readLine();

                                try {
                                    Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(start_date);
                                    Date date2=new SimpleDateFormat("yyyy-MM-dd").parse(end_date);

                                    if(date1.after(date2)){out.println("Invalid date,please enter valid date");out.flush(); break;}

                                    ArrayList<String> history_array =  new ArrayList<>();
                                    for(int i=0;i<user_info.Date.size();i++)
                                    {
                                        Date datex=new SimpleDateFormat("yyyy-MM-dd").parse(user_info.Date.get(i));
                                        if(datex.after(date1) && datex.before(date2))
                                        {
                                            history_array.add(user_info.details.get(i));
                                        }
                                    }
                                    out.println(history_array);out.flush();
                                }
                                catch (Exception e)
                                {
                                    out.println("Invalid date,please enter valid date");out.flush();
                                }

                                break;


                            case 7: //Pay

                                out.println(user_info.Amount);out.flush();

                                String pay_account_id = br.readLine(); //Account ID
                                String pay_account_username=null;
                                for (Map.Entry<String, bank_details> entry : map.entrySet()) { //check if exists

                                    if(pay_account_id.equals(entry.getValue().Account_ID))
                                    {
                                        pay_account_username = entry.getKey();
                                        break;
                                    }
                                }
                                if(pay_account_username!=null)
                                {
                                    out.println("true");out.flush();
                                    out.println(map.get(pay_account_username).Name);out.flush();

                                    int choise1 = parseInt(br.readLine());  //1.Yes     2.No
                                    if(choise1==2)break;
                                    //System.out.println("please enter amount you want to pay ");
                                    int pay_amount = parseInt(br.readLine());
                                    if(pay_amount>=user_info.Amount)
                                    {out.println("true");out.flush();System.out.println("invalid amount");break;}
                                    out.println("false");out.flush();

                                    int choise2 = parseInt(br.readLine()); //1.Yes   2.No
                                    if(choise2==2)break;

                                    user_info.Amount-=pay_amount;
                                    map.get(pay_account_username).Amount+=pay_amount;
                                    out.println(user_info.Amount);out.flush();


                                    DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("HH-mm");
                                    String mytimeP = LocalTime.now().format(dtf2);
                                    LocalDate mydateP = LocalDate.now();
                                    String mydateP_string = mydateP.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                                    user_info.Date.add(mydateP_string);
                                    user_info.details.add( mydateP + " " + mytimeP + " " +  pay_amount + "-");

                                    map.get(pay_account_username).details.add(mydateP + " " + mytimeP + " " +  pay_amount + "+");
                                    map.get(pay_account_username).Date.add(mydateP_string);
                                }
                                else
                                {
                                    out.println("false");out.flush();
                                    //System.out.println("There is no user with this account id,please try again");
                                }

                                break;

                            case 8: //Loan System
                                System.out.println("Welcome to our loan system");
                                out.println(user_info.Amount);out.flush();
                                System.out.println("Your current bank account is: " + user_info.Amount);
                                System.out.println("Please enter your MONTHLY gross income: ");
                                int monthly_income = parseInt(br.readLine());
                                System.out.println("Please enter your MONTHLY Debt Payments: ");
                                int monthly_dept = parseInt(br.readLine());
                                long DTI = Math.round( 100* ((double) monthly_dept/(double)monthly_income) );   //dept to income ratio
                                System.out.println("Your Dept-To-Income(DTI) Ratio is: " + DTI + " %");                       //36% dti ia ideal
                                if(DTI < 40)
                                {
                                    System.out.println("You are eligible for our loan policy");
                                    System.out.println("Please enter loan type: ");
                                    System.out.println("1.Personal Loan \n 2.Home Loan \n 3.car Loan \n 4.Gold Loan");
                                    int loan_type = parseInt(br.readLine());
                                    switch (loan_type){
                                        case 1:
                                            int multiplier = 35;
                                            double annual_interest = 10;
                                            double monthly_interest = annual_interest/12 /100 ;



                                            if("true".equals(br.readLine()))
                                            {
                                                user_info.loan_running =true;
                                                loan_details user_loan_info = new loan_details();
                                                user_loan_info.Loan_amount = parseInt(br.readLine());
                                                user_loan_info.Loan_Duration =parseInt(br.readLine());
                                                user_loan_info.Loan_EMI = parseInt(br.readLine());
                                                user_loan_info.Loan_interest =annual_interest;
                                                user_loan_info.Loan_type = "Personal Loan";

                                                loan_map.put(user_name,user_loan_info);

                                                user_info.Amount+=user_loan_info.Loan_amount;
                                                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH-mm-ss");
                                                String mytime = LocalTime.now().format(dtf);
                                                LocalDate mydate = LocalDate.now();
                                                user_info.details.add(mydate + " " + mytime + " " + user_loan_info.Loan_amount + "+");
                                                String mydate_string = mydate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
                                                user_info.Date.add(mydate_string);

                                            }
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
                    out.println("false");out.flush();
                    //System.out.println("Sorry there is no account for this password and User Name");

                }

            } else if(temp1==2){    //Register for user

                String username = br.readLine();
                if (map.containsKey(username))
                {
                    out.println("true");out.flush();
                   // System.out.println("Already exists UserName, Please change user name");

                }
                else
                {
                    out.println("false");out.flush();
                    String password = br.readLine();
                    String name= br.readLine();

                    bank_details user = new bank_details();

                    String account_type = br.readLine();
                switch (parseInt(account_type)){
                    case 1:
                        user.Type = "Saving Account";
                        break;
                    case 2:
                        user.Type = "Salary Account";
                        break;
                    case 3:
                        user.Type = "Fixed deposit Account";
                        break;
                    case 4:
                        user.Type = "Recurring deposit Account";
                        break;
                    case 5:
                        user.Type = "NRI Account";
                        break;

                }
                    user.Name = name;
                    user.Password = password;
                    String account_id = Integer.toString ((int) (Math.random() * 1001));
                    out.println(account_id);out.flush();
                    user.Account_ID  = account_id;

                    user.answer_foorgot_password =br.readLine();

                    map.put(username, user);

                }

            }
            else if (temp1==3) //Forget Password
            {

                String usename_fpassword = br.readLine();

                if(map.containsKey(usename_fpassword))
                {
                    out.println("true");out.flush();

                    String answer_fpassword = br.readLine(); //question answer
                    if(answer_fpassword.equals(map.get(usename_fpassword).answer_foorgot_password))
                    {
                        out.println("true");out.flush();
                        map.get(usename_fpassword).Password = br.readLine(); //change password
                    }
                    else
                    {out.println("false");out.flush();}
                }
                else
                {
                    out.println("false");out.flush();
                }

            }
            else if (temp1==4)  //Admin
            {

                String a_username = br.readLine();
                String a_password = br.readLine();

                if(a_username.equals("admin") && a_password.equals("admin"))
                {
                    out.println("true");out.flush();
                    int a_choice;
                    do {

                        //System.out.println("Show all requests ->1 \nDelete First user -> 2\nExit -> 3");
                        a_choice = parseInt(br.readLine());
                        switch (a_choice) {

                            case 1:
                                out.println(delete_account);out.flush();
                                break;
                            case 2:
                                out.println(delete_account.peek());out.flush();
                                //System.out.println(delete_account.peek() + " wants to delete Account");
                                //System.out.println("Delete account: \n Yes -> 1 \n No -> 2");
                                int k = parseInt(br.readLine());
                                if (k == 1){ map.remove(delete_account.peek());loan_map.remove(delete_account.peek());}
                                delete_account.remove();
                                break;
                        }
                    }
                    while (a_choice !=3);

                }
                else{
                    out.println("false");out.flush();
                    //System.out.println("incorrect");
                }


            }
            else if(temp1==5) //Exit and write in file
            {

                String outputFilePath_password = "password.txt";
                String outputFilePath_details = "details.txt";
                String outputFilePath_history = "history.txt";
                String outputFilePath_deleteAccount = "delete.txt";
                String outputFilePath_loan = "loan_details.txt";

                File file1 = new File(outputFilePath_password);
                File file2 = new File(outputFilePath_details);
                File file3 = new File(outputFilePath_history);
                File file4 = new File(outputFilePath_deleteAccount);
                File file5 = new File(outputFilePath_loan);

                BufferedWriter bf1 = null;
                BufferedWriter bf2 = null;
                BufferedWriter bf3 = null;
                BufferedWriter bf4 = null;
                BufferedWriter bf5 = null;

                try {

                    bf1 = new BufferedWriter(new FileWriter(file1));
                    bf2 = new BufferedWriter(new FileWriter(file2));
                    bf3 = new BufferedWriter(new FileWriter(file3));
                    bf4 = new BufferedWriter(new FileWriter(file4));
                    bf5 = new BufferedWriter(new FileWriter(file5));

                    for (Map.Entry<String, bank_details> entry : map.entrySet()) {

                        bf1.write(entry.getKey() + ":" + entry.getValue().Password );
                        bf1.newLine();

                        bf2.write(entry.getKey() + ":" + entry.getValue().Name + ":" + entry.getValue().Type + ":" + entry.getValue().Amount + ":" + entry.getValue().Account_ID + ":" + entry.getValue().answer_foorgot_password + ":" + entry.getValue().loan_running);
                        bf2.newLine();

                        bf3.write(entry.getKey() + ":");
                        bf3.write(entry.getValue().details.size() + ":");
                        for(String n : entry.getValue().details)
                        {
                            bf3.write(n + ": ");
                        }
                        for(String k : entry.getValue().Date)
                        {
                            bf3.write(k + ":");
                        }
                        bf3.newLine();


                    }
                    while(!delete_account.isEmpty())
                    {
                        bf4.write(delete_account.peek());
                        delete_account.remove();
                        bf4.newLine();
                    }
                    for (Map.Entry<String, loan_details> entry1 : loan_map.entrySet()) {
                        bf5.write(entry1.getKey() + ":" + entry1.getValue().Loan_amount + ":" + entry1.getValue().Loan_Duration + ":" + entry1.getValue().Loan_EMI + ":" + entry1.getValue().Loan_interest + ":" + entry1.getValue().Loan_type);
                        bf5.newLine();
                    }


                    bf1.flush();
                    bf2.flush();
                    bf3.flush();
                    bf4.flush();
                    bf5.flush();

                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        while (temp1 != 5);


    }
}
