import java.util.function.LongToDoubleFunction;

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
        String path = "datasets\\corr_6.txt";
        int k = 4;

        System.out.println(path+" "+"k: "+k);
        
  //      ResultSet resultSet1 = new PointWise().pointWise(path, k);
  //      resultSet1.print();

        ResultSet resultSet2 = new UnitWise().unitWise(path, k);
       resultSet2.print();

    //    System.out.println(resultSet1.sGroups.size()+" hha "+resultSet2.sGroups.size());
    }
}
