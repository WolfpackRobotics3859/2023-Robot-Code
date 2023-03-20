// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.hallway;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.HallwaySubsystem;

public class ArmsOutCommand extends CommandBase {
  /** Creates a new ArmsOutCommand. */
  private HallwaySubsystem hallwaySubsystem;
  public ArmsOutCommand(HallwaySubsystem hallwaySubsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.hallwaySubsystem = hallwaySubsystem;
    addRequirements(hallwaySubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() 
  {
    hallwaySubsystem.armsOut();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    hallwaySubsystem.rest();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
