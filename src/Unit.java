import java.util.ArrayList;
import java.util.List;

public class Unit {
    List<Integer> unit = new ArrayList<>();
    public int index;

    public int size() {

        return unit.size();
    }

    public Unit(List<Integer> unit, int i) {

        this.unit = unit;
        index = i;
    }
}
