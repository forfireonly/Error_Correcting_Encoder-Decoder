import java.util.Scanner;
class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scn = new Scanner(System.in);
        int newInteger = scn.nextInt();
        System.out.print(newInteger > -15 && newInteger <= 12 ||
                newInteger > 14 && newInteger < 17 ||
                newInteger >= 19 ? "True" : "False");

    }
}