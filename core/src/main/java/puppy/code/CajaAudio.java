package puppy.code;

import java.util.HashMap;
import com.badlogic.gdx.audio.Sound;

/*	CLASE CajaAudio
 * Esta clase funciona como registro de sonidos de distintas acciones
 * en el juego. Cuenta con metodos y constantes static para poder utilizar 
 * la clase sin necesidad de instanciarla.*/

public class CajaAudio {
	private static final HashMap<String, Sound> CAJASONIDO = new HashMap<>();
	//HashMap con los sonidos del juego.
	
	/*Metodo cargarSonido: Permite cargar un sonido al hashMap de sonidos, asociandolos
	 con una clave String.*/
	public static void cargarSonido(String clave, Sound nuevo) {
		CAJASONIDO.put(clave, nuevo);
	}
	
	/*Metodo reproducirSonido: Encargado de reproducir un sonido especificado
	  por su clave String. Recibe tambien un float que permite ajustar el volumen
	  con el que se reproducira el sonido.*/
	public static void reproducirSonido(String clave, float volumen) {
		if(CAJASONIDO.containsKey(clave))
			CAJASONIDO.get(clave).play(volumen);
	}
}