/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flappy;

/**
 *
 * @author David
 */
import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseMotionListener;
import java.net.URL;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Toolkit;
/*import java.io.BufferedReader;
 import java.io.FileReader;
 import java.io.File;
 import java.io.FileNotFoundException;
 import java.io.PrintWriter;
 import java.io.FileWriter;
 import java.io.IOException;*/
import java.util.LinkedList;
import java.util.Random;
//prueba
// YA BASTAAAAAAAAAAAAAAAA

public class flap extends JFrame implements Runnable, KeyListener, MouseListener, MouseMotionListener {

    private static final long serialVersionUID = 1L;
    // Se declaran las variables.

    //private SoundClip aww;    // Objeto AudioClip
    //private SoundClip yay;    // Objeto AudioClip
    private int puntos;                 //variable donde se guardara la puntuacion   
    private double velocidady;          //variable con la cual se obtendrá el movimiento de kirby
    private int aceleracion;            //variable con la cual se obtendrá el movimiento de kirby
    private int vidas;                  //vidas del juego
    private int random;
    private double tiempo;              //varible con la cual se obtendrá el movimiento de kirby
    private int dificultad;
    private int posrX;             //posicion inicial de kirby en x
    private int posrY;
    private int yabasta;
    private Image Fondo;           //fondo del juego
    private Image instrucciones;   //instrucciones del juego
    private Image credits;         //creditos
    private Image dbImage;	// Imagen a proyectar	
    private Graphics dbg;	// Objeto grafico
    private Wall Pipe;          //objeto pipe de la clase wall (será la pipa de abajo)
    private Wall2 Pipe2;        //objeto pipe2 de la clase wall2 (será la pipa de arriba)
    private Birdy Kirby;        //objeto kirby de la clase birdy
    private boolean pausar = false;     //booleana que controlará la pausa
    private boolean time = false;       //
    private boolean instr = false;      //booleana que controlará el display de las instrucciones
    private boolean sound_off = false;  //booleana que controlará el sonido
    private boolean inicio = false;     //boolena que indicará si el juego ya comenzó
    private boolean salta = false;      //booleana para saber si kirby debe de saltar
    private boolean choca = false;
    private boolean restart = false;
    private LinkedList<Wall> lista;     //lista de pipe
    private LinkedList<Wall2> lista2;   //lista de pipe2
    private URL fiURL;
    private long tiempoActual;
    private long tiempoInicial;
    //private String nombreArchivo;
    //private String[] arr;

