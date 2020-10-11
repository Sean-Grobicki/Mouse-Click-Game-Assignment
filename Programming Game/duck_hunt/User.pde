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
    
    void render()
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
    
    void move()
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
    
    void update()
    {
        move();
        render();
        fire();
    }
    
    void fire()
    { 
      if(mousePressed && (mouseButton == LEFT))//creates the explosion animation if fired and checks other animation complete before firing again.
      {
        fired = true; //<>//
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
    
    boolean overDuck(Duck d)
    {
        if(abs(d.x - x) < size && abs(d.y - y) < size)// returns true if the crosshair is over the duck.
        {
              return true;
        }
        return false;
    }


}
