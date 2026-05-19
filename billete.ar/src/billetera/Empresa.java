package billetera;

import java.util.HashSet;
import java.util.Set;

public class Empresa {

    //datos
    private String cuit;
    private String nombreFantasia;
    private String telefono;
    private String email;
    private String nombreContacto;
    private Set<String> dniAutorizados;

    //constructor
    public Empresa(String cuit,
                   String nombreFantasia,
                   String telefono,
                   String email,
                   String nombreContacto) {

        this.cuit = cuit;
        this.nombreFantasia = nombreFantasia;
        this.telefono = telefono;
        this.email = email;
        this.nombreContacto = nombreContacto;

        this.dniAutorizados = new HashSet<>();
    }
}