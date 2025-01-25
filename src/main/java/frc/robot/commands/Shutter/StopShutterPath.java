package frc.robot.commands.Shutter;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Shutter.ShutterAuto;

public class StopShutterPath extends InstantCommand{

    private final ShutterAuto shutter;

    public StopShutterPath(ShutterAuto shutter) {
        this.shutter = shutter;
        addRequirements(shutter);
    }

    @Override
    public void initialize() {
        shutter.stopShutter();
    }
}
    

