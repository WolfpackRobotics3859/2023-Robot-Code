// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SensorTerm;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.AbsoluteSensorRange;
import com.ctre.phoenix.sensors.CANCoder;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.ThrowerConstants;

public class ThrowerSubsystem extends SubsystemBase {
  private WPI_TalonFX primaryMotor;
  private WPI_TalonFX secondaryMotor;
  private CANCoder throwerEncoder;

  /** Creates a new ThrowerSubsystem. */
  public ThrowerSubsystem() {
    throwerEncoder = new CANCoder(5);
    throwerEncoder.configFactoryDefault();
    primaryMotor = new WPI_TalonFX(11);
    secondaryMotor = new WPI_TalonFX(12);
    secondaryMotor.configFactoryDefault();
    secondaryMotor.setInverted(true);
    secondaryMotor.follow(primaryMotor);
    primaryMotor.configFactoryDefault();
    primaryMotor.setInverted(false);
    primaryMotor.configVoltageCompSaturation(10, Constants.kTimeoutMs);
    primaryMotor.enableVoltageCompensation(true);
		primaryMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.kTimeoutMs); //changed to 10... 11:48 3/31
		primaryMotor.configNominalOutputForward(0, Constants.kTimeoutMs);
		primaryMotor.configNominalOutputReverse(0, Constants.kTimeoutMs);
		primaryMotor.configPeakOutputReverse(-1, Constants.kTimeoutMs);
		primaryMotor.setSelectedSensorPosition(0, 0, Constants.kTimeoutMs);
    primaryMotor.configRemoteFeedbackFilter(throwerEncoder, 0);
    primaryMotor.configSelectedFeedbackSensor(FeedbackDevice.RemoteSensor0);
    primaryMotor.setSensorPhase(true);
    throwerEncoder.setPosition(0);
    primaryMotor.configNeutralDeadband(0.01);
    resetEncoders();

    //secondaryMotor.configFactoryDefault()
    configurePIDSlots();

    configureShuffleboard();
    primaryMotor.selectProfileSlot(0, 0);
    
