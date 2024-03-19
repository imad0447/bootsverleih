import javax.swing.*;
import java.awt.*;

import java.sql.*;

import java.util.ArrayList;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class Bootsverleih extends JFrame {
    private CardLayout cardLayout = new CardLayout();
    private JPanel contentPanel = new JPanel(cardLayout);
    private JPanel SeitenPanel = new JPanel();
    private JPanel SeitenButtonObenPanel = new JPanel();
    private JPanel SeitenButtonUntenPanel = new JPanel();
    private Connection connection;
    private JScrollPane kundenScrollPane;
    private JScrollPane booteScrollPane;
    private JScrollPane buchungenScrollPane;
    private JPanel kundenPanel = new JPanel();
    private JPanel bootePanel = new JPanel();
    private JPanel buchungenPanel = new JPanel();
    private JPanel buchungenDetailPanel = new JPanel();
    private JPanel kundenDetailPanel = new JPanel();
    private JPanel booteDetailPanel = new JPanel();
    private JPanel hinzufügenPanel = new JPanel();
    private JLabel kundenNrLabel, anredeLabel, vornameLabel, nachnameLabel, strasseLabel, plzLabel, wohnortLabel, geburtsdatumLabel;
    private JLabel bootNrLabel, typLabel, farbeLabel, kategorieLabel, maxPersonenLabel, baujahrLabel;
    private JLabel buchungNrLabel, bKundenNrLabel, bBootNrLabel, ausleihdatumLabel, banredeLabel, bvornameLabel, bnachnameLabel, bstrasseLabel, bplzLabel, bwohnortLabel, bgeburtsdatumLabel, btypLabel, bfarbeLabel, bkategorieLabel, bmaxPersonenLabel, bbaujahrLabel;
    private JTextField kundenSearchField; 
    private JTextField booteSearchField; 
    private JTextField buchungenSearchField; 
    private JComboBox<String> anredeComboBox;
    private JComboBox<String> typComboBox;
    private JComboBox<String> catComboBox;
    private JComboBox<String> namenComboBox;
    private JComboBox<String> booteComboBox;
    private JTextField firstNameField, lastNameField, streetField, zipField, cityField;
    private JTextField farbeField, maxPersonenField, baujahrField;
    private JSpinner birthDatePicker;

    public Bootsverleih() {
        setTitle("Bootsverleih");

        SeitenButtonObenPanel.setLayout(new BoxLayout(SeitenButtonObenPanel, BoxLayout.Y_AXIS));
        SeitenButtonUntenPanel.setLayout(new BoxLayout(SeitenButtonUntenPanel, BoxLayout.Y_AXIS));

        SeitenButtonObenPanel.setPreferredSize(new Dimension(250, 460));
        SeitenButtonUntenPanel.setPreferredSize(new Dimension(250, 100));
        SeitenPanel.setPreferredSize(new Dimension(250, 600));

        JButton buchungButton = new JButton("Buchungen");
        JButton kundenButton = new JButton("Kunden");
        JButton booteButton = new JButton("Boote");
        JButton hinzufügenButton = new JButton("Hinzufügen");
        JButton beendenButton = new JButton("Beenden");

        buchungButton.setMaximumSize(new Dimension(200, 50));
        kundenButton.setMaximumSize(new Dimension(200, 50));
        booteButton.setMaximumSize(new Dimension(200, 50));
        hinzufügenButton.setMaximumSize(new Dimension(200, 50));
        beendenButton.setMaximumSize(new Dimension(200, 50));

        buchungButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        kundenButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        booteButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        hinzufügenButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        beendenButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        buchungButton.addActionListener(e -> cardLayout.show(contentPanel, "Buchungen"));
        kundenButton.addActionListener(e -> cardLayout.show(contentPanel, "Kunden"));
        booteButton.addActionListener(e -> cardLayout.show(contentPanel, "Boote"));
        hinzufügenButton.addActionListener(e -> cardLayout.show(contentPanel, "Hinzufügen"));
        beendenButton.addActionListener(e -> System.exit(0));

        kundenSearchField = new JTextField(20); 
        kundenSearchField.setMaximumSize(new Dimension(700, 40));
        kundenSearchField.setAlignmentX(Component.LEFT_ALIGNMENT);
        kundenSearchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterKundenButtons(kundenSearchField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterKundenButtons(kundenSearchField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterKundenButtons(kundenSearchField.getText());
            }
        });
        booteSearchField = new JTextField(20);
        booteSearchField.setMaximumSize(new Dimension(700, 40));
        booteSearchField.setAlignmentX(Component.LEFT_ALIGNMENT);
        booteSearchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterBooteButtons(booteSearchField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
               filterBooteButtons(booteSearchField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
               filterBooteButtons(booteSearchField.getText());
            }
        });
        buchungenSearchField = new JTextField(20); 
        buchungenSearchField.setMaximumSize(new Dimension(700, 40));
        buchungenSearchField.setAlignmentX(Component.LEFT_ALIGNMENT);
        buchungenSearchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterBuchungenButtons(buchungenSearchField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
               filterBuchungenButtons(buchungenSearchField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
               filterBuchungenButtons(buchungenSearchField.getText());
            }
        });

        SeitenButtonObenPanel.add(buchungButton);
        SeitenButtonObenPanel.add(kundenButton);
        SeitenButtonObenPanel.add(booteButton);
        SeitenButtonUntenPanel.add(hinzufügenButton);
        SeitenButtonUntenPanel.add(beendenButton);

        SeitenPanel.add(SeitenButtonObenPanel, BorderLayout.NORTH);
        SeitenPanel.add(SeitenButtonUntenPanel, BorderLayout.SOUTH);
      
        buchungenScrollPane = new JScrollPane(buchungenPanel);
        contentPanel.add(buchungenScrollPane, "Buchungen");
        contentPanel.add(buchungenDetailPanel, "Buchungsdetails");
        kundenScrollPane = new JScrollPane(kundenPanel);
        contentPanel.add(kundenScrollPane, "Kunden");
        contentPanel.add(kundenDetailPanel, "Kundendetails");
        booteScrollPane = new JScrollPane(bootePanel);
        contentPanel.add(booteScrollPane, "Boote");
        contentPanel.add(booteDetailPanel, "Bootsdetails");
        
        contentPanel.add(hinzufügenPanel, "Hinzufügen");
        
        connectToDatabase();

        createKundenPanel();
        createBootePanel();
        createBuchungenPanel();

        buchungenDetailPanel.setLayout(new BoxLayout(buchungenDetailPanel, BoxLayout.Y_AXIS));
        kundenDetailPanel.setLayout(new BoxLayout(kundenDetailPanel, BoxLayout.Y_AXIS));
        booteDetailPanel.setLayout(new BoxLayout(booteDetailPanel, BoxLayout.Y_AXIS));
        buchungenDetailPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        kundenDetailPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        booteDetailPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        JButton neuerKundeButton = new JButton("Neuer Kunde");
        neuerKundeButton.addActionListener(e -> cardLayout.show(contentPanel, "Neuer Kunde"));
        neuerKundeButton.setPreferredSize(new Dimension(720, 50)); 
        neuerKundeButton.setMinimumSize(new Dimension(720, 50));
        neuerKundeButton.setMaximumSize(new Dimension(720, 50));

        JLabel hinzuLabel = new JLabel("Hinzufügen");
        hinzuLabel.setFont(new Font("Arial", Font.BOLD, 35));

        hinzufügenPanel.setLayout(new BoxLayout(hinzufügenPanel, BoxLayout.Y_AXIS));
        hinzufügenPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        hinzufügenPanel.add(hinzuLabel, BorderLayout.PAGE_START);
        hinzufügenPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        hinzufügenPanel.add(neuerKundeButton);

        JButton neuesBootButton = new JButton("Neues Boot");
        neuesBootButton.addActionListener(e -> cardLayout.show(contentPanel, "Neues Boot"));
        neuesBootButton.setPreferredSize(new Dimension(720, 50));
        neuesBootButton.setMinimumSize(new Dimension(720, 50));
        neuesBootButton.setMaximumSize(new Dimension(720, 50));
        hinzufügenPanel.add(neuesBootButton);

        JButton neueBuchungButton = new JButton("Neue Buchung");
        neueBuchungButton.addActionListener(e -> cardLayout.show(contentPanel, "Neue Buchung"));
        neueBuchungButton.setPreferredSize(new Dimension(720, 50));
        neueBuchungButton.setMaximumSize(new Dimension(720, 50));
        hinzufügenPanel.add(neueBuchungButton);

        JButton kundenEditButton = new JButton("Bearbeiten");
        kundenEditButton.addActionListener(e -> cardLayout.show(contentPanel, "Hinzufügen"));
        kundenEditButton.setPreferredSize(new Dimension(720, 50));
        kundenEditButton.setMinimumSize(new Dimension(720, 50));
        kundenEditButton.setMaximumSize(new Dimension(720, 50));
        JPanel kundenEditButtonPanel = new JPanel();
        kundenEditButtonPanel.setLayout(new GridLayout(1,0));
        kundenEditButtonPanel.add(kundenEditButton);
        

        JButton backButtonKunde = new JButton("Zurück");
        backButtonKunde.addActionListener(e -> cardLayout.show(contentPanel, "Kunden"));
        backButtonKunde.setPreferredSize(new Dimension(720, 50)); 
        backButtonKunde.setMinimumSize(new Dimension(720, 50));
        backButtonKunde.setMaximumSize(new Dimension(720, 50));
        JPanel backButtonKundePanel = new JPanel(new BorderLayout());
        backButtonKundePanel.add(backButtonKunde, BorderLayout.PAGE_END);

        

        JButton backButtonBoot = new JButton("Zurück");
        backButtonBoot.addActionListener(e -> cardLayout.show(contentPanel, "Boote"));
        backButtonBoot.setPreferredSize(new Dimension(720, 50));  
        backButtonBoot.setMinimumSize(new Dimension(720, 50));
        backButtonBoot.setMaximumSize(new Dimension(720, 50));
        JPanel backButtonBootPanel = new JPanel(new BorderLayout());
        backButtonBootPanel.add(backButtonBoot, BorderLayout.PAGE_END);

        JButton backButtonBuchung = new JButton("Zurück");
        backButtonBuchung.addActionListener(e -> cardLayout.show(contentPanel, "Buchungen"));
        backButtonBuchung.setPreferredSize(new Dimension(720, 50));
        backButtonBuchung.setMinimumSize(new Dimension(720, 50));
        backButtonBuchung.setMaximumSize(new Dimension(720, 50));
        JPanel backButtonBuchungPanel = new JPanel(new BorderLayout());
        backButtonBuchungPanel.add(backButtonBuchung, BorderLayout.PAGE_END);

        kundenNrLabel = new JLabel();
        anredeLabel = new JLabel();
        vornameLabel = new JLabel();
        nachnameLabel = new JLabel();
        strasseLabel = new JLabel();
        plzLabel = new JLabel();
        wohnortLabel = new JLabel();
        geburtsdatumLabel = new JLabel();

        bootNrLabel = new JLabel();
        typLabel = new JLabel();
        farbeLabel = new JLabel();
        kategorieLabel = new JLabel();
        maxPersonenLabel = new JLabel();
        baujahrLabel = new JLabel();

        buchungNrLabel = new JLabel();
        bKundenNrLabel = new JLabel();
        bBootNrLabel = new JLabel();
        ausleihdatumLabel = new JLabel();
        banredeLabel = new JLabel();
        bvornameLabel = new JLabel();
        bnachnameLabel = new JLabel();
        bstrasseLabel = new JLabel();
        bplzLabel = new JLabel();
        bwohnortLabel = new JLabel();
        bgeburtsdatumLabel = new JLabel();
        btypLabel = new JLabel();
        bfarbeLabel = new JLabel();
        bkategorieLabel = new JLabel();
        bmaxPersonenLabel = new JLabel();
        bbaujahrLabel = new JLabel();

        kundenDetailPanel.setLayout(new BoxLayout(kundenDetailPanel, BoxLayout.Y_AXIS));
        kundenDetailPanel.add(createKundenDetailRow("Kundenummer:", kundenNrLabel));
        kundenDetailPanel.add(createKundenDetailRow("Anrede:", anredeLabel));
        kundenDetailPanel.add(createKundenDetailRow("Vorname:", vornameLabel));
        kundenDetailPanel.add(createKundenDetailRow("Nachname:", nachnameLabel));
        kundenDetailPanel.add(createKundenDetailRow("Straße:", strasseLabel));
        kundenDetailPanel.add(createKundenDetailRow("PLZ:", plzLabel));
        kundenDetailPanel.add(createKundenDetailRow("Wohnort:", wohnortLabel));
        kundenDetailPanel.add(createKundenDetailRow("Geburtsdatum:", geburtsdatumLabel));
        kundenDetailPanel.add(Box.createRigidArea(new Dimension(0, 262)));
        kundenDetailPanel.add(kundenEditButtonPanel);
        kundenDetailPanel.add(backButtonKundePanel);

         booteDetailPanel.setLayout(new BoxLayout(booteDetailPanel, BoxLayout.Y_AXIS));
         booteDetailPanel.add(createBooteDetailRow("Bootsnummer:", bootNrLabel));
         booteDetailPanel.add(createBooteDetailRow("Typ:", typLabel));
         booteDetailPanel.add(createBooteDetailRow("Farbe:", farbeLabel));
         booteDetailPanel.add(createBooteDetailRow("Kategorie:", kategorieLabel));
         booteDetailPanel.add(createBooteDetailRow("Maximale anzahl Personen:", maxPersonenLabel));
         booteDetailPanel.add(createBooteDetailRow("Baujahr:", baujahrLabel));
         booteDetailPanel.add(backButtonBootPanel);

         buchungenDetailPanel.setLayout(new BoxLayout(buchungenDetailPanel, BoxLayout.Y_AXIS));
         buchungenDetailPanel.add(createBuchungenDetailRow("Buchungsnummer:", buchungNrLabel));
         buchungenDetailPanel.add(createBuchungenDetailRow("Ausleihdatum:", ausleihdatumLabel));
         buchungenDetailPanel.add(createBuchungenDetailRow("Kundenummer:", bKundenNrLabel));
         buchungenDetailPanel.add(createBuchungenDetailRow("Anrede:", banredeLabel));
         buchungenDetailPanel.add(createBuchungenDetailRow("Vorname:", bvornameLabel));
         buchungenDetailPanel.add(createBuchungenDetailRow("Nachname:", bnachnameLabel));
         buchungenDetailPanel.add(createBuchungenDetailRow("Straße:", bstrasseLabel));
         buchungenDetailPanel.add(createBuchungenDetailRow("PLZ:", bplzLabel));  
         buchungenDetailPanel.add(createBuchungenDetailRow("Wohnort:", bwohnortLabel));  
         buchungenDetailPanel.add(createBuchungenDetailRow("Geburtsdatum:", bgeburtsdatumLabel));
         buchungenDetailPanel.add(createBuchungenDetailRow("Bootsnummer:", bBootNrLabel));
         buchungenDetailPanel.add(createBuchungenDetailRow("Bootstyp:", btypLabel));
         buchungenDetailPanel.add(createBuchungenDetailRow("Farbe:", bfarbeLabel));
         buchungenDetailPanel.add(createBuchungenDetailRow("Bootskategorie:", bkategorieLabel));
         buchungenDetailPanel.add(createBuchungenDetailRow("Maximale anzahl Personen:", bmaxPersonenLabel));
         buchungenDetailPanel.add(createBuchungenDetailRow("Baujahr:", bbaujahrLabel));
         buchungenDetailPanel.add(backButtonBuchungPanel);

        JLabel kundenLabel = new JLabel("Kunden");
        kundenLabel.setFont(new Font("Arial", Font.BOLD, 35));

        kundenPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        kundenPanel.add(kundenLabel);
        kundenPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        kundenPanel.add(kundenSearchField);
        kundenPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        createKundenButtons();

        JLabel buchungenLabel = new JLabel("Buchungen");
        buchungenLabel.setFont(new Font("Arial", Font.BOLD, 35));

        buchungenPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buchungenPanel.add(buchungenLabel);
        buchungenPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        buchungenPanel.add(buchungenSearchField);
        buchungenPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        createBuchungenButtons();

        JLabel booteLabel = new JLabel("Boote");
        booteLabel.setFont(new Font("Arial", Font.BOLD, 35));

        bootePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        bootePanel.add(booteLabel);
        bootePanel.add(Box.createRigidArea(new Dimension(0, 25)));
        bootePanel.add(booteSearchField);
        bootePanel.add(Box.createRigidArea(new Dimension(0, 15)));
        createBooteButtons();

        getContentPane().add(contentPanel, BorderLayout.CENTER);
        getContentPane().add(SeitenPanel, BorderLayout.WEST);

        createKundeHinzufügenPanel();
        createKundeHinzugefügtPanel();
        createBooteHinzufügenPanel();
        createBootHinzugefügtPanel();
        createBuchungenHinzufügenPanel();
        createBuchungHinzugefügtPanel();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void createKundenPanel() {
      kundenPanel.setLayout(new BoxLayout(kundenPanel, BoxLayout.Y_AXIS));

  }
  
  private void createBootePanel() {
      bootePanel.setLayout(new BoxLayout(bootePanel, BoxLayout.Y_AXIS));

  }
  
  private void createBuchungenPanel() {
      buchungenPanel.setLayout(new BoxLayout(buchungenPanel, BoxLayout.Y_AXIS));

  }
  

    private JPanel createKundenDetailRow(String labelText, JLabel dataLabel) {
        JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rowPanel.add(new JLabel(labelText));
        rowPanel.add(dataLabel);
        return rowPanel;
    }

    private JPanel createBuchungenDetailRow(String labelText, JLabel dataLabel) {
      JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
      rowPanel.add(new JLabel(labelText));
      rowPanel.add(dataLabel);
      return rowPanel;
  }

  private JPanel createBooteDetailRow(String labelText, JLabel dataLabel) {
   JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
   rowPanel.add(new JLabel(labelText));
   rowPanel.add(dataLabel);
   return rowPanel;
}

    private void connectToDatabase() {
        try {
            String url = "jdbc:mysql://localhost:3388/bootsverleih";
            String user = "root";
            String password = "";
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void filterKundenButtons(String searchTerm) {
        Component[] components = kundenPanel.getComponents();
        for (Component component : components) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                if (button.getText().toLowerCase().contains(searchTerm.toLowerCase())) {
                    button.setVisible(true);
                } else {
                    button.setVisible(false);
                }
            }
        }
    }
    private void filterBooteButtons(String searchTerm) {
        Component[] components = bootePanel.getComponents();
        for (Component component : components) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                if (button.getText().toLowerCase().contains(searchTerm.toLowerCase())) {
                    button.setVisible(true);
                } else {
                    button.setVisible(false);
                }
            }
        }
    }
    private void filterBuchungenButtons(String searchTerm) {
        Component[] components = buchungenPanel.getComponents();
        for (Component component : components) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                if (button.getText().toLowerCase().contains(searchTerm.toLowerCase())) {
                    button.setVisible(true);
                } else {
                    button.setVisible(false);
                }
            }
        }
    }

    private void createKundeHinzugefügtPanel() {
        JPanel KundeHinzugefügtPanel = new JPanel();
        KundeHinzugefügtPanel.setLayout(new BoxLayout(KundeHinzugefügtPanel, BoxLayout.Y_AXIS));
        
        JLabel kundeHinzugefügtLabel = new JLabel("Der Kunde wurde Erfolgreich Angelegt");

        JButton buchungmitKundeButton = new JButton("Buchung erstellen");
        buchungmitKundeButton.setPreferredSize(new Dimension(720, 50));
        buchungmitKundeButton.setMinimumSize(new Dimension(720, 50));
        buchungmitKundeButton.setMaximumSize(new Dimension(720, 50));
        buchungmitKundeButton.addActionListener(e -> {
            cardLayout.show(contentPanel, "Neue Buchung");
            updateKundenPanel();
        });
        

        JButton kundeFertigButton = new JButton("Fertig");
        kundeFertigButton.setPreferredSize(new Dimension(720, 50));
        kundeFertigButton.setMinimumSize(new Dimension(720, 50));
        kundeFertigButton.setMaximumSize(new Dimension(720, 50));
        kundeFertigButton.addActionListener(e -> {
            cardLayout.show(contentPanel, "Kunden");
            updateKundenPanel();
        });
        KundeHinzugefügtPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        KundeHinzugefügtPanel.add(kundeHinzugefügtLabel);
        KundeHinzugefügtPanel.add(Box.createRigidArea(new Dimension(0, 429)));
        KundeHinzugefügtPanel.add(buchungmitKundeButton);
        KundeHinzugefügtPanel.add(kundeFertigButton);

        contentPanel.add(KundeHinzugefügtPanel, "KundeHinzugefügtPanel");
    }

    private void createBootHinzugefügtPanel() {
        JPanel BootHinzugefügtPanel = new JPanel();
        BootHinzugefügtPanel.setLayout(new BoxLayout(BootHinzugefügtPanel, BoxLayout.Y_AXIS));
        
        JLabel bootHinzugefügtLabel = new JLabel("Das Boot wurde Erfolgreich Angelegt");

        JButton bootFertigButton = new JButton("Fertig");
        bootFertigButton.setPreferredSize(new Dimension(720, 50));
        bootFertigButton.setMinimumSize(new Dimension(720, 50));
        bootFertigButton.setMaximumSize(new Dimension(720, 50));
        bootFertigButton.addActionListener(e -> {
            cardLayout.show(contentPanel, "Boote");
            updateBootePanel();
        });
        BootHinzugefügtPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        BootHinzugefügtPanel.add(bootHinzugefügtLabel);
        BootHinzugefügtPanel.add(Box.createRigidArea(new Dimension(0, 479)));
        BootHinzugefügtPanel.add(bootFertigButton);

        contentPanel.add(BootHinzugefügtPanel, "BootHinzugefügtPanel");
    }

    private void createBuchungHinzugefügtPanel() {
        JPanel BuchungHinzugefügtPanel = new JPanel();
        BuchungHinzugefügtPanel.setLayout(new BoxLayout(BuchungHinzugefügtPanel, BoxLayout.Y_AXIS));
        
        JLabel buchungHinzugefügtLabel = new JLabel("Die Buchung wurde Erfolgreich Angelegt");

        JButton buchungFertigButton = new JButton("Fertig");
        buchungFertigButton.setPreferredSize(new Dimension(720, 50));
        buchungFertigButton.setMinimumSize(new Dimension(720, 50));
        buchungFertigButton.setMaximumSize(new Dimension(720, 50));
        buchungFertigButton.addActionListener(e -> {
            cardLayout.show(contentPanel, "Kunden");
            updateKundenPanel();
        });
        BuchungHinzugefügtPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        BuchungHinzugefügtPanel.add(buchungHinzugefügtLabel);
        BuchungHinzugefügtPanel.add(Box.createRigidArea(new Dimension(0, 479)));
        BuchungHinzugefügtPanel.add(buchungFertigButton);

        contentPanel.add(BuchungHinzugefügtPanel, "BuchungHinzugefügtPanel");
    }

    private void createKundeHinzufügenPanel() {
        JPanel kundeHinzufügenPanel = new JPanel();
        kundeHinzufügenPanel.setLayout(new BoxLayout(kundeHinzufügenPanel, BoxLayout.Y_AXIS));

        JLabel salutationLabel = new JLabel("Anrede:");
        salutationLabel.setPreferredSize(new Dimension(100, 25));
        anredeComboBox = new JComboBox<>(new String[]{"Herr", "Frau", "Andere"});
        kundeHinzufügenPanel.add(createRowPanel(salutationLabel, anredeComboBox));

        JLabel firstNameLabel = new JLabel("Vorname:");
        firstNameLabel.setPreferredSize(new Dimension(100, 25));
        firstNameField = new JTextField();
        firstNameField.setColumns(15);
        kundeHinzufügenPanel.add(createRowPanel(firstNameLabel, firstNameField));

        JLabel lastNameLabel = new JLabel("Nachname:");
        lastNameLabel.setPreferredSize(new Dimension(100, 25));
        lastNameField = new JTextField();
        lastNameField.setColumns(15);
        kundeHinzufügenPanel.add(createRowPanel(lastNameLabel, lastNameField));

        JLabel streetLabel = new JLabel("Straße:");
        streetLabel.setPreferredSize(new Dimension(100, 25));
        streetField = new JTextField();
        streetField.setColumns(15);
        kundeHinzufügenPanel.add(createRowPanel(streetLabel, streetField));

        JLabel zipLabel = new JLabel("PLZ:");
        zipLabel.setPreferredSize(new Dimension(100, 25));
        zipField = new JTextField();
        zipField.setColumns(15);
        kundeHinzufügenPanel.add(createRowPanel(zipLabel, zipField));

        JLabel cityLabel = new JLabel("Wohnort:");
        cityLabel.setPreferredSize(new Dimension(100, 25));
        cityField = new JTextField();
        cityField.setColumns(15);
        kundeHinzufügenPanel.add(createRowPanel(cityLabel, cityField));

        JLabel birthDateLabel = new JLabel("Geburtsdatum:");
        birthDateLabel.setPreferredSize(new Dimension(100, 25));
        SpinnerDateModel model = new SpinnerDateModel();
        birthDatePicker = new JSpinner(model);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(birthDatePicker, "dd/MM/yyyy");
        birthDatePicker.setEditor(editor);
        kundeHinzufügenPanel.add(createRowPanel(birthDateLabel, birthDatePicker));

        JButton addButton = new JButton("Hinzufügen");
        addButton.setPreferredSize(new Dimension(720, 50)); 
        addButton.setMinimumSize(new Dimension(720, 50));
        addButton.setMaximumSize(new Dimension(720, 50));
        addButton.addActionListener(e -> {
            addCustomerToDatabase();
            updateKundenPanel();
        });


        JPanel addButtonPanel = new JPanel(new BorderLayout());
        addButtonPanel.add(addButton, BorderLayout.PAGE_END);
        kundeHinzufügenPanel.add(addButtonPanel);
        
        contentPanel.add(kundeHinzufügenPanel, "Neuer Kunde");
    }

    private void createBooteHinzufügenPanel() {
      JPanel bootHinzufügenPanel = new JPanel();
      bootHinzufügenPanel.setLayout(new BoxLayout(bootHinzufügenPanel, BoxLayout.Y_AXIS));
      
      JLabel typLabel = new JLabel("Typ:");
      typLabel.setPreferredSize(new Dimension(100, 25));
      typComboBox = new JComboBox<>(new String[]{"Kanu", "Kajak"});
      bootHinzufügenPanel.add(createRowPanel(typLabel, typComboBox));

      JLabel farbeLabel = new JLabel("Farbe:");
      farbeLabel.setPreferredSize(new Dimension(100, 25));
      farbeField = new JTextField();
      farbeField.setColumns(15);
      bootHinzufügenPanel.add(createRowPanel(farbeLabel, farbeField));

      JLabel catLabel = new JLabel("Kategorie:");
      catLabel.setPreferredSize(new Dimension(100, 25));
      catComboBox = new JComboBox<>(new String[]{"A", "B", "C", "D"});
      bootHinzufügenPanel.add(createRowPanel(catLabel, catComboBox));

      JLabel maxPersonenLabel = new JLabel("Maximale Personen:");
      maxPersonenLabel.setPreferredSize(new Dimension(100, 25));
      maxPersonenField = new JTextField();
      maxPersonenField.setColumns(15);
      bootHinzufügenPanel.add(createRowPanel(maxPersonenLabel, maxPersonenField));

      JLabel baujahrLabel = new JLabel("Baujahr:");
      baujahrLabel.setPreferredSize(new Dimension(100, 25));
      baujahrField = new JTextField();
      baujahrField.setColumns(15);
      bootHinzufügenPanel.add(createRowPanel(baujahrLabel, baujahrField));

      JButton addButton = new JButton("Hinzufügen");
      addButton.setPreferredSize(new Dimension(720, 50));
      addButton.setMinimumSize(new Dimension(720, 50));
      addButton.setMaximumSize(new Dimension(720, 50));
      addButton.addActionListener(e -> {
          addBootToDatabase();
          updateBootePanel();
      });
      JPanel addButtonPanel = new JPanel(new BorderLayout());
      addButtonPanel.add(addButton, BorderLayout.PAGE_END);
      bootHinzufügenPanel.add(addButtonPanel);

      contentPanel.add(bootHinzufügenPanel, "Neues Boot");
  }

    private void createBuchungenHinzufügenPanel() {
   JPanel buchungHinzufügenPanel = new JPanel();
   buchungHinzufügenPanel.setLayout(new BoxLayout(buchungHinzufügenPanel, BoxLayout.Y_AXIS));

   JLabel bKundenLabel = new JLabel("Kunde:");
   bKundenLabel.setPreferredSize(new Dimension(100, 25));
  namenComboBox = new JComboBox<>();
  updateNameComboBox();

  buchungHinzufügenPanel.add(createRowPanel(bKundenLabel, namenComboBox));

  JLabel bBootLabel = new JLabel("Boot:");
  bBootLabel.setPreferredSize(new Dimension(100, 25));
  booteComboBox = new JComboBox<>();
  updateBooteComboBox();

  buchungHinzufügenPanel.add(createRowPanel(bBootLabel, booteComboBox));

   JLabel ausleihdatumLabel = new JLabel("Ausleihdatum:");
   ausleihdatumLabel.setPreferredSize(new Dimension(100, 25));
   SpinnerDateModel model = new SpinnerDateModel();
   birthDatePicker = new JSpinner(model);
   JSpinner.DateEditor editor = new JSpinner.DateEditor(birthDatePicker, "dd/MM/yyyy");
   birthDatePicker.setEditor(editor);
   buchungHinzufügenPanel.add(createRowPanel(ausleihdatumLabel, birthDatePicker));

   JButton addButton = new JButton("Hinzufügen");
   addButton.setPreferredSize(new Dimension(720, 50));
   addButton.setMinimumSize(new Dimension(720, 50));
   addButton.setMaximumSize(new Dimension(720, 50));
   addButton.addActionListener(e -> {
       addBuchungToDatabase();
       updateBuchungenPanel();
   });
   JPanel addButtonPanel = new JPanel(new BorderLayout());
   addButtonPanel.add(addButton, BorderLayout.PAGE_END);
   buchungHinzufügenPanel.add(addButtonPanel);

   contentPanel.add(buchungHinzufügenPanel, "Neue Buchung");

    }

    private void updateNameComboBox() {

        ArrayList<String> names = getNamesFromDatabase();
        namenComboBox.removeAllItems();
        for (String name : names) {
            namenComboBox.addItem(name);
        }
    }

    private ArrayList<String> getNamesFromDatabase() {
        ArrayList<String> names = new ArrayList<>();
        String query = "SELECT PKundenNr, Vorname, Nachname From T_Kunden";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String name = rs.getString("PKundenNr") + ": " + rs.getString("Vorname") + " " + rs.getString("Nachname"); 
                names.add(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return names;
    }

    private void updateBooteComboBox() {

        ArrayList<String> boote = getBooteFromDatabase();
        booteComboBox.removeAllItems();
        for (String boot : boote) {
            booteComboBox.addItem(boot);
        }
    }

    private ArrayList<String> getBooteFromDatabase() {
        ArrayList<String> boote = new ArrayList<>();
        String query = "SELECT PBootNr, Typ, Kategorie From T_Boote";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String boot = rs.getString("PBootNr") + ": " + rs.getString("Typ") + " " + rs.getString("Kategorie"); 
                boote.add(boot);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return boote;
    }


    private JPanel createRowPanel(JLabel label, JComponent component) {
        JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rowPanel.add(label);
        rowPanel.add(component);
        return rowPanel;
    }

    private void addCustomerToDatabase() {
        String insertQuery = "INSERT INTO T_Kunden (Anrede, Vorname, Nachname, Straße, PLZ, Wohnort, Geburtsdatum) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertQuery)) {
            pstmt.setString(1, (String) anredeComboBox.getSelectedItem());
            pstmt.setString(2, firstNameField.getText());
            pstmt.setString(3, lastNameField.getText());
            pstmt.setString(4, streetField.getText());
            pstmt.setString(5, zipField.getText());
            pstmt.setString(6, cityField.getText());
            pstmt.setDate(7, new java.sql.Date(((java.util.Date) birthDatePicker.getValue()).getTime()));
            pstmt.executeUpdate();
            cardLayout.show(contentPanel, "KundeHinzugefügtPanel");  
              } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Fehler beim Hinzufügen des Kunden: " + ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addBootToDatabase() {
      String insertQuery = "INSERT INTO T_Boote (Typ, Farbe, Kategorie, MaxPersonen, Baujahr) VALUES (?, ?, ?, ?, ?)";
      try (PreparedStatement pstmt = connection.prepareStatement(insertQuery)) {
         pstmt.setString(1, (String) typComboBox.getSelectedItem());
         pstmt.setString(2, farbeField.getText());
          pstmt.setString(3, (String) catComboBox.getSelectedItem());
          pstmt.setString(4, maxPersonenField.getText());
          pstmt.setString(5, baujahrField.getText());
          pstmt.executeUpdate();
          cardLayout.show(contentPanel, "BootHinzugefügtPanel");  
      } catch (SQLException ex) {
          ex.printStackTrace();
          JOptionPane.showMessageDialog(this, "Fehler beim Hinzufügen des Bootes: " + ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
      }
  }

  private void addBuchungToDatabase() {
   String insertQuery = "INSERT INTO T_Buchungen (FKundenNr, FBootNr, Ausleihdatum) VALUES (?, ?, ?)";
   String kNr = (String) namenComboBox.getSelectedItem();
   String fKNr = kNr.substring(0, Math.min(kNr.length(), 4));

   String bNr = (String) booteComboBox.getSelectedItem();
   String fBNr = bNr.substring(0, Math.min(bNr.length(), 3));

   try (PreparedStatement pstmt = connection.prepareStatement(insertQuery)) {
       pstmt.setString(1,fKNr);
       pstmt.setString(2, fBNr);
       pstmt.setDate(3, new java.sql.Date(((java.util.Date) birthDatePicker.getValue()).getTime()));
       pstmt.executeUpdate(); 
       cardLayout.show(contentPanel, "BuchungHinzugefügtPanel");  
   } catch (SQLException ex) {
       ex.printStackTrace();
       JOptionPane.showMessageDialog(this, "Fehler beim Hinzufügen der Buchung: " + ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
   }
}

    private void updateKundenPanel() {
        JLabel kundenLabel = new JLabel("Kunden");
        kundenLabel.setFont(new Font("Arial", Font.BOLD, 35));
        updateNameComboBox();
        kundenPanel.removeAll();
        kundenPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        kundenPanel.add(kundenLabel);
        kundenPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        kundenPanel.add(kundenSearchField);
        kundenPanel.add(Box.createRigidArea(new Dimension(0, 15)));        
        createKundenButtons();
        kundenPanel.revalidate();
        kundenPanel.repaint();
    }

    private void updateBootePanel() {
        JLabel bootLabel = new JLabel("Boote");
        bootLabel.setFont(new Font("Arial", Font.BOLD, 35));
        updateBooteComboBox();
        bootePanel.removeAll();
        bootePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        bootePanel.add(bootLabel);
        bootePanel.add(Box.createRigidArea(new Dimension(0, 25)));
        bootePanel.add(booteSearchField);
        bootePanel.add(Box.createRigidArea(new Dimension(0, 15)));        
        createBooteButtons();
        bootePanel.revalidate();
        bootePanel.repaint();
  }

  private void updateBuchungenPanel() {
    JLabel buchungenLabel = new JLabel("Buchungen");
    buchungenLabel.setFont(new Font("Arial", Font.BOLD, 35));
    updateBooteComboBox();
    buchungenPanel.removeAll();
    buchungenPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    buchungenPanel.add(buchungenLabel);
    buchungenPanel.add(Box.createRigidArea(new Dimension(0, 25)));
    buchungenPanel.add(buchungenSearchField);
    buchungenPanel.add(Box.createRigidArea(new Dimension(0, 15)));      
    
   
   createBuchungenButtons();
   buchungenPanel.revalidate();
   buchungenPanel.repaint();
}

    private void createKundenButtons() {
        String query = "SELECT PKundenNr, Vorname, Nachname FROM T_Kunden";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String kundenNr = rs.getString("PKundenNr");
                JButton button = new JButton(kundenNr + ": " + rs.getString("Vorname") + " " + rs.getString("Nachname"));
                button.setPreferredSize(new Dimension(700, 50));
                button.setMinimumSize(new Dimension(700, 50));
                button.setMaximumSize(new Dimension(700, 50));
                button.addActionListener(e -> updateDetailKundenPanel(kundenNr));
                kundenPanel.add(button);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createBooteButtons() {
      String query = "SELECT PBootNr, Typ, Kategorie FROM T_Boote";
      try (Statement stmt = connection.createStatement();
           ResultSet rs = stmt.executeQuery(query)) {
          while (rs.next()) {
              String bootNr = rs.getString("PBootNr");
              JButton button = new JButton(bootNr + ": " + rs.getString("Typ") + " " + rs.getString("Kategorie"));
              button.setPreferredSize(new Dimension(700, 50));
              button.setMinimumSize(new Dimension(700, 50));
              button.setMaximumSize(new Dimension(700, 50));
              button.addActionListener(e -> updateDetailBootePanel(bootNr));
              bootePanel.add(button);
          }
      } catch (SQLException e) {
          e.printStackTrace();
      }
  }

  private void createBuchungenButtons() {
   String query = "SELECT PBuchungNr, FKundenNr, FBootNr, Ausleihdatum, Vorname, Nachname FROM T_Buchungen INNER JOIN T_Kunden ON T_Buchungen.FKundenNr = T_Kunden.PKundenNr;";
   try (Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query)) {
       while (rs.next()) {
           String buchungNr = rs.getString("PBuchungNr");
           JButton button = new JButton(buchungNr + ": " + rs.getString("Vorname") + " " + rs.getString("Nachname") + " " + rs.getString("Ausleihdatum"));
           button.setPreferredSize(new Dimension(700, 50));
           button.setMinimumSize(new Dimension(700, 50));
           button.setMaximumSize(new Dimension(700, 50));
           button.addActionListener(e -> updateDetailBuchungenPanel(buchungNr));
           buchungenPanel.add(button);
       }
   } catch (SQLException e) {
       e.printStackTrace();
   }
}

    private void updateDetailKundenPanel(String kundenNr) {
        String detailQuery = "SELECT Anrede, Vorname, Nachname, Straße, PLZ, Wohnort, Geburtsdatum FROM T_Kunden WHERE PKundenNr = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(detailQuery)) {
            pstmt.setString(1, kundenNr);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    kundenNrLabel.setText(kundenNr);
                    anredeLabel.setText(rs.getString("Anrede"));
                    vornameLabel.setText(rs.getString("Vorname"));
                    nachnameLabel.setText(rs.getString("Nachname"));
                    strasseLabel.setText(rs.getString("Straße"));
                    plzLabel.setText(rs.getString("PLZ"));
                    wohnortLabel.setText(rs.getString("Wohnort"));
                    geburtsdatumLabel.setText(rs.getString("Geburtsdatum"));
                    cardLayout.show(contentPanel, "Kundendetails");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateDetailBootePanel(String bootNr) {
      String detailQuery = "SELECT Typ, Farbe, Kategorie, MaxPersonen, Baujahr FROM T_Boote WHERE PBootNr = ?";
      try (PreparedStatement pstmt = connection.prepareStatement(detailQuery)) {
          pstmt.setString(1, bootNr);
          try (ResultSet rs = pstmt.executeQuery()) {
              if (rs.next()) {
                  bootNrLabel.setText(bootNr);
                  typLabel.setText(rs.getString("Typ"));
                  farbeLabel.setText(rs.getString("Farbe"));
                  kategorieLabel.setText(rs.getString("Kategorie"));
                  maxPersonenLabel.setText(rs.getString("MaxPersonen"));
                  baujahrLabel.setText(rs.getString("Baujahr"));
                  cardLayout.show(contentPanel, "Bootsdetails");
                  System.out.println(rs.getString("Typ"));
              }
          }
      } catch (SQLException e) {
          e.printStackTrace();
      }
  }

  private void updateDetailBuchungenPanel(String buchungNr) {
   String detailQuery = "SELECT PBuchungNr, Ausleihdatum, FKundenNr, Anrede, Vorname, Nachname, Straße, PLZ, Wohnort, Geburtsdatum, FBootNr, Typ, Farbe, Kategorie, Maxpersonen, Baujahr FROM T_Buchungen INNER JOIN T_Kunden ON T_Buchungen.FKundenNr = T_Kunden.PKundenNr INNER JOIN T_Boote ON T_Buchungen.FBootNr = T_Boote.PBootNr WHERE PBuchungNr = ?";
   try (PreparedStatement pstmt = connection.prepareStatement(detailQuery)) {
       pstmt.setString(1, buchungNr);
       try (ResultSet rs = pstmt.executeQuery()) {
           if (rs.next()) {
               buchungNrLabel.setText(buchungNr);
               ausleihdatumLabel.setText(rs.getString("Ausleihdatum"));
               bKundenNrLabel.setText(rs.getString("FKundenNr"));
               banredeLabel.setText(rs.getString("Anrede"));
               bvornameLabel.setText(rs.getString("Vorname"));
               bnachnameLabel.setText(rs.getString("Nachname"));
               bstrasseLabel.setText(rs.getString("Straße"));
               bplzLabel.setText(rs.getString("PLZ"));
               bwohnortLabel.setText(rs.getString("Wohnort"));
               bgeburtsdatumLabel.setText(rs.getString("Geburtsdatum"));
               bBootNrLabel.setText(rs.getString("FBootNr"));
               btypLabel.setText(rs.getString("Typ"));
               bfarbeLabel.setText(rs.getString("Farbe"));
               bkategorieLabel.setText(rs.getString("Kategorie"));
               bmaxPersonenLabel.setText(rs.getString("MaxPersonen"));
               bbaujahrLabel.setText(rs.getString("Baujahr"));

               cardLayout.show(contentPanel, "Buchungsdetails");
           }
       }
   } catch (SQLException e) {
       e.printStackTrace();
   }
}
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Bootsverleih frame = new Bootsverleih();
            frame.setSize(1000, 600);
            frame.setResizable(false);
            frame.setVisible(true);
        });
    }
}
