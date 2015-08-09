package yang;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class YangWindow extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private static int DEFAULT_WIDTH = 300;
	private static int DEFAULT_HEIGHT = 200;
	
	private JButton randombButton;
	private JButton openButton;
	private JButton updateButton;
	private JButton saveResultButton;
	private JTextArea textArea;
	private JButton resetButton;
	private JPanel panel;	
	private JLabel label;
	private JLabel labelResult;
	
	Executer executer = Executer.getExecuterInstance();
	 
	static{
		BufferedImage image;
		try {
			image = ImageIO.read(new File("res/lena.jpg"));
			Yang.setCurrentImage(image);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public YangWindow() {
		panel = new JPanel();
		
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screen = kit.getScreenSize();
		DEFAULT_HEIGHT = (int) (screen.getHeight() / 2);
		DEFAULT_WIDTH = (int) (screen.getWidth() / 2);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setLocationByPlatform(true);
		setIconImage(new ImageIcon("res/grayscale.jpg").getImage());
		
		openButton = new JButton("Open"); openButton.setFont(new Font("Consolas", Font.HANGING_BASELINE, 26));
		randombButton = new JButton("Random"); randombButton.setFont(new Font("Consolas", Font.HANGING_BASELINE, 26));
		updateButton = new JButton("Update"); updateButton.setFont(new Font("Consolas", Font.HANGING_BASELINE, 26));
		saveResultButton = new JButton("Save"); saveResultButton.setFont(new Font("Consolas", Font.HANGING_BASELINE, 26));
		textArea = new JTextArea();	textArea.setEditable(false); 
		resetButton = new JButton("Reset"); resetButton.setFont(new Font("Consolas", Font.HANGING_BASELINE, 26));
		label = new JLabel(new ImageIcon(Yang.getCurrentImage()));
		labelResult = new JLabel(new ImageIcon(Yang.getCurrentImage()));
		
		panel.setLayout(new GridLayout(2, 2));
		
		openButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser(new File("E://Data"));
				int result = fc.showOpenDialog(YangWindow.this);
				if(result == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					String fileString = file.toString();
					boolean isImage = fileString.endsWith(".jpg") || fileString.endsWith(".png") || fileString.endsWith(".bmp");
					if(isImage) {
						try {
							Yang.setCurrentImage(ImageIO.read(new File(fileString)));
							label.setIcon(new ImageIcon(Yang.getCurrentImage()));
						} catch (IOException e1) {
						}
					}
				}
			}
		});
		
		updateButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Yang.getResultImage() == null) return;
				Yang.update();
				label.setIcon(new ImageIcon(Yang.getCurrentImage()));
			}
		});
		
		saveResultButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser(new File("E://Data"));
			  
				int result = fc.showSaveDialog(YangWindow.this);
				if (result == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					String fileString = file.toString();
					fileString += ".png";
					BufferedImage img = Yang.getResultImage();
					try {
						ImageIO.write(img, "png", new File(fileString));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		
		resetButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				BufferedImage image = null;
				try {
					image = ImageIO.read(new File("res/lena.jpg"));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Yang.setCurrentImage(image);
				label.setIcon(new ImageIcon(Yang.getCurrentImage()));
			}
		});
		
		JPanel panel1 = new JPanel(new GridLayout(2, 2));
		panel1.add(openButton);
		panel1.add(randombButton);
		panel1.add(updateButton);
		panel1.add(saveResultButton);
		
		JPanel panel2 = new JPanel(new GridLayout(2, 1));
		panel2.add(textArea);
		panel2.add(resetButton);
		
		panel.add(panel1);
		panel.add(panel2);
		panel.add(label);
		panel.add(labelResult);
		
		add(panel);
		
		randombButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				executer.run();
				if(Yang.getResultImage() != null) {
					labelResult.setIcon(new ImageIcon(Yang.getResultImage()));;
				} else {
					labelResult.setIcon(new ImageIcon(Yang.getCurrentImage()));;
				}
				textArea.setFont(new Font("Consolas", Font.BOLD, 20));
				
				textArea.setText("\n" + Yang.getCurrentOperation() +
						"\n" + Yang.getDescription());
			}
		});
		
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	// Test
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				YangWindow frame = new YangWindow();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}

}
