package puppy.code;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.Random;
import com.badlogic.gdx.graphics.Color;

/*	CLASE Block
 * Esta clase representa los bloques que deben destruirse en el juego.
 * Implementan la interfaz de CuadradoDestructible debido a que son
 * entidades posicionables con x e y que pueden ser rotas por algun
 * evento(El disparo del jugador en el contexto del juego).*/

public class Block implements CuadradoDestructible{
    private int x , y , width , height;
    private Color cc;
    private boolean destruido;
    
    public Block(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        destruido = false;
        Random r = new Random(x+y);
        
       cc = new Color(0.1f+r.nextFloat(), r.nextFloat(), r.nextFloat(), 10);
  
    }
    
    public int getWidth() {return width;}
	public void setWidth(int width) {this.width = width;}
	public int getHeight() {return height;}
	public void setHeight(int height) {this.height = height;}
	public int getX() {return x;}
	public void setX(int x) {this.x = x;}
	public int getY() {return y;}
	public void setY(int y) {this.y = y;}
	public boolean getEstadoDestruido() {return destruido;}
	public void setEstadoDestruido(boolean estado) {destruido = estado;}

	public void draw(ShapeRenderer shape){
    	shape.setColor(cc);
        shape.rect(x, y, width, height);
    }
}
