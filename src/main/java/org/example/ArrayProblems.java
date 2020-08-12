package org.example;

import java.util.*;

class ArrayProblems {

    /**
     * Takes two integers stored as an array and returns the product as an array.
     * Time Complexity: O(nm) : O(1) operations performed on each digit of the partial product
     * Space Complexity: O(nm)
     */
    static int[] multiply(int[] a, int[] b) {
        int signBit = (a[0] < 0 ) ^ (b[0] < 0) ? -1 : 1;
        a[0] = Math.abs(a[0]);
        b[0] = Math.abs(b[0]);

        // Number of digits in product is at most sum of number of digits of the factors
        int[] c = new int[a.length + b.length];

        for (int i = a.length - 1; i >= 0; i--) {
            for (int j = b.length - 1; j >= 0; j--) {
                int n = c[i + j + 1] + a[i] * b[j];
                c[i + j + 1] = n % 10;
                c[i + j] += n / 10;
            }
        }

        int firstNonZero = 0;
        while(firstNonZero < c.length && c[firstNonZero] == 0) {
            firstNonZero++;
        }

        if (firstNonZero == c.length) {
            return new int[]{0};
        }

        c = Arrays.copyOfRange(c, firstNonZero, c.length);
        c[0] *= signBit;
        return c;
    }

    /**
     * Given an array where from position i you can advance [0, A[i]] steps,
     * returns whether the last position is reachable.
     * Time Complexity: O(N)
     * Space Complexity: O(1)
     */
    static boolean canReachEnd(List<Integer> maxAdvanceSteps) {
        int maxReached = 0, needToReach = maxAdvanceSteps.size() - 1;
        for (int i = 0; i <= maxReached && maxReached < needToReach; i++) {
            maxReached = Math.max(maxReached, i + maxAdvanceSteps.get(i));
        }
        return maxReached >= needToReach;
    }

    /**
     * Deletes duplicates from A. Returns number of unique elements.
     * Time Complexity: O(N)
     * Space Complexity: O(1)
     */
    static int deleteDuplicates(int[] a) {
        if (a.length <= 1) {
            return a.length;
        }

        int toWrite = 1;
        for (int i = 1; i < a.length; i++) {
            // System.out.printf("ToWrite = %d, i = %d\n", toWrite, i);
            // printArray(a);
            if (a[i] != a[toWrite - 1]) {
                a[toWrite++] = a[i];
            }
            // printArray(a);
        }
        return toWrite;
    }

    static void printArray(int[] a) {
        System.out.print("[");
        for (int n: a) {
            System.out.printf(" %d", n);
        }
        System.out.println(" ]");
    }

    /**
     * Takes an array of a stock's daily prices. Returns the maximum profit that
     * can be made by buying and selling once.
     * Time Complexity: O(N)
     * Space Complexity: O(1)
     */
    static int buyAndSellOnce(int[] prices) {
        int maxProfit = 0, minPrice = prices[0];
        for (int i = 1; i < prices.length; i++) {
            maxProfit = Math.max(maxProfit, prices[i] - minPrice);
            minPrice = Math.min(minPrice, prices[i]);
        }
        return maxProfit;
    }

    /**
     * Returns maximum profit from buying and selling stock at most twice.
     * Second buy must be on a day after the first sell.
     * Time Complexity: O(N)
     * Space Complexity: O(N)
     */
    static int buyAndSellTwice(int[] prices) {
        int[] maxProfitBy = new int[prices.length];

        int maxProfit = 0, minPrice = Integer.MAX_VALUE;
        for (int i = 0; i < prices.length; i++) {
            minPrice = Math.min(minPrice, prices[i]);
            maxProfit = Math.max(maxProfit, prices[i] - minPrice);
            maxProfitBy[i] = maxProfit;
        }

        int maxPrice = -1; // Setting this to Integer.MIN_VALUE results in integer overflow
        for (int i = prices.length - 1; i > 0; i--) {
            maxProfit = Math.max(maxProfit, maxPrice - prices[i] + maxProfitBy[i - 1]);
            maxPrice = Math.max(maxPrice, prices[i]);
        }
        return maxProfit;
    }

