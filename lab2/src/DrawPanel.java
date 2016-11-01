
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author karol
 */
public class DrawPanel extends JPanel{
    
    private BufferedImage myPicture = null;
    
    DrawPanel(BufferedImage obraz)
    {
        myPicture = obraz;
    }
    
    @Override
    public void paintComponents(Graphics g)
    {
        super.paintComponents(g);
        drawPicture(myPicture);
    }
        
    public void drawPicture(BufferedImage obraz)
    {
        Graphics g = this.getGraphics();
        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(obraz, 0, 0, this);
    }
     
}
