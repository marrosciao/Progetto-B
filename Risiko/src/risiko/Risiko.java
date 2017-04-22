package risiko;

import exceptions.LastPhaseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Elisa
 */
public class Risiko {

    /**
     * @param args the command line arguments
     * creare frame che contiene la gui e passare alla gui game
     */
    public static void main(String[] args) throws Exception {

        Game game = new Game(5);
        Gui interfaccia = new Gui(game);
        interfaccia.setVisible(true);
        //game.play();
    }

}
