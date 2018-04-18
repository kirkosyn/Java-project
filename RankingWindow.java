package Windows;

import Constants.*;
import java.awt.*;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Klasa opisująca okno rankingu najlepszych 10 graczy
 */
public class RankingWindow extends Windows
{
    /**
     *Przycisk akceptacji
     */
    private JButton acceptButton;

    /**
     * Panel, w którym znajduje się przycisk akceptacji
     */
    private JPanel buttonsPanel;
    /**
     * Panel, w którym znajdują się obszary tekstowe informujące o nazwie użytkownika
     */
    private JPanel panel;
    /**
     * Panel, w którym znajdują się obszary tekstowe informujące o ilości punktów danego użytkownika
     */
    private JPanel panel2;
    /**
     * Panel, w którym znajdują się komponenty okna
     */
    private JPanel panel_overall;

    /**
     * Wektor oszarów nazw użytkowników
     */
    private Vector<JTextField> users;
    /**
     * Wektor ilości punktów użytkowników
     */
    private Vector<JTextField> scores;

    /**
     * Konstruktor klasy RankingWindow
     */
    public RankingWindow() 
    {
        createComponents();
        settingComponents();
        configureWindow(Constants.rankFrameTitle,Parameters.dimRank);
    }
    /**
     * metoda inicjująca komponenty okna
     */
    private void createComponents()
    {
        acceptButton = new JButton(Constants.acceptButtonTitle);

        buttonsPanel = new JPanel();
        panel = new JPanel(new GridLayout(10, 1));
        panel2 = new JPanel(new GridLayout(10, 1));
        panel_overall = new JPanel(new GridBagLayout());

        users = new Vector<JTextField>(10);
        scores = new Vector<JTextField>(10);
    }
    /**
    * metoda ustawiająca parametry i rozłożenie komponentów w oknie
    */
    private void settingComponents()
    {
        buttonsPanel.add(acceptButton);
        buttonsPanel.setBackground(Constants.windowBgColor);

        GridBagConstraints settings = new GridBagConstraints();
        settings.fill = Constants.layoutConstraints;

        String name, score;
        name = "brak";
        score = "0";

        for (int i = 0; i < 10; i++)
        {
            users.add(new JTextField(name));
            scores.add(new JTextField(score));
        }

        users.forEach(user -> user.setEditable(false));
        users.forEach(user -> user.setPreferredSize(new Dimension(100, 30)));
        users.forEach(user -> panel.add(user));
        scores.forEach(var -> var.setEditable(false));
        scores.forEach(var -> var.setPreferredSize(new Dimension(100, 30)));
        scores.forEach(var -> panel2.add(var));

        panel_overall.add(panel, settings);

        settings.gridx = 1;
        settings.insets = new Insets(0, 50, 0, 0);
        panel_overall.add(panel2, settings);

        settings.gridx = 0;
        settings.gridy = 1;
        settings.gridwidth = 2;
        settings.insets = new Insets(30, 0, 0, 0);
        panel_overall.add(buttonsPanel, settings);

        panel.setBackground(Constants.windowBgColor);
        panel2.setBackground(Constants.windowBgColor);
        panel_overall.setBackground(Constants.windowBgColor);

        add(panel_overall);
    }
}