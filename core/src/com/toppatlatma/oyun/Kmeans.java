package com.toppatlatma.oyun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Random;
import java.lang.Math;

public class Kmeans {
    static int say = 0;
    static float sayi = 0;
    static String hexString;
    static float[][] hexkodlar = new float[145][3];
    public static int[] elemansayi = new int[5];

    // http://introcs.cs.princeton.edu/java/31datatype/Hex2Decimal.java.html
    // adresinden alınmıştır.
    public static float hexCevir(String s) {
        String tipler = "0123456789ABCDEF";
        s = s.toUpperCase();
        float deger = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int d = tipler.indexOf(c);
            deger = 16 * deger + d;
        }
        return deger;
    }


    public static void DosyaOku() {
        File file = new File("C:\\Users\\gokha\\Desktop\\TopPatlatmaOyunu\\android\\assets\\renkhex.txt");

        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNext()) { // kelime kelime okur. boşluklar silinir.

                hexString = scanner.next();
                sayi = hexCevir(hexString);
                hexkodlar[say][0] = sayi;

                hexString = scanner.next();
                sayi = hexCevir(hexString);
                hexkodlar[say][1] = sayi;

                hexString = scanner.next();
                sayi = hexCevir(hexString);
                hexkodlar[say][2] = sayi;

                System.out.println((say + 1) + ". eleman = x : " + hexkodlar[say][0] + " y: " + hexkodlar[say][1] + " z : " + hexkodlar[say][2]);
                say++;
            }

            scanner.close();
        } catch (FileNotFoundException e) {

            e.printStackTrace();
            System.out.println("Dosya Bulunamadı..");
        }

    }

    public static float[][][] merkez(int kumepar) {

        int lineNumber = 0, k, i = 0, j = 0;
        try {
            BufferedReader reader = null;
            reader = new BufferedReader(new FileReader("C:\\Users\\gokha\\Desktop\\TopPatlatmaOyunu\\android\\assets\\renkhex.txt"));
            String satir = reader.readLine();
            while (satir != null) {
                if (satir.length() > 0) {
                    lineNumber++;
                }
                satir = reader.readLine();
            }
        } catch (final IOException e) {
        }
        System.out.println("Verilen Dökümandaki Satır Sayısı: " + lineNumber);


        // merkezler hexkodların arasından rastgele seçiliyor
        Random r = new Random();
        int merkez[] = new int[kumepar];
        /*for (k = 0; k < kümepar; k++) {
            merkez[k] = r.nextInt(142);
            System.out.println(merkez[k]);
        }*/
        int sayi = 10;

        for (i = 0; i < kumepar; i++)
            merkez[i] = sayi + 10 * i;


        float[][] dizi = new float[5][3];

        //dizi----->merkez
        for (i = 0; i < kumepar; i++) {
            for (j = 0; j < 3; j++) {
                dizi[i][j] = hexkodlar[merkez[i]][j];
            }
            System.out.println((i + 1) + ". merkez = x : " + dizi[i][0] + " y: " + dizi[i][1] + " z : " + dizi[i][2]);
        }

        float[][][] kumeler = new float[5][145][3];

//        for (int küme = 0; küme < kümepar; küme++) {
//
//            for (j = 0; j < 3; j++)
//                kümeler[küme][0][j] = dizi[küme][j];
//        }

        for (i = 0; i < kumepar; i++)
            elemansayi[i] = 0;  ////////degisti

        //// 0 değişti elemansayı[küme]


        //0.küme nin elemansayısı  elemansay[0]
        //1.küme nin elemansayısı  elemansay[1]
        //:
        //:
        //4.küme nin elemansayısı  elemansay[4]

        float[][] uzaklik = new float[142][5];
        float[] uzak = new float[5];
        float min;
        int t = 0;
        int kumesıra = 0;

        for (i = 0; i < 142; i++) {
            //min[i] = uzaklık[i][0];
            for (t = 0; t < kumepar; t++) {

                uzak[t] = (float) Math.sqrt((Math.pow(Math.abs(hexkodlar[i][0] - dizi[t][0]), 2) + Math.pow(Math.abs(hexkodlar[i][1] - dizi[t][1]), 2) + Math.pow(Math.abs(hexkodlar[i][2] - dizi[t][2]), 2)));

            }

            min = uzak[0];
            kumesıra = 0;

            for (t = 1; t < kumepar; t++) {

                if (min > uzak[t]) {
                    min = uzak[t];
                    kumesıra = t;
                }
                //System.out.println((i + 1) + ".uz" + min);
            }

            //System.out.println((i + 1) + ". " + kümesıra + ".sıradaki uzaklık :" + uzaklık[i][0]);
            // System.out.println((i + 1) + ". eleman " + kümesıra + ".kümede");
            for (int c = 0; c < 3; c++) {
                kumeler[kumesıra][elemansayi[kumesıra]][c] = hexkodlar[i][c];
            }

            elemansayi[kumesıra]++;

        }

        for (int c = 0; c < kumepar; c++) {
            elemansayi[c]--;

            for (int a = 0; a < elemansayi[c]; a++) {
                System.out.println(c + ". küme ----  x : " + kumeler[c][a][0] + " y:" + kumeler[c][a][1] + " z:" + kumeler[c][a][2]);
            }
        }

        for (t = 0; t < kumepar; t++)
            System.out.println(elemansayi[t]);


    /*------------------------------------------------------------------------------------------*/
    /*------------------------------------------------------------------------------------------*/

        //////yeni merkezler ve yeniden sınıflandırma kararlı hale gelene kadar/////
        boolean durum = true;

        while (durum) {

            durum = false;
            // merkezler hexkodların arasından rastgele seçiliyor

            /*for (k = 0; k < kümepar; k++) {
                merkez[k] = r.nextInt(elemansayı[k]);
                System.out.println("yeni küme merkezi :" + merkez[k]);
            }*/

            int xtoplam = 0, ytoplam = 0, ztoplam = 0;
            for (i = 0; i < kumepar; i++) {

                xtoplam = 0;
                ytoplam = 0;
                ztoplam = 0;

                for (j = 0; j < elemansayi[i] + 1; j++) {
                    xtoplam += kumeler[i][j][0];
                    ytoplam += kumeler[i][j][1];
                    ztoplam += kumeler[i][j][2];
                }

                dizi[i][0] = xtoplam / elemansayi[i];
                dizi[i][1] = ytoplam / elemansayi[i];
                dizi[i][2] = ztoplam / elemansayi[i];

            }


            for (int x = 0; x < kumepar; x++) {
                for (i = 0; i < elemansayi[x] + 1; i++) {
                    //min[i] = uzaklık[i][0];

                  /*  if (i == merkez[t]) {
                        i++;
                     }*/
                    for (t = 0; t < kumepar; t++) {
                        uzak[t] = (float) Math.sqrt((Math.pow(Math.abs(kumeler[x][i][0] - dizi[t][0]), 2) + Math.pow(Math.abs(kumeler[x][i][1] - dizi[t][1]), 2) + Math.pow(Math.abs(kumeler[x][i][2] - dizi[t][2]), 2)));
                    }

                    min = uzak[0];
                    kumesıra = 0;

                    for (t = 0; t < kumepar; t++) {

                        if (min > uzak[t]) {
                            min = uzak[t];
                            kumesıra = t;
                        }
                    }
                    // System.out.println((i + 1) + ". " + kümesıra + ".sıradaki uzaklık :" + min);


                    if (kumesıra != x) {

                        durum = true;// yani kümeler stabil değil.

                        // elemansayı[kümesıra]++;
                        elemansayi[kumesıra]++;

                        kumeler[kumesıra][elemansayi[kumesıra]][0] = kumeler[x][i][0];
                        kumeler[kumesıra][elemansayi[kumesıra]][1] = kumeler[x][i][1];
                        kumeler[kumesıra][elemansayi[kumesıra]][2] = kumeler[x][i][2];

                        for (int a = i; a < elemansayi[x]; a++) {
                            kumeler[x][a][0] = kumeler[x][a + 1][0];
                            kumeler[x][a][1] = kumeler[x][a + 1][1];
                            kumeler[x][a][2] = kumeler[x][a + 1][2];
                        }
                        elemansayi[x]--;
                    }

                }

            }

           /* for (t = 0; t < kümepar; t++)
                System.out.println(elemansayı[t]);
              }*/
            System.out.println("*******************************");
        }

        String decKümeler ="";

        for (int c = 0; c < kumepar; c++) {
            decKümeler +=(c+1)+".küme"+"\n\n";
            for (int a = 0; a < elemansayi[c] + 1; a++) {


                System.out.println(c + ". küme " + (a + 1) + ".eleman ----  x : " + kumeler[c][a][0] + " y:" + kumeler[c][a][1] + " z:" + kumeler[c][a][2]);

                decKümeler += ( " x : " + Integer.toHexString((int)kumeler[c][a][0]) + " y:" + Integer.toHexString((int)kumeler[c][a][1]) + " z:" + Integer.toHexString((int)kumeler[c][a][2])+"\n");
            }

            FileHandle file = Gdx.files.local(kumepar+"lü küme.txt");
            file.writeString(decKümeler, false);

        }

        return kumeler;
    }
}







