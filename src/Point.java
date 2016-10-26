import java.util.ArrayList;
import java.util.List;

public class Point {
	public int index = -1;					//涓嬫爣
	public List<Integer> parents;  	//鐖朵翰鑺傜偣
	public List<Integer> children;		//瀛╁瓙鑺傜偣
	public int dimension = -1; 				//缁村害
	public int layer = -1;					//灞傛
	public Double[] data;				//鏁版嵁


	public Point(int dimension, Double[] data) {
		this.data = data;
		this.dimension = dimension;
		parents = new ArrayList<>();
		children = new ArrayList<>();
	}

	public void print() {
		System.out.print("layer锛�" + layer + " index锛�" + index + " ");
		for(int i=0; i<dimension; i++) {
			System.out.print(data[i] + " ");
		}
		System.out.println();
	}

	//姝ょ偣鏄惁dominate鍙︿竴涓偣
	public boolean dominate(Point p) {
		boolean flag = false;
		for(int i=0; i<dimension; i++) {
			if(this.data[i] > p.data[i]) {
				return false;
			} else if(this.data[i] < p.data[i]) {
				//鑷冲皯鏈変竴涓淮搴﹀皬
				flag = true;
			}
		}
		return flag;
	}

	public List<Integer> unit() {
		List<Integer> unit = new ArrayList<>();
		unit.addAll(parents);
		unit.add(index);
		return unit;
	}
}
