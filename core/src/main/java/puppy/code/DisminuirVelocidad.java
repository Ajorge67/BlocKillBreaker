package puppy.code;

import com.badlogic.gdx.graphics.Texture;

public class DisminuirVelocidad extends Item {
	
	public DisminuirVelocidad(int x, int y, Texture texturaCaible) {
		super(x, y, texturaCaible);
	}

	@Override
    public void aplicarEfecto(Player jugador) {
        jugador.masLento();
	}
}
