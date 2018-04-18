package Enemies;

import Constants.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Klasa opisująca małe komety
 */
public class SmallObject extends JLabel
{
    /**
     * Zdjęcie małej komety
     */
    private ImageIcon imageSmallObject;
    /**
     * Szerokość małej komety
     */
    private int imageWidth;
    /**
     * Wysokość małej komety
     */
    private int imageHeight;
    /**
     * Pozycja X małej komety
     */
    int posX;
    /**
     * Pozycja Y małej komety
     */
    int posY;
    /**
     * Konstruktor małej komety
     * @param posX pozycja X
     * @param posY pozycja Y
     */
    public SmallObject(int posX, int posY)
    {
        settingImage();
        this.posX = posX;
        this.posY = posY;
        this.imageWidth = GameParam.imageSmallWidth;
        this.imageHeight = GameParam.imageSmallHeight;
    }
    /**
     * Metoda ustawienia pozycji i ograniczenia komponentu małej komety
     * @param posX pozycja X
     * @param posY pozycja X
     */
    public void setPositions(int posX, int posY)
    {
        this.posX = posX;
        this.posY = posY;
        setBounds(posX, posY, this.imageWidth, this.imageHeight);
    }
    /**
     * Metoda ustawienia zdjęcia małej komety
     */
    private void settingImage()
    {
        try
        {
            //BufferedImage img = ImageIO.read(new File(GameParam.pathImageSmallObject));
            imageSmallObject = new ImageIcon("src/Images/smallObject.png");
            setIcon(imageSmallObject);
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
     * Metoda ustawienia rozłożenia obiektu małej komety
     * @param settings ustawienia pobrane z okna
     * @param setPosX ustawienie nowej pozycji X
     * @param setPosY ustawienie nowej pozycji Y
     * @return ograniczenia
     */
    public GridBagConstraints setSettings(GridBagConstraints settings, int setPosX, int setPosY)
    {
        settings.gridx = setPosX;
        settings.gridy = setPosY;
        settings.gridwidth = this.getImageWidth();
        settings.gridheight = this.getImageHeight();
        this.setPositions(setPosX, setPosY);
        return settings;
    }
    /**
     * Metoda pobierająca pozycję X małej komety
     * @return pozycja X
     */
    public int getPosX()
    {
        return posX;
    }
    /**
     * Metoda pobierająca pozycję Y małej komety
     * @return pozycja Y
     */
    public int getPosY()
    {
        return posY;
    }
    /**
     * Metoda pobierająca szerokość małej komety
     * @return pozycja szerokość
     */
    public int getImageWidth()
    {
        return imageWidth;
    }
    /**
     * Metoda pobierająca wysokość małej komety
     * @return pozycja wysokość
     */
    public int getImageHeight()
    {
        return imageHeight;
    }
}
