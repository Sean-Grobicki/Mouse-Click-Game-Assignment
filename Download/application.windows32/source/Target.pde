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
    
    void render()
    {
        image(bullseye,x,y);
    }
    
    boolean gameOver(ArrayList<Duck> ducks)
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
