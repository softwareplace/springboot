package com.java.example.model;

import com.java.example.rest.model.BaseResponseRest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BaseResponseMapperTest {

    private BaseResponseMapper unit;

    @BeforeEach
    void setup() {
        unit = spy(Mappers.getMapper(BaseResponseMapper.class));
    }

    @Test
    @DisplayName("must return expected output object")
    void parseTestCase01() {
        var response = BaseResponse.builder()
                .message("[java-example] Application is running")
                .success(true)
                .time(LocalTime.now())
                .date(LocalDate.now())
                .dateTime(LocalDateTime.now())
                .timestamp(System.currentTimeMillis())
                .build();

        var output = unit.parse(response);

        assertNotNull(output);
        assertTrue(output instanceof BaseResponseRest);
        assertEquals(response.message(), output.getMessage());
        assertEquals(response.success(), output.getSuccess());
        assertEquals(response.time(), output.getTime());
        assertEquals(response.date(), output.getDate());
        assertEquals(response.timestamp(), output.getTimestamp());
        assertEquals(response.dateTime(), output.getDateTime());
    }
}