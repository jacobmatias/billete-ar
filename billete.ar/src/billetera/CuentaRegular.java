package billetera;

public class CuentaRegular extends Cuenta {

    private double saldoMaximo = 5000000;

    public CuentaRegular(String cvu, String alias, Usuario titular) {
        super(cvu, alias, titular);
    }
    
    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	
    	sb.append("Regular: ")
    	  .append(getAlias())
    	  .append(", CVU: ")
    	  .append(getCvu());
        
    	return sb.toString();
    }
}