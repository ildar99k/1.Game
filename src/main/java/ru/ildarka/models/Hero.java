package ru.ildarka.models;

import java.awt.*;

public class Hero implements HasImage{
    private int x;
    private final int y;
    Image ship;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }


    public Hero() {
        setX(300);
        y = 520;
        loadImage();
    }

    @Override
    public Image getImage() {
        return ship;
    }

    @Override
    public void loadImage() {
        ship=DataRepository.getDataRepository().getIm_spaceship();
    }
}
