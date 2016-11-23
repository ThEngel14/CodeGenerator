package ui;

import java.awt.Component;
import java.awt.HeadlessException;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

public class SpecifiedFileChooser extends JFileChooser {
	private File lastDir = FileSystemView.getFileSystemView().getHomeDirectory();

	public SpecifiedFileChooser() {
		setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	}

	@Override
	public int showOpenDialog(Component parent) throws HeadlessException {
		setCurrentDirectory(lastDir);
		int ret_value = super.showOpenDialog(parent);

		if (ret_value == JFileChooser.APPROVE_OPTION) {
			lastDir = getSelectedFile();
		}

		return ret_value;
	}
}
