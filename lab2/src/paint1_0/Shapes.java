/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint1_0;

import java.awt.Point;
import java.awt.Shape;

/**
 *
 * @author karol
 */
public interface Shapes {
    public Shape getShape(double scale_width, double scale_height);
    public int getThickness();
    public void increaseThickness();
    public void thickSetNormal();
    public String toString();
    public void changePosition(double scale_width, double scale_height, int x, int y);
    public boolean contains(double scale_width, double scale_height, Point p);
}
