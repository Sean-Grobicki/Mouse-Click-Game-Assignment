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
  
  void newLevel()
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
  
  void populateList()
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
  
  void addDuck()
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
  
  void updateDucks()
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
  
  void update()
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
  
  boolean completeLevel()
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
