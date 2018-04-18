package Enemies;

import Constants.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Klasa opisująca duże komety
 */
public class BigObject extends JLabel {
    /**
     * Zdjęcie dużej komety
     */
    private ImageIcon imageBigObject;
    /**
     * Pozycja X dużej komety
     */
    int posX;
    /**
     * Pozycja Y dużej komety
     */
    int posY;
    /**
     * Szerokość dużej komety
     */
    private int imageWidth;
    /**
     * Wysokość dużej komety
     */
    private int imageHeight;

    /**
     * Konstruktor dużej komety
     * @param posX pozycja X
     * @param posY pozycja Y
     */
    public BigObject(int posX, int posY) {
        settingImage();
        this.posX = posX;
        this.posY = posY;
        this.imageWidth = GameParam.imageBigWidth;
        this.imageHeight = GameParam.imageBigHeight;

    }
    /**
     * Metoda ustawienia zdjęcia dużej komety
     */
    private void settingImage() {
        try {
            //BufferedImage img = ImageIO.read(new File(GameParam.pathImageBigObject));
            imageBigObject = new ImageIcon("src/Images/bigObject.png");
            setIcon(imageBigObject);
            setBackground(Constants.windowBgColor);
        }
            /*catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }*/ catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Metoda ustawienia pozycji i ograniczenia komponentu dużej komety
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
     * Metoda ustawienia rozłożenia obiektu dużej komety
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
     * Metoda pobierająca pozycję X dużej komety
     * @return pozycja X
     */
    public int getPosX() {
        return posX;
    }
    /**
     * Metoda pobierająca pozycję Y dużej komety
     * @return pozycja Y
     */
    public int getPosY() {
        return posY;
    }
    /**
     * Metoda pobierająca szerokość dużej komety
     * @return szerokość
     */
    public int getImageWidth() {
        return imageWidth;
    }
    /**
     * Metoda pobierająca wysokość dużej komety
     * @return wysokość
     */
    public int getImageHeight() {
        return imageHeight;
    }
}