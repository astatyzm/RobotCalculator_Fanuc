package Application;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Const.FanucConstants;
import Descriptions.Descriptions;

public class Builder implements ApplicationBuilder, FanucConstants {
	// specific builder - implementation

	static Logger logger = LoggerFactory.getLogger(Builder.class);

	public static String pathToLoad = "";
	public static File fileIn = null;

	// Maven
	public static String readInputFile() {
		logger.info("ReadInputFile method.");
		File fileInputed = fileInPath.getSelectedFile();
		String readedFile = "";
		try {
			readedFile = FileUtils.readFileToString(fileIn, charset);
			logger.info("File loaded correctly.");
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("ReadInputFile exeption.");
		}

		return readedFile;
	}

	public static List<String> checkConfig(String fileToCheckConfig) {
		List<String> configListAll = new ArrayList<String>();
		List<String> configListCorrect = new ArrayList<String>();

		String validPattern = "CONFIG\\s+:\\s+'(F|N)\\s(U|D)\\s(T|B),\\s\\d,\\s\\d,\\s\\d'";
		Pattern patternConfig = Pattern.compile(validPattern);
		Matcher matcherValidConfig = patternConfig.matcher(fileToCheckConfig);

		String configRegexAll = "CONFIG.+\\d'";
		Pattern configPatternAll = Pattern.compile(configRegexAll);
		Matcher configMatcherAll = configPatternAll.matcher(fileToCheckConfig);

		// all configs;
		while (configMatcherAll.find()) {
			configListAll.add(configMatcherAll.group());
		}

		// only correct configs;
		while (matcherValidConfig.find()) {
			configListCorrect.add(matcherValidConfig.group());
		}
		List<String> wrongConfigs = new ArrayList<String>(configListAll);
		wrongConfigs.removeAll(configListCorrect);
		if (!wrongConfigs.isEmpty()) {
			logger.info("There are {} incorrect CONFIGs.", wrongConfigs.size());
		}
		return wrongConfigs;
	}

	public static List<String> checkSpeed(String fileToCheckSpeed) {
		List<String> speedListAll = new ArrayList<String>();
		List<String> speedListCorrect = new ArrayList<String>();

		// Join speed valid
		String validJoinPattern = "J\\s(PR|P).+]\\s\\b([0-9]|[1-9][0-9]|100)\\b%";
		Pattern patternJoinSpeed = Pattern.compile(validJoinPattern);
		Matcher matcherValidJoinSpeed = patternJoinSpeed.matcher(fileToCheckSpeed);

		// Linear speed valid
		String validLinearPattern = "L\\s(P|PR).+]\\s\\b([0-9]|[1-9][0-9]|[1-9][0-9][0-9]|[0-1][0-9][0-9][0-9]|2[0-4][0-9][0-9]|2500)mm\\b";
		Pattern speedPatternLinear = Pattern.compile(validLinearPattern);
		Matcher matcherValidLinearSpeed = speedPatternLinear.matcher(fileToCheckSpeed);

		// All speeds in file
		String allSpeed = "(J|L)\\s(PR|P).+]\\s\\d+?(mm|%)";
		Pattern patternSpeedAll = Pattern.compile(allSpeed);
		Matcher matcherSpeedAll = patternSpeedAll.matcher(fileToCheckSpeed);

		while (matcherValidJoinSpeed.find()) {
			speedListCorrect.add(matcherValidJoinSpeed.group());
		}
		while (matcherValidLinearSpeed.find()) {
			speedListCorrect.add(matcherValidLinearSpeed.group());
		}

		while (matcherSpeedAll.find()) {
			speedListAll.add(matcherSpeedAll.group());
		}
		List<String> wrongSpeeds = new ArrayList<String>(speedListAll);
		wrongSpeeds.removeAll(speedListCorrect);
		if (!wrongSpeeds.isEmpty()) {
			logger.info("There are {} incorrect Speeds", wrongSpeeds.size());
		}
		return wrongSpeeds;
	}

