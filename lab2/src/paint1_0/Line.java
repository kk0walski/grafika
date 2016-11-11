/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint1_0;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Line2D;

/**
 *
 * @author karol
 */
public class Line implements Shapes{
    
    double x1;
    double y1;
    double x2;
    double y2;
    int thickness = 2;
    
    public Line(double x1, double y1, double x2, double y2)
    {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
    
    public Line(Point p1 , Point p2){
        this.x1 = p1.getX();
        this.y1 = p1.getY();
        this.x2 = p2.getX();
        this.y2 = p2.getY();
    }

    @Override
    public Shape getShape(double scale_width, double scale_height) {
        return new Line2D.Double(x1*scale_width, y1*scale_height, x2*scale_width, y2*scale_height);
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
        return String.format("Line(%.2f %.2f %.2f %.2f)", x1, y1, x2, y2);
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
