module com.worldle.worldlejavafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires org.json;

    opens com.worldle.worldlejavafx to javafx.fxml;
    exports com.worldle.worldlejavafx;
    exports com.worldle.worldlejavafx.geography;
    opens com.worldle.worldlejavafx.geography to javafx.fxml;
    exports com.worldle.worldlejavafx.components;
    exports com.worldle.worldlejavafx.data;
    opens com.worldle.worldlejavafx.components to javafx.fxml;
}