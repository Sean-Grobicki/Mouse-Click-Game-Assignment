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

void setup()
{
    background = loadImage("Images/background-1.png");
    size(800,600);
    level = new Level(1,5);
    level.populateList();
    lives = loadImage("Images/heart.gif");
}

void draw()
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
void gameBackground()
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
