package Windows;

import Constants.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.*;

/**
 * Klasa opisująca okno sklepu
 */
public class StoreWindow extends JPanel
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
     * Komponent do tworzenia dialogów
     */
    private JOptionPane optionPane = new JOptionPane();
    /**
     * Etykieta informująca o liczbie punktów
     */
    private JLabel points;

    /**
     * Konstruktor klasy StoreWindow
     */
    public StoreWindow() 
    {
        createComponents();
        settingComponents();
        //configureWindow(Constants.storeFrameTitle, Parameters.dimStore);
    }
    /**
     * metoda inicjująca komponenty okna
     */
    private void createComponents()
    {
        acceptButton = new JButton(Constants.acceptButtonTitle);
        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Object[] options = {Constants.acceptButtonTitle,
                        Constants.cancelButtonTitle};
                optionPane.showOptionDialog(
                        null,
                        Constants.sureLabelText,
                        Constants.storeFrameTitle,
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        null,
                        options,
                        options[1]);
            }
        });
        cancelButton = new JButton(Constants.cancelButtonTitle);

        text = new JLabel(Constants.storeLabelText);
        points = new JLabel();

        textPanel = new JPanel(new GridLayout(2, 1));
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
        Windows.configureFont(text);
        setLayout(new GridBagLayout());

        points.setText("Twoje punkty: " + GameParam.score);
        points.setHorizontalAlignment(0);
        textPanel.add(text);
        textPanel.add(points);
        textPanel.setBackground(Constants.windowBgColor);
        GridBagConstraints settings = new GridBagConstraints();
        settings.fill = Constants.layoutConstraints;

        for (int i = 0; i < 4; i++)
        {
            shopItems.add(new JRadioButton());
            itemsGraphics.add(new JLabel());
        }

        ImageIcon icon = new ImageIcon(ShopItems.pathImageShip4);
        ImageIcon icon2 = new ImageIcon(ShopItems.pathImageShip5);
        ImageIcon icon3 = new ImageIcon(ShopItems.pathImageHealth);

        itemsGraphics.elementAt(0).setIcon(icon);
        itemsGraphics.elementAt(1).setIcon(icon2);
        itemsGraphics.elementAt(2).setIcon(icon3);
        itemsGraphics.elementAt(3).setIcon(icon3);
        shopItems.forEach(item -> item.setBackground(Constants.windowBgColor));
        shopItems.elementAt(0).setText("Statek Alpha");
        shopItems.elementAt(1).setText("Statek Beta");
        shopItems.elementAt(2).setText("Dodatkowe 1. zycie");
        shopItems.elementAt(3).setText("Dodatkowe 2. zycie");

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
        settings.insets = new Insets(0, 0, 20, 0);
        add(textPanel, settings);

        settings.gridy = 1;
        settings.insets = new Insets(10, 50, 0, 0);
        add(shopPanel, settings);

        settings.gridy = 2;
        settings.gridwidth = 1;
        add(acceptButton, settings);

        settings.gridx = 1;
        settings.insets = new Insets(10, 50, 0, 50);
        add(cancelButton, settings);

        setBackground(Constants.windowBgColor);

        //add(panel);

    }
}