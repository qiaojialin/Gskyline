package common.result;

import java.util.ArrayList;
import java.util.List;

/**
 * 存放所有合法的sgroup
 * @author thinkpad
 *
 */
public class ResultSet {
    public long num = 0;
	/**
	 * 合法sgroup集合
	 */
    public List<SGroup> sGroups = new ArrayList<>();
    /**
     * 增加一个合法sgroup到结果集合
     * @param sGroup 给定的合法sgroup
     */
    public void add(SGroup sGroup) {
//        sGroups.add(sGroup);
        num++;
    }
    
    /**
     * 打印合法集合
     */
    public void print() {
        System.out.println("result set has: " + num);
        int cnt = 0;
 //   	for(SGroup sGroup: sGroups) {
 //           sGroup.print();
 //           cnt++;
 //       }
  //  	System.out.println(cnt+"haha");
    
    }
}
