package frc.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants;


public class IntakeTeleOp {

    private boolean isCollecting = false;
    private boolean isPieceDetected = false;
    private boolean isBackingOff = false;
    private boolean isManualBackingOff = false;
    private double startTime = 0;
    private double monitoringStartTime = 0;
    private double backOffStartTime = 0;
    private final double currentThreshold = 30; // Ajuste o valor conforme necessário
    private final double normalCurrentThreshold = 14; // Corrente considerada normal após coleta
    private final double initialInertiaTime = 0.5; // Tempo para ignorar a corrente inicial alta devido à inércia
    private final double backOffTime = 0.3; // Tempo de retrocesso em segundos
    private final double backOffSpeed = -0.2; // Velocidade de retrocesso, pode ser negativo para inverter a direção
    private final double intakeManualBackOffSpeed = -1.0; // Velocidade de retrocesso manual
  
public void Suck() {

    boolean startButtonPressed = Constants.DriveConstants.Joy.getRawButtonPressed(2);
    boolean stopButtonPressed = Constants.DriveConstants.Joy.getRawButtonPressed(3); // Supondo que o botão 3 é usado para interromper
    boolean manualBackOffButtonPressed = Constants.DriveConstants.Joy.getRawButton(4); // Supondo que o botão 4 é usado para o retrocesso manual
    boolean manualCollectButtonPressed = Constants.DriveConstants.Joy.getRawButton(1); // Botão 1 para coleta manual

    if (manualBackOffButtonPressed) {
        // Interromper a lógica de coleta e iniciar o retrocesso manual
        isCollecting = false;
        isManualBackingOff = true;
        Constants.intak.MIT.set(intakeManualBackOffSpeed);
        // Parar a vibração do controle caso esteja vibrando
        Constants.DriveConstants.Joy.setRumble(GenericHID.RumbleType.kLeftRumble, 0);
        Constants.DriveConstants.Joy.setRumble(GenericHID.RumbleType.kRightRumble, 0);
    } else if (isManualBackingOff) {
        // Parar o retrocesso manual assim que o botão for liberado
        Constants.intak.MIT.stopMotor();
        isManualBackingOff = false;
    }

    if (startButtonPressed && !isCollecting && !isBackingOff && !isManualBackingOff) {
        // Iniciar a coleta automática
        Constants.intak.MIT.set(1);
        isCollecting = true;
        isPieceDetected = false;
        startTime = Timer.getFPGATimestamp();
        monitoringStartTime = startTime + initialInertiaTime; // Definir o tempo de início da monitorização
    }

    if (manualCollectButtonPressed && !isCollecting && !isBackingOff && !isManualBackingOff) {
        // Iniciar a coleta manual
        Constants.intak.MIT.set(1);
        isCollecting = true;
        isPieceDetected = false;
        startTime = Timer.getFPGATimestamp();
        // Não precisa monitorar a corrente para coleta manual
    }

    if (stopButtonPressed && isCollecting) {
        // Interromper a coleta
        Constants.intak.MIT.stopMotor();
        isCollecting = false;
        // Parar a vibração do controle caso esteja vibrando
        Constants.DriveConstants.Joy.setRumble(GenericHID.RumbleType.kLeftRumble, 0);
        Constants.DriveConstants.Joy.setRumble(GenericHID.RumbleType.kRightRumble, 0);
    }

    if (isCollecting) {
        double current = Constants.intak.MIT.getOutputCurrent();
        double currentTime = Timer.getFPGATimestamp();

        // Apenas monitorar a corrente após o período inicial de inércia
        if (currentTime > monitoringStartTime) {
            if (current > currentThreshold) {
                // Se a corrente ultrapassar o limiar, indica que um game piece está sendo coletado
                isPieceDetected = true;
            }

            if (isPieceDetected && current < normalCurrentThreshold) {
                // Se a corrente voltar ao normal após detectar um game piece, a coleta foi concluída
                Constants.intak.MIT.stopMotor();
                isCollecting = false;
                // Vibrar o controle
                Constants.DriveConstants.Joy.setRumble(GenericHID.RumbleType.kLeftRumble, 1);
                Constants.DriveConstants.Joy.setRumble(GenericHID.RumbleType.kRightRumble, 1);
                startTime = Timer.getFPGATimestamp(); // Reinicia o timer para a vibração
            }
        }
    }

    if (!isCollecting && isPieceDetected && !isBackingOff && !isManualBackingOff) {
        double currentTime = Timer.getFPGATimestamp();
        if (currentTime - startTime >= 0.5) { // Vibrar por 0.5 segundos
            Constants.DriveConstants.Joy.setRumble(GenericHID.RumbleType.kLeftRumble, 0);
            Constants.DriveConstants.Joy.setRumble(GenericHID.RumbleType.kRightRumble, 0);
            isBackingOff = true;
            backOffStartTime = Timer.getFPGATimestamp();
            Constants.intak.MIT.set(backOffSpeed);
        }
    }

    if (isBackingOff && !isManualBackingOff) {
        double currentTime = Timer.getFPGATimestamp();
        if (currentTime - backOffStartTime >= backOffTime) {
            Constants.intak.MIT.stopMotor();
            isBackingOff = false;
            isPieceDetected = false; // Reset the piece detection for the next cycle
        }
    }
}

}
