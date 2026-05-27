package billetera;

public class CuentaCorporativa extends Cuenta {

    private Empresa empresa;

    public CuentaCorporativa(String cvu, String alias,
                             Usuario titular, Empresa empresa) {

        super(cvu, alias, titular);
        this.empresa = empresa;
    }
    
    @Override
    public String toString() {
    	
    	StringBuilder sb = new StringBuilder();
    	
    	sb.append("Corporativa: " )
    	  .append(getAlias())
    	  .append(", CVU: ")
    	  .append(getCvu());
        
    	return sb.toString();
    	
    	
        
    }
}