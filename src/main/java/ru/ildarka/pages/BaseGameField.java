package ru.ildarka.pages;

import ru.ildarka.models.Bullet;
import ru.ildarka.models.Hero;
import ru.ildarka.models.Monster;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;

abstract class BaseGameField extends JFrame implements KeyListener {
    private final ArrayList<Bullet> deletedBullets = new ArrayList<>();
    protected Hero hero=new Hero();
    static int life = 3;
    private static final int dx = 7;
    private static int dy = 2;
    static int score;
    private static final int level2_speed = 3;
    private static final int level3_speed = 4; /*x и у отвечает за местонахождение персонажа, life
                                                                   за жизни персонажа,n-очки игрока*/
    final ArrayList<Bullet> bullets = new ArrayList<>(); /* динамический массив для хранения
                                                                         координат пули*/
    final ArrayList<Monster> monsters = new ArrayList<>();


    BaseGameField() throws HeadlessException {
        setBounds(300, 60, 640, 640); //создаем окно
        setVisible(true); //делаем его видимым
        setTitle("Cosmos"); // ставим название
        setResizable(false); //делаем его неизменяемым по размерам
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //устанавливаем закрытие программы при нажатии на крестик
        addKeyListener(this); // заставляем слушать клавиатуру
        createMonsters();
    }

    private void createMonsters() {
        for (int i = 0; i < 5; i++) {
            monsters.add(new Monster());
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) /*при нажатии клавиш влево и вправо перемещаем
        персонажа влево и вправо*/ {
        if ((hero.getX() <= 590) && (e.getKeyCode() == KeyEvent.VK_RIGHT)) {
            hero.setX(hero.getX() + dx);
        }
        if ((hero.getX() >= 10) && (e.getKeyCode() == KeyEvent.VK_LEFT)) {
            hero.setX(hero.getX()-dx);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) //запуск пули при нажатии на пробел
    {
        if (e.getKeyCode() == 32) {
            bullets.add(new Bullet(hero.getX()+20, hero.getY()));
        }
    }


    void checkForExitOfBounds(){
        for (Monster monster : monsters) {
            if (monster.getY() > 640) /*если монстр проходит через нижнюю линию, то он перемещается в начало
                                                с новыми рандомными координатами по х, а жизни уменьшаются на 1*/ {
                monster.randomMonster();
                life--;
            }
            monster.setY(monster.getY() + dy);
        }
    }
    void checkForStrike(){
        Iterator<Bullet> bulletIterator = bullets.iterator();
        Bullet bullet;
        deletedBullets.clear();
        while (bulletIterator.hasNext()) {
            bullet = bulletIterator.next();
            for (Monster monster : monsters)//проверка на попадание
            {
                if ((monster.getY() < (bullet.getY() - 20)) && (monster.getY() + 30 > (bullet.getY() - 20))) {
                    if (((monster.getX() + 30) > bullet.getX()) && (monster.getX() < bullet.getX())) {
                                    /*идет проверка совпадения координат пули и монстра, числа 20 и 30 присутствуют
                                    в проверке, т.к монстры имеют такой размер*/
                        monster.randomMonster();
                        score = score + 1;
                        if (score > 5) {
                            dy = level2_speed;
                            if (score > 10) {
                                dy = level3_speed;
                            }
                        } //увеличиваем очки при попадании
                        deletedBullets.add(bullet);
                    }
                }
            }
        }
        bullets.remove(deletedBullets);
        bullets.trimToSize();
        repaint(); //отправляем новые координаты на перерисовку
    }

}
