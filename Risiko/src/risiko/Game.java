package risiko;

import java.util.List;

class Game {

    private RisikoMap map;
    private List<Player> players;
    private Player activePlayer;
    private Player winner;

    public Game(List<Player> players) throws Exception {

        this.players = players;
        this.activePlayer = null;
        this.winner = null;
        init();

    }

    /** DA FARE: P3 (4)
     * Inizializza il gioco.
     * Ovvero chiama il metodo della mappa per l'assegnazione
     * iniziale dei territori ai giocatori - assignCountriesToPlayers() - ,
     * setta un giocatore a caso come activePlayer
     * 
     * @throws rilancia l'eccezione che potrebbe lanciare la mappa nel caso in 
     * cui l'url del file dei territori fosse sbagliato.
     */
    
    private void init() throws Exception{

    }

    public void play() {

        while (this.winner == null) {
            playNextTurn();
        }

    }

    public void playNextTurn() {

        // Setto come giocatoreAttivo il prossimo giocatore
        nextTurn();

        // Fase di rinforzo : 
        reinforce(activePlayer);

        // Fase di attacco : 
        while (activePlayer.wants2Attack() && map.playerCanAttack(activePlayer)) {
            performAttackPhase();
        }

    }

    private void performAttackPhase() {
        
        Country[] countries = activePlayer.chooseFightingCountries();
        Player defender = map.getPlayerFromCountry(countries[1]);
        int nrA = activePlayer.chooseNrArmies('a', countries[0]);
        int nrD = defender.chooseNrArmies('d', countries[1]);
        
        boolean conquered = attack(countries, nrA, nrD);
        
        if (conquered) { // TRUE se il territorio è stato conquistato
            
            map.updateOnConquer(countries, nrA);
            winner = (map.checkIfWinner(activePlayer)) ? activePlayer : null;
            
        }
    }

    /** 
     * Chiede alla mappa di _perform_ la fase di rinforzo.
     *
     */
    private void reinforce(Player activePlayer) {
        map.reinforce(activePlayer);
    }

    /** DA FARE: P3 (5)
     * Setta come activePlayer il successivo nel giro.
     */
    private void nextTurn() {

    }
    
    /**
     * Delega alla mappa di gettare le fighting countries del giocatore
     * @param player il giocatore di turno
     * @return array di 2 countries, country[0] atttaccante, [1] attaccato
     */

    Country[] getFightingCountries(Player player) {
        return map.getFightingCountries(player);
    }

    /** DA FARE : P4 (1)
     * Simula l'attacco. Chiama il metodo per calcolare il risulato del
     * combattimento (computeAttackResult(nrA, nrD)),
     * che gli restituisce il numero di armate perse dai due
     * giocatori, quindi rimuove queste armate dai due territori (Country.removeArmies())
     * (<<BOH potremmo anche fare che il metodo removeArmies restituisca un boolean per
     * far capire se è stato conquistato, ma da quello che ho capito dalle
     * ultime lezioni è meglio differenziare i due metodi) @param countries>>
     * @param nrA @param nrD @return true se il territorio è stato conquistato
     * (lo chiede alla mappa, (map.isConquered(Country))
     */
    private boolean attack(Country[] countries, int nrA, int nrD) {
        return false;
    }

    /** DA FARE: P4 (2)
     * Calcola il risultato del combattimento. Dato il numero di armate
     * attaccanti e di armate in difesa, chiama il metodo per il lancio dei dadi
     * (rollDice(nrDadi)), che restituisce il risultato del lancio dei dadi già
     * in ordine decrescente, confronta i risultati ottenuti e calcola quante
     * armate sono state perse dall'attaccante&difensore. (NB nel caso che i due
     * numeri siano ==, vince il difensore)
     *
     * @return array da 2 elementi, il primo valore è il numero di armate perse
     * dall'attaccante, il secondo il numero di armate perse dal difensore.
     */
    private int[] computeAttackResult(int nrA, int nrD) {
        return new int[2];
    }

    /** DA FARE: P4 (3)
     * Simula il lancio di dadi.(usa rollDice() che simula il lancio di un solo dado)
     *
     * @param nrDice numero di dadi da tirare
     * @return un array[nrDadi] con i risultati del lancio !!! in ordine
     * decrescente.!!
     */
    private int[] rollDice(int nrDice) {
        return null;
    }

    /** DA FARE : P4 (4)
     * Simula il lancio di un dado
     *
     * @return un numero random da 1 a 6
     */
    private int rollDice() {
        return 0;
    }

}
