package Model;

public class Mensaje {
		
		private int id;
		private int dniPaciente;
		private int fecha;
		private String data;
		private String asunto;
		private String username_med;
		private String username_tec;

		
		public Mensaje(int id,String username_med,String username_tec, int dniPaciente, String data,int fecha,String asunto) {
			super();
			this.id=id;
			this.username_med = username_med;
			this.username_tec = username_tec;
			this.dniPaciente = dniPaciente;
			this.fecha=fecha;
			this.data = data;
			this.asunto=asunto;
		}
		
		public String getUsername_med() {
			return username_med;
		}

		public void setUsername_med(String username_med) {
			this.username_med = username_med;
		}

		public String getUsername_tec() {
			return username_tec;
		}

		public void setUsername_tec(String username_tec) {
			this.username_tec = username_tec;
		}

		public int getDniPaciente() {
			return dniPaciente;
		}
		public void setDniPaciente(int dniPaciente) {
			this.dniPaciente = dniPaciente;
		}
		public int getFecha() {
			return fecha;
		}
		public void setFecha(int fecha) {
			this.fecha = fecha;
		}
		public String getData() {
			return data;
		}
		public void setData(String data) {
			this.data = data;
		}
		public String toString() {
			return asunto +" "+ String.valueOf(fecha).substring(6, 8)+"-"+String.valueOf(fecha).substring(4, 6)+"-"+String.valueOf(fecha).substring(0, 4);
			
		}

		public int getId() {
			return id;
		}

		public String getAsunto() {
			return asunto;
		}

}
