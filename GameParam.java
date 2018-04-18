package Constants;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileNotFoundException;

/**
 *  Stałe parametry inicjializacyjne wymagane do startu gry
 */
public final class GameParam
{
    /**
     *  Ścieżka do pliku konfiguracyjnego
     */
    public static final String xmlConfigFile = "src\\ConfigFiles\\level1.xml";
    /**
     *  Ścieżka do pliku konfiguracyjnego
     */
    public static final String xmlConfigFileSecondLvl = "src\\ConfigFiles\\level2.xml";
    /**
     *  Ścieżka do pliku konfiguracyjnego
     */
    public static final String xmlConfigFileThirdLvl = "src\\ConfigFiles\\level3.xml";

    /**
     *  Zmienna określająca ilość małych komet lvl 1
     */
    public static int amountOfSmallEnemies;
    /**
     *  Zmienna określająca ilość dużych komet lvl 1
     */
    public static int amountOfBigEnemies;
    /**
     *  Zmienna określająca ilość małych komet lvl 2
     */
    public static int amountOfSmallEnemiesSecondLvl;
    /**
     *  Zmienna określająca ilość dużych komet lvl 2
     */
    public static int amountOfBigEnemiesSecondLvl;
    /**
     *  Zmienna określająca ilość małych komet lvl 3
     */
    public static int amountOfSmallEnemiesThirdLvl;
    /**
     *  Zmienna określająca ilość dużych komet lvl 3
     */
    public static int amountOfBigEnemiesThirdLvl;

    /**
     *  Zmienna określająca ilość punktów za zniszczenie małej komety
     */
    public static int pointsForSmall;
    /**
     *  Zmienna określająca ilość punktów za zniszczenie dużej komety
     */
    public static int pointsForBig;

    /**
     *  Zmienna określająca domyślną pozycję X statku
     */
    public static int defaultShipPosX;
    /**
     *  Zmienna określająca domyślną pozycję Y statku
     */
    public static int defaultShipPosY;

    /**
     *  Zmienna określająca szerokość statku
     */
    public static int shipWidth;
    /**
     *  Zmienna określająca wysokość statku
     */
    public static int shipHeight;

    /**
     *  Zmienna określająca liczbę żyć
     */
    public static int life;
    /**
     *  Zmienna określająca ilość zdobytych punktów
     */
    public static int score;

    /**
     *  Zmienna określająca szerokość małej komety
     */
    public static int imageSmallWidth;
    /**
     *  Zmienna określająca wysokość małej komety
     */
    public static int imageSmallHeight;

    /**
     *  Zmienna określająca szerokość dużej komety
     */
    public static int imageBigWidth;
    /**
     *  Zmienna określająca wysokość dużej komety
     */
    public static int imageBigHeight;

    /**
     *  Zmienna określająca ściężkę do zdjęcia małej komety
     */
    public static String pathImageSmallObject;
    /**
     *  Zmienna określająca ściężkę do zdjęcia dużej komety
     */
    public static String pathImageBigObject;

    static
    {
        configureGameParameters();
    }
    /**
     * Metoda inicjująca pola z pliku konfiguracyjnego
     */
    public static void configureGameParameters()
    {
        try
        {
            File xmlInputFile = new File(xmlConfigFile);
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(xmlInputFile);
            doc.getDocumentElement().normalize();

            amountOfSmallEnemies = Integer.parseInt(doc.getElementsByTagName("amountOfSmallEnemies").item(0).getTextContent());
            amountOfBigEnemies = Integer.parseInt(doc.getElementsByTagName("amountOfBigEnemies").item(0).getTextContent());

            pointsForSmall = Integer.parseInt(doc.getElementsByTagName("pointsForSmall").item(0).getTextContent());
            pointsForBig = Integer.parseInt(doc.getElementsByTagName("pointsForBig").item(0).getTextContent());

            defaultShipPosX = Integer.parseInt(doc.getElementsByTagName("defaultShipPosX").item(0).getTextContent());
            defaultShipPosY = Integer.parseInt(doc.getElementsByTagName("defaultShipPosY").item(0).getTextContent());

            shipWidth = Integer.parseInt(doc.getElementsByTagName("shipWidth").item(0).getTextContent());
            shipHeight = Integer.parseInt(doc.getElementsByTagName("shipHeight").item(0).getTextContent());

            life = Integer.parseInt(doc.getElementsByTagName("life").item(0).getTextContent());

            imageSmallWidth = Integer.parseInt(doc.getElementsByTagName("imageSmallWidth").item(0).getTextContent());
            imageSmallHeight = Integer.parseInt(doc.getElementsByTagName("imageSmallHeight").item(0).getTextContent());

            imageBigWidth = Integer.parseInt(doc.getElementsByTagName("imageBigWidth").item(0).getTextContent());
            imageBigHeight = Integer.parseInt(doc.getElementsByTagName("imageBigHeight").item(0).getTextContent());

            pathImageSmallObject = doc.getElementsByTagName("pathImageSmallObject").item(0).getTextContent();
            pathImageBigObject = doc.getElementsByTagName("pathImageBigObject").item(0).getTextContent();


            File xmlInputFileSecondLvl = new File(xmlConfigFileSecondLvl);
            DocumentBuilderFactory docFactorySecondLvl = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilderSecondLvl = docFactorySecondLvl.newDocumentBuilder();
            Document docSecondLvl = docBuilderSecondLvl.parse(xmlInputFileSecondLvl);
            docSecondLvl.getDocumentElement().normalize();

            amountOfSmallEnemiesSecondLvl = Integer.parseInt(doc.getElementsByTagName("amountOfSmallEnemies").item(0).getTextContent());
            amountOfBigEnemiesSecondLvl = Integer.parseInt(doc.getElementsByTagName("amountOfBigEnemies").item(0).getTextContent());

            File xmlInputFileThirdLvl = new File(xmlConfigFileThirdLvl);
            DocumentBuilderFactory docFactoryThirdLvl = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilderThirdLvl = docFactoryThirdLvl.newDocumentBuilder();
            Document docThirdLvl = docBuilderThirdLvl.parse(xmlInputFileThirdLvl);
            docThirdLvl.getDocumentElement().normalize();

            amountOfSmallEnemiesThirdLvl = Integer.parseInt(doc.getElementsByTagName("amountOfSmallEnemies").item(0).getTextContent());
            amountOfBigEnemiesThirdLvl = Integer.parseInt(doc.getElementsByTagName("amountOfBigEnemies").item(0).getTextContent());
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
