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
    private String attackerCountryName;
    private String defenderCountryName;
    private final BufferedImage bufferedImage;
    private final Map<Color, String> ColorNameCountry;

    public LabelMapListener(BufferedImage bufferedImage, Map<Color, String> ColorNameCountry, Game game) {
        this.game = game;
        this.bufferedImage = bufferedImage;
        this.ColorNameCountry = ColorNameCountry;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        String Country = getCountryFromClick(e);
        if (Country != null) {
            JOptionPane.showMessageDialog(null, Country);
        }
        // se ultimo reinforce metti nella textArea
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        String countryName = getCountryFromClick(e);

        if (countryName == null) {
            e.getComponent().setCursor(Cursor.getDefaultCursor());
            return;
        }
        
        switch (game.getPhase()) {
            case REINFORCE:
                if (game.controlAttacker(countryName) && game.canReinforce(countryName, 1)) {
                    e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                } else {
                    e.getComponent().setCursor(Cursor.getDefaultCursor());
                }

                break;
                
            case FIGHT:
                
                if(attackerCountryName==null && game.controlAttacker(countryName)){

                    e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    break;   
                }
                if(attackerCountryName!=null && game.controlDefender(attackerCountryName, countryName) ){
                    
                    e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    break;
                }
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
