
import java.util.ArrayList;
import java.util.List;

public class Utils {
	public boolean isGSkylineGroup(SGroup sGroup, PointSet pointSet) {
		if(sGroup == null) {
			System.out.println("sGroup = null");
		}
		List<Integer> curPointSet = sGroup.points;
		List<Point> pSet = pointSet.pSet;
		for(Integer i: curPointSet) {
			Point point = pSet.get(i);
			if(!curPointSet.containsAll(point.parents))
				return false;
		}
		return true;
	}
}
