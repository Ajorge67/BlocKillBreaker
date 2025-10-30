package puppy.code;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;


public class BlockBreakerGame extends ApplicationAdapter {
    private OrthographicCamera camera;
    private Music musicaFondo;
	private SpriteBatch batch; 
	private BitmapFont font; //Escritura de textos
	private ShapeRenderer shape; //Para dibujar las distintas entidades
	private Player jugador; 
	private Enemigo boss; //Jefe del nivel
	private ArrayList<Block> bloques = new ArrayList<>(); //Coleccion de bloques en pantalla
	private ArrayList<Bala> balas = new ArrayList<>(); //Coleccion de balas en pantalla
	private ArrayList<Posicionable> entidadesMuertas = new ArrayList<>(); //Coleccion de entidades que deben salir de pantalla.
	private ArrayList<Obstaculo> obstaculos = new ArrayList<>(); //Coleccion de obstaculos en pantalla
	private ArrayList<Item> items = new ArrayList<>(); //Coleccion de items en pantalla
	private int puntaje;
	private int nivel;
	private long ultimoObstaculo;
	private long spawnObstaculos;
	private Random generadorNum = new Random();
		@Override
		public void create () {	
			cargarSonidos();
			cargarItems();
			camera = new OrthographicCamera();
		    
			camera.setToOrtho(false, 800, 480);
		    musicaFondo = (Music) Gdx.audio.newMusic(Gdx.files.internal("backgroundMusic.mp3"));
		    musicaFondo.setLooping(true); // Para que se repita sin parar
		    musicaFondo.setVolume(0.05f);
		    musicaFondo.play();
		    
		    batch = new SpriteBatch();
		    font = new BitmapFont();
		    font.getData().setScale(3, 2);
		    
		    nivel = 1;
		    crearBloques(2+nivel);
		    jugador = new Player(Gdx.graphics.getWidth()/2-35, 30, 35, 20, 10,3);
		    puntaje = 0;
		    spawnObstaculos = 500000000;
		    
		    shape = new ShapeRenderer();
		    boss = new Enemigo(10, Gdx.graphics.getHeight() - 100, Gdx.graphics.getWidth() - 20 ,Gdx.graphics.getHeight() -  Gdx.graphics.getHeight()/4 + 30, 100, 100, false);
		}
		
