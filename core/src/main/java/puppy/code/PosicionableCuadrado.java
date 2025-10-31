package puppy.code;

/*	INTERFAZ PosicionableCuadrado
 * Esta interfaz esta pensada para cualquier entidad que sea un
 * Cuadrado, y que es posicionable en un plano de dos dimensiones.*/

public interface PosicionableCuadrado extends Posicionable {
	/*Cuenta con firmas de metodos de getter y setters de Width y Height*/
	public int getWidth();
	public int getHeight();
	public void setWidth(int width);
	public void setHeight(int height);

}
