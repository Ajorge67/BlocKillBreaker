package puppy.code;

public class AumentarVelocidad extends Item {
	
	static {
		FabricaItems.registrarItem(1, new AumentarVelocidad(0,0,0,0,0));
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
