package sample;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.toedter.calendar.JDateChooser;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
    private JLabel cinAff;
    private JPanel datePanel;
    private JFormattedTextField dateDeNaissanceField;
    JDateChooser dateChooser = new JDateChooser();

    public App() {

        dateChooser.setDateFormatString("dd/MM/yyyy");
        datePanel.add(dateChooser);

        final File[] tempQR = new File[1];
        generateQRButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    String data = """
                            -----------
                            \nNom : """ + nomField.getText() + """
                            \nPrenom : """ + prenomField.getText() + """
                            \nCIN : """ + cinField.getText() + """
                            \nNumero Tel :""" + telField.getText() + """
                            \nDate De Naissance :""" + simpleDateFormat.format(dateChooser.getDate()) + """
                            \nResultat :""" + resultatField.getSelectedItem() + """
                            \n-----------
                            \nlaboratoire d'analyse medicale DR Sami El-Kadhi
                            \nTel: 71693468
                            \nMF: 1218601GAP000
                            \n-----------""";
                    //TODO: fassa5 ligne laboratoire
                    tempQR[0] = QRGenerator.generate(data);
                    System.out.println("reading temp file . !");
                    BufferedImage bufferedImage = ImageIO.read(tempQR[0]);
//
//                    File out = new File("C:\\Users\\ziedk\\Desktop\\QRs\\res.png");
//
//                    ImageIO.write(image, "png", out);
                    qrImage.setIcon(new ImageIcon(bufferedImage));
                    cinAff.setText("CIN : " + cinField.getText());
                    DateFormat df = SimpleDateFormat.getDateInstance();
                    DateFormatter dff = new DateFormatter(df);
                    dateDeNaissanceField.setFormatterFactory(new DefaultFormatterFactory(dff));
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
//                JFileChooser chooser= new JFileChooser();
//                FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG", "png");
//                chooser.setFileFilter(filter);
//                chooser.addChoosableFileFilter(filter);
                try {

//                    int choice = chooser.showOpenDialog(null);
//                    if (choice != JFileChooser.APPROVE_OPTION) return;
                    image = ImageIO.read(tempQR[0]);
//                    File chosenFile = chooser.getSelectedFile();
//                    String filepath = chosenFile.getAbsolutePath();
//                    chosenFile.delete();
//                    if (!filepath.substring(filepath.lastIndexOf('.') + 1).equals("png")) {
//                        filepath = filepath + ".png";
//                    }
                    char separator = File.separatorChar;
                    String filepath = System.getProperty("user.home") + separator + "Documents" + separator + "QRCodes" + separator;
                    String filename = cinField.getText() + ".png";
                    File out = new File(filepath + filename);
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
