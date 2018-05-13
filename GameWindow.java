package Windows;

import Objects.*;
import Constants.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

/**
 * klasa opisująca okno gry
 */
public class GameWindow extends JPanel implements KeyListener, Runnable
{
    /**
     * wysokość okna
     */
    private int winHeight = Constants.gameFrameHeight;
    /**
     * szerokość okna
     */
    private int winWidth = Constants.gameFrameWidth;

    /**
     * czy gra jest zapauzowana
     */
    private boolean paused = false;
    /**
     * czy gra się zakończyła
     */
    private boolean gameOver = false;

    /**
     * aktualna ilość punktów
     */
    public static int actual_score = GameParam.score;
    /**
     * ilość żyć
     */
    private static int lives = GameParam.life;

    /**
     * imię gracza
     */
    private static String name = null;
    /**
     * lista asteroid
     */
    private static ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();

    /**
     * czy spacja jest wciśnięta
     */
    private static boolean spacePressed = false;
    /**
     * statek
     */
    Ship ship;
    /**
     * ostatni czas systemu
     */
    private long last;
    /**
     * próg czasowy włączenia się strzelania (czas przytrzymania spacji)
     */
    private final long threshold = 160;
    /**
     * ile razy zwiększyła się szerokość okna
     */
    private double how_much_x;
    /**
     * ile razy zwiększyła się wysokość okna
     */
    private double how_much_y;

    /**
     * wątek
     */
    private Thread thread = null;
    /**
     * ilość małych asteroid
     */
    int amountOfSmallEnemies = GameParam.amountOfSmallEnemies;
    /**
     * ilość dużych asteroid
     */
    int amountOfBigEnemies = GameParam.amountOfBigEnemies;
    /**
     * Wybrany level
     */
    private int selected_lvl = GameParam.selected_level;
    /**
     * konstruktor klasy GameWindow
     */
    public GameWindow()
    {
        switch(selected_lvl)
        {
            case 1:
                amountOfBigEnemies = GameParam.amountOfBigEnemies;
                amountOfSmallEnemies = GameParam.amountOfSmallEnemies;
                break;
            case 2:
                amountOfSmallEnemies = GameParam.amountOfSmallEnemiesSecondLvl;
                amountOfBigEnemies = GameParam.amountOfBigEnemiesSecondLvl;
                break;
            case 3:
                amountOfBigEnemies = GameParam.amountOfBigEnemiesThirdLvl;
                amountOfSmallEnemies = GameParam.amountOfSmallEnemiesThirdLvl;
                break;

                default:
                    amountOfBigEnemies = GameParam.amountOfBigEnemies;
                    amountOfSmallEnemies = GameParam.amountOfSmallEnemies;
                    break;

        }
    }
    /**
     * Metoda uruchamiająca grę
     */
    public void run() {
        while (!gameOver) {
            if (!paused) {
                setBackground(Constants.windowBgColor);

                addComponentListener(new ComponentAdapter() {
                    public void componentResized(ComponentEvent ce) {
                        try
                        {
                            how_much_x = ce.getComponent().getWidth()/ (double)winWidth;
                            how_much_y = ce.getComponent().getHeight()/ (double)winHeight;
                            ship.resize_img((int)(ship.getImageWidth()*how_much_x),
                                    (int)(ship.getImageHeight()*how_much_y));
                            asteroids.forEach(asteroid -> asteroid.resize_img((int)(asteroid.getImageWidth()*how_much_x),
                                    (int)(asteroid.getImageHeight()*how_much_y)));
                            setSize(ce.getComponent().getWidth(), ce.getComponent().getHeight());
                        }
                        catch (NullPointerException ee) {
                            return;
                        }
                    }
                });

                collisionCheck();
                moveComponents();
                repaint();

                try {
                    Thread.sleep(17);
                } catch (InterruptedException e) {
                }
                if (asteroids.isEmpty()) {
                    setBackground(Color.GREEN);
                    gameOver = true;
                    JOptionPane.showMessageDialog(this, "Brawo! Wygrałeś! Twoja punktacja to: " + actual_score);

                }
            } else
                setBackground(Constants.windowBgColor);

            if (!asteroids.isEmpty() && lives <= 0) {
                setBackground(Color.RED);
                gameOver = true;
                JOptionPane.showMessageDialog(this, "Niestety, przegrałeś. Twoja punktacja to: " + actual_score);
            }
        }
    }
    /**
     * Metoda tworząca obiekty na ekranie
     */
    public void createComponents()
    {
        last = System.currentTimeMillis();
        ship = new Ship();
        Random x = new Random();
        Random angle = new Random();

        int xc, yc;
        double th;

        for (int i = 0; i < amountOfBigEnemies; i++)
        {
            xc = x.nextInt(Constants.gameFrameWidth - 1) + 1;
            yc = x.nextInt(Constants.gameFrameHeight - 1) + 1;
            th = angle.nextDouble();
            Asteroid ast = new Asteroid(xc, yc, th);
            asteroids.add(ast);
        }

        for (int i = 0; i < amountOfSmallEnemies; i++)
        {
            xc = x.nextInt(Constants.gameFrameWidth - 1) + 1;
            yc = x.nextInt(Constants.gameFrameHeight - 1) + 1;
            th = angle.nextDouble();
            Asteroid ast = new Asteroid(xc, yc, th, 1, true);
            asteroids.add(ast);
        }

        settingComponents();

        thread = new Thread(this);
        thread.start();
    }
    /**
     * Metoda ustawiająca komponenty okna
     */
    public void settingComponents() {
        setBackground(Constants.windowBgColor);
        setSize(Parameters.dimMenu);
        setFocusable(true);
        addKeyListener(this);
        setVisible(true);
    }
    /**
     * Metoda, która zajmuje się poruszaniem obiektów po ekranie
     */
    public void moveComponents() {
        for (int i = 0; i < ship.bullets.size(); i++)
            if (ship.bullets.get(i).move())
                ship.bullets.remove(i);
        for (int i = 0; i < asteroids.size(); i++)
            asteroids.get(i).move();
        ship.move();
    }
    /**
     * Metoda usuwająca obiekty z gry
     */
    public void destroyComponents() {
        ship = null;
        asteroids.clear();
    }

