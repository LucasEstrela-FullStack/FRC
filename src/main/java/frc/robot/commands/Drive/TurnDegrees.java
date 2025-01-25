package frc.robot.commands.Drive;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Drive.Drivetrain;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurnDegrees extends Command {
    private final Drivetrain drivetrain;
    private final double targetAngleDegrees;
    private final double maxSpeed;
    private final PIDController anglePIDController;

    public TurnDegrees(Drivetrain drivetrain, double targetAngleDegrees, double maxSpeed) {
        this.drivetrain = drivetrain;
        this.targetAngleDegrees = targetAngleDegrees;
        this.maxSpeed = maxSpeed;

        anglePIDController = new PIDController(
            Constants.PIDConstants.TURN_kP, 
            Constants.PIDConstants.TURN_kI, 
            Constants.PIDConstants.TURN_kD
        );
        anglePIDController.setTolerance(1); // Tolerância em graus

        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        drivetrain.resetGyro(); // Reseta o Pigeon
        anglePIDController.reset();
        anglePIDController.setSetpoint(targetAngleDegrees);
    }

    @Override
    public void execute() {
        double currentAngle = drivetrain.getAngle();
        double output = anglePIDController.calculate(currentAngle);
        double adjustedOutput = Math.max(-maxSpeed, Math.min(maxSpeed, output));

        drivetrain.tankDrive(-adjustedOutput, adjustedOutput); // Girar no lugar

        // Logging para depuração
        SmartDashboard.putNumber("Current Angle", currentAngle);
        SmartDashboard.putNumber("PID Output", output);
        SmartDashboard.putNumber("Adjusted Output", adjustedOutput);
        SmartDashboard.putNumber("Target Angle", targetAngleDegrees);
        SmartDashboard.putNumber("Setpoint", anglePIDController.getSetpoint());
        SmartDashboard.putBoolean("At Setpoint", anglePIDController.atSetpoint());

    }

    @Override
    public boolean isFinished() {
        return anglePIDController.atSetpoint(); //|| Math.abs(targetAngleDegrees - drivetrain.getAngle()) < anglePIDController.getPositionTolerance();
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.stop();
    }
}
