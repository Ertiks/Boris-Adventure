package com.roguelike_java;

import com.roguelike_java.Entities.*;
import com.roguelike_java.Entities.Items.*;

//METHODES STATICS
public class DungeonGeneration {

    private static int startingPosX = 0;
    private static int startingPosY = 0;



    //GETTERS :
    public static int getStartingPosX(){
        return startingPosX;
    }
    public static int getStartingPosY(){
        return startingPosY;
    }



    // Création de la map
    public static void createRoom(int X, int Y, int sizeX, int sizeY) {

        //Variables de stockage
        int oldRoomX = 0;
        int oldRoomY = 0;


        int nbSalle = (int) (Math.random() * 7) + 4;
        System.out.println("salle : " + nbSalle);

        // création d'un tableau 2D de booléens pour garder une trace des salles
        boolean[][] roomGrid = new boolean[Grid.getSizeX()][Grid.getSizeY()];

        //Debug :
        int zzz = 0;

        // Création des salles aléatoires à l'intérieur de la map
        for (int k = 0; k < nbSalle; k++) {

            //Taille de la salle :
            int roomX = (int) (Math.random() * 10) + 5;
            int roomY = (int) (Math.random() * 10) + 5;
            //Position de la salle :
            int posXRoom = (int) (Math.random() * (sizeX - roomX - 1)) + 1;
            int posYRoom = (int) (Math.random() * (sizeY - roomY - 1)) + 1; 

 
            if (k == 0){
                startingPosX = posXRoom;
                startingPosY = posYRoom;

                System.out.println(startingPosX);
                System.out.println(startingPosY);
            }
            


            boolean areaFree = true;
            for (int i = -1; i <= roomX; i++) {
                for (int j = -1; j <= roomY; j++) {
                    if (X + posXRoom + i < 0 || X + posXRoom + i >= Grid.getSizeX() || Y + posYRoom + j < 0
                            || Y + posYRoom + j >= Grid.getSizeY()) {
                        continue;
                    }
                    if (roomGrid[X + posXRoom + i][Y + posYRoom + j]) {
                        areaFree = false;
                        break;
                    }
                }
                if (!areaFree)
                    break;
            }

            // Si la zone n'est pas libre, on passe à l'itération suivante
            if (!areaFree)
                continue;

            for (int i = 0; i < roomX; i++) {
                for (int j = 0; j < roomY; j++) {

                    Grid.createGround(X + posXRoom + i, Y + posYRoom + j);

                    // Marque cette zone comme occupée dans la grille de la salle
                    roomGrid[X + posXRoom + i][Y + posYRoom + j] = true;
                }
            }

            // Ajoute une zone tampon autour de la salle pour éviter que les salles ne se touchent
            for (int i = -1; i <= roomX; i++) {
                for (int j = -1; j <= roomY; j++) {
                    if (X + posXRoom + i < 0 || X + posXRoom + i >= Grid.getSizeX() || Y + posYRoom + j < 0
                            || Y + posYRoom + j >= Grid.getSizeY()) {
                        continue;
                    }
                    roomGrid[X + posXRoom + i][Y + posYRoom + j] = true;
                }
            }

            if (k > 0){
                new Sword(oldRoomX, oldRoomY);
                createLane(oldRoomX, oldRoomY, posXRoom, posYRoom);
                new Sword(posXRoom, posYRoom);
                System.out.println("nombre de lanes : " + zzz);
                zzz++;
            }

            oldRoomX = posXRoom;
            oldRoomY = posYRoom;
        }
    }

    //Generation de couloir en 1 dimension
    public static void createLaneHorizontal(int X, int Y, int X2){

        System.out.println("coord : " + X + " " + Y + " " + X2);

        int temp;
        if (X > X2) { temp = X2; X2 = X; X = temp; }

        for (int i = X; i <= X2; i++){
            Grid.createGround(i, Y);
        }
    }

