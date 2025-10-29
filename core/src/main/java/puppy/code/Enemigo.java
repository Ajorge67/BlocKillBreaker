package puppy.code;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Enemigo implements Posicionable {
	private int x;
	private int y;
	private int ancho;
	private int alto;
	private int vida;
	private boolean estado;
	
	public Enemigo(int x, int y, int ancho, int alto, int vida, boolean estado) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.vida = vida;
		this.estado = estado;
	}
	
	public void setX(int x) {this.x = x;}
	public void setY(int y) {this.y = y;}
    public int getX() {return x;}
	public int getY() {return y;}
	public int getWidth() {return ancho;}
	public int getHeight() {return alto;}
	public void setWidth(int width) {this.ancho = width;}
	public void setHeight(int height) {this.alto = height;}
	public boolean getEstado() {return estado;}
	public void setEstadoDestruido(boolean destroyed) {estado = destroyed;}
	public void setVida(int vida) {this.vida = vida;}
	public int getVida() {return vida;}
	
	
	public void draw(ShapeRenderer shape){
		if(estado == true)
			shape.setColor(Color.WHITE);
		else
			shape.setColor(Color.BLUE);
        shape.rect(x, y, ancho, alto);
    }

}
