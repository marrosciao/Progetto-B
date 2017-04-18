/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risiko;

/**
 *
 * salva il risultato dell'attacco e ridà le cose
 */
public class AttackResult {
    
    private Country attacker;
    private Player attackerPlayer;
    private Country defender;
    private Player defenderPlayer;
    private int armiesAttacker;
    private int armiesDefender;
    private int lostAttacker;
    private int lostDefender;
    private boolean isConquered;
    
    public AttackResult(Country attacker, Player attackerPlayer, Country defender, Player defenderPlayer, int armiesAttacker, int armiesDefender, int lostAttacker, int lostDefender, boolean isConquered) {
        this.attacker = attacker;
        this.attackerPlayer = attackerPlayer;
        this.defender = defender;
        this.defenderPlayer = defenderPlayer;
        this.armiesAttacker = armiesAttacker;
        this.armiesDefender = armiesDefender;
        this.lostAttacker = lostAttacker;
        this.lostDefender = lostDefender;
        this.isConquered = isConquered;
    }

    public Country getAttacker() {
        return attacker;
    }

    public Player getAttackerPlayer() {
        return attackerPlayer;
    }

    public Country getDefender() {
        return defender;
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
     * 
     */
    @Override
    public String toString(){
        //TO DO
        return null;
    }
    
    
    

    
}
