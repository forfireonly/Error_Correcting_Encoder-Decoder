import java.util.Scanner;
class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scn = new Scanner(System.in);
        int numberShipped = 0;
        int numberFixed = 0;
        int numberRejects = 0;
        scn.nextLine();
        while (scn.hasNext()) {
            int detector = scn.nextInt();

            switch (detector) {
                case 1:
                    numberFixed++;
                    break;
                case -1:
                    numberRejects++;
                    break;
                case 0:
                    numberShipped++;
                    break;
                default:
            }
        }
        System.out.printf("%d %d %d", numberShipped, numberFixed, numberRejects);
    }
}