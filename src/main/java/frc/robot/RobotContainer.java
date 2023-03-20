// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


import frc.robot.commands.drive.DriveCommand;
<<<<<<< Updated upstream
import frc.robot.commands.hallway.FlipForwardCommand;
import frc.robot.commands.hallway.FlipReverseCommand;
import frc.robot.commands.hallway.IntakeCommand;
import frc.robot.commands.hallway.PurgeCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.Constants.OperatorConstants;
=======
import frc.robot.commands.drive.EBrakeCommand;
import frc.robot.commands.drive.LimelightCenterCommand;
import frc.robot.commands.drive.ResetGyroCommand;
import frc.robot.commands.drive.SquareCommand;
import frc.robot.commands.hallway.ArmsInCommand;
import frc.robot.commands.hallway.ArmsOutCommand;
import frc.robot.commands.hallway.IntakeCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.commands.thrower.HomingCommand;
>>>>>>> Stashed changes
import frc.robot.commands.thrower.LowerCommand;
import frc.robot.commands.thrower.PreThrowCommand;
import frc.robot.commands.thrower.ResetEncoderCommand;
import frc.robot.commands.thrower.ThrowCommand;
<<<<<<< Updated upstream
import frc.robot.commands.thrower.TravelCommand;
import frc.robot.commands.vision.ManualControl;
import frc.robot.Constants.SecondaryVisionConstants;
import frc.robot.commands.hallway.IntakeCommand;
=======
import frc.robot.routines.AutoShootRoutine;
import frc.robot.routines.IntakeStageOneCommand;
import frc.robot.routines.IntakeStageTwoRoutine;
import frc.robot.routines.PurgeRoutine;
import frc.robot.routines.ShootAutoPeriodRoutine;
import frc.robot.autos.BackwardsRoutine;
import frc.robot.autos.DriveToPose;
>>>>>>> Stashed changes
import frc.robot.subsystems.HallwaySubsystem;
import frc.robot.subsystems.ThrowerSubsystem;
<<<<<<< Updated upstream
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTableEntry;
=======
import frc.robot.utils.GamePiece;
import frc.robot.utils.Position;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.XboxController;
>>>>>>> Stashed changes
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.Command.InterruptionBehavior;
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
  ShuffleboardTab tab = Shuffleboard.getTab("Settings");
  GenericEntry manualControl = tab.add("Manual Control", false).withWidget(BuiltInWidgets.kToggleSwitch).getEntry();
  
  
  //subsystems
<<<<<<< Updated upstream
  private final PhotonVisionSubsystem photonVisionSubsystem = new PhotonVisionSubsystem();
=======
>>>>>>> Stashed changes
  private final HallwaySubsystem hallwaySubsystem = new HallwaySubsystem();
  private final ThrowerSubsystem throwerSubsystem = new ThrowerSubsystem();
  private final DriveSubsystem driveSubsystem = new DriveSubsystem();
<<<<<<< Updated upstream
  private final SecondaryVisionSubsystem secondaryVisionSubsystem = new SecondaryVisionSubsystem(manualControl);

  //commands
  private final DriveCommand driveCommand = new DriveCommand();

  //triggers
  private final Trigger trigFlipForward = new Trigger(HallwaySubsystem::tipDetect);
  private final Trigger trigFlipReverse = new Trigger(HallwaySubsystem::baseDetect);

  //controllers
  private final CommandXboxController primaryController = new CommandXboxController(0);
  private final CommandXboxController secondaryController = new CommandXboxController(1);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
