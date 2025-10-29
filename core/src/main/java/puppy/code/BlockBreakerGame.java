package puppy.code;

import java.util.ArrayList;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;


public class BlockBreakerGame extends ApplicationAdapter {
    private OrthographicCamera camera;
	private SpriteBatch batch;	   
	private BitmapFont font;
	private ShapeRenderer shape;
	private Player jugador;
	private Enemigo boss;
	private ArrayList<Block> bloques = new ArrayList<>();
	private ArrayList<Bala> balas = new ArrayList<>();
	private ArrayList<Colisionable> entidadesMuertas = new ArrayList<>(); //aqui van a entrar tanto los obstaculos como las balas para eliminarlas.
	private ArrayList<Obstaculo> obstaculos = new ArrayList<>();
	private ArrayList<Item> items = new ArrayList<>();
	private ArrayList<Item> itemsEliminados = new ArrayList<>(); 
	private ArrayList<String> tiposDeItemsPosibles = new ArrayList<>();
	private Texture texturaItem;
	private int vidas;
	private int puntaje;
	private int nivel;
	private long ultimoObstaculo;
	private long spawnObstaculos;
    
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
		    jugador = new Player(Gdx.graphics.getWidth()/2-35, 30, 35, 20, 10);
		    vidas = 3;
		    puntaje = 0;
		    spawnObstaculos = 500000000;
		    texturaItem = new Texture("placeholder.png");
		    tiposDeItemsPosibles.add("AumentarTamano");
		    tiposDeItemsPosibles.add("AumentarVelocidad");
		    tiposDeItemsPosibles.add("DisminuirTamano");
		    tiposDeItemsPosibles.add("DisminuirVelocidad");
		    tiposDeItemsPosibles.add("Escudo");
		    boss = new Enemigo(10, Gdx.graphics.getHeight() - 100, Gdx.graphics.getWidth() - 20 ,Gdx.graphics.getHeight() -  Gdx.graphics.getHeight()/4 + 30, 100, false);
		}
		
		public void crearBloques(int filas) {
			bloques.clear();
			int blockWidth = 70;
		    int blockHeight = 26;
		    int y = Gdx.graphics.getHeight() -  Gdx.graphics.getHeight()/4;
		    for (int cont = 0; cont<filas; cont++ ) {
		    	y -= blockHeight+10;
		    	for (int x = 5; x < Gdx.graphics.getWidth(); x += blockWidth + 10) {
		            bloques.add(new Block(x, y, blockWidth, blockHeight));
		        }
		    }
		}
		
		public void crearObstaculo() {
			int pos_x = MathUtils.random(0, 800 - 5);
			int pos_y = Gdx.graphics.getHeight();
			int velocidad = 10;
			int danio = 1; //aun no se ocupa
			int alto = 30;
		 	int ancho = 30;
			Obstaculo obs = new Obstaculo(pos_x, pos_y, velocidad, danio, ancho, alto );
			obstaculos.add(obs);
			ultimoObstaculo = TimeUtils.nanoTime(); //esto deja constancia de cuando fue el ultimo obstaculo creado
		}
		
		public void crearItem(int x, int y) {
		   
		    float random = MathUtils.random(); // Guardamos el número aleatorio
		    int indiceAleatorio = MathUtils.random(tiposDeItemsPosibles.size() - 1);
		    String tipoElegido = tiposDeItemsPosibles.get(indiceAleatorio);
		    Item nuevoItem = null;

		    if (random >= 0.10f) // si el numero es mayor a 0.10f la funcion se detiene (yo lo dejaria en 0.05 o menos)
		        return; 
		    
		    if (tiposDeItemsPosibles.isEmpty()) // si el array esta vacio se detiene
		        return;
		    
		    switch (tipoElegido) {
	        case "AumentarTamano":
	            nuevoItem = new AumentarTamano(x, y, texturaItem); 
	            //System.out.println("A");
	            break;
	            
	        case "AumentarVelocidad":
	            nuevoItem = new AumentarVelocidad(x, y, texturaItem);
	            //System.out.println("B");
	            break;
	            
	        case "DisminuirTamano":
	            nuevoItem = new DisminuirTamano(x, y, texturaItem);
	            //System.out.println("C");
	            break;
	            
	        case "DisminuirVelocidad":
	            nuevoItem = new DisminuirVelocidad(x, y, texturaItem);
	            //System.out.println("D");
	            break;
	            
	        case "Escudo":
	            nuevoItem = new Escudo(x, y, texturaItem);
	            //System.out.println("E");
	            break;   
	    }
		    
		    if (nuevoItem != null) 
		        items.add(nuevoItem);
		    
		}
		
		public void dibujaTextos() {
			//actualizar matrices de la cámara
			camera.update();
			//actualizar 
			batch.setProjectionMatrix(camera.combined);
			batch.begin();
			for (Item item : items) { //dibuja items
		        batch.draw(item.getTextura(), item.getX(), item.getY());
		    }
			//dibujar textos
			font.draw(batch, "Puntos: " + puntaje, 10, 25);
			font.draw(batch, "Vidas : " + vidas, Gdx.graphics.getWidth()-20, 25);
			font.draw(batch, "Nivel:" + nivel, Gdx.graphics.getWidth()/2, 25);
			batch.end();
		}	
		
		@Override
		public void render () {
		    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); 		
		    shape.begin(ShapeRenderer.ShapeType.Filled);
		    jugador.mover(Gdx.graphics.getWidth());
		    jugador.draw(shape);
		    boss.draw(shape);
		    boss.setEstadoDestruido(false);
		    float delta = Math.min(Gdx.graphics.getDeltaTime(), 1/30f); // para caida de item
		    jugador.updateEfectos(delta);
		    
		    //esto es para crear los obstaculos, si se quiere que se creen más disminuir el numero.
		    if(TimeUtils.nanoTime() - ultimoObstaculo > spawnObstaculos)
		        crearObstaculo();
		    
		    if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE))balas.add(jugador.disparar());
		    for(Bala bala : balas) {
		        bala.actualizar();
		        bala.checkCollision(boss);
		        if(boss.getEstado()) {
		        	boss.setVida(boss.getVida() - 1);
		        }
		        for(Block bloque : bloques)
		            bala.checkCollision(bloque);
		        for(Obstaculo obs : obstaculos)
		            bala.checkCollision(obs);
		        if(bala.getColisiono() || bala.getY() > Gdx.graphics.getHeight())entidadesMuertas.add(bala);
		        bala.draw(shape);
		    }
		    
		    
		    for(Obstaculo obs : obstaculos) {
		        obs.actualizar();
		        obs.checkCollision(jugador);
		        if(jugador.getEstado()) {
		            vidas -= 1;
		            jugador.setEstadoDestruido(false);
		        }
		        if(obs.getEstado())
		            entidadesMuertas.add(obs);
		        obs.draw(shape);
		    }
		   
		    for (Item item : items) {
		        item.caida(delta); // Mueve el item hacia abajo
		        if (jugador.getBounds().overlaps(item.getBoundingBox())) {
		        	item.aplicarEfecto(jugador);
		        	itemsEliminados.add(item);
		        }
		        
		        else if (item.getY() < -item.getTextura().getHeight())  // Si se salio por abajo
		            itemsEliminados.add(item);
		    }
		    
		    items.removeAll(itemsEliminados);
		    itemsEliminados.clear();
		    		  
		    //Ciclo para eliminar todas las entidades que tras colisionar deben desaparecer.
		    for(Colisionable entidad : entidadesMuertas) {
		        balas.remove(entidad);
		        obstaculos.remove(entidad);
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
		    if (vidas<=0) {
		        vidas = 3;
		        nivel = 1;
		        crearBloques(2+nivel);
		        spawnObstaculos = 500000000;
		        //ball = new Bala(pad.getX()+pad.getWidth()/2-5, pad.getY()+pad.getHeight()+11, 10, 5, 7, true);	        	
		    }
		    // verificar si el nivel se terminó
		    if (boss.getVida() <= 0 && bloques.size() == 0) {
		    	puntaje += 500;
		        if(nivel < 6)nivel++;
		        crearBloques(2+nivel);
		        spawnObstaculos -= 50000000;
		        boss.setVida(100);
		    }    	
		    //dibujar bloques
		    for (Block b : bloques) {        	
		        b.draw(shape);
		    }
		    // actualizar estado de los bloques 
		    for (int i = 0; i < bloques.size(); i++) {
		        Block b = bloques.get(i);
		        if (b.getEstado()) {
		            puntaje++;
		            crearItem((int)b.getX(), (int)b.getY()); // cuando se rompe el bloque, crea item
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
		//aaaaaaaaaaaaaaaaa
	}
