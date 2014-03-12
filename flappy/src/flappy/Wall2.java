/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flappy;

/**
 * Clase Malo
 *
 * @author David Martinez A01191485
 */
import java.awt.Image;
import java.awt.Toolkit;

public class Wall2 extends Base {

    private static int conteo;
    private int speed;

    /**
     * Metodo constructor que hereda los atributos de la clase
     * <code>Base</code>.
     *
     * @param posX es la <code>posiscion en x</code> del objeto bomb.
     * @param posY es el <code>posiscion en y</code> del objeto bomb.
     */
    public Wall2(int posX, int posY) {
        super(posX, posY);
        Image pipe1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/warp2.png"));

        //Se crea la animación
        animo = new Animacion();
        animo.sumaCuadro(pipe1, 100);

    }

    public static int getConteo() {
        return conteo;
    }

    public static void setConteo(int cont) {
        conteo = cont;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int vel) {
        speed = vel;
    }
}
