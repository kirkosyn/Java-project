
import javafx.util.Pair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Klasa zajmująca się obsługą wiadomości wysyłanych z serwera
 */
public final class ServerCommands {

    /**
     * aktualnie wybrany poziom
     */
    private static int selected_level = 1;
    /**
     * ilość klientów
     */
    private static int clientNumber = 0;
    /**
     * zmienna informująca o tym, czy serwer akceputje
     * przychodzące połączenia
     */
    private static boolean acceptingClients = true;

    /**
     * statyczne wywołanie funkcji
     * odpowiedź serwera - aktualny wybrany poziom
     */

    static {
        try {
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            DocumentBuilder b = f.newDocumentBuilder();
            Document doc = b.parse("ConfigFiles\\levels.xml");
            doc.getDocumentElement().normalize();
            selected_level = Integer.parseInt(doc.getElementsByTagName("selected_level").item(0).getTextContent());
            System.out.println("Selected level " + selected_level);
        } catch (IOException e) {
            System.out.println("File port.txt couldn't be loaded.");
            System.err.println(e);
        }
        catch (Exception er)
        {
            System.out.println(er);
        }

    }

    /**
     * metoda obsługująca polecenia od klienta
     * @param command polecenie
     * @return wiadomość od serwera
     */
    public static String serverAction(String command) {
        String serverCommand = command;
        String originalCommand = command;
        System.out.println(command);

        if(command.contains("ChangeShip:")){
            originalCommand=command;
            serverCommand="ChangeShip";
        }
        if(command.contains("ChangeName:")){
            originalCommand=command;
            serverCommand="ChangeName";
        }
        if(command.contains("ChangeScore:")){
            originalCommand=command;
            serverCommand="ChangeScore";
        }
        if(command.contains("SelectLevel:")){
            originalCommand=command;
            serverCommand="SelectLevel";
        }
        if(command.contains("GameScore:")){
            originalCommand=command;
            serverCommand="GameScore";
        }
        if(command.contains("SaveShop:"))
        {
            originalCommand=command;
            serverCommand="SaveShop";
        }
        if(command.contains("SaveBoughtThings:"))
        {
            originalCommand=command;
            serverCommand="SaveBoughtThings";
        }

        String serverMessage;

        switch (serverCommand) {
            case "Login":
                serverMessage = login();
                break;
            case "GetSettings":
                serverMessage = getSettings("ConfigFiles\\config.xml");
                break;
            case "GetShopSettings":
                serverMessage = getSettings("ConfigFiles\\shop.xml");
                break;
            case "GetGameplaySettings":
                serverMessage = getSettings("ConfigFiles\\levels.xml");
                break;
            case "GetHelp":
                serverMessage = getHelp();
                break;
            case "GetHighScores":
                serverMessage = getHighScores();
                break;
            case "GetLevel":
                ArrayList<Integer> list = getLevel(selected_level);
                serverMessage = Integer.toString(list.get(0)) + " " + Integer.toString(list.get(1));
                break;
            case "GameScore":
                String str1[] = originalCommand.split(":");
                String str2[] = str1[1].split(" ");
                serverMessage = updateHighScore(str2[0], Integer.parseInt(str2[1]));
                break;
            case "ChangeShip":
                String str3[] = originalCommand.split(":");
                serverMessage = changeShip(str3[1]);
                break;
            case "ChangeName":
                String str4[] = originalCommand.split(":");
                serverMessage = changeName(str4[1]);
                break;
            case "ChangeScore":
                String str5[] = originalCommand.split(":");
                serverMessage = changeScore(str5[1]);
                break;
            case "SelectLevel":
                String str6[] = originalCommand.split(":");
                serverMessage = selectLevel(str6[1]);
                break;
            case "SaveBoughtThings":
                String str8[] = originalCommand.split(":");
                serverMessage = saveBoughtThings(str8[0], str8[1]);
                break;
            case "SaveShop":
                String str7[] = originalCommand.split(":");
                serverMessage = saveShop(str7[1]);
                break;
            case "Logout":
                serverMessage = logout();
                break;
            case "CloseConnection":
                serverMessage = connectionClosed();
                break;
            default:
                serverMessage = "InvalidCommand";
        }
        return serverMessage;
    }

