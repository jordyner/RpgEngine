package fel.cvut.jordaji1.rpg_engine.graphics;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
This class just loads an Image from disk.
*/
public class ImageLoader {
    private final static Logger LOGGER = Logger.getLogger(ImageLoader.class.getName());

    /**
     * 
     * @param pathToImage we select path where PNG file is stored
     * @return if file exists on this location then calls toCompatibleImage (for higher FPS) and returns compatible loaded image.
     */
    public static BufferedImage loadImage(String pathToImage) {
        try {
            BufferedImage loadedImage = ImageIO.read(ImageLoader.class.getResourceAsStream(pathToImage));
            return toCompatibleImage(loadedImage);
        } catch(IOException e){
            LOGGER.log(Level.SEVERE, "Could not load image with path: " + pathToImage);
            return null;
        }
    }
    
    // taken over code from stackoverflow. It is suppose to optimalize rendering of buffered image.
    // link: https://stackoverflow.com/questions/196890/java2d-performance-issues
    private static BufferedImage toCompatibleImage(BufferedImage image)
    {
        // obtain the current system graphical settings
        GraphicsConfiguration gfxConfig = GraphicsEnvironment.
            getLocalGraphicsEnvironment().getDefaultScreenDevice().
            getDefaultConfiguration();

        /*
         * if image is already compatible and optimized for current system 
         * settings, simply return it
         */
        if (image.getColorModel().equals(gfxConfig.getColorModel()))
            return image;

        // image is not optimized, so create a new image that is
        BufferedImage newImage = gfxConfig.createCompatibleImage(
                image.getWidth(), image.getHeight(), image.getTransparency());

        // get the graphics context of the new image to draw the old image on
        Graphics2D g2d = newImage.createGraphics();

        // actually draw the image and dispose of context no longer needed
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        // return the new optimized image
        return newImage; 
    }
    
    
}
