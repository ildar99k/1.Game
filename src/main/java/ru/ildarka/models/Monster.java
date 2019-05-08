package ru.ildarka.models;

import java.awt.*;

public class Monster implements HasImage {
    private int x;
    private int y;
    private Image ufo;
    public void randomMonster(){
        this.setX((int) (640*Math.random()));
        this.setY(-100);
    }

    public Monster() {
        loadImage();
        this.setX((int) (610*Math.random()));
        this.setY((int) (-1000*Math.random()));
    }

    public int getX() {
        return x;
    }

    private void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getMonstersSizeX() {
        return 30;
    }

    public int getMonstersSizeY() {
        return 30;
    }

    @Override
    public Image getImage() {
        return ufo;
    }

    @Override
    public void loadImage() {
        ufo=DataRepository.getDataRepository().getIm_ufo();
    }
}
