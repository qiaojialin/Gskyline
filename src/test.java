import java.util.*;

/**
 * Created by Administrator on 2016/10/26.
 */
public class test {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        Iterator<Integer> iterator = list.iterator();
        while(iterator.hasNext()) {
            int i = iterator.next();
            if(i == 2)
                iterator.remove();
        }
//        for(Integer i: list) {
//            if(i == 2)
//                list.remove(i);
//        }
        for(Integer i: list) {
            System.out.println(i);
        }
    }
}



