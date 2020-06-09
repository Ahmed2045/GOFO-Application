package members;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/**Admin Class
 * @author  Ahmed Emad
 * @author Ahmed Rashad
 * @author Ebrahim Samir
 * @author  Ahmed Omar
 *@author  Karim Abd elhamed
 */
public class Administrator extends User {
    public static final int PLAYER = 0;
    public static final int PLAYGROUND_OWNER = 1;
    public static final int ADMIN = 2;

    private static ArrayList<User> users = new ArrayList<>();
    private static ArrayList<Playground> approvedPlaygrounds = new ArrayList<>();
    private static ArrayList<Playground> requests = new ArrayList<>();
    private static Map<Playground, String> complaints = new HashMap<>();

    public static void addUser(User user) { users.add(user); }

    public static User findUser(String userID, String password){
        for (User user : users)
            if (user.getID().equals(userID) && user.getPassword().equals(password))
                return user;

        return null;
    }

    public static ArrayList<Playground> getPlaygrounds() { return approvedPlaygrounds; }
    public static void removePlayground(Playground playground) {
        playground.getOwner().getPlaygrounds().remove(playground);
        approvedPlaygrounds.remove(playground);
    }

    public static void addRequest(Playground playground){ requests.add(playground); }
    public static ArrayList<Playground> getRequests() { return requests; }

    /**
     * Handles playground requests
     * @param request is the playground awaiting approval
     * @param isApproved specifies if admin approves of playground
     */
    public static void handleRequest(Playground request, boolean isApproved){
        if (isApproved) {
            request.setApproval(true);
            approvedPlaygrounds.add(request);
        }

        requests.remove(request);
    }

    public static void addComplaint( Playground playground, String complaint){ complaints.put(playground, complaint); }
    public static Map<Playground, String> getComplaints() { return complaints; }
}