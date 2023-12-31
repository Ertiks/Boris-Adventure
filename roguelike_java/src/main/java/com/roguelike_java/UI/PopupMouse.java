package com.roguelike_java.UI;

import java.util.ArrayList;

import com.roguelike_java.App;
import com.roguelike_java.EventHandler;
import com.roguelike_java.Grid;
import com.roguelike_java.Entities.Entity;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;


//Classe qui s'occupe de faire apparaitre une popup a l'endroit ou la souris pointe.
//Classe principalement static. Pas sur de vouloir que ça le reste. (potentielles multiples popup ?)
//A REFACTORER, FONCTIONS PRINCIPALES PAS CLAIR.
public class PopupMouse {
    
    private static Image image = new Image("Bloc-test.png");
    private static ImageView popupSprite = new ImageView(image);

    private static int gridPositionX;
    private static int gridPositionY;
    private static int size = 0;
    private static int sizeMax = 3;

    private static boolean visibility = true; //pasutilisé



    //Popup :
    private static Image popupTop = new Image("top-popup.png");
    private static Image popupBot = new Image("bot-popup.png");
    private static Image popupMid = new Image("mid-popup.png");

    private static ImageView popupTopSprite = new ImageView(popupTop);
    private static ImageView popupBotSprite = new ImageView(popupBot);
    private static ArrayList<ImageView> listPopupMidSprite = new ArrayList<ImageView>();

    private static Label label = new Label(" ");


    //GETTER 
    public static boolean isVisible(){
        return visibility;
    }

    //INITIALISATION
    public static void initPopup(){
        image = new Image("Bloc-test.png");
        popupSprite = new ImageView(image);
        label.setTextFill(Color.WHITE);

        for (int i = 0; i < 3; i++){
            listPopupMidSprite.add(new ImageView(popupMid));
        }
    }

    //Update la position de la souris
    public static void getMousePosition(){
        gridPositionX = (EventHandler.getMouseX()/16);
        gridPositionY = (EventHandler.getMouseY()/16);

    }

    //Se charge de récupérer les entités d'une case donnée et les concatene dans une chaine de caractère.
    public static String displayEntities(){

        int numEntities = 0;
        ArrayList<Entity> listEntity;
        String str = "";

        if (gridPositionX < App.sizeX && gridPositionY < App.sizeY){

            listEntity = Grid.getEntities(gridPositionX, gridPositionY);

            if (listEntity.size() == 0){ return null; }

            for (Entity entity : listEntity) {
   
                if (entity.isVisible() == true){
                    str += entity.getName() + "\n";
                    numEntities ++; 
                }
            }
        }

        if (str == ""){
            return null;
        }


        if (numEntities > 0){
            size = numEntities/2;
        }


        return str;
    } 

    //Fait apparaitre la popup
    public static void displayPopup(String message){

        getMousePosition();
        String str = displayEntities();
        makeInvisible();

        if (str != null){
            label.setText(displayEntities());

            int i = 1;
            if (size > sizeMax){
                size = sizeMax;
            }


            //X
            popupTopSprite.setTranslateX((gridPositionX+1)*16);
            popupBotSprite.setTranslateX((gridPositionX+1)*16);
            label.setTranslateX((gridPositionX+2)*16);

            for (i = 1; i <= size; i++){
                listPopupMidSprite.get(i-1).setTranslateX((gridPositionX+1)*16);
                listPopupMidSprite.get(i-1).setTranslateY((gridPositionY + (i*2))*16);
            }

            //Y
            popupTopSprite.setTranslateY(gridPositionY*16);
            popupBotSprite.setTranslateY((gridPositionY+(i*2))*16);
            label.setTranslateY((gridPositionY+1)*16);

            makeVisible();

            //Tofront :
            popupTopSprite.toFront();
            popupBotSprite.toFront();  
            label.toFront();

        }
        
    }

    //Methodes pour cacher ou montrer la popup
    public static void makeVisible(){
        if (visibility == false){
            visibility = true;
            App.displaySprite(popupTopSprite);
            App.displaySprite(popupBotSprite);
            App.displayText(label);

            for (int i = 0; i < size; i++){
                App.displaySprite(listPopupMidSprite.get(i));
            }
        }
    }
    public static void makeInvisible(){
        if (visibility == true){
            visibility = false;
            App.deleteSprite(popupTopSprite);
            App.deleteSprite(popupBotSprite);
            App.deleteText(label);

            for (ImageView sprite : listPopupMidSprite) {
                App.deleteSprite(sprite);
            }
        }
    }



}
