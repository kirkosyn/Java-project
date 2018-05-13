package Windows;

import javax.swing.*;

import Constants.*;

import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.event.*;

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
     * Komponent do tworzenia dialogów
     */
    private JOptionPane optionPane;
    /**
     * Komponent oddzielający MenuItems w pasku menu
     */
    private Component box = Box.createHorizontalGlue();
    /**
     * Komponent przechowujący zdjęcie statku
     */
    private JLabel shipjpg;

    /**
     * Konstruktor klasy MenuWindow
     */
    public MenuWindow()
    {
        createComponents();
        launchFrame();
        configureWindow(Constants.frameTitle, Parameters.dimMenu);
        eventListen();
        //addKeyListener(this);
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
     * Metoda nasłuchująca zdarzenia
     */
    private void eventListen()
    {
        optionPane = new JOptionPane();

        startGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().removeAll();
                revalidate();
                repaint();
                try
                {
                    String inputValue = optionPane.showInputDialog(null,
                            Constants.nickLabelText,
                            Constants.frameTitle,
                            JOptionPane.PLAIN_MESSAGE);
                    if(!inputValue.isEmpty()) {
                        getContentPane().removeAll();
                        GameWindow game = new GameWindow();
                        getContentPane().add(game);
                        game.requestFocus();
                        revalidate();
                        repaint();
                        game.createComponents();
                    }
                }
                catch (Exception ee)
                {
                    return;
                }
            }
        });


        bestUsers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().removeAll();
                getContentPane().add(new RankingWindow());
                revalidate();
                repaint();
            }
        });

        levels.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().removeAll();
                getContentPane().add(new LevelsWindow());
                revalidate();
                repaint();
            }
        });

        shop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().removeAll();
                getContentPane().add(new StoreWindow());
                revalidate();
                repaint();
            }
        });

        changeShips.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().removeAll();
                getContentPane().add(new ChangeShipsWindow());
                revalidate();
                repaint();
            }
        });

        JFrame app = this;
        endGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] options = {Constants.acceptButtonTitle,
                        Constants.cancelButtonTitle};
                int choice = optionPane.showOptionDialog(
                        null,
                        Constants.sureLabelText,
                        Constants.storeFrameTitle,
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        null,
                        options,
                        options[1]);
                if (choice == 0)
                    dispatchEvent(new WindowEvent(app, WindowEvent.WINDOW_CLOSING));
            }
        });
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
        menuBar.add(box);
        menuBar.add(helpMenu);

        ImageIcon icon = new ImageIcon(ShopItems.pathImageShip);
        shipjpg = new JLabel(icon, JLabel.CENTER);

        setJMenuBar(menuBar);
        add(shipjpg);

    }
}