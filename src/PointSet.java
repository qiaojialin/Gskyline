
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Collections;
import java.util.Comparator;


public class PointSet {

	public List<Point> pSet;

	public PointSet() {

		pSet = new ArrayList<>();

	}

	public void readFromFile(String path) {
		try {
			Scanner in = new Scanner(new File(path));
			while (in.hasNextLine()) {
				String str = in.nextLine();
				String[] data_str = str.split(" ");
				int	dimension = data_str.length;
				Double[] data_d = new Double[dimension];
				for(int i=0; i<dimension; i++) {
					data_d[i] = Double.parseDouble(data_str[i]);
				}
				Point point = new Point(dimension, data_d);
 				pSet.add(point);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	//按照第1个属性升序排列，并赋予下标
	public void sort() {
		Collections.sort(pSet, new Comparator<Point>(){
			@Override
			public int compare(Point p1, Point p2) {
				return p1.data[0].compareTo(p2.data[0]);
			}
		});

		//赋予下标
		for(int i=0; i<pSet.size(); i++) {
			pSet.get(i).index = i;
		}
	}

	public void print() {
		for(Point p: pSet) {
			p.print();
		}
	}

}


