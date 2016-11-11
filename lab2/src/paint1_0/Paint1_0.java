/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint1_0;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author karol
 */
public class Paint1_0  extends JFrame implements MouseListener{
    
        private BufferedImage obraz = null;
        public static DefaultListModel model = new DefaultListModel();
        public static int choose = 0;
        private DrawPanel panel;
        private JList lista = null;
        public static Shapes myShape = null;
        public static int myIndex = -1;
	public Paint1_0()
	{
            initUI();
	}

	private void initUI()
	{
            setTitle("Aplikacja");
            setSize(1000, 600);
            createMenuBar();
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setLayout(new BorderLayout());  
            lista = new JList(model);
            lista.setVisibleRowCount(4);
            lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            lista.addMouseListener(this);
            JScrollPane panelBoczny = new JScrollPane(lista);
            this.add(panelBoczny, BorderLayout.WEST);
            readPicture();
            panel = new DrawPanel(obraz);
            this.add(panel, BorderLayout.CENTER);
	}

	private void createMenuBar() {
        JMenuBar menubar = new JMenuBar();
        //JMenu File
        JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);
        //File menu item Exit
        JMenuItem eMenuItem = new JMenuItem("Exit");
        eMenuItem.setMnemonic(KeyEvent.VK_E);
        eMenuItem.setToolTipText("Exit aplication");
        eMenuItem.addActionListener((ActionEvent event) -> {
            System.exit(0);
        });
        JMenuItem sMenuItem = new JMenuItem("Save");
        sMenuItem.setToolTipText("Save shapes to xml file");
        sMenuItem.addActionListener((ActionEvent event) -> {
            panel.save();
        });
        JMenuItem rMenuItem = new JMenuItem("Read");
        rMenuItem.setToolTipText("Read from xml file");
        rMenuItem.addActionListener((ActionEvent event) -> {
            panel.read();
        });
        file.add(eMenuItem);
        file.add(sMenuItem);
        file.add(rMenuItem);
        //Add menu bar
        menubar.add(file);
        
        JMenu shape = new JMenu("Shape");
        JMenuItem square = new JMenuItem("Square");
        square.addActionListener((ActionEvent event) -> {
            choose = 2;
        });
        square.setToolTipText("Draw a square");
        JMenuItem line = new JMenuItem("Line");
        line.setToolTipText("Draw a line");
        line.addActionListener((ActionEvent event) -> {
            choose = 1;
        });
        JMenuItem ellipse = new JMenuItem("Ellipse");
        ellipse.setToolTipText("Draw a ellipse");
        ellipse.addActionListener((ActionEvent event) -> {
            choose = 3;
        });
        shape.add(square);
        shape.add(line);
        shape.add(ellipse);
        menubar.add(shape);
        

        
        setJMenuBar(menubar);
    }
        
    private void readPicture(){
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Please choose an image...");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPEG", "jpeg", "jpg", "png", "bmp", "gif");
        fc.addChoosableFileFilter(filter);

        // You should use the parent component instead of null
        // but it was impossible to tell from the code snippet what that was.
        if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fc.getSelectedFile();
            try {
                obraz = ImageIO.read(selectedFile);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
        
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Paint1_0 interfejs = new Paint1_0();
            interfejs.setVisible(true);
        });
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Mouse clicked");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int index = lista.getSelectedIndex();
        if(e.getClickCount() == 2){
            model.remove(index);
            panel.fromList(index);
            repaint();
            }
        else{
            myShape = panel.lista.get(index);
            myIndex = index;
            panel.lista.get(index).increaseThickness();
            panel.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e){
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
