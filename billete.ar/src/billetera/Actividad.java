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
}
