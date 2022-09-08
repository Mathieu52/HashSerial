package revision1;

public class Automobile extends Vehicule<Automobile.Piece> {
    public enum Piece {Transmission, Carosserie, Moteur, Habitacle};

    public Automobile(String NIP) {
        super(NIP, Piece.class);
    }

    public Automobile() {
        super(Piece.class);
    }
}
