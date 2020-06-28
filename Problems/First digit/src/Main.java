import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // put your code here
        String number = scanner.nextLine();
        System.out.println(number.split("")[0]);
    }
}