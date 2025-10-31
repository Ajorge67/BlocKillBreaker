package puppy.code.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import puppy.code.BlocKillBreaker;

public class Lwjgl3Launcher {
	
    public static void main(String[] args) {
        if (StartupHelper.startNewJvmIfRequired()) return; // This handles macOS support and helps on Windows.
        createApplication();
    }

    private static Lwjgl3Application createApplication() {
        return new Lwjgl3Application(new BlocKillBreaker(), getDefaultConfiguration());
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("BlocKillBreaker v1.0");
        configuration.useVsync(true);
        
        //Configura la cantidad de veces que se ejecuta el codigo cada segundo
        configuration.setForegroundFPS(60);
        
        //Esto es para el tamaño de la ventana
        configuration.setWindowedMode(640, 670);
        
        //Estos son los iconos que aparecen en la ventana y en la barra de tareas
        configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");
        return configuration;
    }
}