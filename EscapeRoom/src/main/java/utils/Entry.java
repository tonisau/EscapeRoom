package utils;

import exceptions.StringException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Entry {
    public static int readInt(String message) {
        System.out.println(message);
        Scanner scanner = new Scanner(System.in);
        boolean isValidInput = false;
        int validInput = 0;
        while (!isValidInput) {
            try {
                validInput = scanner.nextInt();
                isValidInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Format error. " + message);
                scanner.nextLine();
            }
        }
        scanner.nextLine();
        return validInput;
    }

    public static String readString(String message) {
        System.out.println(message);
        Scanner scanner = new Scanner(System.in);
        boolean isValidInput = false;
        String validInput = null;
        while (!isValidInput) {
            try {
                validInput = stringReadLineAndCheck(scanner);
                isValidInput = true;
            } catch (StringException e) {
                System.out.println("Format error. " + e.getMessage());
            }
        }
        scanner.reset();
        return validInput;
    }

    public static double readDouble(String message) {
        System.out.println(message);
        Scanner scanner = new Scanner(System.in);
        boolean isValidInput = false;
        double validInput = 0;
        while (!isValidInput) {
            try {
                validInput = scanner.nextDouble();
                isValidInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Format error. " + message);
                scanner.nextLine();
            }
        }
        scanner.nextLine();
        return validInput;
    }

    private static String stringReadLineAndCheck(Scanner scanner) throws StringException {
        String line = scanner.nextLine();
        if (line.isEmpty()) throw new StringException("Input string is empty.");
        else return line;
    }

    public static boolean readBoolean(String message) {
        Scanner scanner = new Scanner(System.in);
        String result;
        do{
            System.out.println(message);
            result = scanner.nextLine().toUpperCase();
        }while (!result.matches("[YN]") );
        return result.equals("S");
    }
}
