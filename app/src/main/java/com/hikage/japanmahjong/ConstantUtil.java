package com.hikage.japanmahjong;

public class ConstantUtil {

    private static int round;
    private static int[] point = {25000, 25000, 25000, 25000};
    private static boolean[] richiStatus = new boolean[4];
    private static int wind, richi, renchan;
    private static String[] player = {"東", "南", "西", "北"};
    private static int setPoint, returnPoint, honbaPoint;
    private static boolean lastWind, eastRenchan, southRenchan;

    public static void initGame() {
        round = 0;
        wind = 0;
        richi = 0;
        renchan = 0;
        for (int i = 0; i < 4; i++) {
            richiStatus[i] = false;
            point[i] = setPoint;
        }
    }

    public static void setGame(String n[], int p[],
                               int r, int w, int ri, int ren) {
        for (int i = 0; i < 4; i++) {
            player[i] = n[i];
            point[i] = p[i];
        }
        round = r;
        wind = w;
        richi = ri;
        renchan = ren;
    }

    public static void setRule(int s, int r, int h, boolean lw, boolean er, boolean sr) {
        setPoint = s;
        returnPoint = r;
        honbaPoint = h;
        lastWind = lw;
        eastRenchan = er;
        southRenchan = sr;
    }

    public static int getReturnPoint() {
        return returnPoint;
    }

    public static int getHonbaPoint() {
        return honbaPoint;
    }

    public static boolean getLastWind() {
        return lastWind;
    }

    public static boolean getEastRenchan() {
        return eastRenchan;
    }

    public static boolean getSouthRenchan() {
        return southRenchan;
    }

    public static void setPlayer(String s1, String s2, String s3, String s4) {
        player[0] = s1;
        player[1] = s2;
        player[2] = s3;
        player[3] = s4;
    }

    public static void setRound() {
        round++;
        if (round == 4) {
            round = 0;
            wind++;
        }
    }

    public static void setPoint(int player, int p) {
        point[player] += p;
    }

    public static void setPoint(int p0, int p1, int p2, int p3) {
        point[0] += p0;
        point[1] += p1;
        point[2] += p2;
        point[3] += p3;
    }

    public static void setRichi(boolean status) {
        if (status)
            richi++;
        else
            richi--;
    }

    public static void setRichi(int i) {
        richi = i;
    }

    public static void setRichiStatus(int i, boolean b) {
        richiStatus[i] = b;
    }

    public static void setRichiStatus() {
        for (int i = 0; i < 4; i++)
            richiStatus[i] = false;
    }

    public static void setRenchan() {
        renchan++;
    }

    public static void setRenchan(int i) {
        renchan = i;
    }

    public static String getPlayer(int i) {
        return player[i];
    }

    public static int getRound() {
        return round;
    }

    public static int getWind() {
        return wind;
    }

    public static int getPoint(int i) {
        return point[i];
    }

    public static int getRichi() {
        return richi;
    }

    public static boolean getRichiStatus(int i) {
        return richiStatus[i];
    }

    public static int getRenchan() {
        return renchan;
    }

}
