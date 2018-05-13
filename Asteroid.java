package Objects;

import Constants.Constants;
import Constants.GameParam;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Klasa opisująca asteroidy
 */
public class Asteroid extends JComponent
{
    /**
     * Pozycja X asteroidy
     */
    int posX;
    /**
     * Pozycja Y asteroidy
     */
    int posY;
    /**
     * Zdjęcie asteroidy
     */
    private Image imageObject;
    /**
     * Szerokość asteroidy
     */
    private int imageWidth;
    /**
     * Wysokość asteroidy
     */
    private int imageHeight;

//    private int winHeight = Constants.gameFrameHeight;
//    private int winWidth = Constants.gameFrameWidth;
    /**
     * przebyta droga X w przypadku poruszania się asteroidy pod kątem
     */
    private double velocity_posX = 0;
    /**
     * przebyta droga Y w przypadku poruszania się asteroidy pod kątem
     */
    private double velocity_posY = 0;
    /**
     * szybkość poruszania się asteroidy
     */
    private static final int SPEED = 4;
    /**
     * kąt, pod jakim porusza się asteroida
     */
    private double angle = 0;

    /**
     * opóźnienie poruszania się asteroidy
     */
    private static int delay = 0;
    /**
     * promień asteroidy
     */
    private double radius;
    /**
     * czy asteroida się rozdzieliła
     */
    private boolean splitted = false;
    /**
     * na ile asteroid podzieliła się asteroida
     */
    private double multiplier = 1;

    /**
     * konstruktor asteroidy
     * @param angle kąt
     * @param posX pozycja X
     * @param posY pozycja Y
     */
    public Asteroid(int posX, int posY, double angle)
    {
        try
        {
            BufferedImage img;
            img = ImageIO.read(new File(GameParam.pathImageBigObject));
            imageObject = img;
            imageWidth = GameParam.imageBigWidth;
            imageHeight = GameParam.imageBigHeight;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.posX = posX - imageWidth/2;
        this.posY = posY - imageHeight;
        this.angle = angle;
        this.radius = 40;

    }
    /**
     * konstruktor asteroidy
     * @param angle kąt
     * @param posX pozycja X
     * @param posY pozycja Y
     * @param multiplier na ile asteroid się rozdziela
     * @param splitted czy się rodzieliła asteroida
     */
    public Asteroid(int posX, int posY, double angle, double multiplier, boolean splitted)
    {
        try
        {
            BufferedImage img;
            img = ImageIO.read(new File(GameParam.pathImageSmallObject));
            imageObject = img;
            imageWidth = GameParam.imageSmallWidth;
            imageHeight = GameParam.imageSmallHeight;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.posX = posX - imageWidth/2;
        this.posY = posY - imageHeight;
        this.angle = angle;
        this.multiplier = multiplier;
        radius = 15;
        this.splitted = splitted;
    }

    /**
     * metoda, która porusza asteroidą po ekranie
     */
    public void move() {
        if (angle > (2 * Math.PI))
            angle -= (2 * Math.PI);
        else if (angle < 0)
            angle += (2 * Math.PI);

        velocity_posX = SPEED * Math.cos(angle);
        velocity_posY = SPEED * Math.sin(angle);

        posX = (int) (posX + velocity_posX);
        posY = (int) (posY + velocity_posY);

        if (posX <= 0 - imageWidth)
            posX = Constants.gameFrameWidth - imageWidth;
        if (posX >= Constants.gameFrameWidth + imageWidth)
            posX = 1 - imageWidth;
        if (posY >= Constants.gameFrameHeight - imageHeight)
            posY = 1 - imageHeight;
        if (posY <= 0 - imageHeight)
            posY = Constants.gameFrameHeight - imageHeight;

    }

    /**
     * metoda odrysowująca asteroidę
     * @param g grafika
     */
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(this.imageObject, this.getPosX(), this.getPosY(), null);

    }

    /**
     * metoda zmieniająca rozmiar obrazka pod względem zmiany okna
     * @param h nowa wysokość
     * @param w nowa szerokość
     */
    public void resize_img(final int w, final int h)
    {
        BufferedImage image = (BufferedImage) imageObject;
        BufferedImage resized = resize(image, h, w);
        imageObject = (Image) resized;
    }
    /**
     * metoda, która odrysowuje zmieniony obrazek
     * @param height nowa wysokość obrazka
     * @param width nowa szerokość obrazka
     * @param img zdjęcie obrazka
     * @return nowy obrazek
     */
    private static BufferedImage resize(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }
    /**
     * metoda sprawdzająca kolizję asteroidy ze statkiem
     * @param ship statek
     * @return prawda lub fałsz, jeżeli statek zderzył się
     */
    public boolean shipCollision(Ship ship)
    {
        boolean collided = false;

        double xdist = Math.pow((double) (this.posX - ship.getPosX()), 2.0);
        double ydist = Math.pow((double) (this.posY - ship.getPosY()), 2.0);
        double distance = Math.sqrt(xdist + ydist);

        if (distance <= this.radius + ship.radius)
            collided = true;

        return collided;
    }
    /**
     * Metoda sprawdzająca, czy pocisk zderzył się z asteroidą,
     * jeśli była to duża asteroida, to rozdziela się ona na dwie
     * mniejsze asteroidy, w przeciwnym przypadku zwraca null, co
     * oznacza, że asteroida została zniszczona
     *
     * @param bullet pociski
     * @return lista asteroid
     */
    public ArrayList<Asteroid> shotCollision(Bullet bullet)
    {
        double xdist = Math.pow((double) (posX - bullet.getPosX()), 2.0);
        double ydist = Math.pow((double) (posY - bullet.getPosY()), 2.0);
        double distance = Math.sqrt(xdist + ydist);

        if (distance <= radius)
        {
            if (!this.splitted)
            {
                Asteroid first = new Asteroid(posX, posY, angle - Math.PI / 2, 0.5, true);
                Asteroid second = new Asteroid(posX, posY, angle + Math.PI / 2, 0.5, true);
                ArrayList<Asteroid> list = new ArrayList<Asteroid>();
                list.add(first);
                list.add(second);
                return list;
            }
            else
                {
                ArrayList<Asteroid> list = new ArrayList<Asteroid>();
                list.add(null);
                return list;
                }
        }

        return null;
    }

    /**
     * metoda restartująca opóźnienie przesuwania się asteroidy
     */
    public static void restartDelay()
    {
        delay = 0;
    }

    /**
     * Metoda pobierająca pozycję X obiektu
     *
     * @return pozycja X
     */
    public int getPosX()
    {
        return posX;
    }

    /**
     * Metoda pobierająca pozycję Y obiektu
     *
     * @return pozycja Y
     */
    public int getPosY()
    {
        return posY;
    }
    /**
     * Metoda pobierająca szerokość asteroidy
     *
     * @return szerokość
     */
    public int getImageWidth() {return imageWidth;}
    /**
     * Metoda pobierająca wysokość asteroidy
     *
     * @return wysokość
     */
    public int getImageHeight() {return imageHeight;}

//    public void setWin(int w, int h)
//    {
//        winWidth = w;
//        winHeight = h;
//    }
    /**
     * Metoda ustawiająca rozmiar asteroidy
     * @param w szerokość
     * @param h wysokość
     */
    public void setSize(int w, int h)
    {
        imageHeight = h;
        imageWidth = w;
    }

}
