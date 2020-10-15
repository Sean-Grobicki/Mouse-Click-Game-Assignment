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
    
    @Override void update(int uX,int uY)//update overidden so ability method can be called.
    {
        super.update(uX,uY);
        ability();  
    }
    
    void ability()
    {
        if(abs(userX - x) < sizeX && abs(userY - y) < sizeY)// increases speed of duck if user hovers over them.
        {
            x-=2;
        }
    }
}
