package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Player  implements PosicionableCuadrado  {
    private int x;
    private int y;
    private int width;
    private int height;
    private int velocidad;
    private boolean dañado = false;
    private boolean escudo = false;
    private int widthOriginal;
    private int velocidadOriginal;
    private float temporizadorEfecto = 0;

    
    public Player(int x, int y, int ancho, int alto, int velocidad) {
    	this.x = x;
    	this.y= y;
    	width = ancho;
    	height = alto;
    	this.velocidad = velocidad;
    	this.widthOriginal = ancho; 
    	this.velocidadOriginal = velocidad;
    }
     
    public void setX(int x) {this.x = x;}
	public void setY(int y) {this.y = y;}
    public int getX() {return x;}
	public int getY() {return y;}
	public int getWidth() {return width;}
	public int getHeight() {return height;}
	public void setWidth(int width) {this.width = width;}
	public void setHeight(int height) {this.height = height;}
	public boolean getEstado() {return dañado;}
	public int getVelocidad() { return velocidad; }
    public void setVelocidad(int v) { this.velocidad = v; }
    
	public void setEstadoDestruido(boolean destroyed) {
		if(destroyed) {
			if(this.escudo) { // si hay escudo, no hay dano y se quita escudo
				this.dañado = false;
				this.escudo = false;
			}
			else 
				this.dañado = true;
			
		}
		else 
			this.dañado = false;		
	}

    

	public void draw(ShapeRenderer shape){
		if(this.escudo){ // dibuja un escudo cuando esta activo
			shape.setColor(Color.CYAN);
			int padding = 4;
			shape.rect(x - padding, y - padding, width + (padding * 2), height + (padding * 2));
		}
        shape.setColor(Color.BLUE);
        shape.rect(x, y, width, height);
        
    }
    
	public void mover(int widthVentana) {
		int copiaX = x;
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A))copiaX = x - velocidad;
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D))copiaX = x + velocidad;
		if (copiaX > 0 && copiaX + width < widthVentana) x = copiaX;
	}
	
    public Bala disparar() {
    	return new Bala(x + width/2,y,5,10);
    }
    
    public void ganarEscudo() {
    	this.escudo = true;   	
    }
    
    public void hacerGrande() {
    	setWidth( Math.min(150, (int)(widthOriginal * 3.0f)) );
    	this.temporizadorEfecto = 5.0f;
    }
    
    public void hacerPequeno() {
    	setWidth( Math.max(20, (int)(widthOriginal * 0.2f)) );    	
    	this.temporizadorEfecto = 5.0f;
    }
    
    public void masRapido() {
    	setVelocidad( Math.min(20, (int)(velocidadOriginal * 3.0f)) );
    	this.temporizadorEfecto = 5.0f;
    }
    
    public void masLento() {
    	setVelocidad( Math.min(20, (int)(velocidadOriginal * 0.2f)) );
    	this.temporizadorEfecto = 5.0f;
    }
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
	public void updateEfectos(float delta) { //metodo para que vuelva a modo original luego de 5 seg
	        if (temporizadorEfecto > 0) {
	        	temporizadorEfecto -= delta; 
	            if (temporizadorEfecto <= 0) {
	                setWidth(widthOriginal);
	                setVelocidad(velocidadOriginal);
	            }
	        }
        
        
    }
}
