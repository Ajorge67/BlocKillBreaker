package puppy.code;

import java.util.Random;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PowerDown extends Item {
	private int tipoDowngrade;
    private static Random random = new Random();
    
    public PowerDown(float x, float y) {
        super(x,y); 
        tipoDowngrade = random.nextInt(3); 
        
        switch(tipoDowngrade) {
        case 0: 
            this.textura = new Texture("placeholder.png"); 
            break;
        case 1:
            this.textura = new Texture("placeholder_downgrade.png");
            break;
        case 2:
            this.textura = new Texture("placeholder_downgrade.png");
            break;
        }
        
        if (this.boundingBox != null && this.textura != null) {
            this.boundingBox.width = this.textura.getWidth();
            this.boundingBox.height = this.textura.getHeight();
        }
    }
    
    public void draw(SpriteBatch batch) {
        batch.draw(textura, position.x, position.y);
    }
    
    @Override
    public void aplicarEfecto(Player jugador) {
    
        switch (tipoDowngrade) {
        case 0:
            // jugador.reducirVelocidad(); 
            break;
        case 1:
            // jugador.reducirCadencia(); 
            break;
        case 2:
            // jugador.invertirControles();
            break;
        }
    }

    public void dispose() {
        if (textura != null) 
            textura.dispose();       
    }

}