    primaryMotor.configMotionAcceleration(ThrowerConstants.throwConeHighAcceleration);
    primaryMotor.configMotionCruiseVelocity(ThrowerConstants.throwConeHighAcceleration);
    primaryMotor.configMotionSCurveStrength(ThrowerConstants.throwProfileSmoothing);

  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("throwere", primaryMotor.getSelectedSensorPosition());
  }
  
  //check to see if the motor is at the target position
  public boolean motionProfileFinished() {
    return primaryMotor.getClosedLoopError() < ThrowerConstants.motionProfileTolerance;
  }
  public void resetEncoders() {
    primaryMotor.setSelectedSensorPosition(0);
    throwerEncoder.setPosition(0);
  }
  public void coastMode() {
    primaryMotor.setNeutralMode(NeutralMode.Coast);
  }

  public void brakeMode() {
    primaryMotor.setNeutralMode(NeutralMode.Brake);
  }
  public void manualMode(double percent) {
    primaryMotor.set(ControlMode.PercentOutput, percent);
  }

  public void setLoadPosition() {
    configureMovement();
    primaryMotor.set(ControlMode.MotionMagic, ThrowerConstants.loadPosition);
  }

  public void setPreThrowPosition() {
    configureMovement();
    primaryMotor.set(ControlMode.MotionMagic, ThrowerConstants.preShootPosition);
  }

  public void setThrowConeHighPosition() {
    configureThrowConeHigh();
    primaryMotor.set(ControlMode.MotionMagic, ThrowerConstants.throwConeHighPosition);
  }

  public void setThrowConeLowPosition() {
    configureThrowConeLow();
    primaryMotor.set(ControlMode.MotionMagic, ThrowerConstants.throwConeLowPosition);
  }

  public void setThrowCubeHighPosition() {
    primaryMotor.selectProfileSlot(0, 0);
    
    primaryMotor.configMotionAcceleration(ThrowerConstants.throwCubeHighAcceleration);
    primaryMotor.configMotionCruiseVelocity(ThrowerConstants.throwCubeHighAcceleration);
    primaryMotor.configMotionSCurveStrength(2);

    primaryMotor.set(ControlMode.MotionMagic, ThrowerConstants.throwCubeHighPosition);
  }

  public void setThrowCubeLowPosition() {
    primaryMotor.selectProfileSlot(0, 0);
    primaryMotor.configMotionAcceleration(ThrowerConstants.throwCubeLowAcceleration);
    primaryMotor.configMotionCruiseVelocity(ThrowerConstants.throwCubeLowCruiseVelocity);
    primaryMotor.configMotionSCurveStrength(4);
    primaryMotor.set(ControlMode.MotionMagic, ThrowerConstants.throwCubeLowPosition);
  }

  public void setThrowConePurgePosition() {
    configureThrowConeLow();
    primaryMotor.set(ControlMode.MotionMagic, ThrowerConstants.purgePosition);
  }

  public void zeroPosition() {
    configureMovement(); 
    primaryMotor.set(ControlMode.MotionMagic, 0);
  }
  public void homing() {
    configureMovement();
    primaryMotor.set(ControlMode.MotionMagic, -1400);
   }
  public void manualSpeed(double speed) {
    primaryMotor.set(ControlMode.PercentOutput, speed);
  }

  //Configures motor's pid and motion magic to be more stable for travel
  public void configureMovement() {
    primaryMotor.selectProfileSlot(1, 0);
    primaryMotor.configMotionAcceleration(ThrowerConstants.travelAcceleration);
    primaryMotor.configMotionCruiseVelocity(ThrowerConstants.travelCruiseVelocity);
    primaryMotor.configMotionSCurveStrength(ThrowerConstants.travelProfileSmoothing);
  }

  //configures motor's pid and motion magic to be more powerful for launching
  public void configureThrowConeHigh() {
    primaryMotor.selectProfileSlot(0, 0);
    
    primaryMotor.configMotionAcceleration(ThrowerConstants.throwConeHighAcceleration);
    primaryMotor.configMotionCruiseVelocity(ThrowerConstants.throwConeHighAcceleration);
    primaryMotor.configMotionSCurveStrength(ThrowerConstants.throwProfileSmoothing);
  }

  public void configureThrowConeLow() {
    primaryMotor.selectProfileSlot(0, 0);
    primaryMotor.configMotionAcceleration(ThrowerConstants.throwConeLowAcceleration);
    primaryMotor.configMotionCruiseVelocity(ThrowerConstants.throwConeLowCruiseVelocity);
    primaryMotor.configMotionSCurveStrength(ThrowerConstants.throwProfileSmoothing);
  }

  //configures pid slots for the motor, slot 0 for throw, slot 1 for traveling
  public void configurePIDSlots() {
    //slot 0
    primaryMotor.config_kP(0, ThrowerConstants.throwkP);
    primaryMotor.config_kI(0, ThrowerConstants.throwkI);
    primaryMotor.config_kD(0, ThrowerConstants.throwkD);
    primaryMotor.config_kF(0, ThrowerConstants.throwkF);
    //slot 1
    primaryMotor.config_kP(1, ThrowerConstants.travelkP);
    primaryMotor.config_kI(1, ThrowerConstants.travelkI);
    primaryMotor.config_kD(1, ThrowerConstants.travelkD);
    primaryMotor.config_kF(1, ThrowerConstants.travelkF);
    

  }
  public void configureShuffleboard() {
    ShuffleboardTab tab = Shuffleboard.getTab("Thrower");
    //sets each shuffleboard widget to a boolean supplier from the motor, so it updates
    tab.addNumber("Primary Motor Velocity", primaryMotor::getSelectedSensorVelocity);
    tab.addNumber("Primary Motor Position", primaryMotor::getSelectedSensorPosition);
    tab.addNumber("Primary Motor Error", primaryMotor::getClosedLoopError);
    tab.add(this);
  }
}
