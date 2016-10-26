import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Administrator on 2016/10/26.
 */
public class test {
    public static void main(String[] args) {
        User user = new User();
        user.a = 1;
        user.b = 2;
        set(user);
        System.out.print(user.a);
    }

    public static void set(User u) {
        User user = u;
        user.a = 2;
    }

    static class User {
        public int a;
        public int b;
    }
}



