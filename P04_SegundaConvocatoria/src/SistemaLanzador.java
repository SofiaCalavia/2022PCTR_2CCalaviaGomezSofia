package p04.c1;

import java.util.Hashtable;



public class SistemaLanzador {
public static void main(String[] args) {	
	//Número máximo M de enemigos al mismo tiempo.

	int M = 3;
	
	// Definir el número de enemigos de cada tipo y el número máximo de enemigos
	Hashtable<Integer, Integer> numEnemigosTipo = new Hashtable<>();
	
	// CASO DE EJEMPLO DEL ANEXO
	
	 	numEnemigosTipo.put(0, 4);
        numEnemigosTipo.put(1, 3);
        numEnemigosTipo.put(2, 2);
        numEnemigosTipo.put(3, 1);
        
        //numEnemigosTipo.put(4, 7);
        //numEnemigosTipo.put(5, 2);
        //numEnemigosTipo.put(6, 3);
        //numEnemigosTipo.put(7, 1);

	

    IJuego juego = new Juego(numEnemigosTipo, M);
    
    //Se lanzarán hilos que generan enemigos e hilos que eliminan enemigos.
    
    
    for (int tipo = 0; tipo < numEnemigosTipo.size() ; tipo++) {
    	for (int num = 0 ; num < numEnemigosTipo.get(tipo); num++){
    		Thread hiloEnemigo = new Thread(new ActividadEnemiga(tipo, juego));
            hiloEnemigo.start();
    	}
    }
    for (int tipo = 0; tipo < numEnemigosTipo.size() ; tipo++) {
    	for (int num = 0 ; num < numEnemigosTipo.get(tipo); num++){
    		Thread hiloAliado = new Thread(new ActividadAliada(tipo, juego));
            hiloAliado.start();
    	}
    }
		
	}
}
