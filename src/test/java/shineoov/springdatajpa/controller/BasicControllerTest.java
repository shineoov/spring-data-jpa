package shineoov.springdatajpa.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import shineoov.springdatajpa.domain.item.Item;
import shineoov.springdatajpa.domain.item.ItemRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BasicControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ItemRepository itemRepository;

    @Test
    @DisplayName("도메인 클래스 컨버터")
    void domainClassConverter() throws Exception {
        //given
        String givenItemName = "newItem";
        int givenPrice = 5000;
        Item newItem = new Item(givenItemName, givenPrice);
        itemRepository.save(newItem);

        //when
        ResultActions resultActions = mockMvc.perform(get("/domain-class-converter/{id}", newItem.getId()));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("name").value(givenItemName))
                .andExpect(jsonPath("price").value(givenPrice))
                .andDo(print());
    }
}