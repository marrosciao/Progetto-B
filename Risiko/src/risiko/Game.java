package risiko;

import exceptions.LastPhaseException;
import exceptions.PendingOperationsException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Game {
    private RisikoMap map;
    private List<Player> players;
    private Player activePlayer;
    private Player winner;
    private Phase phase;
    private AttackResult attackResult;

    public Game(int nrPlayers) throws Exception {
        this.players = new ArrayList<>();
        this.activePlayer = null;
        this.winner = null;
        this.map = new RisikoMap();
        init(nrPlayers);
    }

    public Phase getPhase() {
        return phase;
    }

    /**
     * Inizializza il gioco. Ovvero chiama il metodo della mappa per
     * l'assegnazione iniziale dei territori ai giocatori -
     * assignCountriesToPlayers() - , setta un giocatore a caso come
     * activePlayer
     *
     * @author Federico
     * @throws rilancia l'eccezione che potrebbe lanciare la mappa nel caso in
     * cui l'url del file dei territori fosse sbagliato.
     */
    private void init(int nrPlayers) throws Exception {
        buildPlayers(nrPlayers);
        map.assignCountriesToPlayers(players);

        Random randomGenerator = new Random();
        int randomIndex = randomGenerator.nextInt(players.size());
        activePlayer = players.get(randomIndex);
        map.computeBonusArmies(activePlayer);
        this.phase = Phase.REINFORCE;
    }

    //Aggiungere nomi diversi per i giocatori
    private void buildPlayers(int nrPlayers) {
        for (int i = 0; i < nrPlayers; i++) 
            this.players.add(new Player("Giocatore-"+i));
    }

    public void attack(String countryAttackerName,  String countryDefenderName, int nrA, int nrD) {
        
        Player  defenderPlayer = map.getPlayerFromCountryName(countryDefenderName);
        Player  attackerPlayer = map.getPlayerFromCountryName(countryAttackerName);
        int     armiesLost[]   = fight(countryAttackerName, countryDefenderName, nrA, nrD);
        boolean conquered      = map.isConquered(countryDefenderName);
        if (conquered) {
            map.updateOnConquer(countryAttackerName, countryDefenderName, nrA);
            if (map.hasLost(defenderPlayer))     players.remove(defenderPlayer);
        }
        attackResult=new AttackResult(attackerPlayer,
                                      defenderPlayer, nrA, nrD, 
                                      armiesLost[0],   armiesLost[1], conquered);
    }

    /**
     * Simula lo scontro tra due armate, eliminado quelle perse per ogni country.
     * @param countries
     * @param nrA
     * @param nrD
     * @return armiesLost necessario per istanziare attackResult nel metodo attack
     * @author Andrea
     */
    private int[] fight(String countryAttackerName, String countryDefenderName, int nrA, int nrD) {
       	int armiesLost[] = computeArmiesLost(nrA, nrD);
        map.removeArmies(countryAttackerName, armiesLost[0]);
        map.removeArmies(countryDefenderName, armiesLost[1]);
        return armiesLost;
    }
    
    /**
     * Genera il numero di armate perse per giocatore durante uno scontro. 
     * @return array da 2 elementi, il primo valore è il numero di armate perse
     * dall'attaccante, il secondo il numero di armate perse dal difensore.
     * @author Andrea
     */
    private int[] computeArmiesLost(int nrA, int nrD) {
        int resultsDiceAttack[] = rollDice(nrA);
        int resultsDiceDefens[] = rollDice(nrD);
        int armiesLost[] = new int[2];
        int min = (nrA > nrD) ? nrD : nrA;
        for (int i = 0; i < min; i++) {
            if (resultsDiceAttack[i] > resultsDiceDefens[i]) 
                armiesLost[1]++;
            else 
                armiesLost[0]++;
        }
        return armiesLost;
    }

    /**
     * Lancia una serie di dadi e restituisce i loro valori in ordine decrescente!
     * @param nrDice numero di dadi da tirare
     * @return un array[nrDadi]con i risultati del lancio in ordine decrescente!
     * @author Andrea
     */
    private int[] rollDice(int nrDice) {
        int dices[] = new int[nrDice];
        int tmp;
        for (int i = 0; i < nrDice; i++)
            dices[i] = rollDice();
        Arrays.sort(dices);            
        if (nrDice > 1) {
            tmp = dices[0];
            dices[0] = dices[nrDice - 1];
            dices[nrDice - 1] = tmp;
        }
        return dices;
    }
    
    /**
     * Lancia il dado e ritorna il suo risultato.
     * @return un numero random da 1 a 6
     * @author Andrea
     */
    private int rollDice() {
        return (int) (Math.random() * 6) + 1;
    }    

    /**
     *  ridà il risultato
     *  @author Andrea
     */
    public AttackResult getAttackResult() {
        return attackResult;
    }

    /**
     *  Controlla se il difensore deve essere eliminato dal gioco.
     */
    private void hasLost(Player defenderPlayer) {
        if (map.hasLost(defenderPlayer))
            players.remove(defenderPlayer);
    }

    /**
     * controlla e aggiunge le armate al territorio, queste vengono prese dal
     * campo bonusArmies del giocatore fino ad esaurimento
     *
     * @param player giocatore che vuole eseguire azione
     * @param nArmies numero di armate da aggiungere
     * @param country territorio sul quale aggiungerle
     * @return
     */
    public boolean reinforce(String countryName, int nArmies) {
        if (activePlayer.getBonusArmies() - nArmies >= 0) {
            activePlayer.decrementBonusArmies(nArmies);
            map.addArmies(countryName, nArmies);
            return true;
        }
        return false;
    }
    
    public boolean canReinforce(String countryName, int nArmies){
        return activePlayer.getBonusArmies() - nArmies >= 0;
    }
    /**
     * Cambia la fase.
     * - 1 Controlla che non ci siano operazioni in sospeso relative alla corrente
     * fase del gioco:
     *   > REINFORCE : activePlayer non deve avere bonus armies
     * 
     * - 2 SE è l'ultima fase chiama passTurn()
     *
     * @throws PendingOperationsException se non è possibile passare alla fase
     * successiva perché ci sono operazioni in sospeso.
     * @author Carolina
     */
    public void nextPhase() /*throws PendingOperationsException */{

        // #1 (switch?)
        if (phase == Phase.REINFORCE && activePlayer.getBonusArmies() != 0) 
             //throw new PendingOperationsException();
            return;  
        
        // #2
        try {
            this.phase = phase.next();
        } catch (LastPhaseException ex) {
            passTurn();
        }
    }

    /**
     * Passa il turno al giocatore successivo.
     * Ovvero 
     * 1 - Setta come active player il successivo nel giro
     * 2 - Setta come fase la prima del turno
     * 3 - Assegna all'active player le armate bonus
     * 
     * @author Carolina
     */
    public void passTurn() {
        
        // #1
        nextTurn();
        
        // #2
        this.phase = Phase.values()[0];
        
        // #3
        map.computeBonusArmies(activePlayer);
    }

    /**
     * Setta come activePlayer il successivo nel giro.
     *
     * @author Federico
     */
    private void nextTurn() {
        ListIterator<Player> iter = players.listIterator(players.indexOf(activePlayer) + 1);

        if (iter.hasNext()) {
            activePlayer = iter.next();
        } else {
            activePlayer = players.get(0);
        }
    }
   
    //  M E T O D I   R I P R E S I   D A   M A P

    /**
     * Controlla se il giocatore ha vinto
     *
     * @param player l'active player
     * @return true se il giocatore ha vinto, false altrimenti
     * @author Carolina
     */
    public boolean hasWon(Player player) {
        return map.checkIfWinner(player);
    }

    /**
     *  Controlla che country sia dell'activePlayer e che si legale attaccare.
     */
    public boolean controlAttacker(String countryName) {
        return map.controlAttacker(countryName, activePlayer);
    }
    
    /**
     * Controlla che country sia di player.
     */
    public boolean controlPlayer(Country country) {
        return map.controlPlayer(country, activePlayer);
    }

    /**
     * Controlla che il territorio non sia dell'active player e che sia un
     * confinante dell'attacker.
     */
    public boolean controlDefender(String attacker, String defender) {
        return map.controlDefender(attacker, defender, activePlayer);
    }

    /**
     *  Ridà il max numero di armate per lo spinner rispetto al tipo di country.
     */
    public int getMaxArmies(String countryName, boolean isAttacker) {
        return map.getMaxArmies(countryName, isAttacker);
    }

    
    //  M E T O D I   P E R   D A R E   I N F O
     
    /**
     *  Ridà i country per i combo.
     *  ANDREA: genera un'eccezione
     */
    public Country[] getCountryList() {
        Country[] cl = new Country[map.getCountriesList().size()]; 
        map.getCountriesList().toArray(cl);
        return cl;
        //return (Country[]) map.getCountriesList().toArray();
    }
    
    // TMP
    public String[] getCountryNameList(){
        Country[] countries = getCountryList();
        String[] names = new String[countries.length];
        
        for(int i =0; i< countries.length; i++){
            names[i]=countries[i].getName();
        }
        
        return names;
        
    }

    /**
     *  ridà le info da metter nel text area.
     */
    public Map<Country, Player> getCountryPlayer() {
        return map.getCountryPlayer();
    }

    /**
     * Restituisce l'active player e la fase del gioco in cui ci si trova
     * @author Federico
     */
    public String getInfo() {
        return "Turno di: " + activePlayer.getName() + "\nFase corrente: " + phase.name();
    }

    boolean canAttackFromCountry(String attackerCountry) {
        return map.canAttackFromCountry(attackerCountry);
    }
}
