package puppy.code;

import java.util.HashMap;

import java.util.Random;

public class FabricaItems {
	private static final HashMap<Integer,Item> GENERADORES = new HashMap<>();
	
	public static void registrarItem(int id, Item itemGenerador) {
		GENERADORES.put(id, itemGenerador);
	}
	
	public static Item generarItem(int x, int y, int width, int height, int velocidad) {
		Random generadorNum = new Random();
		Item seleccionado;
		int numGenerado = generadorNum.nextInt(GENERADORES.size());
		seleccionado = GENERADORES.get(numGenerado);
		return seleccionado.crearItem(x, y, width, height, velocidad);
	}
	
}
