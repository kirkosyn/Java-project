package Windows;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.Box;
import Constants.*;

/**
 * Klasa opisująca okno główne menu
 */
public class MenuWindow extends Windows
{
    /**
     * Pasek menu gry
     */
    private JMenuBar menuBar;
    /**
     * Rozwijalna lista opcji gry
     */
    private JMenu gameMenu;
    /**
     * Przycisk pomocy
     */
    private JMenu helpMenu;
    /**
     * Przycisk menu startu gry
     */
    private JMenuItem startGame;
    /**
     * Przycisk menu rankingu użytkowników
     */
    private JMenuItem bestUsers;
    /**
     * Przycisk menu wyboru poziomów
     */
    private JMenuItem levels;
    /**
     * Przycisk menu sklepu
     */
    private JMenuItem shop;
    /**
     * Przycisk menu zmiany statku
     */
    private JMenuItem changeShips;
    /**
     * Przycisk menu zakończenia gry
     */
    private JMenuItem endGame;

    /**
     * Konstruktor klasy MenuWindow
     */
    public MenuWindow()
    {
        createComponents();
        launchFrame();
        configureWindow(Constants.frameTitle, Parameters.dimMenu);
    }
    /**
     * metoda inicjująca komponenty okna
     */
    private void createComponents()
    {
        menuBar = new JMenuBar();

        gameMenu = new JMenu(Constants.menuTitle);
        helpMenu = new JMenu(Constants.helpTitle);

        startGame = new JMenuItem(Constants.startMenuItemTitle);
        bestUsers = new JMenuItem(Constants.bestUsersMenuItemTitle);
        levels = new JMenuItem(Constants.levelsMenuItemTitle);
        shop = new JMenuItem(Constants.shopMenuItemTitle);
        changeShips = new JMenuItem(Constants.changeShipsMenuItemTitle);
        endGame = new JMenuItem(Constants.endGameMenuItemTitle);

    }
    /**
     * metoda ustawiająca parametry i rozłożenie komponentów w oknie
     */
    public void launchFrame() 
    {
        gameMenu.add(startGame);
        gameMenu.add(bestUsers);
        gameMenu.add(levels);
        gameMenu.add(shop);
        gameMenu.add(changeShips);
        gameMenu.add(endGame);
        menuBar.add(gameMenu);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(helpMenu);
        
        setJMenuBar(menuBar);
    }
}