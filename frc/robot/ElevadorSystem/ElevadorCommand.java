package frc.robot.ElevadorSystem;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.Joystick;

public class ElevadorCommand extends Command {
    private final Elevador elevador;
    private final Joystick joystick1;

    // Definição das alturas desejadas (em cm)
    private static final double ALTURA_INICIAL = 18.0;
    private static final double ALTURA_L1 = 46.0;
    private static final double ALTURA_L2 = 58.0;
    private static final double ALTURA_L3 = 70.0;
    private static final double ALTURA_L4 = 90.0;

    // Tolerância para evitar oscilações
    private static final double TOLERANCIA_CM = 1.0; 

    public ElevadorCommand(Elevador elevador, Joystick joystick1) {
        this.elevador = elevador;
        this.joystick1 = joystick1;
        addRequirements(elevador);
    }

    @Override
    public void execute() {
        boolean descer = joystick1.getRawButton(0);
        boolean L1 = joystick1.getRawButton(1);
        boolean L2 = joystick1.getRawButton(2);
        boolean L3 = joystick1.getRawButton(3);
        boolean L4 = joystick1.getRawButton(4);

        controlarElevador(descer, L1, L2, L3, L4);
    }

    private void controlarElevador(boolean descer, boolean L1, boolean L2, boolean L3, boolean L4) {
        double alturaAtual = elevador.getAlturaAtual();
        double alturaDesejada = alturaAtual; // Mantém altura se nenhum botão for pressionado

        if (L1) {
            alturaDesejada = ALTURA_L1;
        } else if (L2) {
            alturaDesejada = ALTURA_L2;
        } else if (L3) {
            alturaDesejada = ALTURA_L3;
        } else if (L4) {
            alturaDesejada = ALTURA_L4;
        } else if (descer) {
            alturaDesejada = ALTURA_INICIAL;
        }

        // Só envia comando se a diferença for maior que a tolerância, evitando tremores
        if (Math.abs(alturaDesejada - alturaAtual) > TOLERANCIA_CM) {
            elevador.setElevadorAltura(alturaDesejada);
        }
    }

    @Override
    public void end(boolean interrupted) {
        elevador.pararMotores();
    }

    @Override
    public boolean isFinished() {
        return false; // Mantém o comando ativo até ser interrompido
    }
}
