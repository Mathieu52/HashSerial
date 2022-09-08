package revision1;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Garage {
    private Vehicule[] stationnements;
    private Vehicule[] garages;
    private HashMap<LocalDateTime, String> historique = new HashMap<>();


    /**
     * crée un Garage avec le nombre de place de stationnement demandé et toujours 2 places de garage pour les réparations.
     *
     * @param nombrePlacesStationnement le nombre de places requises.
     */
    public Garage(int nombrePlacesStationnement) {
        assert nombrePlacesStationnement > 0 : "valeur négative";
        garages = new Vehicule[4];
        stationnements = new Vehicule[nombrePlacesStationnement];
    }

    /**
     * Stationne l'auto dans le premier emplacement vide. La méthode trouveIndexPlaceLibre peut aider à le faire.
     * La méthode stationnementEstPlein indique s'il y a une place disponible.
     *
     * @param auto l'auto à placer dans le stationnement
     * @return faux s'il n'y a plus de palce
     */
    public boolean stationne(Vehicule auto) {
        int freeIndex = trouveIndexPlaceLibre();
        if (freeIndex == -1)
            return false;
        stationnements[freeIndex] = auto;
        return true;
    }

    /**
     * indique si le stationnement est plein
     *
     * @return vrai s'il est plein
     */
    private boolean stationnementEstPlein() {
        for (Vehicule automobile : stationnements)
            if (automobile == null)
                return false;
        return true;
    }

    /**
     * trouve la première place libre. On doit vérifier qu'il y a une place libre avant d'appeler cette méthode
     *
     * @return l'indice de la place libre ou ArrayIndexOutOfBoundsException si le stationnement est plein
     */
    private int trouveIndexPlaceLibre() {
        for (int indexStationement = 0; indexStationement < stationnements.length; indexStationement++)
            if (stationnements[indexStationement] == null)
                return indexStationement;
        throw new ArrayIndexOutOfBoundsException("Le stationnement est plein");
    }

    /**
     * trouve la première place libre. On doit vérifier qu'il y a une place libre avant d'appeler cette méthode
     *
     * @return l'indice de la place libre ou ArrayIndexOutOfBoundsException si le stationnement est plein
     */
    public int trouvePlace() {
        return trouveIndexPlaceLibre() + 1;
    }


    /**
     * entre le vehicule demandé dans le garage à la place demandé. On peut retrouver la
     * place de stationnement d'un vehicule à l'aide de la méthode chercheVehiculeStationnement.
     *
     * @param vehiculeRepare le vehicule à entrer (il doit être dans le stationnement)
     * @param placeGarage    la place du garage (elle ne doit pas contenir de vehicule)
     * @return vrai si le vehicule a pu être entré
     */
    public boolean entreVehiculeGarage(Vehicule vehiculeRepare, int placeGarage) {
        int indexGarage = placeGarage - 1;
        assert vehiculeRepare != null : "null Vehicule";
        assert indexGarage >= 0 : "place négative";
        assert indexGarage < garages.length : "place inexistante";

        if (garages[indexGarage] != null)
            return false;

        int indexStationements = chercheVehiculeStationnement(vehiculeRepare);
        if (indexStationements == -1)
            return false;

        garages[indexGarage] = vehiculeRepare;
        stationnements[indexStationements] = null;
        return true;
    }

    /**
     * trouve le vehicule reçu en paramètre dans le stationnement et retourne son index
     *
     * @param vehiculeRepare le véhicule à trouver (avec méthode equals)
     * @return l'index du vehicule ou -1 s'il n'a pas été trouvé
     */
    private int chercheVehiculeStationnement(Vehicule vehiculeRepare) {
        assert vehiculeRepare != null : "parametre null";

        int index = -1;
        for (int indexStationement = 0; indexStationement < stationnements.length; indexStationement++)
            if (vehiculeRepare.equals(stationnements[indexStationement])) {
                index = indexStationement;
                break;
            }
        return index;
    }

    /**
     * sort le vehicule du garage et le remet dans le stationnement.
     *
     * @param placeGarage       la place du garabe où prendre le vehicule à sortir ( il doit y avoir un vehicule à cette place)
     * @param placeStationement la place de stationnement où mettre le vehicule (le stationnement doit être libre)
     * @return retourne vrai si le vehicule est sorti.
     */
    public boolean sortVehicule(int placeGarage, int placeStationement) {
        int indexGarage = placeGarage - 1;
        int indexStationement = placeStationement - 1;

        if (indexGarage < 0 || indexGarage >= garages.length || indexStationement < 0 || indexStationement >= stationnements.length)
            return false;
        if (garages[indexGarage] == null || stationnements[indexStationement] != null)
            return false;

        stationnements[indexStationement] = garages[indexGarage];
        garages[indexGarage] = null;

        return true;
    }

    /**
     * gère le départ d'un véhicule en l'effacant du stationnement et en retournant sa valeur
     *
     * @param auto le vehicule qui doit être retiré
     * @return le vehicule qui doit être retiré si il est trouvé null autrement.
     */
    public Vehicule faitDepartVehicule(Vehicule auto) {
        assert auto != null : "null Vehicule";

        for (int indexStationement = 0; indexStationement < stationnements.length; indexStationement++)
            if (auto.equals(stationnements[indexStationement])) {
                stationnements[indexStationement] = null;
                return auto;
            }

        return null;
    }

    /**
     * répare tous les dommages de tous les vehicules dans le garage en indiquant l'état réparé.
     */
    public void repare() {
        for (Vehicule auto : garages)
            if (auto != null) {
                String infoRepare = auto.repare();
                while(!historique.putIfAbsent(LocalDateTime.now(), auto.repare()).equals(infoRepare));
            }
    }

    public void afficheHistorique() {
        System.out.println("Historique: ");
        historique.forEach((key, value) -> System.out.println(key + ":" + value));
        System.out.println("fin de l'historique");
    }

    public void enregisteVehicule(String nomFichier) {
        try (FileWriter fileWriter = new FileWriter(nomFichier)) {
            fileWriter.write("Stationement :\n");

            for (int i = 0; i < stationnements.length; i++)
                fileWriter.write("  " + (i + 1) + " - " +  (stationnements[i] == null ? "Libre" : stationnements[i].toString()) + '\n');

            fileWriter.write("Garage :\n");
            for (int i = 0; i < garages.length; i++)
                fileWriter.write("  " + (i + 1) + " - " +  (garages[i] == null ? "Libre" : garages[i].toString()) + '\n');

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
