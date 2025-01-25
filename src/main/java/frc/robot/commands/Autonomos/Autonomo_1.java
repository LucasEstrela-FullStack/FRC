package frc.robot.commands.Autonomos;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Drive.DriveDistance;
import frc.robot.commands.Drive.TurnDegrees;
import frc.robot.subsystems.Drive.Drivetrain;


public class Autonomo_1 extends SequentialCommandGroup {
    public Autonomo_1(Drivetrain drivetrain) {
        addCommands(
            new DriveDistance(drivetrain, 1.2, 1),
            new DriveDistance(drivetrain, -0.9, 1),
            new TurnDegrees(drivetrain, -42.5, 0.8),
            new DriveDistance(drivetrain, 1.2, 1),
            new DriveDistance(drivetrain, -1.1, 1),
            new TurnDegrees(drivetrain, 45, 0.8),
            new DriveDistance(drivetrain, 0.2, 1),
            new TurnDegrees(drivetrain, 45, 0.8),
            new DriveDistance(drivetrain, 1.3, 1),
            new DriveDistance(drivetrain, -1.2, 1),
            new TurnDegrees(drivetrain, -45, 0.8)
        );
    }
    
}
