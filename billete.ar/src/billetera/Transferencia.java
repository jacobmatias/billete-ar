package billetera;

public class Transferencia extends Actividad{
	
	private Cuenta cuentaOrigen;
    private Cuenta cuentaDestino;
    private boolean aprobada;

	public Transferencia(double monto, Cuenta cuentaOrigen, Cuenta cuentaDestino,boolean aprobada) {
		super(monto,0);
		this.cuentaOrigen  = cuentaOrigen;
        this.cuentaDestino = cuentaDestino;
        this.aprobada = aprobada;
	}

	public Cuenta getCuentaOrigen() {
		return cuentaOrigen;
	}

	public Cuenta getCuentaDestino() {
		return cuentaDestino;
	}
	
	public boolean isAprobada() {
		return aprobada;
	}

	@Override

	public String describir() {

	    String estado;

	    if(aprobada)
	        estado = "Aprobado";
	    else
	        estado = "Rechazado";

	    StringBuilder sb = new StringBuilder();

	    sb.append("fecha: ")
	      .append(getFechaConstitucion())
	      .append("\norigen: ")
	      .append(cuentaOrigen.getTitular())
	      .append(" (")
	      .append(cuentaOrigen.getCvu())
	      .append(")")
	      .append("\ndestino: ")
	      .append(cuentaDestino.getTitular())
	      .append(" (")
	      .append(cuentaDestino.getCvu())
	      .append(")")
	      .append("\nmonto: ")
	      .append(getMonto())
	      .append("\n")
	      .append(estado);

	    return sb.toString();
	}

	

}
