package puppy.code;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Obstaculo implements ColisionableCuadrado, CuadradoDestructible{
	private int x;
	private int y;
	private int velocidad;
	private int daño;
	private int width;
    private int height;
    private boolean destruido;
	private Color color = Color.RED;
	
	public Obstaculo(int x, int y, int velocidad, int daño, int alto, int ancho) {
		this.x = x;
		this.y = y;
		this.velocidad = velocidad;
		this.daño = daño;
		width = ancho;
	    height = alto;
	    destruido = false;
	}
	
	public int getX() {return x;}
	public void setX(int x) {this.x = x;}
	public int getY() {return y;}
	public void setY(int y) {this.y = y;}
	public int getWidth() {return width;}
	public int getHeight() {return height;}
	public void setWidth(int width) {this.width = width;}
	public void setHeight(int height) {this.height = height;}
	public boolean getEstadoDestruido() {return destruido;}
	public void setEstadoDestruido(boolean estado) {destruido = estado;}
	
	public void draw(ShapeRenderer shape){
        shape.setColor(color);
        shape.rect(x, y, width, height);
    }
	
	public void actualizar() {
		y -= velocidad;
	}
	
	public void checkCollision(Player jugador) {
		if(collidesWithSquare(jugador)) {
			destruido = true;
			jugador.setEstadoDestruido(true);
		}
	}
	
	public boolean collidesWithSquare(PosicionableCuadrado jugador) {
		boolean intersectaX = (jugador.getX() + jugador.getWidth() > x) && (jugador.getX() < x + width);
		boolean intersectaY = (jugador.getY() + jugador.getHeight() > y) && (jugador.getY() < y + height);
		return intersectaX && intersectaY;
	}

}
