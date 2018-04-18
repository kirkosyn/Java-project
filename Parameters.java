package Constants;

import javafx.util.Pair;

import java.awt.*;

/**
 *  Ustawienia wielkości okien i czcionki
 */
public final class Parameters
{
    /**
     *  Zmienna określająca czcionkę
     */
    public static Font font;
    /**
     *  Zmienna określająca rozmiar okna głównego
     */
    public static Dimension dimMenu;
    /**
     *  Zmienna określająca rozmiar okna wyboru poziomu
     */
    public static Dimension dimLevels;
    /**
     *  Zmienna określająca rozmiar okna wygranej
     */
    public static Dimension dimWin;
    /**
     *  Zmienna określająca rozmiar okna zmiany statku
     */
    public static Dimension dimChange;
    /**
     *  Zmienna określająca rozmiar okna wpisania nicku
     */
    public static Dimension dimNick;
    /**
     *  Zmienna określająca rozmiar okna rankingu
     */
    public static Dimension dimRank;
    /**
     *  Zmienna określająca rozmiar okna sklepu
     */
    public static Dimension dimStore;
    /**
     *  Zmienna określająca rozmiar okna powtierdzenia
     */
    public static Dimension dimSure;
    /**
     *  Zmienna określająca rozmiar okna gry
     */
    public static Dimension dimGame;
    /**
     *  Zmienna określająca położenie statku
     */
    public static Pair<Integer,Integer> posXYship;

    static
    {
        configureParameters();
    }
    /**
     * Metoda inicjująca pola
     */
    public static void configureParameters()
    {
        font = new Font(Constants.fontName, Constants.fontDisplay, Constants.fontSize);
        dimMenu = new Dimension(Constants.mainMenuFrameWidth, Constants.mainMenuFrameHeight);
        dimLevels = new Dimension(Constants.levelsFrameWidth, Constants.levelsFrameHeight);
        dimWin = new Dimension(Constants.winFrameWidth, Constants.winFrameHeight);
        dimChange = new Dimension(Constants.shipsFrameWidth, Constants.shipsFrameHeight);
        dimNick = new Dimension(Constants.nickFrameWidth, Constants.nickFrameHeight);
        dimRank = new Dimension(Constants.rankFrameWidth, Constants.rankFrameHeight);
        dimStore = new Dimension(Constants.storeFrameWidth, Constants.storeFrameHeight);
        dimSure = new Dimension(Constants.sureFrameWidth, Constants.sureFrameHeight);
        dimGame = new Dimension(Constants.gameFrameWidth, Constants.gameFrameHeight);
        posXYship = new Pair<> (GameParam.defaultShipPosX, GameParam.defaultShipPosY);

    }

}
