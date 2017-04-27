
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

/**
 * Dividere verify attack in modo da avere un controllo sull'attaccante, uno sul
 * difensore (guarda gui) mettere metodo che mi ridà il massimo di armate con
 * cui posso attacare o difendere Cambiamo country[] in attacker, defender
 */
public class RisikoMap {

    private final int DEFAULT_ARMIES = 3;
    //private final String urlCountries = "files/territori.txt";
    private final String urlCountries = "files/prova.txt";
    private Map<Country, Player> countryPlayer;
    private Map<Country, List<Country>> countryNeighbors;
    private Map<String, Country> nameCountry;

    public Map<Country, Player> getCountryPlayer() {
        return countryPlayer;
    }

    public RisikoMap() throws Exception {

        this.countryPlayer = new HashMap<>();
        this.countryNeighbors = new HashMap<>();
        this.nameCountry = new HashMap<>();
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

            String line;

            while ((line = br.readLine()) != null) {

                if (!line.startsWith("-")) {

                    StringTokenizer st = new StringTokenizer(line, ",");

                    if (st.hasMoreTokens()) {

                        Country country = new Country(st.nextToken());
                        country.setArmies(DEFAULT_ARMIES);
                        this.countryPlayer.put(country, null);
                        this.nameCountry.put(country.getName(), country);
                    }
                }
            }

            buildCountryNeighbors();

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
    private void buildCountryNeighbors() throws Exception {

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
                            country = nameCountry.get(token);
                        } else {
                            Country neighbour = nameCountry.get(token);
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

    /**
     * Ritorna il giocatore successivo nella lista dei players a quello che si
     * passa; se il giocatore è l'ultimo della lista ritorna il primo.
     *
     * @param players
     * @param round
     * @return Player
     * @author Elisa
     */
    private Player nextPlayer(List<Player> players, int round) {

        return players.get(round % (players.size()));

    }

    /**
     * Ritorna una lista con tutti i countries
     *
     *
     * @return List
     * @author Elisa
     */
    public List<Country> getCountriesList() {
        return new ArrayList<>(countryPlayer.keySet());
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
    
    public Player getPlayerFromCountryName (String countryName){
        return countryPlayer.get(getCountryByName(countryName));
    }

    /**
     * Esegue la fase di rinforzo di inizio turno. Nr armate bonus =
     * ceil(nrTerritoriGiocatore/3) e le distribuisce a caso sui territori del
     * giocatore
     *
     * @param player il giocatore di turno
     * @author Elisa
     */
    void computeBonusArmies(Player player) {
        int bonus = (int) Math.floor(getMyCountries(player).size() / 3);
        player.setBonusArmies(bonus);
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
//    public Country[] getFightingCountries(Player player) {
//        int attackIndex, defenseIndex;
//        Country[] selected = new Country[2];
//
//        List<Country> attack = this.getMyCountries(player);
//        Collections.shuffle(attack);
//        for (Country a : attack) {
//            List<Country> defense = this.getNeighbors(a);
//
//            Collections.shuffle(defense);
//
//            for (Country d : defense) {
//                selected[0] = a;
//                selected[1] = d;
//                if (verifyAttack(selected, player)) {
//                    return selected;
//                }
//            }
//        }
//        return null;
//    }
    /**
     * Controlla che l'attacco sia valido. return true se Country[0] è del
     * giocatore di turno e ha di un'armata, Country[1] è di un altro giocatore,
     * e i due territori sono confinanti, false altrimenti.
     *
     * @param countries array di territori, [0] attaccante, [1] attaccato
     * @param player il giocatore di turno.
     * @author Alessandro
     */
    /*public boolean verifyAttack(Country[] countries, Player player) {
        if (this.countryPlayer.get(countries[0]) != null && this.countryPlayer.get(countries[0]).equals(player) && countries[0].getArmies() > 1) {
            if (this.countryPlayer.get(countries[1]) != null && !this.countryPlayer.get(countries[1]).equals(player) ) {
                return true;
            }
        }
        return false;
    }*/
 /*
        Controlla che il territorio sia dell'active player e che si legale attaccare
     */
    public boolean controlAttacker(String countryName, Player player) {
        Country country = getCountryByName(countryName);
        return this.countryPlayer.get(country).equals(player) && country.getArmies() > 1;

    }
    
    public Country getCountryByName (String name){
        return this.nameCountry.get(name);
    }
    
    public boolean controlPlayer(Country country, Player player) {
        return this.countryPlayer.get(country).equals(player) ;

    }

    /**
     * Controlla che il territorio non sia dell'active player e che sia un
     * confinante dell'attacker
     */
    public boolean controlDefender(String attackerName, String defenderName, Player player) {
        
        Country attacker = getCountryByName(attackerName);
        Country defender = getCountryByName(defenderName);
        
        return !this.countryPlayer.get(defender).equals(player) && this.getNeighbors(attacker).contains(defender);
    }

    /*
        Ridà il massimo numero di armate per lo spinner rispetto al tipo di country
     */
    public int getMaxArmies(String countryName, boolean isAttacker) {
        
        Country country = getCountryByName(countryName);
        
        if (isAttacker) {
             return Math.min(3, country.getArmies()-1);
        }
        return Math.min(3, country.getArmies()); 
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
    public void updateOnConquer(String countryAttackerName,  String countryDefenderName, int armies) {
        
        Country countryAttacker = getCountryByName(countryAttackerName);
        Country countryDefender = getCountryByName(countryDefenderName);
        
        Player attack = this.countryPlayer.get(countryAttacker);
        this.countryPlayer.put(countryDefender, attack);
        countryAttacker.removeArmies(armies);
        countryDefender.setArmies(armies);
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

        List<Country> countryList = getCountriesList(); 

        return getMyCountries(player).containsAll(countryList);
    }

    /**
     * Controlla se il territorio è stato conquistato
     *
     * @return true se non ha armate
     */
    public boolean isConquered(String countryName) {
        return getCountryByName(countryName).isConquered();
    }

    public Map<Country, List<Country>> getCountryNeighbors() {
        return this.countryNeighbors;
    }

    /*
     *   controlla se il difensore non ha più territori
     */
    public boolean hasLost(Player defenderPlayer) {
        return getMyCountries(defenderPlayer).isEmpty();
    }

    public void addArmies(String countryName, int nArmies) {
       getCountryByName(countryName).addArmies(nArmies);
    }

    public void removeArmies(String countryName, int nArmies) {
        getCountryByName(countryName).removeArmies(nArmies);
    }

    boolean canAttackFromCountry(String countryName) {
        return getCountryByName(countryName).getArmies()>1;
    }
}