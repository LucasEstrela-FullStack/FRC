// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autonomos.Autonomo_1;
import frc.robot.commands.Intake.StartIntakePath;
import frc.robot.commands.Shutter.StartShutterPath;
import frc.robot.commands.Shutter.StopShutterPath;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.Drive.Drivetrain;
import frc.robot.subsystems.Intake.IntakeAuto;
import frc.robot.subsystems.Shutter.ShutterAuto;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.util.ReplanningConfig;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.proto.Kinematics;
import edu.wpi.first.math.proto.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {

  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  private final Drivetrain drivetrain = new Drivetrain();
  private final IntakeAuto intake = new IntakeAuto();
  private final ShutterAuto shutter = new ShutterAuto();



  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);

  private final SendableChooser<Command> chooser = new SendableChooser<>();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings

    NamedCommands.registerCommand("Ligar_Intake", new InstantCommand(intake::Startintake));
    NamedCommands.registerCommand("Desligar_Intake", new InstantCommand(intake::Stopintake));
    NamedCommands.registerCommand("Recolher_Intake", new InstantCommand(intake::Recolher));
    NamedCommands.registerCommand("Ligar_Shutter", new InstantCommand(shutter::shuttt));
    NamedCommands.registerCommand("Desligar_Shutter", new InstantCommand(shutter::stopShutter));

    configureBindings();
    configureAutonomousChooser();
    configureAutoBuilder();

  }

  

  private void configureAutoBuilder(){

              AutoBuilder.configureRamsete(
            drivetrain::getPose, // Robot pose supplier
            drivetrain::resetPose, // Method to reset odometry (will be called if your auto has a starting pose)
            drivetrain::getCurrentSpeeds, // Current ChassisSpeeds supplier
            drivetrain::Drive, // Method that will drive the robot given ChassisSpeeds
            Constants.AutoConstants.kRamseteB, // Ele determina a força com que o controlador tenta seguir a trajetória
            Constants.AutoConstants.kRamseteZeta, // Ele afeta a estabilidade e a suavidade do movimento do robô ao seguir a trajetória.
            new ReplanningConfig(), // Default path replanning config. See the API for the options here
            () -> {
              var alliance = DriverStation.getAlliance();
              if (alliance.isPresent()) {
                return alliance.get() == DriverStation.Alliance.Red;
              }
              return false;
            },
            drivetrain // Reference to this subsystem to set requirements
            
        );
  }
  

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
            // Vincula o comando ao botão 5 do joystick
      // new JoystickButton(Constants.DriveConstants.Joy, 7).whileTrue(new TestAndTuneCommand(drivetrain));

  }

  private void configureAutonomousChooser() {

            // Opção padrão para não fazer nada
        chooser.setDefaultOption("Meio_12p", new Autonomo_1(drivetrain));

            // Adicionando outras opções autônomas

        // chooser.addOption("Meio_17p", new Autonomo_2(drivetrain,intake));
        // chooser.addOption("Esq_12p", new Autonomo_3(drivetrain));


        SmartDashboard.putData("Auto Mode", chooser);
    }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() { 
    // An example command will be run in autonomous
    return new PathPlannerAuto("Auto_1");
    // return chooser.getSelected();
    // return null;
  }
}
