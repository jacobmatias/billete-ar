package billetera;

import java.time.LocalDate;
import java.util.List;

public class Principal {

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("      SIMULACIÓN DEL SISTEMA BILLETE.AR          ");
        System.out.println("=================================================\n");

        LocalDate hoy = LocalDate.now();
        Utilitarios.definirHoy(hoy);
        Utilitarios.actualizarCotizacion("USD", 1020.50);
        Utilitarios.actualizarCotizacion("EUR", 1100.25);
        Utilitarios.actualizarCotizacion("FLE", 1.02);
        System.out.println("Cotizaciones iniciales - USD: 1020.50 | EUR: 1100.25 | FLE: 1.02\n");

        IBilletera billetera = new Billetera();

        System.out.println("[0] Estado inicial de la billetera: ");
        System.out.println(billetera);
        System.out.println("-------------------------------------------------\n");

        // 1. Registrar usuarios y empresas
        System.out.println("[1] Registrando usuarios y empresas...");
        billetera.registrarUsuario("11111111", "Ana Garcia", "(011) 4444-6666", "aGarcia@mockMail.com");
        billetera.registrarUsuario("22222222", "Carlos Lopez", "(011) 4444-5555", "cLopes@otroMock.com");

        System.out.println("  Registrando Empresa...");
        billetera.registrarEmpresa("30-11111111-9", "Empresa Ficticia SA", "(011) 5555-1234",
                "contacto@empresaficticia.com", "Roberto Sanchez");

        System.out.println("  Agregando personas autorizadas...");
        billetera.agregarPersonaAutorizada("30-11111111-9", "11111111");
        billetera.agregarPersonaAutorizada("30-11111111-9", "11111112");
        billetera.agregarPersonaAutorizada("30-11111111-9", "11111113");

        System.out.println("Usuarios y empresas registrados con éxito.\n");

        // 2. Crear cuentas
        System.out.println("[2] Creando cuentas...");
        String cvuAnaRegular = billetera.crearCuentaRegular("11111111", "ana.regular");

        try {
            System.out.println("  Intentando crear Cuenta Premium con $100.000 (debe fallar)...");
            billetera.crearCuentaPremium("22222222", "carlos.premium.fail", 100000);
        } catch (IllegalArgumentException e) {
            System.out.println("  -> Error esperado capturado: " + e.getMessage());
        }

        System.out.println("  Creando Cuenta Premium válida con $1.000.000...");
        String cvuCarlosPremium = billetera.crearCuentaPremium("22222222", "carlos.premium", 1000000);

        System.out.println("  Creando Cuenta Corporativa...");
        String cvuAnaCorp = billetera.crearCuentaCorporativa("11111111", "ana.corp", "30-11111111-9");
        System.out.println("Cuentas creadas exitosamente.\n");

        System.out.println("CVU Ana (Regular): " + cvuAnaRegular);
        System.out.println("CVU Carlos (Premium): " + cvuCarlosPremium);

        System.out.println("  Consultando CVU por alias 'ana.regular'...");
        String cvuConsultado = billetera.consultarCvu("ana.regular");
        System.out.println("  CVU Obtenido por alias: " + cvuConsultado + "\n");

        // 3. Realizar transferencias
        System.out.println("[3] Realizando transferencias...");
        System.out.println("  Carlos transfiere $200.000 a Ana...");
        billetera.realizarTransferencia(cvuCarlosPremium, cvuAnaRegular, 200000);
        System.out.println("  Saldo Disponible Carlos: $" + billetera.obtenerSaldoDisponible(cvuCarlosPremium));
        System.out.println("  Saldo Disponible Ana: $" + billetera.obtenerSaldoDisponible(cvuAnaRegular) + "\n");

        // 4. Inversiones
        System.out.println("[4] Realizando inversiones...");
        System.out.println("  Carlos invierte $500.000 en Renta Fija a 30 días...");
        int idInvCarlos = billetera.realizarInversionRentaFija("22222222", cvuCarlosPremium, 500000, 30);

        System.out.println("  Ana invierte $100.000 Vinculada a Divisa (USD)...");
        billetera.realizarInversionDivisa("11111111", cvuAnaRegular, 100000, 60, "USD", 0.05);

        try {
            System.out.println(
                    "  Carlos intenta crear un Fondo de Liquidez con $1.000.000 (requiere 20M, debe fallar)...");
            billetera.realizarInversionLiquidez("22222222", cvuCarlosPremium, 1000000, 30);
        } catch (IllegalArgumentException e) {
            System.out.println("  -> Error esperado capturado: " + e.getMessage());
        }

        System.out.println("Inversiones registradas.\n");

        // 5. Consulta de saldos y total invertido
        System.out.println("[5] Consultando saldos y total invertido (operación en O(1))...");
        System.out.println("  Total invertido por Carlos: $" + billetera.obtenerTotalInvertido("22222222"));
        System.out.println(
                "  Saldo Disponible de Carlos: $" + billetera.obtenerSaldoDisponible(cvuCarlosPremium));
        System.out.println("  Total invertido por Ana: $" + billetera.obtenerTotalInvertido("11111111") + "\n");

        System.out.println("  Simulando el paso de 20 días...");
        Utilitarios.definirHoy(hoy.plusDays(20));
        Utilitarios.actualizarCotizacion("USD", 1080.00);
        Utilitarios.actualizarCotizacion("EUR", 1160.50);
        Utilitarios.actualizarCotizacion("FLE", 1.15);
        System.out.println("  Nuevas cotizaciones - USD: 1080.00 | EUR: 1160.50 | FLE: 1.15\n");

        // 6. Precancelar inversión
        System.out.println("[6] Probando precancelación...");
        System.out.println("  Cancelando inversión de Carlos (ID: " + idInvCarlos + ")...");

        billetera.precancelarInversion("22222222", cvuCarlosPremium, idInvCarlos);
        System.out.println("  -> Inversión cancelada con éxito.");

        System.out.println(
                "  Intentando precancelar una inversión con ID inexistente");
        try {
            billetera.precancelarInversion("22222222", cvuCarlosPremium, -123);
        } catch (IllegalArgumentException e) {
            System.out.println("  -> Error esperado capturado: " + e.getMessage());
        }
        System.out.println();

        // 7. Historial y Volumen
        System.out.println("[7] Consultando Historial y Cuentas con mayor volumen...");
        System.out.println("  Historial Global del sistema:");
        for (String act : billetera.consultarHistorialGlobal()) {
            System.out.println("    - " + act);
        }

        System.out.println("\n  Top 2 cuentas con mayor volumen de transacciones:");
        List<String> topCuentas = billetera.cuentasConMayorVolumen(2);
        for (int i = 0; i < topCuentas.size(); i++) {
            System.out.println("    #" + (i + 1) + " CVU: " + topCuentas.get(i));
        }

        System.out.println("\n-------------------------------------------------");
        System.out.println("[8] Estado final de la billetera: ");
        System.out.println(billetera);
        System.out.println("-------------------------------------------------\n");

        System.out.println("=================================================");
        System.out.println("      SIMULACIÓN COMPLETADA EXITOSAMENTE         ");
        System.out.println("=================================================");
    }
}