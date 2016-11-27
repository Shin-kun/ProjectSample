package alptraum.puzzles.level1.level1;

/**
 * Created by Loewe on 11/21/2016.
 */

import alptraum.Camera;
import alptraum.Hero;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.*;
import org.newdawn.slick.tiled.TiledMap;

public class Puzzle4 extends BasicGameState{
    Animation hero,steady, movingUp, movingDown,movingRight, movingLeft;
    int[] duration = {200,200,200}; // an animation is a series of frames(milliseconds)
    int[] duration2 = {1000,100,100};
    float heroPositionX = 1.5f;// keep track of position of hero
    float heroPositionY = 9f;
    Hero player;
    Rectangle rHero;
    Rectangle cake1,cake2,cake3,cake4,cake5,cake6,cake7,cake8,cake9;
    Rectangle sword1,sword2,sword3,sword4,sword5,sword6,sword7,sword8,sword9,sword10,sword11;
    Rectangle meat1,meat2,meat3;
    private TiledMap cave4;
    Camera camera;
    private static final float SPEEDOBJ = 0.025f;
    private boolean blocked[][];
    private static final int TILESIZE = 32;
    Image cakes;
    Image swords;
    Image meats;
    Image hp;
    private boolean up1, down1, up2, down2;
    private static final int NUMBEROFLAYERS = 5;
    private static final float SPEED = 0.0025f;

    private float moveX1 = 6f, moveX2 = 7f, moveX3 = 8f, moveX4 = 9f,moveX5 = 10f,moveX6 = 11f,moveX7 = 12f,
            moveX8 = 13f,moveX9 = 14f,moveX10 = 15f, moveX11 = 16f, moveX12 = 17f, moveX13 = 18f;
    private float moveY = 2f;
    private float moveY2 = 10f;

    public Puzzle4(int state,Hero player) throws SlickException{
        this.player = player;
    }

    private void initializeBlocked() {
        for (int l = 0; l < NUMBEROFLAYERS; l++) {
            String layerValue = cave4.getLayerProperty(l, "blocked", "false");
            if (layerValue.equals("true")) {
                for (int c = 0; c < cave4.getHeight(); c++) {
                    for(int r = 0; r < cave4.getWidth(); r++) {
                        if(cave4.getTileId(r, c, l) != 0) {
                            blocked[r][c] = true;
                        }
                    }
                }
            }
        }
    }

