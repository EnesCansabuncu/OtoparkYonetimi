public class PaymentManager {
    // Singleton örneği
    private static PaymentManager instance;

    // Private constructor
    private PaymentManager() {}

    // Singleton getInstance metodu
    public static PaymentManager getInstance() {
        if (instance == null) {
            instance = new PaymentManager();
        }
        return instance;
    }

    // Ödeme işlemi metodu
    public void processPayment(String paymentMethod) {
        // Ödeme metoduna göre işlem yapabiliriz.
        System.out.println(paymentMethod + " ile ödeme yapıldı!");
    }
}
