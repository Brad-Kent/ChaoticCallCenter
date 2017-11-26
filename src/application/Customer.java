package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Customer
{
    // Fields
    private String firstName, lastName, emailAdd, feedBack, serviceProvider, phoneNoHm;
    private int rating, id; // rating can be array abased on No of providers
    private static int numOfCustomers = 0, newCustomers = 0, currentCustomer = 0, maxCustomers = 100;
    // Class Helpers / Fields
    private static String ratings[] = {"Un - Known", "Worst", "Poor", "Average", "Good", "Excellent" };
    // This can be used if complexity increases and service providers need to be tracked and rated etc.
    // private static String servieProviders[] = {"Telstra", "Optus", "Inet"}; // Add more
    private static Customer customers[] = new Customer[maxCustomers];
	private static File file = new File("");

    //region Constructors
    private Customer(int id, String firstName, String lastName, String emailAdd, String phoneNoHm, int rating, String serviceProvider) {
        this.id = id;
        this.serviceProvider = serviceProvider;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAdd = emailAdd;
        this.phoneNoHm = phoneNoHm;
        this.rating = rating;
        numOfCustomers++;
    }
    private Customer(int id, String firstName, String lastName, String emailAdd, String phoneNoHm, int rating)
    {
      this(id, firstName,lastName, emailAdd, phoneNoHm, rating, "Un Known");
    }
    private Customer(int id, String firstName, String lastName, String emailAdd, String phoneNoHm)
    {
        this(id, firstName, lastName, emailAdd, phoneNoHm, 0);
    }
    private Customer(int id, String firstName, String lastName, String emailAdd)
    {
        this(id, firstName, lastName, emailAdd, " ", 0);
    }
    private Customer(int id, String firstName, String lastName)
    {
        this(id, firstName, lastName, " ", "", 0);
    }
    private Customer(int id, String firstName)
    {
        this(id, firstName, " ", " ", "", 0);
    }
    private Customer(int id)
    {
        this(id, " ", " ", " ", "", 0);
    }
    private Customer()
    {
        this(0, " ", " ", " ", "", 0);
    }
    //endregion

    /// Setters 
    private static void setFirstName(String firstName){customers[currentCustomer].firstName       = firstName;}
    private static void setLastName (String lastName ){customers[currentCustomer].lastName        = lastName ;}
    private static void setEmailAdd (String emailAdd ){customers[currentCustomer].emailAdd        = emailAdd ;}
    private static void setPhoneNoHm(String phoneNoHm){customers[currentCustomer].phoneNoHm       = phoneNoHm;}
    private static void setServiePro(String service  ){customers[currentCustomer].serviceProvider = service  ;}
    private static void setReview   (String review   ){customers[currentCustomer].feedBack        = review   ;}
    private static void setRating   (int rating)      {customers[currentCustomer].rating          = rating   ;}
    /// Getters 
    private static String getFirstName() {return customers[currentCustomer].firstName      ;}
    private static String getLastName () {return customers[currentCustomer].lastName       ;}
    private static String getEmailAdd () {return customers[currentCustomer].emailAdd       ;}
    private static String getService  () {return customers[currentCustomer].serviceProvider;}
    private static String getPhoneNoHm() {return customers[currentCustomer].phoneNoHm      ;}
    private static String getReview   () {return customers[currentCustomer].feedBack       ;}
    private static int getRating() {return customers[currentCustomer].rating;}
    private static int getId    () {return customers[currentCustomer].id    ;} // For Sorting & array Count

    static void setUpCustomers(){ getCustomers    ();}
    static void onClose       (){writeToCustomers();}
    static int  getCustomerCount(){return numOfCustomers;}
    

    static String[] getCustomer(int index)
    {
        int temp = currentCustomer;
        currentCustomer = index;

        String[] customerData =
                {
                        getFirstName(),
                        getLastName(),
                        getEmailAdd(),
                        getPhoneNoHm(),
                        ratings[getRating()],
                        getService(),
                        getReview()
                };
        currentCustomer = temp;
        return customerData;
    }
    static void updateCustomer(String[] updatedCustomer, int customerIndex)
    {
        if (customerIndex >= 0 && customerIndex <= maxCustomers)
        {
            currentCustomer = customerIndex;

            setFirstName(updatedCustomer[0]);
            setLastName (updatedCustomer[1]);
            setEmailAdd (updatedCustomer[2]);
            setPhoneNoHm(updatedCustomer[3]);

            setRating(stringToInt(updatedCustomer[4]));
            setServiePro(updatedCustomer[5]);
            //System.out.println(getFirstName() + "  " + "Rating: " + getRating());
        }
    }
    static void addNewCustomer(String[] newCustomerData)
    {
        currentCustomer = numOfCustomers;

        String[] newCustomer = checkNewCustomerData(newCustomerData);

        customers[currentCustomer] = new Customer(newID(), newCustomer[0],   newCustomer[1],  newCustomer[2],  newCustomer[3], stringToInt(newCustomer[4]), newCustomer[5]); // Rating

        newCustomers++;
        currentCustomer = numOfCustomers - 1;
        System.out.println("New Customer Added: " + numOfCustomers);
    }
    // Private
    private static String[] checkNewCustomerData(String[] newCustomer)
    {
        for (int i = 0; i < newCustomer.length; i++)
        {
            if (newCustomer[i] == null){ newCustomer[i] = " ";}
        }
        return newCustomer;
    }
    private static int newID()
    {
        return numOfCustomers + 1; // could be upgraded when delete etc. comes into play
    }
    private static int stringToInt(String num)
    {
        int value;

        try   { value = Integer.parseInt(num); }
        catch ( NumberFormatException e ) { value = 0;  }

        return value;
    }
    private static String intToString(int convertMePlz)
    {
        try {return Integer.toString(convertMePlz);}

        catch (NumberFormatException ex) {return intToString(0);}
    }
    private static void getCustomers()
    {
         String txtCustomer  = "Customers";
         String txtName1     = "reviews1" ;
         String txtName      = "reviews"  ;
         String fileName     = file.getAbsolutePath() + "/DataBase/reviews.txt";
        	 // "/Users/bradkent/Documents/SoftwareEng/Java/workspace/CaoticCallCenter/src/oop1/CaoticCallCenter/DataFiles/reviews.txt";
         //"/Users/bradkent/Documents/SoftwareEng/Java/workspace/CaoticCallCenter/src/oop1/CaoticCallCenter/DataDiles/reviews.txt";//"C:\\Users\\Battl\\IdeaProjects\\Caotic Call Center\\src\\sample/reviews.txt";

         String lines[] = new String[1000];

         int gg = 0; int i = 0;  int x = 0;

         try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName)))
         {
             while (( lines[x] = bufferedReader.readLine()) != null)
             {
                 // System.out.println(lines[x]);
                 // System.out.println(lines[x]);
                 int ds = x;
                 System.out.println("Input: " + lines[x]);
                 if (((ds + 1) % 8) == 0)
                 {
                     i =  x - 7;
                     Customer.customers[gg] = new Customer(
                             stringToInt(lines[i++]),
                             lines[i++],
                             lines[i++],
                             lines[i++],
                             lines[i++],
                             stringToInt(lines[i++]),
                             lines[i]);
                     gg++; newCustomers++;
                 }
                 // System.out.println(lines[x] + " || x : " + x);
                 x++;
             }
             bufferedReader.close();

         } catch (IOException ex) {ex.printStackTrace();}

         System.out.println("Num of Customers:     " + numOfCustomers);
         System.out.println("Num of New Customers: " + newCustomers  );
         getReviews();
    }
    private static void getReviews()
    {
         String fileName = file.getAbsolutePath() + "/DataBase/reviews1.txt";
        		 //"/Users/bradkent/Documents/SoftwareEng/Java/workspace/CaoticCallCenter/src/oop1/CaoticCallCenter/DataDiles/reviews1.txt";/// "C:\\Users\\Battl\\IdeaProjects\\Caotic Call Center\\src\\sample/reviews1.txt";
         String reviewLines[] = new String[1000];
         int y = 0;
        currentCustomer = 0;
         StringBuilder stringBuilder = new StringBuilder();
         stringBuilder.append("null");

         try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName)))
         {
             while ((reviewLines[y] = bufferedReader.readLine()) != null && currentCustomer < numOfCustomers)
             {
                 if (reviewLines[y].equals("-"))
                 {
                    stringBuilder.delete(0,4);
                     setReview(stringBuilder.toString());
                     currentCustomer++;
                     stringBuilder = new StringBuilder();

                     y++;
                 }
                 stringBuilder.append(reviewLines[y]);
                 System.out.println(reviewLines[y]);
                 y++;
             }
             bufferedReader.close();
         }
         catch (IOException ex) {ex.printStackTrace();}
    }

    private static void writeToReviews()
    {
        // will do if needed
    }
    private static void writeToCustomers()
    {
        // String fileName = "/Users/bradkent/Documents/SoftwareEng/Java/workspace/CaoticCallCenter/src/oop1/CaoticCallCenter/DataDiles/Customers.txt";// "C:\\Users\\Battl\\IdeaProjects\\Caotic Call Center\\src\\sample/reviews.txt";
        // try {
        //     FileWriter fileWriter = new FileWriter(fileName);

        //     BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        //     currentCustomer = 0;
        //     for(int i = 0; i < numOfCustomers; i++)
        //     {
        //         bufferedWriter.write(intToString(getId())); //ID
        //         bufferedWriter.newLine();

        //         bufferedWriter.write(getFirstName());// F name
        //         bufferedWriter.newLine();

        //         bufferedWriter.write(getLastName()); // L name
        //         bufferedWriter.newLine();

        //         bufferedWriter.write(getEmailAdd()); // email
        //         bufferedWriter.newLine();

        //         bufferedWriter.write(getPhoneNoHm()); // phone
        //         bufferedWriter.newLine();

        //         bufferedWriter.write(intToString(getRating())); // rating
        //         bufferedWriter.newLine();

        //         bufferedWriter.write(getService());
        //         bufferedWriter.newLine();

        //         bufferedWriter.newLine();
        //         currentCustomer++;
        //         System.out.println("Writing Customers: " + currentCustomer);
        //     }
        //     bufferedWriter.close();
        // }
        // catch(IOException ex) {System.out.println("Error writing to file '" + fileName + "'");}
            // ex.printStackTrace();
    }
}
