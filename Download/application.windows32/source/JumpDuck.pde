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
    
    @Override void ability()
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
