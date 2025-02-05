package frc.robot.TankDrive;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.Joystick;

public class MovimentoCommand extends Command {
    private final Movimento movimento;
    private final Joystick joystick;

    public MovimentoCommand(Movimento movimento, Joystick joystick) {
        this.movimento = movimento;
        this.joystick = joystick;
        addRequirements(movimento); // Registra o subsistema como requisito
    }

    @Override
    public void execute() {
        double aceleraesquerda = joystick.getRawAxis(2); // ré
        double aceleradireita = joystick.getRawAxis(3);  // acelerador
        double analogE = joystick.getRawAxis(0);        // controle de direção

        // Passa os valores do joystick diretamente para o subsistema
        controlarMotoresOmni(aceleraesquerda, aceleradireita, analogE);
    }

    private void controlarMotoresOmni(double aceleraesquerda, double aceleradireita, double analogE) {
        double motorD = ((aceleraesquerda - aceleradireita) - (analogE));
        double motorE = ((-aceleraesquerda + aceleradireita) - (analogE));

        // Ajusta os motores com os valores calculados
        movimento.setMD(motorD);
        movimento.setME(motorE);
    }

    @Override
    public void end(boolean interrupted) {
        movimento.pararMotores();
    }

    @Override
    public boolean isFinished() {
        return false; // Continua enquanto o comando estiver ativo
    }
}
