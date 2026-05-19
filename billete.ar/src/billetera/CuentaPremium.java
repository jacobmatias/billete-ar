package billetera;

public class CuentaPremium extends Cuenta {

    private double montoMinimo = 500000;

    public CuentaPremium(String cvu, String alias,
                         Usuario titular, double saldoInicial) {

        super(cvu, alias, titular);
        this.saldo = saldoInicial;
    }
}