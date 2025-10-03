package estrategia;

public class PagoEfectivo implements EstrategiaDePago {
    @Override
    public void pagar(double monto) {
        System.out.println("Pago en Efectivo aceptado por $" +
                String.format("%.2f", monto) + ". Pendiente de confirmar al recibir.");
        // Lógica real del pago
    }
}