		/*Metodo cargarItems: Sirve para cargar los metodos static de cada subclase de Item
		Si se quiere crear un nuevo efecto, hay que agregarlo aqui.*/
		public void cargarItems() {
			try {
				Class.forName("puppy.code.AumentarTamano",true, ClassLoader.getSystemClassLoader());
				Class.forName("puppy.code.AumentarVelocidad",true, ClassLoader.getSystemClassLoader());
				Class.forName("puppy.code.DisminuirTamano",true, ClassLoader.getSystemClassLoader());
				Class.forName("puppy.code.DisminuirVelocidad",true, ClassLoader.getSystemClassLoader());
				Class.forName("puppy.code.Escudo",true, ClassLoader.getSystemClassLoader());
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
		/*Metodo cargarSonidos: Encargado de cargar los sonidos del juego a la clase
		  CajaAudio. Si se agrega un nuevo audio hay que registrarlo aqui.*/
		public void cargarSonidos() {
			CajaAudio.cargarSonido("DISPARO", Gdx.audio.newSound(Gdx.files.internal("disparo.mp3")));
			CajaAudio.cargarSonido("PASARNIVEL", Gdx.audio.newSound(Gdx.files.internal("pasarNivel.mp3")));
			CajaAudio.cargarSonido("PERDERNIVEL", Gdx.audio.newSound(Gdx.files.internal("perderNivel.mp3")));
			CajaAudio.cargarSonido("DANIOJEFE", Gdx.audio.newSound(Gdx.files.internal("danioBoss.mp3")));
			CajaAudio.cargarSonido("PERDERVIDA", Gdx.audio.newSound(Gdx.files.internal("perderVida.mp3")));
		}
		
		/*Metodo crearBloques: Encargado de generar los bloques del nivel.*/
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
		
		/*Metodo dibujaTextos: Encargado de dibujar los textos que estan
		  presentes en la pantalla, ademas de la barra de vida del jefe.*/
		public void dibujaTextos() {
			//actualizar matrices de la cámara
			camera.update();
			//actualizar 
			batch.setProjectionMatrix(camera.combined);
			batch.begin();
			//dibujar textos
			font.draw(batch, "Puntos: " + puntaje, 10, 25);
			font.draw(batch, "Vidas : " + jugador.getVida(), Gdx.graphics.getWidth()-20, 25);
			font.draw(batch, "Nivel:" + nivel, Gdx.graphics.getWidth()/2, 25);
			font.draw(batch, "Vida Jefe: " + barraVidaJefe(), 15, Gdx.graphics.getHeight() - 200);
			batch.end();
		}	
		
		/*Metodo barraVidaJefe: Encargado de crear la barra de vida del jefe, en
		 base a su vida actual y su vida original. Devuelve un string con caracteres
		 que representan la cantidad de vida que tiene actualmente el jefe.*/
		public String barraVidaJefe() {
			String barra = "";
			float porcVida = ((float)boss.getVidaActual()/(float)boss.getVidaOriginal()) * 100;
			int nBarras = (int)porcVida / 2;
			for(int x = 0; x < nBarras; x++)
				barra += "I";
			return barra;
		}
		
		/*Metodo para generar los obstaculos en pantalla. La forma de generarlos
		  cambia dependiendo del contexto que se esta en el nivel.*/
		public void generarObstaculos() {
			/*Condicion para generar obstaculos.*/
		    if(TimeUtils.nanoTime() - ultimoObstaculo > spawnObstaculos) {
		    	if(bloques.size() == 0) //Si no hay bloques los obstaculos se generan arriba del jugador
		    		if(nivel < 8)
		    			obstaculos.add(new Obstaculo(jugador.getX(),Gdx.graphics.getHeight(),30,30, 10 + nivel));
		    		else
		    			obstaculos.add(new Obstaculo(jugador.getX(),Gdx.graphics.getHeight(),30,30, 18));
		    	else //Si quedan bloques, se generan de manera aleatoria.
		    		obstaculos.add(new Obstaculo(MathUtils.random(0, 800 - 30), Gdx.graphics.getHeight(),30,30, 10));
		    	ultimoObstaculo = TimeUtils.nanoTime();
		    }
		    
		}
		
		/*Revision de si se presiono alguna tecla para disparar.*/
		public void escucharDisparo() {
		    if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.W)) {
		    	CajaAudio.reproducirSonido("DISPARO",1f);
		    	balas.add(jugador.disparar());
		    }
		}
		
		/*Metodo para controlar las balas en pantalla. Actualiza la posicion y
	      las dibuja, ademas de revisar las colisiones. Actua dependiendo si choco con
	      el enemigo, con un bloque o con un obstaculo.*/
		public void procesarBalas() {
		    for(Bala bala : balas) {
		        bala.actualizar();
		        bala.checkCollisionSquare(boss);
		        
		        if(boss.getEstadoDestruido() && bloques.size() == 0) {
		        	CajaAudio.reproducirSonido("DANIOJEFE",0.2f);
		        	boss.setVidaActual(boss.getVidaActual() - 1);
		        }
		        
		        //Ciclo para comprobar colision con cada bloque.
		        for(Block bloque : bloques)
		            bala.checkCollisionSquare(bloque);
		        
		        if(bloques.size() != 0) { //Los obstaculos solo son destruibles al quedar bloques en pantalla.
		        	for(Obstaculo obs : obstaculos)
		        		bala.checkCollisionSquare(obs);
		        }
		        
		        if(bala.getColisiono() || bala.getY() > Gdx.graphics.getHeight())entidadesMuertas.add(bala);
		        bala.draw(shape);
		    }
		}
		
		/*Metodo para controlar los obstaculos en pantalla. Actualiza su posicion
	      y los dibuja. Comprueba si choco con el player para
	      reducir su vida. Tambien revisa si los obstaculos fueron destruidos, para
	      agregarlo a las entidades muertas.*/
		public void procesarObstaculos() {
		    for(Obstaculo obs : obstaculos) {
		        obs.actualizar();
		        obs.checkCollision(jugador);
		        if(jugador.getEstado()) {
		        	CajaAudio.reproducirSonido("PERDERVIDA", 0.3f);
		            jugador.setVida(jugador.getVida() - 1);
		            jugador.setEstadoDestruido(false);
		        }
		        if(obs.getEstadoDestruido() || obs.getY() < 0)
		            entidadesMuertas.add(obs);
		        obs.draw(shape);
		    }
		}
		
