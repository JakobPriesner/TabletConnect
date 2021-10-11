package logic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class MyImage implements Serializable {

    byte[] picture;

    public MyImage(BufferedImage image){
        transformImage(image);
    }

    private void transformImage(BufferedImage img){

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(img, "jpg", baos);
            baos.flush();
            picture = baos.toByteArray();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public BufferedImage getImage() throws IOException {
        InputStream is = new ByteArrayInputStream(picture);
        return ImageIO.read(is);
    }

}
