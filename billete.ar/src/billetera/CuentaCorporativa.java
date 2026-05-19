package billetera;

public class CuentaCorporativa extends Cuenta {

    private Empresa empresa;

    public CuentaCorporativa(String cvu, String alias,
                             Usuario titular, Empresa empresa) {

        super(cvu, alias, titular);
        this.empresa = empresa;
    }
}