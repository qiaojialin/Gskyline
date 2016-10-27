package common.result;

import common.unit.Unit;
import common.unit.Units;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SGroup {
	public List<Integer> points = new ArrayList<>();

	public SGroup(Unit unit) {

		points = unit.unit;
		sort();
	}

	public SGroup(List<Unit> unitSet, Units units) {
		for(int i: units.units) {
			points.removeAll(unitSet.get(i).unit);
			points.addAll(unitSet.get(i).unit);
		}
		sort();
	}

	private void sort() {
		Collections.sort(points, new Comparator<Integer>(){
			@Override
			public int compare(Integer i1, Integer i2) {
				return i1.compareTo(i2);
			}
		});
	}

	public void print() {
		for(int p: points) {
			System.out.print(p + " ");
		}
		System.out.println();
	}

	public String toString() {
		String s = "";
		for(int p: points) {
			s += p + " ";
		}
		return s;
	}
}
