package com.aswemake.api.aswemakeapitask.restDocs;

import com.aswemake.api.aswemakeapitask.controller.ItemController;
import com.aswemake.api.aswemakeapitask.dto.GlobalResponse;
import com.aswemake.api.aswemakeapitask.dto.item.response.ItemSelectResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.payload.JsonFieldType;

import static java.time.LocalDateTime.now;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ItemControllerRestDocsTest extends RestDocsSupport {
    @InjectMocks
    private ItemController itemController;

    @Override
    protected Object initController() {
        return new ItemController();
    }

    @Test
    @DisplayName("상품 정보 조회")
    void selectItem() throws Exception {
        Long id = 1L;
        GlobalResponse response = GlobalResponse
                .builder()
                .status(HttpStatus.OK)
                .timestamp(now())
                .message("쿠폰 정보 조회 성공")
                .data(ItemSelectResponseDto.builder()
                        .id(id)
                        .name("테스트 상품_AAA")
                        .price(1000)
                        .stockQuantity(100)
                        .remainingStockQuantity(50)
                        .build())
                .build();

        mockMvc.perform(get("/v1/items/{id}", id)
                        .pathInfo("/v1/items/1")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("items/select",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("상품 아이디")
                        ),
                        responseFields(
                                fieldWithPath("status").description("HTTP 상태코드"),
                                fieldWithPath("timestamp").description("응답 시간"),
                                fieldWithPath("message").description("응답 메시지"),
                                fieldWithPath("data").description("응답 데이터"),
                                fieldWithPath("data.id").description("상품 아이디").type(JsonFieldType.NUMBER),
                                fieldWithPath("data.name").description("상품 이름"),
                                fieldWithPath("data.price").description("상품 단가 가격"),
                                fieldWithPath("data.stockQuantity").description("상품 재고 수량"),
                                fieldWithPath("data.remainingStockQuantity").description("상품 남은 현재고 수량")
                        )
                ));
    }
}