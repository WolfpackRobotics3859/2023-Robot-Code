// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


import frc.robot.commands.drive.DriveCommand;
import frc.robot.commands.drive.EBrakeCommand;
import frc.robot.commands.drive.LimelightCenterCommand;
import frc.robot.commands.drive.ResetGyroCommand;
import frc.robot.commands.drive.SquareCommand;
import frc.robot.commands.hallway.ArmsInCommand;
import frc.robot.commands.hallway.ArmsOutCommand;
import frc.robot.commands.hallway.IntakeCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.commands.thrower.HomingCommand;
import frc.robot.commands.thrower.LowerCommand;
import frc.robot.commands.thrower.PreThrowCommand;
import frc.robot.commands.thrower.ResetEncoderCommand;
import frc.robot.commands.thrower.ThrowCommand;
import frc.robot.routines.AutoShootRoutine;
import frc.robot.routines.IntakeStageOneCommand;
import frc.robot.routines.IntakeStageTwoRoutine;
import frc.robot.routines.PurgeRoutine;
import frc.robot.routines.ShootAutoPeriodRoutine;
import frc.robot.autos.BackwardsRoutine;
import frc.robot.autos.DriveToPose;
import frc.robot.autos.ThrowBalanceAuto;
import frc.robot.subsystems.HallwaySubsystem;
import frc.robot.subsystems.HomePitmanArms;
import frc.robot.subsystems.ThrowerSubsystem;
import frc.robot.utils.GamePiece;
import frc.robot.utils.Position;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.XboxController;
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
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.Command.InterruptionBehavior;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
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
  private final HallwaySubsystem hallwaySubsystem = new HallwaySubsystem();
  private final ThrowerSubsystem throwerSubsystem = new ThrowerSubsystem();
  private final DriveSubsystem driveSubsystem = new DriveSubsystem();

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
  private final Command m_simpleAuto = new SequentialCommandGroup(new ResetGyroCommand(driveSubsystem).withTimeout(0.01), new ResetEncoderCommand(throwerSubsystem).withTimeout(0.01), new HomePitmanArms(hallwaySubsystem).withTimeout(1.5), new InstantCommand(() -> {hallwaySubsystem.armsOut();}));
  private final Command m_complexAuto = new SequentialCommandGroup(new ResetGyroCommand(driveSubsystem).withTimeout(0.01), new ResetEncoderCommand(throwerSubsystem).withTimeout(0.01), new HomePitmanArms(hallwaySubsystem).withTimeout(1.5), new InstantCommand(() -> {hallwaySubsystem.armsOut();}), new ThrowCommand(throwerSubsystem, Position.THROW_CUBE_HIGH).withTimeout(2.5), new WaitCommand(1), new ThrowBalanceAuto(driveSubsystem, throwerSubsystem));
  SendableChooser<Command> m_chooser = new SendableChooser<>();
  public RobotContainer() {
    m_chooser.setDefaultOption("Simple Auto", m_simpleAuto);
    m_chooser.addOption("Complex Auto", m_complexAuto);
    SmartDashboard.putData(m_chooser);
    // Set default commands for subsystems
    driveSubsystem.setDefaultCommand(
            new DriveCommand(
                driveSubsystem, 
                () -> primaryController.getRawAxis(translationAxis)*0.75, 
                () -> primaryController.getRawAxis(strafeAxis)*0.75, 
                () -> -primaryController.getRawAxis(rotationAxis)*0.75, 
                () -> robotCentric.getAsBoolean()
            )
        );
    primaryController.rightBumper().whileTrue(new DriveCommand(
      driveSubsystem, 
      () -> -primaryController.getRawAxis(translationAxis)*0.3,  
      () -> -primaryController.getRawAxis(strafeAxis)*0.3,
      () -> -primaryController.getRawAxis(rotationAxis)*0.2, 
      () -> robotCentric.getAsBoolean()
  ));

    primaryController.leftBumper().whileTrue(new DriveCommand(
        driveSubsystem, 
        () -> -primaryController.getRawAxis(translationAxis), 
        () -> -primaryController.getRawAxis(strafeAxis),
        () -> -primaryController.getRawAxis(rotationAxis), 
        () -> false
    ));
    //throwerSubsystem.setDefaultCommand(new HomingCommand(throwerSubsystem));
    //primaryController.b().whileTrue(new EBrakeCommand(driveSubsystem));
  //shoot right trig, robot right bump, homing kailey left trigger
    primaryController.x().whileTrue(new LimelightCenterCommand(driveSubsystem));
    primaryController.y().whileTrue(new SquareCommand(driveSubsystem, 0));
    //primaryController.povLeft().whileTrue(new SquareCommand(driveSubsystem, -90));

    secondaryController.leftTrigger(0.1).and(hallwaySubsystem::cubeMode).whileTrue(new SequentialCommandGroup(new InstantCommand(() -> {hallwaySubsystem.setArmState(false);}), new WaitCommand(0.2), new ThrowCommand(throwerSubsystem, Position.THROW_CUBE_HIGH)));
    secondaryController.rightTrigger(0.1).and(hallwaySubsystem::cubeMode).whileTrue(new SequentialCommandGroup(new InstantCommand(() -> {hallwaySubsystem.setArmState(false);}), new WaitCommand(0.2), new ThrowCommand(throwerSubsystem, Position.THROW_CUBE_LOW)));
    
    secondaryController.leftTrigger(0.1).and(hallwaySubsystem::coneMode).whileTrue(new SequentialCommandGroup(new InstantCommand(() -> {hallwaySubsystem.setArmState(false);}), new WaitCommand(0.2), new ThrowCommand(throwerSubsystem, Position.THROW_CONE_HIGH)));
    secondaryController.rightTrigger(0.1).and(hallwaySubsystem::coneMode).whileTrue(new SequentialCommandGroup(new InstantCommand(() -> {hallwaySubsystem.setArmState(false);}), new WaitCommand(0.2), new ThrowCommand(throwerSubsystem, Position.THROW_CONE_LOW)));
    
    secondaryController.y().whileTrue(new SequentialCommandGroup(new InstantCommand(() -> {hallwaySubsystem.setArmState(false);}), new WaitCommand(0.2), new ThrowCommand(throwerSubsystem, Position.PURGE)));
    
    //primaryController.povDown().whileTrue(new SequentialCommandGroup(new InstantCommand(() -> {hallwaySubsystem.setArmState(false);}), new WaitCommand(0.7), new ThrowCommand(throwerSubsystem, Position.THROW_CONE_LOW)));
    //primaryController.povDown().whileTrue(new ThrowCommand(throwerSubsystem, Position.THROW_CONE_LOW));
    secondaryController.rightBumper().onTrue(new InstantCommand(() -> {hallwaySubsystem.setArmState(!hallwaySubsystem.isArmsEngaged());}));

    //secondaryController.leftBumper().whileTrue(new IntakeStageOneCommand(throwerSubsystem, hallwaySubsystem).alongWith( new IntakeCommand(hallwaySubsystem, GamePiece.CONE)));
    //secondaryController.x().whileTrue(new IntakeStageOneCommand(throwerSubsystem, hallwaySubsystem).alongWith( new IntakeCommand(hallwaySubsystem, GamePiece.CUBE)));
    //secondaryController.rightBumper().whileTrue(new IntakeStageTwoRoutine(throwerSubsystem, hallwaySubsystem));
    //secondaryController.b().whileTrue(new ArmsOutCommand(hallwaySubsystem));
    secondaryController.a().onTrue(new InstantCommand(() -> {hallwaySubsystem.setCubeMode(!hallwaySubsystem.cubeMode());}));

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

    Trigger armsEngagedTrigger = new Trigger(hallwaySubsystem::isArmsEngaged);
    armsEngagedTrigger.whileFalse(new ArmsOutCommand(hallwaySubsystem));
    armsEngagedTrigger.whileTrue(new ArmsInCommand(hallwaySubsystem));
    
    SmartDashboard.putData("SendAlliance", new InstantCommand(() -> driveSubsystem.setAlliance(DriverStation.getAlliance())).ignoringDisable(true));
    
    //Manual Controls 
    SmartDashboard.putData(new ThrowCommand(throwerSubsystem, Position.THROW_CONE_LOW));
    SmartDashboard.putData(new LowerCommand(throwerSubsystem));
    SmartDashboard.putData(new PreThrowCommand(throwerSubsystem));
    SmartDashboard.putData(new ResetEncoderCommand(throwerSubsystem));
    SmartDashboard.putData(new HomePitmanArms(hallwaySubsystem));

    SmartDashboard.putData(new ArmsOutCommand(hallwaySubsystem));
    SmartDashboard.putData(new ArmsInCommand(hallwaySubsystem));
    SmartDashboard.putData(new HomingCommand(throwerSubsystem));
    SmartDashboard.putData(new SquareCommand(driveSubsystem, 0));
    SmartDashboard.putData(new LimelightCenterCommand(driveSubsystem));

    SmartDashboard.putData(new IntakeCommand(hallwaySubsystem, throwerSubsystem, GamePiece.CONE));

    SmartDashboard.putData(new AutoShootRoutine(new Pose2d(new Translation2d(2.03, 6.40), new Rotation2d(Units.degreesToRadians(180))), Position.THROW_CONE_HIGH, driveSubsystem, hallwaySubsystem, throwerSubsystem));

    SmartDashboard.putData(new IntakeStageTwoRoutine(throwerSubsystem, hallwaySubsystem));
    SmartDashboard.putData(new HomingCommand(throwerSubsystem));
    SmartDashboard.putData(new PurgeRoutine(hallwaySubsystem, throwerSubsystem));
    SmartDashboard.putData(new ShootAutoPeriodRoutine(throwerSubsystem, hallwaySubsystem, driveSubsystem));
    SmartDashboard.putData(new BackwardsRoutine(driveSubsystem));
    SmartDashboard.putData("Endcoder reset", new InstantCommand(() -> {throwerSubsystem.resetEncoders();}));
    SmartDashboard.putData(driveSubsystem);
    SmartDashboard.putData(hallwaySubsystem);
    SmartDashboard.putData(throwerSubsystem);
    
    //hallwaySubsystem.setDefaultCommand(new IdleSpinCommand(hallwaySubsystem, GamePiece.CUBE));
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
    //hallway
    //primaryController.rightBumper().whileTrue(new PurgeRoutine(hallwaySubsystem, throwerSubsystem).withInterruptBehavior(InterruptionBehavior.kCancelIncoming));
  
    
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    //return new SequentialCommandGroup(new ResetGyroCommand(driveSubsystem).withTimeout(0.01), new ResetEncoderCommand(throwerSubsystem).withTimeout(0.01), new HomePitmanArms(hallwaySubsystem).withTimeout(1.5), new InstantCommand(() -> {hallwaySubsystem.armsOut();}));
    // returns command to run in auto period
    //return null;
    //return new ShootAutoPeriodRoutine(throwerSubsystem, hallwaySubsystem, driveSubsystem);
    //return new ResetGyroCommand(driveSubsystem);
    //return new exampleAuto(driveSubsystem);
    return m_chooser.getSelected();
    //return new SequentialCommandGroup(new ResetGyroCommand(driveSubsystem).withTimeout(0.01), new ParallelRaceGroup(new IntakeCommand(hallwaySubsystem, GamePiece.NONE), new SequentialCommandGroup(new PreThrowCommand(throwerSubsystem).withTimeout(1), new HomingCommand(throwerSubsystem))) );
  }
}
