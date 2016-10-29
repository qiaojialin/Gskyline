package common.layer;

import common.point.Point;
import common.point.PointSet;

import java.util.ArrayList;
import java.util.List;


public class SkylineLayer {
    public List<Integer> points = new ArrayList<>();
    int layer = -1;
    Point tail = null;

    SkylineLayer(int l) {
        layer = l;
    }

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