package alptraum;


import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Created by Loewe on 11/25/2016.
 */

public class Health {
    Image[] hp;

    public Health() throws SlickException{
        hp = new Image[] {new Image("res/background/puzzle1/FOOD/heart.png"), new Image("res/background/puzzle1/FOOD/heart.png"),
                new Image("res/background/puzzle1/FOOD/heart.png"),new Image("res/background/puzzle1/FOOD/heart.png"),
                new Image("res/background/puzzle1/FOOD/heart.png")};
    }

    public Image[] returnHealth(){
        return hp;
    }
}
