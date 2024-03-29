// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.thrower;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ThrowerSubsystem;

public class PreThrowCommand extends CommandBase {
  /** Creates a new PreThrowCommand. */
  private ThrowerSubsystem throwerSubsystem;
  public PreThrowCommand(ThrowerSubsystem _throwerSubsystem) {
    throwerSubsystem = _throwerSubsystem;

    addRequirements(throwerSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    throwerSubsystem.setPreThrowPosition();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    //end beheivor handled by default command
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
