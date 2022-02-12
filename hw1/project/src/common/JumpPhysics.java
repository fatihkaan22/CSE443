package common;

public class JumpPhysics {
  static double JUMP_FACTOR = 140;

  public static double getNextPosByElapsedTime(double initialVelocity, double deltaT, int ground) {
    // delta_x = v_0 * t - 1/2 * g * t^2
    double deltaX = initialVelocity * deltaT - 0.5 * Util.GRAVITATIONAL_ACCELERATION * Math.pow(deltaT, 2);
    double position = ground - deltaX * JUMP_FACTOR;
    return Math.min(position, ground);
  }

  public static double getTimeInAir(double initialVelocity) {
    return 2 * initialVelocity / Util.GRAVITATIONAL_ACCELERATION;
  }

  public static double getDeltaTInSeconds(long start, long end) {
    return (start - end) / 1000000000.0;
  }

}
