// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.routines;

import java.util.ArrayList;
import java.util.function.Supplier;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;
import frc.robot.autos.DriveToPose;
import frc.robot.commands.drive.LimelightCenterCommand;
import frc.robot.commands.drive.SquareCommand;
import frc.robot.commands.hallway.ArmsOutCommand;
import frc.robot.commands.thrower.HomingCommand;
import frc.robot.commands.thrower.ThrowCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.HallwaySubsystem;
import frc.robot.subsystems.ThrowerSubsystem;
import frc.robot.utils.Position;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AutoShootRoutine extends SequentialCommandGroup {
  /** Creates a new AutoShootRoutine. */
  ArrayList<Pose2d> conePoses = new ArrayList<Pose2d>();

  ArrayList<Pose2d> cubePoses = new ArrayList<Pose2d>();
  
  DriveSubsystem driveSubsystem;
  HallwaySubsystem hallwaySubsystem;
  ThrowerSubsystem throwerSubsystem;
  Position position;
  public Pose2d pickPose() {
    //tag 1
  conePoses.add(new Pose2d(new Translation2d(1.98, 6.39-0.58), new Rotation2d(Units.degreesToRadians(180))));
  cubePoses.add(new Pose2d(new Translation2d(1.98, 6.93), new Rotation2d(Units.degreesToRadians(180))));
  conePoses.add(new Pose2d(new Translation2d(1.98, 6.93+0.58), new Rotation2d(Units.degreesToRadians(180))));
  //tag 2
  conePoses.add(new Pose2d(new Translation2d(1.98, 5.27-0.58), new Rotation2d(Units.degreesToRadians(180))));
  cubePoses.add(new Pose2d(new Translation2d(1.98, 5.27), new Rotation2d(Units.degreesToRadians(180))));
  conePoses.add(new Pose2d(new Translation2d(1.98, 5.72+0.58), new Rotation2d(Units.degreesToRadians(180))));
  //tag 3
  conePoses.add(new Pose2d(new Translation2d(1.98, 3.64-0.58), new Rotation2d(Units.degreesToRadians(180))));
  cubePoses.add(new Pose2d(new Translation2d(1.98, 3.64), new Rotation2d(Units.degreesToRadians(180))));
  conePoses.add(new Pose2d(new Translation2d(1.98, 3.64+0.58), new Rotation2d(Units.degreesToRadians(180))));

  //tag 6
  conePoses.add(new Pose2d(new Translation2d(14.57, 3.60+0.58), new Rotation2d(Units.degreesToRadians(0))));
  cubePoses.add(new Pose2d(new Translation2d(14.57, 3.60), new Rotation2d(Units.degreesToRadians(0))));
  conePoses.add(new Pose2d(new Translation2d(14.57, 3.60-0.58), new Rotation2d(Units.degreesToRadians(0))));
  //tag 7 
  conePoses.add(new Pose2d(new Translation2d(14.57, 5.25+0.58), new Rotation2d(Units.degreesToRadians(0))));
  cubePoses.add(new Pose2d(new Translation2d(14.57, 5.25), new Rotation2d(Units.degreesToRadians(0))));
  conePoses.add(new Pose2d(new Translation2d(14.57, 5.25-0.58), new Rotation2d(Units.degreesToRadians(0))));
  //tag 8
  conePoses.add(new Pose2d(new Translation2d(14.57, 6.92+0.58), new Rotation2d(Units.degreesToRadians(0))));
  cubePoses.add(new Pose2d(new Translation2d(14.57, 6.92), new Rotation2d(Units.degreesToRadians(0))));
  conePoses.add(new Pose2d(new Translation2d(14.57, 6.92-0.58), new Rotation2d(Units.degreesToRadians(0))));
    Pose2d closestPose = new Pose2d(new Translation2d(9999,9999), new Rotation2d());
  
  if(position == Position.THROW_CONE_HIGH || position == Position.THROW_CONE_LOW) {
    closestPose = new Pose2d();
    for (Pose2d x : conePoses) {
      if(driveSubsystem.getPose().getTranslation().getDistance(x.getTranslation()) < driveSubsystem.getPose().getTranslation().getDistance(closestPose.getTranslation())) {
        closestPose = x;
        System.out.println("Pose closer - new pose " + x.toString());
      } else {
        System.out.println("Pose not closer " + Double.toString(driveSubsystem.getPose().getTranslation().getDistance(x.getTranslation())) + " - pose was " + x.toString());
      }
    }
    SmartDashboard.putString("Chosen Pose", closestPose.toString());
  } else if(position == Position.THROW_CUBE_HIGH || position == Position.THROW_CUBE_LOW) {
    closestPose = new Pose2d();
    for (Pose2d x : cubePoses) {
      if(driveSubsystem.getPose().getTranslation().getDistance(x.getTranslation()) < driveSubsystem.getPose().getTranslation().getDistance(closestPose.getTranslation())) {
        System.out.println("Pose closer - new pose " + x.toString());
        closestPose = x;
      } else {
        System.out.println("Pose not closer " + Double.toString(driveSubsystem.getPose().getTranslation().getDistance(x.getTranslation())) + " - pose was " + x.toString());
      }
    }
    SmartDashboard.putString("Chosen Pose", closestPose.toString());
  }
  return closestPose;
}

  public AutoShootRoutine(Pose2d poseToGoSupplier, Position position, DriveSubsystem driveSubsystem, HallwaySubsystem hallwaySubsystem, ThrowerSubsystem throwerSubsystem) {
    this.position = position;
    this.driveSubsystem = driveSubsystem;
    this.hallwaySubsystem = hallwaySubsystem;
    this.throwerSubsystem = throwerSubsystem;
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    /*//tag 1
    poses.add(new Pose2d(new Translation2d(2.02, 6.39), new Rotation2d(Units.degreesToRadians(180))));
    poses.add(new Pose2d(new Translation2d(2.02, 6.93), new Rotation2d(Units.degreesToRadians(180))));
    poses.add(new Pose2d(new Translation2d(2.02, 7.44), new Rotation2d(Units.degreesToRadians(180))));
    //tag 2
    poses.add(new Pose2d(new Translation2d(2.02, 4.74), new Rotation2d(Units.degreesToRadians(180))));
    poses.add(new Pose2d(new Translation2d(2.02, 5.25), new Rotation2d(Units.degreesToRadians(180))));
    poses.add(new Pose2d(new Translation2d(2.02, 5.72), new Rotation2d(Units.degreesToRadians(180))));
    //tag 3
    poses.add(new Pose2d(new Translation2d(2.02, 4.03), new Rotation2d(Units.degreesToRadians(180))));
    poses.add(new Pose2d(new Translation2d(2.02, 3.58), new Rotation2d(Units.degreesToRadians(180))));
    poses.add(new Pose2d(new Translation2d(2.02, 3.01), new Rotation2d(Units.degreesToRadians(180))));

    //tag 6
    poses.add(new Pose2d(new Translation2d(14.54, 4.16), new Rotation2d(Units.degreesToRadians(0))));
    poses.add(new Pose2d(new Translation2d(14.54, 3.63), new Rotation2d(Units.degreesToRadians(0))));
    poses.add(new Pose2d(new Translation2d(14.54, 3.08), new Rotation2d(Units.degreesToRadians(0))));
    //tag 7 
    poses.add(new Pose2d(new Translation2d(14.54, 5.75), new Rotation2d(Units.degreesToRadians(0))));
    poses.add(new Pose2d(new Translation2d(14.54, 5.30), new Rotation2d(Units.degreesToRadians(0))));
    poses.add(new Pose2d(new Translation2d(14.54, 4.76), new Rotation2d(Units.degreesToRadians(0))));
    //tag 8
    poses.add(new Pose2d(new Translation2d(14.54, 7.42), new Rotation2d(Units.degreesToRadians(0))));
    poses.add(new Pose2d(new Translation2d(14.54, 6.98), new Rotation2d(Units.degreesToRadians(0))));
    poses.add(new Pose2d(new Translation2d(14.54, 6.39), new Rotation2d(Units.degreesToRadians(0)))); */
    
      

    class RumbleCommand extends CommandBase {
      @Override
      public void execute() {
          RobotContainer.secondaryController.getHID().setRumble(RumbleType.kBothRumble, 1);
          RobotContainer.primaryController.getHID().setRumble(RumbleType.kBothRumble, 1);
      }

      @Override
      public void end(boolean isInterupted) {
        RobotContainer.secondaryController.getHID().setRumble(RumbleType.kBothRumble, 0);
          RobotContainer.primaryController.getHID().setRumble(RumbleType.kBothRumble, 0);
      }
    }
    pickPose();
    /*addCommands(//new DriveToPose(this::pickPose, driveSubsystem).withTimeout(3),
                new ArmsOutCommand(hallwaySubsystem),
                new WaitCommand(1), 
                new ThrowCommand(throwerSubsystem, position).withTimeout(0.6),
                new HomingCommand(throwerSubsystem)
                //new RumbleCommand().withTimeout(1) 
                //new HomingCommand(throwerSubsystem)
                );*/
    //addCommands(new SquareCommand(driveSubsystem),
             //   new LimelightCenterCommand(driveSubsystem));
  }
}
