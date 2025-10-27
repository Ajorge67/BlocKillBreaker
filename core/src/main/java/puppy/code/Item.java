package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;

public abstract class Item {
	protected Vector2 position;
    protected Texture textura;
    protected Rectangle boundingBox; 
    protected float velocidadCaida = 100f;
	
    public Item(float x, float y) {
        this.position = new Vector2(x, y);
        
    }

    public void update(float delta) {
        position.y -= velocidadCaida * delta;
        boundingBox.setPosition(position);
    }

    
    public abstract void aplicarEfecto(Player jugador);
    
}


