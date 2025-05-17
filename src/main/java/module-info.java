module pl.naprawy.systemnaprawprojekt {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.sql;
    requires java.persistence;

    opens pl.naprawy to javafx.fxml, org.hibernate.orm.core;
    exports pl.naprawy;
    exports pl.naprawy.model;
    opens pl.naprawy.model to javafx.fxml, org.hibernate.orm.core;
    exports pl.naprawy.controller;
    opens pl.naprawy.controller to javafx.fxml, org.hibernate.orm.core;
    exports pl.naprawy.util;
    opens pl.naprawy.util to javafx.fxml, org.hibernate.orm.core;
    exports pl.naprawy.service;
    opens pl.naprawy.service to javafx.fxml, org.hibernate.orm.core;
}