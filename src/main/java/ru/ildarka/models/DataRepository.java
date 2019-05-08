package ru.ildarka.models;

import javax.swing.*;
import java.awt.*;

public class DataRepository {
    private static DataRepository dataRepository=new DataRepository();
    Image im;
    Image im_spaceship;
    Image im_ufo;
    Image heart;
   private void loadRes() {
        im = new ImageIcon(getClass().getResource("/space.jpg")).getImage();
        im_spaceship = new ImageIcon(getClass().getResource("/ship.png")).getImage();
        im_ufo = new ImageIcon(getClass().getResource("/ufo.png")).getImage();
        heart = new ImageIcon(getClass().getResource("/heart.png")).getImage();
   }

    public Image getIm() {
        return im;
    }

    public Image getIm_spaceship() {
        return im_spaceship;
    }

    public Image getIm_ufo() {
        return im_ufo;
    }

    public Image getHeart() {
        return heart;
    }

    private DataRepository() {
       loadRes();
    }

    public static DataRepository getDataRepository() {
        return dataRepository;
    }
}

