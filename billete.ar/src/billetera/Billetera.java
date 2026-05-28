package billetera;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class Billetera implements IBilletera {
	
	private Map<String, Usuario> usuarios; //clave = dni , valor = usuario
	private Map<String, Cuenta> cuentas; // lista de cuentas , clave = cvu y valor Cuenta
	private Map<String, Empresa> empresas; //lista de empresas , clave = cuit y valor = empresa
	private Map<String, String> aliasCVUs; //diccionario , clave = alias , valor = cvu
	private List<Actividad> historialGlobal;
	private Map<Integer, Inversion> inversiones;
	
	
	public Billetera() {
	    usuarios = new HashMap<>();
	    cuentas = new HashMap<>();
	    empresas = new HashMap<>();
	    aliasCVUs = new HashMap<>();
	    historialGlobal = new ArrayList<>();
	    inversiones = new HashMap<>();
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
		
		//verificamos que el usuario exista
		if(!usuarios.containsKey(dniUsuario)) throw new IllegalArgumentException("El usuario no existe!");
		
		//verificamos que el alias ya no exista
		if(aliasCVUs.containsKey(alias)) throw new IllegalArgumentException("El alias ya existe!");
		
		//generamos el siguiente CVU
		String cvu = Utilitarios.generarSiguienteCvu();
		
		//capturamos el usuario
		Usuario user = usuarios.get(dniUsuario);
		
		//creamos la cuenta
		CuentaRegular nuevaCuenta = new CuentaRegular(cvu, alias, user);
		
		//agregamos la cuenta creada
		cuentas.put(cvu, nuevaCuenta);
		
		//agregamos el alias creado
		aliasCVUs.put(alias, cvu);
		
		//asociamos la cuenta al usuario
		user.agregarCuenta(nuevaCuenta);
		
		
		//retornamos el cvu creado
		return cvu;
	}

	@Override
	public String crearCuentaPremium(String dniUsuario, String alias, double depositoInicial) {
		//verificamos que el usuario exista
				if(!usuarios.containsKey(dniUsuario)) throw new IllegalArgumentException("El usuario no existe!");
				
				//verificamos que el alias ya no exista
				if(aliasCVUs.containsKey(alias)) throw new IllegalArgumentException("El alias ya existe!");
				
				if(depositoInicial < 500000.00) throw new IllegalArgumentException("El monto es menor al permitido inicialmente, deben ser $500.000");
				
				//generamos el siguiente CVU
				String cvu = Utilitarios.generarSiguienteCvu();
				
				//capturamos el usuario
				Usuario user = usuarios.get(dniUsuario);
				
				//creamos la cuenta
				CuentaPremium nuevaCuenta = new CuentaPremium(cvu,alias,user,depositoInicial);
				
				//agregamos la cuenta creada
				cuentas.put(cvu, nuevaCuenta);
				
				//agregamos el alias creado
				aliasCVUs.put(alias, cvu);
				
				//asociamos la cuenta al usuario
				user.agregarCuenta(nuevaCuenta);
				
				
				//retornamos el cvu creado
				return cvu;
	}

	@Override
	public String crearCuentaCorporativa(String dniUsuario, String alias, String cuitEmpresa) {
		//verificamos que el usuario exista
		if(!usuarios.containsKey(dniUsuario)) throw new IllegalArgumentException("El usuario no existe!");
		
		//verificamos que el alias ya no exista
		if(aliasCVUs.containsKey(alias)) throw new IllegalArgumentException("El alias ya existe!");
		
		//verificamos que exista la empresa
		if(!empresas.containsKey(cuitEmpresa))  throw new IllegalArgumentException("La empresa no existe!");
		
		
		
		//capturamos la empresa
		
		Empresa empresa = empresas.get(cuitEmpresa);
		
		   // verificamos autorizado
	    if(!empresa.estaAutorizado(dniUsuario))
	        throw new IllegalArgumentException("Usuario no autorizado");
	    
	    // generamos cvu
	    String cvu = Utilitarios.generarSiguienteCvu();
	    

	    // obtenemos usuario
	    Usuario user = usuarios.get(dniUsuario);
	    
	    // creamos cuenta
	    CuentaCorporativa nuevaCuenta =
	        new CuentaCorporativa(cvu, alias, user, empresa);

	    // guardamos cuenta
	    cuentas.put(cvu, nuevaCuenta);

	    // guardamos alias
	    aliasCVUs.put(alias, cvu);

	    // asociamos usuario
	    user.agregarCuenta(nuevaCuenta);

	    // retornamos cvu
	    return cvu;
	}

	@Override
	public List<String> obtenerCuentas(String dniUsuario) {
		
		if(!usuarios.containsKey(dniUsuario)) throw new IllegalArgumentException("Usuario no registrado");
		
		Usuario user = usuarios.get(dniUsuario);
		

	    List<String> resultado = new ArrayList<>();

	    Iterator<Cuenta> it = user.obtenerCuentas().iterator();

	    while(it.hasNext()) {
	    	//polimorfismo!!
	        Cuenta cuenta = it.next();

	        resultado.add(cuenta.toString());
	    }

	    return resultado;
		
		
		
	}

	@Override
	public double obtenerSaldoDisponible(String cvu) {
		Cuenta cuenta = cuentas.get(cvu);
		if (cuenta == null) throw new IllegalArgumentException("Cuenta inexistente");
		return cuenta.obtenerSaldo();
	}

	@Override
	public void realizarTransferencia(String cvuOrigen, String cvuDestino, double monto) {
		
		//verificamos que las cuentas existan
		
		if(!cuentas.containsKey(cvuOrigen))
	        throw new IllegalArgumentException("Cuenta origen inexistente");

	    if(!cuentas.containsKey(cvuDestino))
	        throw new IllegalArgumentException("Cuenta destino inexistente");

	    Cuenta origen = cuentas.get(cvuOrigen);
	    Cuenta destino = cuentas.get(cvuDestino);

	    
	    boolean aprobada = false;
	    //verificamos que si es cuenta regular no supere lo maximo permitido
	    
	    if(destino instanceof CuentaRegular &&
	    		   destino.getSaldo() + monto > 5000000) {

	    		    throw new IllegalStateException(
	    		        "La cuenta regular supera el limite permitido");
	    		}
	    //
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

	    Usuario usuario = usuarios.get(dni);
	    Cuenta cuenta = cuentas.get(cvu);

	    if (usuario == null || cuenta == null)
	        throw new RuntimeException("Usuario o cuenta inexistente");

	    cuenta.debitar(monto);

	    // sumamos una actividad al volumen
	    cuenta.sumarVolumen(monto);

	    double tasa = 0.20;

	    Inversion inversion = new RentaFija(monto, plazoDias, tasa);

	    inversiones.put(inversion.getId(), inversion);

	    usuario.aumentarInvertido(monto);
	    
	    cuenta.agregarActividad(inversion);
	    historialGlobal.add(inversion);

	    return inversion.getId();
	}

	@Override
	public int realizarInversionDivisa(String dni, String cvu, double monto, int plazoDias, String divisa,
			double tasa) {
		Usuario usuario = usuarios.get(dni);
		Cuenta cuenta = cuentas.get(cvu);
		if (usuario == null || cuenta == null) throw new RuntimeException("Usuario o cuenta inexistente");
		
		cuenta.debitar(monto);
		
		cuenta.sumarVolumen(monto);

		
		Inversion inversion = new VinculadaADivisa(monto, plazoDias, divisa, tasa);
		inversiones.put(inversion.getId(), inversion);
		usuario.aumentarInvertido(monto);
		
		 cuenta.agregarActividad(inversion);
		 historialGlobal.add(inversion);
		
		return inversion.getId();
	}

	@Override
	public int realizarInversionLiquidez(String dni, String cvu, double monto, int plazoDias) {
		Usuario usuario = usuarios.get(dni);
		Cuenta cuenta = cuentas.get(cvu);
		if (usuario == null || cuenta == null) throw new RuntimeException("Usuario o cuenta inexistente");
		
		if (!(cuenta instanceof CuentaCorporativa)) {
			throw new IllegalArgumentException("El fondo de liquidez requiere una cuenta corporativa");
		}
		
		cuenta.debitar(monto);
		
		cuenta.sumarVolumen(monto);

		
		Inversion inversion = new FondoLiquidez(monto, plazoDias);
		inversiones.put(inversion.getId(), inversion);
		usuario.aumentarInvertido(monto);
		
		cuenta.agregarActividad(inversion);
		historialGlobal.add(inversion);
		
		return inversion.getId();
	}

	@Override
	public void precancelarInversion(String dni, String cvu, int idInversion) {
		Usuario usuario = usuarios.get(dni);
		Cuenta cuenta = cuentas.get(cvu);
		Inversion inversion = inversiones.get(idInversion);
		if (usuario == null || cuenta == null || inversion == null) throw new IllegalArgumentException("Datos inexistentes");
		
		double montoDevuelto = inversion.precancelar();
		cuenta.acreditar(montoDevuelto);
		usuario.disminuirInvertido(inversion.getMonto());
	}

	@Override
	public String consultarCvu(String alias) {
		
		if(!aliasCVUs.containsKey(alias)) throw new IllegalArgumentException("No existe el alias solcitiado");
		
		return aliasCVUs.get(alias);
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
		Usuario usuario = usuarios.get(dniUsuario);
		if (usuario == null) throw new RuntimeException("Usuario inexistente");
		return usuario.obtenerTotalInvertido();
	}

	@Override
	public List<String> cuentasConMayorVolumen(int cantidadTop) {

	    // verificamos que el top pedido sea valido
	    if(cantidadTop <= 0)
	        throw new IllegalArgumentException("La cantidad debe ser positiva");

	    // convertimos todas las cuentas del Map a una lista
	    // porque las listas se pueden ordenar
	    List<Cuenta> lista = new ArrayList<>(cuentas.values());

	    // ordenamos las cuentas de MAYOR volumen a MENOR volumen
	    lista.sort((a,b) -> Double.compare(b.getVolumenTransferido(), a.getVolumenTransferido()));

	    // lista donde vamos a guardar el resultado final
	    List<String> resultado = new ArrayList<>();

	    // creamos el iterador para recorrer las cuentas
	    Iterator<Cuenta> it = lista.iterator();

	    // contador para limitar la cantidad del TOP
	    int contador = 0;

	    // recorremos mientras:
	    // - haya elementos
	    // - no superemos el top solicitado
	    while(it.hasNext() && contador < cantidadTop) {

	        // obtenemos la siguiente cuenta
	        Cuenta cuenta = it.next();

	        // agregamos el formato usando toString()
	        resultado.add(cuenta.toString());

	        // aumentamos contador
	        contador++;
	    }

	    // devolvemos la lista final
	    return resultado;
	}
	
	@Override
	public String toString() {

	    StringBuilder sb = new StringBuilder();

	    sb.append("Usuarios registrados: ")
	      .append(usuarios.size())
	      .append("\nCuentas registradas: ")
	      .append(cuentas.size())
	      .append("\nEmpresas registradas: ")
	      .append(empresas.size());

	    return sb.toString();
	}	
}
