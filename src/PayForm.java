import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class PayForm extends JDialog {
    private JButton btncrediCard1month;
    private JButton btncreditCard1year;
    private JButton btntransfer1month;
    private JButton btntranfer1year;
    private JButton btnbtc1month;
    private JButton btnbtc1year;
    private JPanel payForm;
    private User user; // Kullanıcı bilgisi

    public PayForm(User user) {
        this.user = user; // Kullanıcı nesnesini al

        // Kredi Kartı 1 Aylık Ödeme Butonu
        btncrediCard1month.addActionListener(e -> handlePayment("Kredi Kartı", "1 Ay"));
        // Kredi Kartı 1 Yıllık Ödeme Butonu
        btncreditCard1year.addActionListener(e -> handlePayment("Kredi Kartı", "1 Yıl"));
        // Banka Transferi 1 Aylık Ödeme Butonu
        btntransfer1month.addActionListener(e -> handlePayment("Banka Transferi", "1 Ay"));
        // Banka Transferi 1 Yıllık Ödeme Butonu
        btntranfer1year.addActionListener(e -> handlePayment("Banka Transferi", "1 Yıl"));
        // Bitcoin 1 Aylık Ödeme Butonu
        btnbtc1month.addActionListener(e -> handlePayment("Bitcoin", "1 Ay"));
        // Bitcoin 1 Yıllık Ödeme Butonu
        btnbtc1year.addActionListener(e -> handlePayment("Bitcoin", "1 Yıl"));

        setContentPane(payForm);
        setTitle("Ödeme Sayfası");
        setSize(400, 300);
        setModal(true);
        setLocationRelativeTo(null);
    }

    private void handlePayment(String method, String duration) {
        final String DB_URL = "jdbc:mysql://localhost:3306/otaparkdb?serverTimezone=UTC";
        final String USER = "root";
        final String PASSWORD = "1234";

        String sql = "INSERT INTO payment (licensePlate, paymentMethod, paymentDuration) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // SQL sorgusuna değerleri ekle
            stmt.setString(1, user.placeNumber);
            stmt.setString(2, method);
            stmt.setString(3, duration);

            stmt.executeUpdate(); // Ödeme verisini veritabanına ekle

            // Başarı mesajını `System.out.println` ile yazdır
            System.out.println(String.format(
                    "Ödeme Başarılı!\n\nİsim: %s %s\nAraç Plakası: %s\nÖdeme Yöntemi: %s\nSüre: %s",
                    user.name, user.surname, user.placeNumber, method, duration
            ));
            dispose(); // Ödeme ekranını kapat

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Ödeme sırasında hata oluştu!"); // Hata mesajını `System.out.println` ile yazdır
            dispose(); // Hata durumunda da ekranı kapat
        }
    }

    public JPanel getPayFormPanel() {
        return payForm;
    }
}
