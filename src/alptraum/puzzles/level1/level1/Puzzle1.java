package alptraum.puzzles.level1.level1;

/**
 * Created by Loewe on 11/10/2016.
 */

/**
 * //TODO
    NOTE:
        X = 19;
        Y= 8
        heroPositionX = 9;
        heroPositionY = 7  movingright

        heroPositionX = 13 moving left
        heroPositionY = 7;

        movingDown x = 18
                    y = 7

    //TODO adding saving points


**/
//newly added function in hero is checkDamage please check
import alptraum.Health;
import alptraum.Hero;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.*;
import org.newdawn.slick.tiled.TiledMap;
import alptraum.Camera;

public class Puzzle1 extends BasicGameState{
    Animation hero,steady, movingUp, movingDown,movingRight, movingLeft;
    private TiledMap cave1;
    private static final int TILEWIDTH = 32;
    private static final int TILEHEIGHT = 32;

    int[] duration = {200,200,200}; // an animation is a series of frames(milliseconds)
    int[] duration2 = {1000,100,100};
    private float heroPositionX = 20f;      // keep track of position of hero
    private float heroPositionY = 10f;
    private boolean nowUp,nowDown, nowRight,nowLeft;
    float heroW = 20.0f;
    float heroL = 27.0f;
    Hero player;
    Camera camera;
    //Health health;
    private boolean[][] blocked;
    private static final int NUMBEROFLAYERS = 6;
    private static final float SPEED = 0.0025f;
   //boolean moreleft,moreright,moreUp,moreDown;
    Rectangle rHero;
    Rectangle weapon1,weapon2,weapon3,weapon4;
    Image swords;
    Image hp;
    private float swordMovementX = 3f;
    private float swordMovementY = 6f;
    private float swordMovementX1 = 7f;
    private float swordMovementY1 = 10f;
    private float swordMovementX2 = 16f;
    private float swordMovementY2 = 5f;
    private float swordMovementX3 = 3f;
    private float swordMovementY3 = 4f;

    boolean diagdown, diagup, up1, down1, right,left;

    public Puzzle1(int state, Hero player) throws SlickException{
        this.player = player;
    }

    private void initializeBlocked() {
        for (int l = 0; l < NUMBEROFLAYERS; l++) {
            String layerValue = cave1.getLayerProperty(l, "blocked", "false");
            if (layerValue.equals("true")) {
                for (int c = 0; c < cave1.getHeight(); c++) {
                    for(int r = 0; r < cave1.getWidth(); r++) {
                        if(cave1.getTileId(r, c, l) != 0) {
                            blocked[r][c] = true;
                        }
                    }
                }
            }
        }
    }

    private boolean isBlocked(float x, float y) {
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
        weapon4 = new Rectangle(0,0,0,0);
        rHero = new Rectangle(15,0,heroW,heroL);

        nowDown = nowLeft = nowRight = nowUp = false;
        right = down1 = true;
        up1 = left = false;

        cave1 = new TiledMap("res/background/puzzle1/Cave1part2Final.tmx", "res/background/puzzle1");
        swords = new Image("res/background/puzzle1/FOOD/Sword.png");
        hp = new Image("res/background/puzzle1/FOOD/heart.png");

        diagdown = false;
        diagup = true;
        blocked = new boolean[cave1.getWidth()][cave1.getHeight()];
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
        camera = new Camera(container,cave1);
    }
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        camera.translateGraphics();
        camera.drawMap(0);
        camera.drawMap(2);
        camera.drawMap(3);
        camera.drawMap(5);
        hero.draw(heroPositionX * TILEWIDTH, heroPositionY * TILEHEIGHT);
        camera.drawMap(4);

        swords.draw(swordMovementX * 32,swordMovementY * 32);
        swords.draw(swordMovementX1 * 32, swordMovementY1 * 32);
        swords.draw(swordMovementX2 * 32,swordMovementY2 * 32);
        swords.draw(swordMovementX3 * 32,swordMovementY3 * 32);
        //player HP

