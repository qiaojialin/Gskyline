import java.util.ArrayList;
import java.util.List;

public class PointWise {
	public void generateGroups(List<SkylineLayer> layers, PointSet pointSet, int k) {
		if(layers == null || pointSet == null)
			System.out.println("layers is null or pointset is null");
		ArrayList<SGroup> result = new ArrayList<>();
		ArrayList<SGroup> curGroupLayer = new ArrayList<>();
		for(int i = 1; i <= k; i++) {
			for(SGroup sg: result) {
				ArrayList<Integer> childrenSet = new ArrayList<>();
				for(Integer tmp: sg.points) {
					Point pl = pointSet.pSet.get(tmp);
					childrenSet.addAll(pl.children);
				}
				int max = maxLayer(sg, pointSet);
				List<Integer> tailSet = getTailSet(layers, sg);
				for(int index = 0; index < tailSet.size(); index++) {
					Integer pt = tailSet.get(index);
					if(!childrenSet.contains(pt) && !isSkylineLayer(pt, layers))
						tailSet.remove(index);
					Point pj = pointSet.pSet.get(index);
					if(pj.layer - max >= 2)
						tailSet.remove(index);
				}
				for(Integer remainp: tailSet) {
					
				}
			}
			
		}		
		

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
	
	
	//pt is the point index in the pointSet; layers is the current skylinelayers
	//to judge whether this point is skyline layer
	public boolean isSkylineLayer(int pt, List<SkylineLayer> layers) {
		return false;
	}
	
	public List<Integer> getTailSet(List<SkylineLayer> layers, SGroup p) {
		return null;
	}
	
	public List<Integer> tailList(List<SkylineLayer> layers, int p) {
		
		return null;
	}
	
	
	
	
}
