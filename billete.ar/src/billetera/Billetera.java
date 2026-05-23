package billetera;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;


public class Billetera implements IBilletera {
	
	private Map<String, Usuario> usuarios; //clave = dni , valor = usuario
	private Map<String, Cuenta> cuentas; // lista de cuentas , clave = cvu y valor Cuenta
	private Map<String, Empresa> empresas; //lista de empresas , clave = cuit y valor = empresa
	private Map<String, String> alias; //diccionario , clave = alias , valor = cvu
	private List<Actividad> historialGlobal;
	
	
	public Billetera() {
	    usuarios = new HashMap<>();
	    cuentas = new HashMap<>();
	    empresas = new HashMap<>();
	    alias = new HashMap<>();
	    historialGlobal = new ArrayList<>();
	}
	
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
		if(!cuentas.containsKey(cvuOrigen))
	        throw new IllegalArgumentException("Cuenta origen inexistente");

	    if(!cuentas.containsKey(cvuDestino))
	        throw new IllegalArgumentException("Cuenta destino inexistente");

	    Cuenta origen = cuentas.get(cvuOrigen);
	    Cuenta destino = cuentas.get(cvuDestino);

	    boolean aprobada = false;

	    if(origen.getSaldo() >= monto){

	        origen.debitar(monto);
	        destino.acreditar(monto);

	        aprobada = true;

	        origen.sumarVolumen(monto);
	        destino.sumarVolumen(monto);
	    }

	    Transferencia t = new Transferencia(monto,origen,destino,aprobada);
	    origen.agregarActividad(t);
	    destino.agregarActividad(t);

	    historialGlobal.add(t);
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
		List<String> resultado = new ArrayList<>();

	    for(Actividad a : historialGlobal){
	        resultado.add(a.describir());
	    }

	    return resultado;
	}

	@Override
	public List<String> consultarHistorialCuenta(String cvu) {
		if(!cuentas.containsKey(cvu))
	        throw new IllegalArgumentException();

	    List<String> resultado = new ArrayList<>();

	    Cuenta cuenta = cuentas.get(cvu);

	    for(Actividad a : cuenta.getActividades()){
	        resultado.add(a.describir());
	    }

	    return resultado;
	}

	@Override
	public List<String> consultarHistorialUsuario(String dniUsuario) {
		if(!usuarios.containsKey(dniUsuario))
	        throw new IllegalArgumentException("Usuario inexistente");
	    Usuario usuario =usuarios.get(dniUsuario);
	    List<String> historial = new ArrayList<>();
	    for(Cuenta cuenta :usuario.obtenerCuentas()) {
	        for(Actividad actividad :cuenta.getActividades()) {
	            historial.add(actividad.describir());
	        }
	    }
	    return historial;
	}

	@Override
	public double obtenerTotalInvertido(String dniUsuario) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<String> cuentasConMayorVolumen(int cantidadTop) {
		List<Cuenta> lista = new ArrayList<>(cuentas.values());
	    lista.sort((a,b)->Double.compare(b.getVolumenTransferido(),a.getVolumenTransferido()));
	    List<String> resultado =
	            new ArrayList<>();
	    for(int i=0;i < cantidadTop && i < lista.size();i++){
	        Cuenta c = lista.get(i);
	        resultado.add(c.getAlias() + " (" + c.getCvu() + ")");}
	    return resultado;
	}

}
