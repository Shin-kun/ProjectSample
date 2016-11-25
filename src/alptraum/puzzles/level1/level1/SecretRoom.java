package alptraum.puzzles.level1.level1;

import alptraum.Camera;
import alptraum.Hero;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

/**
 * Created by niervin on 11/21/2016.
 */
public class SecretRoom extends BasicGameState{
    Animation hero,steady, movingUp, movingDown,movingRight, movingLeft;
    int[] duration = {200,200,200}; // an animation is a series of frames(milliseconds)
    int[] duration2 = {1000,100,100};
    float heroPositionX = 2f;// keep track of position of hero
    float heroPositionY = 5f;
    Hero player;
    Rectangle rHero;
    private TiledMap secretRoom;
    Camera camera;

    private static final int NUMBEROFLAYERS = 5;
    private static final float SPEED = 0.00095f;
    boolean moreleft,moreright,moreUp,moreDown;

    public SecretRoom(int state,Hero player) throws SlickException {
        this.player = player;
    }


    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        secretRoom = new TiledMap("res/background/puzzle1/Cave1Part3Final.tmx", "res/background/puzzle1");

        Image[] heroSteady = {new Image("res/characters/hero/0.png"),new Image("res/characters/hero/3.png"),new Image("res/characters/hero/4.png")};
        Image[] walkUp = {new Image("res/characters/hero/2.png"),new Image("res/characters/hero/11.png"),new Image("res/characters/hero/12.png")};
        Image[] walkLeft = {new Image("res/characters/hero/1.png"),new Image("res/characters/hero/9.png"),new Image("res/characters/hero/10.png")};
        Image[] walkRight = {new Image("res/characters/hero/R1.png"),new Image("res/characters/hero/R2.png"),new Image("res/characters/hero/R3.png")};
        Image[] walkDown = {new Image("res/characters/hero/0.png"),new Image("res/characters/hero/7.png"),new Image("res/characters/hero/8.png")};

        movingRight = new Animation(walkRight,duration,true);
        movingUp = new Animation(walkUp,duration,true);
        movingLeft = new Animation(walkLeft,duration,true);
        movingDown = new Animation(walkDown,duration,true);
        steady = new Animation(heroSteady,duration2, true);
        hero = steady;
        camera = new Camera(container,secretRoom);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        camera.translateGraphics();
        camera.drawMap(0);
        camera.drawMap(1);
        camera.drawMap(2);

        hero.draw(heroPositionX * 32,heroPositionY * 32);
        camera.drawMap(3);

        g.drawString("hero X position: "+heroPositionX+"\nhero Y position: "+heroPositionY,400,200);
        g.drawString("HERO NAME: "+player.getName()+" ",100,100);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        Input input = gc.getInput();
        if (input.isKeyDown(Input.KEY_UP)) {
            System.out.println(delta + " OH YEAH");
            hero = movingUp;
            //if(moreUp || !isBlocked(heroPositionX, heroPositionY)){

            heroPositionY -= delta * SPEED;
            /*} else {
                moreDown = moreleft = moreright = true;
            }*/
        }
        else if (input.isKeyDown(Input.KEY_DOWN)) {
            hero = movingDown;
            //if(moreDown || !isBlocked(heroPositionX, heroPositionY)) {
            heroPositionY += delta * SPEED;
            /*} else {
                moreUp = moreleft = moreright = true;
            }*/
        }
        else if (input.isKeyDown(Input.KEY_LEFT)) {
            hero = movingLeft;
            //if (moreleft || !isBlocked(heroPositionX  , heroPositionY )) {
            heroPositionX -=  delta * SPEED;
            /*} else {
                moreDown = moreUp = moreright = true;
            }*/
        }
        else if (input.isKeyDown(Input.KEY_RIGHT)) {
            hero = movingRight;
            //if (moreright || !isBlocked(heroPositionX, heroPositionY)){
            heroPositionX += delta * SPEED;

            /*} else {
                moreleft = moreDown = moreUp = true;
            }*/
        } if(!(input.isKeyDown(input.KEY_UP)) &&!( input.isKeyDown(input.KEY_DOWN)) &&!(input.isKeyDown(input.KEY_LEFT))&&!(input.isKeyDown(input.KEY_RIGHT))){
            hero = steady;
        }

        if((int) heroPositionX == 0 && ((int) heroPositionY >= 2 && (int) heroPositionY <= 7)){
            sbg.enterState(6);
        }


    }

    @Override
    public int getID(){
        return 11;
    }

}
