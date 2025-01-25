package frc.robot.subsystems.Drive;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.revrobotics.CANSparkBase.IdleMode;

import frc.robot.Constants;

public class DriveTeleOp {

    private boolean isRotating = false;
    private long rotationStartTime = 0;
    private final long rotationDuration = 530; // Duração da rotação em milissegundos

    public void InvertedM() {
        // Constants.Drive_Univer.MSE.etInverted(true);
        Constants.Drive_Univer.MID.setInverted(true);
    }

    public void SetfreioL() {
        Constants.Drive_Univer.MSD.setIdleMode(IdleMode.kBrake);
        Constants.Drive_Univer.MSE.setIdleMode(IdleMode.kBrake);
    }

    public void SetfreioD() {
        Constants.Drive_Univer.MSD.setIdleMode(IdleMode.kCoast);
        Constants.Drive_Univer.MSE.setIdleMode(IdleMode.kCoast);
    }

    public void Moviment() {
       
        int pov = Constants.DriveConstants.Joy.getPOV();
        if (pov == 0) { // Assume que o POV 0 é o botão de rotação
            if (!isRotating) {
                startRotation();
            }
        }

        if (isRotating) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - rotationStartTime < rotationDuration) {
                rotate180Degrees();
            } else {
                stopRotation();
            }
        } else {
            // VARIÁVEIS
            double GatD = Constants.DriveConstants.Joy.getRawAxis(3);
            double GatE = Constants.DriveConstants.Joy.getRawAxis(2);
            double AnalogE = Constants.DriveConstants.Joy.getRawAxis(0);

            Constants.Drive_Univer.MSD.set(((GatD - GatE) - (AnalogE)));
            Constants.Drive_Univer.MID.set(((GatD - GatE) - (AnalogE)));
            Constants.Drive_Univer.MSE.set(((GatD - GatE) + (AnalogE)));
            Constants.Drive_Univer.MIE.set(VictorSPXControlMode.PercentOutput, ((AnalogE) + (GatD - GatE)));
        }
    }

    private void startRotation() {
        if (!isRotating) {
            isRotating = true;
            rotationStartTime = System.currentTimeMillis();
            // Inicie a rotação com valores apropriados para o seu robô
        }
    }

    private void rotate180Degrees() {
        // Defina a rotação do robô para 180 graus
        // Por exemplo, defina a velocidade dos motores para girar
        Constants.Drive_Univer.MSD.set(-0.5); // Valor de exemplo, ajuste conforme necessário
        Constants.Drive_Univer.MID.set(-0.5); // Valor de exemplo, ajuste conforme necessário
        Constants.Drive_Univer.MSE.set(0.5);  // Valor de exemplo, ajuste conforme necessário
        Constants.Drive_Univer.MIE.set(VictorSPXControlMode.PercentOutput, 0.5); // Valor de exemplo, ajuste conforme necessário
    }

    private void stopRotation() {
        isRotating = false;
        // Pare a rotação definindo os motores para zero
        Constants.Drive_Univer.MSD.set(0);
        Constants.Drive_Univer.MID.set(0);
        Constants.Drive_Univer.MSE.set(0);
        Constants.Drive_Univer.MIE.set(VictorSPXControlMode.PercentOutput, 0);
    }

    public void Mov_2() {
        
        double drive3D = Constants.DriveConstants.Joy.getRawAxis(1);
        double Giro = Constants.DriveConstants.Joy.getRawAxis(2);

        Constants.Drive_Univer.MSD.set(((-drive3D) - (Giro)));
        Constants.Drive_Univer.MID.set(((-drive3D) - (Giro)));
        Constants.Drive_Univer.MSE.set(((-drive3D) + (Giro)));
        Constants.Drive_Univer.MIE.set(VictorSPXControlMode.PercentOutput, ((-drive3D) - (Giro)));
        System.out.println(drive3D);
    }
}