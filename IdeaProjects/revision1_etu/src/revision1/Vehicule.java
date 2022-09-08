package revision1;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;



public abstract class Vehicule <PieceEnum extends Enum<PieceEnum>> {
    public enum Etat {TRES_BRISE, BRISE, REPARE, NEUF}
    private static Etat ETAT_DEFAULT = Etat.NEUF;

    private String NIP;

    private HashMap<PieceEnum, Etat> etatPieces = new HashMap<>();

    public Vehicule(String NIP, Class<PieceEnum> Piece) {
        this(Piece);
        this.NIP = NIP;
    }

    public Vehicule(Class<PieceEnum> Piece) {
        for (PieceEnum piece : Piece.getEnumConstants())
            etatPieces.put(piece, ETAT_DEFAULT);
    }

    public Etat getEtat(PieceEnum piece) {
        return etatPieces.get(piece);
    }

    public void setEtat(PieceEnum piece, Etat etat) {
        etatPieces.replace(piece, etat);
    }

    @Override
    public String toString() {
        String  str = this.getClass().getSimpleName() + "{" + "NIP='" + NIP + '\'';
        for (PieceEnum piece : etatPieces.keySet())
            str += ", etat" + piece + "=" + getEtat(piece);
        return str + '}';
    }

    public String getNIP() {
        return NIP;
    }

    /**
     * répare tous les dommages du vehicule en indiquant l'état réparé.
     * Défi: essayez de gérer le cout des réparations.
     */
    public String repare() {
        String info = ' ' + this.getNIP() + '-';
        for (PieceEnum piece : etatPieces.keySet())
            if (getEtat(piece) != Etat.NEUF && getEtat(piece) != Etat.REPARE) {
                info += ' ' + piece.name();
                this.setEtat(piece, Etat.REPARE);
            }
        return info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicule that = (Vehicule) o;
        return Objects.equals(NIP, that.NIP);
    }

}
