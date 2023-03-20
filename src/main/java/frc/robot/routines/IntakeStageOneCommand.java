// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.routines;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.hallway.ArmsOutCommand;
import frc.robot.commands.hallway.IntakeCommand;
import frc.robot.commands.thrower.HomingCommand;
import frc.robot.subsystems.HallwaySubsystem;
import frc.robot.subsystems.ThrowerSubsystem;
import frc.robot.utils.GamePiece;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class IntakeStageOneCommand extends SequentialCommandGroup {
  /** Creates a new IntakeStageOneCommand. */
  public IntakeStageOneCommand(ThrowerSubsystem throwerSubsystem, HallwaySubsystem hallwaySubsystem) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(/*new ArmsOutCommand(hallwaySubsystem),*/ new HomingCommand(throwerSubsystem));
  }
}
