// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drive;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class EBrakeCommand extends CommandBase {
  /** Creates a new EBrakeCommand. */
  private DriveSubsystem driveSubsystem;
  public EBrakeCommand(DriveSubsystem driveSubsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.driveSubsystem = driveSubsystem;
    }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //TODO: set swerve module states to all inner
    SwerveModuleState[] innerStates = new SwerveModuleState[4];
    innerStates[0] = new SwerveModuleState(0, Rotation2d.fromDegrees(45));
    innerStates[1] = new SwerveModuleState(0, Rotation2d.fromDegrees(45));
    innerStates[2] = new SwerveModuleState(0, Rotation2d.fromDegrees(45));
    innerStates[3] = new SwerveModuleState(0, Rotation2d.fromDegrees(45));
    
    driveSubsystem.setModuleStates(innerStates);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
