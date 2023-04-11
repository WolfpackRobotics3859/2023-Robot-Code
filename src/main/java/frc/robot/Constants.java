// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.ArrayList;
import java.util.List;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import frc.lib.util.SwerveModuleConstants;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static final int kTimeoutMs = 30;

  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }

  public static class SwerveConstants {
    public static final int pigeonID = 42;
    public static final boolean invertGyro = false; // Always ensure Gyro is CCW+ CW-
    /* Drivetrain Constants */
    public static final double trackWidth = Units.inchesToMeters(20.35); //TODO: This must be tuned to specific robot //20.35
    public static final double wheelBase = Units.inchesToMeters(20.35); //TODO: This must be tuned to specific robot //15.25
    public static final double wheelCircumference = Units.inchesToMeters(4.0 * Math.PI);

    /* Swerve Kinematics 
     * No need to ever change this unless you are not doing a traditional rectangular/square 4 module swerve */
     public static final SwerveDriveKinematics swerveKinematics = new SwerveDriveKinematics(
        new Translation2d(wheelBase / 2.0, trackWidth / 2.0),
        new Translation2d(wheelBase / 2.0, -trackWidth / 2.0),
        new Translation2d(-wheelBase / 2.0, trackWidth / 2.0),
        new Translation2d(-wheelBase / 2.0, -trackWidth / 2.0));

        public static final SwerveDriveKinematics circleKinematics = new SwerveDriveKinematics(
          new Translation2d(wheelBase / 2.0, trackWidth / 2.0),
          new Translation2d(wheelBase / 2.0, -trackWidth / 2.0),
          new Translation2d(-wheelBase / 2.0, trackWidth / 2.0),
          new Translation2d(-wheelBase / 2.0, -trackWidth / 2.0)
          );

    /* Module Gear Ratios */
    public static final double driveGearRatio = (7.13 / 1.0);
    public static final double angleGearRatio = (15.43 / 1.0);

    /* Motor Inverts */
    public static final boolean angleMotorInvert = true;
    public static final boolean driveMotorInvert = false;

    /* Angle Encoder Invert */
    public static final boolean canCoderInvert = false;

    /* Swerve Current Limiting */
    public static final int angleContinuousCurrentLimit = 25;
    public static final int anglePeakCurrentLimit = 40;
    public static final double anglePeakCurrentDuration = 0.1;
    public static final boolean angleEnableCurrentLimit = true;

    public static final int driveContinuousCurrentLimit = 35;
    public static final int drivePeakCurrentLimit = 60;
    public static final double drivePeakCurrentDuration = 0.1;
    public static final boolean driveEnableCurrentLimit = true;

    /* These values are used by the drive falcon to ramp in open loop and closed loop driving.
     * We found a small open loop ramp (0.25) helps with tread wear, tipping, etc */
    public static final double openLoopRamp = 0.25;
    public static final double closedLoopRamp = 0.0;

    /* Angle Motor PID Values */
    //TODO: tune this to like not made up values, pls and thx
    public static final double angleKP = 0.6;
    public static final double angleKI = 0.0;
    public static final double angleKD = 12.0;
    public static final double angleKF = 0.0;

    /* Drive Motor PID Values */
    public static final double driveKP = 0.01; //TODO: This must be tuned to specific robot
    public static final double driveKI = 0.0;
    public static final double driveKD = 0.0;
    public static final double driveKF = 0.0;

    /* Drive Motor Characterization Values 
     * Divide SYSID values by 12 to convert from volts to percent output for CTRE */
    public static final double driveKS = (0.18342 / 12); //TODO: This must be tuned to specific robot
    public static final double driveKV = (2.2214 / 12);
    public static final double driveKA = (0.38641 / 12);

    /* Swerve Profiling Values */
    /** Meters per Second */
    public static final double maxSpeed = 2.5; //TODO: This must be tuned to specific robot
    /** Radians per Second */
    public static final double maxAngularVelocity = 10.0; //TODO: This must be tuned to specific robot

    /* Neutral Modes */
    public static final NeutralMode angleNeutralMode = NeutralMode.Coast;
    public static final NeutralMode driveNeutralMode = NeutralMode.Brake;

    /* Module Specific Constants */
    /* Front Left Module - Module 0 */
    public static final class Mod0 { //TODO: This must be tuned to specific robot
        public static final int driveMotorID = 1;
        public static final int angleMotorID = 2;
        public static final int canCoderID = 1;
        public static final Rotation2d angleOffset = Rotation2d.fromDegrees(55.3);
        public static final SwerveModuleConstants constants = 
            new SwerveModuleConstants(driveMotorID, angleMotorID, canCoderID, angleOffset);
    }

    /* Front Right Module - Module 1 */
    public static final class Mod1 { //TODO: This must be tuned to specific robot
        public static final int driveMotorID = 3;
        public static final int angleMotorID = 4;
        public static final int canCoderID = 2;
        public static final Rotation2d angleOffset = Rotation2d.fromDegrees(230.6);
        public static final SwerveModuleConstants constants = 
            new SwerveModuleConstants(driveMotorID, angleMotorID, canCoderID, angleOffset);
    }
    
    /* Back Left Module - Module 2 */
    public static final class Mod2 { //TODO: This must be tuned to specific robot
        public static final int driveMotorID = 5;
        public static final int angleMotorID = 6;
        public static final int canCoderID = 3;
        public static final Rotation2d angleOffset = Rotation2d.fromDegrees(316.6);
        public static final SwerveModuleConstants constants = 
            new SwerveModuleConstants(driveMotorID, angleMotorID, canCoderID, angleOffset);
    }

    /* Back Right Module - Module 3 */
    public static final class Mod3 { //TODO: This must be tuned to specific robot
        public static final int driveMotorID = 7;
        public static final int angleMotorID = 8;
        public static final int canCoderID = 4;
        public static final Rotation2d angleOffset = Rotation2d.fromDegrees(92.8);
        public static final SwerveModuleConstants constants = 
            new SwerveModuleConstants(driveMotorID, angleMotorID, canCoderID, angleOffset);
    }
  }

  public static class ThrowerConstants {

    public static final int primaryMotorID = 5;

    public static final boolean primaryMotorInverted = false;
    public static final boolean secondaryMotorInverted = false;

    //motion magic values 
    public static int travelCruiseVelocity = 1500;
    public static int travelAcceleration = 1500;
    public static int travelProfileSmoothing = 2;

    public static int throwConeHighCruiseVelocity = 1200; //cube shenanoigabs
    public static int throwConeHighAcceleration = 2350;
    public static int throwProfileSmoothing = 2;

    public static int throwConeLowCruiseVelocity = 400; //DO NOT CHANGE FOR THE LOVE OF GOD
    public static int throwConeLowAcceleration = 2050; 

    public static int throwCubeLowCruiseVelocity = 520; //old 4/1 11:35 420 and 1100
    public static int throwCubeLowAcceleration = 1100; 
    
    public static int throwCubeHighCruiseVelocity = 1200; 
    public static int throwCubeHighAcceleration = 2350; 

    //motion profile tolerance - how close the motor has to be to the target position to be considered "done" (measured in encoder ticks)
    public static int motionProfileTolerance = 15;

    //pid values
    public static double travelkP = 1.8; //1.2
    public static int travelkI = 0;
    public static int travelkD = 0;
    public static double travelkF = 0;

    public static double throwkP = 1.8; //0.2
    public static int throwkI = 0;
    public static int throwkD = 0;
    public static double throwkF = 0; //0.2

    //Encoder position values
    public static int loadPosition = 785;
    public static int preShootPosition = 900;
    public static int purgePosition = 200;
    public static int throwConeHighPosition = 900; //7500
    public static int throwConeLowPosition = 900; //3900

    public static int throwCubeHighPosition = 950; //7500
    public static int throwCubeLowPosition = 820;

    public static double centerThreshold = 0;

    
  }

  public static class HallwayConstants {
    
  }

  public static class AutoConstants {
    
    
    public static final double kMaxSpeedMetersPerSecond = 0.5;
        public static final double kMaxAccelerationMetersPerSecondSquared = 0.5;
        public static final double kMaxAngularSpeedRadiansPerSecond = Math.PI;
        public static final double kMaxAngularSpeedRadiansPerSecondSquared = Math.PI;
    
        public static final double kPXController = 1;
        public static final double kPYController = 1;
        public static final double kPThetaController = 1;
    
        /* Constraint for the motion profilied robot angle controller */
        public static final TrapezoidProfile.Constraints kThetaControllerConstraints =
            new TrapezoidProfile.Constraints(
                kMaxAngularSpeedRadiansPerSecond, kMaxAngularSpeedRadiansPerSecondSquared);
        
        
  }

  public static class PhotonVisionConstants {
    
  }

  public static class SecondaryVisionConstants {
    
  }
}
