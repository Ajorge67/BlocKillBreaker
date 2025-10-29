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
import com.badlogic.gdx.graphics.Texture;
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
	//private ArrayList<Item> itemsEliminados = new ArrayList<>(); 
	//private ArrayList<String> tiposDeItemsPosibles = new ArrayList<>();
	//private Texture texturaItem;
	private int vidas;
	private int puntaje;
	private int nivel;
	private long ultimoObstaculo;
	private long spawnObstaculos;
	private Sound sonidoDisparo;
	private Random generadorNum = new Random();
    
		@Override
		public void create () {	
			cargarItems();
			camera = new OrthographicCamera();
		    camera.setToOrtho(false, 800, 480);
		    musicaFondo = (Music) Gdx.audio.newMusic(Gdx.files.internal("backgroundMusic.mp3"));
		    musicaFondo.setLooping(true); // Para que se repita sin parar
		    musicaFondo.setVolume(0.2f);
		    musicaFondo.play();
		    sonidoDisparo = Gdx.audio.newSound(Gdx.files.internal("disparo.mp3"));
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
		    //texturaItem = new Texture("texturaEfecto.png");
		    //tiposDeItemsPosibles.add("AumentarTamano");
		    //tiposDeItemsPosibles.add("AumentarVelocidad");
		    //tiposDeItemsPosibles.add("DisminuirTamano");
		    //tiposDeItemsPosibles.add("DisminuirVelocidad");
		    //tiposDeItemsPosibles.add("Escudo");
		    boss = new Enemigo(10, Gdx.graphics.getHeight() - 100, Gdx.graphics.getWidth() - 20 ,Gdx.graphics.getHeight() -  Gdx.graphics.getHeight()/4 + 30, 100, 100, false);
		}
		
		//Metodo para cargar los metodos static de cada subclase de Item
		//Si se quiere crear un nuevo efecto, hay que agregarlo aqui.
		public static void cargarItems() {
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
		
		/**public void crearItem(int x, int y) {
		   
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
	            nuevoItem = new AumentarTamano(x, y, 45, 45,8); 
	            //System.out.println("A");
	            break;
	            
	        case "AumentarVelocidad":
	            nuevoItem = new AumentarVelocidad(x, y, 45, 45 ,8);
	            //System.out.println("B");
	            break;
	            
	        case "DisminuirTamano":
	            nuevoItem = new DisminuirTamano(x, y, 45, 45 ,8);
	            //System.out.println("C");
	            break;
	            
	        case "DisminuirVelocidad":
	            nuevoItem = new DisminuirVelocidad(x, y, 45, 45 ,8);
	            //System.out.println("D");
	            break;
	            
	        case "Escudo":
	            nuevoItem = new Escudo(x, y, 45, 45 ,8);
	            //System.out.println("E");
	            break;   
	    }
		    
		    if (nuevoItem != null) 
		        items.add(nuevoItem);
		    
		}**/
		
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
		    
		    if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.W)) {
		    	sonidoDisparo.play();
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
		        for(Obstaculo obs : obstaculos)
		            bala.checkCollisionSquare(obs);
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
		        vidas = 3;
		        nivel = 1;
		        crearBloques(2+nivel);
		        spawnObstaculos = 500000000;
		        puntaje = 0;
		    }
		    // verificar si el nivel se terminó
		    if (boss.getVidaActual() <= 0 && bloques.size() == 0) {
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
