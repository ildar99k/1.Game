package ru.ildarka.models;

public class Monster {
    private int x;
    private int y;

    public void randomMonster(){
        this.setX((int) (640*Math.random()));
        this.setY(-100);
    }

    public Monster() {
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
        int monstersSizeX = 30;
        return monstersSizeX;
    }

    public int getMonstersSizeY() {
        int monstersSizeY = 30;
        return monstersSizeY;
    }
}
