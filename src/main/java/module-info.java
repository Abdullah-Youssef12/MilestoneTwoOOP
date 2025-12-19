// src/main/java/module-info.java
module org.example.milestonetwooop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.management;

    // existing opens/exports …
    opens org.example.milestonetwooop to javafx.fxml;
    exports org.example.milestonetwooop;

    // allow javafx to access your GUI classes
    opens gui to javafx.fxml, javafx.graphics;
    exports gui;
}
