package p04.c1;

import java.util.Hashtable;
import java.util.Random;

public class Juego implements IJuego{
	private Hashtable<Integer, Integer> contadoresEnemigosTipo;
    private Hashtable<Integer, Integer> contadoresEliminadosTipo;
    private int EnemigosTotales;
    private int MAXENEMIGOS;
    private int MINENEMIGOS = 0;
    private int EnemigosSupuestos;
    
    public Juego (Hashtable<Integer, Integer> numEnemigosTipo, int maxEnemigos) {
    	this.MAXENEMIGOS = maxEnemigos;
    	
    	//Se deberá llevar la cuenta en cada momento de los enemigos actuales de cada tipo
    	this.contadoresEnemigosTipo = new Hashtable<>(numEnemigosTipo);
    	
    	//Se deberá llevar la cuenta en cada momento de los enemigos totales.
    	this.EnemigosTotales = 0;
    	
    	//Se deberá llevar la cuenta en cada momento de los enemigos eliminados de cada tipo.
    	this.contadoresEliminadosTipo = new Hashtable<>();
    	
    	for (int tipo : numEnemigosTipo.keySet()) {
            this.contadoresEnemigosTipo.put(tipo, 0);
            this.contadoresEliminadosTipo.put(tipo, 0);
        }
    	
    }
	@Override
	public synchronized void generarEnemigo(int tipo) throws InterruptedException {
		// TODO Auto-generated method stub
		comprobarAntesDeGenerar(tipo);
		
		Random rand = new Random();
		int randomNumber = rand.nextInt(5) + 1;
		randomNumber = randomNumber * 1000;
		try {
			Thread.sleep(randomNumber);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		contadoresEnemigosTipo.put(tipo, contadoresEnemigosTipo.get(tipo) + 1);
		EnemigosSupuestos = EnemigosTotales + 1;
        EnemigosTotales++;
		imprimirInfo(tipo,"Generado");
		checkInvariante();
		notifyAll();
		
	}
	@Override
	public synchronized void eliminarEnemigo(int tipo) throws InterruptedException {
		// TODO Auto-generated method stub
		comprobarAntesDeEliminar(tipo);
		EnemigosSupuestos = EnemigosTotales - 1;
		contadoresEnemigosTipo.put(tipo, contadoresEnemigosTipo.get(tipo) - 1);
		contadoresEliminadosTipo.put(tipo, contadoresEliminadosTipo.get(tipo) + 1);
		EnemigosTotales--;
		imprimirInfo(tipo,"Eliminado");
		checkInvariante();
		notifyAll();
		
	}
	
	//Cada vez que se genere o elimine un enemigo, se mostrará por pantalla la información

	public void imprimirInfo(int tipo,String dato) {
		System.out.printf("\n%s enemigo tipo %d \n",dato,tipo);
        System.out.println("--> Enemigos Totales: " + EnemigosTotales);
        for (int i = contadoresEnemigosTipo.size() - 1;i >= 0;i--) {
        System.out.println("---> Enemigos tipo " + i + ":" + contadoresEnemigosTipo.get(i) + "------------ [Eliminados: " + contadoresEliminadosTipo.get(i) + "]");
        }
	 }
	
	//Los contadores de enemigos vivos deben sumar lo mismo que el contador total de enemigos.
	protected synchronized void checkInvariante() {
		while (EnemigosTotales != EnemigosSupuestos) {
	        try {
	            wait();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	// PRECONDICIONES Y POSTOCONDICIONES DE GENERAR NO MAS DE M ENEMIGOS NI Un enemigo de un tipo identificado con un número entero mayor no podrá generarse hasta
	//que se haya generado al menos un enemigo de un tipo con un entero inmediatamente
	//inferior
	protected synchronized void comprobarAntesDeGenerar(int tipo) throws InterruptedException {
	    while(EnemigosTotales >= MAXENEMIGOS || tipo > 0 && contadoresEnemigosTipo.get(tipo - 1) == 0 && contadoresEliminadosTipo.get(tipo - 1) == 0) {
	        wait();
	    }
	}
	
	//Nunca podrá haber menos de 0 enemigos
	protected synchronized void comprobarAntesDeEliminar(int tipo) throws InterruptedException{
		while (EnemigosTotales <= MINENEMIGOS || contadoresEnemigosTipo.get(tipo) == 0) {
	        wait();
	    }
	}
}
