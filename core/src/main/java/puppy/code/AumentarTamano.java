package puppy.code;
import com.badlogic.gdx.graphics.Texture; 

public class AumentarTamano extends Item {
	
	public AumentarTamano(int x, int y, Texture texturaCaible) {
		super(x, y, texturaCaible);
	}

	@Override
    public void aplicarEfecto(Player jugador) {
        jugador.hacerGrande();
	}
}

