package com.roguelike_java;

import java.lang.Math;

//CLASSE DE METHODES UTILITAIRES DIVERSES 
public class Utils {
    
    //Distance entre 2 points en 1 dimension
    public static int distance1D(int x1, int x2){
        return Math.abs(x1 - x2);
    }

    //Distance en BLOCS entre deux points (en 2 dimensions)
    public static int distance2Dgrid(int x1, int y1, int x2, int y2){
        return (Math.abs(x2-x1) + Math.abs(y2-y1));
    }

    public static int distance2Dsquare(int x1, int y1, int x2, int y2){
        return ((x2-x1)*(x2-x1)  +  (y2-y1)*(y2-y1));
    }
}
