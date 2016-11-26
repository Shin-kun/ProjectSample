package alptraum.puzzles.level1.level1;

/**
 * Created by Loewe on 11/10/2016.
 */
/**
    //TODO
    y = 10;
    x = 7    movingright;
**/
import alptraum.Camera;
import alptraum.Hero;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.*;
import org.newdawn.slick.tiled.TiledMap;

public class Puzzle2 extends BasicGameState{
    Animation hero,steady, movingUp, movingDown,movingRight, movingLeft;
    private static final int TILEWIDTH = 32;
    private static final int TILEHEIGHT = 32;

    private int[] duration = {200,200,200}; // an animation is a series of frames(milliseconds)
    private int[] duration2 = {1000,100,100};
    private float heroPositionX = 5f;// keep track of position of hero
    private float heroPositionY = 11f;
    private  float heroW = 20.0f;
    private  float heroL = 27.0f;
    Rectangle weapon1, weapon2, weapon3, food1;
    Image hp;
    Image swords;
    Rectangle rHero;
    private TiledMap cave2;
    private static final int NUMBEROFLAYERS = 4;
    private static final float SPEED = 0.0025f;

    Hero player;
    Camera camera;
    private boolean[][] blocked;
    Image cake1;
    boolean diagupright, diagdownright, diagdownleft, diagupleft, diagdown1,diagup1;
    boolean upmove, downmove;

    private float sword1moveX = 19, sword2moveX = 3, sword3moveX = 10f;
    private float sword1moveY = 7, sword2moveY = 6, sword3moveY = 3f;

    public Puzzle2(int state, Hero player) throws SlickException{
        this.player = player;
    }

    private void initializeBlocked() {
        int numTiles = 0;
        for (int l = 0; l < NUMBEROFLAYERS; l++) {
            String layerValue = cave2.getLayerProperty(l, "blocked", "false");
            if (layerValue.equals("true")) {
                for (int c = 0; c < cave2.getHeight(); c++) {
                    for(int r = 0; r < cave2.getWidth(); r++) {
                        if(cave2.getTileId(r, c, l) != 0) {
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
        weapon1 = new Rectangle(0,0,0,0);
        weapon2 = new Rectangle(0,0,0,0);
        weapon3 = new Rectangle(0,0,0,0);
        rHero = new Rectangle(15,0,heroW,heroL);


        cave2 = new TiledMap("res/background/puzzle1/cave1Final.tmx");
        blocked = new boolean[cave2.getWidth()][cave2.getHeight()];
        initializeBlocked();
        diagupright = diagdown1 = true;
        diagdownright = diagdownleft = diagupleft = true;
        upmove = diagup1 = false;
        downmove = true;

        cake1 = new Image("res/background/puzzle1/FOOD/Cake.png");
        swords = new Image("res/background/puzzle1/FOOD/Sword.png");
        hp = new Image("res/background/puzzle1/FOOD/heart.png");

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
        camera = new Camera(container,cave2);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        //cave2.draw(heroPositionX-50,heroPositionY-100,2.5f);
        //camera.centerOn(heroPositionX * 32, heroPositionY * 32);
        camera.translateGraphics();
        camera.drawMap(0);
        camera.drawMap(1);
        camera.drawMap(2);
        hero.draw(heroPositionX * 32, heroPositionY * 32);
        camera.drawMap(3);
        swords.draw(sword1moveX * 32, sword1moveY * 32);
        swords.draw(sword2moveX * 32, sword2moveY * 32);
        swords.draw(sword3moveX * 32, sword3moveY * 32);
        cake1.draw(12 * TILEWIDTH,6 * TILEHEIGHT,25,25);

        weapon1 = new Rectangle(sword1moveX * 32, sword1moveY * 32, 25,25);
        weapon2 = new Rectangle(sword2moveX * 32, sword2moveY * 32,25,25);
        weapon3 = new Rectangle(sword3moveX * 32, sword3moveY * 32,25,25);
        rHero = new Rectangle(heroPositionX * TILEWIDTH - 1,heroPositionY * TILEHEIGHT,heroW,heroL);
        food1 = new Rectangle(12 * TILEWIDTH,6 * TILEHEIGHT,25,25);

        if(player.getHp() == 5)
            hp.draw(3 * TILEWIDTH,1 * TILEHEIGHT,20,15);
        if(player.getHp() >= 4) {
            hp.draw(4 * TILEWIDTH, 1 * TILEHEIGHT, 20, 15);
            System.out.println("wut");
        }
        if(player.getHp() >= 3)
            hp.draw(5 * TILEWIDTH,1 * TILEHEIGHT,20,15);
        if(player.getHp() >= 2)
            hp.draw(6 * TILEWIDTH,1 * TILEHEIGHT,20,15);
        if(player.getHp() >= 1)
            hp.draw(7 * TILEWIDTH,1 * TILEHEIGHT,20,15);



        g.drawString("HEALTH: ", 1 * TILEWIDTH, 1 * TILEHEIGHT);
        g.drawString("hero X position: "+heroPositionX+"\nhero Y position: "+heroPositionY,400,200);
        g.drawString("HERO NAME: "+player.getName()+" ",100,100);

        g.draw(rHero);
        g.draw(weapon1);
        g.draw(weapon2);
        g.draw(weapon3);
        g.draw(food1);
    }

    private void swordmove(int delta){
        if((int) sword1moveY == 6) {    //sword1
            downmove = true;
        }
        if(downmove && (int) sword1moveY != 10){
            sword1moveY += delta * 0.0025f;
            upmove = true;
        } else if(upmove && (int) sword1moveY != 6){
            sword1moveY -= delta * 0.0025f;
            downmove = false;
        }
        //sword2
        if(diagdown1 && (int) sword2moveX != 6 && (int) sword2moveY != 9){
            sword2moveX += delta * 0.005f;
            sword2moveY += delta * 0.005f;
            diagup1 = true;
        } else if(diagup1 && (int) sword2moveX != 3 && (int) sword2moveY != 6){
            sword2moveX -= delta * 0.0025f;
            sword2moveY -= delta * 0.0025f;
            diagdown1 = false;
        } else {
            diagdown1 = true;
        }
        //sword3
        if((int) sword3moveX != 15 && (int) sword3moveY == 3){
            //move right
            sword3moveX += delta * 0.0095f;
        } else if((int) sword3moveX == 15 && (int) sword3moveY != 8){
            //move down
            sword3moveY += delta * 0.0095f;

        } else if((int) sword3moveY == 8 && (int) sword3moveX != 9){
            //move left
            sword3moveX -= delta  * 0.015f;

        } else if((int) sword3moveX == 9 && (int) sword3moveY != 3){
            //move up
            sword3moveY -= delta * 0.015f;
        }
    }



    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        Input input = gc.getInput();
        swordmove(delta);
        if (input.isKeyDown(Input.KEY_UP)) {
            System.out.println(delta + " OH YEAH");
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

        if((int) heroPositionY == 12 && ((int) heroPositionX >= 4 && (int) heroPositionX <= 6)){
            sbg.enterState(6);
        }
        if((int) heroPositionX == 22 && ((int) heroPositionY >= 7 && (int) heroPositionY <= 10)){
            sbg.enterState(8);
        }
    }

    @Override
    public int getID() {
        return 7;
    }
}
