package billetera;

public class CuentaPremium extends Cuenta {

    private double montoMinimo = 500000;

    public CuentaPremium(String cvu, String alias,
                         Usuario titular, double saldoInicial) {

        super(cvu, alias, titular);
        this.saldo = saldoInicial;
    }
    
    @Override
    public String toString() {
    	
    	StringBuilder sb = new StringBuilder();
    	
    	sb.append("Premium: " )
    	  .append(getAlias())
    	  .append(", CVU: ")
    	  .append(getCvu());
        
    	return sb.toString();
    	
    }
}