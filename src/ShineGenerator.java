import java.io.IOException;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class ShineGenerator {

	public static void main(String[] args) throws IOException {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				UIManager.put("swing.boldMetal", Boolean.FALSE);
				FileSelection.createAndShowGUI();
			}
		});

	}
}
