import java.util.Scanner;
class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scn = new Scanner(System.in);
        String input = scn.nextLine();
        String[] inputArray = input.split(" ");
        boolean isAlphabetical = true;
        for (int i = 0; i + 1 < inputArray.length; i++) {
            int comparison = inputArray[i].compareTo(inputArray[i + 1]);
            if (comparison > 0) {
                isAlphabetical = false;
                break;
            }
        }
        System.out.print(isAlphabetical);
    }
}