package puppy.code;

/*	INTERFAZ Posicionable
 * Esta interfaz esta pensada para toda entidad que pueda posicionarse
 * en algun plano de dos dimensiones.*/

public interface Posicionable {
	/*Cuenta con firmas de metodos getter y setter de posiciones X e Y*/
	public int getX();
	public int getY();
	public void setX(int x);
	public void setY(int y);

}