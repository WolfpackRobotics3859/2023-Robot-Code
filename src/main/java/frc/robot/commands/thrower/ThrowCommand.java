// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.thrower;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ThrowerSubsystem;
import frc.robot.utils.Position;

public class ThrowCommand extends CommandBase {
  ThrowerSubsystem throwerSubsystem;
  private Position position;

  public ThrowCommand(ThrowerSubsystem _throwerSubsystem, Position position) {
    throwerSubsystem = _throwerSubsystem;
    this.position = position;
    
    addRequirements(throwerSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if(position.equals(Position.THROW_CONE_HIGH)) {
      throwerSubsystem.setThrowConeHighPosition();
    }
    if(position.equals(Position.THROW_CONE_LOW)) {
      throwerSubsystem.setThrowConeLowPosition();
    }
    if(position.equals(Position.THROW_CUBE_HIGH)) {
      throwerSubsystem.setThrowCubeHighPosition();
    }
    if(position.equals(Position.THROW_CUBE_LOW)) {
      throwerSubsystem.setThrowCubeLowPosition();
    }
    if(position.equals(Position.PURGE)) {
      throwerSubsystem.setThrowConePurgePosition();
    }
    
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    //end beheivor handled by default command
    throwerSubsystem.manualMode(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    //return throwerSubsystem.motionProfileFinished();
    return throwerSubsystem.inRange();
  }
}
