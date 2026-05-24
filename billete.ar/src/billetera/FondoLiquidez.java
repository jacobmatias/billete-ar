package billetera;

public class FondoLiquidez extends Inversion {

    private static final String ACTIVO_FLE = "FLE";
    private static final double TASA_INTERES = 0.08;
    private double cotizacionInicial;

    public FondoLiquidez(double monto, int plazoDias) {
        super(monto, plazoDias);
        if (monto < 20000000) {
            throw new IllegalArgumentException("El monto minimo para invertir en un Fondo de Liquidez es de 20.000.000 de pesos.");
        }
        this.cotizacionInicial = Utilitarios.consultarCotizacion(ACTIVO_FLE);
    }

    @Override
    public double calcularInversion() {
        long dias = getDiasTranscurridos();
        double cantidadFle = monto / cotizacionInicial;
        double interesFle = cantidadFle * (TASA_INTERES / 365.0) * dias;
        double totalFle = cantidadFle + interesFle;
        double cotizacionActual = Utilitarios.consultarCotizacion(ACTIVO_FLE);
        return totalFle * cotizacionActual;
    }

    @Override
    public double precancelar() {
        throw new RuntimeException("Los Fondos de Liquidez empresarial no son precancelables.");
    }
}