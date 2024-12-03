package utils;

import classes.enums.Level;
import classes.enums.Material;
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

    public static Level readLevel(String message) {
        System.out.println(message);
        Scanner scanner = new Scanner(System.in);
        boolean isValidInput = false;
        Level validInput = null;
        while (!isValidInput) {
            try {
                validInput = checkValidLevel(stringReadLineAndCheck(scanner));
                isValidInput = true;
            } catch (StringException | IncorrectInputException e) {
                System.out.println("Format error. " + e.getMessage());
            }
        }
        scanner.reset();
        return validInput;
    }

    private static Level checkValidLevel(String input) throws IncorrectInputException {
        try {
            return Level.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IncorrectInputException("Introduced level doesn't match with the options. ");
        }
    }

    public static Material readMaterial(String message) {
        System.out.println(message);
        Scanner scanner = new Scanner(System.in);
        boolean isValidInput = false;
        Material validInput = null;
        while (!isValidInput) {
            try {
                validInput = checkValidMaterial(stringReadLineAndCheck(scanner));
                isValidInput = true;
            } catch (StringException | IncorrectInputException e) {
                System.out.println("Format error. " + e.getMessage());
            }
        }
        scanner.reset();
        return validInput;
    }

    private static Material checkValidMaterial(String input) throws IncorrectInputException {
        try {
            return Material.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IncorrectInputException("Introduced material doesn't match with the options. ");
        }
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

    public static boolean readBoolean(String message) {
        Scanner scanner = new Scanner(System.in);
        String result;
        do{
            System.out.println(message);
            result = scanner.nextLine().toUpperCase();
        }while (!result.matches("[YN]") );
        return result.equals("Y");
    }
}
