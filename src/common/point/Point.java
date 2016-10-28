package common.point;

import common.unit.Unit;

import java.util.ArrayList;
import java.util.List;
/**
 * 定义点结构，点的dominate的定义，点的unit的计算方法
 * @author thinkpad
 *
 */
public class Point {
	/**
	 * 点在点集的下标
	 */
	public int index = -1;					
	/**
	 * 点的所有父亲节点
	 */
	public List<Integer> parents;  
	/**
	 * 点的所有孩子节点
	 */
	public List<Integer> children;		
	/**
	 * 点的维度
	 */
	public int dimension = -1; 				
	/**
	 * 点的层次
	 */
	public int layer = -1;
	/**
	 * 点的坐标
	 */
	public Double[] data;			

	/**
	 * 根据点的维度和坐标对点进行初始化
	 * @param dimension 维度
	 * @param data 坐标
	 */
	public Point(int dimension, Double[] data) {
		this.data = data;
		this.dimension = dimension;
		parents = new ArrayList<>();
		children = new ArrayList<>();
	}

	public void print() {
		System.out.print("layer：" + layer + " index：" + index + "  data:");
		for(int i=0; i<dimension; i++) {
			System.out.print(data[i] + " ");
		}
		System.out.println();
	}

	/**
	 * 判断一个点是否可以dominate给定点
	 * @param p 给定点
	 * @return 可以dominate给定点返回true
	 */
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
	/**
	 * 
	 * @return 返回一个点的unit集合
	 */
	public Unit unit() {
		List<Integer> points = new ArrayList<>();
		points.addAll(parents);
		points.add(index);
		Unit units = new Unit(points, index);
		return units;
	}

}

