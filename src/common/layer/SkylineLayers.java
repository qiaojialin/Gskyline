package common.layer;

import common.point.Point;
import common.point.PointSet;
import common.result.ResultSet;
import common.result.SGroup;
import common.unit.Unit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
public class SkylineLayers {

	public List<SkylineLayer> layers;
	
	public SkylineLayers() {
		layers = new ArrayList<>();
	}


	public void createSkylineLayers(PointSet pointSet) {
		List<Point> pSet = pointSet.pSet;
		
		pSet.get(0).layer = 0;

		//Skyline layer start from 0
		int maxLayer = 0;
		SkylineLayer layer0= new SkylineLayer(0);
		//the layer0's tail point is point0
		layer0.tail = pSet.get(0);
		layer0.points.add(0);
		layers.add(layer0);

		for(int i=1; i<pSet.size(); i++) {
//			System.out.println(i);

			//if layer0 don't dominate the point, make the point as the tail point of layer0
			if(!layer0.dominate(pointSet,  pSet.get(i))) {
//				System.out.println("layer0");
				pSet.get(i).layer = 0;
				layer0.tail = pSet.get(i);
				layer0.points.add(i);
			}

			//if maxlayer dominate the point, create a new layer
			else if(layers.get(maxLayer).dominate(pointSet,  pSet.get(i))) {
//				System.out.println("new layer");
				SkylineLayer newLayer = new SkylineLayer(++maxLayer);
				pSet.get(i).layer = maxLayer;
				newLayer.tail = pSet.get(i);
				newLayer.points.add(i);
				layers.add(newLayer);
			}

			//binary search to find layer the point belongs to
			else {
//				System.out.println("binary search to find layer");
				int low = 1;
				int high = layers.size()-1;
				while(low <= high){
					int middle = (low + high) / 2;
					//第middle层不dominate此点，middle-1层dominate此点，则属于middle层
					if(!layers.get(middle).dominate(pointSet,  pSet.get(i)) && layers.get(middle-1).dominate(pointSet,  pSet.get(i))) {
						pSet.get(i).layer = middle;
						layers.get(middle).tail =  pSet.get(i);
						layers.get(middle).points.add(i);
						break;
					} else if(layers.get(middle).dominate(pointSet, pSet.get(i))) {
						low = middle + 1;
					} else {
						high = middle - 1;
					}
				}

			}
		}
	}
	
	public void makeDSG(PointSet pointSet) {
		List<Point> pSet = pointSet.pSet;
		for(int i=0; i<pSet.size(); i++) {
			int currentLayer = pSet.get(i).layer;
			for(int l_index=0; l_index<currentLayer; l_index++) {
				SkylineLayer layer= layers.get(l_index);
				for(int p: layer.points) {
					if(pSet.get(p).dominate(pSet.get(i))) {
						pSet.get(p).children.add(i);
						pSet.get(i).parents.add(p);
					}
				}
			}
		}
	}

	public void preProcessing(PointSet pointSet, int k, ResultSet resultSet) {
		List<Point> pSet = pointSet.pSet;
		for(SkylineLayer layer: layers) {
			Iterator<Integer> iterator = layer.points.iterator();
			while(iterator.hasNext()) {
				int p = iterator.next();
				Unit unit = pSet.get(p).unit();
				if(unit.size() > k) {
					iterator.remove();
				} else if(unit.size() == k) {
					SGroup sGroup = new SGroup(unit);
					resultSet.sGroups.add(sGroup);
//					System.out.println("@++++<<<000: " + sGroup);
					iterator.remove();
				}
			}
		}
		//delete empty layer
		Iterator<SkylineLayer> iterator = layers.iterator();
		while(iterator.hasNext()) {
			SkylineLayer layer = iterator.next();
			if(layer.points.isEmpty()) {
				iterator.remove();
			}
		}
	}

	public void print() {
		System.out.println("common.layer.SkylineLayers:");
		for(SkylineLayer layer: layers) {
			layer.print();
		}
	}

}
