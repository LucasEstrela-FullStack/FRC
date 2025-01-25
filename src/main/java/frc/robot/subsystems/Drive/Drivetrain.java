package frc.robot.subsystems.Drive;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix6.hardware.Pigeon2;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.util.ReplanningConfig;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Drivetrain extends SubsystemBase {

    public static Pigeon2 Pigeon = new Pigeon2(15);

    private static DifferentialDriveOdometry odometry;
    public final DifferentialDriveKinematics kinematics;

    private final PIDController leftPIDController = new PIDController(Constants.PIDConstants.kP, Constants.PIDConstants.kI, Constants.PIDConstants.kD);
    private final PIDController rightPIDController = new PIDController(Constants.PIDConstants.kP, Constants.PIDConstants.kI, Constants.PIDConstants.kD);
    

    private double[] YPR = new double[3];

    

        // Adicionar variáveis para velocidades iniciais
    private ChassisSpeeds startingSpeeds = new ChassisSpeeds(0, 0, 0);

    public Drivetrain() {

        Constants.encoders.leftEncoder.setDistancePerPulse(Math.PI * Constants.DriveConstants.WHEEL_DIAMETER_METERS / Constants.DriveConstants.ENCODER_CPR);
        Constants.encoders.rightEncoder.setDistancePerPulse(Math.PI * Constants.DriveConstants.WHEEL_DIAMETER_METERS / Constants.DriveConstants.ENCODER_CPR);


        kinematics = new DifferentialDriveKinematics(Constants.DriveConstants.TRACK_WIDTH_METERS);
        odometry = new DifferentialDriveOdometry(getRotation2d(), Constants.encoders.leftEncoder.getDistance(), Constants.encoders.rightEncoder.getDistance());

        resetEncoders();

        
        
    }

    public void tankDrive(double leftSpeed, double rightSpeed) {
        Constants.Drive_Univer.MSE.set(leftSpeed);
        Constants.Drive_Univer.MIE.set(VictorSPXControlMode.PercentOutput,leftSpeed);
        Constants.Drive_Univer.MSD.set(rightSpeed);
        Constants.Drive_Univer.MID.set(rightSpeed);

        SmartDashboard.putNumber("Left Speed", leftSpeed);
        SmartDashboard.putNumber("Right Speed", rightSpeed);

    }

    public void driveDistance(double distance, double maxSpeed) {

        double leftOutput = leftPIDController.calculate(Constants.encoders.leftEncoder.getDistance(), distance);
        double rightOutput = rightPIDController.calculate(Constants.encoders.rightEncoder.getDistance(), distance);

        leftOutput = Math.max(-maxSpeed, Math.min(maxSpeed, leftOutput));
        rightOutput = Math.max(-maxSpeed, Math.min(maxSpeed, rightOutput));

        SmartDashboard.putNumber("Left Output", leftOutput);
        SmartDashboard.putNumber("Right Output", rightOutput);

        tankDrive(leftOutput, rightOutput);
    }
    

    public void resetEncoders() {
        Constants.encoders.leftEncoder.reset();
        Constants.encoders.rightEncoder.reset();
    }

    public double getLeftEncoderDistance() {
        return Constants.encoders.leftEncoder.getDistance();
    }

    public double getRightEncoderDistance() {
        return Constants.encoders.rightEncoder.getDistance();
    }

    public double getAverageDistance() {
        return (Constants.encoders.leftEncoder.getDistance() + Constants.encoders.rightEncoder.getDistance()) / 2.0;
    }

    public Rotation2d getRotation2d() {
        return Rotation2d.fromDegrees(getHeading());
    }

    public double getAngle() {
        return Pigeon.getYaw().getValue();
    }

    public void resetGyro() {
        Pigeon.setYaw(0);
    }

    public Pose2d getPose() {
        return odometry.getPoseMeters();
    }

    public void resetPose(Pose2d pose) {
        odometry.resetPosition(getRotation2d(), getLeftEncoderDistance(), getRightEncoderDistance(), pose);
    }

    public DifferentialDriveKinematics getKinematics() {
        return kinematics;
    }

    public double getHeading() {
        return Math.IEEEremainder(getAngle(), 360);
    }

    private double convertRpmToMetersPerSecond(double rpm) {
        double wheelDiameterMeters = 0.1524; // 6 polegadas em metros
        double wheelCircumferenceMeters = Math.PI * wheelDiameterMeters;
        return (rpm / 60.0) * wheelCircumferenceMeters;
    }

    public ChassisSpeeds getCurrentSpeeds() {
    
        double leftEncoderRPM = getLeftEncoderRate(); // RPM
        double rightEncoderRPM = getRightEncoderRate(); // RPM
    
        // Converter RPM para m/s
        double leftWheelSpeed = convertRpmToMetersPerSecond(leftEncoderRPM);
        double rightWheelSpeed = convertRpmToMetersPerSecond(rightEncoderRPM);
    
        return kinematics.toChassisSpeeds(
            new DifferentialDriveWheelSpeeds(leftWheelSpeed, rightWheelSpeed)
        );

    }
    
        // Método para obter a velocidade das rodas (exemplo)
        public double getLeftEncoderRate() {
            return Constants.encoders.leftEncoder.getRate();
        }
    
        public double getRightEncoderRate() {
            return Constants.encoders.rightEncoder.getRate();
        }

        public void Drive(ChassisSpeeds speeds) {
            var wheelSpeeds = kinematics.toWheelSpeeds(speeds);
        
            // Obter as velocidades reais das rodas dos encoders em m/s
            double currentLeftSpeed = convertRpmToMetersPerSecond(getLeftEncoderRate());
            double currentRightSpeed = convertRpmToMetersPerSecond(getRightEncoderRate());
        
            // Ajuste de PID para a velocidade das rodas
            double leftOutput = leftPIDController.calculate(currentLeftSpeed, wheelSpeeds.leftMetersPerSecond);
            double rightOutput = rightPIDController.calculate(currentRightSpeed, wheelSpeeds.rightMetersPerSecond);
        
            // Aplicar os valores ajustados aos motores
            tankDrive(leftOutput, rightOutput);
        }

        // Método para definir as velocidades iniciais
        public void setStartingSpeeds(ChassisSpeeds speeds) {
            this.startingSpeeds = speeds;
        }
    
        // Método para obter as velocidades iniciais
        public ChassisSpeeds getStartingSpeeds() {
            return startingSpeeds;
        }

    @Override
    public void periodic() {
        
        odometry.update(getRotation2d(), getLeftEncoderDistance(), getRightEncoderDistance());
        
        SmartDashboard.putNumber("Left Encoder Distance", getLeftEncoderDistance());
        SmartDashboard.putNumber("Right Encoder Distance", getRightEncoderDistance());

        SmartDashboard.putNumber("Pigeon Yaw", getAngle());

        SmartDashboard.putNumber("Pigeon Pitch", Pigeon.getPitch().getValue());
        SmartDashboard.putNumber("Pigeon Roll", Pigeon.getRoll().getValue());

        SmartDashboard.putNumber("Left PID Output", leftPIDController.calculate(getLeftEncoderRate(), 1.0));
        SmartDashboard.putNumber("Right PID Output", rightPIDController.calculate(getRightEncoderRate(), 1.0));

        SmartDashboard.putNumber("Left Encoder Rate", getLeftEncoderRate());
        SmartDashboard.putNumber("Right Encoder Rate", getRightEncoderRate());

    }

    public void stop() {
        tankDrive(0, 0);
        
    }
}
