package hello;

import java.util.LinkedList;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlumnoController {

	private LinkedList<Alumno> listaAlumnos;

	/*GET ==> 	muestra la lista completa de alumnos*/
	@RequestMapping("/alumno")
 	public LinkedList<Alumno> mostrarLista() {
		if (listaAlumnos==null) {
			System.out.println("LISTA VACIA");
			Alumno sinAlumnos=new Alumno(0);
			LinkedList<Alumno> listaVacia = new LinkedList<Alumno>();
			listaVacia.add(sinAlumnos);
			return listaVacia;
		}
		return listaAlumnos;
	}

	/*GET ==>	busca por legajo un alumno y lo muestra si esta agregado en la lista*/
	@RequestMapping("/")
 	public Alumno buscarAlumno(@RequestParam(value = "legajo") int legajo){
		int pos;
		try {
			pos=buscarLegajo(legajo);
			if (pos>=0) {
				Alumno alum=listaAlumnos.get(pos);
				return alum;
			}
			if(pos==-1){
				throw new MiExcepcion("ERROR: Alumno no encontrado");
			}
			throw new MiExcepcion("ERROR: Lista Vacia");

		} catch (MiExcepcion e) {
			System.out.println(e.getMessage());
			return new Alumno(0);
		}

	}

	private int buscarLegajo(int legajo) {
		boolean existe = false;
		int i=0;
		if(listaAlumnos!=null){
			for (Alumno alumno : listaAlumnos) {
				if(alumno.getLegajo()==legajo){
					existe = true;
					return i; //posicion del alumno
				}else i++;
			}
			if(!existe) return -1; //no se encontro el alumno
		}
		return -2; //lista vacia
	}

	/*POST ==>	agrega un alumno a la lista*/
	@RequestMapping(value = "/agregar", method = RequestMethod.POST)
	public String agregarAlumno(
			@RequestParam(value = "legajo") String legajo, 
			@RequestParam(value = "nombre") String nombre, 
			@RequestParam(value = "apellido") String apellido,
			@RequestParam(value = "carrera") String carrera, 
			@RequestParam(value = "materias") String materias) throws MiExcepcion {
		try {
			int numLeg = Integer.parseInt(legajo);
			if (buscarLegajo(numLeg) >= 0) {
				return "El Alumno ya esta en la lista";
			} else {
				if ((nombre.equals("")) || (apellido.equals("")) || carrera.equals("") || materias.equals("")) {
					throw new MiExcepcion("ERROR: Faltan uno o mas datos del alumno");
				}
				
				if (!(carrera.equals("ISI") || carrera.equals("IEM") || carrera.equals("IQ") || carrera.equals("TSP") || carrera.equals("LAR"))) {
					throw new MiExcepcion("ERROR: La Carrera ingresada no corresponde a ninguna de las existentes");
				}
				
				int mat = Integer.parseInt(materias);
				if (mat<=0 || mat>35){
					throw new MiExcepcion("ERROR: Cantidad de materias no valida");
				}
				
				Alumno al = new Alumno(numLeg, nombre, apellido, carrera, mat);
				agregar(al);
				return "Alumno Agregado";
			}
		} catch (MiExcepcion e) {
			return e.getMessage();
		} catch (NumberFormatException e) {
			return "ERROR: Ingreso letras para el Legajo o cantidad de materias Materias";
		}
	}

	private void agregar(Alumno alumno) {
		if (listaAlumnos == null) {
			listaAlumnos = new LinkedList<Alumno>();
		}
		listaAlumnos.add(alumno);
	}

	/*DELETE ==>	eliminar alumno con el legajo pasado */
	@RequestMapping(value = "/eliminar", method = RequestMethod.DELETE)
	public String borrarAlumno(
		@RequestParam(value = "legajo") int legajo) {
		try {
			int pos = buscarLegajo(legajo);
			if (pos >= 0) {
				listaAlumnos.remove(pos);// elimina al alumno de la lista
				return "Alumno Eliminado";
			}
			if (pos == -1) {
				throw new MiExcepcion("Error no se encotro el alumno");
			}
			throw new MiExcepcion("Error lista vacia");

		} catch (MiExcepcion e) {
			return e.getMessage();
		} catch (NumberFormatException e) {
			return "ERROR: Ingreso letras en el Legajo";
		}

	}

	/*PUT ==> 	Modificar algun dato del alumno con el legajo pasado como parametro*/
	@RequestMapping(value = "/{legajo}", method = RequestMethod.PUT)
	public Alumno modificarAlumno(
			@PathVariable int legajo , 
			@RequestParam(value = "nombre", required = false) String nombre, 
			@RequestParam(value = "apellido", required = false) String apellido,
			@RequestParam(value = "carrera", required = false) String carrera, 
			@RequestParam(value = "materias", required = false) String materias) {
		try {
			int pos = buscarLegajo(legajo);
			if (pos >= 0) {
				if (nombre == null && apellido == null && carrera == null && materias==null) {
					System.out.println("ERROR: TODOS LOS PARAMETROS SON NULOS");
					return new Alumno(0);
				}
				if (nombre != null) {
					listaAlumnos.get(pos).setNombre(nombre);
				}
				if (apellido != null) {
					listaAlumnos.get(pos).setApellido(apellido);
				}
				if (carrera != null) {
					listaAlumnos.get(pos).setCarrera(carrera);
				}
				if (materias !=null) {
					int mat = Integer.parseInt(materias);
					listaAlumnos.get(pos).setCantMat(mat);
				}

				return listaAlumnos.get(pos);
			}

			if (pos == -1) {
				throw new MiExcepcion("Error no se encotro el alumno");
			}
			throw new MiExcepcion("Error lista vacia");

		} catch (NumberFormatException e) {
			System.out.println("ERROR: Ingreso letras en el Legajo");
			return new Alumno(0);
		} catch (MiExcepcion e) {
			System.out.println(e.getMessage());
			return new Alumno(0);
		}

	}
		
}
