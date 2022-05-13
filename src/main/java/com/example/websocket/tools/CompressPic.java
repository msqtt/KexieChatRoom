package com.example.websocket.tools;

import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class CompressPic {

    public static boolean CompressPic(InputStream oriPic, String contextPath, String base, String last) throws IOException {
        BufferedImage image = ImageIO.read(oriPic);
        if (last.equals("gif")) return false;
        if (image.getWidth() > 1080 || image.getHeight() > 1920) {
            Thumbnails.of(contextPath + base+ "." + last).size(1920, 1080).outputQuality(0.9).outputFormat("jpg").toFile(contextPath + base);
        }
        else {
            Thumbnails.of(contextPath + base + "." + last).outputQuality(0.9).scale(1f).outputFormat("jpg").toFile(contextPath + base);
        }

        if (last.equals("jpg")) return false;
        return true;

    }
    public static void CompressHead(String path, String id) throws IOException {

        BufferedImage image = ImageIO.read(new File(path + id + ".jpg"));
        if (image.getWidth() > 1080 || image.getHeight() > 1920) {
            Thumbnails.of(path + id + ".jpg").size(1920, 1080).outputQuality(0.9).outputFormat("jpg").toFile( path + id);
        }
        else {
            Thumbnails.of(path + id + ".jpg").outputQuality(0.9).scale(1f).outputFormat("jpg").toFile(path+id);
        }

    }
}
