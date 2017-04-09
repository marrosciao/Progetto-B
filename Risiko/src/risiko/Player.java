package risiko;

import java.util.Random;

class Player {

    private Game game;

    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * BOH RAGA MEGA DUBBIO.
     * Demanda al gioco di scegliere il territorio attaccante e attaccato.
     * (non mi ricordo perché sia necessario fare questo giro: immaginando che
     * player.getFightingCountries() sia chiamato in game, il giocatore così
     * facendo chiede a game di scegliere le sue fightingCountries, e poi le
     * restituisce a game.... Non è forse meglio che giocatore invece di avere
     * game come attributo abbia map a cui chiede i suoi territori, scelga
     * secondo qualche criterio l'attaccante, richieda alla map i confinanti e
     * poi scelga l'attaccato o una cosa del genere?). 
     * @return un'array (length : 2) di country, in cui country[0] è l'attaccante e country[1]
     * l'attaccato
     */
    public Country[] chooseFightingCountries() {
       return game.getFightingCountries(this);      
    }

    /**
     * DA FARE: P3 (2) Ritorna il numero di armate con cui attaccare/difendere.
     *
     * @param c indica se il giocatore è l'attaccante o il "difensore" ('a'
     * attaccante 'd' "difensore" )
     * @param country il territorio interessato dall'attacco
     * @return un int random tra 1 e: - min(3, nrArmateSulTerritorio-1) nel caso
     * di armate per l'attacco - min(3, nrArmateSulTerritorio) nel caso di
     * armate per la difesa
     */
    public int chooseNrArmies(char c, Country country) {
        
        Random rand = new Random();
        int limite=0;
        
        switch(c){
        
            case('a'):
                limite=Math.min( 3, country.getArmies()-1 );
                break;
                
            case('d'):
                limite=Math.min( 3, country.getArmies() );
                break;
 
        }
        return rand.nextInt(limite)+1;
    }

    /**
     * DA FARE : P3 (3) continua in Game Ritorna un boolean random.
     *
     * @return true se il giocatore vuole attaccare, false se vuole concludere
     * la fase di attacco.
     */
    public boolean wants2Attack() {
        
        Random randomGenerator = new Random();
        
        return randomGenerator.nextBoolean(); 
    }
}
