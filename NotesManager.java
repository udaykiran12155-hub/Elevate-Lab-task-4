import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NotesManager {

    private static final String FILENAME = "notes.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        while (choice != 3) {
            printMenu();
            try {
                choice = Integer.parseInt(scanner.nextLine()); // Read the full line to avoid input issues
            } catch (NumberFormatException e) {
                System.out.println(" Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    readNotes();
                    break;
                case 2:
                    writeNote(scanner);
                    break;
                case 3:
                    System.out.println("Goodbye! ");
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1, 2, or 3.");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n---  Simple Notes Manager ---");
        System.out.println("1. View all notes");
        System.out.println("2. Add a new note");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void readNotes() {
        System.out.println("\n--- Your Notes ---");
        File notesFile = new File(FILENAME);
        if (!notesFile.exists()) {
            System.out.println("(No notes found. Add one!)");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String currentLine;
            boolean hasNotes = false;
            while ((currentLine = reader.readLine()) != null) {
                System.out.println(currentLine);
                hasNotes = true;
            }
            if (!hasNotes) {
                System.out.println("(File is empty. Add a new note!)");
            }
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }
        System.out.println("--------------------");
    }


    private static void writeNote(Scanner scanner) {
        System.out.print("Enter your new note: ");
        String note = scanner.nextLine();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String timestamp = dtf.format(now);


        try (FileWriter writer = new FileWriter(FILENAME, true)) {
            writer.write("[" + timestamp + "] " + note + "\n");
            System.out.println(" Note saved successfully!");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}