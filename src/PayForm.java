import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PayForm   {
    private JButton btncrediCard1month;
    private JButton btncreditCard1year;
    private JButton btntransfer1month;
    private JButton btntranfer1year;
    private JButton btnbtc1month;
    private JButton btnbtc1year;
    private JPanel payForm;
    private JButton çıkışYapButton;

    private User user; // User nesnesi burada saklanacak

    // PayForm constructor (yapıcı) metodunu güncelledik
    public PayForm(User user) {
        this.user = user; // User nesnesini alıyoruz
        PaymentManager paymentManager = PaymentManager.getInstance();

        // Butonların olay işleyicilerini tanımlıyoruz
        btncrediCard1month.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Payment payment = PaymentFactory.createPayment("creditCard1month");
                payment.pay();
                paymentManager.processPayment("Kredi Kartı");

                // Ödeme sonrası uyarı mesajı
                JOptionPane.showMessageDialog(payForm, "Kredi Kartı ile ödeme yapıldı.", "Ödeme Başarılı", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btncreditCard1year.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Payment payment = PaymentFactory.createPayment("creditCard1year");
                payment.pay();
                paymentManager.processPayment("Kredi Kartı");

                // Ödeme sonrası uyarı mesajı
                JOptionPane.showMessageDialog(payForm, "Kredi Kartı ile ödeme yapıldı.", "Ödeme Başarılı", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btntransfer1month.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Payment payment = PaymentFactory.createPayment("transfer1month");
                payment.pay();
                paymentManager.processPayment("Banka Transferi");

                // Ödeme sonrası uyarı mesajı
                JOptionPane.showMessageDialog(payForm, "Banka Transferi ile ödeme yapıldı.", "Ödeme Başarılı", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btntranfer1year.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Payment payment = PaymentFactory.createPayment("transfer1year");
                payment.pay();
                paymentManager.processPayment("Banka Transferi");

                // Ödeme sonrası uyarı mesajı
                JOptionPane.showMessageDialog(payForm, "Banka Transferi ile ödeme yapıldı.", "Ödeme Başarılı", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnbtc1month.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Payment payment = PaymentFactory.createPayment("btc1month");
                payment.pay();
                paymentManager.processPayment("Bitcoin");

                // Ödeme sonrası uyarı mesajı
                JOptionPane.showMessageDialog(payForm, "Bitcoin ile ödeme yapıldı.", "Ödeme Başarılı", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnbtc1year.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Payment payment = PaymentFactory.createPayment("btc1year");
                payment.pay();
                paymentManager.processPayment("Bitcoin");

                // Ödeme sonrası uyarı mesajı
                JOptionPane.showMessageDialog(payForm, "Bitcoin ile ödeme yapıldı.", "Ödeme Başarılı", JOptionPane.INFORMATION_MESSAGE);
            }
        });



    }

    public JPanel getPayFormPanel() {
        return payForm;
    }
}
