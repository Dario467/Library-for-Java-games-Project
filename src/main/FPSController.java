package main;

public class FPSController {
    private final double drawTime;
    private double delta;
    private long lastTime;

    public FPSController(int fps) {
        this.drawTime = (double) 1000000000 /fps;
        delta = 0;
        this.lastTime = System.nanoTime();
    }

    public boolean shouldUpdate(){
        long currentTime = System.nanoTime();
        delta += (currentTime-lastTime)/drawTime;
        lastTime = currentTime;
        if( delta >= 1){
            delta--;
            return true;
        }
        return false;
    }
}