	public static List<String> chceckZone(String fileToCheckZone) {
		List<String> zoneListAll = new ArrayList<String>();
		List<String> zoneListCorrect = new ArrayList<String>();

		// FINE && CNT(0,100) L || J
		String validZoneFineCNT = "(J|L)\\s.+((FINE)|(CNT(([0-9]|[1-9][0-9]|100)\\b)))";
		Pattern patternZoneFineCNT = Pattern.compile(validZoneFineCNT);
		Matcher matcherZoneFineCNT = patternZoneFineCNT.matcher(fileToCheckZone);

		while (matcherZoneFineCNT.find()) {
			zoneListCorrect.add(matcherZoneFineCNT.group());
		}
		// CD(0,100) L
		String validZoneCD = "L\\s.+((CD(([0-9]|[1-9][0-9]|100)\\b)))";
		Pattern patternZoneCD = Pattern.compile(validZoneCD);
		Matcher matcherZoneCD = patternZoneCD.matcher(fileToCheckZone);
		while (matcherZoneCD.find()) {
			zoneListCorrect.add(matcherZoneCD.group());
		}
		// CR(0,1000) L
		String validZoneCR = "L\\s.+((CR(([0-9]|[1-9][0-9]|[1-9][0-9][0-9]|1000)\\b)))";
		Pattern patternZoneCR = Pattern.compile(validZoneCR);
		Matcher matcherZoneCR = patternZoneCR.matcher(fileToCheckZone);
		while (matcherZoneCR.find()) {
			zoneListCorrect.add(matcherZoneCR.group());
		}
		// all Zones
		String zoneAll = "(J|L)\\s.+(FINE|(CNT|CD|CR)([0-9]|[1-9][0-9]|[1-9][0-9][0-9]|\\d.+)\\b)";
		Pattern patternZoneAll = Pattern.compile(zoneAll);
		Matcher matcherZoneAll = patternZoneAll.matcher(fileToCheckZone);
		while (matcherZoneAll.find()) {
			zoneListAll.add(matcherZoneAll.group());
		}

		List<String> wrongZone = new ArrayList<String>(zoneListAll);
		wrongZone.removeAll(zoneListCorrect);
		if (!wrongZone.isEmpty()) {
			logger.info("There are {} incorrect Zones.", wrongZone.size());
		}
		return wrongZone;
	}

	public static String reversePath(String pathToReverse) {
		String reversedPath = null;

		reversedPath = pathToReverse.replaceAll("(Y = +)", "$1-").replaceAll("(Y = +)--", "$1")
				.replaceAll("(W = +)", "$1-").replaceAll("(W = +)--", "$1").replaceAll("(R = +)", "$1-")
				.replaceAll("(R = +)--", "$1");
		logger.info("Reversed method.");
		return reversedPath;
	}

	// Maven
	public static File saveOutputFile(String fileToSave) {
		String fileName = fileInPath.getSelectedFile().getName();
		File saveFolder = new File(fileInPath.getCurrentDirectory(),
				Descriptions.STRING_NEW_FOLDER_NAME.displayString());
		saveFolder.mkdir();
		File outFile = new File(saveFolder, fileName);
		// System.out.print(outFile.getAbsolutePath());
		try {
			FileUtils.writeStringToFile(outFile, fileToSave, charset, false);
			logger.info("Output file saved successfully.");
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Error durnig save output file.");
		}
		return outFile;
	}

	public JCheckBox buildConfigCheckBox() {
		confiCheckBox.setBounds(10, 100, 250, 25);
		confiCheckBox.setOpaque(false);
		frame.add(confiCheckBox);
		logger.info("BuildConfigCheckBox");
		return confiCheckBox;
	}

	public JCheckBox buildSpeedCheckBox() {
		speedCheckBox.setBounds(10, 125, 250, 25);
		speedCheckBox.setOpaque(false);
		frame.add(speedCheckBox);
		logger.info("BuildSpeedCheckBox");
		return speedCheckBox;
	}

	public JCheckBox buildZoneCheckBox() {
		zoneCheckBox.setBounds(10, 150, 250, 25);
		zoneCheckBox.setOpaque(false);
		frame.add(zoneCheckBox);
		frame.add(zoneCheckBox);
		logger.info("BuildZoneCheckBox");
		return zoneCheckBox;
	}