    public flap() {
        init();
        start();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Metodo <I>init</I> sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se inizializan las variables o se crean los objetos a
     * usarse en el <code>Applet</code> yinicial se definen funcionalidades.
     */
    public void init() {
        dificultad = 0;
        vidas = 5;
        puntos = 0;
        aceleracion = 1;
        yabasta = 5;

        random = (int) ((Math.random() * (4 - 1)) + 1);
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        this.setSize(400, 600);
        URL noURL = this.getClass().getResource("Images/instrucciones.gif");
        instrucciones = Toolkit.getDefaultToolkit().getImage(noURL);
        fiURL = this.getClass().getResource("files/tarea_pareja.txt");
        URL goURL = this.getClass().getResource("Images/credits.gif");
        credits = Toolkit.getDefaultToolkit().getImage(goURL);
        setBackground(Color.black);
        lista = new LinkedList();
        lista2 = new LinkedList();
        //se generan los ladrillos
        for (int i = 1; i <= 3; i++) {

            Pipe = new Wall(i * 350, (int) (Math.random() * (getHeight() - getHeight() / 2) + 200));
            lista.addLast(Pipe);
            Pipe2 = new Wall2(Pipe.getPosX(), Pipe.getPosY() - 600);
            lista2.addLast(Pipe2);

        }
        //~~~~~~~~~~~ Creamos a la bola
        posrX = (int) (100);    // posicion en x es un cuarto del applet
        int posrY = (int) (getHeight() / 2);    // posicion en yinicial es un cuarto del applet
        Kirby = new Birdy(posrX, posrY);
        Kirby.setPosX(posrX);
        Kirby.setPosY(posrY);
        URL neURL = this.getClass().getResource("Images/fondo.png");
        Fondo = Toolkit.getDefaultToolkit().getImage(neURL);
    }

    /**
     * Metodo <I>start</I> sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se crea e inicializa el hilo para la animacion este metodo
     * es llamado despues del init o cuando el usuario visita otra pagina
     * yinicial luego regresa a la pagina en donde esta este <code>Applet</code>
     *
     */
    public void start() {
        // Declaras un hilo
        Thread th = new Thread(this);
        // Empieza el hilo
        th.start();
    }

    /**
     * Metodo <I>run</I> sobrescrito de la clase <code>Thread</code>.<P>
     * En este metodo se ejecuta el hilo, es un ciclo indefinido donde se
     * incrementa la posicion en x o yinicial dependiendo de la direccion,
     * finalmente se repinta el <code>Applet</code> yinicial luego manda a
     * dormir el hilo.
     *
     */
    @Override
    public void run() {
        //Guarda el tiempo actual del sistema
        tiempoActual = System.currentTimeMillis();
        //Ciclo principal del Applet. Actualiza yinicial despliega en pantalla la animación hasta que el Applet sea cerrado
        while (vidas > 0) {
            if (!pausar && !instr) {
                actualiza();
                checaColision();
                repaint(); // Se actualiza el <code>Applet</code> repintando el contenido.

            }
            try {
                // El thread se duerme.
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                System.out.println("Error en " + ex.toString());
            }

        }
    }

    /**
     * Metodo usado para actualizar la posicion de objetos elefante yinicial
     * Kirbyinicial.
     *
     */
    public void actualiza() {
        //Determina el tiempo que ha transcurrido desde que el Applet inicio su ejecución
        long tiempoTranscurrido = System.currentTimeMillis() - tiempoActual;
        //Guarda el tiempo actual
        tiempoActual += tiempoTranscurrido;
        Kirby.actualiza(tiempoTranscurrido);
        if (time) {
            tiempo += .09;
        }
        if (inicio) {
            if (salta) {
                velocidady = -5 + 4 * tiempo;
                time = true;
                Kirby.setPosY(Kirby.getPosY() + (int) velocidady);

            }

            /*if (save && instr) {                                                             //si la bandera de grabar esta prendida, se graba el archivo yinicial se apaga la bandera
             grabaArchivo();
             save = false;
             }
             if (load) {                                                             //si la bandera de cargar esta prendida, se carga el archivo yinicial se apaga la bandera
             leeArchivo();
             load = false;
             }*/
            for (int i = 0; i < lista.size(); i++) {
                Pipe = (Wall) (lista.get(i));
                Pipe.setPosX(Pipe.getPosX() - 3);
                if ((Kirby.getPosX() > Pipe.getPosX()) && (Kirby.getPosX() < Pipe.getPosX() + 3)) {
                    puntos++;
                }
            }
            for (int i = 0; i < lista2.size(); i++) {
                Pipe2 = (Wall2) (lista2.get(i));
                Pipe2.setPosX(Pipe2.getPosX() - 3);
            }
            velocidady = velocidady + aceleracion;
            Kirby.setPosY(Kirby.getPosY() + (int) velocidady);
        }
        if (puntos > 7) {
            dificultad = dificultad + 50;
        } else if (puntos > 12) {
            dificultad = dificultad + 65;
        }
        if (restart) {
            puntos = 0;
            posrX = (int) (100);    // posicion en x es un cuarto del applet
            int posrY = (int) (getHeight() / 2);    // posicion en yinicial es un cuarto del applet
            Kirby = new Birdy(posrX, posrY);
            Kirby.setPosX(posrX);
            Kirby.setPosY(posrY);
            lista = new LinkedList();
            lista2 = new LinkedList();
            //se generan los ladrillos
            for (int i = 1; i <= 3; i++) {

                Pipe = new Wall(i * 350, (int) (Math.random() * (getHeight() - getHeight() / 2) + 200));
                lista.addLast(Pipe);
                Pipe2 = new Wall2(Pipe.getPosX(), Pipe.getPosY() - 600);
                lista2.addLast(Pipe2);

            }
            restart = false;
            inicio = false;
        }
    }

    /**
     * Metodo usado para checar las colisiones del objeto elefante yinicial
     * Kirbyinicial con las orillas del <code>Applet</code>.
     */
    public void checaColision() {
        if (Kirby.getPosY() >= getHeight()) {
            int posrX = (int) (100);    // posicion en x es un cuarto del applet
            int posrY = (int) (getHeight() / 2);    // posicion en yinicial es un cuarto del applet
            Kirby.setPosX(posrX);
            Kirby.setPosY(posrY);
            time = false;
            inicio = false;
            vidas = 5;

        }
        if (Kirby.getPosY() <= 0) {
            Kirby.setPosX(Kirby.getPosX());
            Kirby.setPosY(30);
        }
        for (Wall i : lista) {
            if (i.getPosX() + i.getAncho() <= 0) {
                i.setPosX(getWidth() + 80);

            }

        }
        for (Wall2 i : lista2) {
            if (i.getPosX() + i.getAncho() <= 0) {
                i.setPosX(getWidth() + 80);

            }
            if (Kirby.intersecta(i)) {
                inicio = false;
                inicio = false;
                choca = true;

            }

        }

        for (Wall i : lista) {
            if (Kirby.intersecta(i)) {
                inicio = false;
                choca = true;

            }
        }
        for (Wall2 i : lista2) {
            if (Kirby.intersecta(i)) {
                inicio = false;
                choca = true;

            }
        }

        //Colision entre objetos
        /*if (Kirby.intersecta(Pipe) && (Kirby.getPosY() + Kirby.getAlto() - 5) < Pipe.getPosY()) {
         int posrX = (int) (0);    // posicion en x es un cuarto del applet
         int posrY = (int) (getHeight() / 2);    // posicion en yinicial es un cuarto del applet
         Kirby.setPosX(posrX);
         Kirby.setPosY(posrY);
         puntos = puntos + 2;
         time = false;
         pico = false;
         inicio = false;
         tiempo = 0;

         // emitir aww de alegria
         if (!sound_off) {
         yay.play();
         }
         }*/
    }

    /**
     * Metodo <I>update</I> sobrescrito de la clase <code>Applet</code>,
     * heredado de la clase Container.<P>
     * En este metodo lo que hace es actualizar el contenedor
     *
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     */
    @Override
    public void paint(Graphics g) {
        // Inicializan el DoubleBuffer
        if (dbImage == null) {
            dbImage = createImage(this.getSize().width, this.getSize().height);
            dbg = dbImage.getGraphics();
        }

        // Actualiza la imagen de fondo.
        dbg.setColor(getBackground());
        dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);

        // Actualiza el Foreground.
        dbg.setColor(getForeground());
        paint1(dbg);

        // Dibuja la imagen actualizada
        g.drawImage(dbImage, 0, 0, this);
    }

