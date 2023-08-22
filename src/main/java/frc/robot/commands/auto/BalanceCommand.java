// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveSubsystem;

public class BalanceCommand extends CommandBase {
  private DriveSubsystem driveSubsystem;
  /** Creates a new BalanceCommand. */
  public BalanceCommand(DriveSubsystem driveSub) {
    driveSubsystem = driveSub;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(driveSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double angle = driveSubsystem.gyro.getRoll();
    double output = -0.15 * (angle/Math.abs(angle));

    if(Math.abs(angle) > 11.0){
      driveSubsystem.drive(new Translation2d(output, 0).times(Constants.SwerveConstants.maxSpeed), 0, true, true);
    } else {
      driveSubsystem.drive(new Translation2d(0, 0), 0, true, true);
    }
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
