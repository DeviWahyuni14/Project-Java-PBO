import java.sql.Connection;

public class TestKoneksi {
    public static void main(String[] args) {
        try (Connection conn = Database.connect()) {

            if (conn.isValid(2)) {
                System.out.println("BERHASIL TERHUBUNG KE SQLITE!");
            }

        } catch (Exception e) {
            System.out.println("GAGAL TERHUBUNG!");
            e.printStackTrace();
        }
    }
}