/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risiko;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Elisa
 */
public class Risiko {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        
        int nrPlayers = 3;
        List<Player> players = new ArrayList<>();
        
        for(int i = 0; i<nrPlayers; i++){
            Player player = new Player();
            players.add(player);
        }
        
        Game game = new Game(players);
        game.play();
    }
    
}