    public static void createLaneVertical(int X, int Y, int Y2){

        int temp; 
        if (Y > Y2) { temp = Y2; Y2 = Y; Y = temp; }

        for (int i = Y2; i >= Y; i--){
            Grid.createGround(X, i);
        }
    }

    //Generation de couloir en 2 dimensions
    public static void createLane(int X1, int Y1, int X2, int Y2){

        int midpointX = Math.abs(X2-X1)/2 + Math.min(X1, X2);
        int midpointY = Math.abs(Y2-Y1)/2 + Math.min(Y1, Y2);

        createLaneHorizontal(X1, Y1, midpointX);
        createLaneHorizontal(midpointX, Y2, X2);
        createLaneVertical(midpointX, Y1, Y2);
    }

    // // Création de la grande salle :
    // for (int i = 1; i < sizeX - 1; i++) {
    // for (int j = 1; j < sizeY - 1; j++) {
    // Ground ground = new Ground(X + i, Y + j);
    // App.deleteSprite(Grid.getGrid().get(X + i).get(Y + j).get(0));
    // Grid.getGrid().get(X + i).get(Y + j).remove(0);
    // Grid.getGrid().get(X + i).get(Y + j).add(ground);
    // }
    // }

    /*
     * Création d'une salle secondaire dans une salle principale
     */
    // public static void createBloc(int X, int Y, int sizeX, int sizeY) {
    // createRoom(X, Y, sizeX, sizeY);
    // for (int i = 0; i < sizeX; i++) {
    // for (int j = 0; j < sizeY; j++) {
    // if (i == 0 || i == sizeX - 1 || j == 0 || j == sizeY - 1) {
    // Wall wall = new Wall(X + i, Y + j);
    // // Suppression :
    // App.deleteSprite(Grid.getGrid().get(X + i).get(Y + j).get(0)); // Gerer ça
    // avec une vrai fonction.
    // Grid.getGrid().get(X + i).get(Y + j).remove(0);

    // // Creation :
    // Grid.getGrid().get(X + i).get(Y + j).add(wall);
    // // App.displaySprite(wall);
    // }
    // }
    // }
    // }

    // public static void connectVerticalBloc(int X, int Y, int sizeX, int sizeY) {
    // // Crée la salle
    // createRoom(X, Y, sizeX, sizeY);
    // // Connecte la salle en ajoutant des murs
    // for (int i = 0; i < sizeX; i++) {
    // for (int j = 0; j < sizeY; j++) {
    // // Ajoute un mur à toutes les positions sur les bords, excluant l'axe Y
    // if ((i == 0 || i == sizeX - 1) && (j != 0 && j != sizeY - 1)) {
    // // Crée un mur à la position actuelle
    // Wall wall = new Wall(X + i, Y + j);
    // // Suppression de l'élément existant
    // App.deleteSprite(Grid.getGrid().get(X + i).get(Y + j).get(0));
    // Grid.getGrid().get(X + i).get(Y + j).remove(0);
    // // Ajoute le mur à la place
    // Grid.getGrid().get(X + i).get(Y + j).add(wall);
    // }
    // }
    // }
    // }

    // public static void connectHorizontalBloc(int X, int Y, int sizeX, int sizeY)
    // {
    // // Crée la salle
    // createRoom(X, Y, sizeX, sizeY);

    // // Connecte la salle en ajoutant des murs
    // for (int i = 0; i < sizeX; i++) {
    // for (int j = 0; j < sizeY; j++) {
    // // Ajoute un mur à toutes les positions sur les bords, excluant l'axe X
    // if ((j == 0 || j == sizeY - 1) && (i != 0 && i != sizeX - 1)) {
    // // Crée un mur à la position actuelle
    // Wall wall = new Wall(X + i, Y + j);

    // // Suppression de l'élément existant
    // App.deleteSprite(Grid.getGrid().get(X + i).get(Y + j).get(0));
    // Grid.getGrid().get(X + i).get(Y + j).remove(0);

    // // Ajoute le mur à la place
    // Grid.getGrid().get(X + i).get(Y + j).add(wall);
    // }
    // }
    // }
    // }

}
