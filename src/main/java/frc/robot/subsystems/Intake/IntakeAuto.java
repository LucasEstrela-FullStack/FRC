package frc.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeAuto extends SubsystemBase{

    private boolean isCollecting = false;
    private boolean isPieceDetected = false;
    private boolean isBackingOff = false;
    private double startTime = 0;
    private double monitoringStartTime = 0;
    private double backOffStartTime = 0;
    private final double currentThreshold = 30; // Ajuste o valor conforme necessário
    private final double normalCurrentThreshold = 14; // Corrente considerada normal após coleta
    private final double initialInertiaTime = 0.5; // Tempo para ignorar a corrente inicial alta devido à inércia
    private final double backOffTime = 0.3; // Tempo de retrocesso em segundos
    private final double backOffSpeed = -0.2; // Velocidade de retrocesso, pode ser negativo para inverter a direção

    public void Suck() {
        if (!isCollecting && !isBackingOff) {
            // Reinicializar todos os estados relevantes
            isCollecting = true;
            isPieceDetected = false;
            startTime = Timer.getFPGATimestamp();
            monitoringStartTime = startTime + initialInertiaTime; // Definir o tempo de início da monitorização

            // Iniciar a coleta automática
            Constants.intak.MIT.set(1);
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
                    startTime = Timer.getFPGATimestamp(); // Reinicia o timer para a vibração
                }
            }
        }

        if (!isCollecting && isPieceDetected && !isBackingOff) {
            double currentTime = Timer.getFPGATimestamp();
            if (currentTime - startTime >= 0.5) { // Vibrar por 0.5 segundos
                isBackingOff = true;
                backOffStartTime = Timer.getFPGATimestamp();
                Constants.intak.MIT.set(backOffSpeed);
            }
        }

        if (isBackingOff) {
            double currentTime = Timer.getFPGATimestamp();
            if (currentTime - backOffStartTime >= backOffTime) {
                Constants.intak.MIT.stopMotor();
                isBackingOff = false;
                isPieceDetected = false; // Resetar a detecção do game piece para o próximo ciclo
            }
        }
    }
    public void Startintake(){
        Constants.intak.MIT.set(1);
    }
    public void Stopintake(){
        Constants.intak.MIT.stopMotor();
    }
    public void Recolher(){
        Constants.intak.MIT.set(-0.2);
    }
}
