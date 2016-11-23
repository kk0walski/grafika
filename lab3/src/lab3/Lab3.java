/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab3;

import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.*;

/**
 *
 * @author karol
 */
public class Lab3{
    
    private ArrayList<Polygon> lista = new ArrayList<Polygon>();
    private AffineTransform transform = new AffineTransform();
    private int size;
    private int[] xPol;
    private int[] yPol;

    
    public Lab3(){
        try {
            readFile("/home/karol/NetBeansProjects/grafika/lab3/src/lab3/figury.txt");
        } catch (IOException ex) {
            Logger.getLogger(Lab3.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void readFile(String plik) throws FileNotFoundException, IOException{
        File file = new File(plik);
        Scanner scan = new Scanner(file);
        while(scan.hasNext()){
            if(size == 0 && scan.hasNextInt()){
                size = scan.nextInt();
                xPol = new int[size];
                yPol = new int[size];
                for(int i = 0; i < size; i++)
                {
                    xPol[i] = scan.nextInt();
                }
                for(int i = 0; i < size; i++)
                {
                    yPol[i] = scan.nextInt();
                }
                lista.add(new Polygon(xPol,yPol,size));
                size = 0;
                xPol = null;
                yPol = null;
            }else if(scan.hasNextDouble()){
                transform.concatenate(new AffineTransform(scan.nextDouble(),
                                                scan.nextDouble(),
                                                scan.nextDouble(),
                                                scan.nextDouble(),
                                                scan.nextDouble(),
                                                scan.nextDouble()));
            }else{
                scan.nextLine();
                }
            }
        }
    
        public void draw(){
            BufferedImage image = new BufferedImage(1000,1000,BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = image.createGraphics();
            g2d.transform(transform);
            for(Polygon p : lista){
                g2d.draw(p);
            }
            g2d.dispose();
            File file = new File("obraz.png");
        try {
            ImageIO.write(image, "png", file);
        } catch (IOException ex) {
            Logger.getLogger(Lab3.class.getName()).log(Level.SEVERE, null, ex);
        }
        }

	public static void main(String[] args) {
        Lab3 wynik = new Lab3();
        wynik.draw();
    }
}