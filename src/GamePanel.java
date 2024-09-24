import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener {
    private final int PANEL_WIDTH = 800;
    private final int PANEL_HEIGHT = 600;
    private final int GROUND_LEVEL = PANEL_HEIGHT - 100;
    private final int PLAYER_SIZE = 50;
    private int playerX = 50;
    private int playerY = GROUND_LEVEL - PLAYER_SIZE;
    private int playerSpeed = 5;
    private int gravity = 2;
    private int jumpPower = 15;
    private int verticalSpeed = 0;
    private boolean isJumping = false;

    private Timer timer;

    // Constructor
    public GamePanel() {
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBackground(Color.CYAN);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        timer = new Timer(1000 / 60, this);
    }

    public void startGame() {
        timer.start();
    }

    // Dibuja al jugador y el suelo
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGround(g);
        drawPlayer(g);
    }

    private void drawGround(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(0, GROUND_LEVEL, PANEL_WIDTH, PANEL_HEIGHT - GROUND_LEVEL);
    }

    private void drawPlayer(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(playerX, playerY, PLAYER_SIZE, PLAYER_SIZE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Gravedad
        if (playerY + PLAYER_SIZE < GROUND_LEVEL) {
            verticalSpeed += gravity;
        } else {
            verticalSpeed = 0;
            isJumping = false;
            playerY = GROUND_LEVEL - PLAYER_SIZE;
        }

        playerY += verticalSpeed;

        repaint();
    }

    // Clase interna para gestionar la entrada del teclado
    private class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    playerX -= playerSpeed;
                    break;
                case KeyEvent.VK_RIGHT:
                    playerX += playerSpeed;
                    break;
                case KeyEvent.VK_SPACE:
                    if (!isJumping) {
                        isJumping = true;
                        verticalSpeed = -jumpPower;
                    }
                    break;
            }
        }
    }
}
