package alptraum.puzzles.level1.level1;

/**
 * Created by niervin on 11/26/2016.
 */
import alptraum.Character;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Ghost extends Character {
    Image[] ghostPic = {new Image("res/characters/Ghost/0.png"), new Image("res/characters/Ghost/7.png"), new Image("res/characters/Ghost/15.png")};;

    public Ghost() throws SlickException{
        super("Rens Arrews");
        setMessages();
    }
    public void setImages(){ super.setImages( new int[]{0,2,3,5,1,2,4,3,2});}

    public Image getImage(int i){
        return ghostPic[i];
    }

    public void setMessages(){
        String[] dialogue = new String[13];
        dialogue[0] = "Hiya!";
        dialogue[1] = "Looks like this is a dangerous level to overcome.";
        dialogue[2] = "But don't worry!";
        dialogue[3] = "As long as you avoid the moving swords, you'll be fineeee.";
        dialogue[4] = "Ngehehehehehe!!";

        dialogue[5] = "Can dogs see ghosts?";
        dialogue[6] = "A dog just barked at me when I took a piss.";
        dialogue[7] = "Boy, was it scary.";
        dialogue[8] = "If I saw that dog again, I might hide behind a tree.";
        dialogue[9] = "Ngek";

        dialogue[10] = "If you have almost run out of HP.";
        dialogue[11] = "try to take that cake over there.";
        dialogue[12] = "Perhaps it may restore your health a bit.";

        super.setMessages(dialogue);
    }
    public String Interact(int i) {
        if (i < 13) {
            return messages[getMessages()];

        } else {

            return messages[9];
        }
    }
}