=======

  //controllers
  public final static CommandXboxController primaryController = new CommandXboxController(0);
  public final static CommandXboxController secondaryController = new CommandXboxController(1);

  //sum drive garbag
  /* Drive Controls */
  private final int translationAxis = XboxController.Axis.kLeftY.value;
  private final int strafeAxis = XboxController.Axis.kLeftX.value;
  private final int rotationAxis = XboxController.Axis.kRightX.value;
  //private final JoystickButton zeroGyro = new JoystickButton(primaryController.getHID(), XboxController.Button.kY.value);
  private final JoystickButton robotCentric = new JoystickButton(primaryController.getHID(), XboxController.Button.kRightBumper.value);
  
  //auto chooser
  private final Command m_simpleAuto = new ShootAutoPeriodRoutine(throwerSubsystem, hallwaySubsystem, driveSubsystem);
  private final Command m_complexAuto = new SequentialCommandGroup(new ShootAutoPeriodRoutine(throwerSubsystem, hallwaySubsystem, driveSubsystem), new BackwardsRoutine(driveSubsystem).withTimeout(3));
  SendableChooser<Command> m_chooser = new SendableChooser<>();
>>>>>>> Stashed changes
  public RobotContainer() {
    m_chooser.setDefaultOption("Simple Auto", m_simpleAuto);
    m_chooser.addOption("Complex Auto", m_complexAuto);
    SmartDashboard.putData(m_chooser);
    // Set default commands for subsystems
<<<<<<< Updated upstream
    //driveSubsystem.setDefaultCommand(driveCommand);
=======
    driveSubsystem.setDefaultCommand(
            new DriveCommand(
                driveSubsystem, 
                () -> primaryController.getRawAxis(translationAxis)*0.85, 
                () -> primaryController.getRawAxis(strafeAxis)*0.85, 
                () -> -primaryController.getRawAxis(rotationAxis)*0.85, 
                () -> robotCentric.getAsBoolean()
            )
        );
    primaryController.rightBumper().whileTrue(new DriveCommand(
      driveSubsystem, 
      () -> -primaryController.getRawAxis(translationAxis)*0.3, 
      () -> -primaryController.getRawAxis(strafeAxis)*0.3,
      () -> -primaryController.getRawAxis(rotationAxis)*0.3, 
      () -> robotCentric.getAsBoolean()
  ));

    //primaryController.b().whileTrue(new EBrakeCommand(driveSubsystem));
  //shoot right trig, robot right bump, homing kailey left trigger
    primaryController.x().whileTrue(new LimelightCenterCommand(driveSubsystem));
    primaryController.y().whileTrue(new SquareCommand(driveSubsystem, 0));
    primaryController.povLeft().whileTrue(new SquareCommand(driveSubsystem, -90));
    primaryController.povUp().whileTrue(new ThrowCommand(throwerSubsystem, Position.THROW_CONE_HIGH));
    primaryController.povDown().whileTrue(new ThrowCommand(throwerSubsystem, Position.THROW_CONE_LOW));
    primaryController.povRight().whileTrue(new SquareCommand(driveSubsystem, 90));

    secondaryController.rightTrigger(0.1).onTrue(new ArmsInCommand(hallwaySubsystem).withInterruptBehavior(InterruptionBehavior.kCancelSelf));
    secondaryController.leftTrigger(0.1).onTrue(new ArmsOutCommand(hallwaySubsystem).withInterruptBehavior(InterruptionBehavior.kCancelSelf));
    secondaryController.rightBumper().whileTrue(new HomingCommand(throwerSubsystem));
    //secondaryController.leftBumper().whileTrue(new IntakeStageOneCommand(throwerSubsystem, hallwaySubsystem).alongWith( new IntakeCommand(hallwaySubsystem, GamePiece.CONE)));
    //secondaryController.x().whileTrue(new IntakeStageOneCommand(throwerSubsystem, hallwaySubsystem).alongWith( new IntakeCommand(hallwaySubsystem, GamePiece.CUBE)));
    //secondaryController.rightBumper().whileTrue(new IntakeStageTwoRoutine(throwerSubsystem, hallwaySubsystem));
    //secondaryController.b().whileTrue(new ArmsOutCommand(hallwaySubsystem));
    //secondaryController.a().whileTrue(new ArmsInCommand(hallwaySubsystem));

    //secondaryController.x().whileTrue(new IntakeCommand(hallwaySubsystem, GamePiece.CUBE));
    //secondaryController.y().whileTrue(new IntakeCommand(hallwaySubsystem, GamePiece.NONE));

    primaryController.a().whileTrue(new ResetGyroCommand(driveSubsystem));
    //primaryController.y().onTrue(new ResetEncoderCommand(throwerSubsystem));
    secondaryController.leftBumper().whileTrue(new IntakeCommand(hallwaySubsystem, throwerSubsystem, null));
    //primaryController.rightTrigger().onTrue(new AutoShootRoutine(null, Position.THROW_CONE_HIGH, driveSubsystem, hallwaySubsystem, throwerSubsystem));
    //primaryController.leftTrigger().whileTrue(new AutoShootRoutine(throwerSubsystem, Position.THROW_CONE_HIGH));
    //secondaryController.leftTrigger().onTrue(new HomingCommand(throwerSubsystem));
    //primaryController.b().onTrue(new DriveToPose(new Pose2d(new Translation2d(2.03, 6.40), new Rotation2d(Units.degreesToRadians(180))), driveSubsystem));
    //primaryController.b().whileTrue(new JoyControlCommand(primaryController, hallwaySubsystem).repeatedly());

    SmartDashboard.putData("SendAlliance", new InstantCommand(() -> driveSubsystem.setAlliance(DriverStation.getAlliance())).ignoringDisable(true));
    
    //Manual Controls 
    SmartDashboard.putData(new ThrowCommand(throwerSubsystem, Position.THROW_CONE_HIGH));
    SmartDashboard.putData(new LowerCommand(throwerSubsystem));
    SmartDashboard.putData(new PreThrowCommand(throwerSubsystem));
    SmartDashboard.putData(new ResetEncoderCommand(throwerSubsystem));

    SmartDashboard.putData(new ArmsOutCommand(hallwaySubsystem));
    SmartDashboard.putData(new ArmsInCommand(hallwaySubsystem));
    SmartDashboard.putData(new HomingCommand(throwerSubsystem));
    SmartDashboard.putData(new SquareCommand(driveSubsystem, 0));
    SmartDashboard.putData(new LimelightCenterCommand(driveSubsystem));

    SmartDashboard.putData(new IntakeCommand(hallwaySubsystem, throwerSubsystem, GamePiece.CONE));

    SmartDashboard.putData(new AutoShootRoutine(new Pose2d(new Translation2d(2.03, 6.40), new Rotation2d(Units.degreesToRadians(180))), Position.THROW_CONE_HIGH, driveSubsystem, hallwaySubsystem, throwerSubsystem));
>>>>>>> Stashed changes

    SmartDashboard.putData(new IntakeStageTwoRoutine(throwerSubsystem, hallwaySubsystem));
    SmartDashboard.putData(new HomingCommand(throwerSubsystem));
    SmartDashboard.putData(new PurgeRoutine(hallwaySubsystem, throwerSubsystem));
    SmartDashboard.putData(new ShootAutoPeriodRoutine(throwerSubsystem, hallwaySubsystem, driveSubsystem));
    SmartDashboard.putData(new BackwardsRoutine(driveSubsystem));
    SmartDashboard.putData(driveSubsystem);
    SmartDashboard.putData(hallwaySubsystem);
    SmartDashboard.putData(throwerSubsystem);
    
    //hallwaySubsystem.setDefaultCommand(new IdleSpinCommand(hallwaySubsystem, GamePiece.CUBE));
    // Configure the trigger bindings
    configureBindings();
<<<<<<< Updated upstream
    configureThrowerSubsystem();
=======
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
    //Configure button bindings
    throwerSubsystem.setDefaultCommand(new TravelCommand(throwerSubsystem));
    
    //hallway
    primaryController.rightTrigger(0.2).whileTrue(new IntakeCommand(hallwaySubsystem, "yellow", 0).withInterruptBehavior(InterruptionBehavior.kCancelSelf)); //TODO, create enums
    primaryController.rightBumper().whileTrue(new PurgeCommand(hallwaySubsystem).withInterruptBehavior(InterruptionBehavior.kCancelIncoming));
    trigFlipForward.or(secondaryController.y()).whileTrue(new FlipForwardCommand(hallwaySubsystem));
    trigFlipReverse.or(secondaryController.a()).whileTrue(new FlipReverseCommand(hallwaySubsystem));

=======
    //Configure button bindings 
    //hallway
    //primaryController.rightBumper().whileTrue(new PurgeRoutine(hallwaySubsystem, throwerSubsystem).withInterruptBehavior(InterruptionBehavior.kCancelIncoming));
  
>>>>>>> Stashed changes
    
    //thrower
    primaryController.povUp().whileTrue(new TravelCommand(throwerSubsystem));
    primaryController.povDown().whileTrue(new PreThrowCommand(throwerSubsystem));
    primaryController.povLeft().whileTrue(new ThrowCommand(throwerSubsystem));
    primaryController.povRight().whileTrue(new LowerCommand(throwerSubsystem));

    primaryController.y().whileTrue(new ResetEncoderCommand(throwerSubsystem));
  }