		/*Metodo para controlar los items en pantalla. Se actualiza su posicion
	      y se dibujan, ademas de comprobar si hay colisiones con el jugador.*/
		public void procesarItems() {
		    for (Item item : items) {
		        item.actualizar(); // Mueve el item hacia abajo
		        item.draw(shape);
		        if (item.collidesWithSquare(jugador)) {
		        	item.aplicarEfecto(jugador);
		        	entidadesMuertas.add(item);
		        }
		        
		        else if (item.getY() < 0)  // Si se salio por abajo
		            entidadesMuertas.add(item);
		        	
		    };
		    
		}
		
		/*Metodo para eliminar todas las entidades que deben desaparecer
	      de la pantalla.*/
		public void quitarEntidadesMuertas() {
		    for(Posicionable entidad : entidadesMuertas) {
		        balas.remove(entidad);
		        obstaculos.remove(entidad);
		        items.remove(entidad);
		    }
		    
		}
		
		/*Comprobamos si hay gameOver, que es cuando el jugador tiene 
	      la vida en 0. Se reinician las variables de funcionamiento
	      del juego.*/
		public void gestionarGameOver() {
		    if (jugador.getVida()<=0) {
		    	CajaAudio.reproducirSonido("PERDERNIVEL",0.3f);
		        jugador.setVida(3);
		        nivel = 1;
		        crearBloques(2+nivel);
		        spawnObstaculos = 500000000;
		        puntaje = 0;
		    }
		    
		}
		
		/*Comprobamos si el nivel termino, que es cuando no quedan bloques
	      y el boss no tiene vida.*/
		public void gestionarLevelUp() {
		    if (bloques.size() == 0 && boss.getVidaActual() <= 0) {
		    	CajaAudio.reproducirSonido("PASARNIVEL",0.3f);
		    	puntaje += 500;
		    	nivel++;
		        if(nivel < 6) //Solo hasta el nivel 6 se generan mas hileras de bloques, despues de mantiene la cantidad.
		        	crearBloques(2+nivel);
		        else 
		        	crearBloques(8);
		        
		        spawnObstaculos -= 50000000;
		        boss.setVidaOriginal(100 + (nivel - 1) * 20); //La vida del boss aumenta 20 en cada nivel.
		        boss.setVidaActual(100 + (nivel - 1) * 20);
		    }
		    
		}
		
		/*Metodo para revisar si los bloques fueron destruidos o no, aplicando
	     la accion que corresponda segun eso.*/
		public void procesarBloques() {
		    for (int i = 0; i < bloques.size(); i++) {
		        Block b = bloques.get(i);
		        b.draw(shape);
		        if (b.getEstadoDestruido()) {
		            puntaje++;
		            if(generadorNum.nextInt(10) == 0)//Al romper un bloque hay probabilidad 1 de 11 de generar un item.
		            	items.add(FabricaItems.generarItem(b.getX(), b.getY(), 45, 45, 10));
		            
		            bloques.remove(b);
		            i--; //Para no saltarse 1 tras eliminar del arraylist
		        }
		    }
		}
		
		@Override
		public void render () {
			
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); 		
		    shape.begin(ShapeRenderer.ShapeType.Filled);
		    jugador.mover(Gdx.graphics.getWidth()); //Llamada a metodo que verifica si se mueve al jugador o no.
		    jugador.draw(shape); //Se dibuja al jugador.
		    boss.draw(shape);
		    boss.setEstadoDestruido(false);
		    float delta = Math.min(Gdx.graphics.getDeltaTime(), 1/30f); // para caida de item
		    jugador.updateEfectos(delta);
		    
		    generarObstaculos();
		    escucharDisparo();
		    procesarBalas();
		    procesarObstaculos();
		    procesarItems();
		    quitarEntidadesMuertas();
		    gestionarGameOver();
		    gestionarLevelUp();
		    procesarBloques();
		    
		    shape.end();
		    dibujaTextos();
		    
		}
		
		
		@Override
		public void dispose () {
			musicaFondo.dispose();
		}
	}