        if(player.getHp() >= 1)
            hp.draw(3 * TILEWIDTH,12 * TILEHEIGHT,20,15);
        if(player.getHp() >= 2) {
            hp.draw(4 * TILEWIDTH, 12 * TILEHEIGHT, 20, 15);
        }
        if(player.getHp() >= 3)
            hp.draw(5 * TILEWIDTH,12 * TILEHEIGHT,20,15);
        if(player.getHp() >= 4)
            hp.draw(6 * TILEWIDTH,12 * TILEHEIGHT,20,15);
        if(player.getHp() == 5)
            hp.draw(7 * TILEWIDTH,12 * TILEHEIGHT,20,15);

        weapon1 = new Rectangle(swordMovementX * 32, swordMovementY * 32, 25,25);
        weapon2 = new Rectangle(swordMovementX1 * 32, swordMovementY1 * 32,25,25);
        weapon3 = new Rectangle(swordMovementX2 * 32, swordMovementY2 * 32,25,25);
        weapon4 = new Rectangle(swordMovementX3 * 32, swordMovementY3 * 32,25,25);

        g.draw(weapon1);
        g.draw(weapon2);
        g.draw(weapon3);
        g.draw(weapon4);

        rHero = new Rectangle(heroPositionX * TILEWIDTH - 1,heroPositionY * TILEHEIGHT,heroW,heroL);
        g.draw(rHero);
        g.drawString("HEALTH: ", 1 * TILEWIDTH, 11.8f * TILEHEIGHT);
        g.drawString("hero X position: "+heroPositionX+"\nhero Y position: "+heroPositionY,400,200);
        g.drawString("HERO NAME: "+player.getName()+" ",100,100);
    }

    private void moveSword(int delta){
        //sword1

        if((int) swordMovementY == 6 && (int) swordMovementX != 6) {
            swordMovementX += delta * 0.004f;
        }else if((int) swordMovementX == 6 && (int) swordMovementY != 9) {
            swordMovementY += delta * 0.004f;
        }else if((int) swordMovementY == 9 && (int) swordMovementX != 3) {
            swordMovementX -= delta * 0.004f;
        }else if((int) swordMovementX == 3 && swordMovementY != 6)
            swordMovementY -= delta * 0.004f;
        //sword2
        if(diagup && (int) swordMovementX1 != 11 && (int) swordMovementY1 != 6){
            swordMovementY1 -= delta * 0.004f;
            swordMovementX1 += delta * 0.004f;
            diagdown = true;
        } else if(diagdown && (int) swordMovementX1 != 7 && (int) swordMovementY1 != 10){
            diagup = false;
            swordMovementY1 += delta * 0.004f;
            swordMovementX1 -= delta * 0.004f;
        } else {
            diagup = true;
        }
        //sword3
        if(down1 && (int) swordMovementY2 != 10){
            swordMovementY2 += delta * 0.00065f;
            up1 = true;
        } else if(up1 && (int) swordMovementY2 != 5){
            swordMovementY2 -= delta * 0.00065f;
            down1 = false;
        } else {
            down1 = true;
        }
        //sword4
        if(right && (int) swordMovementX3 != 5) {
            swordMovementX3 += delta * 0.00065f;
            left = true;
        } else if(left && (int) swordMovementX3 != 1){
            swordMovementX3 -= delta * 0.00065f;
            right = false;
        } else {
            right = true;
        }
    }


    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        Input input = gc.getInput();
        moveSword(delta);
        System.out.println(player.getHp() + " mao ni si HP");

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

        if(((int) heroPositionX >= 3 && (int) heroPositionX <= 5) && (int) heroPositionY == 0){
            sbg.enterState(7);
        }
        if((int) heroPositionX == 22 && ((int) heroPositionY >= 2 && (int) heroPositionY <= 6)){
            sbg.enterState(11);
        }

        if(rHero.intersects(weapon1) || rHero.intersects(weapon2) || rHero.intersects(weapon3) || rHero.intersects(weapon4)){
            player.minusHp();
        }

//        player.checkDamage(rHero,weapon1);
//        player.checkDamage(rHero,weapon2);
//        player.checkDamage(rHero,weapon3);
//        player.checkDamage(rHero,weapon4);
    }

    @Override
    public int getID() {
        return 6;
    }
}
