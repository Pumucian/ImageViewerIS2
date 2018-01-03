package main;

import GUI.ImageViewerUI;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageViewer1 {

    // File representing the folder that you select using a FileChooser
    static List<ImageIcon> imageList;
    static File dir = new File("C:\\Users\\fali0\\Desktop");
    static String imageName;
    static ImageIcon image;
    static Image aux;
    static double aux2;
    static int newW, newH;

    // array of supported extensions (use a List if you prefer)
    static final String[] EXTENSIONS = new String[]{
        "gif", "png", "bmp", "jpg" // and other formats you need
    };
    // filter to identify images based on their extensions
    static FilenameFilter IMAGE_FILTER = (final File dir1, final String name) -> {
        for (final String ext : EXTENSIONS) {
            if (name.endsWith("." + ext)) {
                return (true);
            }
        }
        return (false);
    };

    public static void main(String[] args) {
        getImages(dir);
        System.out.println(imageList.size());
        ImageViewerUI UI = new ImageViewerUI();
        UI.setVisible(true);
    }
    
    public static void getImages(File dir){
        imageList = new ArrayList<>();
        if (dir.isDirectory()) { // make sure it's a directory
            for (final File f : dir.listFiles(IMAGE_FILTER)) {
                BufferedImage img;

                try {
                    img = ImageIO.read(f);

                    // you probably want something more involved here
                    // to display in your UI
                    imageName = f.getName();
                    image = new ImageIcon(dir + "\\" + imageName);
                    boolean mod = false;
                    if (image.getIconHeight() > ImageViewerUI.getLabelHeight()){
                        mod = true;
                        aux2 = (double) ImageViewerUI.getLabelHeight()/(double)image.getIconHeight();
                        newW = (int) (image.getIconWidth() * aux2);
                        newH = (int) (image.getIconHeight() * aux2);
                        if (newW > ImageViewerUI.getLabelWidth()){
                            aux2 = (double) ImageViewerUI.getLabelWidth()/(double)image.getIconWidth();
                            newW = (int) (image.getIconWidth() * aux2);
                            newH = (int) (image.getIconHeight() * aux2);
                        }
                    } else if ((image.getIconWidth() > ImageViewerUI.getLabelWidth())){
                        aux2 = ImageViewerUI.getLabelWidth()/(double) image.getIconWidth();
                        mod = true;
                        newW = (int) (image.getIconWidth() * aux2);
                        newH = (int) (image.getIconHeight() * aux2);
                    }
                    if (mod){
                        aux = image.getImage();
                        aux = aux.getScaledInstance(newW, newH, aux.SCALE_SMOOTH);
                        image = new ImageIcon(aux);
                    }
                    imageList.add(image);
                    //Ver reescalado con clase image
                    
                    
                } catch (final IOException e) {
                    // handle errors here
                }
            }
        }
    }
    
    public static String getDir(){
        return dir.getPath();
    }
    
    public static List<ImageIcon> getImageList(){
        return imageList;
    }
}