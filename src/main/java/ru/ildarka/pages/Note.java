package ru.ildarka.pages;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


class Note {
    public Note(String player) { //принимает на вход строку, содержающую имя и рекорд
        int i, k, a = 0, b = 0;
        List<String> string = new ArrayList<>(); //массив строк, который будет хранить все имена и все рекорды
        String stscore = ""; // строка для счета от всего остального
        int[] score = new int[6]; // массив отделенных от имен рекордов
        try {
            File file = new File(getClass().getResource("/Record.txt").toURI());
            FileReader fr = new FileReader(file);//открываем файл для считывания
            Scanner scan = new Scanner(fr); //подключаем к нему сканер
            for (i = 0; i < 5; i++) { //считываем все строки из файла
                if (scan.hasNext())
                    string.add(scan.nextLine());
            }
            string.add(player); //добавляем последнюю строку из законченной игры
            for (i = 0; i < 6; i++) { //отделение рекорда от имени
                for (k = 0; k < string.size(); k++) {
                    if ((string.get(k).charAt(k) == ' ') || (k == (string.get(k).length() - 1))) {
                        a = b;//отделение строки от имени
                        b = k;
                    }
                }
                try {
                    stscore = string.get(i).substring(a + 1, b + 1); // выделяет подстроку диапозон от a+1 до b+1(score)
                } catch (NumberFormatException ignored) {
                }
                try {
                    score[i] = Integer.parseInt(stscore); //превращение строки рекорда в число инт
                } catch (NumberFormatException ignored) {
                }
            }
            int Index_of_Max; //для сортировки запоминаем индекс максимального числа
            int swapscore; //для нужд сортировки
            String swapstring;//--
            for (i = 0; i < 5; i++) { //сортировка рекордов по убыванию
                Index_of_Max = i;
                for (k = i; k < 6; k++) {
                    if (score[k] > score[Index_of_Max]) {
                        Index_of_Max = k;
                    }
                }
                swapscore = score[i]; //меняем рекорды местами
                score[i] = score[Index_of_Max];
                score[Index_of_Max] = swapscore;
                swapstring = string.get(i); //меняем строки местами
                string.set(i, string.get(Index_of_Max));
                string.set(Index_of_Max, swapstring);
            }
            try {
                fr.close();
            } catch (IOException ex) {
                Logger.getLogger(Note.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException | URISyntaxException e) {
            e.printStackTrace();
        }
        try {
            File file = new File(getClass().getResource("/Record.txt").toURI());
            FileWriter fw = new FileWriter(file, false); //запись рекордов в файл
            for (String str : string) {
                str = str + "\r\n";
                fw.write(str);
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
