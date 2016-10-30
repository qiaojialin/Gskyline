package common.result;

import common.unit.Unit;
import common.unit.Units;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
/**
 * 一个skylinegroup，其中包含了点的下标的集合
 * @author thinkpad
 *
 */
public class SGroup {
	/**
	 * 一个skylinegroup中的点的下标集合
	 */
	public List<Integer> points = new ArrayList<>();

	public SGroup(Unit unit) {

		points = unit.unit;
		sort();
	}
	
	/**
	 * 用一个点的下标集合初始化sgroup
	 * @param p 点下标集合
	 */
	public SGroup(List<Integer> p) {
		points = p;
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
	
	/**
	 * 打印sgroup信息
	 */
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
