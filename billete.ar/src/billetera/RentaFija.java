package billetera;

public abstract class RentaFija extends Inversion {
    
    private double tasaInteres;

    public RentaFija(double monto, int plazoDias, double tasaInteres) {
        super(monto, plazoDias);
        this.tasaInteres = tasaInteres;
    }

}