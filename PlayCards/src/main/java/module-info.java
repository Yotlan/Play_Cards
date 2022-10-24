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

    opens fr.playcards to javafx.fxml;
    exports fr.playcards;
    opens fr.playcards.cardgame to javafx.fxml;
    exports fr.playcards.cardgame;
    opens fr.playcards.room to java.rmi;
    exports fr.playcards.room;
}