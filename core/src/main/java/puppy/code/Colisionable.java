package puppy.code;

public interface Colisionable {
	
	public void checkCollision(Posicionable elemento);
	public boolean collidesWith(Posicionable elemento);
}
