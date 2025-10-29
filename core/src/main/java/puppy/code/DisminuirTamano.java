package puppy.code;

public class DisminuirTamano extends Item {
	
	static {
		FabricaItems.registrarItem(2, new DisminuirTamano(0,0,0,0,0));
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
