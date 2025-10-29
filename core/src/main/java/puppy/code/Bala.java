package puppy.code;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Bala implements ColisionableCuadrado {
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
	    public int getY() {return y;}
	    
	    public boolean getColisiono() {
	    	return colisiono;
	    }
	    
	    public void setColisiono(boolean colisiono) {
	    	this.colisiono = colisiono;
	    }
	    
	    public void draw(ShapeRenderer shape){
	        shape.setColor(color);
	        shape.circle(x, y, size);
	    }
	    
	    public void actualizar() {
	    	y += velocidad;
	    }
	    
	    /**public void update() {
	    	if (estaQuieto) return;
	        x += xSpeed;
	        y += ySpeed;
	        if (x-size < 0 || x+size > Gdx.graphics.getWidth()) {
	            xSpeed = -xSpeed;
	        }
	        if (y+size > Gdx.graphics.getHeight()) {
	            ySpeed = -ySpeed;
	        }
	    }
	    
	    public void checkCollision(Player paddle) {
	        if(collidesWith(paddle)){
	            color = Color.GREEN;
	            ySpeed = -ySpeed;
	        }
	        else{
	            color = Color.WHITE;
	        }
	    }
	    private boolean collidesWith(Player pp) {

	    	boolean intersectaX = (pp.getX() + pp.getWidth() >= x-size) && (pp.getX() <= x+size);
	        boolean intersectaY = (pp.getY() + pp.getHeight() >= y-size) && (pp.getY() <= y+size);		
	    	return intersectaX && intersectaY;
	    }**/
	    
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
