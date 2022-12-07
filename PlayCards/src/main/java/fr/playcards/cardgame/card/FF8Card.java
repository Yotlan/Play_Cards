package fr.playcards.cardgame.card;

//Import part
import fr.playcards.client.Client;

public class FF8Card implements Card {
    public String name;
    public int level;
    public int up;
    public int right;
    public int down;
    public int left;
    public String element;
    public Client owner;

    public FF8Card(String name, int level, int up, int right, int down, int left, String element) {
        this.name = name;
        this.level = level;
        this.up = up;
        this.right = right;
        this.down = down;
        this.left = left;
        this.element = element;
        this.owner = null;
    }

    public String getName() {
        return this.name;
    }
    public int getLevel(){
        return this.level;
    }
    public int getUp() {
        return this.up;
    }
    public int getRight() {
        return this.right;
    }
    public int getDown() {
        return this.down;
    }
    public int getLeft() {
        return this.left;
    }
    public String getElement() {
        return this.element;
    }
    public Client getOwner() {
        return this.owner;
    }
    public void setOwner(Client newOwner) {
        this.owner = newOwner;
    }

}
