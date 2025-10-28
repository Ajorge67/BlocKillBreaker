package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public abstract class Item {
    protected int x;
    protected int y;
    
    protected Texture textura;
    protected Rectangle boundingBox; 
    protected float velocidadCaida = 100f;

    
    public Item(int x, int y, Texture textura) {
        this.x = x;
        this.y = y;
        this.textura = textura;
        this.boundingBox = new Rectangle(x, y, textura.getWidth(), textura.getHeight());
    }

   
    public void caida(float delta) {
        this.y -= velocidadCaida * delta; // se mueve en -y
        this.boundingBox.setPosition(this.x, this.y);
    }

  
    public abstract void aplicarEfecto(Player jugador);

 
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Texture getTextura() {
        return textura;
    }

    public Rectangle getBoundingBox() {
        return boundingBox;
    }
}