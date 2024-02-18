public class Config {
    public static final String INPUT_PATH_FOLDER = "C:\\Users\\User\\Desktop\\input";
    public static final String ARCHIVE_FOLDER_PATH = "C:\\Users\\User\\Desktop\\archive";
    public static final String REPORT_FILE_PATH = "C:\\Users\\User\\Desktop\\report-file.txt";
    public static final String FILE_WITH_NUMBERS_PATH = "C:\\Users\\User\\Desktop\\file-with-numbers.txt";

    public static final String JDBC_DRIVER = "org.postgresql.Driver";
    public static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/ParsingDB";
    public static final String DATABASE_USER = "postgres";
    public static final String DATABASE_PASSWORD = "root";

    public static final String TRUNCATE_TABLE = "TRUNCATE TABLE db";
    public static final String INSERT_INTO_DB = "INSERT INTO db(id,account,count)" + "VALUES(DEFAULT,?,?)";
    public static final String UPDATE_DB = "UPDATE db SET count=? WHERE account = ?";
}
