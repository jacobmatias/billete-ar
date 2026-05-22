package billetera;

public abstract class FondoLiquidez extends Inversion {

    private static final String ACTIVO_FLE = "FLE";
    private static final double TASA_INTERES = 0.08;
    private double cotizacionInicial;

    public FondoLiquidez(double monto, int plazoDias) {
        super(monto, plazoDias);
        if (monto < 20000000) {
            throw new IllegalArgumentException("El monto minimo para invertir en un Fondo de Liquidez es de 20.000.000 de pesos.");
        }
        
        // cotizacion del FLE en el momento
        this.cotizacionInicial = Utilitarios.consultarCotizacion(ACTIVO_FLE);
    }
}