import java.util.ArrayList;
import java.util.List;


public class ResultSet {
    List<SGroup> sGroups = new ArrayList<>();

    public void add(SGroup sGroup) {
        sGroups.add(sGroup);
    }

    public void print() {
        for(SGroup sGroup: sGroups) {
            sGroup.print();
        }
    }
}
