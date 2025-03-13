package fp.trenes.test;

import java.time.LocalTime;

import fp.trenes.TipoTren;
import fp.trenes.TrayectoTren;
import fp.trenes.TrayectoTrenImpl2;

public class TestTrayectoTren {

	public static void main(String[] args) {
		//Utilidad de las interfaces, metafora de tuerca y tornillo
		TrayectoTren t = new TrayectoTrenImpl2( "02471", "Sevilla-Madrid", TipoTren.AV_CITY, "Sevill-Santa Justa", "Madrid-Puerta de Atocha",
				LocalTime.of(7, 0),
				LocalTime.of(10, 2)); 
		t.anadirEstacionIntermedia(1, "Cordoba", LocalTime.of(7, 45), LocalTime.of(7, 50));
		System.out.println(t);
		
	}

}
