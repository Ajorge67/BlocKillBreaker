package puppy.code;

/*	CLASE AumentarVelocidad
 * 
 * Esta clase es uno de los items efecto del juego, el cual aumenta
 * la velocidad de movimiento del jugador. Extiende de Item. 
 * Tiene su bloque static para registrar el item en la FabricaItems.*/

public class AumentarVelocidad extends Item {
	
	/*Bloque static para registrar este subtipo de item al cargar la clase.*/
	static {
		FabricaItems.getInstance().registrarItem(1, new AumentarVelocidad(0,0,0,0,0));
	}
	
	public AumentarVelocidad(int x, int y, int width, int height, int velocidad) {
		super(x, y, width, height, velocidad);
	}
	
	public Item crearItem(int x, int y, int width, int height, int velocidad) {
		return new AumentarVelocidad(x,y,width,height,velocidad);
	}

	@Override
    public void aplicarEfecto(Player jugador) {
        jugador.masRapido();
	}
}
