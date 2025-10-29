package puppy.code;
import com.badlogic.gdx.graphics.Texture; 
public class Escudo extends Item {
	
	public Escudo(int x, int y,  int width, int height, int velocidad) {
		super(x, y, width, height, velocidad);
	}

	@Override
    public void aplicarEfecto(Player jugador) {
        jugador.ganarEscudo();
	}
}
