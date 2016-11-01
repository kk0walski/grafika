
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author karol
 */
public class Interfejs extends JFrame implements ActionListener{
    
    public Interfejs()
    {
        initUI();
    }
    
    private void initUI()
    {
        createMenuBar();
        
        setTitle("Aplikacja");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    private void createMenuBar() {
        JMenuBar menubar = new JMenuBar();
        ImageIcon icon = new ImageIcon("exit.png");
        ImageIcon pIcon = new ImageIcon("pictureRead.png");
        //JMenu File
        JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);
        //File menu item Exit
        JMenuItem eMenuItem = new JMenuItem("Exit", icon);
        eMenuItem.setMnemonic(KeyEvent.VK_E);
        eMenuItem.setToolTipText("Exit aplication");
        eMenuItem.addActionListener((ActionEvent event) -> {
            System.exit(0);
        });
        file.add(eMenuItem);
        //File menu item read
        JMenuItem pMenuItem = new JMenuItem("ReadPicture", pIcon);
        pMenuItem.setToolTipText("Read picture");
        pMenuItem.addActionListener((ActionEvent event) -> {
            readPicture();
        });
        file.add(pMenuItem);
        menubar.add(file);
        
        setJMenuBar(menubar);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void readPicture(){
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Please choose an image...");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPEG", "jpeg", "jpg", "png", "bmp", "gif");
        fc.addChoosableFileFilter(filter);

        BufferedImage origImage = null;
        // You should use the parent component instead of null
        // but it was impossible to tell from the code snippet what that was.
        if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fc.getSelectedFile();
            try {
                origImage = ImageIO.read(selectedFile);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public static void main(String[] args){
        EventQueue.invokeLater(() -> {
            new Interfejs();
        });
    }
}
