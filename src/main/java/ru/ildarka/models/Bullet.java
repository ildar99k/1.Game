package ru.ildarka.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Bullet {
    private int x;
    private int y;

    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    private void setY(int y) {
        this.y = y;
    }
    public static void moveBullets(ArrayList<Bullet> bullets){
        List<Bullet> deletedBullets=new ArrayList<>();
        Iterator<Bullet> iterator = bullets.iterator();
        Bullet bullet;
        while (iterator.hasNext()) {
            bullet = iterator.next();
            bullet.setY(bullet.getY() - 10); //при каждой итерации таймера координата всех существующих пуль изменяется на 10 ед.
            if (bullet.getY() < 0) //если пуля вылетает за верхнюю границу, то память от нее освобождается
            {
                deletedBullets.add(bullet);
            }
        }
        bullets.remove(deletedBullets);
        bullets.trimToSize();
    }
}