    /**
     * Metodo <I>keyinicialPressed</I> sobrescrito de la interface
     * <code>KeyinicialListener</code>.<P>
     * En este metodo maneja el evento que se genera al presionar cualquier la
     * tecla.
     *
     * @param e es el <code>evento</code> generado al presionar las teclas.
     */
    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_P) {
            pausar = !pausar;
        } else if (e.getKeyCode() == KeyEvent.VK_I) {
            instr = !instr;
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            sound_off = !sound_off;
        } else if (e.getKeyCode() == KeyEvent.VK_O) {
            inicio = true;

        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            tiempo = 0.0;
            salta = true;
        } else if (e.getKeyCode() == KeyEvent.VK_R) {
            restart = true;

        }

    }

    /**
     * Metodo <I>keyinicialTyinicialped</I> sobrescrito de la interface
     * <code>KeyinicialListener</code>.<P>
     * En este metodo maneja el evento que se genera al presionar una tecla que
     * no es de accion.
     *
     * @param e es el <code>evento</code> que se genera en al presionar las
     * teclas.
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Metodo <I>keyinicialReleased</I> sobrescrito de la interface
     * <code>KeyinicialListener</code>.<P>
     * En este metodo maneja el evento que se genera al soltar la tecla
     * presionada.
     *
     * @param e es el <code>evento</code> que se genera en al soltar las teclas.
     */
    @Override
    public void keyReleased(KeyEvent e) {

    }

    /**
     * Metodo <I>paint</I> sobrescrito de la clase <code>Applet</code>, heredado
     * de la clase Container.<P>
     * En este metodo se dibuja la imagen con la posicion actualizada, ademas
     * que cuando la imagen es cargada te despliega una advertencia.
     *
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     */
    public void paint1(Graphics g) {
        if (vidas > 0) {
            if (Pipe != null && Kirby != null) {
                //Dibuja la imagen en la posicion actualizada
                g.drawImage(Fondo, 0, 0, getSize().width, getSize().height, this);
                g.drawImage(Kirby.getImagenI(), Kirby.getPosX(), Kirby.getPosY(), this);
                if (pausar) {
                    g.setColor(Color.white);
                    g.drawString(Birdy.getPAUSADO(), Pipe.getPosX() + Pipe.getAncho() / 3, Pipe.getPosY() + Pipe.getAlto() / 2);

                }
                for (int i = 0; i < lista.size(); i++) {
                    Pipe = (Wall) lista.get(i);
                    g.drawImage(Pipe.getImagenI(), Pipe.getPosX(), Pipe.getPosY() + (random * 10), this);
                }
                for (int i = 0; i < lista2.size(); i++) {
                    Pipe2 = (Wall2) lista2.get(i);
                    g.drawImage(Pipe2.getImagenI(), Pipe2.getPosX(), Pipe2.getPosY() + (random * 10), this);
                }
                g.setColor(Color.white);
                g.drawString(" Puntaje = " + puntos, 60, 60);
                if (instr) {

                    g.drawImage(instrucciones, 0, 0, this);

                }
            } else {
                //Da un mensaje mientras se carga el dibujo	
                g.drawString("No se cargo la imagen..", 20, 20);
            }
        } else {
            g.drawImage(credits, 0, 0, this);
        }
    }

    /**
     * Metodo <I>mouseClicked</I> sobrescrito de la interface
     * <code>MouseListener</code>.<P>
     * En este metodo maneja el evento que se genera al presionar el mouse
     *
     * @param e es el <code>evento</code> que se genera en al presionar el mouse
     */
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    /**
     * Metodo <I>mouseEntered</I> sobrescrito de la interface
     * <code>MouseListener</code>.<P>
     * En este metodo maneja el evento que se genera al ingresar el mouse.
     *
     * @param e es el <code>evento</code> que se genera en al ingresar el mouse
     */
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * Metodo <I>mouseExited</I> sobrescrito de la interface
     * <code>MouseListener</code>.<P>
     * En este metodo maneja el evento que se genera al sacar el mouse.
     *
     * @param e es el <code>evento</code> que se genera en al sacar el mouse
     */
    @Override
    public void mouseExited(MouseEvent e) {
    }

    /**
     * Metodo <I>mousePressed</I> sobrescrito de la interface
     * <code>MouseListener</code>.<P>
     * En este metodo maneja el evento que se genera al presionar el mouse.
     *
     * @param e es el <code>evento</code> que se genera en al presionar el mouse
     */
    @Override
    public void mousePressed(MouseEvent e) {

    }

    /**
     * Metodo <I>mouseReleased</I> sobrescrito de la interface
     * <code>MouseListener</code>.<P>
     * En este metodo maneja el evento que se genera al soltar el mouse.
     *
     * @param e es el <code>evento</code> que se genera en al soltar el mouse
     */
    @Override
    public void mouseReleased(MouseEvent e) {
    }

    /**
     * Metodo <I>mouseMoved</I> sobrescrito de la interface
     * <code>MouseListener</code>.<P>
     * En este metodo maneja el evento que se genera al mover el mouse.
     *
     * @param e es el <code>evento</code> que se genera en al mover el mouse
     */
    @Override
    public void mouseMoved(MouseEvent e) {
    }
//a

    /**
     * Metodo <I>mouseDragged</I> sobrescrito de la interface
     * <code>MouseListener</code>.<P>
     * En este metodo maneja el evento que se genera al jalar el mouse.
     *
     * @param e es el <code>evento</code> que se genera en al jalar el mouse
     */
    @Override
    public void mouseDragged(MouseEvent e) {
    }

}
