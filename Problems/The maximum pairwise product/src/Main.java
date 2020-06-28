import java.util.Scanner;
class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scn = new Scanner(System.in);
        int numberOfInts = scn.nextInt();
        int[] arrayOfInts = new int[numberOfInts];
        int max = 0;

        for (int i = 0; i < numberOfInts; i++) {
            arrayOfInts[i] = scn.nextInt();
        }
        for (int i = 0; i < numberOfInts - 1; i++) {
            for (int j = i + 1; j < numberOfInts; j++) {
                if (arrayOfInts[i] * arrayOfInts[j] > max) {
                    max = arrayOfInts[i] * arrayOfInts[j];
                }
            }
        }
        System.out.print(max);
    }
}