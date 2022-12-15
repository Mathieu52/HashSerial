package revision1;

import java.io.Serializable;

public class Moto extends Vehicule<Moto.Piece> {
    public enum Piece {Transmission, Moteur}

    public Moto(String NIP) {
        super(NIP, Piece.class);
    }

    public Moto() {
        super(Piece.class);
    }
}
