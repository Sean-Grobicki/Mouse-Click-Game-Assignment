import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class duck_hunt extends PApplet {

Level level;
Gamemode gamemode = Gamemode.STARTMENU;
PImage background;
PImage lives;
int levelCounter = 0;
int gameCounter = 0;
String text = "";
enum Gamemode
{
    STARTMENU,PLAYGAME,NEWLEVEL,GAMEOVER
}

public void setup()
{
    background = loadImage("Images/background-1.png");
    
    level = new Level(1,5);
    level.populateList();
    lives = loadImage("Images/heart.gif");
}

public void draw()
{
  switch(gamemode)// enum and switch used to determine what gamemode it is in.
  {
    case STARTMENU:
      background(255,0,0);
      textSize(40);
      text("Welcome to Duck Hunt",170,250); 
      text("Click mouse to Start Game.",130,300);
      if(mousePressed && (mouseButton == LEFT))
      {
          gamemode = Gamemode.PLAYGAME;
      }
      break;
      case PLAYGAME:
        levelCounter = 0;
        gameCounter++;
        gameBackground();
        if(gameCounter > 8)
        {
          level.update();
          if(level.completeLevel())//changes to new level screen
           {
              gamemode = Gamemode.NEWLEVEL;
          }
          if(level.lives <= 0)//changes to gameover screen.
          {
              gamemode = Gamemode.GAMEOVER;
          }
        }
        break;
      case NEWLEVEL:
        levelCounter ++;
        background(255,0,0);
        textSize(40);
        text("You Completed Level "+ level.levelNumber,170,250); 
        text("Your score is "+ level.score,230,300);
        text("Click mouse to start new level.",100,350);
        if(mousePressed && (mouseButton == LEFT) && levelCounter > 60)
        {
          gamemode = Gamemode.PLAYGAME;
          level.newLevel();
          level.populateList();//will upgrade to next level and then add enemies to the list.
        }
        break;
      case GAMEOVER:
        levelCounter ++;
        gameCounter = 0;
        background(255,0,0);
        textSize(40);
        text("Game Over. You got to Level "+ level.levelNumber+".",120,250); 
        text("Your score was "+ level.score,230,300);
        text("Click mouse to play again.",150,350);
        if(mousePressed && (mouseButton == LEFT) && levelCounter > 60)
        {
            level = new Level(1,5);// create a new level to start new game.
            level.populateList();
            gamemode = Gamemode.PLAYGAME;
            text = "";
        }
        break;
  }
}
public void gameBackground()
  {
    // avoid repeated actions in creating the background with lives.
        image(background,0,0);
        textSize(20);
        text("Level "+ level.levelNumber,width - 80,20);
        int tempLives = level.lives;
        for(int i = 90; i >= 30 ;i-=30)
        {
            if(tempLives != 0)
            {
              image(lives,width - i,30);
              tempLives --;
            }
        }
        textSize(15);
        text("Score: " + level.score,width - 80,80);
  }
class Duck
{
  int x,y;
  int speed;
  PImage image1,image2,image3;
  PImage rImage1,rImage2,rImage3;
  int counter = 0;
  boolean active = false;
  int userX;
  int userY;
  int sizeX;
  int sizeY;
  boolean change = false;
  int score;
  boolean revert = false;
  
  Duck(int x, int y,int speed)
  {
    this.x = x;
    this.y = y;
    this.speed = speed;
    image1 = loadImage("Images/duck1.png");
    image2 = loadImage("Images/duck2.png");
    image3 = loadImage("Images/duck3.png");
    rImage1 = loadImage("Images/rduck1.png");
    rImage2 = loadImage("Images/rduck2.png");
    rImage3 = loadImage("Images/rduck3.png");
    sizeX = image1.width;
    sizeY = image1.height;
    score = 1;
  }
  public void setActive(boolean b)
  {
      active = b;
  }
  
  public void render()
  {
    // counter used to determine animation sequence.
      if(counter < 5)
      {
        if(revert)
        {
         image(rImage1,x,y);// rImage is for the reverse image when going in opposite direction.
        }
        else
        {
          image(image1,x,y);
        }
      }
      else if(counter < 15)
      {
         if(revert)
        {
         image(rImage2,x,y);
        }
        else
        {
          image(image2,x,y);
        }
      }
      else if(counter < 20)
      {
        if(revert)
        {
         image(rImage3,x,y);
        }
        else
        {
          image(image3,x,y);
        }
      }
      else
      {
        if(revert)
        {
         image(rImage3,x,y);
        }
        else
        {
          image(image3,x,y);
        }
        counter = 0;
      }
      counter ++;
  }

  
  public void move()
  {
      x+=speed;
      if(x >= width || x <= 0)
      {
        speed = -speed;
        y -= 137;
        revert = !revert;//revert used to use the correct direction for the duck to be animated in.
        change = true;// change set to true so jump animation knows when duck has moved up.
      }
      else
      {
          change = false;
      }
  }
  
  public void update(int uX,int uY)//parameters used so correct user position can be set by subclasses.
  {
      userX = uX;
      userY = uY;
      render();
      move();
  }
  
  public boolean isHit()
  {
      if(sizeX > abs(x - userX) && sizeY > abs(y - userY))// returns true if duck collides with the user.
      {
          return true;
      }
      return false;
  }
}
class Explosion
{
    PImage image1 = loadImage("Images/explosion1.png");
    PImage image2 = loadImage("Images/explosion2.png");
    PImage image3 = loadImage("Images/explosion3.png");
    int counter = 0;
    int x;
    int y;
    
    
    Explosion(int x,int y)
    {
        this.x = x;
        this.y = y;
    }
    
    public boolean render()
    {
        imageMode(CENTER);// used so explosion  animation appears in centre.
        if(counter < 3)//counter used to determine animation sequence;
        {
            image(image1,x,y);
        }
        else if(counter < 6)
        {
            image(image2,x,y);
        }
        else if (counter < 9) 
        {
            image(image3,x,y);
        }
        else
        {
            imageMode(CORNERS);
            return false;
        }
        counter ++;
        imageMode(CORNERS);
        return true; //returns true while explosion is still rendering.
    }
    
}
class JumpDuck extends SpeedDuck// it inherits from speed duck so there is no need to override update again.
{
    boolean inProgress = false;
    boolean up = true;
    int startY;
    
    JumpDuck(int x,int y,int speed)
    {
        super(x,y,speed);
        image1 = loadImage("Images/jumpduck1.png");
        image2 = loadImage("Images/jumpduck2.png");
        image3 = loadImage("Images/jumpduck3.png");
        rImage1 = loadImage("Images/rjumpduck1.png");
        rImage2 = loadImage("Images/rjumpduck2.png");
        rImage3 = loadImage("Images/rjumpduck3.png");
        score = 2;
    }
    
    public @Override void ability()
    {
        if(change)//if theres a change in height means duck will go back to correct height.
        {
            startY -= 137;
        }
        if(inProgress)// These determine the jumping animation sequence for these ducks.
        {
            if(startY <= y)
            {
                inProgress = false;
            }
            else if(y <= (startY - 40))
            {
                up = false;
                y+=3;
            }
            else if(up)
            {
                y-=3;
            }
            else
            {
                y+=3;
            }
        }
        else
        {
            if(abs(userX - x) < sizeX && abs(userY - y) < sizeY)// starts the animation if the user hovers over it.
            {
                inProgress = true;
                up = true;
                startY = y;
                y-=3;
            }
        }
    }
}
class Level
{
  int levelNumber;
  int enemies;
  int speed = -3;
  int startX = width-10;
  int startY = 340;
  int score = 0;
  ArrayList<Duck> ducks;
  User user = new User();
  int currDuck = 0;
  int duckTimer = 180;
  int startTimer = 180;
  boolean allActive = false;
  Target target = new Target(0,60);
  int lives = 3;
  
  Level(int levelNumber, int enemies)
  {
    this.levelNumber = levelNumber;
    this.enemies = enemies;
  }
  
  public void newLevel()
  {
    //start of each level will create the speed and number of enemies in array.
    levelNumber ++;
    enemies += 1;
    speed --;
    currDuck = 0;
    allActive = false;
    if(startTimer > 40)
    {
        startTimer -= 20;
    }
    duckTimer = startTimer;
    score += 20;
  }
  
  public void populateList()
  {
       ducks = new ArrayList<Duck>();// polymorphism used to add the 3 diiferent ducks into same array all treated like enemies.
       float rand = 0;
       int count = 0;
       while(count < enemies)
       {
         rand = random(0,30);// randomly adds different ducks to array with norma duck being most likely.
         if(rand < 20)
         {
             ducks.add(new Duck(startX,startY,speed));
         }
         else if(rand < 25)
         {
             ducks.add(new SpeedDuck(startX,startY,speed));
         }
         else
         {
             ducks.add(new JumpDuck(startX,startY,speed));
         }
         count ++;
       }
  }
  
  public void addDuck()
  {
      if(duckTimer > startTimer && currDuck < enemies)// makes each duck appear on the screen with a delay.
      {
          ducks.get(currDuck).setActive(true);
          currDuck ++;
          duckTimer = 0;
      }
      if(currDuck == enemies)// called when all ducks on screen.
      {
          allActive = true;
      }
      
  }
  
  public void updateDucks()
  {
      if(!allActive)// checks if it's time to add duck until all appear.
      {
        addDuck();
        duckTimer ++;
      }
      for(int i = 0;i < ducks.size();i ++)// updates all the ducks in array that are active.
      {
        if(ducks.get(i).active)
        {
          ducks.get(i).update(user.x,user.y);
        }
      }
  }
  
  public void update()
  {
      updateDucks();
      user.update();
      for(int i = 0;i < ducks.size();i ++)
      { 
         if(ducks.get(i).isHit() && ducks.get(i).active && user.fired)// checks if the ducks have been hit and if they are increases score and sets them to not active.
         {
             score += ducks.get(i).score;
             ducks.get(i).setActive(false);
         }
      }
      target.render();
      if(target.gameOver(ducks))// decreases lives if duck reaches end.
      {
        lives --;
      }
  }
  
  public boolean completeLevel()
  {
       if(allActive)
       {
           for(Duck d : ducks)// checks to see if level is completed if all ducks are inactive.
           {
              if(d.active)
               {
                   return false;
               }
           }
           return true;
       }
       return false;
  }
}
class SpeedDuck extends Duck
{
    SpeedDuck(int x,int y,int speed)
    {
        super(x,y,speed);
        image1 = loadImage("Images/speedduck1.png");
        image2 = loadImage("Images/speedduck2.png");
        image3 = loadImage("Images/speedduck3.png");
        rImage1 = loadImage("Images/rspeedduck1.png");
        rImage2 = loadImage("Images/rspeedduck2.png");
        rImage3 = loadImage("Images/rspeedduck3.png");
        score = 3;
    }
    
    public @Override void update(int uX,int uY)//update overidden so ability method can be called.
    {
        super.update(uX,uY);
        ability();  
    }
    
    public void ability()
    {
        if(abs(userX - x) < sizeX && abs(userY - y) < sizeY)// increases speed of duck if user hovers over them.
        {
            x-=2;
        }
    }
}
class Target
{
    PImage bullseye;
    int x;
    int y;
    int size = 80;
    
    Target(int x,int y)
    {
        bullseye = loadImage("Images/bullseye.png");
        this.x = x;
        this.y = y;
    }
    
    public void render()
    {
        image(bullseye,x,y);
    }
    
    public boolean gameOver(ArrayList<Duck> ducks)
    {
        for(int i = 0;i < ducks.size();i ++)// checks if any of the ducks have collided with them and sets them to not active if they collide.
        {
            if(abs(x-ducks.get(i).x) < size && abs(y-ducks.get(i).y) < size && ducks.get(i).active == true)
            {
                ducks.get(i).setActive(false);
                return true;      
            }
      }
        return false;
    }  
}
class User
{
    PImage crossHair;
    PImage shotgun;
    int x = width/2;
    int y = height/2;
    int size = 80;
    boolean fired = false;
    Explosion e;
    
    User()
    {
       crossHair = loadImage("Images/crosshair.png");
       shotgun = loadImage("Images/shotgun.png");
    }
    
    public void render()
    {
        imageMode(CENTER);
        image(crossHair,x,y);
        if(x < 120)
        {
          image(shotgun,120,height-20);
        }
        else
        {
          image(shotgun,x,height-20);
        }
        
        imageMode(CORNERS);
    }
    
    public void move()
    {
      // moves the crosshair towards the cursor to determine the position of crosshair.
        if(mouseX < x+(size/4) && mouseX > x-(size/4))
        {
            
        }
        else if(mouseX < x)
        {
            x -= 3;
        }
        else
        {
            x += 3;
        }
        if(mouseY < y+(size/4) && mouseY > y-(size/4))
        {
            
        }
        else if(mouseY < y)
        {
            y -= 3;
        }
        else
        {
            y += 3;
        }
    }
    
    public void update()
    {
        move();
        render();
        fire();
    }
    
    public void fire()
    { 
      if(mousePressed && (mouseButton == LEFT))//creates the explosion animation if fired and checks other animation complete before firing again.
      {
        fired = true;
        e = new Explosion(x,y);
      }
      else
      {
          fired = false;
      }
      if(e != null)
      {
          if(!e.render())
          {
              e = null;
          }
      }
    }
    
    public boolean overDuck(Duck d)
    {
        if(abs(d.x - x) < size && abs(d.y - y) < size)// returns true if the crosshair is over the duck.
        {
              return true;
        }
        return false;
    }


}
  public void settings() {  size(800,600); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "duck_hunt" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
