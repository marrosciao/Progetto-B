/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risiko;

import java.util.List;
import java.util.Map;

/**
 *
 * @author alessandro
 */
public class GameStatus {
    private Map<Country, Player> countryPlayer;
    private Map<Country, List<Country>> countryNeighbors;
    private List<Player> players;
    private Player activePlayer;

    public GameStatus(Map<Country, Player> countryPlayer, Map<Country, List<Country>> countryNeighbors, List<Player> players, Player activePlayer) {
        this.countryPlayer = countryPlayer;
        this.countryNeighbors = countryNeighbors;
        this.players = players;
        this.activePlayer = activePlayer;
    }
    
    //da inserire: metodi che restituiscono le varie informazioni sullo stato attuale del gioco senza poterlo modificare
}
