package cc.forestadventure.scene;

import java.util.ArrayList;
import java.util.List;

import cc.adventure.sprite.Character;
import cc.adventure.sprite.Explosion;
import cc.adventure.sprite.Shrub;
import cc.adventure.sprite.Vine;
import cc.adventure.sprite.Background;
import cc.adventure.sprite.Boulder;
import cc.adventure.sprite.Bullet;
import cc.forestadventure.Director;
import cc.forestadventure.util.Direction;
import cc.forestadventure.util.Group;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GameScene {
	private Canvas canvas = new Canvas(Director.WIDTH, Director.HEIGHT);
	private GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
	
	private KeyProcess keyProcess = new KeyProcess();
	private Refresh refresh = new Refresh();
	private boolean running = false;
	
	private Background background = new Background();
	private Character self = new Character(949, 928, Group.green, Direction.stop, Direction.down,this);//the size of picture not sure yet
	public List<Bullet> bullets = new ArrayList<>();
    public List<Character> characters = new ArrayList<>();
    public List<Explosion> explosion = new ArrayList<>();
    public List<Vine>  vines = new ArrayList<>();
	public List<Boulder> boulders = new ArrayList<>();
	public List<Shrub> shrubs = new ArrayList<>();
		
	private void paint() {
		background.paint(graphicsContext);
		self.paint(graphicsContext);
		self.impactCharacter(characters);
		self.impactCharacter(vines);
		self.impactCharacter(boulders); //newly added
		
		 for (int i = 0; i < bullets.size(); i++) {
	            Bullet bullet = bullets.get(i);
	            bullet.paint(graphicsContext);
	            bullet.impactCharacter(characters);
	            bullet.impactVine(vines);
	            bullet.impactBoulder(boulders); 
	        }
		 for (int i = 0; i < characters.size(); i++) {
			 Character character = characters.get(i);
			 character.paint(graphicsContext);
			 character.impactCharacter(vines);
	         character.impactCharacter(self);
	         character.impactCharacter(boulders); //newly added after Boulder Class is created
	         character.impactCharacter(characters);//newly added
	         
	        }
		 for (int i = 0; i < explosion.size(); i++) {
			    Explosion e = explosion.get(i);
	            e.paint(graphicsContext);
	        }
		 
		 for (int i = 0; i < vines.size(); i++) {
	            Vine vine = vines.get(i);
	            vine.paint(graphicsContext);
	        }
		 
		 for (int i = 0; i < boulders.size(); i++) {
			 Boulder boulder = boulders.get(i);
			 boulder.paint(graphicsContext);
		 }
		 
		 for (int i = 0; i < shrubs.size(); i++) {
			 Shrub shrub = shrubs.get(i);
			 shrub.paint(graphicsContext);
		 }
		 
       graphicsContext.setFill(Color.RED);
       graphicsContext.setFont(new Font(10));
       graphicsContext.fillText("Number of Monsters：" + characters.size(), 200, 60);
       //graphicsContext.fillText("Number of Bullets子弹的数量：" + bullets.size(), 200, 90);
		}
		
	
	
	public void init(Stage stage) {
		AnchorPane root = new AnchorPane(canvas);
		stage.getScene().setRoot(root);
		stage.getScene().setOnKeyReleased(keyProcess);
		stage.getScene().setOnKeyPressed(keyProcess);
		running = true;
		initSprite();
		refresh.start();
	}
	
	private void initSprite() {
        for (int i = 0; i < 6; i++) {
            Character character = new Character(200 + i * 80, 100,Group.red, Direction.stop, Direction.down, this);//monster
            characters.add(character);
        }
        for (int i = 0; i < 20; i++) {
            Vine vine1 = new Vine(100 + i* 31, 200 );
            Vine vine2 = new Vine(100 + i* 31, 232 );
            vines.add(vine1);
            vines.add(vine2);
        }
        
        for (int i = 0; i < 5; i++) {
        	Boulder boulder = new Boulder(300 +i*80,300);
        	boulders.add(boulder);
        }
        
        for (int i = 0;i < 4; i++) {
        	Shrub shrub = new Shrub(350 + i*50, 400);
        	shrubs.add(shrub);
        }
	}
	
	public void clear(Stage stage) {
		stage.getScene().
		stage.getScene().removeEventHandler(KeyEvent.KEY_RELEASED, keyProcess);
		refresh.stop();
	}
	
	private class Refresh extends AnimationTimer {
		@Override
		public void handle(long now) {
			if (running) {
			paint();
			}
		}
	}
	
	private class KeyProcess implements EventHandler<KeyEvent> {

        @Override
        public void handle(KeyEvent event) {
            KeyCode keyCode = event.getCode();


            if(event.getEventType() == KeyEvent.KEY_RELEASED) {
                if(keyCode.equals(KeyCode.SPACE)) {
                    pauseOrContinue();
                }
                if(self != null) self.released(keyCode);
            }else if(event.getEventType() == KeyEvent.KEY_PRESSED) {
                if(self != null) {
                	self.pressed(keyCode);
                }
            }
        }
    }
	
	
	public void pauseOrContinue() {
		if(running) {
			running = false;
		}else {
			running = true;
		}
	}
		

}