<<<<<<< Updated upstream
  private void configureThrowerSubsystem() {
    //Configure thrower subsystem

    //TODO: uncomment when motors dont need to be reset by hand
    //throwerSubsystem.setDefaultCommand(new TravelCommand(throwerSubsystem));

    Trigger isPurpleTrigger = new Trigger(secondaryVisionSubsystem::isPurple);
    Trigger isYellowTrigger = new Trigger(secondaryVisionSubsystem::isYellow);

    //no longer need a trigger for manual control because logic is handled in subsystem
    //Trigger manualControlTrigger = new Trigger(() -> manualControl.getBoolean(false));

    //uses triggers to cover edge case of detected color switching mid-intake
    //only implementing triggers for base first and tip first until intake testing is complete
    primaryController.a()
      .and(isYellowTrigger)
      .and(() -> secondaryVisionSubsystem.getOrientation() == 0)
      .whileTrue(new IntakeCommand(hallwaySubsystem, "yellow", 0).alongWith(new LowerCommand(throwerSubsystem)));

    primaryController.a()
      .and(isPurpleTrigger)
      .and(() -> secondaryVisionSubsystem.getOrientation() == 0)
      .whileTrue(new IntakeCommand(hallwaySubsystem, "purple", 0).alongWith(new LowerCommand(throwerSubsystem)));

    primaryController.a()
      .and(isYellowTrigger)
      .and(() -> secondaryVisionSubsystem.getOrientation() == 2)
      .whileTrue(new IntakeCommand(hallwaySubsystem, "yellow", 2).alongWith(new LowerCommand(throwerSubsystem)));

    primaryController.a()
      .and(isPurpleTrigger)
      .and(() -> secondaryVisionSubsystem.getOrientation() == 2)
      .whileTrue(new IntakeCommand(hallwaySubsystem, "purple", 2).alongWith(new LowerCommand(throwerSubsystem)));

    //codriver manual control buttons
    secondaryController.y().whileTrue(new ManualControl(secondaryVisionSubsystem, "yellow")); //TODO more enums
    secondaryController.x().whileTrue(new ManualControl(secondaryVisionSubsystem, "purple"));

  }

=======
>>>>>>> Stashed changes
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // returns command to run in auto period
    return null;
<<<<<<< Updated upstream
=======
    //return new ShootAutoPeriodRoutine(throwerSubsystem, hallwaySubsystem, driveSubsystem);
    //return new ResetGyroCommand(driveSubsystem);
    //return new exampleAuto(driveSubsystem);
    //return m_chooser.getSelected();
    //return new SequentialCommandGroup(new ResetGyroCommand(driveSubsystem).withTimeout(0.01), new ParallelRaceGroup(new IntakeCommand(hallwaySubsystem, GamePiece.NONE), new SequentialCommandGroup(new PreThrowCommand(throwerSubsystem).withTimeout(1), new HomingCommand(throwerSubsystem))) );
>>>>>>> Stashed changes
  }
}
