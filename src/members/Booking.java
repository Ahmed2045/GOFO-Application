package members;

public class Booking {
    /**booking class
     * @author  Ahmed Emad
     * @author Ahmed Rashad
     * @author Ebrahim Samir
     * @author  Ahmed Omar
     *@author  Karim Abd elhamed
     */
    private String slot; // Stores booked slot
    private Playground playground; // Stores booked playground

    public String getSlot() {
        return slot;
    }
    public void setSlot(String slot) {
        this.slot = slot;
    }

    public Playground getPlayground() {
        return playground;
    }
    public void setPlayground(Playground playground) {
        this.playground = playground;
    }
}