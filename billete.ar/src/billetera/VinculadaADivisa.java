package billetera;

public abstract class VinculadaADivisa extends Inversion {

    private String divisa;
    private double tasaInteres; 
    private double cotizacionInicial;

    public VinculadaADivisa(double monto, int plazoDias, String divisa, double tasaInteres) {
        super(monto, plazoDias);
        this.divisa = divisa;
        this.tasaInteres = tasaInteres;
        
        //cotizacion de la divisa en el momento
        this.cotizacionInicial = Utilitarios.consultarCotizacion(divisa);
    }

}
