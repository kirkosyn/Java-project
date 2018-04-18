package Windows;

import java.awt.*;
import javax.swing.*;

import Constants.*;
import javafx.util.Pair;

import java.util.Random;

/**
 * Klasa opisująca okna
 */
public class Windows extends JFrame
{
    /**
     * metoda ustawiająca parametry okna
     * @param frameTitle tytuł okna
     * @param dim wielkość okna (szerokość, wysokość)
     */
    void configureWindow(String frameTitle, Dimension dim)
    {
        setTitle(frameTitle);
        setPreferredSize(dim);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        Container contentPane = getContentPane();
        contentPane.setBackground(Constants.windowBgColor);
        pack();
    }
    /**
     * metoda ustawiająca parametry czcionki
     * @param text tekst,którego czcionka ma być ustawiona
     */
    void configureFont(JLabel text)
    {
        text.setFont(Parameters.font);
        text.setForeground(Constants.fontColor);
        text.setHorizontalAlignment(SwingConstants.CENTER);
    }
    /**
     * metoda losująca pozycję obiektu w oknie
     */
    Pair<Integer, Integer> randomGenerator()
    {
        Random rand = new Random();
        Integer valueX, valueY;

        while(true)
        {
            valueX = rand.nextInt(600);
            if (valueX < 190 || valueX > 410)
                break;
        }
        while(true)
        {
            valueY = rand.nextInt(480);
            if (valueY < 150 || valueY > 330)
                break;
        }

        Pair<Integer, Integer> pair = new Pair<> (valueX, valueY);

        return pair;
    }
}
