package Windows;

import Constants.*;

import java.awt.*;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.ImageIcon;

/**
 * Klasa opisująca okno sklepu
 */
public class StoreWindow extends Windows
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
     * Etykieta wyboru produktu
     */
    private JLabel text;

    /**
     * Panel, w którym znajduje się etykieta wyboru produktu
     */
    private JPanel textPanel;
    /**
     * Panel, w którym znajdują się komponenty okna
     */
    private JPanel panel;

    /**
     * Wektor RadioButtonów, umożliwiających wybór produktu
     */
    private Vector<JRadioButton> shopItems;
    /**
     * Wektor zdjęć statków
     */
    private Vector<JLabel> itemsGraphics;
    /**
     * Panel, w którym znajdują się komponenty produktów
     */
    private JPanel shopPanel;

    /**
     * Konstruktor klasy StoreWindow
     */
    public StoreWindow() 
    {
        createComponents();
        settingComponents();
        configureWindow(Constants.storeFrameTitle, Parameters.dimStore);
    }
    /**
     * metoda inicjująca komponenty okna
     */
    private void createComponents()
    {
        acceptButton = new JButton(Constants.acceptButtonTitle);
        cancelButton = new JButton(Constants.cancelButtonTitle);

        text = new JLabel(Constants.storeLabelText);

        textPanel = new JPanel();
        panel = new JPanel(new GridBagLayout());
        //shopPanel = new JPanel(new GridBagLayout());
        shopPanel = new JPanel(new GridLayout(4,2));

        shopItems = new Vector<JRadioButton>(4);
        itemsGraphics = new Vector<JLabel>(4);
    }
    /**
     * metoda ustawiająca parametry i rozłożenie komponentów w oknie
     */
    private void settingComponents()
    {
        configureFont(text);
        textPanel.add(text);
        textPanel.setBackground(Constants.windowBgColor);

        GridBagConstraints settings = new GridBagConstraints();
        settings.fill = Constants.layoutConstraints;

        for (int i = 0; i < 4; i++)
        {
            shopItems.add(new JRadioButton());
            itemsGraphics.add(new JLabel());
        }

        ImageIcon icon = new ImageIcon("src/Images/ship.png");
        itemsGraphics.forEach(item -> item.setIcon(icon));
        shopItems.forEach(item -> item.setBackground(Constants.windowBgColor));

        shopPanel.add(itemsGraphics.elementAt(0));
        shopPanel.add(itemsGraphics.elementAt(1));
        shopPanel.add(shopItems.elementAt(0));
        shopPanel.add(shopItems.elementAt(1));
        shopPanel.add(itemsGraphics.elementAt(2));
        shopPanel.add(itemsGraphics.elementAt(3));
        shopPanel.add(shopItems.elementAt(2));
        shopPanel.add(shopItems.elementAt(3));

        shopPanel.setBackground(Constants.windowBgColor);

        settings.gridwidth = 2;
        settings.insets = new Insets(10, 0, 0, 0);
        panel.add(textPanel, settings);

        settings.gridy = 1;
        settings.insets = new Insets(10, 50, 0, 0);
        panel.add(shopPanel, settings);

        settings.gridy = 2;
        settings.gridwidth = 1;
        panel.add(acceptButton, settings);

        settings.gridx = 1;
        settings.insets = new Insets(10, 50, 0, 50);
        panel.add(cancelButton, settings);

        panel.setBackground(Constants.windowBgColor);

        add(panel);

    }
}