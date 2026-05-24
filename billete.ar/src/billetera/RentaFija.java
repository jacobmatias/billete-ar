package billetera;

public class RentaFija extends Inversion {
    
    private double tasaInteres;

    public RentaFija(double monto, int plazoDias, double tasaInteres) {
        super(monto, plazoDias);
        this.tasaInteres = tasaInteres;
    }

    @Override
    public double calcularInversion() {
        long dias = getDiasTranscurridos();
        double interes = monto * (tasaInteres / 365.0) * dias;
        return monto + interes;
    }
}