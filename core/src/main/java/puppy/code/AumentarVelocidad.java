package puppy.code;

import com.badlogic.gdx.graphics.Texture;

public class AumentarVelocidad extends Item {
	
	public AumentarVelocidad(int x, int y, int width, int height, int velocidad) {
		super(x, y, width, height, velocidad);
	}

	@Override
    public void aplicarEfecto(Player jugador) {
        jugador.masRapido();
	}
}
