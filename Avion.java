package practicaAvionBien;

// Clase que actua como constructor de un objeto Avion
public class Avion {
	private String[][] asientos;

	public Avion() {
		this.asientos = new String[4][4];
	}

	public String[][] getAsientos() {
		return asientos;
	}

	public void setAsientos(String[][] asientos) {
		this.asientos = asientos;
	}
	
	/**
	 * Pre: --- 
	 * Post: Método que rellena el avion.
	 */
	public void rellenar() {
		for (int i = 0; i < this.asientos.length; i++) {
			for (int j = 0; j < this.asientos[i].length; j++) {
				this.asientos[i][j] = "L";
			}
		}
	}
	
}
