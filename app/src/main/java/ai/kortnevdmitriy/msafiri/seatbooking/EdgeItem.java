package ai.kortnevdmitriy.msafiri.seatbooking;

public class EdgeItem extends AbstractItem {

    public EdgeItem(String label) {
        super(label);
    }


    @Override
    public int getType() {
        return TYPE_EDGE;
    }

}
