package com.roguelike_java;

public abstract class Enemy extends Unit{
    
    Enemy(String name, int X, int Y, int hp, int atk){
        super(name, X, Y, "Hashtag.png", hp, "enemy");
        ListEntity.addEnemy(this); //S'ajoute a la liste d'ennemis a jouer.

        setAtk(atk);
    }

    public Boolean canAttack(int X, int Y){ //Check si on peut attaquer la cible.

        String tag;

        for (int i = 0; i < Grid.getGrid().get(X).get(Y).size(); i ++){
                tag = Grid.getGrid().get(X).get(Y).get(i).getTag();
                if(tag == "PJ"){ return true; }
        }
        return false;
    }

    //METHODES ABSTRACT
    public abstract void doAction();
}
