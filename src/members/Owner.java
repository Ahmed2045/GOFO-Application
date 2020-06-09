package members;

import java.util.ArrayList;

public class Owner extends User {

    public Owner(){ role = Administrator.PLAYGROUND_OWNER; }

    public Owner(String userID, String password){
        super(userID, password);
        role = Administrator.PLAYGROUND_OWNER;
    }

    private ArrayList<Playground> playgrounds = new ArrayList<>();
    private ArrayList<Booking> bookings = new ArrayList<>();

    public void addPlayground(Playground playground){
        playground.setOwner(this);
        playgrounds.add(playground);
        Administrator.addRequest(playground);
    }
    public void removePlayground(Playground playground){ playgrounds.remove(playground); }
    public ArrayList<Playground> getPlaygrounds() { return playgrounds; }

    public void addBooking(Booking booking) { bookings.add(booking); }
    public ArrayList<Booking> getBookings() { return bookings; }
}