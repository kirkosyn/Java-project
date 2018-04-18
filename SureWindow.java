package Windows;

import Constants.*;

import java.awt.*;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;

/**
 * Klasa opisująca okno proszące o potwierdzenie użytkownika
 */
public class SureWindow extends Windows
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
     * Etykieta potwierdzenia
     */
    private JLabel text;

    /**
     * Panel, w którym znajdują się komponenty okna
     */
    private JPanel panel;
    /**
     * Panel, w którym znajduje się etykieta powterdzenia
     */
    private JPanel textPanel;

    /**
     * Konstruktor klasy SureWindow
     */
    public SureWindow() 
    {
        createComponents();
        settingComponents();
        configureWindow(Constants.storeFrameTitle, Parameters.dimSure);
    }
    /**
     * metoda inicjująca komponenty okna
     */
    private void createComponents()
    {
        acceptButton = new JButton(Constants.acceptButtonTitle);
        cancelButton = new JButton(Constants.cancelButtonTitle);
        text = new JLabel(Constants.sureLabelText);

        panel = new JPanel(new GridBagLayout());
        textPanel = new JPanel();
    }
    /**
     * metoda ustawiająca parametry i rozłożenie komponentów w oknie
     */
    private void settingComponents()
    {
        GridBagConstraints settings = new GridBagConstraints();
        settings.fill = Constants.layoutConstraints;

        configureFont(text);
        textPanel.add(text);
        textPanel.setBackground(Constants.windowBgColor);

        settings.gridwidth = 2;
        panel.add(textPanel, settings);

        settings.gridy = 1;
        settings.gridwidth = 1;
        settings.insets = new Insets(10, 0, 0, 0);
        panel.add(acceptButton, settings);

        settings.gridx = 1;
        settings.insets = new Insets(10, 40, 0, 0);
        panel.add(cancelButton, settings);

        panel.setBackground(Constants.windowBgColor);

        add(panel);
    }
}