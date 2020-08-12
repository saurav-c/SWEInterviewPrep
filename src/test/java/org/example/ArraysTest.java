package org.example;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ArraysTest {

    @Test
    public void testMultiply() {
        // 123 * 45
        Assert.assertArrayEquals(intToArray(123 * 45),
                                ArrayProblems.multiply(intToArray(123), intToArray(45)));
        // -1234 * 568
        Assert.assertArrayEquals(intToArray(-1234 * 568),
                ArrayProblems.multiply(intToArray(-1234), intToArray(568)));
        // -237 * -649
        Assert.assertArrayEquals(intToArray(-237 * -649),
                ArrayProblems.multiply(intToArray(-237), intToArray(-649)));
        // 100 * 0
        Assert.assertArrayEquals(intToArray(100 * 0),
                ArrayProblems.multiply(intToArray(100), intToArray(0)));
        // 450 * 1
        Assert.assertArrayEquals(intToArray(450 * 1),
                ArrayProblems.multiply(intToArray(450), intToArray(1)));
    }

    private int[] intToArray(int n) {
        int signBit = n < 0 ? -1 : 1;
        n = Math.abs(n);

        String s = Integer.toString(n);
        int[] nArray = s.chars().map(c -> c - '0').toArray();
        nArray[0] *= signBit;
        return nArray;
    }

    @Test
    public void testCanReachEnd() {
        List<Integer> board1 = Arrays.asList(3, 3, 1, 0, 2, 0, 1);
        Assert.assertTrue(ArrayProblems.canReachEnd(board1));

        List<Integer> board2 = Arrays.asList(3, 2, 0, 0, 2, 0, 1);
        Assert.assertFalse(ArrayProblems.canReachEnd(board2));
    }

    @Test
    public void testDeleteDuplicates() {
        int[] a = new int[]{2, 3, 5, 5, 7, 11, 11, 11, 13};
        Assert.assertEquals(6, ArrayProblems.deleteDuplicates(a));
        Assert.assertArrayEquals(new int[]{2, 3, 5, 7, 11, 13}, Arrays.copyOfRange(a, 0, 6));
    }

    @Test
    public void testBuyAndSellOnce() {
        int[] prices = new int[]{310, 315, 275, 295, 260, 270, 290, 230, 255, 250};
        Assert.assertEquals(30, ArrayProblems.buyAndSellOnce(prices));

        // Do not buy
        int[] downPrices = new int[]{300, 275, 270, 200, 150, 50};
        Assert.assertEquals(0, ArrayProblems.buyAndSellOnce(downPrices));
    }

    @Test
    public void testBuyAndSellTwice() {
        int[] prices = new int[]{12, 11, 13, 9, 12, 8, 14, 13, 15};
        Assert.assertEquals(10, ArrayProblems.buyAndSellTwice(prices));
    }

    @Test
    public void testEnumeratePrime() {
        Assert.assertArrayEquals(new int[] {2, 3, 5, 7, 11, 13},
                ArrayProblems.enumeratePrimes(15));
    }

    @Test
    public void testPermuteElements() {
        String[] elems = new String[] {"A", "B", "C", "D"};
        int[] permutations = new int[] {3, 0, 1, 2};
        ArrayProblems.permuteElements(elems, permutations);
        Assert.assertArrayEquals(new String[] {"B", "C", "D", "A"}, elems);
    }

    @Test
    public void testNextPermutation() {
        int[] elems = new int[] {1, 4, 2, 3};
        elems = ArrayProblems.nextPermutation(elems);
        Assert.assertArrayEquals(new int[] {1, 4, 3, 2}, elems);
        elems = ArrayProblems.nextPermutation(elems);
        Assert.assertArrayEquals(new int[] {2, 1, 3, 4}, elems);

        elems = new int[] {4, 3, 2, 1};
        elems = ArrayProblems.nextPermutation(elems);
        Assert.assertArrayEquals(new int[]{}, elems);
    }

    @Test
    public void testRandomSubset() {
        int[] elem1 = new int[] {1, 2, 3, 4, 5};
        int[] elem2 = new int[] {1, 2, 3, 4, 5};
        ArrayProblems.randomSubset(elem1, 3);
        ArrayProblems.randomSubset(elem2, 3);

        try {
            Assert.assertArrayEquals(Arrays.copyOf(elem1, 3), Arrays.copyOf(elem2, 3));
        } catch (AssertionError e) {
            return;
        }
        assert false;
    }

    @Test
    public void testRandomSubsetStream() {
        List<Integer> a = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            a.add(i);
        }

        Iterator<Integer> iter = a.iterator();
        List<Integer> subset1 = ArrayProblems.randomSubsetStream(iter, 7);

        iter = a.iterator();
        List<Integer> subset2 = ArrayProblems.randomSubsetStream(iter, 7);

        Integer[] arr1 = subset1.toArray(new Integer[0]);
        Integer[] arr2 = subset2.toArray(new Integer[0]);

        Assert.assertFalse(Arrays.deepEquals(arr1, arr2));
    }

    @Test
    public void testRandomPermutation() {
        int[] permutation1 = ArrayProblems.randomPermutation(10);
        Integer[] p1 = Arrays.stream(permutation1).boxed().toArray(Integer[]::new);

        int[] permutation2 = ArrayProblems.randomPermutation(10);
        Integer[] p2 = Arrays.stream(permutation2).boxed().toArray(Integer[]::new);

        Assert.assertFalse(Arrays.deepEquals(p1, p2));
    }

     @Test
    public void testRandomSubset2() {
        List<Integer> s1 = ArrayProblems.randomSubset(20, 5);
        List<Integer> s2 = ArrayProblems.randomSubset(20, 5);

        Assert.assertFalse(Arrays.deepEquals(s1.toArray(new Integer[0]), s2.toArray(new Integer[0])));
     }

     @Test
    public void testRandomInt() {
        int[] values = new int[] {1, 2, 3, 4};
        double[] probabilities = new double[] {0.1, 0.2, 0.3, 0.4};
        int x = ArrayProblems.randomIntFromDistribution(values, probabilities);
     }

     @Test
    public void testSpiralOrder() {
        List<List<Integer>> arr = new ArrayList<>();

        int start = 1;
        for (int i = 0; i < 3; i++) {
            arr.add(new ArrayList<>());
            for (int j = 0; j < 3; j++) {
                arr.get(i).add(start + j * 3);
            }
            start++;
        }

        arr.forEach(x -> x.forEach(System.out::println));
        System.out.println();
        ArrayProblems.spiralOrder(arr);
     }

     @Test
    public void testPascals() {
        int[][] p = ArrayProblems.pascals(6);
     }
}
