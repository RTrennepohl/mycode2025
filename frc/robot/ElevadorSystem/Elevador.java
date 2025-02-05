package frc.robot.ElevadorSystem;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevador extends SubsystemBase {
    private final SparkMax BaseDireita;
    private final SparkMax BaseEsquerda;
    private final SparkClosedLoopController pidController;
    private final RelativeEncoder encoder;

    private static final double ENCODER_POR_CM = 10.0; // Ajuste conforme necessário
    private static final double ALTURA_INICIAL_CM = 18.0;

    public Elevador() {
        BaseDireita = new SparkMax(6, MotorType.kBrushed);
        BaseEsquerda = new SparkMax(7, MotorType.kBrushed);

        configurarMotores();

        pidController = BaseDireita.getClosedLoopController();
        encoder = BaseDireita.getEncoder();
        encoder.setPosition(0);
    }

    private void configurarMotores() {
        SparkMaxConfig config = new SparkMaxConfig();
        config.idleMode(IdleMode.kBrake);
        config.closedLoop.feedbackSensor(FeedbackSensor.kPrimaryEncoder);
        config.closedLoop.pid(0.1, 0.0, 0.0); 

        BaseDireita.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    // Substituindo `.follow()` manualmente
    public void sincronizarMotores() {
        BaseEsquerda.set(BaseDireita.get());
    }

    public void setElevadorAltura(double alturaCm) {
        double posicaoEncoder = (alturaCm - ALTURA_INICIAL_CM) * ENCODER_POR_CM;
        pidController.setReference(posicaoEncoder, ControlType.kPosition);
        sincronizarMotores(); // Garante que os dois motores funcionem juntos
    }

    public double getAlturaAtual() {
        return (encoder.getPosition() / ENCODER_POR_CM) + ALTURA_INICIAL_CM;
    }

    public void zeroElevador() {
        encoder.setPosition(0);
    }

    public void pararMotores() {
        BaseDireita.set(0);
        BaseEsquerda.set(0);
    }
}
