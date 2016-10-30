package common.result;

import java.util.ArrayList;
import java.util.List;

/**
 * 存放所有合法的sgroup
 * @author thinkpad
 *
 */
public class ResultSet {
	/**
	 * 合法sgroup集合
	 */
    public List<SGroup> sGroups = new ArrayList<>();
    /**
     * 增加一个合法sgroup到结果集合
     * @param sGroup 给定的合法sgroup
     */
    public void add(SGroup sGroup) {
        sGroups.add(sGroup);
    }
    
    /**
     * 打印合法集合
     */
    public void print() {
        System.out.println("result set has: " + sGroups.size());
        for(SGroup sGroup: sGroups) {
            sGroup.print();
        }
    }
}
