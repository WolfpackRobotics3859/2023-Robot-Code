// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.Constants.SecondaryVisionConstants;
import frc.robot.commands.hallway.IntakeCommand;
import frc.robot.subsystems.HallwaySubsystem;
import frc.robot.subsystems.PhotonVisionSubsystem;
import frc.robot.subsystems.SecondaryVisionSubsystem;
import frc.robot.subsystems.ThrowerSubsystem;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final PhotonVisionSubsystem photonVisionSubsystem = new PhotonVisionSubsystem();
  private final HallwaySubsystem hallwaySubsystem = new HallwaySubsystem();
  private final ThrowerSubsystem throwerSubsystem = new ThrowerSubsystem();
  private final SecondaryVisionSubsystem secondaryVisionSubsystem = new SecondaryVisionSubsystem();



  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    //Configure button bindings
    Trigger isPurpleTrigger = new Trigger(secondaryVisionSubsystem::isPurple);
    Trigger isYellowTrigger = new Trigger(secondaryVisionSubsystem::isYellow);

    ShuffleboardTab tab = Shuffleboard.getTab("Settings");
    GenericEntry manualControl = tab.add("Manual Control", false).getEntry();

    //no longer need a trigger for manual control because logic is handled in subsystem
    //Trigger manualControlTrigger = new Trigger(() -> manualControl.getBoolean(false));

    //uses triggers to cover edge case of detected color switching mid-intake
    //only implementing triggers for base first and tip first until intake testing is complete
    m_driverController.a()
      .and(isYellowTrigger)
      .and(() -> secondaryVisionSubsystem.getOrientation() == 0)
      .whileTrue(new IntakeCommand(hallwaySubsystem, "yellow", 0));

    m_driverController.a()
      .and(isPurpleTrigger)
      .and(() -> secondaryVisionSubsystem.getOrientation() == 0)
      .whileTrue(new IntakeCommand(hallwaySubsystem, "purple", 0));

    m_driverController.a()
      .and(isYellowTrigger)
      .and(() -> secondaryVisionSubsystem.getOrientation() == 2)
      .whileTrue(new IntakeCommand(hallwaySubsystem, "yellow", 2));

    m_driverController.a()
      .and(isPurpleTrigger)
      .and(() -> secondaryVisionSubsystem.getOrientation() == 2)
      .whileTrue(new IntakeCommand(hallwaySubsystem, "purple", 2));

    //codriver manual control buttons
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // returns command to run in auto period
    return null;
  }
}
