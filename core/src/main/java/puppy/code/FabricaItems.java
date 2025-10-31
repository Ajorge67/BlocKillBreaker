package puppy.code;

import java.util.HashMap;
import java.util.Random;

/*	Clase FabricaItems
 * Esta clase esta diseñada para funcionar como generador de los
 * distintos items presentes en el juego. La idea del diseño es
 * respetar el principio Open/Close. Tiene un HashMap de items
 * que funcionan como generadores al clonarse con su respectivo 
 * metodo .crearItem()*/

public class FabricaItems {
	private static final HashMap<Integer,Item> GENERADORES = new HashMap<>();
	
	/*Metodo registrarItem: Permite agregar un item al HashMap de generadores.*/
	public static void registrarItem(int id, Item itemGenerador) {
		GENERADORES.put(id, itemGenerador);
	}
	
	/*Metodo generarItem: Permite generar un item al azar de los presentes en el hashMap.*/
	public static Item generarItem(int x, int y, int width, int height, int velocidad) {
		Random generadorNum = new Random();
		Item seleccionado;
		int numGenerado = generadorNum.nextInt(GENERADORES.size());
		seleccionado = GENERADORES.get(numGenerado);
		return seleccionado.crearItem(x, y, width, height, velocidad);
	}
	
}
