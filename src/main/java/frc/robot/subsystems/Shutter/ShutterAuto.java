package frc.robot.subsystems.Shutter;


import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShutterAuto extends SubsystemBase{

    private boolean isLaunching = false;
    private double startTime = 0;
    private final double intakeDelay = 0.2; // Tempo de atraso antes de iniciar o intake (em segundos)
    private final double intakeDuration = 0.4; // Duração do funcionamento do intake em segundos

    private boolean intakeRunning = false;

    public void shuttt() {
        if (!isLaunching) {
            isLaunching = true;
            startTime = Timer.getFPGATimestamp();
            startShutter();
        }

        if (isLaunching) {
            double currentTime = Timer.getFPGATimestamp();
            double elapsedTime = currentTime - startTime;

            // Após o delay especificado, ligue o intake
            if (elapsedTime >= intakeDelay && !intakeRunning) {
                deliverToShutter();
                intakeRunning = true;
            }

            // Após intakeDelay + intakeDuration segundos, pare o intake e o shutter
            if (elapsedTime >= intakeDelay + intakeDuration) {
                stopIntake();
                resetState();
            }
        }
    }

    private void resetState() {
        isLaunching = false;
        startTime = 0;
        intakeRunning = false;
    }

    // Função para iniciar todos os motores do shutter na velocidade normal
    public void startShutter() {
        Constants.shutter.NSD.set(1);
        Constants.shutter.NID.set(1);
        Constants.shutter.NSE.set(1);
        Constants.shutter.NIE.set(1);
    }

    // Função para parar todos os motores do shutter
    public void stopShutter() {
        Constants.shutter.NSD.set(0);
        Constants.shutter.NID.set(0);
        Constants.shutter.NSE.set(0);
        Constants.shutter.NIE.set(0);
    }

    // Função para ligar o intake
    public void deliverToShutter() {
        Constants.intak.MIT.set(1);
    }

    // Função para desligar o intake
    public void stopIntake() {
        Constants.intak.MIT.stopMotor();
    }

    public boolean isLaunching() {
        return isLaunching;
    }
}
