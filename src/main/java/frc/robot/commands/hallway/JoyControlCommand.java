// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.hallway;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.HallwaySubsystem;

public class JoyControlCommand extends CommandBase {
  /** Creates a new JoyControlCommand. */
  private WPI_TalonFX kennyFlipperLeft = new WPI_TalonFX(20);
  private WPI_TalonFX kennyFlipperRight = new WPI_TalonFX(21);
  private WPI_TalonFX chrisCrossLeft = new WPI_TalonFX(22);
  private WPI_TalonFX chrisCrossRight = new WPI_TalonFX(23);

  public JoyControlCommand(HallwaySubsystem hallwaySubsystem) {
    kennyFlipperLeft.configFactoryDefault();
    kennyFlipperRight.configFactoryDefault();
    kennyFlipperRight.setInverted(true);
    kennyFlipperRight.follow(kennyFlipperLeft);
    
    chrisCrossLeft.configFactoryDefault();
    chrisCrossRight.configFactoryDefault();
    chrisCrossRight.setInverted(true);
    chrisCrossRight.follow(chrisCrossLeft);

    

    SmartDashboard.putNumber("Hallway Speed", SmartDashboard.getNumber("Hallway Speed", 0));
    SmartDashboard.putNumber("Flipper Speed", SmartDashboard.getNumber("Hallway Speed", 0));
    

    addRequirements(hallwaySubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    chrisCrossLeft.set(ControlMode.PercentOutput, RobotContainer.primaryController.getLeftY() * -0.2);
    chrisCrossRight.set(ControlMode.PercentOutput, RobotContainer.primaryController.getRightY() * -0.2);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    chrisCrossRight.set(ControlMode.PercentOutput, 0);
    chrisCrossLeft.set(ControlMode.PercentOutput, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
