package Windows;

import Enemies.*;
import Constants.*;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class GameWindow extends Windows
{
    private JPanel gamePanel;
    private JPanel gameStatus;
    private Ship ship;
    private Vector<SmallObject> small_objects;
    private Vector<BigObject> big_objects;
    private JLabel score;
    private int actual_score;
    private JLabel life;
    //private int windowSizeWidth;
    //private int windowSizeHeight;

    public GameWindow()
    {
        setLayout(new GridBagLayout());
        createComponents();
        settingComponents();
        configureWindow(Constants.frameTitle, Parameters.dimGame);
    }

    private void createComponents()
    {
        actual_score = 0;
        gamePanel = new JPanel(new GridBagLayout());
        gameStatus = new JPanel(new GridLayout(2, 1));
        small_objects = new Vector<SmallObject>();
        big_objects = new Vector<BigObject>();
        ship = new Ship();
        life = new JLabel("Liczba zyc: " + String.valueOf(GameParam.life));
        configureFont(life);
        score = new JLabel("Liczba punktow: " + String.valueOf(actual_score));
        configureFont(score);
    }

    private void settingComponents()
    {
        int amountOfSmallEnemies = GameParam.amountOfSmallEnemies;
        int amountOfBigEnemies = GameParam.amountOfBigEnemies;

        //Random rand = new Random();

        for (int i = 0; i < amountOfBigEnemies; i++)
            big_objects.add(new BigObject(randomGenerator().getKey(), randomGenerator().getValue()));

        for (int i = 0; i < amountOfSmallEnemies; i++)
            small_objects.add(new SmallObject(randomGenerator().getKey(), randomGenerator().getValue()));


        GridBagConstraints settings = new GridBagConstraints();
        settings.fill = Constants.layoutConstraints;

        gameStatus.add(score);
        gameStatus.add(life);
        gameStatus.setBounds(190,0,200,40);
        gameStatus.setBackground(Constants.windowBgColor);

        settings.gridx = 190;
        settings.gridy = 0;
        settings.gridwidth = 200;
        settings.gridheight = 40;
        add(gameStatus, settings);

        int posX = ship.getPosX()-ship.getImageWidth()/2;
        int posY = ship.getPosY()-ship.getImageHeight()/2;
        ship.setPositions(posX, posY);

        settings.gridx = ship.getPosX();
        settings.gridy = ship.getPosY();
        settings.gridwidth = ship.getImageWidth();
        settings.gridheight = ship.getImageHeight();
        add(ship, settings);

        big_objects.forEach(item -> add(item, item.setSettings(settings, item.getPosX(), item.getPosY())));
        small_objects.forEach(item -> add(item, item.setSettings(settings, item.getPosX(), item.getPosY())));
    }
}
