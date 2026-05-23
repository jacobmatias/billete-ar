package billetera;
import java.util.ArrayList;
import java.util.List;

public class Usuario {

	String dni;
	String nombre;
	String telefono;
	String email;
	double totalInvertido;
	List<Cuenta> cuentas;
	
	
	//constructor
	public Usuario(String dni,String nombre,String telefono,String email) {
		this.dni=dni;
		this.nombre=nombre;
		this.telefono=telefono;
		this.email=email;
		
		this.totalInvertido= 0; //todos arrancan en cero por defecto
		
		this.cuentas = new ArrayList<>(); //inicializamos la lista de cuentas para evitar el null point exception
	}
	
	void agregarCuenta(Cuenta cuenta) {
		cuentas.add(cuenta);
	}

	List<Cuenta> obtenerCuentas(){
		return cuentas;
	}
	
	double obtenerTotalInvertido(){
		return totalInvertido;
	}
	
	void aumentarInvertido(double monto) {
		this.totalInvertido = totalInvertido + monto;
	}

	
	void disminuirInvertido(double monto) {
		this.totalInvertido = totalInvertido - monto;
	}

	public String getDni() {
		return dni;
	}

}
