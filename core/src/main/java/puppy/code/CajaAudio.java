package puppy.code;

import java.util.HashMap;
import com.badlogic.gdx.audio.Sound;

public class CajaAudio {
	private static final HashMap<String, Sound> CAJASONIDO = new HashMap<>();
	
	public static void cargarSonido(String clave, Sound nuevo) {
		CAJASONIDO.put(clave, nuevo);
	}
	
	public static void reproducirSonido(String clave, float volumen) {
		if(CAJASONIDO.containsKey(clave))
			CAJASONIDO.get(clave).play(volumen);
	}
}
