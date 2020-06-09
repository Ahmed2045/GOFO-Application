package com.company;

import members.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**main class
 * @author  Ahmed Emad
 * @author Ahmed Rashad
 * @author Ebrahim Samir
 * @author  Ahmed Omar
 *@author  Karim Abd elhamed
 */
public class Main {

    public static void main(String[] args) {

        User admin = new User("admin", "admin");
        Player player = new Player("player", "player");
        Owner owner = new Owner("owner", "owner");

        // Three users already added to the system
        Administrator.addUser(admin);
        Administrator.addUser(player);
        Administrator.addUser(owner);

        while (true)
            launchMenu();
    }

    /** Displays first menu */
    private static void launchMenu() {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to Go Football!");
        System.out.println("1) Log In\t2) Sign Up\t3) Exit");

        int choice;
        do { choice = validateInt(input); } while (choice == -1);

        switch (choice) {
            case 1: // 1) Log In
                logIn();
                break;
            case 2: // 2) Sign Up
                signUp();
                break;
            case 3: // 3) Exit"
                System.exit(0);
            default:
                System.out.println("Invalid Input!");
        }
    }

    /** Checks user credentials and logs him in if he's a member */
    private static void logIn() throws NullPointerException {

        Scanner input = new Scanner(System.in);
        User user;

        System.out.print("User ID: ");
        String userID = input.nextLine();

        System.out.print("Password: ");
        String password = input.nextLine();

        user = Administrator.findUser(userID, password.toString());

        if (user == null) {
            System.out.println("Invalid user credentials!");
            return;
        }

        chooseUser(user);
    }

    /** Takes user information and signs him up */
    private static void signUp() {
        Scanner input = new Scanner(System.in);
        User user;

        System.out.println("Sign Up As:\n1) Player\t2) Playground Owner");
        int choice;
        do { choice = validateInt(input); } while (choice == -1);

        switch (choice) {
            case 1:
                user = new Player();
                break;
            case 2:
                user = new Owner();
                break;
            default:
                System.out.println("Invalid Input!");
                return;
        }

        System.out.print("Name: ");
        user.setName(input.nextLine());

        System.out.print("\nEmail: ");
        String email = input.nextLine();

        while (!validateEmail(email)){
            System.out.println("Invalid E-mail, try again");
            email = input.nextLine();
        }
        user.setEmail(email);

        System.out.print("\nUser ID: ");
        user.setID(input.nextLine());

        System.out.print("\nPassword: ");
        user.setPassword(input.nextLine());

        System.out.print("\nAddress: ");
        user.setAddress(input.nextLine());

        System.out.print("\nAccount Number: ");

        user.setAccountNumber(input.nextLine());
        while (user.getAccountNumber().matches("[a-zA-Z]+") || user.getAccountNumber().length() < 13) { // Checks if bank account is a number
            System.out.println("Invalid, input should be numerical and not less than 13 digits, please try again");
            user.setAccountNumber(input.nextLine());
        }

        Administrator.addUser(user);
        System.out.println("User added Successfully!");
        chooseUser(user);
    }

    /**
     * Checks user type based on the role attribute and redirect him to the right menu
     * @param user is the user object carrying login info
     */
    private static void chooseUser(User user) throws NullPointerException {

        System.out.print("Welcome");

        if (user.getName() == null) // Prints user id if name wasn't specified yet
            System.out.println(", " + user.getID());
        else
            System.out.println(", " + user.getName());

        switch (user.getRole()) {
            case Administrator.PLAYER:
                startPlayerMenu((Player) user);
                break;
            case Administrator.PLAYGROUND_OWNER:
                startOwnerMenu((Owner) user);
                break;
            case Administrator.ADMIN:
                startAdminMenu();
                break;
        }
    }

