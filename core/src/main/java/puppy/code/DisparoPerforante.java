package puppy.code;

public class DisparoPerforante implements EstrategiaDisparo{
	
	public Bala[] disparar(int x, int y){
		Bala[] disparo;
		disparo = new Bala[] {
				new Bala(x,y+15,8,0,10),
				new Bala(x,y+12,8,0,10),
				new Bala(x,y+9,8,0,10),
				new Bala(x,y+6,8,0,10),
				new Bala(x,y+3,8,0,10),
				new Bala(x,y,8,0,10),
				};
				
		return disparo;
	}
	
}
