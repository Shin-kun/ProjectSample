package alptraum.puzzles.level1.level1;

import alptraum.Camera;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import alptraum.Hero;
import org.newdawn.slick.tiled.TiledMap;

/**
 *
 * Created by Loewe on 11/14/2016.
 *
 */

public class Puzzle3 extends BasicGameState {
    Animation hero,steady, movingUp, movingDown,movingRight, movingLeft;
    int[] duration = {200,200,200}; // an animation is a series of frames(milliseconds)
    int[] duration2 = {1000,100,100};
    float heroPositionX = 2f;// keep track of position of hero
    float heroPositionY = 10f;
    Hero player;
    Rectangle rHero;
    private TiledMap cave3;
    Camera camera;
    private boolean[][] blocked;
    Image meats;
    Image cake1,arrow;
    Image hp;
    private static final int NUMBEROFLAYERS = 4;
    private static final float SPEED = 0.0025f;
    private static final int TILESIZE = 32;
    private static final float MEATSPEED = 0.0045f;
    private float meat1X = 4f, meat2X = 7f, meat3X = 11f, meat4X = 15f, meat5X = 8f;
    private float meat1Y = 4f, meat2Y = 6f, meat3Y = 3f, meat4Y = 3f, meat5Y = 8f;
    private float cake1X = 14f;
    private float cake1Y = 3f;

    boolean diagup1, diagdown1,diagdown2,diagup2;
    boolean up1,down1, up2,down2;

    public Puzzle3(int state,Hero player) throws SlickException{
        this.player = player;
    }

