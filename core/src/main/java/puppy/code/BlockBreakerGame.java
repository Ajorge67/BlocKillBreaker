package puppy.code;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
	private BitmapFont font;
	private ShapeRenderer shape;
	private Player jugador;
	private Enemigo boss;
	private ArrayList<Block> bloques = new ArrayList<>();
	private ArrayList<Bala> balas = new ArrayList<>();
	private ArrayList<Posicionable> entidadesMuertas = new ArrayList<>(); //aqui van a entrar tanto los obstaculos como las balas para eliminarlas.
	private ArrayList<Obstaculo> obstaculos = new ArrayList<>();
	private ArrayList<Item> items = new ArrayList<>();
	private int vidas;
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
		    shape = new ShapeRenderer();
		    jugador = new Player(Gdx.graphics.getWidth()/2-35, 30, 35, 20, 10);
		    vidas = 3;
		    puntaje = 0;
		    spawnObstaculos = 500000000;
		    boss = new Enemigo(10, Gdx.graphics.getHeight() - 100, Gdx.graphics.getWidth() - 20 ,Gdx.graphics.getHeight() -  Gdx.graphics.getHeight()/4 + 30, 100, 100, false);
		}
		
		//Metodo para cargar los metodos static de cada subclase de Item
		//Si se quiere crear un nuevo efecto, hay que agregarlo aqui.
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
		
		public void cargarSonidos() {
			CajaAudio.cargarSonido("DISPARO", Gdx.audio.newSound(Gdx.files.internal("disparo.mp3")));
			CajaAudio.cargarSonido("PASARNIVEL", Gdx.audio.newSound(Gdx.files.internal("pasarNivel.mp3")));
			CajaAudio.cargarSonido("PERDERNIVEL", Gdx.audio.newSound(Gdx.files.internal("perderNivel.mp3")));
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
		
		public void crearObstaculo(int x, int y, int velocidad) {
			int danio = 1; //aun no se ocupa
			int alto = 30;
		 	int ancho = 30;
			Obstaculo obs = new Obstaculo(x, y, velocidad, danio, ancho, alto );
			obstaculos.add(obs);
			ultimoObstaculo = TimeUtils.nanoTime(); //esto deja constancia de cuando fue el ultimo obstaculo creado
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
			font.draw(batch, "Nivel:" + nivel, Gdx.graphics.getWidth()/2, 25);
			font.draw(batch, "Vida Jefe: " + barraVidaJefe(), 15, Gdx.graphics.getHeight() - 200);
			batch.end();
		}	
		
		public String barraVidaJefe() {
			String barra = "";
			float porcVida = ((float)boss.getVidaActual()/(float)boss.getVidaOriginal()) * 100;
			int nBarras = (int)porcVida / 2;
			for(int x = 0; x < nBarras; x++)
				barra += "I";
			return barra;
		}
		
		@Override
		public void render () {
			System.out.println(nivel);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); 		
		    shape.begin(ShapeRenderer.ShapeType.Filled);
		    jugador.mover(Gdx.graphics.getWidth());
		    jugador.draw(shape);
		    boss.draw(shape);
		    boss.setEstadoDestruido(false);
		    float delta = Math.min(Gdx.graphics.getDeltaTime(), 1/30f); // para caida de item
		    jugador.updateEfectos(delta);
		    
		    //esto es para crear los obstaculos, si se quiere que se creen más disminuir el numero.
		    if(TimeUtils.nanoTime() - ultimoObstaculo > spawnObstaculos) {
		    	if(bloques.size() == 0)
		    		if(nivel < 8)
		    			crearObstaculo(jugador.getX(),Gdx.graphics.getHeight(), 10 + nivel);
		    		else
		    			crearObstaculo(jugador.getX(),Gdx.graphics.getHeight(), 18);
		    	else
		    		crearObstaculo(MathUtils.random(0, 800 - 30), Gdx.graphics.getHeight(), 10);
		    }
		    
		    if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.W)) {
		    	CajaAudio.reproducirSonido("DISPARO");
		    	balas.add(jugador.disparar());
		    }
		    for(Bala bala : balas) {
		        bala.actualizar();
		        bala.checkCollisionSquare(boss);
		        
		        if(boss.getEstadoDestruido() && bloques.size() == 0) {
		        	boss.setVidaActual(boss.getVidaActual() - 1);
		        }
		        
		        for(Block bloque : bloques)
		            bala.checkCollisionSquare(bloque);
		        
		        if(bloques.size() != 0) {
		        	for(Obstaculo obs : obstaculos)
		        		bala.checkCollisionSquare(obs);
		        }
		        
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
		        if(obs.getEstadoDestruido() || obs.getY() < 0)
		            entidadesMuertas.add(obs);
		        obs.draw(shape);
		    }
		   
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
		    
		    		  
		    //Ciclo para eliminar todas las entidades que tras colisionar deben desaparecer.
		    for(Posicionable entidad : entidadesMuertas) {
		        balas.remove(entidad);
		        obstaculos.remove(entidad);
		        items.remove(entidad);
		    }
		    
		    // verificar game over
		    if (vidas<=0) {
		    	CajaAudio.reproducirSonido("PERDERNIVEL");
		        vidas = 3;
		        nivel = 1;
		        crearBloques(2+nivel);
		        spawnObstaculos = 500000000;
		        puntaje = 0;
		    }
		    // verificar si el nivel se terminó
		    if (boss.getVidaActual() <= 0 && bloques.size() == 0) {
		    	CajaAudio.reproducirSonido("PASARNIVEL");
		    	puntaje += 500;
		    	nivel++;
		        if(nivel < 6)crearBloques(2+nivel);
		        else crearBloques(8);
		        
		        spawnObstaculos -= 50000000;
		        boss.setVidaOriginal(100 + (nivel - 1) * 20); //La vida del boss aumenta 20 en cada nivel
		        boss.setVidaActual(100 + (nivel - 1) * 20);
		    }    	
		    //dibujar bloques
		    for (Block b : bloques) {        	
		        b.draw(shape);
		    }
		    // actualizar estado de los bloques 
		    for (int i = 0; i < bloques.size(); i++) {
		        Block b = bloques.get(i);
		        if (b.getEstadoDestruido()) {
		            puntaje++;
		            if(generadorNum.nextInt(10) == 0)items.add(FabricaItems.generarItem(b.getX(), b.getY(), 45, 45, 10));
		            //crearItem((int)b.getX(), (int)b.getY()); // cuando se rompe el bloque, crea item
		            bloques.remove(b);
		            i--; //para no saltarse 1 tras eliminar del arraylist
		        }
		    }
		    
		    shape.end();
		    
		    dibujaTextos();
		    
		}
		
		
		@Override
		public void dispose () {
			musicaFondo.dispose();
		}
		//aaaaaaaaaaaaaaaaa
	}
