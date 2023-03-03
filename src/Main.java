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


    private int cameraVX = 0;
    private int cameraVY = 0;

    private Rectangle hitbox;
    private Rectangle hitbox2;

    private Rectangle hitbox3;
    private int frames = 0;


    private int plcVY = 0;
    private int plcVX = 0;



    private int nftVY = 5;
    private int nftVX = 5;


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

        hitbox = new Rectangle(200, 450, camera.getWidth()/15, camera.getHeight()/15);
        hitbox2 = new Rectangle(1500, 450, plc.getWidth()/6, plc.getHeight()/6);
        hitbox3 = new Rectangle(900, 450, nft1.getWidth()/4, nft1.getHeight()/4);

        if (hitbox3.x > getWidth()) {
            nftVX = -5;
        }
        if (hitbox3.x < -20) {
            nftVX = 5;
        }

        if (hitbox3.y > getWidth()) {
            nftVY = -5;
        }
        if (hitbox3.x < getHeight()) {
            nftVY = 5;
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

        g.drawImage(backround1, 0, 0, backround1.getWidth(), backround1.getHeight(), null);
        g.drawImage(nft1, hitbox3.x, hitbox3.y, nft1.getWidth()/4, nft1.getHeight()/4, null);

        g.drawImage(plc, hitbox2.x, hitbox2.y, plc.getWidth()/6, plc.getHeight()/6, null);
        g.drawImage(camera, hitbox.x, hitbox.y, camera.getWidth()/15, camera.getHeight()/15, null);
        frames++;
    }

    private void update() {
        hitbox.x += cameraVX;

        hitbox.y += cameraVY;

        hitbox2.x += plcVX;

        hitbox2.y += plcVY;

        hitbox3.x += nftVX;

        hitbox3.y += nftVY;

        if (hitbox.intersects(hitbox3)) {
            hitbox3.x = (int) (Math.random()*1920);
            hitbox3.y = (int) (Math.random()*1080);
        }


     if (hitbox2.intersects(hitbox3)) {
        hitbox3.x = (int) (Math.random()*1920);
        hitbox3.y = (int) (Math.random()*1080);
    }
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