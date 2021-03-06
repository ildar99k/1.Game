package ru.ildarka.pages;

import ru.ildarka.models.Bullet;
import ru.ildarka.models.DataRepository;
import ru.ildarka.models.Monster;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.DataFormatException;

/**
 * @author Ildar
 */
class Game extends BaseGameField {
    public Game(final String name)  //запуск конструктора
    {
        super();
        final Timer timer = new Timer(); //создаем объект класса таймер, чтобы повторять действия через промежуток времени
        TimerTask timerTask = new TimerTask() // объект хранит в себя децствия для таймера 
        {
            @Override
            public void run() {
                Bullet.moveBullets(bullets);
                checkForExitOfBounds();
                if (life <= 0) //при проигрыше открываем окно рекордов
                {
                    timer.cancel();
                    dispose();
                    String scorestr = Integer.toString(score);//перевод очков из типа инт в строку
                    String player = name + " " + scorestr;
                    new Note(player);
                    GameOver gameover = new GameOver(scorestr);
                    gameover.setBounds(300, 60, 640, 640);
                    gameover.setVisible(true);
                }
                checkForStrike();
            }
        };

        timer.schedule(timerTask, 0, 50);
    }

    public void paint(Graphics gr) {
        Graphics2D g2 = (Graphics2D) gr;
        gr.clearRect(hero.getX(), hero.getY(), 40, 60);
        gr.drawImage(DataRepository.getDataRepository().getIm(), 0, 0, 640, 640, rootPane);
        gr.drawImage(hero.getImage(), hero.getX(), hero.getY(), 40, 60, rootPane);
        gr.setColor(Color.ORANGE);
        for (Monster monster : monsters) //рисуем всех монстров
        {
            gr.clearRect(monster.getX(), monster.getY(), monster.getMonstersSizeX(), monster.getMonstersSizeY());
            gr.drawImage(monster.getImage(), monster.getX(), monster.getY(), 30, 30, rootPane);
        }
        gr.setColor(Color.red);
        Font currentFont = gr.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getSize() * 2F);
        gr.setFont(newFont);
        gr.drawString("Score: " + score, 310, 70); //рисуем табло с жизнями
        for (int i = 0; i < life; i++) {
            gr.drawImage(DataRepository.getDataRepository().getHeart(), 320 + 20 * i, 90, 20, 20, rootPane);
        }
        g2.setStroke(new BasicStroke(2));
        for (Bullet bullet : bullets) {
            gr.clearRect(bullet.getX(), bullet.getY() - 20, 2, 20);
            gr.drawLine(bullet.getX(), bullet.getY(), bullet.getX(), bullet.getY() - 20);
        }
    }
}