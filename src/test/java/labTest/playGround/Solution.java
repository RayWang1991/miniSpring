package labTest.playGround;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Solution {

    private boolean[] numInUse = new boolean[10];

    private int res = 0;

    private class DiffInfo {
        int          diffNum;
        Set<Integer> noZeroIndex = new HashSet<Integer>(4);
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.solve(new String[]{"CCD", "JC", "FJDHD"});
    }

    public void solve(final String[] input) {
        DiffInfo diffInfo = scanDiff(input);
        int numOfDiff = diffInfo.diffNum;
        Set<Integer> noZeroIndexes = diffInfo.noZeroIndex;

        genNumAt(new int[numOfDiff], noZeroIndexes, 0, new Executable() {
            public void execute(int[] permutation) {
                int[] nums = populateNumbers(permutation, input);
                if (isValidPermutation(nums)) {
                    res++;
                    //show nums
                    System.out.println(nums[0] + "*" + nums[1] + "=" + nums[2]);
                }
            }
        });
    }

    public DiffInfo scanDiff(String[] strs) {
        DiffInfo diffInfo = new DiffInfo();
        boolean[] charInUse = new boolean[26];
        int res = 0;
        for (String str : strs) {
            boolean isFirst = true;
            for (char c : str.toCharArray()) {
                if (!charInUse[c - 'A']) {
                    charInUse[c - 'A'] = true;
                    res++;
                }

                if (isFirst) {
                    diffInfo.noZeroIndex.add(res - 1);
                    isFirst = false;
                }
            }
        }
        diffInfo.diffNum = res;
        return diffInfo;
    }

    public int[] populateNumbers(int[] temp, String[] strs) {
        int[] numbers = new int[26];
        Arrays.fill(numbers, -1);
        int[] res = new int[3];
        int cur = 0;
        for (int i = 0; i < 3; i++) {
            String str = strs[i];
            int a = 0;
            for (char c : str.toCharArray()) {
                a = a * 10;
                int t;
                if ((t = numbers[c - 'A']) != -1) {
                } else {
                    t = numbers[c - 'A'] = temp[cur++];
                }
                a += t;
            }
            res[i] = a;
        }
        return res;
    }

    public boolean isValidPermutation(int[] nums) {
        return nums[0] * nums[1] == nums[2];
    }

    public void genNumAt(int[] temp, Set<Integer> noZeroIndexes, int index, Executable executable) {
        if (index == temp.length) {
            executable.execute(temp);
            return;
        }

        for (int i = 0; i < 10; i++) {
            if (isValid(index, i, noZeroIndexes)) {
                numInUse[i] = true;
                temp[index] = i;
                genNumAt(temp, noZeroIndexes, index + 1, executable);
                numInUse[i] = false;
            }
        }
    }

    public boolean isValid(int index, int num, Set<Integer> noZeroSet) {
        return !numInUse[num] && (!noZeroSet.contains(index) || num != 0);
    }

    interface Executable {
        void execute(int[] permutation);
    }
}
