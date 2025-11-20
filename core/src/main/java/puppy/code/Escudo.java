package puppy.code;

/*	CLASE Escudo
 * 
 * Esta clase es uno de los items efecto del juego, el cual entrega un
 * escudo que evita un hit de daño al jugador. Extiende de Item. 
 * Tiene su bloque static para registrar el item en la FabricaItems.*/

public class Escudo extends Item {
	
	/*Bloque static para registrar este subtipo de item al cargar la clase.*/
	static {
		FabricaItems.getInstance().registrarItem(4, new Escudo(0,0,0,0,0));
	}
	
	public Escudo(int x, int y,  int width, int height, int velocidad) {
		super(x, y, width, height, velocidad);
	}
	
	public Item crearItem(int x, int y, int width, int height, int velocidad) {
		return new Escudo(x,y,width,height,velocidad);
	}

	@Override
    public void aplicarEfecto(Player jugador) {
        jugador.ganarEscudo();
	}
}
