package ru.ildarka.pages;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Note {
    public Note(String player){ //принимает на вход строку, содержающую имя и рекорд
        int i,k,a=0,b=0;
        String[] string=new String[6]; //массив строк, который будет хранить все имена и все рекорды
        String stscore= ""; // строка для счета от всего остального
        int[] score=new int[6]; // массив отделенных от имен рекордов
        try {
            FileReader fr = new FileReader(String.valueOf(getClass().getResource("/Record.txt")));//открываем файл для считывания
            Scanner scan = new Scanner(fr); //подключаем к нему сканер
            for  (i=0;i<5;i++)
            { //считываем все строки из файла
                string[i]=scan.nextLine();
            }
            string[5]=player; //добавляем последнюю строку из законченной игры
            for (i=0;i<6;i++)
            { //отделение рекорда от имени
                for (k=0;(k<string[i].length());k++)
                {
                    if ((string[i].charAt(k)==' ')||(k==(string[i].length()-1)))
                    { 
                        a=b;//отделение строки от имени
                        b=k;
                    }
                }
                try 
                {
                    stscore=string[i].substring(a+1,b+1); // выделяет подстроку диапозон от a+1 до b+1(score)
                } 
                catch (NumberFormatException ignored)
                {
                }
                try 
                {
                    score[i]=Integer.parseInt(stscore); //превращение строки рекорда в число инт
                } 
                catch (NumberFormatException ignored)
                {
                }
            }
            int Index_of_Max; //для сортировки запоминаем индекс максимального числа
            int swapscore; //для нужд сортировки
            String swapstring;//--
            for (i=0;i<5;i++)
            { //сортировка рекордов по убыванию
                Index_of_Max=i;
                for (k=i;k<6;k++){
                    if (score[k]>score[Index_of_Max])
                    {
                        Index_of_Max=k;
                    }
                }
                swapscore=score[i]; //меняем рекорды местами
                score[i]=score[Index_of_Max];
                score[Index_of_Max]=swapscore;
                swapstring=string[i]; //меняем строки местами
                string[i]=string[Index_of_Max];
                string[Index_of_Max]=swapstring;
            }
            try 
            {
                fr.close();
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(Note.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try (FileWriter fw = new FileWriter(String.valueOf(getClass().getResource("/Record.txt")),false)) //запись рекордов в файл
        {
            for (i=0;i<5;i++) {
                string[i] = string[i] + "\r\n";
                fw.write(string[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
