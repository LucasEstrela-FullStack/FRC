package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake.IntakeAuto;

public class StartIntakePath extends Command{

    private final IntakeAuto intake;

    public StartIntakePath(IntakeAuto intake) {
        this.intake = intake;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        intake.Startintake();
    }

}

