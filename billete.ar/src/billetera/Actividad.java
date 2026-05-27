package billetera;

import java.time.LocalDate;

public abstract class  Actividad {
	protected LocalDate fechaConstitucion;
	protected double monto;
	protected int plazoDias;
	
	public Actividad(double monto, int plazoDias) {
		this.fechaConstitucion = Utilitarios.hoy();
		this.monto = monto;
		this.plazoDias = plazoDias;
	}
	
	public double getplazoDias() {
        return plazoDias;
    }
	public LocalDate getFechaConstitucion() {
		return fechaConstitucion;
	}
	public double getMonto() {
		return monto;
	}
	
	public abstract String describir();
	 
	public String toString() {

	    StringBuilder sb = new StringBuilder();

	    sb.append("[")
	      .append(fechaConstitucion)
	      .append("] ")
	      .append("[plazo: ")
	      .append(plazoDias)
	      .append("] ")
	      .append("[monto: ")
	      .append(monto)
	      .append("] ")
	      .append(describir());

	    return sb.toString();
	}
	
}
