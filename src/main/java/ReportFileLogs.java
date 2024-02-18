import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class ReportFileLogs {
    public static void negativeAmountTransaction(LocalDate localDate, LocalTime localTime, File resultFile, String beginCorrectLine,
                                                 String beginLine, String endCorrectLine) {
        try (BufferedWriter writerReport = new BufferedWriter(new FileWriter(Config.REPORT_FILE_PATH, true))) {
            writerReport.write(localDate + " " + localTime + "||" + "file: "
                    + resultFile.toString().substring(resultFile.toString().lastIndexOf("\\") + 1)
                    + ": translation from " + beginCorrectLine + " on " + beginLine + ": " + endCorrectLine
                    + "|| error: Negative transfer amount" + "\n");
            writerReport.flush();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    public static void successTransaction(LocalDate localDate, LocalTime localTime, File resultFile, String beginCorrectLine,
                                          String beginLine, String endCorrectLine) {
        try (BufferedWriter writerReport = new BufferedWriter(new FileWriter(Config.REPORT_FILE_PATH, true))) {
            writerReport.write(localDate + " " + localTime + "||" + "file: "
                    + resultFile.toString().substring(resultFile.toString().lastIndexOf("\\") + 1)
                    + ": translation from " + beginCorrectLine + " on " + beginLine + ": " + endCorrectLine
                    + " || successfully processed \n");
            writerReport.flush();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    public static void inCorrectDataTransaction(LocalDate localDate, LocalTime localTime, File resultFile, String beginCorrectLine,
                                                String beginLine, String endCorrectLine, NumberFormatException e) throws IOException {
        BufferedWriter writerReport = new BufferedWriter(new FileWriter(Config.REPORT_FILE_PATH, true));
        writerReport.write(localDate + " " + localTime + "||" + "file: "
                + resultFile.toString().substring(resultFile.toString().lastIndexOf("\\") + 1) +
                ": translation from " + beginCorrectLine + " on " + beginLine + ": " + endCorrectLine
                + " || error: Incorrect data -"
                + e.getMessage().substring(17) + "\n");
        writerReport.flush();
        writerReport.close();
    }
}


