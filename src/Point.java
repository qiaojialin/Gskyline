import java.util.ArrayList;
import java.util.List;

public class Point {
	public int index = -1;					//下标
	public List<Integer> parents;  	//父亲节点
	public List<Integer> children;		//孩子节点
	public int dimension = -1; 				//维度
	public int layer = -1;					//层次
	public Double[] data;				//数据


	public Point(int dimension, Double[] data) {
		this.data = data;
		this.dimension = dimension;
		parents = new ArrayList<>();
		children = new ArrayList<>();
	}

	public void print() {
		System.out.print("layer：" + layer + " index：" + index + " ");
		for(int i=0; i<dimension; i++) {
			System.out.print(data[i] + " ");
		}
		System.out.println();
	}

	//此点是否dominate另一个点
	public boolean dominate(Point p) {
		boolean flag = false;
		for(int i=0; i<dimension; i++) {
			if(this.data[i] > p.data[i]) {
				return false;
			} else if(this.data[i] < p.data[i]) {
				//至少有一个维度小
				flag = true;
			}
		}
		return flag;
	}

}
