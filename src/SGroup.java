import java.util.List;

public class SGroup {
	List<Integer> points;

	public SGroup(Unit unit) {
		points = unit.unit;
	}

	public SGroup(List<Integer> points) {

		this.points = points;
	}

	public void print() {
		for(int p: points) {
			System.out.print(p + " ");
		}
		System.out.println();
	}
}
