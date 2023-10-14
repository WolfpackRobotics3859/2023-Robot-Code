package frc.robot.commands.drive;

import frc.robot.Constants.SwerveConstants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.utils.GamePiece;
import frc.robot.utils.ModulePosition;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class DriveCornerCommand extends CommandBase {    
    private DriveSubsystem s_Swerve;    
    private DoubleSupplier translationSup;
    private DoubleSupplier strafeSup;
    private DoubleSupplier rotationSup;
    private BooleanSupplier robotCentricSup;
    private ModulePosition modulePos;

    public DriveCornerCommand(DriveSubsystem s_Swerve, DoubleSupplier translationSup, DoubleSupplier strafeSup, DoubleSupplier rotationSup, BooleanSupplier robotCentricSup, ModulePosition modulePos) {
        this.s_Swerve = s_Swerve;
        addRequirements(s_Swerve);

        this.translationSup = translationSup;
        this.strafeSup = strafeSup;
        this.rotationSup = rotationSup;
        this.robotCentricSup = robotCentricSup;
        this.modulePos = modulePos;
    }

    @Override
    public void execute() {
        /* Get Values, Deadband*/
        double translationVal = MathUtil.applyDeadband(translationSup.getAsDouble(), 0.1);
        double strafeVal = MathUtil.applyDeadband(strafeSup.getAsDouble(), 0.1);
        double rotationVal = MathUtil.applyDeadband(rotationSup.getAsDouble(), 0.1);

        //rotation point var to use later, fallback to center if something goes wrong
        Translation2d rotationPoint = new Translation2d();
        //module translation points
        Translation2d frontLeftTranslation2d = new Translation2d(SwerveConstants.wheelBase / 2.0, SwerveConstants.trackWidth / 2.0);
        Translation2d frontRightTranslation2d = new Translation2d(SwerveConstants.wheelBase / 2.0, -SwerveConstants.trackWidth / 2.0);
        Translation2d backLeftTranslation2d = new Translation2d(-SwerveConstants.wheelBase / 2.0, SwerveConstants.trackWidth / 2.0);
        Translation2d backRightTranslation2d = new Translation2d(-SwerveConstants.wheelBase / 2.0, -SwerveConstants.trackWidth / 2.0);
        
        //a crap ton of logic to be able to select rotation points field relatively
        
        //TODO make sure robot gyro is reporting gyro values in right format

        //if the robot is facing the same way you are facing

        //TODO this first if is wrong, wraparound logic
        if((s_Swerve.gyro.getYaw() >= 0 && s_Swerve.gyro.getYaw() <= 40) || (s_Swerve.gyro.getYaw() >= 300 && s_Swerve.gyro.getYaw() <= 360)) { //TODO 45 degree logic/tuning (prefer ccw on ambibuity)
            switch(modulePos) {
                case FRONT_LEFT:
                    rotationPoint = frontLeftTranslation2d;
                case FRONT_RIGHT:
                    rotationPoint = frontRightTranslation2d;
                case BACK_RIGHT:
                    rotationPoint = backLeftTranslation2d;
                case BACK_LEFT:
                    rotationPoint = backLeftTranslation2d;
                    
            }
        } else if(s_Swerve.gyro.getYaw() >= 40 && s_Swerve.gyro.getYaw() <= 130) {
            switch(modulePos) {
                case FRONT_LEFT:
                    rotationPoint = frontRightTranslation2d;
                case FRONT_RIGHT:
                    rotationPoint = backRightTranslation2d;
                case BACK_RIGHT:
                    rotationPoint = backLeftTranslation2d;
                case BACK_LEFT:
                    rotationPoint = frontLeftTranslation2d;
                    
            }
        } else if(s_Swerve.gyro.getYaw() >= 130 && s_Swerve.gyro.getYaw() <= 210) {
            switch(modulePos) {
                case FRONT_LEFT:
                    rotationPoint = backRightTranslation2d;
                case FRONT_RIGHT:
                    rotationPoint = backLeftTranslation2d;
                case BACK_RIGHT:
                    rotationPoint = frontLeftTranslation2d;
                case BACK_LEFT:
                    rotationPoint = frontRightTranslation2d;
                    
            }
        } else if(s_Swerve.gyro.getYaw() >= 210 && s_Swerve.gyro.getYaw() <= 300) {
            switch(modulePos) {
                case FRONT_LEFT:
                    rotationPoint = backLeftTranslation2d;
                case FRONT_RIGHT:
                    rotationPoint = frontLeftTranslation2d;
                case BACK_RIGHT:
                    rotationPoint = frontRightTranslation2d;
                case BACK_LEFT:
                    rotationPoint = backRightTranslation2d;
                    
            }
        }

        /* Drive */
        s_Swerve.drive(
            new Translation2d(translationVal, strafeVal).times(SwerveConstants.maxSpeed), 
            rotationVal * SwerveConstants.maxAngularVelocity, 
            !robotCentricSup.getAsBoolean(), 
            true,
            rotationPoint //center of rotation
        );
    }
}
