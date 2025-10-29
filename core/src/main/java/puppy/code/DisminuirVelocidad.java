package puppy.code;

public class DisminuirVelocidad extends Item {
	
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
