module org.openjfx.sfaxbest {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires jbcrypt;
    requires jakarta.mail;
    requires javafx.media;
    requires java.datatransfer;
    requires org.openjfx.sfaxbest;
    requires javafx.graphics;


    opens entities to org.hibernate.orm.core, jakarta.persistence,javafx.base;
    opens org.openjfx.sfaxbest to javafx.fxml;

    exports org.openjfx.sfaxbest;
}