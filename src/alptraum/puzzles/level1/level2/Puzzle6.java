package alptraum.puzzles.level1.level2;

import alptraum.Camera;
import alptraum.Hero;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

/**
 * Created by Loewe on 11/23/2016.
 */

//TODO more collisions check


public class Puzzle6 extends BasicGameState{
    Animation hero,steady, movingUp, movingDown,movingRight, movingLeft;
    private static final int TILEWIDTH = 32;
    private static final int TILEHEIGHT = 32;

    int[] duration = {200,200,200}; // an animation is a series of frames(milliseconds)
    int[] duration2 = {1000,100,100};
    float heroPositionX = 1f;// keep track of position of hero
    float heroPositionY = 2f;
    float heroW = 25.0f;
    float heroL = 34.0f;

    boolean nowleft,nowright,nowUp,nowDown;
    private TiledMap futureroom2;
    private static final int NUMBEROFLAYERS = 5;
    private static final float SPEED = 0.0025f;
    boolean moreleft,moreright,moreUp,moreDown;

    Hero player;
    Camera camera;
    private boolean[][] blocked;

    public Puzzle6(int state, Hero player) throws SlickException {
        this.player = player;
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        camera.translateGraphics();
        camera.drawMap(0);
        camera.drawMap(1);
        camera.drawMap(2);
        camera.drawMap(3);
        camera.drawMap(5);
        hero.draw(heroPositionX * 32, heroPositionY * 32);
        camera.drawMap(4);
        g.drawString("hero X position: "+heroPositionX+"\nhero Y position: "+heroPositionY,400,200);
        g.drawString("HERO NAME: "+player.getName()+" ",100,100);
    }


    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        Input input = gc.getInput();

        if (input.isKeyDown(Input.KEY_UP)) {
            System.out.println(delta + " OH YEAH");
            movingUp.update(delta);
            hero = movingUp;
            if(/*moreUp || */!isBlocked(heroPositionX, heroPositionY - delta * SPEED - 0.1f)){
                heroPositionY -= delta * SPEED;
            /*} else {
                moreDown = moreleft = moreright = true;
            */}
        }
        else if (input.isKeyDown(Input.KEY_DOWN)) {
            movingDown.update(delta);
            hero = movingDown;
            if(/*moreDown ||*/ !isBlocked(heroPositionX, heroPositionY + delta * SPEED + 0.1f)){
                heroPositionY += delta * SPEED;
            /*} else {
                moreleft = true;
            */}
        }
        else if (input.isKeyDown(Input.KEY_LEFT)) {
            movingLeft.update(delta);
            hero = movingLeft;
            if (/*moreleft ||*/ !isBlocked(heroPositionX - delta * SPEED - 0.1f, heroPositionY )) {
                heroPositionX -=  delta * SPEED;
            /*} else {
                moreDown = true;
            */}
        }
        else if (input.isKeyDown(Input.KEY_RIGHT)) {
            movingRight.update(delta);
            hero = movingRight;
            if (/*moreright || */!isBlocked(heroPositionX + delta * SPEED + 0.4f, heroPositionY)){
                heroPositionX += delta * SPEED;
            /*} else {
                moreleft = moreDown = moreUp = true;
            */}
        } else {
            hero = steady;
        }

        if((int) heroPositionX == 0 && ((int) heroPositionY <= 2 && (int) heroPositionY >= 1)){
            sbg.enterState(12);
        }
        teleport();

    }

    private void initializeBlocked() {
        int numTiles = 0;
        for (int l = 0; l < NUMBEROFLAYERS; l++) {
            String layerValue = futureroom2.getLayerProperty(l, "blocked", "false");
            if (layerValue.equals("true")) {
                for (int c = 0; c < futureroom2.getHeight(); c++) {
                    for(int r = 0; r < futureroom2.getWidth(); r++) {
                        if(futureroom2.getTileId(r, c, l) != 0) {
                            blocked[r][c] = true;
                            numTiles++;
                        }
                    }
                }
            }
        }
        System.out.println(numTiles + " mao ni si numTiles");
    }

    private boolean isBlocked(float x, float y) {
        int xBlock = (int) x /* TILEWIDTH*/;
        int yBlock = (int) y /* TILEHEIGHT*/;
        System.out.println(xBlock + " mao ni si Xblock");
        System.out.println(yBlock + " mao ni si yBlock");
        System.out.println(blocked[xBlock][yBlock] + " mao ni ilang values");
        return blocked[xBlock][yBlock];
    }

    private void teleport(){
        //first teleport
        if((int) heroPositionX == 2 && (heroPositionY <= 4 && heroPositionY >= 3.6)){
            heroPositionX = 2;
            heroPositionY = 8;
            heroPositionY++;

        } else if((int) heroPositionX == 2 && (heroPositionY >= 8 && heroPositionY <= 8.3)){
            heroPositionX = 2;
            heroPositionY = 4;
            heroPositionX--;
        }
        //second teleport
        if((int) heroPositionX == 4 && (heroPositionY <= 8 && heroPositionY >= 7.6)){
            heroPositionX = 5;
            heroPositionY = 1;
            heroPositionY++;

        } else if((int) heroPositionX == 5 && (heroPositionY <= 1.2 && heroPositionY >=0.9)){
            heroPositionX = 4;
            heroPositionY = 8;
            heroPositionY++;
            heroPositionX++;
        }
        //third teleport
        if((int) heroPositionX == 10 && (heroPositionY <= 4 && heroPositionY >= 3.7)){
            heroPositionX = 8;
            heroPositionY = 8;
            heroPositionX++;

        } else if((int) heroPositionX == 8 && (heroPositionY <= 8 && heroPositionY >= 7.7)){
            heroPositionX = 10;
            heroPositionY = 4;
            heroPositionY--;
        }

        //fourth teleport
        if((int) heroPositionX == 21 && (heroPositionY <= 11 && heroPositionY >= 10.7)){
            heroPositionX = 17;
            heroPositionY = 5;
            heroPositionX++;

        } else if((int) heroPositionX == 17 && (heroPositionY <= 5 && heroPositionY >= 4.7)){
            heroPositionX = 21;
            heroPositionY = 11;
            heroPositionX--;
        }


    }


    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        futureroom2 = new TiledMap("res/background/puzzle2/futureroom1.tmx");
        blocked = new boolean[futureroom2.getWidth()][futureroom2.getHeight()];
        initializeBlocked();
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
        nowleft = nowright = nowUp = nowDown = false;
        moreDown = moreleft = moreright = moreUp = true;
        camera = new Camera(container,futureroom2);
    }


    public int getID() {
        return 13;
    }
}
