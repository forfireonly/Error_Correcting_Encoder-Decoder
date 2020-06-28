package correcter;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static byte[] bytes;



    static void decodeFile(byte[] bytes) throws IOException {

        Files.deleteIfExists(Paths.get("decoded.txt"));
        StringBuilder binaryStringForDecoding = new StringBuilder();

        for (byte currentByte : bytes) {
            binaryStringForDecoding.append(String.format("%8s", Integer.toBinaryString(currentByte & 0xFF))
                    .replace(' ', '0')).append(" "); //converting byte array into string of 1 and 0
        }

        String[] binaryStringArrayForDecoding = String.valueOf(binaryStringForDecoding).split(" ");

        StringBuilder binaryForDecoding = new StringBuilder();
        StringBuilder binaryByteForDecoding = new StringBuilder();
        for (String each : binaryStringArrayForDecoding) {  //correcting string based on hamming code
            binaryByteForDecoding.setLength(0);
            int wrongParityBit = 0;
            String[] binaryByteString = each.split("");


            if (Integer.parseInt(binaryByteString[0]) != (Integer.parseInt(binaryByteString[2]) + Integer.parseInt(binaryByteString[4]) +
                    + Integer.parseInt(binaryByteString[6])) % 2) {
                wrongParityBit++;
            }
            if (Integer.parseInt(binaryByteString[1]) != (Integer.parseInt(binaryByteString[2]) + Integer.parseInt(binaryByteString[5]) +
                    + Integer.parseInt(binaryByteString[6])) % 2) {
                wrongParityBit += 2;
            }
            if (Integer.parseInt(binaryByteString[3]) != (Integer.parseInt(binaryByteString[4]) + Integer.parseInt(binaryByteString[5]) +
                    + Integer.parseInt(binaryByteString[6])) % 2) {
                wrongParityBit += 4;
            }

            if (wrongParityBit > 2) {
                binaryByteString[wrongParityBit - 1 ] = String.valueOf(Integer.parseInt(binaryByteString[wrongParityBit - 1]) ^ 1);
            }

            binaryByteForDecoding.append(binaryByteString[2]).append(binaryByteString[4]).append(binaryByteString[5]).append(binaryByteString[6]);
            binaryForDecoding.append(binaryByteForDecoding);

        }

        /*for (String each : binaryStringArrayForDecoding) {  //correcting string based on parity
            int j = 0;
            String[] binaryByteString = each.split("");

            if (binaryByteString[j].equals(binaryByteString[j + 1])) {
                binaryForDecoding.append(binaryByteString[j]);
            } else {
                binaryForDecoding.append(Integer.parseInt(binaryByteString[j + 2]) ^ Integer.parseInt(binaryByteString[j + 4])
                        ^ Integer.parseInt(binaryByteString[j + 6]));

            }
            if (binaryByteString[j + 2].equals(binaryByteString[j + 3])) {
                binaryForDecoding.append(binaryByteString[j + 2]);

            } else {
                binaryForDecoding.append(Integer.parseInt(binaryByteString[j]) ^ Integer.parseInt(binaryByteString[j + 4])
                        ^ Integer.parseInt(binaryByteString[j + 6]));
            }
            if (binaryByteString[j + 4].equals(binaryByteString[j + 5])) {
                binaryForDecoding.append(binaryByteString[j + 4]);
            } else {
                binaryForDecoding.append(Integer.parseInt(binaryByteString[j]) ^ Integer.parseInt(binaryByteString[j + 2])
                        ^ Integer.parseInt(binaryByteString[j + 6]));
            }

        }*/


        String[] binaryArrayForDecoding = String.valueOf(binaryForDecoding).split("");
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < binaryArrayForDecoding.length - 1; i += 8) {
            int counter = 0;
            StringBuilder character = new StringBuilder();
            for (int j = 0; j < 8; j++){
                character.append(binaryArrayForDecoding[i + j]);
            }
            int charCode = Integer.parseInt(String.valueOf(character), 2);
            String str = Character.toString((char) charCode);
            text.append(str);
        }

        try (CharArrayWriter contactWriter = new CharArrayWriter();
             FileWriter bc1 = new FileWriter("decoded.txt");
        ) {
            contactWriter.write(String.valueOf(text));
            contactWriter.writeTo(bc1);
        } catch (Exception e) {
            System.out.println("Something is wrong");
        }
    }

    static void introduceErrorInBites(byte[] bytes) throws IOException {
        Files.deleteIfExists(Paths.get("received.txt"));
        Random r = new Random();

        for (int i = 0; i < bytes.length; i++) {
            int randomIndex = r.nextInt(7);
            bytes[i] ^= 1 << randomIndex;
        }

            File outputFile = new File("received.txt");
            try (FileOutputStream outputStream = new FileOutputStream(outputFile, true);
            ) {
                outputStream.write(bytes);
            } catch (Exception e) {
                System.out.println("Something is wrong");
            }

    }

    public static String convertStringToBinary(String input) {

        StringBuilder result = new StringBuilder();
        char[] chars = input.toCharArray();
        for (char aChar : chars) {
            result.append(
                    String.format("%8s", Integer.toBinaryString(aChar))   // char -> int, auto-cast
                            .replaceAll(" ", "0")                         // zero pads
            );
        }
        return result.toString();

    }

    static void encodingText(String string) throws IOException {


        Files.deleteIfExists(Paths.get("encoded.txt"));

        String binaryString = convertStringToBinary(string);

        String[] binaryStringArray = binaryString.split("");
        StringBuilder binaryStringToEncode = new StringBuilder();

        for (int i = 0; i < binaryString.length(); i += 4) { // encoding based on hamming code
            binaryStringToEncode.setLength(0);
            int firstBit = (Integer.parseInt(binaryStringArray[i]) + Integer.parseInt(binaryStringArray[i + 1]) +
                    Integer.parseInt(binaryStringArray[i + 3])) % 2;
            binaryStringToEncode.append(firstBit);
            int secondBit = (Integer.parseInt(binaryStringArray[i]) + Integer.parseInt(binaryStringArray[i + 2]) +
                    Integer.parseInt(binaryStringArray[i + 3])) % 2;
            binaryStringToEncode.append(secondBit);
            binaryStringToEncode.append(binaryStringArray[i]);
            int fourthBit = (Integer.parseInt(binaryStringArray[i + 1]) + Integer.parseInt(binaryStringArray[i + 2]) +
                    Integer.parseInt(binaryStringArray[i + 3])) %2;
            binaryStringToEncode.append(fourthBit);
            binaryStringToEncode.append(binaryStringArray[i + 1]);
            binaryStringToEncode.append(binaryStringArray[i + 2]);
            binaryStringToEncode.append(binaryStringArray[i + 3]);
            binaryStringToEncode.append(0);
            int j = (byte) Integer.parseInt(String.valueOf(binaryStringToEncode), 2);
            File outputFile = new File("encoded.txt");
            try (FileOutputStream outputStream = new FileOutputStream(outputFile, true);
            ) {
                outputStream.write(j);
            } catch (Exception e) {
                System.out.println("Something is wrong");
            }
        }


        /*for (int i = 0; i < binaryString.length(); i += 3) { encoding based on parity
            binaryStringToEncode.setLength(0);
           // if (i + 2 < binaryString.length()) {
                binaryStringToEncode.append(binaryString.charAt(i)).append(binaryString.charAt(i));
                int a = Integer.parseInt(String.valueOf(binaryString.charAt(i)));
                int b = 0;
                int c = 0;
                if (i + 1 >= binaryString.length()) {
                    binaryStringToEncode.append(0).append(0);

                } else {
                    binaryStringToEncode.append(binaryString.charAt(i + 1)).append(binaryString.charAt(i + 1));
                    b = Integer.parseInt(String.valueOf(binaryString.charAt(i + 1)));
                }
                if (i + 2 >= binaryString.length()) {
                    binaryStringToEncode.append(0).append(0);

                } else {
                    binaryStringToEncode.append(binaryString.charAt(i + 2)).append(binaryString.charAt(i + 2));
                    c = Integer.parseInt(String.valueOf(binaryString.charAt(i + 2)));
                }

                int d = (char) ((a + b + c) % 2);
                if (d == 1) {
                    binaryStringToEncode.append("1").append("1");
                } else {
                    binaryStringToEncode.append("0").append("0");
                }

                int j = (byte) Integer.parseInt(String.valueOf(binaryStringToEncode), 2);
                File outputFile = new File("encoded.txt");
                try (FileOutputStream outputStream = new FileOutputStream(outputFile, true);
                ) {
                    outputStream.write(j);
                } catch (Exception e) {
                    System.out.println("Something is wrong");
                }
        }*/
    }

    public static void main(String[] args) throws IOException {

        Scanner scn = new Scanner(System.in);
        System.out.print("Write a mode: ");
        String choice = scn.next();


        switch (choice) {
            case "encode":
                StringBuilder textForEncoding = new StringBuilder();
                File fileForEncoding = new File("send.txt");
                try (Scanner scanner = new Scanner(fileForEncoding)) {
                    while (scanner.hasNext()) {
                        textForEncoding.append(scanner.nextLine());
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("No file");
                }

                encodingText(textForEncoding.toString());

                break;
            case "decode":
                File fileForDecoding = new File("received.txt");
                try (FileInputStream stream = new FileInputStream(fileForDecoding)) {
                    bytes = stream.readAllBytes();
                } catch (Exception e) {
                    System.out.println("Error in reading the file: " + fileForDecoding.getName());
                }
                decodeFile(bytes);
                break;
            case "send":
                File file = new File("encoded.txt");
                try (FileInputStream stream = new FileInputStream(file)) {
                    bytes = stream.readAllBytes();
                } catch (Exception e) {
                    System.out.println("Error in reading the file: " + file.getName());
                }
                introduceErrorInBites(bytes);
            default:

        }

    }
}
