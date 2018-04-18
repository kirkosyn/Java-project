package Windows;

import Constants.*;

import java.awt.*;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * Klasa opisująca okno wpisywania nicku przez użytkownika
 */
public class NickWindow extends Windows
{
    /**
     * Przycisk akceptacji
     */
    private JButton acceptButton;
    /**
     * Przycisk anulacji
     */
    private JButton cancelButton;

    /**
     * Etykieta wpisania nicka
     */
    private JLabel text;

    /**
     * Panel, w którym znajduje się etykieta wpisania nicka
     */
    private JPanel textPanel;
    /**
     * Panel, w którym znajdują się komponenty okna
     */
    private JPanel panel;

    /**
     * Obszar wpisywania nicku
     */
    private JTextField nickField;

    /**
     * Ustawienia rozłożenia komponentów w oknie
     */
    private GridBagConstraints settings;

    /**
     * Konstruktor klasy NickWindow
     */
    public NickWindow() 
    {
        createComponents();
        settingComponents();
        configureWindow(Constants.nickFrameTitle, Parameters.dimNick);
    }
    /**
     * metoda inicjująca komponenty okna
     */
    private void createComponents()
    {
        acceptButton = new JButton(Constants.acceptButtonTitle);
        cancelButton = new JButton(Constants.cancelButtonTitle);

        text = new JLabel(Constants.nickLabelText);
        textPanel = new JPanel();

        panel = new JPanel(new GridBagLayout());
        nickField = new JTextField();
    }
    /**
     * metoda ustawiająca parametry i rozłożenie komponentów w oknie
     */
    private void settingComponents()
    {
        configureFont(text);

        textPanel.add(text);
        textPanel.setBackground(Constants.windowBgColor);

        settings = new GridBagConstraints();
        settings.fill = Constants.layoutConstraints;
        settings.ipady = 10;

        settings.gridwidth = 2;
        panel.add(textPanel, settings);

        settings.gridy = 1;
        settings.gridwidth = 2;
        settings.insets = new Insets(0, 0, 10, 0);
        panel.add(nickField, settings);

        settings.gridy = 2;
        settings.gridwidth = 1;
        panel.add(acceptButton, settings);

        settings.gridx = 1;
        settings.insets = new Insets(0, 50, 10, 0);
        panel.add(cancelButton, settings);

        panel.setBackground(Constants.windowBgColor);

        add(panel);
    }
}