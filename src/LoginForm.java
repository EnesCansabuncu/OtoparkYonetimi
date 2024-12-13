import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginForm extends JDialog {
    private JTextField tfplaceNumber;
    private JPasswordField tfpassword;
    private JButton btnCancel;
    private JButton btnLogin;
    private JPanel loginPanel;

    public LoginForm(JFrame parent) {
        super(parent);
        setTitle("Login");
        setContentPane(loginPanel);
        setMinimumSize(new Dimension(400, 450));
        setModal(true);
        setLocationRelativeTo(parent);

        // Cancel butonu olay işleyicisi
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Login ekranını kapat
            }
        });

        // Login butonu olay işleyicisi
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String licensePlate = tfplaceNumber.getText();
                String password = String.valueOf(tfpassword.getPassword());

                // Kullanıcı doğrulama işlemi
                user = getAuthenticatedUser(licensePlate, password);

                if (user != null) {
                    JOptionPane.showMessageDialog(LoginForm.this, "Giriş başarılı!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                    dispose(); // Login ekranını kapat
                    openPayForm(user); // Kullanıcıyı PayForm'a yönlendir
                } else {
                    JOptionPane.showMessageDialog(LoginForm.this, "Girdiğiniz bilgiler yanlış", "Hata!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setVisible(true);
    }

    public User user;

    private User getAuthenticatedUser(String licensePlate, String password) {
        User user = null;
        final String DB_URL = "jdbc:mysql://localhost:3306/otaparkdb?serverTimezone=UTC";
        final String USER = "root";
        final String PASSWORD = "1234";
        final String SQL = "SELECT * FROM user WHERE licensePlate = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(SQL)) {

            preparedStatement.setString(1, licensePlate);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.name = resultSet.getString("name");
                user.surname = resultSet.getString("surname");
                user.placeNumber = resultSet.getString("licensePlate");
                user.password = resultSet.getString("password");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    private void openPayForm(User user) {
        // Burada PayForm'u başlatıyoruz ve user bilgisini gönderiyoruz
        SwingUtilities.invokeLater(() -> {
            JFrame payFrame = new JFrame("Payment Page");
            PayForm payForm = new PayForm(user); // User bilgilerini PayForm'a gönderiyoruz
            payFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            payFrame.setSize(800, 600);
            payFrame.add(payForm.getPayFormPanel()); // PayForm panelini ekliyoruz
            payFrame.setVisible(true); // PayForm'u gösteriyoruz
        });
    }

    public static void main(String[] args) {
        new LoginForm(null); // Login formunu başlatıyoruz
    }
}
