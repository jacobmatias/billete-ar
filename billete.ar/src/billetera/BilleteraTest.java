package billetera;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;
import static org.junit.Assert.*;

public class BilleteraTest {
    private IBilletera billetera;

    @Before
    public void setUp() {
        billetera = new Billetera();
        billetera.registrarUsuario("11111111", "Alice", "123", "a@test.com");
        billetera.registrarUsuario("22222222", "Bob", "456", "b@test.com");

        // TODO: inicializar Utilitarios con valoraciones para las pruenas
        // que permitan calcular los intereses parciales y totales. así como los
        // vencimientos de las inversiones
        Utilitarios.definirHoy(LocalDate.now());
        Utilitarios.actualizarCotizacion("USD", 1000);
        Utilitarios.actualizarCotizacion("EUR", 1100);
    }

    @Test
    public void testCrearCuentaRegular() {
        String cvu = billetera.crearCuentaRegular("11111111", "alice.regular");
        List<String> cuentas = billetera.obtenerCuentas("11111111");
        assertEquals(1, cuentas.size());
        assertEquals(0.0, billetera.obtenerSaldoDisponible(cvu), 0.01);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCrearCuentaPremiumFallaPorPocoDinero() {
        billetera.crearCuentaPremium("11111111", "alice.premium", 100000);
    }

    @Test
    public void testCrearCuentaPremiumExito() {
        String cvu = billetera.crearCuentaPremium("11111111", "alice.premium", 600000);
        List<String> cuentas = billetera.obtenerCuentas("11111111");
        assertEquals(1, cuentas.size());
        assertEquals(600000.0, billetera.obtenerSaldoDisponible(cvu), 0.01);
    }

    @Test
    public void testTransferencia() {
        String cvuAlice = billetera.crearCuentaPremium("11111111", "alice.p", 1000000);
        String cvuBob = billetera.crearCuentaRegular("22222222", "bob.r");

        billetera.realizarTransferencia(cvuAlice, cvuBob, 100000);

        assertEquals(900000.0, billetera.obtenerSaldoDisponible(cvuAlice), 0.01);
        assertEquals(100000.0, billetera.obtenerSaldoDisponible(cvuBob), 0.01);

        // Validar historial
        assertEquals(1, billetera.consultarHistorialCuenta(cvuAlice).size());
        assertEquals(1, billetera.consultarHistorialCuenta(cvuBob).size());
    }

    @Test
    public void testInversionYSaldoDisponible() {
        String cvuAlice = billetera.crearCuentaPremium("11111111", "alice.p", 2000000);

        billetera.realizarInversionRentaFija("11111111", cvuAlice, 500000, 30);
        double invertidoMasDisponible = billetera.obtenerSaldoDisponible(cvuAlice)
                + billetera.obtenerTotalInvertido("11111111");

        assertEquals(500000.0, billetera.obtenerTotalInvertido("11111111"), 0.01);
        assertEquals(1500000.0, billetera.obtenerSaldoDisponible(cvuAlice), 0.01);
        assertEquals(2000000.0, invertidoMasDisponible, 0.01);

        billetera.realizarInversionDivisa("11111111", cvuAlice, 100000, 60, "USD", 0.05);
        assertEquals(600000.0, billetera.obtenerTotalInvertido("11111111"), 0.01);
    }

    @Test
    public void testTopCuentasPorVolumenDeTransacciones() {
        String cvuAlice = billetera.crearCuentaPremium("11111111", "alice.p", 5000000);
        String cvuBob = billetera.crearCuentaPremium("22222222", "bob.p", 5000000);

        billetera.realizarTransferencia(cvuAlice, cvuBob, 100);
        billetera.realizarTransferencia(cvuAlice, cvuBob, 100);
        billetera.realizarInversionRentaFija("11111111", cvuAlice, 500, 30);

        List<String> top = billetera.cuentasConMayorVolumen(2);
        assertTrue(top.get(0).contains(cvuAlice)); // Alice tiene 3 transacciones (2 transf, 1 inv)
        assertTrue(top.get(1).contains(cvuBob)); // Bob tiene 2 (las transferencias recibidas)
    }

    @Test(expected = IllegalStateException.class)
    public void testLimiteCuentaRegular() {
        String cvuAlice = billetera.crearCuentaPremium("11111111", "alice.p", 6000000);
        String cvuBob = billetera.crearCuentaRegular("22222222", "bob.r");

        // intenta transferir 5,1 millones, lo que supera el limite de 5M para las
        // cuentas regulares
        billetera.realizarTransferencia(cvuAlice, cvuBob, 5100000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRestriccionFondoLiquidez() {
        String cvuAlice = billetera.crearCuentaPremium("11111111", "alice.p", 5000000);
        // intenta invertir en fondo de liquidez sin cuenta corporativa
        billetera.realizarInversionLiquidez("11111111", cvuAlice, 5000000, 30);
    }

    @Test
    public void testRegistrarEmpresaYCuentaCorporativa() {
        billetera.registrarEmpresa("30-12345678-9", "Empresa SA", "11223344", "contacto@empresa.com", "Juan");
        billetera.agregarPersonaAutorizada("30-12345678-9", "11111111");

        String cvuCorp = billetera.crearCuentaCorporativa("11111111", "empresa.corp", "30-12345678-9");
        List<String> cuentas = billetera.obtenerCuentas("11111111");

        assertEquals(1, cuentas.size());
        assertTrue(cuentas.get(0).contains(cvuCorp));
    }

    @Test
    public void testConsultarCvu() {
        String cvu = billetera.crearCuentaRegular("11111111", "alice.regular.alias");
        String cvuConsultado = billetera.consultarCvu("alice.regular.alias");
        assertEquals(cvu, cvuConsultado);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConsultarCvuInexistenteLanzaError() {
        billetera.consultarCvu("alias.inexistente");
    }

    @Test
    public void testPrecancelarInversionRentaFija() {
        String cvuAlice = billetera.crearCuentaPremium("11111111", "alice.p.inv", 2000000);
        int idInversion = billetera.realizarInversionRentaFija("11111111", cvuAlice, 500000, 30); // TNA 20%

        assertEquals(500000.0, billetera.obtenerTotalInvertido("11111111"), 0.01);

        Utilitarios.definirHoy(Utilitarios.hoy().plusDays(20));

        billetera.precancelarInversion("11111111", cvuAlice, idInversion);

        // Despues de precancelar, el saldo invertido debe bajar a 0
        assertEquals(0.0, billetera.obtenerTotalInvertido("11111111"), 0.01);

        // monto_invertido x (taza_interes / 365 dias_del_año) * cant_dias
        double interesesEsperados = 500000 * (0.20 / 365) * 20;
        double saldoEsperado = 2000000D; // saldo inicial
        saldoEsperado += interesesEsperados / 2; // la mitad porque fué precancelado.

        assertEquals(saldoEsperado, billetera.obtenerSaldoDisponible(cvuAlice), 0.01);
    }

    @Test
    public void testPrecancelarInversionDivisa() {
        String cvuAlice = billetera.crearCuentaPremium("11111111", "alice.p.inv", 2000000);
        int idInversion = billetera.realizarInversionDivisa("11111111", cvuAlice, 500000, 30, "USD", 0.03);

        assertEquals(500000.0, billetera.obtenerTotalInvertido("11111111"), 0.01);

        // calculo el equivalente en pesos de los USD invertidos
        double divisasEquivalente = 500000 / Utilitarios.consultarCotizacion("USD");

        // simulo que pasaron 20 dias y el dolar subio un 6%
        Utilitarios.definirHoy(Utilitarios.hoy().plusDays(20));
        Utilitarios.actualizarCotizacion("USD", 1060);

        billetera.precancelarInversion("11111111", cvuAlice, idInversion);

        // Despues de precancelar, el saldo invertido debe bajar a 0
        assertEquals(0.0, billetera.obtenerTotalInvertido("11111111"), 0.01);

        // monto_invertido x (taza_interes / 365 dias) * cant_dias
        double interesesEnDivisas = divisasEquivalente * (0.03 / 365) * 20; // calculo los intereses en USD.
        double saldoEsperado = 2000000D - 500000; // saldo inicial - saldo invertido
        interesesEnDivisas /= 2; // divido los intereses a la mitad porque fue precancelado.

        // sumo al saldo el equivalente en pesos de los USD obtenidos.
        saldoEsperado += (divisasEquivalente + interesesEnDivisas) * Utilitarios.consultarCotizacion("USD");

        assertEquals(saldoEsperado, billetera.obtenerSaldoDisponible(cvuAlice), 0.01);
    }

    @Test
    public void testConsultarHistorialGlobal() {
        String cvuAlice = billetera.crearCuentaPremium("11111111", "alice.hist", 1000000);
        String cvuBob = billetera.crearCuentaRegular("22222222", "bob.hist");

        billetera.realizarTransferencia(cvuAlice, cvuBob, 50000);

        List<String> global = billetera.consultarHistorialGlobal();
        assertFalse("El historial global no debe estar vacio", global.isEmpty());
    }

    @Test
    public void testConsultarHistorialUsuario() {
        String cvuAlice = billetera.crearCuentaPremium("11111111", "alice.hist.usu", 1000000);
        String cvuBob = billetera.crearCuentaRegular("22222222", "bob.hist.usu");

        billetera.realizarTransferencia(cvuAlice, cvuBob, 50000);
        billetera.realizarTransferencia(cvuAlice, cvuBob, 10000);

        List<String> historialAlice = billetera.consultarHistorialUsuario("11111111");
        // Alice hizo 2 transferencias
        assertEquals(2, historialAlice.size());
    }

}