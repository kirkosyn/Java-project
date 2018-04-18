package Constants;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileNotFoundException;

/**
 *  Stałe parametry inicjializacyjne wymagane do sklepu
 */
public final class ShopItems
{
    /**
     *  Ścieżka do pliku konfiguracyjnego
     */
    public static final String xmlConfigFile = "src\\ConfigFiles\\shop.xml";

    /**
     *  Zmienna określająca cenę 1. przedmiotu
     */
    public static int priceFirstItem;
    /**
     *  Zmienna określająca cenę 2. przedmiotu
     */
    public static int priceSecondItem;
    /**
     *  Zmienna określająca cenę 3. przedmiotu
     */
    public static int priceThirdItem;
    /**
     *  Zmienna określająca cenę 4. przedmiotu
     */
    public static int priceFourthItem;


    static
    {
        configureShopParameters();
    }
    /**
     * Metoda inicjująca pola z pliku konfiguracyjnego
     */
    public static void configureShopParameters()
    {
        try {
            File xmlInputFile = new File(xmlConfigFile);
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(xmlInputFile);
            doc.getDocumentElement().normalize();

            priceFirstItem = Integer.parseInt(doc.getElementsByTagName("priceFirstItem").item(0).getTextContent());
            priceSecondItem = Integer.parseInt(doc.getElementsByTagName("priceSecondItem").item(0).getTextContent());
            priceThirdItem = Integer.parseInt(doc.getElementsByTagName("priceThirdItem").item(0).getTextContent());
            priceFourthItem = Integer.parseInt(doc.getElementsByTagName("priceFourthItem").item(0).getTextContent());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
