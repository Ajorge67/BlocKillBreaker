package puppy.code;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/*	CLASE Enemigo
 * Esta clase representa al enemigo que esta presente al final
 * de cada nivel del juego. Implementa la interfaz CuadradoDestructible,
 * pues permite establecer la animacion al dibujar al jefe de que esta
 * recibiendo los ataques.*/

public class Enemigo implements CuadradoDestructible {
	private int x;
	private int y;
	private int ancho;
	private int alto;
	private int vidaOriginal;
	private int vidaActual;
	private boolean destruido;
	
	public Enemigo(int x, int y, int ancho, int alto, int vidaOriginal, int vidaActual, boolean estado) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.vidaOriginal = vidaOriginal;
		this.vidaActual = vidaActual;
		destruido = estado;
	}
	
	public void setX(int x) {this.x = x;}
	public void setY(int y) {this.y = y;}
    public int getX() {return x;}
	public int getY() {return y;}
	public int getWidth() {return ancho;}
	public int getHeight() {return alto;}
	public void setWidth(int width) {this.ancho = width;}
	public void setHeight(int height) {this.alto = height;}
	public boolean getEstadoDestruido() {return destruido;}
	public void setEstadoDestruido(boolean estado) {destruido = estado;}
	public void setVidaOriginal(int vidaOriginal) {this.vidaOriginal = vidaOriginal;}
	public int getVidaOriginal() {return vidaOriginal;}
	public void setVidaActual(int vidaActual) {this.vidaActual = vidaActual;}
	public int getVidaActual() {return vidaActual;}
	
	public void draw(ShapeRenderer shape){
		if(destruido == true)
			shape.setColor(Color.WHITE);
		else
			shape.setColor(Color.BLUE);
        shape.rect(x, y, ancho, alto);
    }

}
