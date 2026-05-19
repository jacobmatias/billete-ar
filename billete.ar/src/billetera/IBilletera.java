package billetera;

import java.util.*;

/**
 * Interfaz principal para el sistema de Billetera Virtual.
 * Define las operaciones disponibles para usuarios, cuentas e inversiones.
 */
public interface IBilletera {

    /**
     * [Nuevo]
     * 11) Registra una nueva empresa en el sistema.
     * Lanza error si la empresa ya esta registrada o algun campo es inválido.
     *
     * @param cuit           El CUIT de la empresa.
     * @param nombreFantasia El nombre de fantasía de la empresa.
     * @param telefono       El teléfono de contacto de la empresa.
     * @param email          El correo electrónico de la empresa.
     * @param nombreContacto El nombre de la persona de contacto.
     */
    void registrarEmpresa(String cuit, String nombreFantasia, String telefono, String email, String nombreContacto);

    /**
     * [Nuevo]
     * 12) Agrega una persona autorizada a operar en nombre de una empresa.
     * Lanza error si la empresa no existe o la persona ya está autorizada.
     *
     * @param cuitEmpresa   El CUIT de la empresa a la cual se agregará el
     *                      autorizado.
     * @param dniAutorizado El DNI de la persona autorizada (puede no estar
     *                      registrado aún en el sistema).
     */
    void agregarPersonaAutorizada(String cuitEmpresa, String dniAutorizado);

    /**
     * 1) Registra un nuevo usuario en la plataforma.
     * Lanza error si el usuario ya está registrado o algun campo es inválido.
     *
     * @param dni      El DNI del usuario.
     * @param nombre   El nombre completo del usuario.
     * @param telefono El número de teléfono del usuario.
     * @param email    El correo electrónico del usuario.
     */
    void registrarUsuario(String dni, String nombre, String telefono, String email);

    /**
     * 2) Crea una nueva cuenta de tipo regular para un usuario existente.
     * Lanza error si el usuario no existe o el alias ya está registrado.
     *
     * @param dniUsuario El DNI del usuario titular de la cuenta.
     * @param alias      El alias deseado para la cuenta.
     * @return El CVU de la cuenta creada.
     */
    String crearCuentaRegular(String dniUsuario, String alias);

    /**
     * 2) Crea una nueva cuenta de tipo premium para un usuario existente.
     * Lanza error si el usuario no existe o el alias ya está registrado.
     *
     * @param dniUsuario      El DNI del usuario titular de la cuenta.
     * @param alias           El alias deseado para la cuenta.
     * @param depositoInicial El monto inicial depositado en la cuenta (debe cumplir
     *                        los requisitos mínimos).
     * @return El CVU de la cuenta creada.
     */
    String crearCuentaPremium(String dniUsuario, String alias, double depositoInicial);

    /**
     * 2) Crea una nueva cuenta de tipo corporativa vinculada a una empresa y a un
     * usuario autorizado.
     * Lanza error si el usuario no existe, el alias ya está registrado o la empresa
     * no existe.
     *
     * @param dniUsuario  El DNI del usuario que será titular/administrador de la
     *                    cuenta.
     * @param alias       El alias deseado para la cuenta.
     * @param cuitEmpresa El CUIT de la empresa a la que pertenece la cuenta.
     * @return El CVU de la cuenta creada.
     */
    String crearCuentaCorporativa(String dniUsuario, String alias, String cuitEmpresa);

    /**
     * 3) Obtiene una lista con los identificadores (CVU o alias) de todas las
     * cuentas asociadas a un usuario.
     * Para cada cuenta en el listado se debe con el siguiente formato:
     * - "[Tipo]: [Alias] ([CVU])"
     * 
     * Lanza error si el usuario no existe.
     *
     * @param dniUsuario El DNI del usuario.
     * @return Una lista de cadenas de texto representando las cuentas del usuario.
     */
    List<String> obtenerCuentas(String dniUsuario);

    /**
     * 4) Consulta el saldo disponible de una cuenta específica.
     * Lanza error si la cuenta no existe.
     *
     * @param cvu El CVU de la cuenta a consultar.
     * @return El monto correspondiente al saldo disponible.
     */
    double obtenerSaldoDisponible(String cvu);

    /**
     * 5) Realiza una transferencia de dinero entre dos cuentas.
     * Lanza error si alguna de las cuentas no existe.
     *
     * @param cvuOrigen  El CVU de la cuenta origen desde la cual se debitará el
     *                   monto.
     * @param cvuDestino El CVU de la cuenta destino que recibirá el dinero.
     * @param monto      El importe a transferir.
     */
    void realizarTransferencia(String cvuOrigen, String cvuDestino, double monto);

