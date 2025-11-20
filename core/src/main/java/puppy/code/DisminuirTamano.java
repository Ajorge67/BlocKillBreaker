package puppy.code;

/*	CLASE DisminuirTamano
 * 
 * Esta clase es uno de los items efecto del juego, el cual disminuye
 * el tamaño del jugador. Extiende de Item. Tiene su bloque static
 * para registrar el item en la FabricaItems.*/

public class DisminuirTamano extends Item {
	
	/*Bloque static para registrar este subtipo de item al cargar la clase.*/
	static {
		FabricaItems.getInstance().registrarItem(2, new DisminuirTamano(0,0,0,0,0));
	}
	
	public DisminuirTamano(int x, int y, int width, int height, int velocidad) {
		super(x, y, width, height, velocidad);
	}
	
	public Item crearItem(int x, int y, int width, int height, int velocidad) {
		return new DisminuirTamano(x,y,width,height,velocidad);
	}

	@Override
    public void aplicarEfecto(Player jugador) {
        jugador.hacerPequeno();
	}
}
