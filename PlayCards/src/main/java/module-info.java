module fr.playcards {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.rmi;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;

    opens fr.playcards to java.rmi, javafx.fxml;
    exports fr.playcards;

    opens fr.playcards.room to java.rmi;
    exports fr.playcards.room;

    opens fr.playcards.cardgame to javafx.fxml;
    exports fr.playcards.cardgame;

    opens fr.playcards.cardgame.card to javafx.fxml;
    exports fr.playcards.cardgame.card;

    opens fr.playcards.client to java.rmi, javafx.fxml;
    exports fr.playcards.client;

    opens fr.playcards.server to java.rmi, javafx.fxml;
    exports fr.playcards.server;
}