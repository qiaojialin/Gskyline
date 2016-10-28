import algorithm.PointWise;
import algorithm.UnitWise;
import common.layer.SkylineLayers;
import common.point.PointSet;
import common.result.ResultSet;

public class GSkyline {
    public static void main(String[] args) {
        String path = "datasets\\anti_4.txt";
        int k = 2;
        ResultSet resultSet = new UnitWise().unitWise(path, k);
        //ResultSet resultSet2 = new PointWise().pointWise(path, k);

        resultSet.print();
        //resultSet2.print();
    }
}
