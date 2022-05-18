public class Time {
    private int hour;
    private int minute;
    public int dzien=0;
    private String notification="";

    public int setTime(int hour, int minute){
        if(hour<0||hour>24||minute<0||minute>60){
            return -1;
        }
        this.hour=hour;
        this.minute=minute;

        return 0;
    }
    public int getTime(){
        return this.hour*3600+this.minute*60;
    }
    public void setNotification(String notification){
        this.notification=notification;
    }
    public String getNotification(){
        return this.notification;
    }


}
