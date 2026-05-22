package billetera;

import java.util.List;
import java.util.Map;
import java.util.HashMap;


public class Billetera implements IBilletera {
	
	private Map<String, Usuario> usuarios; //clave = dni , valor = usuario
	private Map<String, Cuenta> cuentas; // lista de cuentas , clave = cvu y valor Cuenta
	private Map<String, Empresa> empresas; //lista de empresas , clave = cuit y valor = empresa
	private Map<String, String> alias; //diccionario , clave = alias , valor = cvu
	
	@Override
	public void registrarEmpresa(String cuit, String nombreFantasia, String telefono, String email,String nombreContacto) {
		
		if(empresas.containsKey(cuit)) { //validamos que no exista el cuit
			
			throw new IllegalArgumentException("La empresa ya existe");
			
		}
		
		
		Empresa nuevaEmpresa = new Empresa(cuit,nombreFantasia,telefono,email,nombreContacto);
		
		empresas.put(cuit,nuevaEmpresa);
	}

	@Override
	public void agregarPersonaAutorizada(String cuitEmpresa, String dniAutorizado) {
		
		if(empresas.containsKey(cuitEmpresa)) {
			
			if (!empresas.get(cuitEmpresa).estaAutorizado(dniAutorizado)) {
				empresas.get(cuitEmpresa).agregarAutorizado(dniAutorizado);
			}
			else {
				throw new IllegalArgumentException("La persona ya esta autorizada");
			}
			
		}
		
		else {
			throw new IllegalArgumentException("El cuit no existe");
		}
		
	}

	@Override
		public void registrarUsuario(String dni, String nombre, String telefono, String email) {
			
			
			
			if(usuarios.containsKey(dni)) { //validamos que no este el dni ya
				
				throw new IllegalArgumentException("El usuario ya existe");
			}
			
			Usuario nuevoUsuario = new Usuario(dni,nombre,telefono,email);
			
			
			usuarios.put(dni, nuevoUsuario);
			
	
		}

	@Override
	public String crearCuentaRegular(String dniUsuario, String alias) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String crearCuentaPremium(String dniUsuario, String alias, double depositoInicial) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String crearCuentaCorporativa(String dniUsuario, String alias, String cuitEmpresa) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> obtenerCuentas(String dniUsuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double obtenerSaldoDisponible(String cvu) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void realizarTransferencia(String cvuOrigen, String cvuDestino, double monto) {
		// TODO Auto-generated method stub

	}

	@Override
	public int realizarInversionRentaFija(String dni, String cvu, double monto, int plazoDias) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int realizarInversionDivisa(String dni, String cvu, double monto, int plazoDias, String divisa,
			double tasa) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int realizarInversionLiquidez(String dni, String cvu, double monto, int plazoDias) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void precancelarInversion(String dni, String cvu, int idInversion) {
		// TODO Auto-generated method stub

	}

	@Override
	public String consultarCvu(String alias) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> consultarHistorialGlobal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> consultarHistorialCuenta(String cvu) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> consultarHistorialUsuario(String dniUsuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double obtenerTotalInvertido(String dniUsuario) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<String> cuentasConMayorVolumen(int cantidadTop) {
		// TODO Auto-generated method stub
		return null;
	}

}
