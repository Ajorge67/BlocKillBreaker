package puppy.code;

/*	CLASE AumentarVelocidad
 * 
 * Esta clase es uno de los items efecto del juego, el cual disminuye
 * la velocidad de movimiento del jugador. Extiende de Item. 
 * Tiene su bloque static para registrar el item en la FabricaItems.*/

public class DisminuirVelocidad extends Item {
	
	/*Bloque static para registrar este subtipo de item al cargar la clase.*/
	static {
		FabricaItems.registrarItem(3, new DisminuirVelocidad(0,0,0,0,0));
	}
	
	public DisminuirVelocidad(int x, int y, int width, int height, int velocidad) {
		super(x, y, width, height, velocidad);
	}
	
	public Item crearItem(int x, int y, int width, int height, int velocidad) {
		return new DisminuirVelocidad(x,y,width,height,velocidad);
	}

	@Override
    public void aplicarEfecto(Player jugador) {
        jugador.masLento();
	}
}