    private boolean isBlocked(float x, float y) {
        if((x < 0 ) || x >= TILESIZE || y < 0 || y >= TILESIZE){
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
        cake1 = new Rectangle(0,0,0,0);
        cake2 = new Rectangle(0,0,0,0);
        cake3 = new Rectangle(0,0,0,0);
        cake4 = new Rectangle(0,0,0,0);
        cake5 = new Rectangle(0,0,0,0);
        cake6 = new Rectangle(0,0,0,0);
        cake7 = new Rectangle(0,0,0,0);
        cake8 = new Rectangle(0,0,0,0);
        cake9 = new Rectangle(0,0,0,0);
        sword1 = new Rectangle(0,0,0,0);
        sword2 = new Rectangle(0,0,0,0);
        sword3 = new Rectangle(0,0,0,0);
        sword4 = new Rectangle(0,0,0,0);
        sword5 = new Rectangle(0,0,0,0);
        sword6 = new Rectangle(0,0,0,0);
        sword7 = new Rectangle(0,0,0,0);
        sword8 = new Rectangle(0,0,0,0);
        sword9 = new Rectangle(0,0,0,0);
        sword10 = new Rectangle(0,0,0,0);
        sword11 = new Rectangle(0,0,0,0);
        meat1 = new Rectangle(0,0,0,0);
        meat2 = new Rectangle(0,0,0,0);
        meat3 = new Rectangle(0,0,0,0);
        rHero = new Rectangle(0,15,0,0);

        up1 = down2= false;
        down1 = up2 = true;
        hp = new Image("res/background/puzzle1/FOOD/heart.png");
        swords = new Image("res/background/puzzle1/FOOD/Sword.png");
        cakes = new Image("res/background/puzzle1/FOOD/Cake.png");
        meats = new Image("res/background/puzzle1/FOOD/Meat.png");

        cave4 = new TiledMap("res/background/puzzle1/Cave3part2Final.tmx", "res/background/puzzle1");
        blocked = new boolean[cave4.getWidth()][cave4.getHeight()];
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
        camera = new Camera(container,cave4);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        camera.translateGraphics();
        camera.drawMap(0);
        camera.drawMap(1);
        camera.drawMap(2);

        hero.draw(heroPositionX * 32,heroPositionY * 32);
        camera.drawMap(3);
        camera.drawMap(4);
        cake1 = new Rectangle(moveX1 * 32,moveY * 32,25,25);
        cake2 = new Rectangle(moveX1 * 32,moveY2 * 32,25,25);
        cake3 = new Rectangle(moveX3 * 32,moveY * 32,25,25);
        cake4 = new Rectangle(moveX5 * 32,moveY2 * 32,25,25);
        cake5 = new Rectangle(moveX5 * 32,moveY * 32,25,25);
        cake6 = new Rectangle(moveX7 * 32,moveY * 32,25,25);
        cake7 = new Rectangle(moveX9 * 32,moveY * 32,25,25);
        cake8 = new Rectangle(moveX10 * 32,moveY2 * 32,25,25);
        cake9 = new Rectangle(moveX4 * 32,moveY2 * 32,25,25);

        sword1 = new Rectangle(moveX2 * 32,moveY *32, 25,25);
        sword2 = new Rectangle(moveX4 * 32,moveY *32, 25,25);
        sword3 = new Rectangle(moveX4 * 32,moveY2 *32, 25,25);
        sword4 = new Rectangle(moveX6 * 32,moveY *32, 25,25);
        sword5 = new Rectangle(moveX8 * 32,moveY *32, 25,25);
        sword6 = new Rectangle(moveX10 * 32,moveY *32, 25,25);
        sword7 = new Rectangle(moveX13 * 32,moveY *32, 25,25);
        sword8 = new Rectangle(moveX9 * 32,moveY2 *32, 25,25);
        sword9 = new Rectangle(moveX7 * 32,moveY2 *32, 25,25);
        sword10 = new Rectangle(moveX6 * 32,moveY2 *32, 25,25);
        sword10 = new Rectangle(moveX3 * 32,moveY2 *32, 25,25);

        meat1 = new Rectangle(moveX13 * 32, moveY * 32,25,25);
        meat2 = new Rectangle(moveX12 * 32, moveY * 32,25,25);
        meat3 = new Rectangle(moveX11 * 32, moveY * 32,25,25);
        cakes.draw(moveX1 * 32, moveY * 32);
        cakes.draw(moveX1 * 32, moveY2 * 32);
        meats.draw(moveX13 * 32, moveY * 32);
        swords.draw(moveX2 * 32, moveY * 32);
        cakes.draw(moveX3 * 32, moveY * 32);
        swords.draw(moveX4 * 32, moveY * 32);
        swords.draw(moveX4 * 32, moveY2 * 32);
        cakes.draw(moveX5 * 32, moveY2 * 32);
        cakes.draw(moveX5 * 32, moveY * 32);
        swords.draw(moveX6 * 32, moveY * 32);
        meats.draw(moveX12 * 32, moveY * 32);
        cakes.draw(moveX7 * 32, moveY * 32);
        swords.draw(moveX8 * 32, moveY * 32);
        cakes.draw(moveX9 * 32, moveY * 32);
        meats.draw(moveX11 * 32, moveY * 32);
        swords.draw(moveX10 * 32, moveY * 32);
        cakes.draw(moveX10 * 32, moveY2 * 32);
        swords.draw(moveX13 * 32, moveY * 32);
        cakes.draw(moveX4 * 32, moveY2 * 32);
        swords.draw(moveX9 * 32, moveY2 * 32);
        swords.draw(moveX7 * 32, moveY2 * 32);
        swords.draw(moveX6 * 32, moveY2 * 32);
        swords.draw(moveX3 * 32, moveY2 * 32);
        g.draw(cake1);
        g.draw(cake2);
        g.draw(cake3);
        g.draw(cake4);
        g.draw(cake5);
        g.draw(cake6);
        g.draw(cake7);
        g.draw(cake8);
        g.draw(cake9);
        g.draw(sword1);
        g.draw(sword2);
        g.draw(sword3);
        g.draw(sword4);
        g.draw(sword5);
        g.draw(sword6);
        g.draw(sword7);
        g.draw(sword8);
        g.draw(sword9);
        g.draw(sword10);
        g.draw(sword11);
        g.draw(meat1);
        g.draw(meat2);
        g.draw(meat3);

        if(player.getHp() >= 1)
            hp.draw(3 * TILESIZE,12 * TILESIZE,20,15);
        if(player.getHp() >= 2) {
            hp.draw(4 * TILESIZE, 12 * TILESIZE, 20, 15);
        }
        if(player.getHp() >= 3)
            hp.draw(5 * TILESIZE,12 * TILESIZE,20,15);
        if(player.getHp() >= 4)
            hp.draw(6 * TILESIZE,12 * TILESIZE,20,15);
        if(player.getHp() == 5)
            hp.draw(7 * TILESIZE,12 * TILESIZE,20,15);
        g.drawString("Health: ", 1 * TILESIZE, 11.8f * TILESIZE);
        g.drawString("hero X position: "+heroPositionX+"\nhero Y position: "+heroPositionY,400,200);
        g.drawString("HERO NAME: "+player.getName()+" ",100,100);
    }

    private void move(int delta){
        if(down1 && (int) moveY != 11){
            moveY += delta * SPEEDOBJ;
            up1 = true;
        } else if(up1 && (int) moveY != 1f){
            moveY -= delta * SPEEDOBJ;
            down1 = false;
        } else {
            down1 = true;
        }
        if(up2 && (int) moveY2 != 1f){
            moveY2 -= delta *SPEEDOBJ;
            down2 = true;
        } else if(down2 && (int) moveY2 != 11f){
            up2 = false;
            moveY2 += delta * SPEEDOBJ;
        } else {
            up2 = true;
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        Input input = gc.getInput();
        move(delta);
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

        if(!(input.isKeyDown(input.KEY_UP)) &&!( input.isKeyDown(input.KEY_DOWN)) &&!(input.isKeyDown(input.KEY_LEFT))&&!(input.isKeyDown(input.KEY_RIGHT))){
            hero = steady;
        }

        if((int) heroPositionX == 0 && (heroPositionY > 8 && heroPositionY < 11))
            sbg.enterState(9);
    }

    @Override
    public int getID(){
        return 10;
    }
}
