import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Reversebinary {

    public static void main(String[] args) {

        int n = Integer.parseInt(getLine()); //No error-handling for simplicity
        final double length = Math.floor(Math.log(n) / Math.log(2)) + 1, iterations = length / 2;

        //We get the number, measure its length in binary and call the method that will exchange each couple of bits
        for (int i = 0; i < iterations; i++) {
            n = exchangeBits(n, i, (int) length - i - 1);
        }

        System.out.println(n);
    }

    /**
     * This method is based in the XOR operation
     *
     * @param n The number the bits are in.
     * @param i Index of one of the bits to be exchanged, zero-indexed from the right
     * @param j Index of the other bit to be exchanged, zero-indexed from the right
     * @return The value of the given integer with the indicated bits exchanged
     */
    private static int exchangeBits(int n, int i, int j) {
        int a = (n >> i) & 1;
        int b = (n >> j) & 1;

        /**
         * If both values are the same, do nothing.
         */
        if ((a ^ b) == 0) {
            return n;
        }

        /**
         * Otherwise apply the change by using a regular OR to select the bits and XOR to perform
         * the switch. Note that the switch that XOR triggers is guaranteed to be correct because
         * we know that (1) the bits are different and (2) binary only features two values.
         */
        return n ^= (1 << i) | (1 << j);
    }

    /**
     * This could definitively be better, but I don't think that's the point of the puzzle anyway
     */
    private static String getLine() {
        String r = null;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            r = br.readLine();
        } catch (IOException io) {
            io.printStackTrace(System.err);
        }

        return r;
    }
}