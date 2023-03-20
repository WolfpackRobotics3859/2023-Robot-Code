// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.routines;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.drive.ResetGyroCommand;
import frc.robot.commands.thrower.HomingCommand;
import frc.robot.commands.thrower.ThrowCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.HallwaySubsystem;
import frc.robot.subsystems.ThrowerSubsystem;
import frc.robot.utils.Position;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ShootAutoPeriodRoutine extends SequentialCommandGroup {
  /** Creates a new ShootAutoPeriodRoutine. */
  public ShootAutoPeriodRoutine(ThrowerSubsystem throwerSubsystem, HallwaySubsystem hallwaySubsystem, DriveSubsystem driveSubsystem) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(new ResetGyroCommand(driveSubsystem).withTimeout(0.001), new HomingCommand(throwerSubsystem), new IntakeStageTwoRoutine(throwerSubsystem, hallwaySubsystem).withTimeout(4), new WaitCommand(3), new AutoShootRoutine(new Pose2d(), Position.THROW_CONE_HIGH, driveSubsystem, hallwaySubsystem, throwerSubsystem).withTimeout(2));
  }
}