    /**
     * Metoda sprawdzająca kolizje zachodzące w grze
     * - zderzenie statku z asteroidą
     * - zderzenie asteroidy z pociskiem
     * jeżeli zaszło zderzenie asteroidy, usuwana jest
     * ona z listy i dodawane są za nią punkty graczowi
     * jeżeli zaszło zderzenie statku, kończy się gra
     */
    public void collisionCheck() {
        for (int j = 0; j < asteroids.size(); j++)
            for (int i = 0; i < ship.bullets.size(); i++) {
                ArrayList<Asteroid> returned = null;
                if (j < asteroids.size() && i < ship.bullets.size())
                    returned = asteroids.get(j).shotCollision(ship.bullets.get(i));
                if (returned != null && returned.size() == 2)
                {
                    asteroids.add(returned.get(0));
                    asteroids.add(returned.get(1));
                    asteroids.remove(asteroids.get(j));
                    ship.bullets.remove(i);
                    actual_score += GameParam.pointsForBig;
                }
                else if (returned != null && returned.size() == 1)
                {
                    asteroids.remove(asteroids.get(j));
                    ship.bullets.remove(i);
                    actual_score += GameParam.pointsForSmall;
                }
            }
        for (int i = 0; i < asteroids.size(); i++)
            if (asteroids.get(i).shipCollision(ship)) {
                lives -= 1;
                gameOver = true;
                destroyComponents();
                createComponents();
            }
    }

    /**
     * Metoda odrysowująca ekran gry
     */
    public void paintComponent(Graphics g)
    {
        try {
            super.paintComponent(g);
            g.setFont(Parameters.font);
            g.drawString("Liczba zyc: " + String.valueOf(lives), 5, 40);
            g.drawString("Liczba punktow: " + String.valueOf(actual_score), 5, 20);

            ship.draw(g);

            for (int i = 0; i < ship.bullets.size(); i++)
                ship.bullets.get(i).draw(g);
            for (int i = 0; i < asteroids.size(); i++)
                asteroids.get(i).draw(g);
        }

        catch (NullPointerException e)
        {
            return;
        }
    }

    /**
     * Metoda ustalająca zdarzenia przycisków na klawiaturze
     * - kiedy przycisk jest puszczony
     */
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (!paused && !gameOver) {
            if (key == KeyEvent.VK_UP) {
                ship.zeroAcceleration();
                ship.accelerating = false;
            }
            if (key == KeyEvent.VK_LEFT)
                ship.turningLeft = false;
            if (key == KeyEvent.VK_RIGHT)
                ship.turningRight = false;
        }
    }
    /**
     * Metoda ustalająca zdarzenia przycisków na klawiaturze
     * - kiedy przycisk jest wciśnięty
     */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (!paused && !gameOver) {
            if (key == KeyEvent.VK_UP)
                ship.accelerating = true;
            else if (key == KeyEvent.VK_LEFT)
                ship.turningLeft = true;
            else if (key == KeyEvent.VK_RIGHT)
                ship.turningRight = true;
            else if (key == KeyEvent.VK_SPACE) {
                long now = System.currentTimeMillis();
                if (now - last >= threshold) {
                    ship.shoot_bullet();
                    last = now;
                    spacePressed = true;
                }
            }
        }
        if (!gameOver)
            if (key == KeyEvent.VK_P) {
                if (!paused)
                    paused = true;
                else
                    paused = false;
            }
        if (key == KeyEvent.VK_ENTER) {
            if (gameOver && lives > 0) {
                gameOver = false;
                destroyComponents();
                createComponents();
            }
        }
    }

    /**
     * Metoda ustalająca zdarzenia przycisków na klawiaturze
     * - kiedy przycisk jest przyciśnięty
     */
    public void keyTyped(KeyEvent e) {
    }
    /**
     * Metoda ustalająca, czy panel gry jest aktywny (skoncentrowany)
     * @return zawsze true (zawsze aktywny i wrażliwy na przyciski)
     */
    public boolean isFocusTraversable ( ) {
        return true ;
    }
}

