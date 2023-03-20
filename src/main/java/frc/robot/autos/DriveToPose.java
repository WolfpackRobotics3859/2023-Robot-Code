// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.autos;

import java.util.function.Supplier;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class DriveToPose extends CommandBase {
  private final DriveSubsystem drive;
  private Pose2d target;
  private Supplier<Pose2d> targetSupplier;
  private final PIDController xController, yController, angController;

  private Pose2d currentPose;
  private double xVel, yVel, omega;

  public DriveToPose(Pose2d pose, DriveSubsystem drive) {
    this.drive = drive;
    this.target = pose;
    xController = new PIDController(2.5, 0, 0);
    yController = new PIDController(2.5, 0, 0);
    angController = new PIDController(2, 0, 0.1);

    angController.enableContinuousInput(-Math.PI, Math.PI);
    
    xController.setTolerance(0.03);
    yController.setTolerance(0.03);
    angController.setTolerance(0.5);
 
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drive);
  }

  public DriveToPose(Supplier<Pose2d> poseSupplier, DriveSubsystem drive) {
    this.drive = drive;
    this.target = null;
    targetSupplier = poseSupplier;

    xController = new PIDController(2.5, 0, 0);
    yController = new PIDController(2.5, 0, 0);
    angController = new PIDController(2, 0, 0.1);

    angController.enableContinuousInput(-Math.PI, Math.PI);
    
    xController.setTolerance(0.03);
    yController.setTolerance(0.03);
    angController.setTolerance(0.5);
 
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drive);
  }
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if(target == null) {
      target = targetSupplier.get();
    }
    xController.setSetpoint(target.getX());
    yController.setSetpoint(target.getY());
    angController.setSetpoint(target.getRotation().getRadians());
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    currentPose = drive.getPose();
    xVel = xController.calculate(currentPose.getX());
    yVel = yController.calculate(currentPose.getY());
    omega = angController.calculate(currentPose.getRotation().getRadians());
    drive.drive(new Translation2d(xVel, yVel), omega, true, false);
    SmartDashboard.putString("selected pose", target.toString());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drive.drive(new Translation2d(), 0, true, false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (xController.atSetpoint() && yController.atSetpoint() && angController.atSetpoint());
    //return false;
  }
}
