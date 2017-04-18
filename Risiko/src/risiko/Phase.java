/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risiko;

/**
 *
 * @author Elisa
 */
public enum Phase {
    REINFORCE(0), FIGHT(1); //MOVE, PLAYCARDS, ecc
    
    private int value;

       
    private Phase(int value){
        this.value=value;
    }
    
    public int getValue() {
        return value;
    }
    
    
    
}
