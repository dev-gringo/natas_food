package estrategia;

public class PagoTarjeta implements EstrategiaDePago {
    @Override
    public void pagar(double monto) {
        System.out.println("Procesando pago con Tarjeta de Crédito/Débito por $" +
                String.format("%.2f", monto) + ". Comisión 2%.");
        // Lógica real del pago
    }
}