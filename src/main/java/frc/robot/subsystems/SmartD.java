package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Robot;


public class SmartD {


  private static final String DashDefault  = "0";
  private static final String DashOpt1     = "1";
  private static final String DashOpt2     = "2";
  private static final String DashOpt3     = "3";
  private static final String DashOpt4     = "4";
  private static final String DashOpt5     = "5";
  private static final String DashOpt6     = "6";
  private static final String DashOpt7     = "7";


  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  public int Automode;

  public void InicializarSmartD(){
    m_chooser.setDefaultOption("Auto: Inativo", DashDefault);      // Cria o botão principal
    m_chooser.addOption("Auto: MOVE MEIO", DashOpt1);                // Adiciona botões a matriz
    m_chooser.addOption("Auto: MOVE ESQUERDA", DashOpt2);                // Adiciona botões a matriz
    m_chooser.addOption("Auto: MOVE DIREITA", DashOpt3);                // Adiciona botões a matriz
    m_chooser.addOption("Auto: 3 NOTAS", DashOpt4);                // Adiciona botões a matriz
    m_chooser.addOption("Auto: 3 NOTAS Esquerda", DashOpt5);
    m_chooser.addOption("Auto: 3 NOTAS Direita", DashOpt6);
    m_chooser.addOption("Auto: 4 NOTAS", DashOpt7);

    SmartDashboard.putData("MODO AUTONOMO SELECT:", m_chooser);
    SmartDashboard.putData(CommandScheduler.getInstance());
  }

  public void SelectedAuton(){
    m_autoSelected = m_chooser.getSelected();
    switch(m_autoSelected)
    {
      case "0":
      Automode=0;
        break;
      case "1":
      Automode=1;
        break;
      case "2":
      Automode=2;
        break;
      case "3":
      Automode=3;
        break;
      case "4":
      Automode=4;
        break;
      case "5":
      Automode=5;
        break;
      case "6":
      Automode=6;
        break;
      case "7":
      Automode=7;
        break;
      default:
      Automode=(-1);
      break;
    }
  }

  public void Analise_Tele(){

  }

  public void Tempo(){
    SmartDashboard.putNumber("TEMPO_GET()", Robot.tempo.get());
  }

}
