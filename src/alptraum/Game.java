package alptraum;
/**
 * Created by User on 10/19/2016.
 */
import alptraum.puzzles.level1.level1.*;
import alptraum.puzzles.level1.level2.*;
import alptraum.puzzles.level1.level3.*;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Game extends StateBasedGame{
    public static final String gamename = "Alptraum's Cure";
    public static final int MENU = 0;
    public static final int PLAY = 1;
    public static final int REALWORLD = 2;
    public static final int HOUSE1 = 3;
    public static final int HOUSE2 = 4;
    public static final int HOUSE3 = 5;
    public static final int PUZZLE1 = 6;
    public static final int PUZZLE2 = 7;
    public static final int PUZZLE3 = 8;
    public static final int PATH1 = 9;
    public static final int PUZZLE4 = 10;
    public static final int SECROOM = 11;
    public static final int PUZZLE5 = 12;
    public static final int PUZZLE6 = 13;
    public static final int PUZZLE7 = 14;
    //public static final int TESTING = 12;

    private Hero character;

    public Game(String gamename) throws SlickException { //states ni of our game
        super(gamename);
        character = new Hero("Mike");
        this.addState(new Menu(MENU));
        this.addState(new Play(PLAY,character));
        this.addState(new RealWorld(REALWORLD,character));
        this.addState(new House1(HOUSE1,character));
        this.addState(new House2(HOUSE2,character));
        this.addState(new House3(HOUSE3,character));
        this.addState(new House3(HOUSE3,character));
        this.addState(new Puzzle2(PUZZLE2,character));
        this.addState(new Puzzle1(PUZZLE1,character));
        this.addState(new Puzzle3(PUZZLE3,character));
        this.addState(new Puzzle4(PUZZLE4,character));
        this.addState(new Path(PATH1,character));
        this.addState(new SecretRoom(SECROOM,character));
        this.addState(new Puzzle5(PUZZLE5,character));
        this.addState(new Puzzle6(PUZZLE6,character));
        this.addState(new Puzzle7(PUZZLE7,character));
    }

    @Override
    public void initStatesList(GameContainer gc) throws SlickException { //needed by StateBasedGame //GameContainer is responsible for the frame rate, input system, main game loop, etc.
        //need to give a background
        this.getState(MENU).init(gc,this);
        this.getState(PLAY).init(gc,this);
        this.getState(REALWORLD).init(gc,this);
        this.getState(HOUSE1).init(gc,this);
        this.getState(HOUSE2).init(gc,this);
        this.getState(HOUSE3).init(gc,this);
        this.getState(PUZZLE1).init(gc,this);
        this.getState(PUZZLE2).init(gc,this);
        this.getState(PUZZLE3).init(gc,this);
        this.getState(PUZZLE4).init(gc,this);
        this.getState(PUZZLE5).init(gc,this);
        //this.getState(TESTING).init(gc,this);
        this.getState(PATH1).init(gc,this);
        this.getState(SECROOM).init(gc,this);
        this.enterState(PATH1);          //first needed to show
    }

    public static void main(String[] args){
        AppGameContainer appgc;
        try{
            appgc = new AppGameContainer(new Game(gamename));
            appgc.setDisplayMode(720, 405, false);
            //appgc.setVSync(true);
            //appgc.setTargetFrameRate(60);
            appgc.start();
        }catch(SlickException e){
            e.printStackTrace();
        }
    }
}