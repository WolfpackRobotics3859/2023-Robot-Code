// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.hallway;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.HallwaySubsystem;
import frc.robot.subsystems.ThrowerSubsystem;
import frc.robot.utils.GamePiece;

public class IntakeCommand extends CommandBase {
  private HallwaySubsystem hallwaySubsystem;
  private ThrowerSubsystem throwerSubsystem;
  private GamePiece gamePiece; 

  public IntakeCommand(HallwaySubsystem hallwaySubsystem, ThrowerSubsystem throwerSubsystem, GamePiece gamePiece) {
    this.hallwaySubsystem = hallwaySubsystem;
    this.throwerSubsystem = throwerSubsystem;
    this.gamePiece = gamePiece;

    addRequirements(throwerSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    hallwaySubsystem.setArmState(false);
    throwerSubsystem.setLoadPosition();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    throwerSubsystem.manualMode(0);
    hallwaySubsystem.setArmState(true);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
