package fp.trenes;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TrayectoTrenImpl2 implements TrayectoTren {
	private String codigoTren; 
	private String nombre; 
	private TipoTren tipoTren; 
	private List<Parada> paradas;
	
	public TrayectoTrenImpl2(String codigoTren, String nombre, TipoTren tipoTren, String origen, String destino, 
			LocalTime horaSalida, LocalTime horaLlegada) { 
		checkCodigo(codigoTren); 
		checkNotNull(horaSalida); 
		checkNotNull(horaLlegada); 
		checkOrdenHoras(horaSalida, horaLlegada); 
		
		this.codigoTren = codigoTren; 
		this.nombre = nombre; 
		this.tipoTren = tipoTren; 
		this.paradas = new ArrayList<Parada>(); 
		paradas.add(new Parada(origen, null, horaSalida)); 
		paradas.add(new Parada(destino, horaLlegada, null));
	}
	
	private void checkNotNull(LocalTime parametro) { 
		if(parametro == null) { 
			throw new IllegalArgumentException(); 
		}
	}
	
	private void checkOrdenHoras(LocalTime horaSalida, LocalTime horaLlegada) { 
		if(!horaSalida.isBefore(horaLlegada)) { 
			throw new IllegalArgumentException();
		}
	}
	
	private void checkCodigo(String codigoTren) { 
		if(!(codigoTren.length() == 5 && codigoTren.chars().allMatch(Character::isDigit))) {
			throw new IllegalArgumentException(); 
		}
	}
	
	@Override
	public String getCodigoTren() {
		return codigoTren;
	}

	@Override
	public String getNombre() {
		return nombre;
	}

	@Override
	public TipoTren getTipoTren() {
		return tipoTren;
	}

	@Override
	public List<String> getEstaciones() {
		List<String> res = new ArrayList<>(); 
		for(Parada p:paradas) {
			res.add(p.estacion());
		}
		return res;
	}

	@Override
	public List<LocalTime> getHorasLLegada() {
		List<LocalTime> res = new ArrayList<>(); 
		for(Parada p:paradas) {
			res.add(p.horaLlegada());
		}
		return res;
	}

	@Override
	public List<LocalTime> getHorasSalida() {
		List<LocalTime> res = new ArrayList<>(); 
		for(Parada p:paradas) {
			res.add(p.horaSalida());
		}
		return res;
	}

	@Override
	public LocalTime getHoraLlegada() {
		return paradas.get(0).horaSalida();
	}

	@Override 
	public LocalTime getHoraSalida() {
		return paradas.get(paradas.size()-1).horaLlegada();
			
	}
	
	
	@Override
	public LocalTime getHoraSalida(String estacion) {
		LocalTime res = null;
		List<String> estaciones = getEstaciones(); 
		Integer index = estaciones.indexOf(estacion);
		if(!(index == 1 || index == 0 || index == estaciones.size() -1)) { 
			res = this.getHorasSalida().get(index) ; 
		} 
		return res; 
	}

	@Override 
	public LocalTime getHoraLlegada(String estacion) {
		LocalTime res = null;
		List<String> estaciones = getEstaciones(); 
		Integer index = estaciones.indexOf(estacion);
		if(!(index == 1 || index == 0 || index == estaciones.size() -1)) { 
			res = this.getHorasLLegada().get(index) ; 
		} 
		return res; 
	
	}
	

	@Override
	public Duration getDuracionTrayecto() {
		return Duration.between(getHoraSalida(), getHoraLlegada());
	}
	
	@Override
	public void anadirEstacionIntermedia(int posicion, String estacion, LocalTime horaLlegada, LocalTime horaSalida) {
		//TODO: Comprobaciones
		Parada p = new Parada(estacion, horaLlegada, horaSalida); 
		paradas.add(posicion,p);
	}

	@Override
	public void eliminarEstacionIntermedia(String estacion) {
		// TODO Comprobaciones
		List<String> estaciones = getEstaciones(); 
		int posicion = estaciones.indexOf(estacion); 
		if(posicion != 1 && posicion != 0 && posicion != estaciones.size() -1) { 
			paradas.remove(posicion);
		}
	}

	@Override
	public int compareTo(TrayectoTren t) {
		int cmp = nombre.compareTo(t.getNombre()); 
		if(cmp == 0) { 
			cmp = getHoraSalida().compareTo(t.getHoraSalida()); 
		}
		if(cmp == 0) { 
			cmp = codigoTren.compareTo(t.getCodigoTren()); 
		}
		return cmp;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigoTren, nombre, getHoraSalida());
	}

	@Override
	public boolean equals(Object obj) {
		boolean res = false; 
		if(obj instanceof TrayectoTren) { 
			TrayectoTren other = (TrayectoTren) obj;
			res = Objects.equals(codigoTren, other.getCodigoTren()) && Objects.equals(nombre, other.getNombre()) 
					&& Objects.equals(getHoraSalida(), other.getHoraSalida());
		}
		return res;
	}
	
	public String toString() { 
		String res = nombre + "-" + tipoTren + " (" + codigoTren + ")\n"; 
		for(Parada p:paradas) { 
			LocalTime horaLlegada = p.horaLlegada();
			LocalTime horaSalida = p.horaSalida();
			res += p.estacion() + "\t" + (horaLlegada == null?"":horaLlegada) + "\t" + (horaSalida == null?"": horaSalida) + "\n"; 
		}
		return res;
	}
	
	
}
