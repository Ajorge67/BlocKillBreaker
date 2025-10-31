package puppy.code;

/*	INTERFAZ ColisionableCuadrado
 * Esta interfaz esta pensada para toda entidad que necesite colisionar
 * con un cuadrado.*/

public interface ColisionableCuadrado {
	
	/*Firma de metodo que busca implementar logica de colision con un 
	  cuadrado. Retorna un true si colisionan, o un false si no.*/
	public boolean collidesWithSquare(PosicionableCuadrado elemento);
}
