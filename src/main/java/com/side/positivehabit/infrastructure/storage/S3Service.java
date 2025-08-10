package com.side.positivehabit.infrastructure.storage;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Value("${cloud.aws.region.static}")
    private String region;

    /**
     * 파일 업로드
     */
    public String uploadFile(MultipartFile file, String key) {
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());

            PutObjectRequest request = new PutObjectRequest(
                    bucketName,
                    key,
                    file.getInputStream(),
                    metadata
            );

            amazonS3.putObject(request);

            return getFileUrl(key);
        } catch (IOException e) {
            log.error("Failed to upload file to S3", e);
            throw new BadRequestException("파일 업로드에 실패했습니다.");
        }
    }

    /**
     * 바이트 배열 업로드 (썸네일용)
     */
    public String uploadFile(byte[] data, String key, String contentType) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(contentType);
        metadata.setContentLength(data.length);

        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);

        PutObjectRequest request = new PutObjectRequest(
                bucketName,
                key,
                inputStream,
                metadata
        );

        amazonS3.putObject(request);

        return getFileUrl(key);
    }

    /**
     * 파일 삭제
     */
    public void deleteFile(String key) {
        try {
            amazonS3.deleteObject(bucketName, key);
            log.info("Deleted file from S3: {}", key);
        } catch (Exception e) {
            log.error("Failed to delete file from S3: {}", key, e);
            // 삭제 실패는 무시 (이미 삭제된 파일일 수 있음)
        }
    }

    /**
     * URL에서 파일 삭제
     */
    public void deleteFileFromUrl(String fileUrl) {
        try {
            String key = extractKeyFromUrl(fileUrl);
            deleteFile(key);
        } catch (Exception e) {
            log.error("Failed to delete file from URL: {}", fileUrl, e);
        }
    }

    /**
     * 파일 존재 여부 확인
     */
    public boolean doesFileExist(String key) {
        try {
            amazonS3.getObjectMetadata(bucketName, key);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Pre-signed URL 생성 (임시 다운로드 링크)
     */
    public String generatePresignedUrl(String key, int expirationMinutes) {
        java.util.Date expiration = new java.util.Date();
        long expTimeMillis = expiration.getTime() + (1000L * 60 * expirationMinutes);
        expiration.setTime(expTimeMillis);

        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, key)
                        .withMethod(HttpMethod.GET)
                        .withExpiration(expiration);

        URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();
    }

    /**
     * 파일 URL 생성
     */
    private String getFileUrl(String key) {
        return String.format("https://%s.s3.%s.amazonaws.com/%s",
                bucketName, region, key);
    }

    /**
     * URL에서 키 추출
     */
    private String extractKeyFromUrl(String fileUrl) {
        try {
            String decodedUrl = URLDecoder.decode(fileUrl, StandardCharsets.UTF_8);
            String prefix = String.format("https://%s.s3.%s.amazonaws.com/",
                    bucketName, region);

            if (decodedUrl.startsWith(prefix)) {
                return decodedUrl.substring(prefix.length());
            }

            // 다른 형식의 S3 URL 처리
            if (decodedUrl.contains(".amazonaws.com/")) {
                String[] parts = decodedUrl.split(".amazonaws.com/");
                if (parts.length > 1) {
                    return parts[1];
                }
            }

            throw new BadRequestException("Invalid S3 URL format");
        } catch (Exception e) {
            log.error("Failed to extract key from URL: {}", fileUrl, e);
            throw new BadRequestException("잘못된 파일 URL입니다.");
        }
    }
}