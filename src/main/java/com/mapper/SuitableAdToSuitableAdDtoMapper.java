package com.mapper;

import com.domain.SuitableAd;
import com.dto.SuitableAdDto;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * This is {@link SuitableAdToSuitableAdDtoMapper}
 * that helper for mapping from {@link com.domain.SuitableAd}
 * to {@link com.dto.SuitableAdDto} object.
 */

@Component
public class SuitableAdToSuitableAdDtoMapper
        extends AbstractConverter<SuitableAd, SuitableAdDto> {

    /**
     * This method convert SuitableAd to SuitableDto object by
     * modelMapper.
     *
     * @param suitableAd object.
     * @return {@link SuitableAdDto}.
     */
    @Override
    protected SuitableAdDto convert(final SuitableAd suitableAd) {
        final @NotNull int authorFkId = suitableAd.getAuthor().getId();
        return SuitableAdDto.builder()
                .id(suitableAd.getId())
                .version(suitableAd.getVersion())
                .category(suitableAd.getCategory())
                .title(suitableAd.getTitle())
                .priceFrom(suitableAd.getPriceFrom())
                .priceTo(suitableAd.getPriceTo())
                .authorFkId(authorFkId)
                .build();
    }
}