    /**
     * Returns a list of all primes in [2, n]
     * Time Complexity: O(N log log N) --> (1/2 + 1/3 + 1/5 + 1/7 + 1/11 + ...)
     * Space Complexity: O(N)
     */
    static int[] enumeratePrimes(int n) {
        // It bit at index i is set, i is a composite number
        BitSet composites = new BitSet(n + 1);
        composites.set(0, 2);

        List<Integer> primes = new ArrayList<>();

        for (int i = 2; i <= n; i++) {
            if (!composites.get(i)) {
                primes.add(i);
                for (int j = 2 * i; j <= n; j += i) {
                    composites.set(j);
                }
            }
        }
        return primes.stream().mapToInt(i -> i).toArray();
    }

    /**
     * Given a permutation P applies it to ELEMENTS
     * Time Complexity: O(N)
     * Space Complexity: O(1)
     */
    static <E> void permuteElements(E[] elements, int[] permutation) {
        for (int i = 0; i < elements.length; i++) {
            int next = i;
            while (permutation[next] >= 0) {
                swapArrays(elements, i, permutation[next]);
                int temp = permutation[next];
                // Indicates that this move has been performed
                permutation[next] -= permutation.length;
                next = temp;
            }
        }

        // Reset permutation
        for (int i = 0; i < permutation.length; i++) {
            permutation[i] += permutation.length;
        }
    }

