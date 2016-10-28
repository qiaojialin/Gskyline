package common.layer;

import common.point.Point;
import common.point.PointSet;

import java.util.ArrayList;
import java.util.List;

/**
 * 存储skyline layer中的信息包括：层数，包含点在点集中的下标集合，层中最后一个元素
 * @author thinkpad
 *
 */

public class SkylineLayer {
	/**
	 * 层中包含点在点集中的下标
	 */
    public List<Integer> points = new ArrayList<>();
    /**
     * 层序号，从0起
     */
    int layer = -1;
    /**
     * 层中最后一个点
     */
    Point tail = null;

    /**
     * 根据层序号构造一个层
     * @param l 层序号
     */
    SkylineLayer(int l) {
        layer = l;
    }
    
    /**
     * 判断本层的点是否可以dominate给定点
     * @param pointSet 输入点集
     * @param point 给定点
     * @return 如果这层可以dominate这个点，返回true
     */

    boolean dominate(PointSet pointSet, Point point) {
        //对二维数据比较layer的tail point
        if(point.dimension == 2) {
            return tail.dominate(point);
        } //对高维数据比较layer中的所有点
        else {
            List<Point> pSet = pointSet.pSet;
            for(int p: points) {
                //只要有一个layer中的点dominate这个点，就返回true
                if(pSet.get(p).dominate(point)) {
                    return true;
                }
            }
            //如果本层的点都不dominate此点，则本层不dominate此点
            return false;
        }
    }

    public void print(){
        System.out.print("layer" + layer + ": ");
        for(Integer i: points) {
            System.out.print(" " + i);
        }
        System.out.println();
    }
}