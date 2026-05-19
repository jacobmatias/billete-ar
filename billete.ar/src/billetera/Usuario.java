package billetera;
import java.util.List;

public class Usuario {

	int dni;
	String nombre;
	int telefono;
	String email;
	double totalInvertido;
	List<Cuenta> cuentas;
	
	
	//constructor
	public Usuario(int dni,String nombre,int telefono,String email,double totalInvertido) {
		this.dni=dni;
		this.nombre=nombre;
		this.telefono=telefono;
		this.email=email;
		this.totalInvertido=totalInvertido;
	}
	
	void agregarCuenta(Cuenta cuenta) {
		
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

}
