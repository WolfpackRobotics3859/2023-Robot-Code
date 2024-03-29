// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.routines;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.hallway.ArmsInCommand;
import frc.robot.commands.hallway.ArmsOutCommand;
import frc.robot.commands.thrower.HomingCommand;
import frc.robot.subsystems.HallwaySubsystem;
import frc.robot.subsystems.ThrowerSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class PurgeRoutine extends SequentialCommandGroup {
  /** Creates a new PurgeRoutine. */
  public PurgeRoutine(HallwaySubsystem hallwaySubsystem, ThrowerSubsystem throwerSubsystem) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    class TinyThrow extends CommandBase {
      @Override
      public void initialize() {
        throwerSubsystem.setThrowConePurgePosition();
      }

      @Override
      public boolean isFinished() {
        return throwerSubsystem.motionProfileFinished();
      }
    }
    addCommands(new ArmsOutCommand(hallwaySubsystem), 
    new WaitCommand(3), 
    new TinyThrow(), 
    new HomingCommand(throwerSubsystem), 
    new ArmsInCommand(hallwaySubsystem));
    
  }
}
