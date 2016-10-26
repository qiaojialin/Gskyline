import java.util.ArrayList;
import java.util.List;
public class SkylineLayers {

	public List<SkylineLayer> layers;


	public SkylineLayers() {
		layers = new ArrayList<>();
	}

	public void createSkylineLayers(PointSet pointSet) {
		List<Point> pSet = pointSet.pSet;
		pSet.get(0).layer = 0;

		//Skyline layer从0开始计数
		int maxLayer = 0;
		SkylineLayer layer0= new SkylineLayer(0);
		//第一层的初始尾点是p0，同时加到第一层点集中
		layer0.tail = pSet.get(0);
		layer0.points.add(0);
		layers.add(layer0);

		for(int i=1; i<pSet.size(); i++) {

			//如果第0层不dominate此点，则变成第0层的尾点
			if(!layer0.dominate(pointSet,  pSet.get(i))) {
				pSet.get(i).layer = 0;
				layer0.tail = pSet.get(i);
				layer0.points.add(i);
			}

			//如果最高层dominate此点，则新建一层
			else if(layers.get(maxLayer).dominate(pointSet,  pSet.get(i))) {
				SkylineLayer newLayer = new SkylineLayer(++maxLayer);
				pSet.get(i).layer = maxLayer;
				newLayer.tail = pSet.get(i);
				newLayer.points.add(i);
				layers.add(newLayer);
			}

			//二分查找点属于的layer
			else {
				int low = 1;
				int high = layers.size()-1;
				while(low <= high){
					int middle = (low + high) / 2;
					//第middle层不dominate此点，middle-1层dominate此点，则属于middle层
					if(!layers.get(middle).dominate(pointSet,  pSet.get(i)) && layers.get(middle-1).dominate(pointSet,  pSet.get(i))) {
						pSet.get(i).layer = middle;
						layers.get(middle).tail =  pSet.get(i);
						layers.get(middle).points.add(i);
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
			for(Integer p: layer.points) {
				Unit unit = pSet.get(p).unit();
				if(unit.size() > k){
					layer.points.remove(p);
				} else if(unit.size() == k) {
					SGroup sGroup = new SGroup(unit);
					resultSet.sGroups.add(sGroup);
				}
			}
		}
	}

}
