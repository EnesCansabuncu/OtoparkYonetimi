import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class RegisterForm extends JDialog {
    private JTextField tfName;
    private JTextField tfSurname;
    private JTextField tfplaceNumber;
    private JPasswordField tfPassword;
    private JButton btnRegister;
    private JButton btnCancel;
    private JPanel registerPanel;

    public RegisterForm(JFrame parent) {
        super(parent);
        setTitle("Create new account");
        setContentPane(registerPanel);
        setMinimumSize(new Dimension(800, 600));
        setModal(true);
        setLocationRelativeTo(parent);

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });

        setVisible(true);
    }

    private void registerUser() {
        String name = tfName.getText();
        String surname = tfSurname.getText();
        String placeNumber = tfplaceNumber.getText();
        String password = String.valueOf(tfPassword.getPassword());

        if (name.isEmpty() || surname.isEmpty() || placeNumber.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bu alanlar boş geçilemez", "HATA", JOptionPane.ERROR_MESSAGE);
            return;
        }

        user = addUserToDatabase(name, surname, placeNumber, password);

        if (user != null) {
            JOptionPane.showMessageDialog(this, "Kayıt başarılı!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
            dispose(); // Kayıt ekranını kapat
            new LoginForm(null); // Giriş ekranını aç
        } else {
            JOptionPane.showMessageDialog(this, "Kayıt başarısız oldu!", "HATA", JOptionPane.ERROR_MESSAGE);
        }
    }

    public User user;

    private User addUserToDatabase(String name, String surname, String placeNumber, String password) {
        User user = null;
        final String DB_URL = "jdbc:mysql://localhost:3306/otaparkdb?serverTimezone=UTC";
        final String USER = "root";
        final String PASSWORD = "1234";
        final String SQL = "INSERT INTO user (name, surname, licensePlate, password) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(SQL)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, placeNumber);
            preparedStatement.setString(4, password);

            int addedRows = preparedStatement.executeUpdate();

            if (addedRows > 0) {
                user = new User();
                user.name = name;
                user.surname = surname;
                user.placeNumber = placeNumber;
                user.password = password;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    public static void main(String[] args) {
        RegisterForm registerForm = new RegisterForm(null);
        User user = registerForm.user;

        if (user != null) {
            System.out.println("Kayıt başarılı oldu");
        } else {
            System.out.println("Kayıt başarısız oldu");
        }
    }
}
