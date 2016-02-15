package hello;

public class Alumno {
	 	
		private final int legajo;
		private String nombre;
		private String apellido;
		private String carrera;
		private int cantMat;
		
		public Alumno(int legajo) {
			this.legajo = legajo;
		}

		public Alumno(int legajo, String nombre, String apellido, String carrera, int cantMat) {
			this.legajo = legajo;
			this.nombre = nombre;
			this.apellido = apellido;
			this.carrera = carrera;
			this.cantMat = cantMat;
		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public String getApellido() {
			return apellido;
		}

		public void setApellido(String apellido) {
			this.apellido = apellido;
		}

		public String getCarrera() {
			return carrera;
		}

		public void setCarrera(String carrera) {
			this.carrera = carrera;
		}

		public int getCantMat() {
			return cantMat;
		}

		public void setCantMat(int cantMat) {
			this.cantMat = cantMat;
		}

		public int getLegajo() {
			return legajo;
		}
	   
}
