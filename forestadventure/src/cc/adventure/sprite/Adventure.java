package cc.adventure.sprite;


import java.util.Random;

import cc.forestadventure.Director;
import cc.forestadventure.scene.GameScene;
import cc.forestadventure.util.Direction;
import cc.forestadventure.util.Group;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Adventure extends Role{
	
	Direction pdir;
	boolean keyup, keydown, keyleft, keyright;
    double oldx, oldy;
    public static Random random = new Random();
	
    public Adventure(double x, double y, double width, double height, Group group, Direction dir, GameScene gameScene) {
		super(x, y, 60, 60, group, dir, gameScene);
		// the size 60-60 match the picture
		// TODO Auto-generated constructor stub
		this.pdir = pdir;
        speed = 5;
        if(group.equals(Group.green)) {//adventure
            imageMap.put("up", new Image("resources/adventure-up.png"));
            imageMap.put("down", new Image("resources/adventure-1.png"));
            imageMap.put("left", new Image("resources/adventure-left.png"));
            imageMap.put("right", new Image("resources/adventure-rigrht.png"));
        } else {
            imageMap.put("up", new Image("image/monter-1.png"));
            imageMap.put("down", new Image("image/monter-1.png"));
            imageMap.put("left", new Image("image/monter-2.png"));
            imageMap.put("right", new Image("image/monter-2.png"));
        }
		
	}

    public void pressed(KeyCode keyCode) {
        switch (keyCode) {
            case UP:
                keyup = true;
                break;
            case DOWN:
                keydown = true;
                break;
            case LEFT:
                keyleft = true;
                break;
            case RIGHT:
                keyright = true;
        }
        redirect();
    }

    public void released(KeyCode keyCode) {
        switch (keyCode) {
            case F:
//                openFire(); do later
                break;
            case UP:
                keyup = false;
                break;
            case DOWN:
                keydown = false;
                break;
            case LEFT:
                keyleft = false;
                break;
            case RIGHT:
                keyright = false;
        }
        redirect();
    }

    public void redirect() {
        if(keyup && !keydown && !keyleft && !keyright) dir = Direction.up;
        else if(!keyup && keydown && !keyleft && !keyright) dir = Direction.down;
        else if(!keyup && !keydown && keyleft && !keyright) dir = Direction.left;
        else if(!keyup && !keydown && !keyleft && keyright) dir = Direction.right;
        else if(!keyup && !keydown && !keyleft && !keyright) dir = Direction.stop;
    }
    
    
    @Override
    public void move() {
        oldx = x;
        oldy = y;
        switch (dir) {
            case up:
                y -= speed;
                break;
            case down:
                y += speed;
                break;
            case left:
                x -= speed;
                break;
            case right:
                x += speed;
                break;
        }

        if(dir != Direction.stop) {
            pdir = dir;
        }

        if(x < 0) x = 0;
        if(y < 0) y = 0;
        if(x > Director.WIDTH - width - 5) x = Director.WIDTH - width - 5;
        if(y > Director.HEIGHT - height - 30) y = Director.HEIGHT - height - 30;

        if(group.equals(Group.red)) {
            int i = random.nextInt(60);
            switch (i) {
                case 15:
                    Direction d[] = Direction.values();
                    dir = d[random.nextInt(d.length)];
                    break;
                case 30:
//                    openFire(); do later
                    break;
            }
        }
    }


}