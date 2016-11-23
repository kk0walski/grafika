/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab3b;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author karol
 */
public class Lab3b {

    /**
     * @param args the command line arguments
     */
    
    private BufferedImage obraz = null;
    private BufferedImage canvas;
    private AffineTransform transform = new AffineTransform();
    private AffineTransform odwrotna;
    private int x_res = 0;
    private int y_res = 0;
    
    public Lab3b(){
        readPicture();
        try {
            Scanner in = new Scanner(System.in);
            System.out.println("Podaj sciezke do pliku");
            readFile(in.nextLine());
            in.close();
        } catch (IOException ex) {
            Logger.getLogger(Lab3b.class.getName()).log(Level.SEVERE, null, ex);
        }
        canvas = new BufferedImage(x_res, y_res, BufferedImage.TYPE_INT_RGB);
        try {
            odwrotna = transform.createInverse();
        } catch (NoninvertibleTransformException ex) {
            Logger.getLogger(Lab3b.class.getName()).log(Level.SEVERE, null, ex);
        }
        drawImage();
        try {
            savePicture();
        } catch (IOException ex) {
            Logger.getLogger(Lab3b.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        Lab3b test = new Lab3b();
    }
    
    private void readPicture(){
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Please choose an image...");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPEG", "jpeg", "jpg", "png", "bmp", "gif");
        fc.addChoosableFileFilter(filter);

        // You should use the parent component instead of null
        // but it was impossible to tell from the code snippet what that was.
        if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fc.getSelectedFile();
            try {
                obraz = ImageIO.read(selectedFile);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
     private void readFile(String plik) throws FileNotFoundException, IOException{
        File file = new File(plik);
        Scanner scan = new Scanner(file);
        while(scan.hasNext()){
            if(scan.hasNextInt() && x_res == 0 && y_res == 0){
                x_res = scan.nextInt();
                y_res = scan.nextInt();
            }
            else if(scan.hasNextDouble()){
                transform.concatenate(new AffineTransform(scan.nextDouble(),
                                                scan.nextDouble(),
                                                scan.nextDouble(),
                                                scan.nextDouble(),
                                                scan.nextDouble(),
                                                scan.nextDouble()));
            }
            else scan.nextLine();
            
        }
     }
     private void drawImage(){
         for(int i = 0; i < y_res; i++){
             for(int j = 0; j < x_res; j++){
                 Point2D dest = new Point2D.Double(0.0,0.0);
                 odwrotna.transform(new Point2D.Double((double)j,(double)i), dest);
                 if(dest.getX() >= 0 && dest.getY() >= 0 && dest.getX() < obraz.getWidth() && dest.getY() < obraz.getHeight()){
                     int myX = (int)dest.getX();
                     int myY = (int)dest.getY();
                     Color colorA = getColor(myX, myY);
                     Color colorB = getColor(myX, myY+1);
                     Color colorC = getColor(myX+1, myY);
                     Color colorD = getColor(myX+1, myY+1);
                     double alfa = dest.getX() - myX;
                     double beta = dest.getY() - myY;
                     Color colorAB = new Color(
                             ((int)((1-alfa)*colorA.getRed() + alfa*colorB.getRed()))%255,
                             ((int)((1-alfa)*colorA.getGreen() + alfa*colorB.getGreen()))%255,
                             ((int)((1-alfa)*colorA.getBlue() + alfa*colorB.getBlue()))%255);
                     Color colorCD = new Color(
                             ((int)((1-alfa)*colorC.getRed() + alfa*colorD.getRed()))%255,
                             ((int)((1-alfa)*colorC.getGreen() + alfa*colorD.getGreen()))%255,
                             ((int)((1-alfa)*colorC.getBlue() + alfa*colorD.getBlue()))%255);
                     Color colorX = new Color(
                             ((int)(beta*colorCD.getRed() + (1 - beta)*colorAB.getRed()))%255,
                             ((int)(beta*colorCD.getGreen()+ (1 - beta)*colorAB.getGreen()))%255,
                             ((int)(beta*colorCD.getBlue()+ (1 - beta)*colorAB.getBlue()))%255);
                     canvas.setRGB(j, i, colorX.getRGB());
                 }
             }
         }
     }
     private Color getColor(int x, int y){
         if(x < 0 || x >= obraz.getWidth() || y < 0 || y >= obraz.getHeight())
         {
             return Color.WHITE;
         }else
         {
             return (new Color(obraz.getRGB(x, y)));
         }
     }
     private void savePicture() throws IOException{
         File file = new File("Obraz.jpg");
         ImageIO.write(canvas, "jpg", file);
     }
}
