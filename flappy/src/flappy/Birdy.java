/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flappy;

/**
 * Clase flappy
 *
 * @author David Martínez A01191485
 */
import java.awt.Image;
import java.awt.Toolkit;

public class Birdy extends Base {

    private static final String PAUSADO = "PAUSADO";
    private static final String DESAPARECE = "DESAPARECE";

    /**
     * Metodo constructor que hereda los atributos de la clase
     * <code>Base</code>.
     *
     * @param posX es la <code>posiscion en x</code> del objeto elefante.
     * @param posY es el <code>posiscion en y</code> del objeto elefante.
     */
    public Birdy(int posX, int posY) {
        super(posX, posY);
        Image kirby0 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/k1.png"));
        Image kirby1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/k2.png"));
        Image kirby2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/k3.png"));
        Image kirby3 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/k4.png"));
        Image kirby4 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/k5.png"));
        Image kirby5 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/k6.png"));
        Image kirby6 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/k5.png"));
        Image kirby7 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/k4.png"));
        Image kirby8 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/k3.png"));
        Image kirby9 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/k2.png"));
        Image kirby10 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/k1.png"));

        animo = new Animacion();
        animo.sumaCuadro(kirby0, 100);
        animo.sumaCuadro(kirby1, 100);
        animo.sumaCuadro(kirby2, 100);
        animo.sumaCuadro(kirby3, 100);
        animo.sumaCuadro(kirby4, 100);
        animo.sumaCuadro(kirby5, 100);
        animo.sumaCuadro(kirby6, 100);
        animo.sumaCuadro(kirby7, 100);
        animo.sumaCuadro(kirby8, 100);
        animo.sumaCuadro(kirby9, 100);
        animo.sumaCuadro(kirby10, 100);

    }

    public static String getPAUSADO() {
        return PAUSADO;
    }

    public static String getDESAPARECE() {
        return DESAPARECE;
    }
}
