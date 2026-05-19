package billetera;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.lang.RuntimeException;

/**
 * Clase auxiliar para obtener la fecha actual.
 * Se utiliza para facilitar las pruebas.
 */
public class Utilitarios {

    private static LocalDate sHoy;

    private static Map<String, Double> sCotizaciones = new HashMap<>();

    private static long sContadorCvu = 1;

    /*
     * Devuelve la fecha definida como Hoy. Si no está definida, toma la fecha
     * actual.
     */
    public static LocalDate hoy() {
        if (sHoy == null) {
            sHoy = LocalDate.now();
        }
        return sHoy;
    }

    /**
     * Fecha en formatoISO.
     * 
     * @param fecha cadena de texto con fecha en formato ISO
     */
    public static void definirHoy(String fecha) {
        definirHoy(LocalDate.parse(fecha));
    }

    /**
     * Establece la fecha recibida como Hoy.
     * 
     * @param fecha fecha a establecer como Hoy
     */
    public static void definirHoy(LocalDate fecha) {
        sHoy = fecha;
    }

    /**
     * Actualiza la cotización de un activo.
     * 
     * @param activo     activo a actualizar
     * @param cotizacion cotización del activo
     */
    public static void actualizarCotizacion(String activo, double cotizacion) {
        sCotizaciones.put(activo, cotizacion);
    }

    /**
     * Consulta la cotización de un activo.
     * 
     * @param activo activo a consultar
     * @return cotización del activo
     */
    public static double consultarCotizacion(String activo) {
        if (!sCotizaciones.containsKey(activo))
            throw new RuntimeException("El activo '" + activo + "' no está registrado.");

        return sCotizaciones.get(activo);
    }

    /**
     * Genera y devuelve el siguiente CVU disponible.
     * El CVU consta de 22 dígitos, utilizando un prefijo fijo para simulaciones y
     * un contador secuencial.
     * 
     * @return cadena de texto con el CVU generado
     */
    public static String generarSiguienteCvu() {
        return String.format("00000031%014d", sContadorCvu++);
    }

}