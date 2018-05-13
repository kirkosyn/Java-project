package Objects;

import Constants.Constants;
import Constants.GameParam;
import Constants.ShopItems;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Klasa opisująca statek
 */
public class Ship extends JComponent {
    /**
     * Pozycja X obiektu
     */
    private int posX;
    /**
     * Pozycja Y obiektu
     */
    private int posY;
    /**
     * Szerokość staktku
     */
    private int imageWidth = GameParam.shipWidth;
    /**
     * Wysokość statku
     */
    private int imageHeight = GameParam.shipHeight;
    /**
     * Wysokość ekranu
     */
    private int winHeight = Constants.gameFrameHeight;
    /**
     * szerokość ekranu
     */
    private int winWidth = Constants.gameFrameWidth;
    /**
     * Zdjęcie statku
     */
    private Image imageObject;

    /**
     * znormalizowanie prędkości (by wolniej poruszał się statek
     * po ekranie)
     */
    private static final double VELOCITY_DECAY = 0.5;

    /**
     * Przyspieszenie statku
     */
    private static final int ACCELERATION = 2;

    /**
     * czy statek przyspiesza
     */
    public boolean accelerating = false;
    /**
     * czy statek obraca się w prawo
     */
    public boolean turningRight = false;
    /**
     * czy statek obraca się w lewo
     */
    public boolean turningLeft = false;
    /**
     * przebyta droga X w przypadku poruszania się statku pod kątem
     */
    private double velocity_posX = 0;
    /**
     * przebyta droga Y w przypadku poruszania się statku pod kątem
     */
    private double velocity_posY = 0;
    /**
     * kąt, pod jakim porusza się statek
     */
    private double angle = 0;
    /**
     * prędkość obrotu statku
     */
    private double rotationalSpeed = 0.1;
    /**
     * lista pocisków
     */
    public ArrayList<Bullet> bullets = new ArrayList<Bullet>();

    /**
     * promień statku
     */
    public int radius = 10;

    /**
     * konstruktor statku
     */
    public Ship()
    {
        try
        {
            BufferedImage img;
            img = ImageIO.read(new File(ShopItems.pathImageShip));
            imageObject = img;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        posX = GameParam.defaultShipPosX - imageWidth/2;
        posY = GameParam.defaultShipPosY - imageHeight;
    }

    /**
     * metoda, która porusza statkiem po ekranie
     */
    public void move()
    {
        if (turningLeft)
            angle -= rotationalSpeed;
        if (turningRight)
            angle += rotationalSpeed;

        if (angle > (2 * Math.PI))
            angle -= (2 * Math.PI);
        else if (angle < 0)
            angle += (2 * Math.PI);

        if (accelerating)
        {
            velocity_posY -= ACCELERATION * Math.cos(angle);
            velocity_posX += ACCELERATION * Math.sin(angle);
        }

        posX = (int) (posX + velocity_posX);
        posY = (int) (posY + velocity_posY);

        velocity_posX *= VELOCITY_DECAY;
        velocity_posY *= VELOCITY_DECAY;

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
     * metoda odrysowująca statek
     * @param g grafika
     */
    public void draw(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;

        double locationX = imageObject.getWidth(this) / 2;
        double locationY = imageObject.getHeight(this) / 2;
        AffineTransform tx = AffineTransform.getRotateInstance(angle, locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

        g2.drawImage(op.filter((BufferedImage) imageObject, null), getPosX(), getPosY(), null);

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
     * metoda, zmieniająca przyspieszenie na zerowe
     */
    public void zeroAcceleration()
    {
        velocity_posX = 0;
        velocity_posY = 0;
    }
    /**
     * metoda zajmująca się wystrzeliwaniem pocisków
     */
    public void shoot_bullet()
    {
        Bullet bullet = new Bullet(posX, posY, angle-Math.PI/2);
        bullet.move();
        bullets.add(bullet);
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
     * Metoda pobierająca szerokość statku
     *
     * @return szerokość
     */
    public int getImageWidth() {return imageWidth;}
    /**
     * Metoda pobierająca wysokość statku
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
     * Metoda ustawiająca rozmiar statku
     * @param w szerokość
     * @param h wysokość
     */
    public void setSize(int w, int h)
    {
        imageHeight = h;
        imageWidth = w;
    }
}
