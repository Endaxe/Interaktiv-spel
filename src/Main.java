import javax.crypto.CipherInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.imageio.ImageIO;

public class Main extends Canvas implements Runnable{
    private BufferStrategy bs;

    private boolean running = false;
    private Thread thread;

    private BufferedImage backround1;
    private BufferedImage backround;
    private BufferedImage camera;
    private BufferedImage nft1;

    private BufferedImage plc;

    private BufferedImage nft2;

    private int cameraX = 200;
    private int cameraY = 450;
    private int cameraVX = 0;
    private int cameraVY = 0;
    private int frames = 0;


    private int plcVY = 0;
    private int plcVX = 0;

    private int plcX = 1500;
    private int plcY = 450;

    private int nftVY = 0;
    private int nftVX = 0;

    private int nftX = 900;
    private int nftY = 400;

    public Main() {
        try {
            backround1 = ImageIO.read(new File("backround1.jpg"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            backround = ImageIO.read(new File("backround.jpg"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            camera = ImageIO.read(new File("camera.png"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            plc = ImageIO.read(new File("police.png"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            nft1 = ImageIO.read(new File("nft1.png"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // setSize(1920,1080);
        setSize(screenSize);
        JFrame frame = new JFrame();
        frame.add(this);
        frame.addKeyListener(new MyKeyListener());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


    }

    public void render() {
        bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        // Rita ut den nya bilden
        draw(g);

        g.dispose();
        bs.show();
    }

    public void draw(Graphics g) {

        if (frames < 75) {
            g.drawImage(backround, 0, 0, backround.getWidth(), backround.getHeight(), null);

        } else  g.clearRect(0,0, getWidth(), getHeight()); {
        }  if (frames < 175){
            g.drawImage(backround1, 0, 0, backround1.getWidth(), backround1.getHeight(), null);
        }


        g.drawImage(nft1, nftX, nftY, nft1.getWidth()/4, nft1.getHeight()/4, null);

        g.drawImage(plc, plcX, plcY, plc.getWidth()/6, plc.getHeight()/6, null);
        g.drawImage(camera, cameraX, cameraY, camera.getWidth()/15, camera.getHeight()/15, null);
        frames++;
    }

    private void update() {
        cameraX += cameraVX;

        cameraY += cameraVY;

        plcX += plcVX;

        plcY += plcVY;

        nftX += nftVX;

        nftY += nftVY;
    }

    public static void main(String[] args) {
        Main minGrafik = new Main();
        minGrafik.start();
    }

    public synchronized void start() {
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        double ns = 1000000000.0 / 60.0;
        double delta = 0;
        long lastTime = System.nanoTime();

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while(delta >= 1) {
                // Uppdatera koordinaterna
                update();
                // Rita ut bilden med updaterad data
                render();
                delta--;
            }
        }
        stop();
    }

    public class MyMouseMotionListener implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent e) {
        }

        @Override
        public void mouseMoved(MouseEvent e) {
        }
    }

    public class MyMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }



    public class MyKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyChar() == 'w') {
                cameraVY = -13;
            }
            if (e.getKeyChar() == 's') {
                cameraVY = 13;
                }

            if (e.getKeyChar() == 'd') {
                cameraVX = 13;
            }

            if (e.getKeyChar() == 'a') {
                cameraVX = -13;
            }

            if (e.getKeyCode() == KeyEvent.VK_UP) {
                plcVY = -13;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                plcVY = 13;
            }

            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                plcVX = 13;
            }

            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                plcVX = -13;
            }


        }

        @Override
        public void keyReleased(KeyEvent e) {

            if (e.getKeyChar() == 'w') {
                cameraVY = 0;
            }
            if (e.getKeyChar() == 's') {
                cameraVY = 0;
            }

            if (e.getKeyChar() == 'd') {
                cameraVX = 0;
            }

            if (e.getKeyChar() == 'a') {
                cameraVX = 0;
            }

            if (e.getKeyCode() == KeyEvent.VK_UP) {
                plcVY = 0;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                plcVY = 0;
            }

            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                plcVX = 0;
            }

            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                plcVX = 0;
            }


        }
    }
}