    /**
     * 6) Genera una nueva inversión de renta fija desde una cuenta.
     * Lanza error si el usuario o la cuenta no existe, o si algun dato es inválido.
     *
     * @param dni       El DNI del usuario.
     * @param cvu       El CVU de la cuenta desde donde se invierte.
     * @param monto     El monto de dinero a invertir.
     * @param plazoDias El plazo en días de la inversión.
     * @return El identificador único de la inversión realizada.
     */
    int realizarInversionRentaFija(String dni, String cvu, double monto, int plazoDias);

    /**
     * 6) Genera una nueva inversión en divisas extranjeras desde una cuenta.
     * Lanza error si el usuario o la cuenta no existe, o si algun dato es inválido.
     *
     * @param dni       El DNI del usuario.
     * @param cvu       El CVU de la cuenta desde donde se invierte.
     * @param monto     El monto de dinero a invertir en moneda local.
     * @param plazoDias El plazo en días de la inversión.
     * @param divisa    El identificador de la divisa elegida.
     * @return El identificador único de la inversión realizada.
     */
    int realizarInversionDivisa(String dni, String cvu, double monto, int plazoDias, String divisa, double tasa);

    /**
     * 6) Genera una nueva inversión de liquidez (fondo común) desde una cuenta.
     * Lanza error si el usuario o la cuenta no existe, o si algun dato es inválido.
     * 
     * @param dni       El DNI del usuario.
     * @param cvu       El CVU de la cuenta desde donde se invierte.
     * @param monto     El monto de dinero a invertir.
     * @param plazoDias El plazo estimado en días.
     * @return El identificador único de la inversión realizada.
     */
    int realizarInversionLiquidez(String dni, String cvu, double monto, int plazoDias);

    /**
     * [Nuevo]
     * 13) Precancela una inversión activa de forma anticipada.
     * Lanza error si algun dato es inválido, la inversión no existe o no está
     * activa.
     *
     * @param dni         El DNI del usuario.
     * @param cvu         El CVU de la cuenta asociada a la inversión.
     * @param idInversion El identificador único de la inversión a cancelar.
     */
    void precancelarInversion(String dni, String cvu, int idInversion);

    /**
     * [Nuevo]
     * 14) Dado un alias consultar el CVU asociado.
     * Lanza error si el alias no está registrado.
     *
     * @param alias Alias para consultar el CVU.
     * @return cvu asociado al alias. Si el alias no está registrado debe lanzar una
     *         excepción.
     */
    String consultarCvu(String alias);

    /**
     * 7) Obtiene el historial global de actividades del sistema.
     * Las actividades se deben mostrar con el siguiente formato:
     * - transferencia:
     * ```
     * origen: [dni] ([cvu])
     * destino: [dni] ([cvu])
     * monto: [monto]
     * [Aprovado/Rechazado]
     * ```
     * - inversion:
     * ```
     * origen: [dni] ([cvu])
     * desc: [tipo inversion]
     * monto: [monto]
     * plazo: [plazo]
     * [Aprovado/Rechazado]
     * ```
     *
     * @return Una lista con el detalle de las actividades globales.
     */
    List<String> consultarHistorialGlobal();

    /**
     * 8) Obtiene el historial de actividades asociado a una cuenta específica.
     * Con el mismo formato que en 7) historial global
     * 
     * Lanza error si la cuenta no existe.
     *
     * @param cvu El CVU de la cuenta a consultar.
     * @return Una lista con los actividades realizados en la cuenta.
     */
    List<String> consultarHistorialCuenta(String cvu);

    /**
     * 8) Obtiene el historial de actividades de un usuario a lo largo de todas
     * sus cuentas.
     * Con el mismo formato que en 7) historial global
     * 
     * Lanza error si el usuario no existe.
     * 
     * @param dniUsuario El DNI del usuario a consultar.
     * @return Una lista de los actividades realizados por el usuario.
     */
    List<String> consultarHistorialUsuario(String dniUsuario);

    /**
     * 9) Calcula el monto total que un usuario tiene invertido considerando todas
     * sus cuentas.
     * Lanza error si el usuario no existe.
     *
     * @param dniUsuario El DNI del usuario a consultar.
     * @return El monto total invertido por el usuario.
     */
    double obtenerTotalInvertido(String dniUsuario);

    /**
     * 10) Obtiene las cuentas con la mayor cantidad de actividades registradas.
     * Se debe usar el mismo formato que el punto 3):
     * - "[Tipo]: [Alias] ([CVU])"
     * 
     * Lanza error si cantidadTop no es positiva.
     *
     * @param cantidadTop El número de cuentas a retornar (Top N).
     * @return Una lista con el detalle de las cuentas con mayor volumen.
     */
    List<String> cuentasConMayorVolumen(int cantidadTop);

    /**
     * [Bonus Track]
     * 15) Procesa todas las inversiones que vencen el dia de hoy
     * y actualiza los saldos agregando los intereses generados segun el tipo de
     * inversion.
     * Sea por taza fija o por cotización de activos más tasa.
     * 
     * El dia actual y las cotizaciones de los activos se deben consultar a
     * Utilitarios.
     * 
     */
    // void procesarInversionesQueVencenHoy();
}