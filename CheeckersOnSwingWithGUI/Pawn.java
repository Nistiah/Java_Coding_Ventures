

public class Pawn {
    public int color;  //border 8, no pawn 0, black 1, white 2
    public int type=0;   //normal 0, queen? 1

    public void setBorderNoPawn(){
        this.color=8;
    }
    public void setNUllPawn(){
        this.color=0;
    }
    public void setBlackPawn(){
        this.color=1;
    }
    public void setWhitePawn(){
        this.color=2;
    }
    public void removePawn(){}


}
