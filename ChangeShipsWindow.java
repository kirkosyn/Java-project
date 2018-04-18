package Windows;

import Constants.*;

import java.awt.*;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;

/**
 * Klasa opisująca okno zmieniania statku
 */
public class ChangeShipsWindow extends Windows
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
     * Etykieta wyboru statku
     */
    private JLabel text;
    /**
     * Zdjęcie statku
     */
    private JLabel lblImage;

    /**
     * Wektor nazw statków
     */
    private Vector<String> shipModels;
    /**
     * Rozwijana lista nazw statków
     */
    private JComboBox<String> shipsSet;

    /**
     * Panel, w którym znajduje się etykieta wyboru statku
     */
    private JPanel textPanel;
    /**
     * Panel, w którym znajdują się komponenty okna
     */
    private JPanel panel;

    /**
     * Konstruktor klasy ChangeShipsWindow
     */
    public ChangeShipsWindow()
    {
        createComponents();
        settingComponents();
        configureWindow(Constants.shipsFrameTitle,Parameters.dimChange);
    }
    /**
     * metoda inicjująca komponenty okna
     */
    private void createComponents()
    {
        acceptButton = new JButton(Constants.acceptButtonTitle);
        cancelButton = new JButton(Constants.cancelButtonTitle);

        text = new JLabel(Constants.shipsLabelText);
        shipModels = new Vector<String>();
        shipsSet = new JComboBox<String>();

        textPanel = new JPanel();
        panel = new JPanel(new GridBagLayout());
    }
    /**
     * metoda ustawiająca parametry i rozłożenie komponentów w oknie
     */
    private void settingComponents()
    {
        lblImage = new JLabel();
        lblImage.setIcon(new ImageIcon("src/Images/ship.png"));
        configureFont(text);
        textPanel.add(text);
        textPanel.setBackground(Constants.windowBgColor);
        panel.setBackground(Constants.windowBgColor);

        GridBagConstraints settings = new GridBagConstraints();
        settings.fill = Constants.layoutConstraints;
        settings.ipady = 10;

        String model;
        model = "Model";

        for (int i = 0; i < 3; i++)
            shipModels.add(model + " " + (i+1));

        shipModels.forEach(ship -> shipsSet.addItem(ship));

        settings.gridwidth = 2;
        panel.add(textPanel, settings);

        settings.gridy = 1;
        settings.gridwidth = 1;
        panel.add(shipsSet, settings);

        settings.gridx = 1;
        settings.insets = new Insets(0, 60, 0, 0);
        panel.add(lblImage, settings);

        settings.gridx = 0;
        settings.gridy = 2;
        settings.insets = new Insets(20, 0, 0, 0);
        panel.add(acceptButton, settings);

        settings.gridx = 1;
        settings.insets = new Insets(20, 50, 0, 0);
        panel.add(cancelButton, settings);

        add(panel);
    }
}