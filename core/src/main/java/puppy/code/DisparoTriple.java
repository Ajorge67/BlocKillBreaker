package puppy.code;

public class DisparoTriple implements EstrategiaDisparo{
	
	public Bala[] disparar(int x, int y){
		Bala[] disparo;
		disparo = new Bala[] {
				new Bala(x - 30,y,5,-2,10),
				new Bala(x,y,5,0,10),
				new Bala(x + 30,y,5,2,10)
				};
				
		return disparo;
	}
}
