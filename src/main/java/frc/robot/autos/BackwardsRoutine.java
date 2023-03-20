// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.autos;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.SwerveConstants;
import frc.robot.subsystems.DriveSubsystem;

public class BackwardsRoutine extends CommandBase {
  /** Creates a new BackwardsRoutine. */
  private DriveSubsystem driveSubsystem;
  public BackwardsRoutine(DriveSubsystem driveSubsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.driveSubsystem = driveSubsystem;
    addRequirements(driveSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    /* Get Values, Deadband*/
    double translationVal = MathUtil.applyDeadband(-0.3, 0.1);
    double strafeVal = MathUtil.applyDeadband(0, 0.1);
    double rotationVal = MathUtil.applyDeadband(0, 0.1);
    

    /* Drive */
    driveSubsystem.drive(
        new Translation2d(translationVal, strafeVal).times(SwerveConstants.maxSpeed), 
        rotationVal * SwerveConstants.maxAngularVelocity, 
        false, 
        true
    );
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
