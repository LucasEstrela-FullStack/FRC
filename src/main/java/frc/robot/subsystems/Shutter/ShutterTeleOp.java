package frc.robot.subsystems.Shutter;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants;




public class ShutterTeleOp {

  private boolean isLaunching = false;
  private boolean hasLaunched = false;
  private double startTime = 0;

  private int Cont[] = new int[3];

  private final double launchDelay = 1.0; // Tempo de atraso antes de iniciar o lançamento (em segundos)
  private final double normalCurrentThreshold = 14; // Corrente considerada normal após coleta
  private final double maxCurrentThreshold = 20.0;
  private final double intakeDuration = 0.3; // Duração do funcionamento do intake em segundos (ajustar conforme necessário)

  private boolean intakeRunning = false;
  private boolean isLowSpeedLaunch = false;

  public void shuttt() {
    boolean startButtonPressed = Constants.DriveConstants.Joy.getRawButtonPressed(6); // Botão para iniciar o lançamento normal
    boolean lowSpeedButtonPressed = Constants.DriveConstants.Joy.getRawButtonPressed(5); // Botão para iniciar o lançamento de baixa velocidade

    if (startButtonPressed && !isLaunching) {
      isLaunching = true;
      isLowSpeedLaunch = false;
      startTime = Timer.getFPGATimestamp();
      startShutter();
    } else if (lowSpeedButtonPressed && !isLaunching) {
      isLaunching = true;
      isLowSpeedLaunch = true;
      startTime = Timer.getFPGATimestamp();
      startShutterLowSpeed();
    }

    if (isLaunching) {
      double currentTime = Timer.getFPGATimestamp();
      double elapsedTime = currentTime - startTime;

      // Após 0.5 segundos, ligue o intake
      if (elapsedTime >= 0.5 && !intakeRunning) {
        deliverToShutter();
        intakeRunning = true;
      }

      // Após 0.5 + intakeDuration segundos, pare o intake e o shutter
      if (elapsedTime >= 0.5 + intakeDuration) {
        stopIntake();
        stopShutter();
        isLaunching = false;
        intakeRunning = false;
      }
    }
  }

  // Função para iniciar todos os motores do shutter na velocidade normal
  private void startShutter() {
    Constants.shutter.NSD.set(1);
    Constants.shutter.NID.set(1);
    Constants.shutter.NSE.set(1);
    Constants.shutter.NIE.set(1);
  }

  // Função para iniciar todos os motores do shutter em velocidade baixa
  private void startShutterLowSpeed() {
    Constants.shutter.NSD.set(0.1);
    Constants.shutter.NID.set(0.4);
    Constants.shutter.NSE.set(0.1);
    Constants.shutter.NIE.set(0.4);
  }

  // Função para parar todos os motores do shutter
  private void stopShutter() {
    Constants.shutter.NSD.stopMotor();
    Constants.shutter.NID.stopMotor();
    Constants.shutter.NSE.stopMotor();
    Constants.shutter.NIE.stopMotor();
  }

  // Função para ligar o intake
  private void deliverToShutter() {
    Constants.intak.MIT.set(1);
  }

  // Função para desligar o intake
  private void stopIntake() {
    Constants.intak.MIT.stopMotor();
  }

}
  

  
