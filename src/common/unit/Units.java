package common.unit;

import java.util.ArrayList;
import java.util.List;

public class Units {
    public List<Integer> units = new ArrayList<>();

    public void add(int i) {

        units.add(i);
    }

    public boolean contains(int i) {
        return units.contains(i);
    }

    public Units(Units u) {

        units.addAll(u.units);
    }

    public Units() {

    }

    public int pointNum(List<Unit> unitSet) {
        List<Integer> points = new ArrayList<>();
        for(int unit: units) {
            points.removeAll(unitSet.get(unit).unit);
            points.addAll(unitSet.get(unit).unit);
        }
        return points.size();
    }

    public int get(int i) {

        return units.get(i);
    }

    public String toString() {
        String s = "";
        for(int i: units) {
            s += i + ",";
        }
        return s;
    }

}
