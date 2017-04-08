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
