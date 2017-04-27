package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.MouseInputAdapter;
import risiko.Game;

/**
 *
 * @author andrea
 */
public class LabelMapListener extends MouseInputAdapter {

    private Game game;
    private final BufferedImage bufferedImage;
    private final Map<Color, String> ColorNameCountry;

    public LabelMapListener(BufferedImage bufferedImage, Map<Color, String> ColorNameCountry, Game game) {
        this.game = game;
        this.bufferedImage = bufferedImage;
        this.ColorNameCountry = ColorNameCountry;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        String countryName = getCountryFromClick(e);

        if (countryName == null) {
            game.resetFightingCountries();
            return;
        }
        switch (game.getPhase()) {
            case REINFORCE:
                if (game.controlAttacker(countryName) && game.canReinforce(countryName, 1)) {
                    //Ho ancora bonus armies e sono su un mio territorio
                    game.reinforce(countryName, 1);
                    //reinforce chiama notify(), la gui si aggiorna
                } 
                break;              
            case FIGHT:
                if(game.getAttackerCountryName()==null && game.controlAttacker(countryName)){
                    //Devo scegliere l'attaccante, sono su un mio territorio da cui posso attaccare
                    game.setAttackerCountry(countryName);
                    break;   
                }
                if(game.getAttackerCountryName()!=null && game.controlDefender(countryName) ){
                    //Devo scegliere il difensore, sono su un territorio confinante attaccabile
                    game.setDefenderCountry(countryName);
                    break;
                }
                //Sono su un territorio non valido per attaccare nè per difendere
                game.resetFightingCountries();
                break;          
        }
        // se ultimo reinforce metti nella textArea
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        String countryName = getCountryFromClick(e);

        if (countryName == null) {
            // Non sono su alcun territorio
            e.getComponent().setCursor(Cursor.getDefaultCursor());
            return;
        }
        switch (game.getPhase()) {
            case REINFORCE:
                if (game.controlAttacker(countryName) && game.canReinforce(countryName, 1)) {
                    //Ho ancora bonus armies e sono su un mio territorio
                    e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                } else {
                    //Non ho più bonusArmies oppure non sono sul mio territorio
                    e.getComponent().setCursor(Cursor.getDefaultCursor());
                }
                break;              
            case FIGHT:
                if(game.getAttackerCountryName()==null && game.controlAttacker(countryName)){
                    //Devo scegliere l'attaccante, sono su un mio territorio da cui posso attaccare
                    e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    break;   
                }
                if(game.getAttackerCountryName()!=null && game.controlDefender(countryName) ){
                    //Devo scegliere il difensore, sono su un territorio confinante attaccabile
                    e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    break;
                }
                //Sono su un territorio non valido per attaccare nè per difendere
                e.getComponent().setCursor(Cursor.getDefaultCursor());
                break;          
        }
    }

    /*Prende le coordinate del click, trova il Color di quel pixel, lo cerca nella Map_ColorCountry
     *restiuisce la Country relativa al quel Color.
     */
    public String getCountryFromClick(MouseEvent e) {
        int x = e.getPoint().x;
        int y = e.getPoint().y;
        return ColorNameCountry.get(new Color(bufferedImage.getRGB(x, y)));
    }

}
