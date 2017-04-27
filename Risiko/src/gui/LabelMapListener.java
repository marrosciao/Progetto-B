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
    private Game    game;
    private String  attackerCountryName;
    private String  defenderCountryName;
    private final BufferedImage       bufferedImage;
    private final Map<Color, String>  ColorNameCountry;

    public LabelMapListener(BufferedImage       bufferedImage, Map<Color, String> ColorNameCountry, Game game) {
        this.game = game;
        this.bufferedImage     = bufferedImage;
        this.ColorNameCountry = ColorNameCountry;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        String Country = getCountryFromClick(e);
        if(Country != null) {
            JOptionPane.showMessageDialog(null,Country);
        }
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
        if(getCountryFromClick(e) == null) 
            e.getComponent().setCursor(Cursor.getDefaultCursor());
        else 
            e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));      
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
