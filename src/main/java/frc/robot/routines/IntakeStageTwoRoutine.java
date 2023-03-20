// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.routines;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.HallwayConstants;
import frc.robot.commands.hallway.ArmsInCommand;
import frc.robot.commands.thrower.PreThrowCommand;
import frc.robot.subsystems.HallwaySubsystem;
import frc.robot.subsystems.ThrowerSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class IntakeStageTwoRoutine extends SequentialCommandGroup {
  /** Creates a new IntakeStageTwoRoutine. */
  public IntakeStageTwoRoutine(ThrowerSubsystem throwerSubsystem, HallwaySubsystem hallwaySubsystem) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(/*new ArmsInCommand(hallwaySubsystem),new WaitCommand(0.5),*/  new PreThrowCommand(throwerSubsystem));
  }
}
