import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidChecker {
    /**
     * Checking for the existence of the input folder.
     * If it doesn’t exist, it creates it and asks you to upload input files
     */
    public List<File> inputFileCheck() throws InputException {
        String directoryPath = Config.INPUT_PATH_FOLDER;
        File directory = new File(directoryPath);
        boolean notExist;
        boolean emptyInput = false;

        if (!directory.exists()) {
            System.out.println("Input folder doesn't exist!");
            System.out.println("Creating input folder....");
            notExist = true;
            if (directory.mkdirs()) {
                System.out.println("Input folder successfully created!");
            } else {
                System.out.println("Input folder not created. System exit...");
                System.exit(1);
            }
            while (notExist) {
                System.out.println("Put some input files!");
                File[] files = directory.listFiles();
                if (!(files.length == 0)) {
                    notExist = false;
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        File[] files = directory.listFiles();
        if (files == null) {
            throw new InputException("Path directory error!");
        }
        try {
            if (files.length == 0) {
                emptyInput = true;
                throw new InputException("Yours folder is empty!");
            }
        } catch (InputException e) {
            while (emptyInput) {
                System.out.println("Put some input files!");
                File[] files0 = directory.listFiles();
                if (!(files0.length == 0)) {
                    emptyInput = false;
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ee) {
                    System.out.println(ee);
                }
            }
            files = directory.listFiles();
        }
        List<File> resultFiles = Arrays.stream(files)
                .filter((file) -> file.getName().endsWith(".txt"))
                .toList();
        if (resultFiles.isEmpty()) {
            throw new InputException("Yours folder doesn't have file in format .txt!");
        }
        return resultFiles;
    }

    /**
     * Checking the validity of the main accounts (file-with-numbers) . If not , ignores them in the future
     */
    public ArrayList<String> validTabChecker() {
        Pattern patternDataOut = Pattern.compile("^[\\d]{5}-[\\d]{5}:[\\d]+.+?$");
        Pattern patternDataOutCheck = Pattern.compile("^.*\\D$");
        ArrayList<String> arrOut = null;
        try (BufferedReader readerDataOut = new BufferedReader(new FileReader(Config.FILE_WITH_NUMBERS_PATH))) {
            String dataOut;
            arrOut = new ArrayList<>();
            while ((dataOut = readerDataOut.readLine()) != null) {
                Matcher matcherDataOut = patternDataOut.matcher(dataOut);
                if (matcherDataOut.matches()) {
                    String dataOutCheck = dataOut.substring(dataOut.indexOf(":") + 1);
                    Matcher matcherDataOutChecker = patternDataOutCheck.matcher(dataOutCheck);
                    if (matcherDataOutChecker.matches()) {
                        System.out.println(dataOut + " will be ignored (Incorrect format)");
                        continue;
                    }
                    arrOut.add(dataOut);
                }
            }
            if (arrOut.isEmpty()) {
                System.out.println(("The entire file-with-numbers is in the wrong format!"));
                System.exit(1);
            }
        } catch (IOException e) {
            System.out.println("File-with-numbers doesn't exist!");
            System.exit(1);
        }

        File file = new File(Config.REPORT_FILE_PATH);
        try {
            if (!(file.length() == 0)) {
                PrintWriter writer = new PrintWriter(file);
                writer.print("");
                writer.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        return arrOut;

    }

    public void archiveWriter(List<File> resultFiles) {
        String directoryPath = Config.ARCHIVE_FOLDER_PATH;
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            System.out.println("Archive doesn't exist!");
            System.out.println("Creating archive folder....");
            if (directory.mkdirs()) {
                System.out.println("archive folder successfully created!");
            } else {
                System.out.println("archive folder not created. System exit...");
                System.exit(1);
            }
        }
        for (File file : resultFiles) {
            try {
                File newFileInArchive = new File(Config.ARCHIVE_FOLDER_PATH + file.getName());
                Files.copy(file.toPath(), newFileInArchive.toPath());
                System.out.println(file.getName() + " successfully created in archive!");
            } catch (FileAlreadyExistsException e) {
                System.out.println("There are already files with the same name in the archive! " + e.getMessage().substring(30));
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}