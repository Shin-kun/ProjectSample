package alptraum.puzzles.level1.level3;

import alptraum.Camera;
import alptraum.Hero;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.*;
import org.newdawn.slick.tiled.TiledMap;

public class Puzzle7 extends BasicGameState{
    Animation hero,steady, movingUp, movingDown,movingRight, movingLeft;
    Animation angel;
    private static final int TILEWIDTH = 32;
    private static final int TILEHEIGHT = 32;

    int[] duration = {200,200,200}; // an animation is a series of frames(milliseconds)
    int[] duration2 = {1000,100,100};
    int[] duration3 = {250,150,150,100};
    float heroPositionX = 3.5f;// keep track of position of hero
    float heroPositionY = 8f;
    float heroW = 25.0f;
    float heroL = 34.0f;

    boolean nowleft,nowright,nowUp,nowDown;
    private TiledMap forest1;
    private static final int NUMBEROFLAYERS = 7;
    private static final float SPEED = 0.0025f;
    Hero player;
    Camera camera;
    private boolean[][] blocked;

    public Puzzle7(int state, Hero player) throws SlickException{
        this.player = player;
    }

    private void initializeBlocked() {
        int numTiles = 0;
        for (int l = 0; l < NUMBEROFLAYERS; l++) {
            String layerValue = forest1.getLayerProperty(l, "blocked", "false");
            if (layerValue.equals("true")) {
                for (int c = 0; c < forest1.getHeight(); c++) {
                    for(int r = 0; r < forest1.getWidth(); r++) {
                        if(forest1.getTileId(r, c, l) != 0) {
                            blocked[r][c] = true;
                            numTiles++;
                        }
                    }
                }
            }
        }
        System.out.println(numTiles + " TOTAL BLOCKS!");
    }

    private boolean isBlocked(float x, float y) {
        if((x < 0 ) || x >= TILEWIDTH || y < 0 || y >= TILEHEIGHT){
            return true;
        }
        int xBlock = (int) x /* TILEWIDTH*/;
        int yBlock = (int) y /* TILEHEIGHT*/;
        System.out.println(xBlock + " mao ni si Xblock");
        System.out.println(yBlock + " mao ni si yBlock");
        System.out.println(blocked[xBlock][yBlock] + " mao ni ilang values");
        return blocked[xBlock][yBlock];
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        forest1 = new TiledMap("res/background/puzzle3/map1Final.tmx");
        blocked = new boolean[forest1.getWidth()][forest1.getHeight()];
        initializeBlocked();

        Image[] heroSteady = {new Image("res/characters/hero/0.png"),new Image("res/characters/hero/3.png"),new Image("res/characters/hero/4.png")};
        Image[] walkUp = {new Image("res/characters/hero/2.png"),new Image("res/characters/hero/11.png"),new Image("res/characters/hero/12.png")};
        Image[] walkLeft = {new Image("res/characters/hero/1.png"),new Image("res/characters/hero/9.png"),new Image("res/characters/hero/10.png")};
        Image[] walkRight = {new Image("res/characters/hero/R1.png"),new Image("res/characters/hero/R2.png"),new Image("res/characters/hero/R3.png")};
        Image[] walkDown = {new Image("res/characters/hero/0.png"),new Image("res/characters/hero/7.png"),new Image("res/characters/hero/8.png")};
        Image[] angelstead = {new Image("res/characters/Angel/8.png"),new Image("res/characters/Angel/9.png"),new Image("res/characters/Angel/10.png")
                        ,new Image("res/characters/Angel/11.png")};

        angel = new Animation(angelstead,duration3,true);

        movingRight = new Animation(walkRight,duration,true);
        movingUp = new Animation(walkUp,duration,true);
        movingLeft = new Animation(walkLeft,duration,true);
        movingDown = new Animation(walkDown,duration,true);
        steady = new Animation(heroSteady,duration2, true);
        hero = steady;
        nowleft = nowright = nowUp = nowDown = false;
        camera = new Camera(container, forest1);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        camera.translateGraphics();
        camera.drawMap(1);
        camera.drawMap(0);
        camera.drawMap(2);
        camera.drawMap(3);
        camera.drawMap(4);
        camera.drawMap(5);
        hero.draw(heroPositionX * 32, heroPositionY * 32);
        angel.draw(12 * TILEWIDTH,4 * TILEHEIGHT);
        camera.drawMap(6);
        g.drawString("hero X position: "+heroPositionX+"\nhero Y position: "+heroPositionY,400,200);
        g.drawString("HERO NAME: "+player.getName()+" ",100,100);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        Input input = gc.getInput();

        if (input.isKeyDown(Input.KEY_UP)) {
            movingUp.update(delta);
            hero = movingUp;
            if(!isBlocked(heroPositionX, heroPositionY + delta * SPEED - 0.1f)){
                heroPositionY -= delta * SPEED;
            }
        }
        else if (input.isKeyDown(Input.KEY_DOWN)) {
            movingDown.update(delta);
            hero = movingDown;
            if(!isBlocked(heroPositionX, heroPositionY + delta * SPEED + 0.1f)){
                heroPositionY += delta * SPEED;
            }
        }
        else if (input.isKeyDown(Input.KEY_LEFT)) {
            movingLeft.update(delta);
            hero = movingLeft;
            if (!isBlocked(heroPositionX - delta * SPEED - 0.1f, heroPositionY )) {
                heroPositionX -=  delta * SPEED;
            }
        }
        else if (input.isKeyDown(Input.KEY_RIGHT)) {
            movingRight.update(delta);
            hero = movingRight;
            if (!isBlocked(heroPositionX + delta * SPEED + 0.4f, heroPositionY)){
                heroPositionX += delta * SPEED;
            }
        } else {
            hero = steady;
        }
        if((heroPositionX >= 2 && heroPositionX <= 5) && (int) heroPositionY == 0){
            sbg.enterState(15);
        }

    }

    @Override
    public int getID() {
        return 14;
    }
}
