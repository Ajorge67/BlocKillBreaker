package puppy.code;

import com.badlogic.gdx.graphics.Texture;

public class DisminuirTamano extends Item {
	
	public DisminuirTamano(int x, int y, Texture texturaCaible) {
		super(x, y, texturaCaible);
	}

	@Override
    public void aplicarEfecto(Player jugador) {
        jugador.hacerPequeno();
	}
}
