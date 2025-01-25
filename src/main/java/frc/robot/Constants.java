// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix6.hardware.Pigeon2;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;


import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;


/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }

      public static final class DriveConstants {

        public static final double WHEEL_DIAMETER_METERS = 0.1524; // 6 inches in meters
        public static final int ENCODER_CPR = 2048; // Encoder counts per revolution

        public static final double TRACK_WIDTH_METERS = 0.555; 

        public static final DifferentialDriveKinematics KINEMATICS =
                new DifferentialDriveKinematics(TRACK_WIDTH_METERS);

        public static final double ksVolts = 0.2; // Ajuste conforme necessário

        public static final double kvVoltSecondsPerMeter = 1.98; // Ajuste conforme necessário
        public static final double kaVoltSecondsSquaredPerMeter = 0.2; // Ajuste conforme necessário

        public static final double kMaxVoltage = 10; // Voltagem máxima que pode ser aplicada ao sistema de direção

               //JOYSTICK
        public static Joystick Joy = new Joystick(0);

        public static int Cont[] = new int[3];
    }

    public static final class PIDConstants {

      public static final double TURN_kP = 0.012;
      public static final double TURN_kI = 0.0; //0.000001;
      public static final double TURN_kD = 0.04;

      // public static final double kP = 0.33; //0.33
      // public static final double kI = 0.2;
      // public static final double kD = 0.5; //0.2

      public static final double kP = 0.2; //0.33
      public static final double kI = 0.000001;
      public static final double kD = 0.000002; //0.2

      public static final double kS = 0.1; // Volts needed to overcome the motor’s static friction
      public static final double kV = 0.1; // Volts needed to maintain a certain velocity
      public static final double kA = 0.1; // Volts needed to achieve a certain acceleration
  }

  public static final class AutoConstants {

    public static final double TURN_TOLERANCE = 0.5;
    public static final double TOLERANCE_METERS = 0.1; //TOLERANCIA EM METROS
    public static final double kMaxSpeedMetersPerSecond = 1.5; // Velocidade máxima
    public static final double kMaxAccelerationMetersPerSecondSquared = 1.0; // Aceleração máxima

    public static final double kRamseteB = 2.2; // Ajuste conforme necessário
    public static final double kRamseteZeta = 0.7; // Ajuste conforme necessário
    

}

  public static class encoders {
    public static Encoder leftEncoder = new Encoder(2,3);
    public static Encoder rightEncoder = new Encoder(0, 1);
  }

  public static final class ShutterConstants {
    // Valor em milissegundos
    public static final double INTAKE_DELIVER_DELAY = 200.0; // por exemplo, 200 milissegundos
}

  public static class Drive_Univer {
  //MOTORES
   public static VictorSPX MIE = new VictorSPX(3);
   public static CANSparkMax MSE = new CANSparkMax(2, MotorType.kBrushed);
   public static CANSparkMax MSD = new CANSparkMax(4, MotorType.kBrushed);
   public static CANSparkMax MID = new CANSparkMax(5, MotorType.kBrushed);
  }

  public static class intak {

    public static CANSparkMax MIT = new CANSparkMax(10, MotorType.kBrushed);

  }

  public static class  shutter {

    //MOTORES
        public static CANSparkMax NSD = new CANSparkMax(6,MotorType.kBrushless);  
        public static CANSparkMax NID = new CANSparkMax(9,MotorType.kBrushless);
        public static CANSparkMax NSE = new CANSparkMax(7,MotorType.kBrushless);
        public static CANSparkMax NIE = new CANSparkMax(8,MotorType.kBrushless);
  
  }


}
