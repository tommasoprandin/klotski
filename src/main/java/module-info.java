module it.unipd {
    requires javafx.controls;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    exports it.unipd;
    opens it.unipd.models;
}
