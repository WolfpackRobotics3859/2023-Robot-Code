// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class HallwaySubsystem extends SubsystemBase {
  private WPI_TalonFX kennyFlipperLeft = new WPI_TalonFX(20);
  private WPI_TalonFX kennyFlipperRight = new WPI_TalonFX(21);
  private WPI_TalonFX chrisCrossRight = new WPI_TalonFX(20);
  private DigitalInput limitSwitch;
  private boolean cubeMode = false;
  private boolean armsEngaged = false;
  private AddressableLED ledstrip;
  private  AddressableLEDBuffer ledbuffer;
  private static SendableChooser<Boolean> cameraChoose = new SendableChooser<>();

  /** Creates a new HallwaySubsystem. */
  public HallwaySubsystem() {
    ledstrip = new AddressableLED(8);
    ledbuffer = new AddressableLEDBuffer(4);
    ledstrip.setLength(ledbuffer.getLength());

    limitSwitch = new DigitalInput(9);
    kennyFlipperLeft.configFactoryDefault();
    kennyFlipperRight.configFactoryDefault();
    chrisCrossRight.configFactoryDefault();

    kennyFlipperLeft.setNeutralMode(NeutralMode.Coast);
    kennyFlipperRight.setNeutralMode(NeutralMode.Coast);
    chrisCrossRight.setNeutralMode(NeutralMode.Brake);
    chrisCrossRight.setSelectedSensorPosition(0);

    chrisCrossRight.configMotionCruiseVelocity(14000, Constants.kTimeoutMs);
    chrisCrossRight.configMotionAcceleration(14000, Constants.kTimeoutMs);
    chrisCrossRight.configMotionSCurveStrength(2, Constants.kTimeoutMs);

    chrisCrossRight.config_kP(0, 1);
    chrisCrossRight.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants.kTimeoutMs);
		chrisCrossRight.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.kTimeoutMs);

    kennyFlipperLeft.setInverted(false);
    kennyFlipperRight.setInverted(true);
    chrisCrossRight.setInverted(true);

    kennyFlipperRight.follow(kennyFlipperLeft);

    //chrisCrossRight.follow(chrisCrossLeft);

    cameraChoose.setDefaultOption("Auto flip", true);
    cameraChoose.addOption("Manual Flip", false);
  }
  public void armsOut() {
    chrisCrossRight.set(ControlMode.MotionMagic, 0); //to be tested
    //chrisCrossRight.set(ControlMode.PercentOutput, 0.15); //club rush changed (above comment)

    armsEngaged = false;
  }

  public void armsIn() {
    chrisCrossRight.set(ControlMode.PercentOutput, -0.15); //club rush changed from -0.15 
    armsEngaged = true;
  }

  public void rest() {
    chrisCrossRight.set(ControlMode.PercentOutput, 0);
  }
  public boolean limitSwitch() {
    return !limitSwitch.get();
  }

  public boolean isArmsEngaged() {
    return armsEngaged;
  }
  
  public void setArmState(boolean engaged) {
    armsEngaged = engaged;
  }

  public void setCubeMode(boolean mode) {
    cubeMode = mode;
  }

  public boolean cubeMode() {
    return cubeMode;
  }

  public boolean coneMode() {
    return !cubeMode;
  }

  public void zero() {
    chrisCrossRight.setSelectedSensorPosition(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Chris Cross Encoder (right)", chrisCrossRight.getSelectedSensorPosition());
    SmartDashboard.putBoolean("switrch", limitSwitch());
    SmartDashboard.putBoolean("Cube Mode", cubeMode);
    if(cubeMode) {
      for (var i = 0; i < ledbuffer.getLength(); i++) {
      ledbuffer.setRGB(i, 0, 255, 0);
      }
      ledstrip.setData(ledbuffer);
    } else {
      for (var i = 0; i < ledbuffer.getLength(); i++) {
        ledbuffer.setRGB(i, 255, 0, 0);
        }
        ledstrip.setData(ledbuffer);
    }
  }
}
