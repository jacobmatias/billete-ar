package billetera;

import java.util.ArrayList;
import java.util.List;

public abstract class Cuenta {

	protected String cvu;
	protected String alias;
	protected double saldo;
	protected Usuario titular;
	protected List<Actividad> actividades;
	protected double volumenTransferido;
	
	  //constructor
    public Cuenta(String cvu, String alias, Usuario titular) {
        this.cvu = cvu;
        this.alias = alias;
        this.titular = titular;
        this.volumenTransferido = 0; 
        this.saldo = 0;
        this.actividades = new ArrayList<>();
    }
	
    public void acreditar(double monto) {
        saldo += monto;
    }

    public void debitar(double monto) {
        saldo -= monto;
    }

    public double obtenerSaldo() {
        return saldo;
    }

    public void agregarActividad(Actividad actividad) {
        actividades.add(actividad);
    }

	public String getCvu() {
		return cvu;
	}

	public String getAlias() {
		return alias;
	}

	public double getSaldo() {
		return saldo;
	}

	public Usuario getTitular() {
		return titular;
	}

	public List<Actividad> getActividades() {
		return actividades;
	}
	
	public void sumarVolumen(double monto) {
	    volumenTransferido += monto;
	}

	public double getVolumenTransferido() {
	    return volumenTransferido;
	}
    
}