	public void buildButtonFieldIn() {

		JButton buttonFileIn = new JButton(Descriptions.STRING_PATH_FILE_IN.displayString());
		buttonFileIn.setBounds(10, 10, 150, 25);
		buttonFileIn.setToolTipText(Descriptions.STRING_PATH_FILE_IN_TOOL_TIP.displayString());
		buttonFileIn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				File selectedFileIn = null;
				String loadedFilePath = null;
				fileInPath.setFileFilter(filter);
				fileInPath.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int result = fileInPath.showOpenDialog(frame);

				fileIn = fileInPath.getSelectedFile();
				if (result == JFileChooser.APPROVE_OPTION) {
					selectedFileIn = fileInPath.getSelectedFile();
					if (selectedFileIn.length() != 0) {
						loadedFilePath = selectedFileIn.getAbsolutePath();
						if ((loadedFilePath != null) && (loadedFilePath.endsWith(".ls"))) {
							JOptionPane.showMessageDialog(frame,
									Descriptions.STRING_FILE_LOAD_SUCCESFULLY.displayString());
							fileLoadedPath.setText(loadedFilePath);
							pathToLoad = readInputFile();

						} else {
							JOptionPane.showMessageDialog(frame, Descriptions.STRING_WRONG_EXTENSION.displayString());
							logger.error("Selected file has wrong extension.");
						}
					} else {
						JOptionPane.showMessageDialog(frame, Descriptions.STRING_EMPTY_FILE.displayString());
						logger.error("Selected file is empty.");
					}
				} else if (result == JFileChooser.CANCEL_OPTION) {
					logger.error("No File selected.");
				}
			}
		});
		frame.add(buttonFileIn);
		logger.info("BuildButtonFieldIn");
	}

	public void builFieldLoadedPath() {
		fileLoadedPath.setToolTipText(Descriptions.STRING_FILELOADED_TOOL_TIP.displayString());
		fileLoadedPath.setForeground(Color.BLACK);
		fileLoadedPath.setBackground(Color.WHITE);
		fileLoadedPath.setHorizontalAlignment(SwingConstants.CENTER);
		fileLoadedPath.setEditable(false);
		fileLoadedPath.setBounds(10, 35, 450, 25);
		frame.add(fileLoadedPath);
		logger.info("BuilFieldLoadedPath");
	}

	public void buildButtonExecuteAndReverse() {
		JButton buttonExecuteAndReverse = new JButton(Descriptions.STRING_EXECUT_REVERSE.displayString());
		buttonExecuteAndReverse.setBounds(10, 60, 150, 25);
		buttonExecuteAndReverse.setToolTipText(Descriptions.STRING_EXECUT_REVERSE_TOOL_TIP.displayString());

		try {
			buttonExecuteAndReverse.addActionListener(new ActionListener() {
				@SuppressWarnings("static-access")
				public void actionPerformed(ActionEvent e) {
					String reversedPath = reversePath(pathToLoad);
					List<String> wrongConfigs = new ArrayList<String>();
					List<String> wrongSpeeds = new ArrayList<String>();
					List<String> wrongZones = new ArrayList<String>();

					if (buildConfigCheckBox().isSelected()) {
						wrongConfigs = checkConfig(reversedPath);
						if (!wrongConfigs.isEmpty()) {
							JOptionPane.showMessageDialog(frame,
									Descriptions.STRING_MESSAGE_WRONG_CONG.displayString() + wrongConfigs);
						}
					}

					if (buildSpeedCheckBox().isSelected()) {
						wrongSpeeds = checkSpeed(reversedPath);
						if (!wrongSpeeds.isEmpty()) {
							JOptionPane.showMessageDialog(frame,
									Descriptions.STRING_MESSAGE_WRONG_CONG.displayString() + wrongSpeeds);
						}
					}
					if (buildZoneCheckBox().isSelected()) {
						wrongZones = chceckZone(reversedPath);
						if (!wrongZones.isEmpty()) {
							JOptionPane.showMessageDialog(frame,
									Descriptions.STRING_MESSAGE_WRONG_ZONE.displayString() + wrongZones);
						}
					}

					if (wrongConfigs.isEmpty() && wrongSpeeds.isEmpty() && wrongZones.isEmpty()
							&& !reversedPath.isEmpty()) {
						saveOutputFile(reversedPath);
						fileSavedSuccesfully.showMessageDialog(frame,
								Descriptions.STRING_FILE_SAVED_SUCCESFULLY.displayString());
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("BuildButtonExecuteAndReverse Exception.");
		}
		frame.add(buttonExecuteAndReverse);
		logger.info("BuildButtonExecuteAndReverse");
	}

	public void loadSetImage() {
		BufferedImage image = null;
		try {
			image = ImageIO.read(getClass().getClassLoader().getResource(Descriptions.STRING_LOGO_JPG.displayString()));
			logger.info("Background image file loaded successfully.");
		} catch (IOException e1) {
			// System.out.println("Load image error. Check image path.");
			e1.printStackTrace();
			logger.error("Background image file load Exception");
		}
		JPanel panelImage = new JPanel();
		JLabel imageLabel = new JLabel(new ImageIcon(image));
		panelImage.setBounds(0, 0, 650, 650);
		panelImage.setBackground(Color.WHITE);
		panelImage.add(imageLabel, null);
		frame.setContentPane(imageLabel);
		logger.info("LoadSetImage");
	}

	public void buildButtonsLayout() {
		buildButtonFieldIn();
		builFieldLoadedPath();
		buildButtonExecuteAndReverse();
		logger.info("BuildButtonsLayout");
	}

	public void loadSetIcon() {
		BufferedImage icon = null;
		try {
			icon = ImageIO
					.read(getClass().getClassLoader().getResource(Descriptions.STRING_FAUCICON_PNG.displayString()));
			logger.info("Icon image file loaded successfully.");
		} catch (IOException e1) {
			e1.printStackTrace();
			logger.error("Icon image file load Exception");
		}
		frame.setIconImage(icon);
		logger.info("LoadSetIcon");
	}

	public void buildMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu(Descriptions.STRING_HELP.displayString());
		menu.setBounds(0, 0, 25, 10);
		JMenuItem itemHelp = new JMenuItem(Descriptions.STRING_LICENSE.displayString());
		itemHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame,
						Descriptions.STRING_EXPIRE_DATE_TEXT.displayString() + formatData.format(expireDate.getTime()));
			}
		});
		menu.add(itemHelp);
		menuBar.add(menu);
		frame.setJMenuBar(menuBar);
		logger.info("BuildMenu");
	}

	public void buildCheckBoxesLayout() {
		buildConfigCheckBox();
		buildSpeedCheckBox();
		buildZoneCheckBox();
		logger.info("BuildCheckBoxesLayout");
	}

	public void buildAuthorLabel() {
		JLabel author = new JLabel(Descriptions.STRING_AUTHOR.displayString());
		author.setSize(150, 50);
		author.setBackground(Color.YELLOW);
		author.setBounds(10, 575, 150, 25);
		frame.add(author);
		logger.info("BuildAuthorLabel");
	}

	public void buildLicense() {
		// January is 0 (y, m, d)
		expireDate.set(2020, 7, 1);
		final SimpleDateFormat formatData = new SimpleDateFormat(
				Descriptions.STRING_SIMPLE_DATE_FORMAT.displayString());
		// Get current date and compare
		if (Calendar.getInstance().after(expireDate)) {
			JOptionPane.showMessageDialog(frame, Descriptions.STRING_MESSAGE_EXPIRED.displayString());
			// Die
			System.exit(0);
			logger.error("License expired.");
		}
		logger.info("BuildLicense");
	}

	public void buildFrame() {
		frame.setTitle(Descriptions.STRING_FRAME_TITLE.displayString());
		frame.setSize(650, 650);
		frame.setAlwaysOnTop(false);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setVisible(true);
		logger.info("BuildFrame");
	}

}
