package authenticationserver.swagger.api;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

/**
 * Created by igor on 26.02.17.
 */
public class QRCodeGenerator {

    public static BufferedImage getQRCode(String text)
    {
        try {
            Map<EncodeHintType, Object> hintMap = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            // Now with zxing version 3.2.1 you could change border size (white border size to just 1)
            hintMap.put(EncodeHintType.MARGIN, 1); /* default = 4 */
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

            QRCodeWriter qrCodeWriter = new QRCodeWriter();

            BitMatrix byteMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 1080, 1080, hintMap);

            int CrunchifyWidth = byteMatrix.getWidth();
            BufferedImage image = new BufferedImage(CrunchifyWidth, CrunchifyWidth,
                    BufferedImage.TYPE_INT_RGB);
            image.createGraphics();

            Graphics2D graphics = (Graphics2D) image.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, CrunchifyWidth, CrunchifyWidth);
            graphics.setColor(Color.BLACK);

            for (int i = 0; i < CrunchifyWidth; i++) {
                for (int j = 0; j < CrunchifyWidth; j++) {
                    if (byteMatrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }

            return image;

        } catch (WriterException e) {
            throw new RuntimeException(e);
        }
    }

    /*public static void main(String[] args) {

        String myCodeText = "PRIVATE: 464 MIIBVwIBADANBgkqhkiG9w0BAQEFAASCAUEwggE9AgEAAkEAtWRxTvY4br+3vFf3XqvQbBwFMXoF5W14vZUYNjd/eY+mZ3gDFYis8hnk9XNLvMvB8tluva6FhdzMJ2EvQRIYoQIDAQABAkEAnzjuuSgqOxSwvHPe4MwiZHNoH5kDyfMA9DEGkri+fb2ezJkgbtFv7AbtiombbqlmDFp4FVezD91AXabA+DndzQIhAOIdImTwjApuUCjUN2ewrsYnsl/ptR7fi5XEwuyL/F2zAiEAzV4Y/DcOBb/mgtQWLS3GP45cs6WJ3wR6yLsrfp1PDlsCIQCBjOnA5aWG8G9TBUv9R9jLRcFIfNG+L5x9IfAAsa/VSwIhAKFL45HMZ94KL7DXhKdtSUTHunV6ccPQj+MyZmHmiZ37AiEA1RyJ0r6Df81PX6jWwKHkO+box9zkAq4OMQIpnfM/Pc8=\n" +
                "ENCR: 64";
        String filePath = "/home/igor/CrunchifyQR.png";
        int size = 1080;
        String fileType = "png";
        File myFile = new File(filePath);

        try {

            Map<EncodeHintType, Object> hintMap = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            // Now with zxing version 3.2.1 you could change border size (white border size to just 1)
            hintMap.put(EncodeHintType.MARGIN, 1); // default = 4
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

            QRCodeWriter qrCodeWriter = new QRCodeWriter();

            BitMatrix byteMatrix = qrCodeWriter.encode(myCodeText, BarcodeFormat.QR_CODE, size,
                    size, hintMap);

            int CrunchifyWidth = byteMatrix.getWidth();
            BufferedImage image = new BufferedImage(CrunchifyWidth, CrunchifyWidth,
                    BufferedImage.TYPE_INT_RGB);
            image.createGraphics();

            Graphics2D graphics = (Graphics2D) image.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, CrunchifyWidth, CrunchifyWidth);
            graphics.setColor(Color.BLACK);

            for (int i = 0; i < CrunchifyWidth; i++) {
                for (int j = 0; j < CrunchifyWidth; j++) {
                    if (byteMatrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }

            ImageIO.write(image, fileType, myFile);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\n\nYou have successfully created QR Code.");
    }*/

}
