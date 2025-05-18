package ByteBuilders;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;

public class App {
    public static void main(String[] args) {
        try {
            // Ruta relativa al archivo HTML en recursos
            File htmlFile = new File("src/main/webapp/Login.html");

            if (htmlFile.exists()) {
                Desktop.getDesktop().browse(htmlFile.toURI());
                System.out.println("Abriendo Login.html...");
            } else {
                System.out.println("Login.html no encontrado.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
