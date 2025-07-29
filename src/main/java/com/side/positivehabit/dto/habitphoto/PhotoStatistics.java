package com.side.positivehabit.dto.habitphoto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhotoStatistics {

    private Long userId;
    private Long habitId;
    private Long totalPhotos;
    private Long totalSize; // bytes

    public Double getTotalSizeMB() {
        return totalSize / (1024.0 * 1024.0);
    }
}