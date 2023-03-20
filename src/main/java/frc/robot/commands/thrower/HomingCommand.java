// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.hallway;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.HallwaySubsystem;

<<<<<<< Updated upstream:src/main/java/frc/robot/commands/hallway/FlipForwardCommand.java
public class FlipForwardCommand extends CommandBase {
  private HallwaySubsystem hallwaySubsystem;
  /** Creates a new FlipCommand. */
  public FlipForwardCommand(HallwaySubsystem _hallwaySubsystem) {
    hallwaySubsystem = _hallwaySubsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(hallwaySubsystem);
=======
public class HomingCommand extends CommandBase {
  /** Creates a new HomingCommand. */
  private ThrowerSubsystem throwerSubsystem;
  public HomingCommand(ThrowerSubsystem throwerSubsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.throwerSubsystem = throwerSubsystem;
>>>>>>> Stashed changes:src/main/java/frc/robot/commands/thrower/HomingCommand.java
  }

  // Called when the command is initially scheduled.
  @Override
<<<<<<< Updated upstream:src/main/java/frc/robot/commands/hallway/FlipForwardCommand.java
  public void initialize() {}
=======
  public void initialize() {
    //throwerSubsystem.manualSpeed(-0.05);
  }
>>>>>>> Stashed changes:src/main/java/frc/robot/commands/thrower/HomingCommand.java

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
<<<<<<< Updated upstream:src/main/java/frc/robot/commands/hallway/FlipForwardCommand.java
    hallwaySubsystem.setFlipper(0.5);
=======
    throwerSubsystem.coastMode();
    throwerSubsystem.manualMode(-0.10);
>>>>>>> Stashed changes:src/main/java/frc/robot/commands/thrower/HomingCommand.java
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
<<<<<<< Updated upstream:src/main/java/frc/robot/commands/hallway/FlipForwardCommand.java
    hallwaySubsystem.setFlipper(0);
=======
    throwerSubsystem.brakeMode();
    throwerSubsystem.manualMode(0);
>>>>>>> Stashed changes:src/main/java/frc/robot/commands/thrower/HomingCommand.java
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