    /** Handles player menu options */
    private static void startPlayerMenu(Player player) throws NullPointerException {
        while (true) {
            Scanner input = new Scanner(System.in);
            System.out.print("1) Book Playground\n2) Manage Team\n3) Cancel Booking\n4) Add Funds\n5) Send Complaint\n6) Log out\n");
            int choice;
            do { choice = validateInt(input); } while (choice == -1);

            switch (choice) {
                case 1: { // 1) Book Playground
                    Booking booking = new Booking();
                    ArrayList<Playground> playgrounds = Administrator.getPlaygrounds();
                    Playground playground;

                    if (playgrounds.isEmpty()) // Returns booking menu if no playground were found
                    {
                        System.out.println("No Playgrounds Available");
                        break;
                    }
                    System.out.println("Choose Playground: ");

                    // Iterates over all available playgrounds and prints their info for the player
                    for (int i = 0, l = playgrounds.size(); i < l; i++)
                        System.out.println(
                                (i + 1) + ") " +
                                playgrounds.get(i).getName() +
                                ", " + playgrounds.get(i).getAddress() +
                                ", " + playgrounds.get(i).getArea() + "m\u00B2" +
                                ", " + playgrounds.get(i).getSlotPrice() + "L.E"
                        );

                    do { choice = validateInt(input); } while (choice == -1);;
                    choice--;

                    if (choice >= playgrounds.size() || choice < 0) { // Checks if choice is out of bounds
                        System.out.println("Invalid choice!");
                        break;
                    }
                    playground = playgrounds.get(choice);

                    if (playground.getSlotPrice() > player.getFunds()) { // Returns if player's balance is insufficient for booking
                        System.out.println("Can't book playground due to Insufficient Funds");
                        break;
                    }

                    booking.setPlayground(playground);
                    player.editFunds(-playground.getSlotPrice()); // Withdraws from player and deposits to owner

                    playground.getOwner().editFunds(playground.getSlotPrice());

                    System.out.println("Choose Slot: ");

                    for (int i = 0, l = playground.getSlots().size(); i < l; i++) { // Iterates over all slots available
                        System.out.print((i + 1) + ") " + playground.getSlots().get(i) + "\t\t");
                        if ((i + 1) % 6 == 0)
                            System.out.println();
                    }

                    do { choice = validateInt(input); } while (choice == -1);
                    choice--;

                    if (choice < 0 || choice >= playground.getSlots().size()) {
                        System.out.println("Invalid Choice!");
                        break;
                    }

                    booking.setSlot(playground.getSlots().get(choice));

                    playground.bookSlot(choice);
                    player.addBooking(booking); // Adds a new booking
                    playground.getOwner().addBooking(booking);
                    break;
                }
                case 2: { // 2) Manage Team
                    while (choice != 5) {

                        //  Prints team info only if team name wasn assigned
                        if (player.getTeamName() != null && player.getTeamName() != "")
                        {
                            System.out.println("Team " + player.getTeamName());
                            for (int i = 0, l = player.getTeam().size(); i < l; i++)
                            {
                                String[] member = player.getTeam().get(i);
                                System.out.println("- " + member[0]);
                            }
                        }
                        System.out.println("1) Modify Team Name\n2) Add Member\n3) Remove Member\n4) Invite by Mail\n5) Return");
                        do { choice = validateInt(input); } while (choice == -1);

                        switch (choice) {
                            case 1: // 1) Modify Team Name

                                System.out.println("Enter Team Name: ");
                                player.setTeamName(input.nextLine());
                                System.out.println("Name Saved");
                                break;
                            case 2: { // 2) Add Member
                                String[] member = new String[2];
                                System.out.println("Add Player: ");
                                System.out.print("Enter Name: ");
                                member[0] = input.nextLine();

                                System.out.print("Enter Email: ");
                                member[1] = input.nextLine();
                                while (!validateEmail(member[1])){
                                    System.out.println("Invalid E-mail, try again");
                                    member[1] = input.nextLine();
                                }

                                player.addMember(member);
                                System.out.println("Player Added");
                                break;
                            }

                            case 3: // 3) Remove Member
                                System.out.println("Enter Player Name: ");
                                if(player.removeMember(input.nextLine()))
                                    System.out.println("Player Removed");
                                else
                                    System.out.println("Player not Found");
                                break;
                            case 4: // 4) Invite by Mail
                                System.out.println("Enter Email");
                                while (!validateEmail(input.nextLine()))
                                    System.out.println("Invalid E-mail");
                                System.out.println("Invitation Sent");
                                break;
                            case 5: // Return
                                break;
                            default:
                                System.out.println("Invalid Input!");
                        }
                    }
                    break;
                }
                case 3: { // 3) Cancel Booking
                    ArrayList<Booking> bookings = player.getBookings();

                    if (bookings.isEmpty()) // Returns if no bookings were found
                    {
                        System.out.println("No bookings found");
                        break;
                    }

                    System.out.println("Choose the slot you wish to cancel: ");
                    for (int i = 0, l = bookings.size(); i < l; i++) // Iterates over all bookings to get their info
                        System.out.println((i + 1) + ") " + bookings.get(i).getPlayground().getName() + ",  " + bookings.get(i).getSlot());

                    do { choice = validateInt(input); } while (choice == -1);
                    choice--;

                    if (choice < 0 || choice >= bookings.size()) {
                        System.out.println("Invalid Choice!");
                        break;
                    }

                    Playground playground = bookings.get(choice).getPlayground();

                    player.editFunds(playground.getSlotPrice());
                    playground.getOwner().editFunds(-playground.getSlotPrice());

                    playground.cancelBooking(choice);
                    player.cancelBooking(choice);
                    break;
                }
                case 4: { // 4) Add Funds
                    System.out.print("Enter the Amount: ");
                    do { player.editFunds(validateInt(input)); } while (choice == -1); // Withdraws money from specified bank account
                    break;
                }
                case 5: { // 5) Send Complaint
                    String complaint;
                    String name;
                    boolean found = false;
                    System.out.println("Enter Playground Name: ");
                    name = input.nextLine();

                    for (Playground playground : Administrator.getPlaygrounds()) {
                        if (name.equalsIgnoreCase(playground.getName())) { // Finds playground by name
                            System.out.println("Enter your complaint: ");
                            complaint = input.nextLine();

                            Administrator.addComplaint(playground, complaint);
                            found = true;
                            break;
                        }
                    }

                    if (!found)
                        System.out.println("No playground found with the name " + name);
                    break;
                }
                case 6: // 6) Log out
                    return;
                default:
                    System.out.println("Invalid Input!");

            }
        }


    }

