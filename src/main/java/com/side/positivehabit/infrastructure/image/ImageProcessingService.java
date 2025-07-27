package com.side.positivehabit.infrastructure.image;

import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
@Service
public class ImageProcessingService {

    private static final String DEFAULT_FORMAT = "jpg";
    private static final float DEFAULT_QUALITY = 0.8f;

    /**
     * 썸네일 생성
     */
    public byte[] createThumbnail(byte[] originalImage, int targetWidth, int targetHeight) throws BadRequestException {
        try {
            // 원본 이미지 읽기
            BufferedImage original = ImageIO.read(new ByteArrayInputStream(originalImage));
            if (original == null) {
                throw new BadRequestException("이미지를 읽을 수 없습니다.");
            }

            // 썸네일 크기 계산 (비율 유지)
            Dimension thumbnailSize = calculateThumbnailSize(
                    original.getWidth(),
                    original.getHeight(),
                    targetWidth,
                    targetHeight
            );

            // 썸네일 생성
            BufferedImage thumbnail = resizeImage(
                    original,
                    thumbnailSize.width,
                    thumbnailSize.height
            );

            // 바이트 배열로 변환
            return imageToByteArray(thumbnail, DEFAULT_FORMAT);

        } catch (IOException e) {
            log.error("Failed to create thumbnail", e);
            throw new BadRequestException("썸네일 생성에 실패했습니다.");
        }
    }

    /**
     * 이미지 리사이즈
     */
    public byte[] resizeImage(byte[] originalImage, int maxWidth, int maxHeight) throws BadRequestException {
        try {
            BufferedImage original = ImageIO.read(new ByteArrayInputStream(originalImage));
            if (original == null) {
                throw new BadRequestException("이미지를 읽을 수 없습니다.");
            }

            // 리사이즈가 필요한지 확인
            if (original.getWidth() <= maxWidth && original.getHeight() <= maxHeight) {
                return originalImage;
            }

            // 새로운 크기 계산
            Dimension newSize = calculateThumbnailSize(
                    original.getWidth(),
                    original.getHeight(),
                    maxWidth,
                    maxHeight
            );

            // 리사이즈
            BufferedImage resized = resizeImage(
                    original,
                    newSize.width,
                    newSize.height
            );

            return imageToByteArray(resized, DEFAULT_FORMAT);

        } catch (IOException e) {
            log.error("Failed to resize image", e);
            throw new BadRequestException("이미지 리사이즈에 실패했습니다.");
        }
    }

    /**
     * 이미지 압축
     */
    public byte[] compressImage(byte[] originalImage, float quality) throws BadRequestException {
        try {
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(originalImage));
            if (image == null) {
                throw new BadRequestException("이미지를 읽을 수 없습니다.");
            }

            // JPEG로 변환하여 압축
            BufferedImage rgbImage = convertToRGB(image);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            // ImageWriter를 사용한 품질 조정
            javax.imageio.ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();
            javax.imageio.ImageWriteParam param = writer.getDefaultWriteParam();

            if (param.canWriteCompressed()) {
                param.setCompressionMode(javax.imageio.ImageWriteParam.MODE_EXPLICIT);
                param.setCompressionQuality(quality);
            }

            writer.setOutput(ImageIO.createImageOutputStream(baos));
            writer.write(null, new javax.imageio.IIOImage(rgbImage, null, null), param);
            writer.dispose();

            return baos.toByteArray();

        } catch (IOException e) {
            log.error("Failed to compress image", e);
            throw new BadRequestException("이미지 압축에 실패했습니다.");
        }
    }

    /**
     * 썸네일 크기 계산 (비율 유지)
     */
    private Dimension calculateThumbnailSize(int originalWidth, int originalHeight,
                                             int targetWidth, int targetHeight) {
        double widthRatio = (double) targetWidth / originalWidth;
        double heightRatio = (double) targetHeight / originalHeight;
        double ratio = Math.min(widthRatio, heightRatio);

        int newWidth = (int) (originalWidth * ratio);
        int newHeight = (int) (originalHeight * ratio);

        return new Dimension(newWidth, newHeight);
    }

    /**
     * 이미지 리사이즈 수행
     */
    private BufferedImage resizeImage(BufferedImage original, int width, int height) {
        Image scaledImage = original.getScaledInstance(width, height, Image.SCALE_SMOOTH);

        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = resized.createGraphics();

        // 렌더링 품질 설정
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.drawImage(scaledImage, 0, 0, null);
        g2d.dispose();

        return resized;
    }

    /**
     * ARGB를 RGB로 변환 (JPEG 호환)
     */
    private BufferedImage convertToRGB(BufferedImage image) {
        if (image.getType() == BufferedImage.TYPE_INT_RGB) {
            return image;
        }

        BufferedImage rgbImage = new BufferedImage(
                image.getWidth(),
                image.getHeight(),
                BufferedImage.TYPE_INT_RGB
        );

        Graphics2D g2d = rgbImage.createGraphics();
        g2d.setColor(Color.WHITE); // 배경색
        g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return rgbImage;
    }

    /**
     * BufferedImage를 바이트 배열로 변환
     */
    private byte[] imageToByteArray(BufferedImage image, String format) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, format, baos);
        return baos.toByteArray();
    }

    /**
     * 이미지 포맷 확인
     */
    public String getImageFormat(byte[] imageData) {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(imageData)) {
            String[] formats = ImageIO.getReaderFormatNames();
            for (String format : formats) {
                ImageIO.setUseCache(false);
                if (ImageIO.read(bais) != null) {
                    return format.toLowerCase();
                }
                bais.reset();
            }
        } catch (IOException e) {
            log.error("Failed to determine image format", e);
        }
        return DEFAULT_FORMAT;
    }
}