package members;

import java.util.ArrayList;

public class Playground {
    private String name;
    private String address;
    private Owner owner;
    private int area;
    private boolean approved = false; // Specifies if playground is approved by admin
    private int slotPrice;
    private ArrayList<String> slots = new ArrayList<>();
    private ArrayList<String> bookedSlots = new ArrayList<>();

    public Playground(){
        setUpSlots();
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String location) { this.address = location; }

    public Owner getOwner() { return owner; }
    public void setOwner(Owner owner) { this.owner = owner; }

    public int getArea() { return area; }
    public void setArea(int area) { this.area = area; }

    public void setApproval(boolean approved) { this.approved = approved; }

    public int getSlotPrice() { return slotPrice; }
    public void setSlotPrice(int slotPrice) { this.slotPrice = slotPrice; }

    public ArrayList<String> getSlots() { return slots; }

    /** Sets up playground slots in strings form
     * each slot is an hour
     * starting from 00:00 to 24:00 slots are filled
     */
    public void setUpSlots(){
        for (int i = 0; i < 24; i++)
        {
            StringBuilder slot = new StringBuilder();
            if (i < 10)
                slot.append("0");

            slot.append(i + ":00 - ");
            if (i + 1 < 10)
                slot.append("0");

            slot.append((i + 1) + ":00");
            slots.add(slot.toString());
        }
    }

    /**
     * Moves slot from slots list to bookedSlots list
     * @param i is the position of the booked slot
     */
    public void bookSlot(int i) {
        bookedSlots.add(slots.get(i));
        slots.remove(i);
    }

    /**
     * Returns to the cancelled slot to the slots list
     * @param i is the slot to be cancelled
     * @throws NumberFormatException is thrown if the slot string was not numerical
     */
    public void cancelBooking(int i) throws NumberFormatException {

        int n = Integer.parseInt(bookedSlots.get(i).substring(0,1));
        slots.add(n, bookedSlots.get(i));
        bookedSlots.remove(i);
    }
    }