    /** Handles owner menu options */
    private static void startOwnerMenu(Owner owner) {

        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.print("1) Add New Playground\n2) Update Existing Playground\n3) View Bookings\n4) Log out\n");

            int choice;
            do { choice = validateInt(input); } while (choice == -1);

            switch (choice) {
                case 1: { // 1) Add New Playground
                    Playground playground = new Playground();

                    System.out.print("Name: ");
                    playground.setName(input.nextLine());

                    System.out.print("Address: ");
                    playground.setAddress(input.nextLine());

                    System.out.print("Area in " + "m\u00B2: ");
                    do { playground.setArea(validateInt(input)); } while (choice == -1);

                    System.out.print("Price per Hour: ");
                    do { playground.setSlotPrice(validateInt(input)); } while (choice == -1);

                    owner.addPlayground(playground); // Adds playground to the requests awaiting approval by admin
                    System.out.println("Playground added, please wait for admin approval");
                    break;
                }
                case 2: { // 2) Update Existing Playground

                    if (owner.getPlaygrounds().isEmpty()) {
                        System.out.println("No Playgrounds");
                        break;
                    }

                    for (int i = 0, l = owner.getPlaygrounds().size(); i < l; i++) {
                        System.out.println("Choose Playground: ");
                        System.out.println((i + 1) + ") " + owner.getPlaygrounds().get(i).getName());
                    }

                    do { choice = validateInt(input); } while (choice == -1);
                    choice--;

                    if (choice >= owner.getPlaygrounds().size() || choice < 0)
                    {
                        System.out.println("Invalid Choice");
                        break;
                    }

                    Playground playground = owner.getPlaygrounds().get(choice);

                    System.out.print("1) Update Name\n2) Update Address\n3) Update Area\n4) Update Price per Hour\n5) Return\n");

                    do { choice = validateInt(input); } while (choice == -1);
                    switch (choice) {
                        case 1:// 1) Update Name
                            System.out.println("New Name: ");
                            playground.setName(input.nextLine());
                            break;
                        case 2: // 2) Update Address
                            System.out.println("New Address: ");
                            playground.setAddress(input.nextLine());
                            break;
                        case 3: // 3) Update Area
                            System.out.println("New Area: ");
                            do { playground.setArea(validateInt(input)); } while (choice == -1);
                            break;
                        case 4: // 4) Update Price per Hour
                            System.out.println("New Price per hour: ");
                            do { playground.setSlotPrice(validateInt(input)); } while (choice == -1);
                            break;
                        case 5: // 5) Return
                            break;
                        default:
                            System.out.println("Invalid Input!");
                    }
                    break;
                }
                case 3: { // 3) View Bookings

                    if (owner.getBookings().isEmpty())
                    {
                        System.out.println("No Bookings Found");
                        break;
                    }
                    for (Booking booking : owner.getBookings()) { // Shows booked playgrounds owned by this owner
                        System.out.println("- " + booking.getPlayground().getName() + ", " + booking.getSlot());
                        System.out.println("---------------");
                    }
                    break;
                }
                case 4: // 4) Log out
                    return;
                default:
                    System.out.println("Invalid Input!");
            }
        }
    }

    /** Handles admin menu options */
    private static void startAdminMenu() {
        Scanner input = new Scanner(System.in);
        int choice;

        while (true) {
            System.out.print("1) View Playground Requests\n2) View Complaints\n3) Log Out\n");
            do { choice = validateInt(input); } while (choice == -1);

            switch (choice) {
                case 1: { // 1) View Playground Requests

                    ArrayList<Playground> requests = Administrator.getRequests();

                    if (requests.isEmpty()) {
                        System.out.println("No playground requests found.");
                        break;
                    }

                    System.out.println("Choose Request: ");

                    for (int i = 0, l = requests.size(); i < l; i++) { // Prints playgrounds that are not yet approved
                        System.out.println((i + 1) + ") " +
                                requests.get(i).getName() + ", " +
                                requests.get(i).getAddress() + ", " +
                                requests.get(i).getArea() + "m\u00B2, " +
                                requests.get(i).getSlotPrice() + " L.E"
                        );
                    }

                    do { choice = validateInt(input); } while (choice == -1);
                    Playground request = requests.get(choice - 1);

                    System.out.print(request.getName() + ":\t1) Approve\t2) Reject\n");
                    do { choice = validateInt(input); } while (choice == -1);

                    switch (choice) {
                        case 1:
                            Administrator.handleRequest(request, true);
                            break;
                        case 2:
                            Administrator.handleRequest(request, false);
                        default:
                            System.out.println("Invalid Input!");
                    }
                    break;
                }
                case 2: { // 2) View Complaints
                    Map<Playground, String> complaints = Administrator.getComplaints();
                    if (complaints.isEmpty()) {
                        System.out.println("No complaints found.");
                        break;
                    }
                    System.out.println("Choose Playground: ");

                    for (Map.Entry<Playground, String> complaint : complaints.entrySet())
                        System.out.println("- " + complaint.getKey().getName());

                    String key = input.nextLine();

                    for (Map.Entry<Playground, String> complaint : complaints.entrySet()) {
                        if (key.equalsIgnoreCase(complaint.getKey().getName())) {
                            System.out.println(complaint.getValue() + "\n Suspend Playground? Y / N");

                            if ("Y".equalsIgnoreCase(input.nextLine())) {
                                Administrator.removePlayground(complaint.getKey());
                                complaint.getKey().getOwner().removePlayground(complaint.getKey());
                                break;
                            }

                        }

                    }
                    break;
                }
                case 3: // 3) Log Out
                    return;
                default:
                    System.out.println("Invalid Input!");
            }

        }

    }

    /**
     * Checks if the email input is in a valid format
     * @param email the string that will be tested
     * @return a boolean, true if email is valid
     */
    private static boolean validateEmail(String email){

        String pattern = "^[\\w-_\\.+]*[\\w-_\\.1]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(pattern);
    }

    /**
     * Validates if input is numerical
     * @param input is the string input tested
     * @return the integer if valid, -1 if not
     */
    private static int validateInt(Scanner input) {

        int inputInt = -1;
        try {
            inputInt = Integer.parseInt(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Input is not a valid numerical value, try again");
        } finally {
            return inputInt;
        }
    }
}