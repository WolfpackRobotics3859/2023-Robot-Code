// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class HallwaySubsystem extends SubsystemBase {
<<<<<<< Updated upstream
  private WPI_TalonFX intake1fx = new WPI_TalonFX(9);
  private WPI_TalonFX intake2fx = new WPI_TalonFX(10);
  private WPI_TalonFX flipper1fx = new WPI_TalonFX(11);
  private WPI_TalonFX flipper2fx = new WPI_TalonFX(12);
=======
  private WPI_TalonFX kennyFlipperLeft = new WPI_TalonFX(20);
  private WPI_TalonFX kennyFlipperRight = new WPI_TalonFX(21);
  private WPI_TalonFX chrisCrossLeft = new WPI_TalonFX(22);
  private WPI_TalonFX chrisCrossRight = new WPI_TalonFX(20);
>>>>>>> Stashed changes

  private static SendableChooser<Boolean> cameraChoose = new SendableChooser<>();

  /** Creates a new HallwaySubsystem. */
  public HallwaySubsystem() {
    kennyFlipperLeft.configFactoryDefault();
    kennyFlipperRight.configFactoryDefault();
    chrisCrossLeft.configFactoryDefault();
    chrisCrossRight.configFactoryDefault();

    kennyFlipperLeft.setNeutralMode(NeutralMode.Coast);
    kennyFlipperRight.setNeutralMode(NeutralMode.Coast);
    chrisCrossLeft.setNeutralMode(NeutralMode.Brake);
    chrisCrossRight.setNeutralMode(NeutralMode.Brake);

    chrisCrossLeft.setSelectedSensorPosition(0);
    chrisCrossRight.setSelectedSensorPosition(0);

    chrisCrossLeft.configMotionCruiseVelocity(16000, Constants.kTimeoutMs);
    chrisCrossLeft.configMotionAcceleration(16000, Constants.kTimeoutMs);
    chrisCrossLeft.configMotionSCurveStrength(2, Constants.kTimeoutMs);

    chrisCrossRight.configMotionCruiseVelocity(1000, Constants.kTimeoutMs);
    chrisCrossRight.configMotionAcceleration(1000, Constants.kTimeoutMs);
    chrisCrossRight.configMotionSCurveStrength(2, Constants.kTimeoutMs);

    chrisCrossLeft.config_kP(0, 1);
    chrisCrossRight.config_kP(0, 1);

    chrisCrossLeft.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants.kTimeoutMs);
		chrisCrossLeft.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.kTimeoutMs);
    chrisCrossRight.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants.kTimeoutMs);
		chrisCrossRight.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.kTimeoutMs);

    kennyFlipperLeft.setInverted(false);
    kennyFlipperRight.setInverted(true);
    chrisCrossLeft.setInverted(false);
    chrisCrossRight.setInverted(true);

    kennyFlipperRight.follow(kennyFlipperLeft);

    //chrisCrossRight.follow(chrisCrossLeft);

    cameraChoose.setDefaultOption("Auto flip", true);
    cameraChoose.addOption("Manual Flip", false);
  }
  public void armsOut() {
    chrisCrossRight.set(ControlMode.MotionMagic, 0);
  }

  public void armsIn() {
    chrisCrossRight.set(ControlMode.PercentOutput, -0.10);
  }

  public void rest() {
    chrisCrossRight.set(ControlMode.PercentOutput, 0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Chris Cross Encoder (left)", chrisCrossLeft.getSelectedSensorPosition());
    SmartDashboard.putNumber("Chris Cross Encoder (right)", chrisCrossRight.getSelectedSensorPosition());
  }
}
