/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 /*
 CHOISE
    1) Line
 */
package paint1_0;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author karol
 */
public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener {

    private Line2D line = null;
    private Rectangle2D rectangle = null;
    private Ellipse2D ellipse = null;

    private BufferedImage backgroundImg;
    private double scale_width;
    private double scale_height;
    private int new_width;
    private int new_height;
    public ArrayList<Shapes> lista = new ArrayList<Shapes>();
    private Point startPoint;
    private boolean dragCondition = false;

    public DrawPanel(BufferedImage image) {
        backgroundImg = image;
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int original_width = backgroundImg.getWidth();
        int original_height = backgroundImg.getHeight();
        int bound_width = this.getWidth();
        int bound_height = this.getHeight();
        new_width = original_width;
        new_height = original_height;

        // first check if we need to scale width
        if (original_width > bound_width) {
            //scale width to fit
            new_width = bound_width;
            //scale height to maintain aspect ratio
            new_height = (bound_width * original_height) / original_width;
        }

        // then check if we need to scale even with the new height
        if (new_height > bound_height) {
            //scale height to fit instead
            new_height = bound_height;
            //scale width to maintain aspect ratio
            new_width = (bound_height * original_width) / original_height;
        }
        scale_width = ((double) new_width) / original_width;
        scale_height = ((double) new_height) / original_height;
        g.drawImage(backgroundImg, 0, 0, new_width, new_height, this);
        Graphics2D g2d = (Graphics2D) g;
        if (line != null) {
            g2d.draw(line);
        }
        if (rectangle != null) {
            g2d.draw(rectangle);
        }
        if (ellipse != null) {
            g2d.draw(ellipse);
        }
        for (Shapes a : lista) {
            g2d.setStroke(new BasicStroke(a.getThickness()));
            g2d.draw(a.getShape(scale_width, scale_height));
        }
    }

    private void addLine(Line2D line) {
        Line linia = new Line(line.getX1() / scale_width, line.getY1() / scale_height, line.getX2() / scale_width, line.getY2() / scale_height);
        lista.add(linia);
        this.toList(linia);
        this.repaint();
    }

    private void addRectangle(Rectangle2D rect) {
        Square kwadrat = new Square(rect.getX() / scale_width,
                rect.getY() / scale_height,
                (rect.getX() + rect.getWidth()) / scale_width,
                (rect.getY() + rect.getHeight()) / scale_height
        );
        lista.add(kwadrat);
        this.toList(kwadrat);
        this.repaint();
    }

    private void addEllipse(Ellipse2D elips) {
        Ellipse elipsa = new Ellipse(elips.getX() / scale_width,
                elips.getY() / scale_height,
                (elips.getX() + elips.getWidth()) / scale_width,
                (elips.getY() + elips.getHeight()) / scale_height
        ); 
        lista.add(elipsa);
        this.toList(elipsa);
        this.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (Paint1_0.choose) {
            case 1:
                line = new Line2D.Double(e.getPoint(), e.getPoint());
                break;
            case 2:
                rectangle = new Rectangle2D.Double(e.getX(), e.getY(), 0, 0);
                startPoint = e.getPoint();
                break;
            case 3:
                ellipse = new Ellipse2D.Double(e.getX(), e.getY(), 0, 0);
                startPoint = e.getPoint();
                break;
            default:
                if(lista.get(Paint1_0.myIndex).contains(scale_width, scale_height, e.getPoint())){
                    dragCondition = true;
                }else dragCondition = false;
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (Paint1_0.choose) {
            case 1:
                addLine(line);
                line = null;
                break;
            case 2:
                addRectangle(rectangle);
                rectangle = null;
                break;
            case 3:
                addEllipse(ellipse);
                ellipse = null;
                break;
            default:
                if(dragCondition){
                    lista.get(Paint1_0.myIndex).changePosition(scale_width, scale_height, e.getX(), e.getY());
                    dragCondition = false;
                    this.repaint();
                }
                break;
        }
        this.repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {

        switch (Paint1_0.choose) {
            case 1:
                if (!line.getP1().equals(e.getPoint())) {
                    line.setLine(line.getP1(), e.getPoint());
                    this.repaint();
                }
                break;
            case 2:
                if (!e.getPoint().equals(startPoint)) {
                    int x = (int) Math.min(e.getX(), startPoint.getX());
                    int y = (int) Math.min(e.getY(), startPoint.getY());
                    int width = (int) Math.abs(startPoint.getX() - e.getX());
                    int height = (int) Math.abs(startPoint.getY() - e.getY());
                    rectangle.setRect(x, y, width, height);
                    this.repaint();
                }
                break;
            case 3:
                if (!e.getPoint().equals(startPoint)) {
                    int x = (int) Math.min(e.getX(), startPoint.getX());
                    int y = (int) Math.min(e.getY(), startPoint.getY());
                    int width = (int) Math.abs(startPoint.getX() - e.getX());
                    int height = (int) Math.abs(startPoint.getY() - e.getY());
                    ellipse = new Ellipse2D.Double(x, y, width, height);
                    this.repaint();
                }
                break;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    public void save() {
        XStream xstream = new XStream(new StaxDriver());
        String xml = xstream.toXML(lista);
        try (FileWriter writer = new FileWriter("Test.xml")) {

            writer.write(xml);
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(DrawPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void read(){
        XStream xstream = new XStream(new StaxDriver());
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("Test.xml"));
            String test = bufferedReader.readLine();
            lista = (ArrayList<Shapes>)xstream.fromXML(test);
            for(Shapes a : lista){
                this.toList(a);
            }
            this.repaint();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DrawPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DrawPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void toList(Shapes shape){
        Paint1_0.model.addElement(shape.toString());
    }
    public void fromList(int index){
        lista.remove(index);
    }
}
