import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Test {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        var rng = new Random();
        for (var i = 0; i < 10; i++) {
            list.add(rng.nextInt(0, 50));
        }

        System.out.println(list);
    }
}
