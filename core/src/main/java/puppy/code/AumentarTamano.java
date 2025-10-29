package puppy.code; 

public class AumentarTamano extends Item {
	
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