    private static <E> void swapArrays(E[] arr, int i, int j) {
        E temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private static void swapArrays(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * Reverses the array ARR in place from index i to index k inclusive
     */
    private static void reverseArray(int[] arr, int i, int j) {
        while (i < j) {
            swapArrays(arr, i, j);
            i++;
            j--;
        }
    }

    /**
     * Given a permutation of elements, returns the next dictionary
     * ordering permutation. Returns empty list if this is the last
     * permutation.
     * i.e. If input its [1, 0, 3, 2], return [1, 2, 0, 3]
     * Time Complexity: O(N)
     * Space Complexity: O(1)
     */
    static int[] nextPermutation(int[] elements) {
        int k = elements.length - 2;
        while (k >= 0 && elements[k] > elements[k + 1]) {
            k--;
        }

        // Entire array is decreasing --> there are no more permutations
        if (k == -1) {
            return new int[0];
        }

        // Swap smallest entry in suffix greater than entry at k with k
        for (int i = elements.length - 1; i > k; i--) {
            if (elements[i] > elements[k]) {
                swapArrays(elements, i, k);
                break;
            }
        }

        // Reverse the suffix [k + 1, elements.length - 1]
        reverseArray(elements, k + 1, elements.length - 1);
        return elements;
    }

    /**
     * Returns a random subset of size K
     * Time Complexity: O(K)
     * Space Complexity: O(1)
     */
    static void randomSubset(int[] elems, int k) {
        Random rand = new Random();
        for (int i = 0; i < k; i++) {
            int r = rand.nextInt(elems.length - i) + i;
            swapArrays(elems, i, r);
        }
    }

    /**
     * Returns a random subset of size K from a stream
     * Time Complexity: O(N)
     * Space Complexity: O(K)
     */
    static <E> List<E> randomSubsetStream(Iterator<E> stream, int k) {
        List<E> subset = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            subset.add(stream.next());
        }

        int count = k;
        Random rand = new Random();
        while (stream.hasNext()) {
            count++;
            E elem = stream.next();
            int r = rand.nextInt(count);
            if (r < k) {
                subset.set(r, elem);
            }
        }
        return subset;
    }

    /**
     * Returns a random permutation of {0, 1, .., n-1}
     * Time Complexity: O(N)
     * Space Complexity: O(N)
     */
    static int[] randomPermutation(int n) {
        int[] permutation = new int[n];
        for (int i = 0; i < n; i++) {
            permutation[i] = i;
        }
        randomSubset(permutation, n);
        return permutation;
    }

    /**
     * Returns a random subset of size K from the set {0, 1, n-1}
     * Time Complexity: O(K)
     * Space Complexity: O(K)
     */
    static List<Integer> randomSubset(int n, int k) {
        List<Integer> subset = new ArrayList<>();
        HashMap<Integer, Integer> seen = new HashMap<>();
        Random random = new Random();
        for (int i = 0; i < k; i++) {
            int r = random.nextInt(n - i);
            int v1 = seen.getOrDefault(i, -1);
            int v2 = seen.getOrDefault(r, -1);

            if (v1 < 0 && v2 < 0) {
                seen.put(i, r);
                seen.put(r, i);
            } else if (v1 < 0) {
                seen.put(i, v2);
                seen.put(r, i);
            } else if (v2 < 0) {
                seen.put(i, r);
                seen.put(r, v1);
            } else {
                seen.put(i, v2);
                seen.put(r, v1);
            }
        }
        for (int i = 0; i < k; i++) {
            subset.add(seen.get(i));
        }
        return subset;
    }

    /**
     * Returns a value from a list given probability distribution
     * Time Complexity: O(N)
     * Space Complexity: O(1)
     *
     * Another solution is to construct a list of the interval prefixes explicitly
     * and then use binary search to find the interval that the random double
     * falls in. This takes O(N) space and O(N) time for the initial call, but
     * latter calls take only O(log N)
     */
    static int randomIntFromDistribution(int[] values, double[] probabilities) {
        assert values.length == probabilities.length;
        assert Arrays.stream(probabilities).sum() == 1;

        double r = new Random().nextDouble();
        double lower = 0, upper = 0;
        for (int i = 0; i < probabilities.length; i++) {
            upper += probabilities[i];
            if (r > lower && r <= upper) {
                return values[i];
            }
            lower += probabilities[i];
        }
        // Should never reach here
        return -1;
    }

    /**
     * Returns the spiral order for a 2D array
     * i.e.
     * Spiral order for the following is: 1, 2, 3, 6, 9, 8, 7, 4, 5
     * 1 | 2 | 3
     * 4 | 5 | 6
     * 7 | 8 | 9
     *
     * Time Complexity: O(N)
     * Space Complexity: O(N)
     */
    static List<Integer> spiralOrder(List<List<Integer>> arr) {
        List<Integer> spiral = new ArrayList<>();
        spiralOrderHelper(arr, spiral, 0, arr.size() - 1, 0, arr.get(0).size() - 1);
        printList(spiral);
        return spiral;
    }

    static void spiralOrderHelper(List<List<Integer>> arr, List<Integer> order, int rStart, int rEnd, int cStart, int cEnd) {
        // Top row
        for (int i = rStart; i <= rEnd; i++) {
            order.add(arr.get(i).get(cStart));
        }

        // Right Column
        for (int i = cStart + 1; i <= cEnd; i++) {
            order.add(arr.get(rEnd).get(i));
        }

        // Bottom row
        for (int i = rEnd - 1; i >= rStart; i--) {
            order.add(arr.get(i).get(cEnd));
        }

        // Left Column
        for (int i = cEnd - 1; i >= cStart + 1; i--) {
            order.add(arr.get(rStart).get(i));
        }

        if (rEnd - rStart > 1 || cEnd - cStart > 1) {
            spiralOrderHelper(arr, order, rStart + 1, rEnd - 1, cStart + 1, cStart - 1);
        }
    }

    static void printList(List<Integer> list) {
        list.forEach(System.out::println);
    }

    /**
     * Returns the first N rows of Pascal's triangle
     * Time Complexity: O(N^2)
     * Space Complexity: O(N^2)
     */
    static int[][] pascals(int n) {
        int[][] pascals = new int[n][];
        int length = 1;
        for (int i = 0; i < n; i++) {
            pascals[i] = new int[length];

            // Fill in 1's
            pascals[i][0] = 1;
            pascals[i][length - 1] = 1;

            for (int j = 1; j < length - 1; j++) {
                pascals[i][j] = pascals[i-1][j-1] + pascals[i-1][j];
            }
            length++;
        }

        for (int i = 0; i < pascals.length; i++) {
            printArray(pascals[i]);
        }
        return pascals;
    }
}
