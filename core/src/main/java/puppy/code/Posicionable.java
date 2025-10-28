package puppy.code;

public interface Posicionable {
	public int getX();
	public int getY();
	public void setX(int x);
	public void setY(int y);
	
	public int getWidth();
	public int getHeight();
	public void setWidth(int width);
	public void setHeight(int height);
	
	public void setEstadoDestruido(boolean destroyed);
	public boolean getEstado();
}