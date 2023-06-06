package p04.c1;

public class ActividadAliada implements Runnable {
	private int tipoEnemigo;
	public IJuego juego;
	public ActividadAliada(int tipoEnemigo,IJuego juego) {
		this.tipoEnemigo = tipoEnemigo;
		this.juego = juego;
	}
	
	public void run() {
		try {
			juego.eliminarEnemigo(tipoEnemigo);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
