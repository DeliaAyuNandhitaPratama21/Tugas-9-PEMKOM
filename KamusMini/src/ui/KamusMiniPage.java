/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import object.FontSetting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class KamusMiniPage extends JFrame {

    private ResourceBundle bundle;
    private Locale currentLocale;

    private JLabel titleLabel;
    private JLabel sourceLangLabel, targetLangLabel, wordLabel, resultLabel;
    private JComboBox<String> sourceLangCombo, targetLangCombo;
    private JTextField wordField;
    private JButton translateButton;
    private JTextArea resultArea;
    private JComboBox<String> languageCombo;

    private Map<String, Map<String, String>> dictionary;

    public KamusMiniPage() {
        // Default Locale English US agar bahasa default English
        currentLocale = new Locale("en", "US");
        bundle = ResourceBundle.getBundle("localization.bundle", currentLocale);

        initDictionary();
        initComponents();
        applyFont();
        updateTexts();
    }

    private void applyFont() {
        try {
            FontSetting fs = new FontSetting("Code2000", Font.BOLD, 14);
            fs.selectContainer(getContentPane());
        } catch (Exception e) {
            System.err.println("Font error: " + e.getMessage());
        }
    }

    private void initDictionary() {
        dictionary = new HashMap<>();

        Map<String, String> enToId = Map.of("hello", "halo", "cat", "kucing", "dog", "anjing", "food", "makanan", "water", "air");
        Map<String, String> enToEs = Map.of("hello", "hola", "cat", "gato", "dog", "perro", "food", "comida", "water", "agua");
        Map<String, String> enToIt = Map.of("hello", "ciao", "cat", "gatto", "dog", "cane", "food", "cibo", "water", "acqua");
        dictionary.put("English-Indonesian", enToId);
        dictionary.put("English-Spanish", enToEs);
        dictionary.put("English-Italian", enToIt);

        Map<String, String> idToEn = Map.of("halo", "hello", "kucing", "cat", "anjing", "dog", "makanan", "food", "air", "water");
        Map<String, String> esToEn = Map.of("hola", "hello", "gato", "cat", "perro", "dog", "comida", "food", "agua", "water");
        Map<String, String> itToEn = Map.of("ciao", "hello", "gatto", "cat", "cane", "dog", "cibo", "food", "acqua", "water");
        dictionary.put("Indonesian-English", idToEn);
        dictionary.put("Spanish-English", esToEn);
        dictionary.put("Italian-English", itToEn);

        dictionary.put("Indonesian-Spanish", Map.of("halo", "hola", "kucing", "gato", "anjing", "perro", "makanan", "comida", "air", "agua"));
        dictionary.put("Indonesian-Italian", Map.of("halo", "ciao", "kucing", "gatto", "anjing", "cane", "makanan", "cibo", "air", "acqua"));
        dictionary.put("Spanish-Indonesian", Map.of("hola", "halo", "gato", "kucing", "perro", "anjing", "comida", "makanan", "agua", "air"));
        dictionary.put("Spanish-Italian", Map.of("hola", "ciao", "gato", "gatto", "perro", "cane", "comida", "cibo", "agua", "acqua"));
        dictionary.put("Italian-Indonesian", Map.of("ciao", "halo", "gatto", "kucing", "cane", "anjing", "cibo", "makanan", "acqua", "air"));
        dictionary.put("Italian-Spanish", Map.of("ciao", "hola", "gatto", "gato", "cane", "perro", "cibo", "comida", "acqua", "agua"));
    }

    private void initComponents() {
        setTitle(bundle.getString("title"));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        titleLabel = new JLabel("", SwingConstants.CENTER);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 3;
        add(titleLabel, gbc);

        sourceLangLabel = new JLabel();
        gbc.gridy = 1; gbc.gridwidth = 1; gbc.gridx = 0;
        add(sourceLangLabel, gbc);

        sourceLangCombo = new JComboBox<>();
        gbc.gridx = 1; gbc.gridwidth = 2;
        add(sourceLangCombo, gbc);

        targetLangLabel = new JLabel();
        gbc.gridy = 2; gbc.gridx = 0; gbc.gridwidth = 1;
        add(targetLangLabel, gbc);

        targetLangCombo = new JComboBox<>();
        gbc.gridx = 1; gbc.gridwidth = 2;
        add(targetLangCombo, gbc);

        wordLabel = new JLabel();
        gbc.gridy = 3; gbc.gridx = 0;
        add(wordLabel, gbc);

        wordField = new JTextField();
        gbc.gridx = 1; gbc.gridwidth = 2;
        add(wordField, gbc);

        translateButton = new JButton();
        gbc.gridy = 4; gbc.gridx = 0; gbc.gridwidth = 3;
        translateButton.addActionListener(this::translateWord);
        add(translateButton, gbc);

        resultLabel = new JLabel();
        gbc.gridy = 5;
        add(resultLabel, gbc);

        resultArea = new JTextArea(3, 20);
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        gbc.gridy = 6; gbc.fill = GridBagConstraints.BOTH;
        add(new JScrollPane(resultArea), gbc);

        languageCombo = new JComboBox<>(new String[]{"English", "Indonesia", "Español", "Italiano"});
        gbc.gridy = 7; gbc.fill = GridBagConstraints.HORIZONTAL;
        languageCombo.addActionListener(e -> changeLanguage());
        add(languageCombo, gbc);
    }

    private void updateTexts() {
        titleLabel.setText(bundle.getString("title"));
        sourceLangLabel.setText(bundle.getString("label_source"));
        targetLangLabel.setText(bundle.getString("label_target"));
        wordLabel.setText(bundle.getString("label_word"));
        translateButton.setText(bundle.getString("button_translate"));
        resultLabel.setText(bundle.getString("label_result"));

        sourceLangCombo.setModel(new DefaultComboBoxModel<>(new String[]{
                bundle.getString("source_english"),
                bundle.getString("source_indonesian"),
                bundle.getString("source_spanish"),
                bundle.getString("source_italian")
        }));

        targetLangCombo.setModel(new DefaultComboBoxModel<>(new String[]{
                bundle.getString("target_english"),
                bundle.getString("target_indonesian"),
                bundle.getString("target_spanish"),
                bundle.getString("target_italian")
        }));
    }

    private void changeLanguage() {
        String selected = (String) languageCombo.getSelectedItem();
        switch (selected) {
            case "English" -> currentLocale = new Locale("en", "US");
            case "Indonesia" -> currentLocale = new Locale("in", "ID");
            case "Español" -> currentLocale = new Locale("es", "ES");
            case "Italiano" -> currentLocale = new Locale("it", "IT");
        }
        bundle = ResourceBundle.getBundle("localization.bundle", currentLocale);
        updateTexts();
    }

    private void translateWord(ActionEvent e) {
        String srcLang = (String) sourceLangCombo.getSelectedItem();
        String tgtLang = (String) targetLangCombo.getSelectedItem();
        String word = wordField.getText().toLowerCase().trim();

        String key = srcLang + "-" + tgtLang;
        Map<String, String> map = dictionary.getOrDefault(key, Map.of());

        String result = map.getOrDefault(word, "Not found");
        resultArea.setText(result);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            KamusMiniPage frame = new KamusMiniPage();
            frame.setVisible(true);
        });
    }
}


