import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UnitWise {

	private List<Unit> units = new ArrayList<>();

	public void generateGroups(List<SkylineLayer> layers, PointSet pointSet, int k, ResultSet resultSet) {
		List<Point> pSet = pointSet.pSet;
		for(SkylineLayer layer: layers) {
			for(int p: layer.points) {
				Unit unit = pSet.get(p).unit();
				units.add(unit);
			}
		}

		Collections.sort(units, new Comparator<Unit>(){
			@Override
			public int compare(Unit p1, Unit p2) {
				Integer i1 = p1.unit.size();
				Integer i2 = p2.unit.size();
				//降序
				return -i1.compareTo(i2);
			}
		});

		for(int i=0; i<units.size(); i++) {
			Unit oneUnit = units.get(i);
			if(lastSize(i) == k) {
				List<Integer> points = new ArrayList<>();
				for(int j=i; j<units.size(); j++) {
					points.removeAll(units.get(j).unit);
					points.addAll(units.get(j).unit);
					SGroup sGroup = new SGroup(points);
					resultSet.add(sGroup);
				}
			}
		}

	}

	//查看此unit以及后面所有并集的元素个数
	public int lastSize(int u) {
		List<Integer> last = new ArrayList<>();
		for(int i=u; i<units.size(); i++) {
			Unit unit = units.get(i);
			last.removeAll(unit.unit);
			last.addAll(unit.unit);
		}
		return last.size();
	}

	//剪枝后的tail set
	public List<Unit> tailSet(int u) {

		return null;
	}
}
