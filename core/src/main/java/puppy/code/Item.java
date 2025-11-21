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
    private boolean listoParaEliminar = false;
    
    //Constantes para la generacion de items.
    public static final int WIDTHFIJO= 45, HEIGHTFIJO = 45, VELFIJA = 8;

    
    public Item(int x, int y, int width, int height, int velocidad) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.velocidad = velocidad;
    }
    
    /*método plantilla necesario para aplicar el template method. En este caso 
     * define el flujo de acciones que realiza un objeto.
     * actualizar->dibujar->verificar la colision->aplicar efecto
     * */
    public final void comportamientoItem(Player jugador, ShapeRenderer shape) {
    	this.actualizar(); //paso 1: actualizar la ubicacion
    	this.draw(shape);  //paso 2: dibujar el item
    	if(this.collidesWithSquare(jugador)) { //paso 3: verificar colisión
    		this.reproducirSonido();// paso opcional: reproducir un sonido
        	this.aplicarEfecto(jugador); //paso opcional: aplicar el efecto del item
        	this.marcaParaEliminar();  //paso opcional: indicar para que se elimine
        }
    }
    
    protected void reproducirSonido() {
    	//aqui debería ir pa que suene algo, pero no tenemos nada lol
        CajaAudio.reproducirSonido("POWERUP",0.2f); 
    }

    protected void marcaParaEliminar() {
    	//metodo para rellenar el template noma
        this.listoParaEliminar = true; 
    }
    
    protected void actualizar() {
        y -= velocidad; // se mueve en -y
    }
    
    protected void draw(ShapeRenderer shape) {
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
    public boolean listoParaEliminar() {return listoParaEliminar;}
}