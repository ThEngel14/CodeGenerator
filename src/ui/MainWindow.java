package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import context.Context;

public class MainWindow extends JFrame {
	private Context context;
	private JTextField inputDir;
	private JTextField outputDir;
	private SpecifiedFileChooser inputFChooser;
	private SpecifiedFileChooser outputFChooser;

	public MainWindow() {
		super();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("CodeGenerator");
		setSize(800, 200);
		setLocationRelativeTo(null);

		inputDir = new JTextField();
		outputDir = new JTextField();
		inputFChooser = new SpecifiedFileChooser();
		outputFChooser = new SpecifiedFileChooser();

		buildLayout();
	}

	private void buildLayout() {
		getContentPane().removeAll();

		setLayout(new BorderLayout());

		JLabel descInput = new JLabel("JSON Directory:");
		descInput.setHorizontalAlignment(JLabel.RIGHT);
		descInput.setVerticalAlignment(JLabel.CENTER);
		JLabel descOutput = new JLabel("Source Directory:");
		descOutput.setHorizontalAlignment(JLabel.RIGHT);
		descOutput.setVerticalAlignment(JLabel.CENTER);

		int width = (int) Math.max(descInput.getPreferredSize().getWidth(), descOutput.getPreferredSize().getWidth());
		descInput.setPreferredSize(new Dimension(width, (int) descInput.getPreferredSize().getHeight()));
		descOutput.setPreferredSize(new Dimension(width, (int) descOutput.getPreferredSize().getHeight()));

		Box inputBox = Box.createVerticalBox();
		inputBox.add(createInputComponent(descInput, inputDir, inputFChooser));
		inputBox.add(createInputComponent(descOutput, outputDir, outputFChooser));

		Box generateCodeBox = Box.createHorizontalBox();
		generateCodeBox.add(Box.createHorizontalGlue());
		generateCodeBox.add(createGenerateCodeButton());
		generateCodeBox.add(Box.createHorizontalGlue());

		generateCodeBox.setBorder(BorderFactory.createEmptyBorder(5, 10, 25, 10));

		add(inputBox, BorderLayout.NORTH);
		add(generateCodeBox, BorderLayout.SOUTH);
	}

	private JComponent createBrowseButton(SpecifiedFileChooser fChooser, JTextField field) {
		JButton browse = new JButton("browse");
		browse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int code = fChooser.showOpenDialog(MainWindow.this);
				if (code == SpecifiedFileChooser.APPROVE_OPTION) {
					field.setText(fChooser.getSelectedFile().getAbsolutePath());
				}
			}
		});
		return browse;
	}

	private JComponent createInputComponent(JLabel description, JTextField field, SpecifiedFileChooser fChooser) {
		Box box = Box.createHorizontalBox();
		box.add(description);
		box.add(Box.createHorizontalStrut(5));
		box.add(field);
		box.add(Box.createHorizontalStrut(5));
		box.add(createBrowseButton(fChooser, field));

		box.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		return box;
	}

	private JButton createGenerateCodeButton() {
		JButton generate = new JButton("Generate Code");
		generate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (inputDir.getText().trim().isEmpty() || outputDir.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(MainWindow.this, "You have to enter an JSON and Source directory!",
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				try {
					Context context = new Context(new File(inputDir.getText().trim()),
							new File(outputDir.getText().trim()));
					context.loadClasses();
					context.processClasses();
					context.writeClasses();

					JOptionPane.showMessageDialog(MainWindow.this, "Code generated successfully.", "Information",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(MainWindow.this, "An error occured!\n\n" + e1.getMessage(), "ERROR",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		return generate;
	}

	public static void main(String[] args) {
		MainWindow w = new MainWindow();
		w.setVisible(true);
	}
}
