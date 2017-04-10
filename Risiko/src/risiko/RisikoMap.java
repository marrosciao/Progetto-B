package risiko;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class RisikoMap {

    private final int DEFAULT_ARMIES = 3;
    //private final String urlCountries = "files/territori.txt";
    private final String urlCountries = "files/prova.txt";
    private Map<Country, Player> countryPlayer;
    private Map<Country, List<Country>> countryNeighbors;

    public RisikoMap() throws Exception {

        this.countryPlayer = new HashMap<>();
        this.countryNeighbors = new HashMap<>();
        init();

    }

    /**
     * Inizializza la mappa leggendo il file specificato in urlCountries (in
     * countryPlayer il player è inizializzato a null)
     *
     * @throws qualche eccezione relativa alla lettura del file
     */
    private void init() throws Exception {

        buildCountryPlayer();
    }

    /**
     * Legge il file specificato a urlCountries, di ogni riga letta prende solo
     * il primo token e builda la Country relativa e la mette in CountryPlayer
     *
     * @author Elisa
     * @throws qualche eccezione legata alla lettura del file
     */
    private void buildCountryPlayer() throws Exception {

        try (BufferedReader br = new BufferedReader(new FileReader(urlCountries))) {

            Map<String, Country> nameCountry = new HashMap<>();
            String line;

            while ((line = br.readLine()) != null) {

                if (!line.startsWith("-")) {

                    StringTokenizer st = new StringTokenizer(line, ",");

                    if (st.hasMoreTokens()) {

                        Country country = new Country(st.nextToken());
                        country.setArmies(DEFAULT_ARMIES);
                        this.countryPlayer.put(country, null);
                        nameCountry.put(country.getName(), country);
                    }
                }
            }

            buildCountryNeighbors(nameCountry);

        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
        }

    }

    /**
     * Legge il file specificato da urlCountries, per ogni country costruisce
     * una List di vicini, salva in CountryNeighbor il territorio e la lista di
     * vicini.
     *
     * @throws qualche eccezione legata alla lettura del file
     * @author Elisa
     */
    private void buildCountryNeighbors(Map<String, Country> stringCountry) throws Exception {

        List<Country> neighbours = new ArrayList<>();
        Country country = null;

        try (BufferedReader br = new BufferedReader(new FileReader(urlCountries))) {

            String line;

            while ((line = br.readLine()) != null) {
                if (!line.startsWith("-")) {
                    StringTokenizer st = new StringTokenizer(line, ",");
                    int i = 0;

                    while (st.hasMoreTokens()) {
                        String token = st.nextToken();
                        if (i == 0) {
                            country = stringCountry.get(token);
                        } else {
                            Country neighbour = stringCountry.get(token);
                            neighbours.add(neighbour);
                        }

                        i++;

                    }

                    this.countryNeighbors.put(country, neighbours);
                    neighbours = new ArrayList<>();
                }
            }

        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
        }

    }

    /**
     * Effettua l'assegnazione iniziale dei territori ai giocatori (random).
     *
     * @param players
     * @author Elisa
     */
    public void assignCountriesToPlayers(List<Player> players) throws Exception {

        List<Country> countries = getCountriesList();
        Collections.shuffle(countries);
        int nCountries = this.countryPlayer.size();
        int round = 0;
        while (nCountries != 0) {
            this.countryPlayer.put(countries.get(round), nextPlayer(players, round));
            //System.out.println(countries.get(round).getName()+" "+nextPlayer(players, round));
            round++;
            nCountries--;
        }

    }

    private Player nextPlayer(List<Player> players, int round) {

        return players.get(round % (players.size()));

    }

    public List<Country> getCountriesList() {
        List<Country> countries = new ArrayList<>();
        for (Map.Entry<Country, Player> entry : this.countryPlayer.entrySet()) {
            countries.add(entry.getKey());
        }
        return countries;
    }

    /**
     * Ritorna il giocatore a cui appartiene quel territorio
     *
     * @param country
     * @return
     */
    public Player getPlayerFromCountry(Country country) {
        return countryPlayer.get(country);
    }

    /**
     * Esegue la fase di rinforzo di inizio turno. Nr armate bonus =
     * ceil(nrTerritoriGiocatore/3) e le distribuisce a caso sui territori del
     * giocatore
     *
     * @param player il giocatore di turno
     * @author Elisa
     */
    void reinforce(Player player) {

        List<Country> myCountries = getMyCountries(player);
        Collections.sort(myCountries);

        int bonus = (int) Math.floor(myCountries.size() / 3);

        myCountries.get(0).addArmies(bonus);
        /*
        while (bonus != 0) {
            int c = (int) Math.floor(Math.random() * myCountries.size());
            myCountries.get(c).addArmies(1);

            bonus--;
            
        }*/

    }

    /**
     * Ritorna una lista dei territori del giocatore
     *
     * @param player
     * @return
     * @author Alessandro
     */
    public List<Country> getMyCountries(Player player) {
        return this.countryPlayer.entrySet().stream().filter(mapEntry -> mapEntry.getValue().equals(player)).map(mapE -> mapE.getKey()).collect(Collectors.toList());
    }

    /**
     * Ritorna la lista dei vicini di un territorio.
     *
     * @param country
     */
    public List<Country> getNeighbors(Country country) {
        return countryNeighbors.get(country);
    }

    /**
     * Controlla se il giocatore può ancora attaccare da uno dei suoi territori.
     *
     * @param activePlayer
     * @return true nel caso in cui almeno uno dei territori di ActivePlayer
     * abbia più di un'armata, false in caso contrario.
     * @author Alessandro QUESTIONE: forse dovremmo controllare che la country
     * eventualmente trovata con più di un'armata, abbia dei vicini attaccabili
     * (non suoi)
     */
    public boolean playerCanAttack(Player player) {
        List<Country> availableCountries = this.getMyCountries(player);
        for (Country c : availableCountries) {
            if (c.getArmies() > 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sceglie il territorio attaccante e attaccato dal giocatore. (Idealmente :
     * continua a tirare a caso coppie di countries (e a chiamare verifyAttack
     * per ognuna di loro, finché verify attack non restituisce true)
     *
     * @param player il giocatore di turno.
     * @return un array di territori, Country[0] quello attaccante, Country[1]
     * quella attaccato
     * @author Alessandro
     */
    public Country[] getFightingCountries(Player player) {
        int attackIndex, defenseIndex;
        Country[] selected = new Country[2];

        List<Country> attack = this.getMyCountries(player);
        Collections.shuffle(attack);
        for (Country a : attack) {
            List<Country> defense = this.getNeighbors(a);

            Collections.shuffle(defense);

            for (Country d : defense) {
                selected[0] = a;
                selected[1] = d;
                if (verifyAttack(selected, player)) {
                    return selected;
                }
            }
        }
        return null;
    }

    /**
     * Controlla che l'attacco sia valido. return true se Country[0] è del
     * giocatore di turno e ha di un'armata, Country[1] è di un altro giocatore,
     * e i due territori sono confinanti, false altrimenti.
     *
     * @param countries array di territori, [0] attaccante, [1] attaccato
     * @param player il giocatore di turno.
     * @author Alessandro
     */
    public boolean verifyAttack(Country[] countries, Player player) {
        if (this.countryPlayer.get(countries[0]) != null && this.countryPlayer.get(countries[0]).equals(player) && countries[0].getArmies() > 1) {
            if (this.countryPlayer.get(countries[1]) != null && !this.countryPlayer.get(countries[1]).equals(player) /*&&countries[1].getArmies() > 1*/) {
                return true;
            }
        }
        return false;
    }

    /**
     * Metodo chiamato nel caso in cui un giocatore abbia conquistato un
     * territorio. Setta il nuovo proprietario del territorio appena conquistato
     * (countryPlayer) e muove tante armate quante sono quelle con cui è stato
     * eseguito l'attacco dal territorio attaccante a quello conquistato.
     *
     * @param countries array di Country (lenght:2) che ha come elemento [0] il
     * territorio attaccante, mentre l'elemento [1] è quello appena conquistato
     *
     * @param armies è il numero di armate con cui è stato sferrato l'attacco, e
     * in questa prima versione del gioco anche il numero di armate che vengono
     * spostate dal territorio attaccante a quello appena conquistato.
     * @author Alessandro
     */
    public void updateOnConquer(Country[] countries, int armies) {
        Player attack = this.countryPlayer.get(countries[0]);
        this.countryPlayer.put(countries[1], attack);
        countries[0].removeArmies(armies);
        countries[1].setArmies(armies);

    }

    /**
     * Metodo chiamato nel caso in cui un giocatore abbia conquistato un
     * territorio. La mappa controlla se ha raggiunto il suo obbiettivo (in
     * questa prima versione del gioco conquistare il mondo).
     *
     * @param player il giocatore di turno
     * @return true se il giocatore ha vinto, false altrimenti.
     * @author Federico
     */
    public boolean checkIfWinner(Player player) {

        List<Country> countryList = new ArrayList<>(countryPlayer.keySet()); //si potrebbe usare anche per il getCountryList?

        return getMyCountries(player).containsAll(countryList);
    }

    /**
     * Controlla se il territorio è stato conquistato
     *
     * @return true se non ha armate
     */
    public boolean isConquered(Country country) {
        return country.isConquered();
    }

    public Map<Country, List<Country>> getCountryNeighbors() {
        return this.countryNeighbors;
    }
}
