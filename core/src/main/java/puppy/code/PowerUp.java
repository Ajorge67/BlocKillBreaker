package puppy.code;

import java.util.Random;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PowerUp extends Item {
	private int tipoPowerUp;
	private static Random random = new Random();
	
	public PowerUp(float x, float y) {
		super(x,y);
		tipoPowerUp = random.nextInt(3);
		
		switch(tipoPowerUp) {
		case 0: //power up velocidad
			this.textura = new Texture("placeholder.png");
			break;
		case 1: //power up velocidad disparo
            this.textura = new Texture("placeholder.png");
            break;
        case 2: //power up escudo
            this.textura = new Texture("placeholder.png");
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
    	switch (tipoPowerUp) {
        case 0:
            //jugador.aumentarVelocidad(); 
            break;
        case 1:
            //jugador.aumentarCadencia(); // 
            break;
        case 2:
            //jugador.activarEscudo();
            break;
    	}
    }

    public void dispose() {
        if (textura != null) 
            textura.dispose();       
    }
}
