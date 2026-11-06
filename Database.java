import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    // File database akan dibuat otomatis di folder root proyek VS Code
    // Ganti baris private static final String URL di Database.java menjadi:
    private static final String URL = "jdbc:sqlite:kendaraan.db";

    /**
     * Membuka koneksi ke database SQLite
     */
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    /**
     * Membuat tabel 'users' otomatis jika belum ada (disesuaikan dengan SQLite
     * Studio Anda)
     */
    public static void initDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS users ("
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " username TEXT NOT NULL UNIQUE,"
                + " password_hash TEXT NOT NULL,"
                + " salt TEXT NOT NULL,"
                + " created_at TEXT DEFAULT CURRENT_TIMESTAMP"
                + ");";

        try (Connection conn = connect();
                Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Gagal inisialisasi database: " + e.getMessage());
        }
    }

    /**
     * Menyimpan pengguna baru ke dalam tabel 'users'
     */
    public static boolean registerUser(String username, String password) throws SQLException {
        String sql = "INSERT INTO users(username, password_hash, salt) VALUES(?, ?, ?)";

        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password); // Dimasukkan ke password_hash
            pstmt.setString(3, "default_salt"); // Nilai salt default

            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;
        }
    }

    /**
     * Memeriksa keberadaan pengguna dan kata sandi saat login
     */
    public static boolean validateLogin(String username, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ? AND password_hash = ?";

        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        }
    }
}