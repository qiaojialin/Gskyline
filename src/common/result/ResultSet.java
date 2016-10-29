package common.result;

import java.util.ArrayList;
import java.util.List;


public class ResultSet {
    public List<SGroup> sGroups = new ArrayList<>();

    public void add(SGroup sGroup) {
        sGroups.add(sGroup);
    }

    public int size() {
        return sGroups.size();
    }

    public void print() {
        System.out.println("result set has: " + sGroups.size());
        for(SGroup sGroup: sGroups) {
            sGroup.print();
        }
    }
}
