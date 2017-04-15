package risiko;

import java.util.Random;

class Player {
    private String name;
    private int bonusArmies;
    //altri attributi carte e obiettivi
    
//    private Game game;
//
//    public Player(Game game){
//        this.game =game;
//    }
//    
//    public void setGame(Game game) {
//        this.game = game;
//    }
//
//    /**
//     * BOH RAGA MEGA DUBBIO.
//     * Demanda al gioco di scegliere il territorio attaccante e attaccato.
//     * (non mi ricordo perché sia necessario fare questo giro: immaginando che
//     * player.getFightingCountries() sia chiamato in game, il giocatore così
//     * facendo chiede a game di scegliere le sue fightingCountries, e poi le
//     * restituisce a game.... Non è forse meglio che giocatore invece di avere
//     * game come attributo abbia map a cui chiede i suoi territori, scelga
//     * secondo qualche criterio l'attaccante, richieda alla map i confinanti e
//     * poi scelga l'attaccato o una cosa del genere?). 
//     * @return un'array (length : 2) di country, in cui country[0] è l'attaccante e country[1]
//     * l'attaccato
//     */
//    public Country[] chooseFightingCountries() {
//       return game.getFightingCountries(this);      
//    }
//
//    /**
//     * Ritorna il numero di armate con cui attaccare/difendere.
//     *
//     * @param c indica se il giocatore è l'attaccante o il "difensore" ('a'
//     * attaccante 'd' "difensore" )
//     * @param country il territorio interessato dall'attacco
//     * @return un int random tra 1 e: - min(3, nrArmateSulTerritorio-1) nel caso
//     * di armate per l'attacco - min(3, nrArmateSulTerritorio) nel caso di
//     * armate per la difesa
//     * @author Federico
//     */
//    public int chooseNrArmies(char c, Country country) {
//        
//        Random rand = new Random();
//        int limite=0;
//        
//        switch(c){
//        
//            case('a'):
//                limite=Math.min( 3, country.getArmies()-1 );
//                break;
//                
//            case('d'):
//                limite=Math.min( 3, country.getArmies() );
//                break;
// 
//        }
//        return rand.nextInt(limite)+1;
//    }
//
//    /**
//     * Ritorna un boolean random.
//     *
//     * @return true se il giocatore vuole attaccare, false se vuole concludere
//     * la fase di attacco.
//     * @author Federico
//     */
//    public boolean wants2Attack() {
//        
//        double probability = 0.8;
//        Random randomGenerator = new Random();
//        
//        return randomGenerator.nextDouble()<probability;
//}    

    public Player(String name) {
        this.name = name;
        this.bonusArmies = 0;
    }

    public String getName() {
        return name;
    }

    public int getBonusArmies() {
        return bonusArmies;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBonusArmies(int bonusArmies) {
        this.bonusArmies = bonusArmies;
    }
    
}
