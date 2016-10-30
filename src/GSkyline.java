import algorithm.PointWise;
import algorithm.UnitWise;
import common.result.ResultSet;

/**
 * GSkyline provides platform to implement and evaluate PointWise and UnitWise
 * @author thinkpad
 *
 */

public class GSkyline {

    public static void main(String[] args) {
        String path = "E:\\datasets\\test.txt";
        int k = 4;

        ResultSet resultSet2 = new PointWise().pointWise(path, k);

        ResultSet resultSet = new UnitWise().unitWise(path, k);
               
        System.out.println(path+" "+"k"+k);

        resultSet.print();
        resultSet2.print();
    }
}
