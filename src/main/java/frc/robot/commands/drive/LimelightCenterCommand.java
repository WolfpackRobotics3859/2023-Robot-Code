// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.LimelightHelpers;
import frc.robot.subsystems.DriveSubsystem;

public class LimelightCenterCommand extends CommandBase {
  /** Creates a new LimelightCenterCommand. */
  DriveSubsystem driveSubsystem;
  double tx;
  public LimelightCenterCommand(DriveSubsystem driveSubsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.driveSubsystem = driveSubsystem;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    driveSubsystem.sideDrive(1);
    double Kp = -0.15;  // Proportional control constant
    tx = LimelightHelpers.getTX("limelight");
    driveSubsystem.sideDrive(Kp * tx);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {driveSubsystem.sideDrive(0);}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(tx) < Constants.ThrowerConstants.centerThreshold;
  }
}
