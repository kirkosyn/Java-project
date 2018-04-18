package Windows;

import Constants.*;

import java.awt.*;
import javax.swing.*;

/**
 * Klasa opisująca okno wygranej/przegranej
 */
public class WinWindow extends Windows
{
    /**
     * Przycisk akceptacji
     */
    private JButton acceptButton;

    /**
     * Etykieta gratulacji
     */
    private JLabel text;
    /**
     * Etykieta ilości zdobytych punktów przez użytkowwnika
     */
    private JLabel text2;

    /**
     * Panel, w którym znajdują się komponenty okna
     */
    private JPanel panel;
    /**
     * Panel, w którym znajduje się przycisk akceptacji
     */
    private JPanel buttonPanel;

    GridBagConstraints settings;

    /**
     * Konstruktor klasy WinWindow
     */
    public WinWindow() 
    {
        createComponents();
        settingComponents();
        configureWindow(Constants.frameTitle, Parameters.dimWin);
    }
    /**
     * metoda inicjująca komponenty okna
     */
    private void createComponents()
    {
        acceptButton = new JButton(Constants.acceptButtonTitle);

        text = new JLabel(Constants.winLabelText);
        text2 = new JLabel();

        panel = new JPanel(new GridBagLayout());
        buttonPanel = new JPanel();
    }
    /**
     * metoda ustawiająca parametry i rozłożenie komponentów w oknie
     */
    private void settingComponents()
    {
        settings = new GridBagConstraints();
        settings.fill = Constants.layoutConstraints;

        String score = "50";

        text2.setText(score);
        configureFont(text);
        configureFont(text2);

        buttonPanel.add(acceptButton);
        buttonPanel.setBackground(Constants.windowBgColor);

        settings.insets = new Insets(10, 0, 0, 0);
        panel.add(text, settings);

        settings.gridy = 1;
        panel.add(text2, settings);
        panel.setBackground(Constants.windowBgColor);

        settings.gridy = 2;
        panel.add(buttonPanel, settings);

        add(panel);
    }
}