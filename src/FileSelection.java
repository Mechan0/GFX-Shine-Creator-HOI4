import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class FileSelection extends JPanel implements ActionListener {
	static private final String newline = "\n";
	JButton openButtonSourceFile, openButtonDestinationFile, writeButton;
	JLabel sourceFileName, destinationFileName;
	File currentSourceFile, currentDestinationFile;
	JTextArea log;
	JFileChooser fc;
	OutputCompiler outputCompiler;
	SavedFileHandler savedFileHandler;

	public FileSelection() {
		super(new BorderLayout());
		outputCompiler = new OutputCompiler();
		savedFileHandler = new SavedFileHandler();
		currentSourceFile = savedFileHandler.getSourceFile();
		currentDestinationFile = savedFileHandler.getDestinationFile();
		// log for feedback to user
		log = new JTextArea(5, 20);
		log.setMargin(new Insets(5, 5, 5, 5));
		log.setEditable(false);
		JScrollPane logScrollPane = new JScrollPane(log);

		fc = new JFileChooser();
		File workingDirectory = new File(System.getProperty("user.dir"));
		fc.setCurrentDirectory(workingDirectory);
		// Create buttons.
		openButtonSourceFile = new JButton("Open File", createImageIcon("images/Open16.gif"));
		openButtonSourceFile.addActionListener(this);

		openButtonDestinationFile = new JButton("Open File", createImageIcon("images/Save16.gif"));
		openButtonDestinationFile.addActionListener(this);

		writeButton = new JButton("Write");
		writeButton.addActionListener(this);
		// For layout purposes, putting each button/text area into a different
		// area
		sourceFileName = new JLabel("No File Selected");
		destinationFileName = new JLabel("No File Selected");
		JPanel mainPanel = new JPanel(new BorderLayout());
		JPanel sourceFilePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		if (currentSourceFile != null && currentSourceFile.exists()) {
			sourceFileName.setText(currentSourceFile.getName());
		}

		JScrollPane SourceFileNamePane = new JScrollPane(sourceFileName);
		SourceFileNamePane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		Dimension d = SourceFileNamePane.getPreferredSize();
		SourceFileNamePane.setPreferredSize(new Dimension(d.width + 100, d.height));
		sourceFilePanel.add(new JLabel("National Focus File: "));
		sourceFilePanel.add(openButtonSourceFile);
		sourceFilePanel.add(SourceFileNamePane);
		mainPanel.add(sourceFilePanel, BorderLayout.NORTH);

		if (currentDestinationFile != null && currentDestinationFile.exists()) {
			destinationFileName.setText(currentDestinationFile.getName());
		}

		JScrollPane destinationFileNamePane = new JScrollPane(destinationFileName);
		destinationFileNamePane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		d = destinationFileNamePane.getPreferredSize();
		destinationFileNamePane.setPreferredSize(new Dimension(d.width + 100, d.height));
		JPanel destinationFilePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		destinationFilePanel.add(new JLabel("Shine File:                  "));
		destinationFilePanel.add(openButtonDestinationFile);
		destinationFilePanel.add(destinationFileNamePane);
		mainPanel.add(destinationFilePanel, BorderLayout.SOUTH);

		// Adding the buttons and the log to this panel.
		add(writeButton, BorderLayout.SOUTH);
		add(mainPanel, BorderLayout.PAGE_START);
		add(logScrollPane, BorderLayout.CENTER);
	}

	/** Returns an ImageIcon, or null if the path was invalid. */
	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = FileSelection.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	/**
	 * Creating the GUI and showing it. Thread safety first!,
	 */
	public static void createAndShowGUI() {
		JFrame frame = new JFrame("Shine Generator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		((JPanel) frame.getContentPane()).setBorder(new EmptyBorder(20, 20, 20, 20));

		frame.add(new FileSelection());

		// Display the window.
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setResizable(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// button action handlers
		if (e.getSource() == openButtonSourceFile) {
			int returnVal = fc.showOpenDialog(FileSelection.this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				log.append("Opening: " + file.getName() + "." + newline);
				currentSourceFile = file;
				sourceFileName.setText(currentSourceFile.getName());
			} else {
				log.append("National Focus interface selection cancelled." + newline);
			}
			log.setCaretPosition(log.getDocument().getLength());
		} else if (e.getSource() == openButtonDestinationFile) {
			int returnVal = fc.showSaveDialog(FileSelection.this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				log.append("Opening: " + file.getName() + "." + newline);
				currentDestinationFile = file;
				destinationFileName.setText(currentDestinationFile.getName());
			} else {
				log.append("Shine File Selection Cancelled." + newline);
			}
			log.setCaretPosition(log.getDocument().getLength());
		} else if (e.getSource() == writeButton) {
			if (currentSourceFile != null && currentDestinationFile != null && currentSourceFile.exists()
					&& currentDestinationFile.exists()) {
				try {
					PrintWriter pw = new PrintWriter(currentDestinationFile);
					String compiledOutputString = outputCompiler.getAndCompile(currentSourceFile);
					pw.print(compiledOutputString);
					savedFileHandler.setDestinationFile(currentDestinationFile);
					savedFileHandler.setSourceFile(currentSourceFile);
					savedFileHandler.saveFilePaths();
					log.append("Wrote Shine to file " + currentDestinationFile.getName() + "\n");
					pw.close();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			} else {
				log.append("Error Writing Files: Both Files must be selected and exist.");
			}
		}
	}
}
