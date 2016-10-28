
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

public class PointWise {
	
	public ResultSet pointWise(String path, int k) {
		PointSet pointSet = new PointSet();
		pointSet.readFromFile(path);
		pointSet.sort();
//        pointSet.print();

		SkylineLayers skylineLayers = new SkylineLayers();
		skylineLayers.createSkylineLayers(pointSet);
        skylineLayers.print();

		ResultSet resultSet = new ResultSet();
		skylineLayers.makeDSG(pointSet);
		skylineLayers.preProcessing(pointSet, k, resultSet);
//        skylineLayers.print();
//        System.out.println("done");

		List<SGroup> tmpResult = new PointWise().generateGroups(skylineLayers.layers, pointSet, k);
		
		for(SGroup sgroup: tmpResult)
			resultSet.add(sgroup);
		return resultSet;
	}
	
	public ArrayList<SGroup> generateGroups(List<SkylineLayer> layers, PointSet pointSet, int k) {
		//System.out.println(isSkylineLayer(3, pointSet));
		for(SkylineLayer layer: layers) {
			layer.print();
		}
		
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
		//System.out.println("done");
		
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
				List<Integer> tobedelete = new ArrayList<>();
				for(int index = 0; index < tailSet.size(); index++) {
					Integer pt = tailSet.get(index);
					if(!childrenSet.contains(pt) && !isSkylineLayer(pt, pointSet))
						tobedelete.add(pt);
						//tailSet.remove(index);
					Point pj = pointSet.pSet.get(pt);
					if(pj.layer - max >= 2)
						tobedelete.add(pt);
						//tailSet.remove(index);
				}

				tailSet.removeAll(tobedelete);
				
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
	public int maxLayer(SGroup sgroup, PointSet pointSet) {
		int max = -1;
		for(Integer index: sgroup.points) {
			Point a = pointSet.pSet.get(index);
			if(a.layer > max)
				max = a.layer;
		}
		return max;

	}
	
	
	//pt is the point index in the pointSet; 
	//to judge whether this point is skyline layer
	public boolean isSkylineLayer(int pt, PointSet pointSet) {
		Point p = pointSet.pSet.get(pt);
		if(p.layer == 0) return true;
		return false;
	}
	
	public List<Integer> getTailSet(List<SkylineLayer> layers, SGroup p, PointSet pointSet) {
		List<Integer> point = p.points;
		int maxlen = Integer.MAX_VALUE;
		List<Integer> result = new ArrayList<>();
		for(Integer i: point) {
			List<Integer> tmp = tailList(layers, i, pointSet);
			if(tmp.size() < maxlen) {
				result = tmp;
				maxlen = tmp.size();				
			}
		}
		return result;
	}
	
	//give layers  and a point index in the pointSet
	//return the tail list of this point
	public List<Integer> tailList(List<SkylineLayer> layers, int p, PointSet pointSet) {
		
		
		Point point = pointSet.pSet.get(p);
		int curlayer = point.layer;
		List<Integer> result = new ArrayList<>();
		//add points in the latter layer to the result
		for(int i = layers.size()-1; i > curlayer; i--) {
			result.addAll(layers.get(i).points);
		}
		//for the current layer, add the points which has larger index than this point
		List<Integer> curlayerPoints = layers.get(curlayer).points;
		int index = curlayerPoints.indexOf(p);
		for(int i = index+1; i < curlayerPoints.size(); i++)
			result.add(curlayerPoints.get(i));
		

		
		
		return result;
	}
	
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
