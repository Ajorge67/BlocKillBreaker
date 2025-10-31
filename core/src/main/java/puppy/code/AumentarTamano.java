package puppy.code; 

/*	CLASE AumentarTamano
 * 
 * Esta clase es uno de los items efecto del juego, el cual aumenta
 * el tamaño del jugador. Extiende de Item. Tiene su bloque static
 * para registrar el item en la FabricaItems.*/

public class AumentarTamano extends Item {
	
	/*Bloque static para registrar este subtipo de item al cargar la clase.*/
	static {
		FabricaItems.registrarItem(0, new AumentarTamano(0,0,0,0,0));
	}
	
	public AumentarTamano(int x, int y,  int width, int height, int velocidad) {
		super(x, y, width, height, velocidad);
	}
	
	public Item crearItem(int x, int y, int width, int height, int velocidad) {
		return new AumentarTamano(x,y,width,height,velocidad);
	}
    
	@Override
    public void aplicarEfecto(Player jugador) {
        jugador.hacerGrande();
	}
}

