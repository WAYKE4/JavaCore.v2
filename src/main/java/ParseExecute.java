import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * The method starts the switch for parsing ; file-report ; output
 */
public class ParseExecute {
    public void reportFileCreating() {
        String directoryPath = Config.REPORT_FILE_PATH;
        File directory = new File(directoryPath);
        try {
            if (directory.createNewFile()) {
                System.out.println("Report-file successfully created!");
            }
        } catch (IOException eeq) {
            System.out.println(eeq);
        }
    }
    public void beginParsing() throws InputException {
        reportFileCreating();
        System.out.println("1. Parsing");
        System.out.println("2. File-report");
        System.out.println("3. Exit");
        boolean firstTimeParsing = true;

        while (true) {
            Scanner scanner = new Scanner(System.in);
            int num = scanner.nextInt();
            switch (num) {
                case 1 -> {
                    InputValidChecker inputValidChecker = new InputValidChecker();
                    Parse parse = new Parse();
                    parse.regexChecker(inputValidChecker.inputFileCheck());
                    firstTimeParsing = false;
                }
                case 2 -> {
                    File file = new File(Config.REPORT_FILE_PATH);
                    try (BufferedReader reader = new BufferedReader(new FileReader(Config.REPORT_FILE_PATH))) {
                        if (file.exists() && firstTimeParsing) {
                            PrintWriter writer = new PrintWriter(file);
                            writer.print("");
                            writer.close();
                            System.out.println("You not parsing!!");
                        }
                        String line;
                        while ((line = reader.readLine()) != null) {
                            System.out.println(line + "\n");
                        }
                        firstTimeParsing = true;
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                }
                case 3 -> {
                    InputValidChecker inputValidChecker = new InputValidChecker();
                    inputValidChecker.archiveWriter(inputValidChecker.inputFileCheck());
                    System.exit(0);
                }
                default -> {
                    System.out.println("Uncorrect number , try again!");
                    System.out.println("1. Parsing");
                    System.out.println("2. File-report");
                    System.out.println("3. Exit");
                }
            }
        }
    }
}
