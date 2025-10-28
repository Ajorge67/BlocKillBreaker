package puppy.code;

import com.badlogic.gdx.graphics.Texture;

public class AumentarVelocidad extends Item {
	
	public AumentarVelocidad(int x, int y, Texture texturaCaible) {
		super(x, y, texturaCaible);
	}

	@Override
    public void aplicarEfecto(Player jugador) {
        jugador.masRapido();
	}
}