    private void initializeBlocked() {
        for (int l = 0; l < NUMBEROFLAYERS; l++) {
            String layerValue = cave3.getLayerProperty(l, "blocked", "false");
            if (layerValue.equals("true")) {
                for (int c = 0; c < cave3.getHeight(); c++) {
                    for(int r = 0; r < cave3.getWidth(); r++) {
                        if(cave3.getTileId(r, c, l) != 0) {
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
        cave3 = new TiledMap("res/background/puzzle1/Cave2Part1Final.tmx", "res/background/puzzle1");
        blocked = new boolean[cave3.getWidth()][cave3.getHeight()];
        initializeBlocked();

        meats = new Image("res/background/puzzle1/FOOD/Meat.png");
        hp = new Image("res/background/puzzle1/FOOD/heart.png");
        cake1 = new Image("res/background/puzzle1/FOOD/Cake.png");
        arrow = new Image("res/background/puzzle1/FOOD/Arrow.png");

        Image[] heroSteady = {new Image("res/characters/hero/0.png"),new Image("res/characters/hero/3.png"),new Image("res/characters/hero/4.png")};
        Image[] walkUp = {new Image("res/characters/hero/2.png"),new Image("res/characters/hero/11.png"),new Image("res/characters/hero/12.png")};
        Image[] walkLeft = {new Image("res/characters/hero/1.png"),new Image("res/characters/hero/9.png"),new Image("res/characters/hero/10.png")};
        Image[] walkRight = {new Image("res/characters/hero/R1.png"),new Image("res/characters/hero/R2.png"),new Image("res/characters/hero/R3.png")};
        Image[] walkDown = {new Image("res/characters/hero/0.png"),new Image("res/characters/hero/7.png"),new Image("res/characters/hero/8.png")};
        diagup1 = down1 = diagdown2 = down2 = true;
        diagdown1 = up1 = diagup2 = up2 = false;
        movingRight = new Animation(walkRight,duration,true);
        movingUp = new Animation(walkUp,duration,true);
        movingLeft = new Animation(walkLeft,duration,true);
        movingDown = new Animation(walkDown,duration,true);
        steady = new Animation(heroSteady,duration2, true);
        hero = steady;
        camera = new Camera(container,cave3);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        camera.translateGraphics();
        camera.drawMap(0);
        camera.drawMap(2);
        camera.drawMap(3);
        hero.draw(heroPositionX * 32,heroPositionY * 32);
        meats.draw(meat1X * 32, meat1Y * 32,25,25);
        meats.draw(meat2X * 32, meat2Y * 32,25,25);
        meats.draw(meat3X * 32, meat3Y * 32,25,25);
        meats.draw(meat4X * 32, meat4Y * 32,25,25);
        meats.draw(meat5X * 32, meat5Y * 32,25,25);
        cake1.draw(cake1X * 32, cake1Y * 32,25,25);
        arrow.draw(19 * TILESIZE, 8 * TILESIZE);
        g.drawString("hero X position: "+heroPositionX+"\nhero Y position: "+heroPositionY,400,200);
        g.drawString("HERO NAME: "+player.getName()+" ",100,100);
        g.drawString("HEALTH: ", 1 * TILESIZE, 11.8f * TILESIZE);

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

    }

    private void meatMove(int delta){
        //meat1
        if((int) meat1Y == 4 && (int) meat1X != 8){
            meat1X += delta * MEATSPEED;
            //moveright
        } else if((int) meat1X == 8 && (int) meat1Y != 6){
            meat1Y += delta * MEATSPEED;
            //movedown
        } else if((int) meat1Y == 6 && (int) meat1X != 3){
            //moveleft
            meat1X -= delta * MEATSPEED;
        } else if((int) meat1X == 3 && (int) meat1Y != 4){
            //moveup
            meat1Y -= delta * MEATSPEED;
        }
        //meat2
        if(diagup1 && (int) meat2X != 12 && (int) meat2Y != 1){
            meat2X += delta * MEATSPEED;
            meat2Y -= delta * MEATSPEED;
            diagdown1 = true;
        } else if(diagdown1 && (int) meat2X != 7 && (int) meat2Y != 6){
            meat2Y += delta * MEATSPEED;
            meat2X -= delta * MEATSPEED;
            diagup1 = false;
        } else{
            diagup1 = true;
        }
        //meat3
        if(down1 && (int) meat3Y != 9){
            //movedown
            meat3Y += delta * MEATSPEED;
            up1 = true;
        } else if(up1 && (int) meat3Y != 2){
            down1 = false;
            meat3Y -= delta * MEATSPEED;
            //moveup
        } else {
            down1 = true;
        }
        //meat4
        if((int) meat4Y == 3 && (int) meat4X != 20){            //move right
            meat4X += delta * MEATSPEED;
        } else if((int) meat4X == 20 && (int)meat4Y != 8){            //movedown
            meat4Y+= delta * MEATSPEED;
        } else if((int) meat4Y == 8 && (int) meat4X != 14){            //moveleft
            meat4X -= delta * MEATSPEED;
        } else if((int) meat4X == 14 && (int) meat4Y != 3){            //moveup
            meat4Y -= delta * MEATSPEED;
        }
        //meat5
        if(diagdown2 && (int) meat5Y != 12 && (int) meat5X != 3){
            meat5Y += delta * MEATSPEED;
            meat5X -= delta * MEATSPEED;
            diagup2 = true;
        } else if(diagup2 && (int) meat5X != 9 && (int) meat5Y != 8) {
            meat5X += delta * MEATSPEED;
            meat5Y -= delta * MEATSPEED;
            diagdown2 = false;
        } else{
            diagdown2 = true;
        }
        //cake1
        if(down2 && (int) cake1Y != 7){
            //movedown
            cake1Y += delta * MEATSPEED;
            up2 = true;
        } else if(up2 && (int) cake1Y != 2){
            cake1Y -= delta * MEATSPEED;
            down2 = false;
        } else {
            down2 = true;
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        Input input = gc.getInput();
        meatMove(delta);

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

        if(!(input.isKeyDown(input.KEY_UP)) &&!( input.isKeyDown(input.KEY_DOWN)) &&!(input.isKeyDown(input.KEY_LEFT))&&!(input.isKeyDown(input.KEY_RIGHT))){
            hero = steady;
        }
        if((int) heroPositionX == 0 && ((int) heroPositionY >= 9 && (int) heroPositionY <= 11))
            sbg.enterState(7);

        if((int) heroPositionY == 0 && ((int) heroPositionX >= 12 && (int) heroPositionX <= 14))
            sbg.enterState(9);
    }

    @Override
    public int getID(){
        return 8;
    }
}
