package members;

public class Booking {

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