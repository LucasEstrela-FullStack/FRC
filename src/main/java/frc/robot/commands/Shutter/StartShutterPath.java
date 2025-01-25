package frc.robot.commands.Shutter;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Shutter.ShutterAuto;

public class StartShutterPath extends Command {
    private final ShutterAuto shutter;

    public StartShutterPath(ShutterAuto shutter) {
        this.shutter = shutter;
        addRequirements(shutter);
    }

    @Override
    public void initialize() {
        shutter.startShutter(); // Inicia o processo de disparo do shutter
    }

}
