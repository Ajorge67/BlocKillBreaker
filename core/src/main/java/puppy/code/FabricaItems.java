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
	/*Se crea el atributo estatico instance, almacena la unica instancia permitida de FabricaItems para cumplir con el patron singleton.*/
	private static FabricaItems instance;
	private HashMap<Integer,Item> GENERADORES;
	
	/*Se crea un constructor PRIVADO, se encarga unicamente de inicializar el hashmap y al ser privado evita que se creen instancias fuera de esta clase. */
	private FabricaItems() {
		GENERADORES = new HashMap<>();
	}
	
	/*Se crea un metodo getInstance que sirve como punto de acceso global a la instancia, si la instancia no existe, la crea, caso contrario devuelve la existente.*/
	public static FabricaItems getInstance() {
		if(instance == null) {
			instance = new FabricaItems();
		}
		return instance;
	}
	
	/*Metodo registrarItem: Permite agregar un item al HashMap de generadores.*/
	public void registrarItem(int id, Item itemGenerador) {
		GENERADORES.put(id, itemGenerador);
	}
	
	/*Metodo generarItem: Permite generar un item al azar de los presentes en el hashMap.*/
	public Item generarItem(int x, int y, int width, int height, int velocidad) {
		Random generadorNum = new Random();
		Item seleccionado;
		
		if(GENERADORES.isEmpty())
			return null;
		
		int numGenerado = generadorNum.nextInt(GENERADORES.size());
		seleccionado = GENERADORES.get(numGenerado);
		if(seleccionado != null)
			return seleccionado.crearItem(x, y, width, height, velocidad);
		
		return null;
	}
	
}
