// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.thrower;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.HallwaySubsystem;
import frc.robot.subsystems.ThrowerSubsystem;

public class HomingCommand extends CommandBase {
  /** Creates a new HomingCommand. */
  private ThrowerSubsystem throwerSubsystem;
  public HomingCommand(ThrowerSubsystem throwerSubsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.throwerSubsystem = throwerSubsystem;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //throwerSubsystem.manualSpeed(-0.05);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    throwerSubsystem.coastMode();
    throwerSubsystem.manualMode(-0.10);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    throwerSubsystem.brakeMode();
    throwerSubsystem.manualMode(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
