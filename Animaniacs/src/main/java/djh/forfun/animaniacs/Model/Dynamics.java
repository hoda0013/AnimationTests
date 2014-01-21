package djh.forfun.animaniacs.Model;

import android.util.FloatMath;

/**
 * Created by dillonhodapp on 1/20/14.
 */
public class Dynamics {

    private static final float TOLERANCE = .01f;

    private float spring;
    private float damping;
    private float position;
    private float velocity;
    private long lastTime = 0;
    private float targetPosition;

    public Dynamics(float spring, float dampingRatio){
        this.damping = dampingRatio * 2 * FloatMath.sqrt(spring);
        this.spring = spring;
    }

    public void setPosition(float position, long now){
        this.position = position;
        lastTime = now;
    }

    public void setVelocity(float velocity, long now){
        this.velocity = velocity;
        lastTime = now;
    }

    public void setTargetPosition(float targetPos, long now){
        this.targetPosition = targetPos;
        lastTime = now;
    }

    public void update(long now){
        float dt = Math.min(now - lastTime, 50) / 1000f;
        float x = position - targetPosition;
        float accel = -spring * x - damping * velocity;

        velocity += accel * dt;
        position += velocity * dt;

        lastTime = now;
    }

    public void setState(float point, int initialVelocity, long startTime){
        position = point;
        velocity = initialVelocity;
        lastTime = startTime;
    }

    public void setTargetPosition(float point){
        targetPosition = point;
    }



    public boolean isAtRest(){
        boolean standingStill = Math.abs(velocity) < TOLERANCE;
        boolean isAtTarget = Math.abs(targetPosition - position) < TOLERANCE;
        return standingStill && isAtTarget;
    }

    public float getPosition(){
        return position;
    }
}
