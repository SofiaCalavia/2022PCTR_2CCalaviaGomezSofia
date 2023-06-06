package p04.c1;

public class ActividadEnemiga implements Runnable{
	private int tipoEnemigo;
	public IJuego juego;
	public ActividadEnemiga(int tipoEnemigo,IJuego juego) {
		this.tipoEnemigo = tipoEnemigo;
		this.juego = juego;

	}
	
	
	public void run() {
		
		try {
			//Un enemigo simulará su actividad durmiendo un tiempo aleatorio entre 1 y 5 segundos
			juego.generarEnemigo(tipoEnemigo);
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}