package billetera;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public abstract class Inversion extends Actividad {
    
    private static int contadorId = 1;
    protected int id;
    protected boolean activa;
    protected boolean precancelada;
    
    public Inversion(double monto, int plazoDias) {
    	super(monto, plazoDias);
        this.id = contadorId++; 
        this.activa = true;
        this.precancelada = false;
    }
    
    public int getId() {
        return id;
    }

    public double getMonto() {
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

    protected long getDiasTranscurridos() {
        long dias = ChronoUnit.DAYS.between(this.fechaConstitucion, Utilitarios.hoy());
        if (dias > plazoDias) {
            return plazoDias;
        }
        return dias;
    }

    public abstract double calcularInversion();
    
    public double precancelar() {
        if (!activa) {
            throw new RuntimeException("La inversión ya no está activa.");
        }
        this.activa = false;
        this.precancelada = true;
        
        double resultadoActual = calcularInversion();
        double rentabilidad = resultadoActual - monto;
        
        return monto + (rentabilidad / 2);
    }
    
    public double finalizar() {
        if (!activa) {
            throw new RuntimeException("La inversión ya no está activa.");
        }
        this.activa = false;
        return calcularInversion();
    }
    
    @Override
    public String describir() {
      //  return "desc: " + this.getClass().getSimpleName() + "\nmonto: " + monto + "\nplazo: " + plazoDias;
        
        StringBuilder sb = new StringBuilder();
        
        sb.append("desc: ")
          .append(this.getClass().getSimpleName())
          .append("\nmonto: ")
          .append(monto)
          .append("\nplazo: ")
          .append(plazoDias);
        
        return sb.toString();
        
    }
}