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

		//Skyline layer浠�0寮�濮嬭鏁�
		int maxLayer = 0;
		SkylineLayer layer0= new SkylineLayer(0);
		//绗竴灞傜殑鍒濆灏剧偣鏄痯0锛屽悓鏃跺姞鍒扮涓�灞傜偣闆嗕腑
		layer0.tail = pSet.get(0);
		layer0.points.add(0);
		layers.add(layer0);

		for(int i=1; i<pSet.size(); i++) {

			//濡傛灉绗�0灞備笉dominate姝ょ偣锛屽垯鍙樻垚绗�0灞傜殑灏剧偣
			if(!layer0.dominate(pointSet, pSet.get(i))) {
				pSet.get(i).layer = 0;
				layer0.tail = pSet.get(i);
				layer0.points.add(i);
			}

			//濡傛灉鏈�楂樺眰dominate姝ょ偣锛屽垯鏂板缓涓�灞�
			else if(layers.get(maxLayer).dominate(pointSet,  pSet.get(i))) {
				SkylineLayer newLayer = new SkylineLayer(++maxLayer);
				pSet.get(i).layer = maxLayer;
				newLayer.tail = pSet.get(i);
				newLayer.points.add(i);
				layers.add(newLayer);
			}

			//浜屽垎鏌ユ壘鐐瑰睘浜庣殑layer
			else {
				int low = 1;
				int high = layers.size()-1;
				while(low <= high){
					int middle = (low + high) / 2;
					//绗琺iddle灞備笉dominate姝ょ偣锛宮iddle-1灞俤ominate姝ょ偣锛屽垯灞炰簬middle灞�
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
	
	public List<List<Point>> nDsSkyline(List<Point> plist, int ds) {
		return null;
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
	
	
	public void preProcessing(PointSet pointSet) {
		
	}

	
	


}

