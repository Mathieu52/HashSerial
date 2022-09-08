package revision1;

public class Application {
    public static void main(String[] args) {
        Garage garage = new Garage(4);
        Automobile autoA = new Automobile("A");
        Moto autoB = new Moto("B");
        Camion autoC = new Camion("C");
        Automobile autoD = new Automobile("D");

        garage.stationne(autoA);
        garage.stationne(autoB);
        garage.stationne(autoC);
        garage.stationne(autoD);

        autoA.setEtat(Automobile.Piece.Transmission, Vehicule.Etat.TRES_BRISE);
        autoA.setEtat(Automobile.Piece.Moteur, Vehicule.Etat.BRISE);
        autoB.setEtat(Moto.Piece.Transmission, Automobile.Etat.BRISE);

        autoC.setEtat(Camion.Piece.Carosserie, Vehicule.Etat.TRES_BRISE);
        autoD.setEtat(Automobile.Piece.Moteur, Vehicule.Etat.BRISE);

        System.out.println("Les véhicules sont endommagées");
        System.out.println(autoA);
        System.out.println(autoB);
        System.out.println(autoC);
        System.out.println(autoD);

        System.out.println("\nLes véhicules sont réparées");
        // Réparation des véhicules A et B
        garage.entreVehiculeGarage(autoA, 1);
        garage.entreVehiculeGarage(autoB,2);

        garage.enregisteVehicule("save.txt");

        garage.repare();
        garage.sortVehicule(1,garage.trouvePlace());
        garage.sortVehicule(2,garage.trouvePlace());

        garage.faitDepartVehicule(autoA);
        garage.faitDepartVehicule(autoB);

        System.out.println(autoA);
        System.out.println(autoB);

        // Réparation des véhicules C et D
        garage.entreVehiculeGarage(autoC, 1);
        garage.entreVehiculeGarage(autoD,2);

        garage.repare();
        garage.sortVehicule(1,garage.trouvePlace());
        garage.sortVehicule(2,garage.trouvePlace());

        garage.faitDepartVehicule(autoC);
        garage.faitDepartVehicule(autoD);

        System.out.println(autoC);
        System.out.println(autoD);

        garage.afficheHistorique();
    }
}
