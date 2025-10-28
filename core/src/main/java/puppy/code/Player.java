package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Player {
    private int x = 20;
    private int y = 20;
    private int width = 100;
    private int height = 10;
    private int velocidad;
    private boolean escudo = false;
    
    public Player(int x, int y, int ancho, int alto, int velocidad) {
    	this.x = x;
    	this.y= y;
    	width = ancho;
    	height = alto;
    	this.velocidad = velocidad;
    }
     
    public int getX() {return x;}
	public int getY() {return y;}
	public int getWidth() {return width;}
	public int getHeight() {return height;}

	public void draw(ShapeRenderer shape){
        shape.setColor(Color.BLUE);
        int x2 = x; //= Gdx.input.getX();
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) x2 =x - velocidad;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) x2=x + velocidad; 
       // y = Gdx.graphics.getHeight() - Gdx.input.getY(); 
        if (x2 > 0 && x2+width < Gdx.graphics.getWidth()) {
            x = x2;
        }
        shape.rect(x, y, width, height);
    }
    
    public Bala disparar() {
    	return new Bala(x,y,5,10);
    }
    
    public void ganarEscudo() {
    	//por implementar
    }
    
    public void hacerGrande() {
    	// por implementar
    }
    
    public void hacerPequeno() {
    	// por implementar
    }
    
    public void masRapido() {
    	// por implementar
    }
    
    public void masLento() {
    	// por implementar
    }
}
