package billetera;

import java.util.ArrayList;
import java.util.List;

public abstract class Cuenta {

	private String cvu;
	private String alias;
	protected double saldo;
	private Usuario titular;
	protected List<Actividad> actividades;
	
	  //constructor
    public Cuenta(String cvu, String alias, Usuario titular) {
        this.cvu = cvu;
        this.alias = alias;
        this.titular = titular;
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
}

