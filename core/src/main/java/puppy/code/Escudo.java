package puppy.code;

public class Escudo extends Item {
	
	static {
		FabricaItems.registrarItem(4, new Escudo(0,0,0,0,0));
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
