package puppy.code;

import com.badlogic.gdx.graphics.Texture;

public class DisminuirTamano extends Item {
	
	public DisminuirTamano(int x, int y, int width, int height, int velocidad) {
		super(x, y, width, height, velocidad);
	}

	@Override
    public void aplicarEfecto(Player jugador) {
        jugador.hacerPequeno();
	}
}
