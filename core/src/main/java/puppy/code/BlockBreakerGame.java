package puppy.code;

import java.util.ArrayList;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


public class BlockBreakerGame extends ApplicationAdapter {
    private OrthographicCamera camera;
	private SpriteBatch batch;	   
	private BitmapFont font;
	private ShapeRenderer shape;
	private Player jugador;
	private ArrayList<Block> bloques = new ArrayList<>();
	private ArrayList<Bala> balas = new ArrayList<>();
	private ArrayList<Bala> balasMuertas = new ArrayList<>();
	private int vidas;
	private int puntaje;
	private int nivel;
    
		@Override
		public void create () {	
			camera = new OrthographicCamera();
		    camera.setToOrtho(false, 800, 480);
		    batch = new SpriteBatch();
		    font = new BitmapFont();
		    font.getData().setScale(3, 2);
		    nivel = 1;
		    crearBloques(2+nivel);
			
		    shape = new ShapeRenderer();
		    jugador = new Player(Gdx.graphics.getWidth()/2-35,40,35,10,10);
		    vidas = 3;
		    puntaje = 0;    
		}
		public void crearBloques(int filas) {
			bloques.clear();
			int blockWidth = 70;
		    int blockHeight = 26;
		    int y = Gdx.graphics.getHeight();
		    for (int cont = 0; cont<filas; cont++ ) {
		    	y -= blockHeight+10;
		    	for (int x = 5; x < Gdx.graphics.getWidth(); x += blockWidth + 10) {
		            bloques.add(new Block(x, y, blockWidth, blockHeight));
		        }
		    }
		}
		public void dibujaTextos() {
			//actualizar matrices de la cámara
			camera.update();
			//actualizar 
			batch.setProjectionMatrix(camera.combined);
			batch.begin();
			//dibujar textos
			font.draw(batch, "Puntos: " + puntaje, 10, 25);
			font.draw(batch, "Vidas : " + vidas, Gdx.graphics.getWidth()-20, 25);
			batch.end();
		}	
		
		@Override
		public void render () {
			System.out.println(balas.size());
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); 		
	        shape.begin(ShapeRenderer.ShapeType.Filled);
	        jugador.draw(shape);
	        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE))balas.add(jugador.disparar());
	        for(Bala bala : balas) {
	        	bala.actualizar();
	        	for(Block bloque : bloques)
	        		bala.checkCollision(bloque);
	        	if(bala.getColisiono() || bala.getY() > Gdx.graphics.getHeight())balasMuertas.add(bala);
	        	bala.draw(shape);
	        }
	        
	        for(Bala bala : balasMuertas) {
	        	balas.remove(bala);
	        }
	        // monitorear inicio del juego
	        /**if (ball.estaQuieto()) {
	        	ball.setXY(pad.getX()+pad.getWidth()/2-5, pad.getY()+pad.getHeight()+11);
	        	if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) ball.setEstaQuieto(false);
	        }else ball.update();
	        //verificar si se fue la bola x abajo
	        if (ball.getY()<0) {
	        	vidas--;
	        	//nivel = 1;
	        	ball = new Bala(pad.getX()+pad.getWidth()/2-5, pad.getY()+pad.getHeight()+11, 10, 5, 7, true);
	        }**/
	        // verificar game over
	        /**if (vidas<=0) {
	        	vidas = 3;
	        	nivel = 1;
	        	crearBloques(2+nivel);
	        	//ball = new Bala(pad.getX()+pad.getWidth()/2-5, pad.getY()+pad.getHeight()+11, 10, 5, 7, true);	        	
	        }**/
	        // verificar si el nivel se terminó
	        if (bloques.size()==0) {
	        	nivel++;
	        	crearBloques(2+nivel);
	        }    	
	        //dibujar bloques
	        for (Block b : bloques) {        	
	            b.draw(shape);
	        }
	        // actualizar estado de los bloques 
	        for (int i = 0; i < bloques.size(); i++) {
	            Block b = bloques.get(i);
	            if (b.destroyed) {
	            	puntaje++;
	                bloques.remove(b);
	                i--; //para no saltarse 1 tras eliminar del arraylist
	            }
	        }
	        
	        //ball.checkCollision(pad);
	        
	        shape.end();
	        dibujaTextos();
		}
		
		@Override
		public void dispose () {

		}
	}
