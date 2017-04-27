package risiko;

import java.util.Random;

public class Player {
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
//   
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
    
    public void decrementBonusArmies(int bonusArmies){
        this.bonusArmies-=bonusArmies;
    }
    
}
