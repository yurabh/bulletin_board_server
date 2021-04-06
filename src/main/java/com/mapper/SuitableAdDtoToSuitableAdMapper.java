package com.mapper;

import com.domain.Author;
import com.domain.SuitableAd;
import com.dto.SuitableAdDto;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * This is {@link SuitableAdDtoToSuitableAdMapper}
 * that helper for mapping from {@link SuitableAdDto}
 * to {@link SuitableAd} object.
 *
 * @author Yuriy Bahlay.
 * @version 1.1.
 */

@Component
public class SuitableAdDtoToSuitableAdMapper
        extends AbstractConverter<SuitableAdDto, SuitableAd> {

    /**
     * This method convert SuitableAdDto to Suitable object by
     * modelMapper.
     *
     * @param suitableAdDto object.
     * @return {@link SuitableAd}.
     */
    @Override
    protected SuitableAd convert(final SuitableAdDto suitableAdDto) {
        final @NotNull int authorFkId = suitableAdDto.getAuthorFkId();
        final Author author = new Author();
        author.setId(authorFkId);
        return SuitableAd.builder()
                .id(suitableAdDto.getId())
                .version(suitableAdDto.getVersion())
                .category(suitableAdDto.getCategory())
                .title(suitableAdDto.getTitle())
                .priceFrom(suitableAdDto.getPriceFrom())
                .priceTo(suitableAdDto.getPriceTo())
                .author(author)
                .build();
    }
}
