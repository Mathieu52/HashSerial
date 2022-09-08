package revision1;

public class Camion extends Vehicule<Camion.Piece> {
    public enum Piece {Transmission, Carosserie, Moteur, EspaceCargaison};

    public Camion(String NIP) {
        super(NIP, Piece.class);
    }

    public Camion() {
        super(Piece.class);
    }
}