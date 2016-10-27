import algorithm.UnitWise;
import common.layer.SkylineLayers;
import common.point.PointSet;
import common.result.ResultSet;

public class GSkyline {
    public static void main(String[] args) {
        String path = "E:\\datasets\\test.txt";
        int k = 4;
        ResultSet resultSet = new UnitWise().unitWise(path, k);

        resultSet.print();
    }
}
