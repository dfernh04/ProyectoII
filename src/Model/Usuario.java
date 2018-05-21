package Model;

/**
 * Objeto Usuario que representa las identificaciones y contrasena de 
 * una persona para acceder a la aplicacion, incluyendo a su rol para saber 
 * 
 * @author HeartLight
 * 
 * @version Final
 * 
 *
 */
public class Usuario {
		private String user;
		private String rol;
		private String con;
		private String nombre;
		private String apellido;
		private String ubicacion;
		private int dni;
		/**
		 * Constructor de un Usuario
		 * @param user Nombe de Usuario
		 * @param rol Rol (medico,admin,tecnico)
		 * @param con contrasena
		 */
		public Usuario(String user,String rol, String con){
			this.user=user;
			this.rol=rol;
			this.con=con;
		}
		
		public Usuario(String nombre, String apellido, String user, String rol, String con, int dni, String ubicacion) {
			super();
			this.nombre = nombre;
			this.apellido = apellido;
			this.user = user;
			this.rol = rol;
			this.con = con;
			this.dni = dni;
			this.ubicacion = ubicacion;
		}
		/**
		 * Getter del nombre de Usuario
		 * @return nombre de Usuario
		 */
		public String getUser() {
			return user;
		}
		/**
		 * Getter del rol
		 * @return (medico,admin,tecnico)
		 */
		public String getRol() {
			return rol;
		}
		/**
		 * Getter de la contrasena del Usuario
		 * @return Contrasena
		 */
		public String getCon() {
			return con;
		}
		
		
}
