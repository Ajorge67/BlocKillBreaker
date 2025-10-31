package puppy.code;

/*	INTERFAZ Destructible
 * Esta interfaz esta pensada para ser implementada por cualquier entidad
 * que necesite un estado modificable, pensado principalmente para algo
 * que se destruye.*/

public interface Destructible {
	/*Firma de metodo para obtener el estado.*/
	public boolean getEstadoDestruido();
	
	/*Firma de metodo para establecer un true o false al estado.*/
	public void setEstadoDestruido(boolean estado);
}
