import java.util.Scanner;
import java.util.EmptyStackException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

class CarLibraryApp {
    private static final Scanner sc = new Scanner(System.in);
    private static StackManager stackManager = new StackManager();
    private static String currentStack = null;

    public static void main(String[] args) {
        while (true) {
            try {
                String opt = printMenu();
                switch (opt) {
                    case "0":
                        System.out.println("Exiting Application");
                        return;
                    case "1":
                        createNewStack();
                        break;
                    case "2":
                        selectStack();
                        break;
                    case "3":
                        addCars();
                        break;
                    case "4":
                        removeCars();
                        break;
                    case "5":
                        peekCar();
                        break;
                    case "6":
                        System.out.println("Is stack empty? " + getCurrentStack().isEmpty());
                        break;
                    case "7":
                        Stack<Car> stack = getCurrentStack();
                        System.out.println("Stack size: " + stack.size() + ", Capacity: " + stack.getCapacity());
                        break;
                    case "8":
                        searchCar();
                        break;
                    case "9":
                        saveStack();
                        break;
                    case "10":
                        loadStack();
                        break;
                    case "u":
                        undoLastOperation();
                        break;
                    case "r":
                        redoLastOperation();
                        break;
                    default:
                        System.out.println("Invalid option. Please choose again.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    private static String printMenu() {
        System.out.println("\nCurrent Stack: " + (currentStack != null ? currentStack : "None"));
        System.out.println("\nOptions:");
        System.out.println(" 0: Exit Application");
        System.out.println(" 1: Create a new stack");
        System.out.println(" 2: Select a stack");
        System.out.println(" 3: Add cars to stack");
        System.out.println(" 4: Remove cars from stack");
        System.out.println(" 5: View top car in stack");
        System.out.println(" 6: Check if stack is empty");
        System.out.println(" 7: Get stack size and capacity");
        System.out.println(" 8: Search for a car by VIN");
        System.out.println(" 9: Save current stack");
        System.out.println("10: Load a stack");
        System.out.println(" u: Undo last operation");
        System.out.println(" r: Redo last operation");
        System.out.print("Choose an option (0-10, u, r): ");
        return sc.nextLine();
    }

    private static void createNewStack() {
        System.out.print("Enter a name for the new stack: ");
        String name = sc.nextLine();
        System.out.print("Enter the capacity of the new stack: ");
        int capacity = getIntInput();
        stackManager.createStack(name, capacity);
        currentStack = name;
        System.out.println("New stack created and selected: " + name);
    }

    private static void selectStack() {
        String[] stackNames = stackManager.getStackNames();
        if (stackNames.length == 0) {
            System.out.println("No stacks available. Please create a new stack first.");
            return;
        }
        System.out.println("Available stacks:");
        for (int i = 0; i < stackNames.length; i++) {
            System.out.println((i + 1) + ": " + stackNames[i]);
        }
        System.out.print("Select a stack (1-" + stackNames.length + "): ");
        int selection = getIntInput();
        if (selection >= 1 && selection <= stackNames.length) {
            currentStack = stackNames[selection - 1];
            System.out.println("Selected stack: " + currentStack);
        } else {
            System.out.println("Invalid selection.");
        }
    }

    private static Stack<Car> getCurrentStack() {
        if (currentStack == null) {
            throw new IllegalStateException("No stack selected. Please create or select a stack first.");
        }
        return stackManager.getStack(currentStack);
    }

    private static void saveStack() {
        if (currentStack == null) {
            System.out.println("No stack selected. Please create or select a stack first.");
            return;
        }
        System.out.print("Enter filename to save the stack: ");
        String filename = sc.nextLine();
        try {
            stackManager.saveStack(currentStack, filename);
            System.out.println("Stack saved successfully.");
        } catch (Exception e) {
            System.out.println("Error saving stack: " + e.getMessage());
        }
    }

    private static void loadStack() {
        System.out.print("Enter filename to load the stack: ");
        String filename = sc.nextLine();
        System.out.print("Enter a name for the loaded stack: ");
        String name = sc.nextLine();
        try {
            stackManager.loadStack(name, filename);
            currentStack = name;
            System.out.println("Stack loaded successfully and selected.");
        } catch (Exception e) {
            System.out.println("Error loading stack: " + e.getMessage());
        }
    }

    private static void addCars() {
        System.out.print("How many cars do you want to add? ");
        int nCars = getIntInput();

        for (int i = 0; i < nCars; i++) {
            try {
                System.out.print("Enter the Year of car: ");
                int year = getIntInput();
                System.out.print("Enter the Make of car: ");
                String make = sc.nextLine();
                System.out.print("Enter the Model of car: ");
                String model = sc.nextLine();
                System.out.print("Enter the VIN of car: ");
                int vin = getIntInput();

                Car newCar = new Car(year, make, model, vin);
                getCurrentStack().push(newCar);
                System.out.println("Car added successfully.");
            } catch (IllegalStateException e) {
                System.out.println("Stack is full. Cannot add more cars.");
                break;
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println("Invalid input. Car not added.");
            }
        }
    }

    private static void removeCars() {
        System.out.print("How many cars do you want to remove? ");
        int carsToRemove = getIntInput();

        for (int i = 0; i < carsToRemove; i++) {
            try {
                Car removedCar = getCurrentStack().pop();
                System.out.println("Removed: " + removedCar);
            } catch (EmptyStackException e) {
                System.out.println("Stack is empty. Cannot remove more cars.");
                break;
            }
        }
    }

    private static void peekCar() {
        try {
            Car topCar = getCurrentStack().peek();
            System.out.println("Top car in the stack: " + topCar);
        } catch (EmptyStackException e) {
            System.out.println("Stack is empty.");
        }
    }

    private static void searchCar() {
        System.out.print("Enter the VIN of the car to search: ");
        int searchVin = getIntInput();
        Car searchCar = new Car(0, "", "", searchVin);
        boolean found = getCurrentStack().contains(searchCar);
        System.out.println("Car with VIN " + searchVin + " is " + (found ? "found" : "not found") + " in the stack.");
    }

    private static void undoLastOperation() {
        // Implement undo functionality here
        System.out.println("Undo functionality not yet implemented.");
    }

    private static void redoLastOperation() {
        // Implement redo functionality here
        System.out.println("Redo functionality not yet implemented.");
    }

    private static int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }
}