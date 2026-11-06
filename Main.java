import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class Main extends JFrame {

    // Atribut form
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JPasswordField txtKonfirmasi;
    private JButton btnDaftar;
    private JLabel lblMasuk;

    // Warna tampilan
    private final Color HITAM = new Color(35, 38, 42);
    private final Color BIRU = new Color(35, 79, 115);
    private final Color ABU = new Color(245, 246, 248);

    // Constructor
    public Main() {
        setTitle("Catatan Kendaraan - Buat Akun");
        setSize(800, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel utama
        JPanel utama = new JPanel(new BorderLayout());
        utama.setBackground(ABU);
        setContentPane(utama);

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(HITAM);
        header.setBorder(new EmptyBorder(22, 28, 22, 28));

        JLabel judulHeader = new JLabel("Catatan kendaraan");
        judulHeader.setFont(new Font("Arial", Font.PLAIN, 25));
        judulHeader.setForeground(Color.WHITE);

        JLabel mode = new JLabel("Layar login - mode Buat akun");
        mode.setFont(new Font("Arial", Font.PLAIN, 17));
        mode.setForeground(new Color(180, 180, 180));

        header.add(judulHeader, BorderLayout.WEST);
        header.add(mode, BorderLayout.EAST);
        utama.add(header, BorderLayout.NORTH);

        // Panel luar form
        JPanel luar = new JPanel(new GridBagLayout());
        luar.setBackground(ABU);
        luar.setBorder(new EmptyBorder(35, 45, 35, 45));

        // Panel form putih
        JPanel form = new JPanel();
        form.setBackground(Color.WHITE);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBorder(new EmptyBorder(42, 42, 42, 42));
        form.setPreferredSize(new Dimension(680, 680));

        // Judul
        JLabel judul = new JLabel("Buat akun");
        judul.setFont(new Font("Arial", Font.BOLD, 32));
        judul.setForeground(HITAM);
        judul.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(judul);

        form.add(Box.createVerticalStrut(8));

        JLabel deskripsi = new JLabel(
                "Isi data untuk membuat akun baru.");
        deskripsi.setFont(new Font("Arial", Font.PLAIN, 20));
        deskripsi.setForeground(Color.GRAY);
        deskripsi.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(deskripsi);

        form.add(Box.createVerticalStrut(32));

        // Nama pengguna
        form.add(buatLabel("Nama pengguna"));
        form.add(Box.createVerticalStrut(10));

        txtUsername = new JTextField();
        aturInput(txtUsername);
        form.add(txtUsername);

        form.add(Box.createVerticalStrut(24));

        // Kata sandi
        form.add(buatLabel("Kata sandi"));
        form.add(Box.createVerticalStrut(10));

        txtPassword = new JPasswordField();
        aturInput(txtPassword);
        form.add(txtPassword);

        form.add(Box.createVerticalStrut(24));

        // Konfirmasi kata sandi
        form.add(buatLabel("Ulangi kata sandi"));
        form.add(Box.createVerticalStrut(10));

        txtKonfirmasi = new JPasswordField();
        aturInput(txtKonfirmasi);
        form.add(txtKonfirmasi);

        form.add(Box.createVerticalStrut(32));

        // Tombol daftar
        btnDaftar = new JButton("Daftar");
        btnDaftar.setFont(new Font("Arial", Font.BOLD, 23));
        btnDaftar.setForeground(Color.WHITE);
        btnDaftar.setBackground(BIRU);
        btnDaftar.setFocusPainted(false);
        btnDaftar.setCursor(
                new Cursor(Cursor.HAND_CURSOR));
        btnDaftar.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 65));
        btnDaftar.setAlignmentX(Component.LEFT_ALIGNMENT);

        form.add(btnDaftar);

        form.add(Box.createVerticalStrut(25));

        // Link masuk
        lblMasuk = new JLabel("Sudah punya akun? Masuk");
        lblMasuk.setFont(new Font("Arial", Font.PLAIN, 20));
        lblMasuk.setForeground(BIRU);
        lblMasuk.setCursor(
                new Cursor(Cursor.HAND_CURSOR));
        lblMasuk.setAlignmentX(Component.CENTER_ALIGNMENT);

        form.add(lblMasuk);

        luar.add(form);
        utama.add(luar, BorderLayout.CENTER);

        // Event tombol daftar
        btnDaftar.addActionListener(e -> daftarAkun());

        // Event tulisan masuk
        lblMasuk.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(
                        Main.this,
                        "Halaman login belum dibuat.",
                        "Informasi",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    // Method untuk membuat label
    private JLabel buatLabel(String teks) {
        JLabel label = new JLabel(teks);
        label.setFont(new Font("Arial", Font.PLAIN, 21));
        label.setForeground(new Color(90, 90, 90));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    // Method mengatur tampilan input
    private void aturInput(JTextField input) {
        input.setFont(new Font("Arial", Font.PLAIN, 21));
        input.setBackground(Color.WHITE);
        input.setForeground(HITAM);
        input.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 65));
        input.setPreferredSize(new Dimension(550, 65));
        input.setAlignmentX(Component.LEFT_ALIGNMENT);
        input.setMargin(new Insets(10, 16, 10, 16));
    }

    // Method proses pendaftaran

    private void daftarAkun() {
        String username = txtUsername.getText().trim();
        char[] password = txtPassword.getPassword();
        char[] konfirmasi = txtKonfirmasi.getPassword();

        try {
            // Validasi kolom kosong
            if (username.isEmpty() ||
                    password.length == 0 ||
                    konfirmasi.length == 0) {

                JOptionPane.showMessageDialog(this, "Semua kolom wajib diisi!", "Peringatan",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Validasi panjang password
            if (password.length < 8) {
                JOptionPane.showMessageDialog(this, "Kata sandi minimal 8 karakter!", "Peringatan",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Validasi konfirmasi password
            if (!Arrays.equals(password, konfirmasi)) {
                JOptionPane.showMessageDialog(this, "Kata sandi tidak sama!", "Peringatan",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Menyimpan akun ke SQLite
            String passwordText = new String(password);

            boolean berhasil = Database.registerUser(username, passwordText);

            if (berhasil) {
                JOptionPane.showMessageDialog(this, "Akun berhasil didaftarkan!\n" + "Nama pengguna: " + username,
                        "Pendaftaran Berhasil", JOptionPane.INFORMATION_MESSAGE);

                // Membuka halaman login
                new LoginForm().setVisible(true);
                this.dispose();
            }

        } catch (SQLException e) {

            String pesan = e.getMessage();

            if (pesan != null &&
                    pesan.toLowerCase().contains("unique")) {

                JOptionPane.showMessageDialog(this, "Nama pengguna sudah digunakan!", "Pendaftaran Gagal",
                        JOptionPane.WARNING_MESSAGE);

            } else {
                JOptionPane.showMessageDialog(this, "Gagal menyimpan akun ke database:\n" + pesan, "Error Database",
                        JOptionPane.ERROR_MESSAGE);
            }

        } finally {
            Arrays.fill(password, '\0');
            Arrays.fill(konfirmasi, '\0');
        }
    }

    // Method utama
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main aplikasi = new Main();
            aplikasi.setVisible(true);
        });
    }
}
