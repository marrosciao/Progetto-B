package risiko;

/**
 *
 * salva il risultato dell'attacco e ridà le cose
 */
public class AttackResult {
    
    private Country attackerCountry;
    private Player  attackerPlayer;
    private Country defenderCountry;
    private Player  defenderPlayer;
    private int     armiesAttacker;
    private int     armiesDefender;
    private int     lostAttacker;
    private int     lostDefender;
    private boolean isConquered;
    
    public AttackResult(Country attackerCountry, Player attackerPlayer, Country defenderCountry, Player defenderPlayer, int armiesAttacker, int armiesDefender, int lostAttacker, int lostDefender, boolean isConquered) {
        this.attackerCountry = attackerCountry;
        this.attackerPlayer = attackerPlayer;
        this.defenderCountry = defenderCountry;
        this.defenderPlayer = defenderPlayer;
        this.armiesAttacker = armiesAttacker;
        this.armiesDefender = armiesDefender;
        this.lostAttacker = lostAttacker;
        this.lostDefender = lostDefender;
        this.isConquered = isConquered;
    }

    public Country getAttacker() {
        return attackerCountry;
    }

    public Player getAttackerPlayer() {
        return attackerPlayer;
    }

    public Country getDefender() {
        return defenderCountry;
    }

    public Player getDefenderPlayer() {
        return defenderPlayer;
    }

    public int getArmiesAttacker() {
        return armiesAttacker;
    }

    public int getArmiesDefender() {
        return armiesDefender;
    }

    public int getLostAttacker() {
        return lostAttacker;
    }

    public int getLostDefender() {
        return lostDefender;
    }

    public boolean isIsConquered() {
        return isConquered;
    }

    
    /**
     * Ritorna la stringa da stampare nel textarea
     * @author alessandro
     * @return 
     * 
     * ANDREA: ci ho scritto un prototipo di codice per controllare il metodo attack     */
    @Override
    public String toString(){
        String str = "Territorio attaccante: "+attackerCountry.getName()+"\n"+
                "Proprietario del territorio attaccante: "+attackerPlayer.getName()+"\n"+
                "Numero armate attaccanti: "+armiesAttacker+"\n"+
                "Numero armate attaccanti perse: "+lostAttacker+"\n"+
                "Territorio difensore: "+defenderCountry.getName()+"\n"+
                "Proprietario del territorio difensroere: "+defenderPlayer.getName()+"\n"+
                "Numero armate in difesa: "+armiesDefender+"\n"+
                "Numero armate in difesa perse: "+lostDefender+"\n"+
                "Territorio in difesa è stato conquistato: "+isConquered+"\n"; 
        return str;
    }
}