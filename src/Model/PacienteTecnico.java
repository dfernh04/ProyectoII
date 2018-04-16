package Model;

public class PacienteTecnico {

	private String nombre;
	private String apellido;
	private String id;
	private String dni;
	
	public PacienteTecnico(String id,String nombre,String apellido,String dni){
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
	}
	
	public String getNombre(){
		return nombre;
	}
	
	public String getApellido(){
		return apellido;
	}
	
	public String getId(){
		return id;
	}
	
	public String getDni(){
		return dni;
	}
}
