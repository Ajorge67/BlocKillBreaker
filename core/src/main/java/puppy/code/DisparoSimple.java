package puppy.code;

public class DisparoSimple implements EstrategiaDisparo{
	
	public Bala[] disparar(int x, int y){
		Bala[] disparo;
		disparo = new Bala[] {
				new Bala(x,y,5,0,10)
				};
				
		return disparo;
	}
}
