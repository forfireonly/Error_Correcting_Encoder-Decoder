import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // put your code here
        int numberOfButtercups = scanner.nextInt();
        boolean isWeekend = scanner.nextBoolean();
        System.out.println(numberOfButtercups >= 10 && numberOfButtercups <= 20 && !isWeekend ||
               numberOfButtercups >= 15 && numberOfButtercups <= 25 && isWeekend);
    }
}