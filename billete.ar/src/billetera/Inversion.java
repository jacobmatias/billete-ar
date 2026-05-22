package billetera;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public abstract class Inversion extends Actividad{
    
    private static int contadorId = 1;
    protected int id;
    protected int plazoDias;
    protected boolean activa;
    protected boolean precancelada;
	private LocalDate fechaConstitucion;
    
    public Inversion(double monto, int plazoDias) {
    	super(monto, plazoDias);
        this.id = contadorId++; 
        this.plazoDias = plazoDias;
        this.activa = true;
        this.precancelada = false;
    }
    
    public int getId() {
        return id;
    }

    public double getmonto() {
        return monto;
    }

    public boolean isActiva() {
        return activa;
    }
    
    public boolean isPrecancelada() {
        return precancelada;
    }

    public LocalDate getFechaConstitucion() {
        return fechaConstitucion;
    }

    public int getPlazoDias() {
        return plazoDias;
    }

}
