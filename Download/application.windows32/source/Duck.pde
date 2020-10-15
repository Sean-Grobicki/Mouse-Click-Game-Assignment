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
  void setActive(boolean b)
  {
      active = b;
  }
  
  void render()
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

  
  void move()
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
  
  void update(int uX,int uY)//parameters used so correct user position can be set by subclasses.
  {
      userX = uX;
      userY = uY;
      render();
      move();
  }
  
  boolean isHit()
  {
      if(sizeX > abs(x - userX) && sizeY > abs(y - userY))// returns true if duck collides with the user.
      {
          return true;
      }
      return false;
  }
}
