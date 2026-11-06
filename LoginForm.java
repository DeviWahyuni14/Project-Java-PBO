import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class LoginForm extends JFrame {

        private static final long serialVersionUID = 1L;

        private JTextField txtUsername;
        private JPasswordField txtPassword;

        private final Color HITAM = new Color(35, 38, 42);
        private final Color BIRU = new Color(35, 79, 115);
        private final Color ABU = new Color(245, 246, 248);

        // Constructor
        public LoginForm() {

                setTitle("Catatan Kendaraan - Masuk");
                setSize(800, 720);
                setLocationRelativeTo(null);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setResizable(false);

                JPanel utama = new JPanel(new BorderLayout());
                utama.setBackground(ABU);
                setContentPane(utama);

                // HEADER
                JPanel header = new JPanel(new BorderLayout());
                header.setBackground(HITAM);
                header.setBorder(new EmptyBorder(25, 28, 25, 28));

                JLabel judulHeader = new JLabel("Catatan kendaraan");
                judulHeader.setFont(new Font("Arial", Font.PLAIN, 25));
                judulHeader.setForeground(Color.WHITE);

                JLabel mode = new JLabel("Layar login - mode Masuk");
                mode.setFont(new Font("Arial", Font.PLAIN, 17));
                mode.setForeground(Color.LIGHT_GRAY);

                header.add(judulHeader, BorderLayout.WEST);
                header.add(mode, BorderLayout.EAST);

                utama.add(header, BorderLayout.NORTH);

                // PANEL LUAR
                JPanel luar = new JPanel(new GridBagLayout());
                luar.setBackground(ABU);
                luar.setBorder(new EmptyBorder(30, 40, 30, 40));

                // FORM LOGIN
                JPanel form = new JPanel();
                form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
                form.setBackground(Color.WHITE);
                form.setBorder(new EmptyBorder(40, 40, 40, 40));
                form.setPreferredSize(new Dimension(680, 580));

                // JUDUL FORM
                JLabel judul = new JLabel("Masuk");
                judul.setFont(new Font("Arial", Font.BOLD, 32));
                judul.setForeground(HITAM);
                judul.setAlignmentX(Component.LEFT_ALIGNMENT);
                form.add(judul);

                form.add(Box.createVerticalStrut(10));

                JLabel deskripsi = new JLabel("Masukkan nama pengguna dan kata sandi.");
                deskripsi.setFont(new Font("Arial", Font.PLAIN, 19));
                deskripsi.setForeground(Color.GRAY);
                deskripsi.setAlignmentX(Component.LEFT_ALIGNMENT);
                form.add(deskripsi);

                form.add(Box.createVerticalStrut(35));

                // INPUT USERNAME
                form.add(buatLabel("Nama pengguna"));
                form.add(Box.createVerticalStrut(10));

                txtUsername = new JTextField();
                aturInput(txtUsername);
                form.add(txtUsername);

                form.add(Box.createVerticalStrut(25));

                // INPUT PASSWORD
                form.add(buatLabel("Kata sandi"));
                form.add(Box.createVerticalStrut(10));

                txtPassword = new JPasswordField();
                aturInput(txtPassword);
                form.add(txtPassword);

                form.add(Box.createVerticalStrut(35));

                // TOMBOL MASUK
                JButton btnMasuk = new JButton("Masuk");
                btnMasuk.setFont(new Font("Arial", Font.BOLD, 23));
                btnMasuk.setBackground(BIRU);
                btnMasuk.setForeground(Color.WHITE);
                btnMasuk.setFocusPainted(false);
                btnMasuk.setOpaque(true);
                btnMasuk.setBorderPainted(false);
                btnMasuk.setCursor(new Cursor(Cursor.HAND_CURSOR));
                btnMasuk.setMaximumSize(new Dimension(Integer.MAX_VALUE, 65));
                btnMasuk.setAlignmentX(Component.LEFT_ALIGNMENT);

                form.add(btnMasuk);

                form.add(Box.createVerticalStrut(25));

                // LINK DAFTAR
                JLabel lblDaftar = new JLabel("Belum punya akun? Daftar");
                lblDaftar.setFont(new Font("Arial", Font.PLAIN, 20));
                lblDaftar.setForeground(BIRU);
                lblDaftar.setAlignmentX(Component.CENTER_ALIGNMENT);
                lblDaftar.setCursor(new Cursor(Cursor.HAND_CURSOR));

                form.add(lblDaftar);

                luar.add(form);
                utama.add(luar, BorderLayout.CENTER);

                // EVENT TOMBOL MASUK
                btnMasuk.addActionListener(e -> prosesLogin());

                lblDaftar.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                                // Membuka halaman Buat Akun (Main)
                                new Main().setVisible(true);

                                // Menutup halaman Login
                                LoginForm.this.dispose();
                        }
                });

        }

        private JLabel buatLabel(String teks) {
                JLabel label = new JLabel(teks);
                label.setFont(new Font("Arial", Font.PLAIN, 21));
                label.setForeground(new Color(90, 90, 90));
                label.setAlignmentX(Component.LEFT_ALIGNMENT);
                return label;
        }

        private void aturInput(JTextField input) {
                input.setFont(new Font("Arial", Font.PLAIN, 21));
                input.setMaximumSize(new Dimension(Integer.MAX_VALUE, 65));
                input.setPreferredSize(new Dimension(550, 65));
                input.setMargin(new Insets(10, 16, 10, 16));
                input.setAlignmentX(Component.LEFT_ALIGNMENT);
        }

        // PROSES LOGIN DENGAN VALIDASI DATABASE (Sudah di-update)
        private void prosesLogin() {
                String username = txtUsername.getText().trim();
                char[] passChars = txtPassword.getPassword();
                String password = new String(passChars);

                try {
                        if (username.isEmpty() || password.isEmpty()) {
                                JOptionPane.showMessageDialog(this, "Nama pengguna dan kata sandi wajib diisi!",
                                                "Peringatan", JOptionPane.WARNING_MESSAGE);
                                return;
                        }

                        // Memeriksa ke database lewat Database.java
                        boolean isValid = Database.validateLogin(username, password);

                        if (isValid) {
                                JOptionPane.showMessageDialog(this, "Login Berhasil! Selamat datang, " + username + ".",
                                                "Sukses", JOptionPane.INFORMATION_MESSAGE);

                                // TODO: Buka halaman utama aplikasi catatan kendaraan di sini
                        } else {
                                JOptionPane.showMessageDialog(this, "Username atau Password salah!", "Gagal Login",
                                                JOptionPane.ERROR_MESSAGE);
                        }

                } catch (SQLException e) {
                        JOptionPane.showMessageDialog(this, "Koneksi database bermasalah: " + e.getMessage(),
                                        "Error Database", JOptionPane.ERROR_MESSAGE);
                } finally {
                        java.util.Arrays.fill(passChars, '\0');
                }
        }

        public static void main(String[] args) {
                // Inisialisasi database agar tabel dibuat jika belum ada
                Database.initDatabase();

                SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
        }
}