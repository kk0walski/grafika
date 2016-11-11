/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint1_0;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author karol
 */
public class Square implements Shapes{
    double x1;
    double y1;
    double x2;
    double y2;
    int thickness = 1;
    
    public Square(double x, double y, double x2, double y2){
        this.x1 = x;
        this.y1 = y;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public Shape getShape(double scale_width, double scale_height) {
        double sX = x1*scale_width;
        double sX2 = x2*scale_width;
        double sY  =y1*scale_height;
        double sY2 = y2*scale_height;
        return new Rectangle2D.Double(Math.min(sX, sX2), Math.min(sY, sY2), Math.abs(sX - sX2), Math.abs(sY - sY2));
    }

    @Override
    public int getThickness() {
        return thickness;
    }

    @Override
    public void increaseThickness() {
        thickness++;
    }

    @Override
    public void thickSetNormal() {
        thickness = 1;
    }
    @Override
    public String toString(){
        return String.format("Square(%.2f %.2f %.2f %.2f)", x1, y1, x2, y2);
    }
    
    @Override
    public boolean contains(double scale_width, double scale_height, Point p) {
        double X = p.getX()/scale_width;
        double Y = p.getY()/scale_height;
        return ((X < x1 + 10 && X > x1 - 10) && (Y < y1 + 10 && Y > y1 - 10)) ||
               ((X < x1 + 10 && X > x1 - 10) && (Y < y2 + 10 && Y > y2 - 10)) ||
               ((X < x2 + 10 && X > x2 - 10) && (Y < y1 + 10 && Y > y1 - 10)) ||
               ((X < x2 + 10 && X > x2 - 10) && (Y < y2 + 10 && Y > y2 - 10));
                
    }

    @Override
    public void changePosition(double scale_width, double scale_height, int x, int y) {
        double sX = ((double)x)/scale_width;
        double sY = ((double)y)/scale_height;
        this.x1 = this.x1 + (sX - this.x1);
        this.x2 = this.x2 + (sX - this.x2);
        this.y1 = this.y1 + (sY - this.y1);
        this.y2 = this.y2 + (sY - this.y2);
    }
}
