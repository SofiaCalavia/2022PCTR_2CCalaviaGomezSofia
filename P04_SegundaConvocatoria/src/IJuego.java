package p04.c1;

public interface IJuego {
	public abstract void generarEnemigo(int tipo) throws InterruptedException;
	public abstract void eliminarEnemigo(int tipo) throws InterruptedException;
}
