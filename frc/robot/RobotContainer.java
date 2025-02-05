// RobotContainer.java
package frc.robot;

//import frc.robot.comandos.autonomo;
import frc.robot.TankDrive.*;
import frc.robot.ElevadorSystem.*;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;

public class RobotContainer {
    private final Movimento movimento = new Movimento();
    private final Elevador elevador = new Elevador();

    private final Joystick joystick0 = new Joystick(0);
  private final Joystick joystick1 = new Joystick (1);

    public RobotContainer() { 
        // Define os comandos padrões para os subsistemas
        movimento.setDefaultCommand(new MovimentoCommand(movimento, joystick0));
        elevador.setDefaultCommand(new ElevadorCommand(elevador, joystick1));
  
    }

    // Retorna o comando autônomo configurado
    public Command getAutonomousCommand() {
        return null; //new autonomo(movimento, limelight, shutter);
    }
}