    /**
     * Metoda zajmująca się logowaniem klientów do serwera
     *
     * @return wiadomość od serwera
     */

    private static String login() {
        String serverMessage;
        if (acceptingClients) {
            serverMessage = "LoggedIn " + clientNumber + "\n";
            clientNumber++;
        } else {
            serverMessage = "ConnectionRejected";
        }
        return serverMessage;
    }

    /**
     * Metoda zajmująca się pobraniem ustawień konfiguracyjnych gry
     *
     * @return pobrane z serwera ustawienia
     */
    private static String getSettings(String filepath) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                sb.append(currentLine);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return sb.toString();
    }

    /**
     * Metoda zajmująca się pobraniem pomocy i zasad gry
     *
     * @return pobrane z serwera zasady
     */
    private static String getHelp() {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader("ConfigFiles\\help.txt"));
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                sb.append(currentLine);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return sb.toString();
    }

    /**
     * Metoda aktualizująca liste wyników
     *
     * @param name   nick gracza
     * @param points liczba zdobytych punktów
     * @return odpowiedź od serwera
     */

    private static String updateHighScore(String name, int points) {

        ArrayList<Pair> highscoreList;
        highscoreList = new ArrayList<>(10);
        highscoreList = loadFromFile(highscoreList);
        String response = "UserScoreRejected";
        for (int i = 0; i < 10; i++) {
            if ((Integer) highscoreList.get(i).getValue() <= points) {
                response = "UserScoreAccepted";
                saveHighscoresToFile(highscoreList, name, points, i);
                break;
            }
        }
        return response;
    }

    /**
     * metoda zapisująca najlepsze wyniki do pliku
     * @param highscoreList lista wyników
     * @param name imię
     * @param points punkty
     * @param which które miejsce
     */
    private static void saveHighscoresToFile(ArrayList<Pair> highscoreList, String name, int points, int which) {
        try {
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            DocumentBuilder b = f.newDocumentBuilder();
            Document doc = b.parse(new File("ConfigFiles\\high_scores.xml"));

            ArrayList<Node> list = createNodes("ConfigFiles\\high_scores.xml");

            String temporary_for_name = "brak";
            int temporary_score = 0;

            for (int j = which; j < 10; j++) {
                temporary_for_name = (String) highscoreList.get(j).getKey();
                temporary_score = (Integer) highscoreList.get(j).getValue();

                highscoreList.set(j, new Pair(name, points));
                list.get(j * 2).setTextContent(name);
                list.get(j * 2 + 1).setTextContent(Integer.toString(points));
                name = temporary_for_name;
                points = temporary_score;
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("ConfigFiles\\high_scores.xml"));
            transformer.transform(source, result);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Metoda zajmująca się pobraniem listy najlepszych wyników
     *
     * @return pobrana z serwera lista najlepszych wyników
     */

    private static String getHighScores() {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader("ConfigFiles\\high_scores.xml"));
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                sb.append(currentLine);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return sb.toString();
    }

    /**
     * Metoda pobierająca parametry danego poziomu gry
     *
     * @param levelNumber numer poziomu
     * @return pobrana lista parametrów
     * - liczby dużych asteroid
     * - liczby małych asteroid
     */

    private static ArrayList<Integer> getLevel(int levelNumber) {
        ArrayList<Integer> list_of_enemies = new ArrayList<>(2);

        try {
            File xmlInputFile = new File("ConfigFiles\\levels.xml");
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            DocumentBuilder b = f.newDocumentBuilder();
            Document doc = b.parse(xmlInputFile);
            doc.getDocumentElement().normalize();


            Node amount_of_small_enemies;
            Node amount_of_big_enemies;

            switch (levelNumber) {
                case 1:
                    amount_of_small_enemies = doc.getElementsByTagName("amountOfSmallEnemies").item(0);
                    amount_of_big_enemies = doc.getElementsByTagName("amountOfBigEnemies").item(0);
                    list_of_enemies.add(Integer.parseInt(amount_of_small_enemies.getTextContent()));
                    list_of_enemies.add(Integer.parseInt(amount_of_big_enemies.getTextContent()));
                    return list_of_enemies;
                case 2:
                    amount_of_small_enemies = doc.getElementsByTagName("amountOfSmallEnemies2").item(0);
                    amount_of_big_enemies = doc.getElementsByTagName("amountOfBigEnemies2").item(0);
                    list_of_enemies.add(Integer.parseInt(amount_of_small_enemies.getTextContent()));
                    list_of_enemies.add(Integer.parseInt(amount_of_big_enemies.getTextContent()));
                    return list_of_enemies;
                case 3:
                    amount_of_small_enemies = doc.getElementsByTagName("amountOfSmallEnemies3").item(0);
                    amount_of_big_enemies = doc.getElementsByTagName("amountOfBigEnemies3").item(0);
                    list_of_enemies.add(Integer.parseInt(amount_of_small_enemies.getTextContent()));
                    list_of_enemies.add(Integer.parseInt(amount_of_big_enemies.getTextContent()));
                    return list_of_enemies;
                case 4:
                    amount_of_small_enemies = doc.getElementsByTagName("amountOfSmallEnemies4").item(0);
                    amount_of_big_enemies = doc.getElementsByTagName("amountOfBigEnemies4").item(0);
                    list_of_enemies.add(Integer.parseInt(amount_of_small_enemies.getTextContent()));
                    list_of_enemies.add(Integer.parseInt(amount_of_big_enemies.getTextContent()));
                    return list_of_enemies;
                case 5:
                    amount_of_small_enemies = doc.getElementsByTagName("amountOfSmallEnemies5").item(0);
                    amount_of_big_enemies = doc.getElementsByTagName("amountOfBigEnemies5").item(0);
                    list_of_enemies.add(Integer.parseInt(amount_of_small_enemies.getTextContent()));
                    list_of_enemies.add(Integer.parseInt(amount_of_big_enemies.getTextContent()));
                    return list_of_enemies;
                default:
                    amount_of_small_enemies = doc.getElementsByTagName("amountOfSmallEnemies").item(0);
                    amount_of_big_enemies = doc.getElementsByTagName("amountOfBigEnemies").item(0);
                    list_of_enemies.add(Integer.parseInt(amount_of_small_enemies.getTextContent()));
                    list_of_enemies.add(Integer.parseInt(amount_of_big_enemies.getTextContent()));
                    return list_of_enemies;
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (Exception err) {
            System.out.println(err);
        }

        return list_of_enemies;
    }

    /**
     * Metoda pobierająca listę najlepszych wyników z pliku
     *
     * @param highscoreList lista wyników
     * @return pobrana lista wyników
     */

    private static ArrayList<Pair> loadFromFile(ArrayList<Pair> highscoreList) {
        try {
            ArrayList<Node> list = createNodes("ConfigFiles\\high_scores.xml");

            for (int i = 0; i < 20; i = i + 2)
                highscoreList.add(new Pair<String, Integer>(list.get(i).getTextContent(),
                        Integer.parseInt(list.get(i + 1).getTextContent())));

            return highscoreList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return highscoreList;
    }

    /**
     * metoda tworząca tablicę obiektów Node w celu zapisu i odczytu danych
     * z pliku
     * @param filepath ścieżka do pliku
     * @return tablice obiektów Node
     */
    private static ArrayList<Node> createNodes(String filepath) {
        ArrayList<Node> list = new ArrayList<>(20);
        try {
            File xmlInputFile = new File(filepath);

            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            DocumentBuilder b = f.newDocumentBuilder();
            Document doc = b.parse(xmlInputFile);
            doc.getDocumentElement().normalize();

            Node firstWinName = doc.getElementsByTagName("firstWinName").item(0);
            list.add(firstWinName);
            Node firstWinScore = doc.getElementsByTagName("firstWinScore").item(0);
            list.add(firstWinScore);
            Node secondWinName = doc.getElementsByTagName("secondWinName").item(0);
            list.add(secondWinName);
            Node secondWinScore = doc.getElementsByTagName("secondWinScore").item(0);
            list.add(secondWinScore);
            Node thirdWinName = doc.getElementsByTagName("thirdWinName").item(0);
            list.add(thirdWinName);
            Node thirdWinScore = doc.getElementsByTagName("thirdWinScore").item(0);
            list.add(thirdWinScore);
            Node fourthWinName = doc.getElementsByTagName("fourthWinName").item(0);
            list.add(fourthWinName);
            Node fourthWinScore = doc.getElementsByTagName("fourthWinScore").item(0);
            list.add(fourthWinScore);
            Node fifthWinName = doc.getElementsByTagName("fifthWinName").item(0);
            list.add(fifthWinName);
            Node fifthWinScore = doc.getElementsByTagName("fifthWinScore").item(0);
            list.add(fifthWinScore);
            Node sixthWinName = doc.getElementsByTagName("sixthWinName").item(0);
            list.add(sixthWinName);
            Node sixthWinScore = doc.getElementsByTagName("sixthWinScore").item(0);
            list.add(sixthWinScore);
            Node seventhWinName = doc.getElementsByTagName("seventhWinName").item(0);
            list.add(seventhWinName);
            Node seventhWinScore = doc.getElementsByTagName("seventhWinScore").item(0);
            list.add(seventhWinScore);
            Node eighthWinName = doc.getElementsByTagName("eighthWinName").item(0);
            list.add(eighthWinName);
            Node eighthWinScore = doc.getElementsByTagName("eighthWinScore").item(0);
            list.add(eighthWinScore);
            Node ninthWinName = doc.getElementsByTagName("ninthWinName").item(0);
            list.add(ninthWinName);
            Node ninthWinScore = doc.getElementsByTagName("ninthWinScore").item(0);
            list.add(ninthWinScore);
            Node tenthWinName = doc.getElementsByTagName("tenthWinName").item(0);
            list.add(tenthWinName);
            Node tenthWinScore = doc.getElementsByTagName("tenthWinScore").item(0);
            list.add(tenthWinScore);


            return list;
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * wylogowanie
     * @return wiadomość serwera
     */
    private static String logout() {
        return "LoggedOut";
    }

    /**
     * zamknięcie połączenia
     * @return wiadomość serwera
     */
    private static String connectionClosed() {
        return "CloseConnectionNow";
    }

    /**
     * metoda zapisująca wybrany statek do serwera
     * @param change wybrany statek
     * @return zmodyfikowany plik konfiguracyjny
     */
    private static String changeShip(String change) {
        try {
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            DocumentBuilder b = f.newDocumentBuilder();
            Document doc = b.parse(new File("ConfigFiles\\levels.xml"));

            Node selected_ship = doc.getElementsByTagName("selected_ship").item(0);
            selected_ship.setTextContent(change);
            System.out.println("Ship changed to " + change);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("ConfigFiles\\levels.xml"));
            transformer.transform(source, result);
        } catch (Exception e) {
            System.out.println("Ship couldn't be changed");
            System.out.println(e);
        }
        return getSettings("ConfigFiles\\levels.xml");
    }

    /**
     * metoda zapisująca nick gracza do serwera
     * @param name ick gracza
     * @return zmodyfikowany plik konfiguracyjny
     */
    private static String changeName(String name) {
        try {
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            DocumentBuilder b = f.newDocumentBuilder();
            Document doc = b.parse(new File("ConfigFiles\\levels.xml"));

            Node user_name_in_game = doc.getElementsByTagName("user_name_in_game").item(0);
            user_name_in_game.setTextContent(name);
            System.out.println("Name changed to " + name);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("ConfigFiles\\levels.xml"));
            transformer.transform(source, result);
        } catch (Exception e) {
            System.out.println("Name couldn't be changed");
            System.out.println(e);
        }
        return getSettings("ConfigFiles\\levels.xml");
    }

    /**
     * metoda zapisująca ilość punktów do serwera
     * @param cost ilość punktów
     * @return zmodyfikowany plik konfiguracyjny
     */
    private static String changeScore(String cost) {
        try {
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            DocumentBuilder b = f.newDocumentBuilder();
            Document doc = b.parse(new File("ConfigFiles\\levels.xml"));

            Node score = doc.getElementsByTagName("score").item(0);
            score.setTextContent(Integer.toString(Integer.parseInt(score.getTextContent()) + Integer.parseInt(cost)));
            System.out.println("Score changed to " + score.getTextContent());

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("ConfigFiles\\levels.xml"));
            transformer.transform(source, result);
        } catch (Exception e) {
            System.out.println("Score couldn't be changed");
            System.out.println(e);
        }
        return getSettings("ConfigFiles\\levels.xml");
    }

    /**
     * metoda zapisująca wybrany poziom do serwera
     * @param level wybrany poziom
     * @return zmodyfikowany plik konfiguracyjny
     */
    private static String selectLevel(String level) {
        try {
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            DocumentBuilder b = f.newDocumentBuilder();
            Document doc = b.parse(new File("ConfigFiles\\levels.xml"));

            Node selected_level = doc.getElementsByTagName("selected_level").item(0);
            selected_level.setTextContent(level);
            System.out.println("Level changed to " + level);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("ConfigFiles\\levels.xml"));
            transformer.transform(source, result);
        } catch (Exception e) {
            System.out.println("Level couldn't be changed");
            System.out.println(e);
        }
        return getSettings("ConfigFiles\\levels.xml");
    }

    /**
     * metoda zapisująca po stronie serwera który
     * produkt został kupiony
     * @param which nr produktu
     * @return zmieniony plik konfiguracyjny
     */
    private static String saveShop(String which)
    {
        try
        {
        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
        DocumentBuilder b = f.newDocumentBuilder();
        Document doc = b.parse(new File("ConfigFiles\\shop.xml"));

        Node firstBought = doc.getElementsByTagName("firstBought").item(0);
        Node secondBought = doc.getElementsByTagName("secondBought").item(0);
        Node thirdBought = doc.getElementsByTagName("thirdBought").item(0);
        Node fourthBought = doc.getElementsByTagName("fourthBought").item(0);

        switch (Integer.parseInt(which)) {
            case 0:
                firstBought.setTextContent(Boolean.toString(true));
                break;
            case 1:
                secondBought.setTextContent(Boolean.toString(true));
                break;
            case 2:
                thirdBought.setTextContent(Boolean.toString(true));
                break;
            case 3:
                fourthBought.setTextContent(Boolean.toString(true));
                break;
            default:
                break;
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("ConfigFiles\\shop.xml"));
        transformer.transform(source, result);
    } catch (Exception err) {
        err.printStackTrace();
    }
    return getSettings("ConfigFiles\\shop.xml");
    }

    /**
     * metoda zapisująca po stronie serwera ilość życia i
     * punktów gracza
     * @param cost punkty
     * @param health życie
     * @return zmieniony plik konfiguracyjny
     */
    private static String saveBoughtThings(String cost, String health)
    {
        try
        {
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            DocumentBuilder b = f.newDocumentBuilder();
            Document doc = b.parse(new File("ConfigFiles\\levels.xml"));

            Node score = doc.getElementsByTagName("score").item(0);
            Node life = doc.getElementsByTagName("life").item(0);
            score.setTextContent(cost);
            life.setTextContent(health);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("ConfigFiles\\levels.xml"));
            transformer.transform(source, result);
        } catch (Exception err) {
            err.printStackTrace();
        }
        return getSettings("ConfigFiles\\levels.xml");
    }
}
