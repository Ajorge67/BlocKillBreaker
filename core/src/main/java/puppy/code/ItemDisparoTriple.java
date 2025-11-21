package puppy.code;

public class ItemDisparoTriple extends Item{
	/*Bloque static para registrar este subtipo de item al cargar la clase.*/
	static {
		FabricaItems.getInstance().registrarItem(5, new ItemDisparoTriple(0,0,0,0,0));
	}
	
	public ItemDisparoTriple(int x, int y,  int width, int height, int velocidad) {
		super(x, y, width, height, velocidad);
	}
	
	public Item crearItem(int x, int y, int width, int height, int velocidad) {
		return new ItemDisparoTriple(x,y,width,height,velocidad);
	}

	@Override
    public void aplicarEfecto(Player jugador) {
        jugador.setEstrategiaDisparo(new DisparoTriple());;
	}
}
