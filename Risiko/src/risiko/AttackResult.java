package risiko;

/**
 *
 * salva il risultato dell'attacco e ridà le cose
 */
public class AttackResult {

    private Player attackerPlayer;
    private Player defenderPlayer;
    private int armiesAttacker;
    private int armiesDefender;
    private int lostAttacker;
    private int lostDefender;
    private boolean isConquered;

    public AttackResult(Player attackerPlayer, Player defenderPlayer, int armiesAttacker, int armiesDefender, int lostAttacker, int lostDefender, boolean isConquered) {

        this.attackerPlayer = attackerPlayer;
        this.defenderPlayer = defenderPlayer;
        this.armiesAttacker = armiesAttacker;
        this.armiesDefender = armiesDefender;
        this.lostAttacker = lostAttacker;
        this.lostDefender = lostDefender;
        this.isConquered = isConquered;
    }

    public Player getAttackerPlayer() {
        return attackerPlayer;
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
     *
     * @author alessandro
     * @return
     *
     * ANDREA: ci ho scritto un prototipo di codice per controllare il metodo
     * attack
     */
    @Override
    public String toString() {
        String str
                = "Proprietario del territorio attaccante: " + attackerPlayer.getName() + "\n"
                + "Numero armate attaccanti: " + armiesAttacker + "\n"
                + "Numero armate attaccanti perse: " + lostAttacker + "\n"
                + "Proprietario del territorio difensroere: " + defenderPlayer.getName() + "\n"
                + "Numero armate in difesa: " + armiesDefender + "\n"
                + "Numero armate in difesa perse: " + lostDefender + "\n"
                + "Territorio in difesa è stato conquistato: " + isConquered + "\n";
        return str;
    }
}
