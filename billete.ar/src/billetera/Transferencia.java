package billetera;

public class Transferencia extends Actividad{
	
	private Cuenta cuentaOrigen;
    private Cuenta cuentaDestino;
    private boolean aprobada;

	public Transferencia(double monto, Cuenta cuentaDestino, Cuenta cuentaOrigen,boolean aprobada) {
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

        return "fecha: " + getFechaConstitucion()
                + "\norigen: "
                + cuentaOrigen.getTitular()
                + " (" + cuentaOrigen.getCvu() + ")"
                + "\ndestino: "
                + cuentaDestino.getTitular()
                + " (" + cuentaDestino.getCvu() + ")"
                + "\nmonto: " + getMonto()
                + "\n" + estado;
    }

	

}
