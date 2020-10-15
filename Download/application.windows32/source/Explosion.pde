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
    
    boolean render()
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
