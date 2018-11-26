package labTest.playGround;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Solution4 {
    private class Node {
        int          value;
        Set<Integer> dependOns = new HashSet<Integer>();
    }

    public void resolve(Scanner sc) {
    }


    public static void main(String[] args) {
        assert args != null && args.length == 1;
        Scanner sc = scanner(args[0]);
//            testWeightQuickUnion(sc);
        sc.close();
    }

    public static Scanner scanner(String arg) {
        try {
            System.out.println(arg);
            FileInputStream f = new FileInputStream(arg);
            Scanner sc = new Scanner(f);
            return sc;
        } catch (FileNotFoundException e) {
            e.printStackTrace(System.out);
        }
        return null;
    }
}
