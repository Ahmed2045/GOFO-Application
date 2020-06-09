package members;

import java.util.ArrayList;
/**player class
 * @author  Ahmed Emad
 * @author Ahmed Rashad
 * @author Ebrahim Samir
 * @author  Ahmed Omar
 *@author  Karim Abd elhamed
 */
public class Player extends User {

    public Player() {
        role = Administrator.PLAYER; // Role is assigned when a new object is created
    }

    public Player(String userID, String password) {
        super (userID, password);
        role = Administrator.PLAYER;
    }

    private String teamName;
    private ArrayList<Booking> bookings = new ArrayList<>();
    private ArrayList<String[]> team = new ArrayList<>();


    public void setTeamName(String teamName) { this.teamName = teamName; }
    public String getTeamName() { return teamName; }

    public void addBooking(Booking booking) { bookings.add(booking); }
    public void cancelBooking(int index) { bookings.remove(index); }
    public ArrayList<Booking> getBookings() { return  bookings; }

    public void addMember(String[] member){ team.add(member);}
    /** Finds member by name and removes him from members list */
    public boolean removeMember(String memberName){
        for (int i = 0, l = team.size(); i < l;i++) {
            if (team.get(i)[0].equalsIgnoreCase(memberName)) {
                team.remove(i);
                return true;
            }
        }
        return false;

    }

    public ArrayList<String[]> getTeam(){ return team;}
}