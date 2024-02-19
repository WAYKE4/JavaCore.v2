import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parse {
    InputValidChecker inputValidChecker = new InputValidChecker();

    /**
     * The method iterates through all files that have been checked for the txt format , their values
     * If it finds a string that meets our condition, it sends it to the changingData method
     */
    public void regexChecker(List<File> resultFiles) {
        DatabaseWriter.InsertIntoDB(inputValidChecker.validTabChecker());
        List<String> resultList = inputValidChecker.validTabChecker();
        System.out.println("DataIn: " + resultList);
        boolean isFirstTime = true;

        for (File resultFile : resultFiles) {
            try (BufferedReader readerFile = new BufferedReader(new FileReader(resultFile))) {
                Pattern patternData = Pattern.compile("^[\\d]{5}-[\\d]{5}:.*[\\d].*$");
                String line;
                while ((line = readerFile.readLine()) != null) {
                    Matcher matcherData = patternData.matcher(line);
                    if (matcherData.matches()) {
                        String correctOut = matcherData.group();
                        if (isFirstTime) {
                            resultList = changingData(correctOut, resultList, true, resultFile);
                            isFirstTime = false;
                        } else {
                            resultList = changingData(correctOut, resultList, false, resultFile);
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        System.out.println("DataOut:" + resultList);
    }

    /**
     * The method takes the subtracted string from the input files and checks it for correctness.
     * If yes, it overwrites the new value in the dataList and so on in a loop.
     * If not, it handles the exception
     * At the end, all the stages of the processed lines (correct and not) are recorded in the report-file
     */
    public List<String> changingData(String correctOut, List<String> resultList, boolean isFirstTime, File resultFile) {
        List<String> dataList;
        String line;
        String endLine;
        String endCorrectLine;
        String lineBack;
        String beginLine;
        String beginCorrectLine;
        LocalDate localDate;
        LocalTime localTime;
        String newLine;

        if (isFirstTime) {
            dataList = inputValidChecker.validTabChecker();
        } else {
            dataList = resultList;
        }
        for (int i = 0; i < Objects.requireNonNull(dataList).size(); i++) {
            try {
                line = dataList.get(i);
                endLine = line.substring(line.indexOf(":") + 1);
                endCorrectLine = correctOut.substring(correctOut.indexOf(":") + 1);
                lineBack = line.substring(0, line.indexOf(":") + 1);
                beginLine = line.substring(0, line.indexOf(":"));
                beginCorrectLine = correctOut.substring(0, correctOut.indexOf(":"));

                if (beginLine.equals(beginCorrectLine)) {
                    int endLineParseInt = Integer.parseInt(endLine);
                    int endCorrectLineParseInt = Integer.parseInt(endCorrectLine);
                    localDate = LocalDate.now();
                    localTime = LocalTime.parse(LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss")));
                    if (endCorrectLineParseInt < 0) {
                        ReportFileLogs.negativeAmountTransaction(localDate, localTime, resultFile, beginCorrectLine, beginLine, endCorrectLine);
                        continue;
                    }
                    int sumLines = endLineParseInt + endCorrectLineParseInt;
                    newLine = lineBack + sumLines;
                    DatabaseWriter.updateDB(lineBack, sumLines);
                    dataList.set(i, newLine);
                    ReportFileLogs.successTransaction(localDate, localTime, resultFile, beginCorrectLine, beginLine, endCorrectLine);
                }
            } catch (NumberFormatException e) {
                try {
                    line = dataList.get(i);
                    endCorrectLine = correctOut.substring(correctOut.indexOf(":") + 1);
                    beginLine = line.substring(0, line.indexOf(":"));
                    beginCorrectLine = correctOut.substring(0, correctOut.indexOf(":"));
                    localDate = LocalDate.now();
                    localTime = LocalTime.parse(LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss")));
                    ReportFileLogs.unCorrectDataTransaction(localDate, localTime, resultFile, beginCorrectLine, beginLine, endCorrectLine, e);
                } catch (IOException ee) {
                    System.out.println(ee);
                }
            }
        }
        return dataList;
    }
}