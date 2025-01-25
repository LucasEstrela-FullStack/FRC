package frc.robot.commands.Drive;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.math.controller.PIDController;
import frc.robot.subsystems.Drive.Drivetrain;
import frc.robot.Constants;
import frc.robot.Constants.AutoConstants;

public class DriveDistance extends Command {
    private final Drivetrain drivetrain;
    private final double targetDistance;
    private final double maxSpeed;
    private final PIDController leftPIDController;
    private final PIDController rightPIDController;
    private final PIDController anglePIDController;

    public DriveDistance(Drivetrain drivetrain, double targetDistance, double maxSpeed) {
        this.drivetrain = drivetrain;
        this.targetDistance = targetDistance;
        this.maxSpeed = maxSpeed;

        leftPIDController = new PIDController(Constants.PIDConstants.kP, Constants.PIDConstants.kI, Constants.PIDConstants.kD);
        rightPIDController = new PIDController(Constants.PIDConstants.kP, Constants.PIDConstants.kI, Constants.PIDConstants.kD);

        // Set tolerance from AutonomousConstants
        leftPIDController.setTolerance(0.2);
        rightPIDController.setTolerance(0.2);

         // Create and configure the angle PID controller
         anglePIDController = new PIDController(Constants.PIDConstants.TURN_kP, Constants.PIDConstants.TURN_kI, Constants.PIDConstants.TURN_kD);
         anglePIDController.setTolerance(1); // Defina a tolerância adequada

        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        drivetrain.resetEncoders();
        drivetrain.resetGyro(); // Reseta o Pigeon
        leftPIDController.reset();
        rightPIDController.reset();
        anglePIDController.reset();
        anglePIDController.setSetpoint(0); // Queremos manter o ângulo inicial
    }

    @Override
    public void execute() {
        double leftOutput = leftPIDController.calculate(drivetrain.getLeftEncoderDistance(), targetDistance);
        double rightOutput = rightPIDController.calculate(drivetrain.getRightEncoderDistance(), targetDistance);

        double currentAngle = drivetrain.getAngle();

        double angleCorrection = anglePIDController.calculate(currentAngle); // Target angle is 0 for straight driving

        // Apply angle correction
        leftOutput -= angleCorrection;
        rightOutput += angleCorrection;

        // Limit motor speeds
        leftOutput = Math.copySign(Math.min(Math.abs(leftOutput), maxSpeed), leftOutput);
        rightOutput = Math.copySign(Math.min(Math.abs(rightOutput), maxSpeed), rightOutput);

        
        drivetrain.tankDrive(leftOutput, rightOutput);
    }

    @Override
    public boolean isFinished() {
        return leftPIDController.atSetpoint() && rightPIDController.atSetpoint() && anglePIDController.atSetpoint();
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.stop();
    }
}
