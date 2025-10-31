package puppy.code;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/*	CLASE ABSTRACTA Item
 * Clase base para el desarrollo de items en el juego. Representa
 * de manera abstracta el esqueleto de un item. Implementa la interfaz
 * ColisionableCuadrado para agregar la logica al chocar con el player, y
 * Posicionable para ubicarlo en un lugar del plano.*/

public abstract class Item implements ColisionableCuadrado, Posicionable{
	private int x;
    private int y;
    private int width;
    private int height;
    private int velocidad;
    
    //Constantes para la generacion de items.
    public static final int WIDTHFIJO= 45, HEIGHTFIJO = 45, VELFIJA = 8;

    
    public Item(int x, int y, int width, int height, int velocidad) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.velocidad = velocidad;
    }

   
    public void actualizar() {
        y -= velocidad; // se mueve en -y
    }
    
    public void draw(ShapeRenderer shape) {
    	shape.setColor(Color.CYAN);
        shape.rect(x, y, width, height);
    
    }
    
    public boolean collidesWithSquare(PosicionableCuadrado jugador) {
    	boolean intersectaX = (jugador.getX() + jugador.getWidth() > x) && (jugador.getX() < x + width);
		boolean intersectaY = (jugador.getY() + jugador.getHeight() > y) && (jugador.getY() < y + height);
		return intersectaX && intersectaY;
    }
    
    public abstract void aplicarEfecto(Player jugador);
    public abstract Item crearItem(int x, int y, int width, int height, int velocidad);
    public int getX() {return x;}
    public int getY() {return y;}
    public void setX(int x) {this.x = x;}
    public void setY(int y) {this.y = y;}
    public int getWidth() {return width;}
    public int getHeight() {return height;}
    public void setWidth(int width) {this.width = width;}
    public void setHeight(int height) {this.height = height;}
}