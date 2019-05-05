package ru.ildarka.pages;

import ru.ildarka.models.Bullet;
import ru.ildarka.models.Monster;

import java.awt.*;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Ildar
 */
class Game extends BaseGameField {
    public Game(final String name)  //запуск конструктора
    {
        super();
        loadRes();
        final Timer timer = new Timer(); //создаем объект класса таймер, чтобы повторять действия через промежуток времени
        TimerTask timerTask = new TimerTask() // объект хранит в себя децствия для таймера 
        {
            @Override
            public void run() {
                Iterator<Bullet> iterator = bullets.iterator();
                Bullet bullet;
                deletedBullets.clear();
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
                for (Monster monster : monsters) {
                    if (monster.getY() > 640) /*если монстр проходит через нижнюю линию, то он перемещается в начало
                                                с новыми рандомными координатами по х, а жизни уменьшаются на 1*/ {
                        monster.randomMonster();
                        life--;
                    }
                    monster.setY(monster.getY() + dy);
                }
                if (life <= 0) //при проигрыше открываем окно рекордов
                {
                    timer.cancel();
                    dispose();
                    String scorestr; //строка, которая содержит очки
                    String player;// строка, которая содержит имя и очки
                    scorestr = Integer.toString(score);//перевод очков из типа инт в строку
                    player = name + " " + scorestr;
                    System.out.println(player);
                    new Note(player);
                    GameOver gameover = new GameOver(scorestr);
                    gameover.setBounds(300, 60, 640, 640);
                    gameover.setVisible(true);
                }
                Iterator<Bullet> bulletIterator=bullets.iterator();
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
        };
        timer.schedule(timerTask, 0, 50);
    }

    public void paint(Graphics gr) {
        Graphics2D g2 = (Graphics2D) gr;
        for (Monster monster : monsters) {
            gr.clearRect(monster.getX(), monster.getY(), monster.getMonstersSizeX(), monster.getMonstersSizeY());
        }
        gr.clearRect(x, y, 40, 60);

        gr.drawImage(im, 0, 0, 640, 640, rootPane);
        gr.drawImage(im_spaceship, x, y, 40, 60, rootPane);
        gr.setColor(Color.ORANGE);
        for (Monster monster : monsters) //рисуем всех монстров
        {
            gr.drawImage(im_ufo, monster.getX(), monster.getY(), 30, 30, rootPane);
        }
        gr.setColor(Color.red);
        Font currentFont = gr.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getSize() * 2F);
        gr.setFont(newFont);
        gr.drawString("Score: " + score, 310, 70); //рисуем табло с жизнями
        for (int i = 0; i < life; i++) {
            gr.drawImage(heart, 320 + 20 * i, 90, 20, 20, rootPane);
        }
        g2.setStroke(new BasicStroke(2));
        for (Bullet bullet : bullets) {
            gr.clearRect(bullet.getX(), bullet.getY() - 20, 2, 20);
            gr.drawLine(bullet.getX(), bullet.getY(), bullet.getX(), bullet.getY() - 20);
        }
    }
}