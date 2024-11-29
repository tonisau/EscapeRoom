package utils;

import classes.enums.Theme;
import exceptions.IncorrectInputException;
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

    public static Theme readTheme(String message) {
        System.out.println(message);
        Scanner scanner = new Scanner(System.in);
        boolean isValidInput = false;
        Theme validInput = null;
        while (!isValidInput) {
            try {
                validInput = checkValidTheme(stringReadLineAndCheck(scanner));
                isValidInput = true;
            } catch (StringException | IncorrectInputException e) {
                System.out.println("Format error. " + e.getMessage());
            }
        }
        scanner.reset();
        return validInput;
    }

    private static Theme checkValidTheme(String input) throws IncorrectInputException {
        try {
            return Theme.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IncorrectInputException("Introduced theme doesn't match with the options. ");
        }
    }

    private static String stringReadLineAndCheck(Scanner scanner) throws StringException {
        String line = scanner.nextLine();
        if (line.isEmpty()) throw new StringException("Input string is empty.");
        else return line;
    }
}
