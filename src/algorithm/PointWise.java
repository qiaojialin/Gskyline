
package algorithm;

import common.layer.SkylineLayer;
import common.layer.SkylineLayers;
import common.point.PointSet;
import common.point.*;
import common.result.ResultSet;
import common.result.SGroup;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 * PointWise class implements the PointWise algorithm for GSkyline
 * @author thinkpad
 *
 */

public class PointWise {
	public long time = 0;
	/**
	 * 通过对原始数据进行读取，生成skyline layer，创建DSG图，预处理剪枝，pointwise算法处理，生成所有的GSkyline集合
	 * @param path 原始数据路径
	 * @param k GSkyline每个group的大小
	 * @return 所有GSkyline集合组成的结果集合
	 */
	public ResultSet pointWise(String path, int k) {
		PointSet pointSet = new PointSet();
		pointSet.readFromFile(path);
		pointSet.sort();
//        pointSet.print();

		SkylineLayers skylineLayers = new SkylineLayers();
		skylineLayers.createSkylineLayers(pointSet);
  //      skylineLayers.print();

		ResultSet resultSet = new ResultSet();
		skylineLayers.makeDSG(pointSet, k);
//		skylineLayers.print();
		skylineLayers.preProcessing(pointSet, k, resultSet);
//        skylineLayers.print();
//        System.out.println("done");
        
        long start = System.currentTimeMillis();

		List<SGroup> tmpResult = new PointWise().generateGroups(skylineLayers.layers, pointSet, k);
		
		long end = System.currentTimeMillis();
		System.out.println("PointWise"+(end-start));
		time = end - start;
		
		for(SGroup sgroup: tmpResult)
			resultSet.add(sgroup);
		return resultSet;
	}
	
	/**
	 * 核心算法PointWise，生成所有的skyline group
	 * @param layers 经过预处理之后的skyline layers
	 * @param pointSet 经过预处理的输入点集
	 * @param k 需要的skyline group大小
	 * @return 所有skyline group,以链表形式存储在SGroup中
	 */
	public ArrayList<SGroup> generateGroups(List<SkylineLayer> layers, PointSet pointSet, int k) {
	//	System.out.println("layers size:"+layers.size());
		
		
		if(layers == null || pointSet == null)
			System.out.println("layers is null or pointset is null");
		ArrayList<SGroup> result = new ArrayList<>();
		
		//initial for the k = 1
		List<Integer> initialset = layers.get(0).points;		
		for(Integer i: initialset) {
			ArrayList<Integer> initial = new ArrayList<>();
			initial.add(i);
			SGroup group = new SGroup(initial);
			result.add(group);			
		}
		
		for(int i = 2; i <= k; i++) {
			
			//result中的每一个sgroup
			ArrayList<SGroup> newresult = new ArrayList<>();
			for(SGroup sg: result) {
				//求出每一个元素的CS，合并成CS集合
				ArrayList<Integer> childrenSet = new ArrayList<>();
				for(Integer tmp: sg.points) {
					Point pl = pointSet.pSet.get(tmp);
					childrenSet.addAll(pl.children);
				}
				//求这个sgroup中的所有元素的最大层数
				int max = maxLayer(sg, pointSet);
				//求这个sgroup的tailset,并对其进行剪枝
				List<Integer> tailSet = getTailSet(layers, sg, pointSet);

				
				Iterator<Integer> it = tailSet.iterator();
				while(it.hasNext()) {
					Integer pt = it.next();
					if(!childrenSet.contains(pt) && !isSkylineLayer(pt, pointSet)) {
						it.remove();
						continue;
					}
					Point pj = pointSet.pSet.get(pt);
					if(pj.layer - max >= 2)
						it.remove();					
				}
				
				
				
				for(Integer etailp: tailSet) {
					ArrayList<Integer> a = new ArrayList<>();
					a.addAll(sg.points);
					a.add(etailp);
					SGroup tmpGroup = new SGroup(a);
					if(isValidSGroup(tmpGroup, pointSet))
						newresult.add(tmpGroup);
				}
			}
			result = newresult;
			

			
		}	
		
		return result;
		

	}
	
	/**
	 * Sgroup中的点的最大层数
	 * @param sgroup 给定的sgroup
	 * @param pointSet 包含信息的点集
	 * @return sgroup中的最大层数点的层数
	 */
	public int maxLayer(SGroup sgroup, PointSet pointSet) {
		int max = -1;
		for(Integer index: sgroup.points) {
			Point a = pointSet.pSet.get(index);
			if(a.layer > max)
				max = a.layer;
		}
		return max;

	}
	

	/**
	 * 判断一个点是不是skyline layer,即判断一个点的层数是不是0
	 * @param pt 需要判断的点在pointSet中的下标
	 * @param pointSet 含有信息的点集
	 * @return 若这个点是skyline layer则返回true;否则返回false
	 */
	public boolean isSkylineLayer(int pt, PointSet pointSet) {
		Point p = pointSet.pSet.get(pt);
		if(p.layer == 0) return true;
		return false;
	}
	
	/**
	 * 寻找一个sgroup的尾集，即为这个group中的每一个元素中，先找到在处理过的skyline layers中位置最靠后的点
	 * （位置靠后代表层数大，或在层数相同时在所在层的下标大）对于最靠后的点，取比这个点更靠后的所有点的集合
	 * @param layers 经过预处理之后的skyline layers
	 * @param p 输入的sgroup
	 * @param pointSet 经过处理的点集
	 * @return 这个sgroup的尾集
	 */
	public List<Integer> getTailSet(List<SkylineLayer> layers, SGroup p, PointSet pointSet) {
		
		List<Integer> point = p.points;
		List<Integer> result = new ArrayList<>();
		
		int maxlayer = -1, maxindex = -1;
		for(Integer pt: point) {
			Point aPoint = pointSet.pSet.get(pt);
			int layer = aPoint.layer;
			int index = layers.get(layer).points.indexOf(pt);
			if(layer >= maxlayer) {
					maxlayer = layer;
					maxindex = index;
			} else if(layer == maxlayer) {
				if(index > maxindex) {
					maxlayer = layer;
					maxindex = index;
				}
				
			}
		}
		
		//add points in the latter layer to the result
		for(int i = layers.size()-1; i > maxlayer; i--) {
			result.addAll(layers.get(i).points);
		}
		//for the current layer, add the points which has larger index than this point
		List<Integer> curlayerPoints = layers.get(maxlayer).points;

		for(int i = maxindex+1; i < curlayerPoints.size(); i++)
			result.add(curlayerPoints.get(i));
				
		return result;
	}
	
	/**
	 * 判断一个sgroup是否符合合法-合法的sgroup的所有父节点都在这个sgroup中
	 * @param sg 给定的sgroup
	 * @param pointSet 经过处理的点集
	 * @return 若合法则返回true,否则返回false
	 */
	public boolean isValidSGroup(SGroup sg, PointSet pointSet) {
		List<Integer> grouplist = sg.points;
		for(Integer pt: grouplist) {
			Point point = pointSet.pSet.get(pt);
			List<Integer> parents = point.parents;
			if(!grouplist.containsAll(parents))
				return false;
		}
		return true;
	}
	
}
