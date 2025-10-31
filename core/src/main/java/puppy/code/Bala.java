package puppy.code;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/*	CLASE Bala
 * Esta clase representa las balas que dispara el jugador para atacar 
 * a las distintas entidades presentes en el juego. Implementa las
 * interfaces de ColisionableCuadrado y Posicionable. La primera
 * por que puede tener colisiones con entidades de tipo PosicionableCuadrado,
 * y la segunda por que es posicionable con un x e y.*/

public class Bala implements ColisionableCuadrado, Posicionable{
	    private int x;
	    private int y;
	    private int size;
	    private int velocidad;
	    private boolean colisiono;
	    private Color color = Color.WHITE;
	    
	    public Bala(int x, int y, int size, int velocidad) {
	        this.x = x;
	        this.y = y;
	        this.size = size;
	        this.velocidad = velocidad;
	        colisiono = false;
	    }
	    
	    
	    public void setXY(int x, int y) {
	    	this.x = x;
	        this.y = y;
	    }
	    public int getX() {return x;}
	    public int getY() {return y;}
	    public void setX(int x) {this.x = x;}
	    public void setY(int y) {this.y = y;}
	    public boolean getColisiono() {return colisiono;}
	    public void setColisiono(boolean colisiono) {this.colisiono = colisiono;}
	    
	    public void draw(ShapeRenderer shape){
	        shape.setColor(color);
	        shape.circle(x, y, size);
	    }
	    
	    public void actualizar() {
	    	y += velocidad;
	    }
	    
	    /*Metodo checkCollisionSquare: Encargado de recibir un CuadradoDestructible,
	     para comprobar si hay colision con el, y en caso de haberlo, cambiarle
	     su estado de destruido.*/
	    public void checkCollisionSquare(CuadradoDestructible block) {
	        if(collidesWithSquare(block)){
	            colisiono = true;
	            block.setEstadoDestruido(true);
	        }
	    }
	    
	    public boolean collidesWithSquare(PosicionableCuadrado bb) {

	    	boolean intersectaX = (bb.getX() + bb.getWidth() >= x-size) && (bb.getX() <= x+size);
	        boolean intersectaY = (bb.getY() + bb.getHeight() >= y-size) && (bb.getY() <= y+size);		
	    	return intersectaX && intersectaY;
	    }
	    
	}
