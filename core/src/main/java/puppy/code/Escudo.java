package puppy.code;
import com.badlogic.gdx.graphics.Texture; 
public class Escudo extends Item {
	
	public Escudo(int x, int y, Texture texturaCaible) {
		super(x, y, texturaCaible);
	}

	@Override
    public void aplicarEfecto(Player jugador) {
        jugador.ganarEscudo();
	}
}
