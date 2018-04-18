package Enemies;


import Constants.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Klasa opisująca statek
 */
public class Ship extends JLabel
{
    /**
     * Pozycja X statku
     */
    private int posX;
    /**
     * Pozycja Y statku
     */
    private int posY;
    /**
     * Szerokość statku
     */
    private int image_width;
    /**
     * Wysokość statku
     */
    private int image_height;
    /**
     * Zdjęcie statku
     */
    private ImageIcon imageShip;
    /**
     * Konstruktor statku
     */
    public Ship()
    {
        image_width = GameParam.shipWidth;
        image_height = GameParam.shipHeight;
        settingImage();
        this.posX = GameParam.defaultShipPosX;
        this.posY = GameParam.defaultShipPosY;
    }
    /**
     * Metoda ustawienia pozycji i ograniczenia komponentu statku
     * @param posX pozycja X
     * @param posY pozycja X
     */
    public void setPositions(int posX, int posY)
    {
        this.posX = posX;
        this.posY = posY;
        setBounds(posX, posY, this.image_width, this.image_height);
    }
    /**
     * Metoda ustawienia zdjęcia statku
     */
    private void settingImage()
    {
        try
        {
            //BufferedImage img = ImageIO.read(new File("src/Images/ship.png"));
            imageShip = new ImageIcon("src/Images/ship.png");
            setIcon(imageShip);
            setBackground(Constants.windowBgColor);
        }
        /*catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }*/
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    /**
     * Metoda pobierająca pozycję X statku
     * @return pozycja X
     */
    public int getPosX()
    {
        return posX;
    }
    /**
     * Metoda pobierająca pozycję Y statku
     * @return pozycja Y
     */
    public int getPosY()
    {
        return posY;
    }
    /**
     * Metoda pobierająca szerokość statku
     * @return szerokość
     */
    public int getImageWidth()
    {
        return image_width;
    }
    /**
     * Metoda pobierająca wysokość statku
     * @return wysokość
     */
    public int getImageHeight()
    {
        return image_height;
    }
}
