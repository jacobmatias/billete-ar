package billetera;

public class VinculadaADivisa extends Inversion {

    private String divisa;
    private double tasaInteres; 
    private double cotizacionInicial;

    public VinculadaADivisa(double monto, int plazoDias, String divisa, double tasaInteres) {
        super(monto, plazoDias);
        this.divisa = divisa;
        this.tasaInteres = tasaInteres;
        this.cotizacionInicial = Utilitarios.consultarCotizacion(divisa);
    }

    @Override
    public double calcularInversion() {
        long dias = getDiasTranscurridos();
        double cantidadDivisa = monto / cotizacionInicial;
        double interesDivisa = cantidadDivisa * (tasaInteres / 365.0) * dias;
        double totalDivisa = cantidadDivisa + interesDivisa;
        double cotizacionActual = Utilitarios.consultarCotizacion(divisa);
        return totalDivisa * cotizacionActual;
    }

    @Override
    public double precancelar() {
        if (!activa) {
            throw new RuntimeException("La inversión ya no está activa.");
        }
        this.activa = false;
        this.precancelada = true;
        
        long dias = getDiasTranscurridos();
        double cantidadDivisa = monto / cotizacionInicial;
        double interesDivisa = cantidadDivisa * (tasaInteres / 365.0) * dias;
        
        double totalDivisa = cantidadDivisa + (interesDivisa / 2.0);
        double cotizacionActual = Utilitarios.consultarCotizacion(divisa);
        return totalDivisa * cotizacionActual;
    }
}