// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }

  public static class SwerveConstants {
    public static SwerveDriveKinematics kinematics;
  }

  public static class ThrowerConstants {
    
  }

  public static class HallwayConstants {
    
  }

  public static class AutoConstants {
    
  }

  public static class PhotonVisionConstants {
      public static final double cameraHeightInches = 31; //replace when robot is done
      public static final double cameraAngle = 42; //replace
      public static final double goalTag = 50; //replace
      public static final double itemTag = 70; //replace
      public static Transform3d cameraToRobot = new Transform3d(); //replace
  }

  public static class SecondaryVisionConstants {
    
  }
}
