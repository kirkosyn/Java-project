package Windows;

import Constants.*;

import java.awt.*;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

/**
 * Klasa opisująca okno poziomów
 */
public class LevelsWindow extends JPanel
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
     * Etykieta wyboru poziomu
     */
    private JLabel text;
    /**
     * Panel, w którym znajduje się etykieta wyboru poziomu
     */
    private JPanel textPanel;
    /**
 * Panel, w którym znajdują się obszary tekstowe informujące o poziomie
 */
    private JPanel panel;
    /**
 * Panel, w którym znajdują się przyciski RadioButton umożliwiające wybór poziomu
 */
    private JPanel panel2;
    /**
     * Panel, w którym znajdują się komponenty okna
     */
    private JPanel panel_overall;

    /**
     * Wektor obszarów tekstowych informujących o poziomie
     */
    private Vector<JTextField> levels;
    /**
     * Wektor RadioButtonów, umożliwiających wybór poziomu
     */
    private Vector<JRadioButton> selectLevel;
    /**
     * Zgrupowanie RadioButtonów, które uniemożliwia zaznaczenie wielu poziomów
     */
    private ButtonGroup group;
    /**
     * Ustawienia rozłożenia komponentów w oknie
     */
    private GridBagConstraints settings;

    /**
     * Konstruktor klasy LevelsWindow
     */
    public LevelsWindow()
    {
        createComponents();
        settingComponents();
        //configureWindow(Constants.levelsFrameTitle, Parameters.dimLevels);
    }
    /**
     * metoda inicjująca komponenty okna
     */
    private void createComponents()
    {
        acceptButton = new JButton(Constants.acceptButtonTitle);
        cancelButton = new JButton(Constants.cancelButtonTitle);

        text = new JLabel(Constants.levelsLabelText);

        textPanel = new JPanel();
        panel = new JPanel(new GridLayout(5, 1));
        panel2 = new JPanel(new GridLayout(5, 1));
        panel_overall = new JPanel(new GridBagLayout());

        levels = new Vector<JTextField>(5);
        selectLevel = new Vector<JRadioButton>(5);

        group = new ButtonGroup();
    }
    /**
     * metoda ustawiająca parametry i rozłożenie komponentów w oknie
     */
    private void settingComponents()
    {
        Windows.configureFont(text);
        setLayout(new GridBagLayout());
        setBackground(Constants.windowBgColor);
        textPanel.add(text);
        textPanel.setBackground(Constants.windowBgColor);

        String name;
        name = "Poziom";

        for (int i = 0; i < 5; i++)
        {
            levels.add(new JTextField(name + " " + (i + 1)));
            selectLevel.add(new JRadioButton());
        }

        levels.forEach(level -> level.setEditable(false));
        levels.forEach(level -> level.setPreferredSize(new Dimension(100, 30)));
        levels.forEach(level -> panel.add(level));
        selectLevel.forEach(var -> var.setPreferredSize(new Dimension(100, 30)));
        selectLevel.forEach(var -> group.add(var));
        selectLevel.forEach(var -> var.setEnabled(false));
        selectLevel.forEach(var -> panel2.add(var));

        selectLevel.elementAt(0).setSelected(true);
        selectLevel.elementAt(0).setEnabled(true);

        settings = new GridBagConstraints();;
        settings.fill = Constants.layoutConstraints;

        settings.gridwidth = 2;
        settings.insets = new Insets(0, 0, 30, 0);
        add(textPanel, settings);

        settings.gridy = 1;
        settings.gridwidth = 1;
        settings.insets = new Insets(0, 0, 0, 0);
        add(panel, settings);

        settings.gridx = 1;
        settings.insets = new Insets(0, 50, 0, 0);
        add(panel2, settings);

        settings.gridx = 0;
        settings.gridy = 2;
        settings.insets = new Insets(30, 0, 0, 0);
        add(acceptButton, settings);

        settings.gridx = 1;
        settings.insets = new Insets(30, 50, 0, 0);
        add(cancelButton, settings);

        panel.setBackground(Constants.windowBgColor);
        panel2.setBackground(Constants.windowBgColor);
       // panel_overall.setBackground(Constants.windowBgColor);

        //add(panel_overall);
    }
}