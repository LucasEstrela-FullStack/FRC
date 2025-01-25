// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.SmartD;
import frc.robot.subsystems.Drive.DriveTeleOp;
import frc.robot.subsystems.Drive.Drivetrain;
import frc.robot.subsystems.Intake.IntakeTeleOp;
import frc.robot.subsystems.Shutter.ShutterTeleOp;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  private CommandScheduler scheduler;

  private SmartD smartD;
  private DriveTeleOp drive;
  private IntakeTeleOp intake;
  private ShutterTeleOp shutter;

  private Drivetrain drivet;

  private Command autonomousDriveCommand;
  private Command autonomousTurnCommand;
  private Command autonomousCommand;
  private RobotContainer robotContainer;


          //Tempo
  public static Timer tempo = new Timer();

  





  private Command m_autonomousCommand;
  private RobotContainer m_robotContainer;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.

  robotContainer = new RobotContainer();
  //autonomos.inic_Encod();
  smartD = new SmartD();
  drive = new DriveTeleOp();
  intake = new IntakeTeleOp();
  shutter = new ShutterTeleOp();

  drivet = new Drivetrain();


    //drive.InvertedM();




    Constants.intak.MIT.setInverted(false);



    smartD.InicializarSmartD();

  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    
    CommandScheduler.getInstance().run();

    drive.SetfreioL();
    // SmartDashboard.putNumber("getOutputCurrent", Constants.intak.MIT.getOutputCurrent());
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {
    tempo.stop();
    tempo.reset();
  }

  @Override
  public void disabledPeriodic() {
    drive.SetfreioD();
  }

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {

    autonomousCommand = robotContainer.getAutonomousCommand();

    if (autonomousCommand != null) {
        autonomousCommand.schedule();
    }

  tempo.reset();
  tempo.start();
  smartD.SelectedAuton();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {

  CommandScheduler.getInstance().run();

  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (autonomousCommand != null) {
      autonomousCommand.cancel();
  }
  tempo.reset();
  tempo.start();
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {

    drive.Moviment();
    intake.Suck();
    shutter.shuttt();
    
  
 
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
