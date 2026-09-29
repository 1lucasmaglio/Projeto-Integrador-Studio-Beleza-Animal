import javax.swing.SwingUtilities;

import view.TelaPrincipal;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            TelaPrincipal tela = new TelaPrincipal();

            tela.setVisible(true);
        });
    }
}