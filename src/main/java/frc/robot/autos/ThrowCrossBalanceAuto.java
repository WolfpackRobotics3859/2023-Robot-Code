// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.autos;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.auto.BalanceCommand;
import frc.robot.commands.auto.DriveBackCommand;
import frc.robot.commands.auto.TurnAroundCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ThrowerSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ThrowCrossBalanceAuto extends SequentialCommandGroup {
  /** Creates a new ThrowCrossBalance. */
  public ThrowCrossBalanceAuto(DriveSubsystem driveSub, ThrowerSubsystem throwSub) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(new InstantCommand(() -> driveSub.zeroGyro()), 
                new DriveBackCommand(driveSub, false).withTimeout(4), new TurnAroundCommand(driveSub).withTimeout(1),
                new DriveBackCommand(driveSub, true), new BalanceCommand(driveSub).repeatedly());
  }
}
