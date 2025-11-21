package puppy.code;

public class ItemDisparoPerforante extends Item {
	/*Bloque static para registrar este subtipo de item al cargar la clase.*/
	static {
		FabricaItems.getInstance().registrarItem(6, new ItemDisparoPerforante(0,0,0,0,0));
	}
	
	public ItemDisparoPerforante(int x, int y,  int width, int height, int velocidad) {
		super(x, y, width, height, velocidad);
	}
	
	public Item crearItem(int x, int y, int width, int height, int velocidad) {
		return new ItemDisparoPerforante(x,y,width,height,velocidad);
	}

	@Override
    public void aplicarEfecto(Player jugador) {
        jugador.setEstrategiaDisparo(new DisparoPerforante());;
	}
}
