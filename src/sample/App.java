package sample;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

public class App {
    private JPanel mainPanel;
    private JTextField nomField;
    private JLabel nomLabel;
    private JTextField prenomField;
    private JTextField cinField;
    private JTextField telField;
    private JComboBox resultatField;
    private JLabel qrImage;
    private JButton generateQRButton;
    private JButton saveButton;

    public App() {

        final File[] tempQR = {null};
        generateQRButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String data = """
                            -----------
                            \nNom : """ + nomField.getText() + """
                            \nPrenom : """ + prenomField.getText() + """
                            \nCIN : """ + cinField.getText() + """
                            \nNumero Tel :""" + telField.getText() + """
                            \nResultat :""" + resultatField.getSelectedItem() + """
                            \n-----------
                            
                            \n-----------""";
                    tempQR[0] = QRGenerator.generate(data);
                    System.out.println("reading temp file . !");
                    BufferedImage bufferedImage = ImageIO.read(tempQR[0]);
//
//                    File out = new File("C:\\Users\\ziedk\\Desktop\\QRs\\res.png");
//
//                    ImageIO.write(image, "png", out);
                    qrImage.setIcon(new ImageIcon(bufferedImage));

                } catch (WriterException writerException) {
                    writerException.printStackTrace();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (NotFoundException notFoundException) {
                    notFoundException.printStackTrace();
                }
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BufferedImage image = null;
                JFileChooser chooser= new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG", "png");
                chooser.setFileFilter(filter);
                chooser.addChoosableFileFilter(filter);
                try {

                    int choice = chooser.showOpenDialog(null);
                    if (choice != JFileChooser.APPROVE_OPTION) return;
                    image = ImageIO.read(tempQR[0]);
                    File chosenFile = chooser.getSelectedFile();
                    String filepath = chosenFile.getAbsolutePath();
                    chosenFile.delete();
                    if (!filepath.substring(filepath.lastIndexOf('.') + 1).equals("png")) {
                        filepath = filepath + ".png";
                    }
//                    "C:\\Users\\ziedk\\Desktop\\QRs\\res.png"
                    File out = new File(filepath);
                    ImageIO.write(image, "png", out);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("QR generator");
        frame.setContentPane(new App().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

        qrImage = new JLabel(new ImageIcon(ImageEditor.getScaledImage(new ImageIcon("question-mark.png").getImage(), 200, 300)));
    }
}
