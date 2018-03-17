package com.java.springboot.demo.controllers;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SearchControllerTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("docs/asciidocs");

    @Before
    public void setUp(){
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();
    }

    @Test
    public void getUserWithNoIDShouldReturnAllUsersJSON() throws Exception {
       this.mvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements").value(7))
                .andDo(document("Get all users",
                       responseFields(subsectionWithPath("_embedded")
                               .description("user result payload"),
                                       fieldWithPath("_embedded.users[].name")
                                               .description("The users name"),
                                       fieldWithPath("_embedded.users[].surname")
                                               .description("The user's surname"),
                                       fieldWithPath("_embedded.users[].address")
                                               .description("The user's address"),
                                       fieldWithPath("_embedded.users[].postCode")
                                               .description("The user's postcode"),
                                       fieldWithPath("_embedded.users[].phone")
                                               .description("The user's phone"),
                                       fieldWithPath("_embedded.users[].creditLimit")
                                               .description("The user's creditLimit"),
                                       fieldWithPath("_embedded.users[].birthday")
                                               .description("The user's birthday"),
                                       fieldWithPath("_embedded.users[]._links.self.href")
                                               .description("Link to context user"),
                                       fieldWithPath("_embedded.users[]._links.user.href")
                                               .description("Link to current user"),
                               subsectionWithPath("_links")
                                       .description("Links to further endpoints"),
                               fieldWithPath("_links.self.href")
                                       .description("Link current endpoint with extra options"),
                               fieldWithPath("_links.self.templated")
                                       .description("indicating if the endpoint is results in a template format"),
                               fieldWithPath("_links.profile.href")
                                       .description("Link current endpoint with extra options"),
                               fieldWithPath("_links.search.href")
                                       .description("search endpoint"),
                               subsectionWithPath("page")
                                       .description("paging values"),
                               fieldWithPath("page.size")
                                       .description("The page size"),
                               fieldWithPath("page.totalElements")
                                       .description("total amount of users for all pages"),
                               fieldWithPath("page.totalPages")
                                       .description("total amount of pages for this request"),
                               fieldWithPath("page.number")
                                       .description("current page number")
                       )
                    ));
    }

    @Test
    public void getUserWithIDPassedShouldReturnValidUserJSON() throws Exception {
       this.mvc.perform(get("/users/1"))
               .andExpect(status().isOk());
    }

    @Test
    public void getUserWithBogusIDPassedShouldReturnBadRequest() throws Exception {
        this.mvc.perform(get("/users/77/"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void getUsersWithASpecifiedNameOrSurname() throws Exception {
        this.mvc.perform(get("/users/search/findByNameIgnoreCaseOrSurnameIgnoreCase?name=John&surname=wick"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.users[0].name").value("John"))
                .andExpect(jsonPath("$._embedded.users[0].surname").value("Johnson"))
                .andExpect(jsonPath("$._embedded.users[1].name").value("John"))
                .andExpect(jsonPath("$._embedded.users[1].surname").value("Smith"))
                .andExpect(jsonPath("$._embedded.users.length()").value(2));
    }

    @Test
    public void getUsersWithASpecifiedNameOrSurnameWithLowerCase() throws Exception {
        this.mvc.perform(get("/users/search/findByNameIgnoreCaseOrSurnameIgnoreCase?surname=gibson"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.users[0].name").value("Mal"))
                .andExpect(jsonPath("$._embedded.users[0].surname").value("Gibson"))
                .andExpect(jsonPath("$._embedded.users.length()").value(1));
    }

    @Test
    public void getUsersWithCreditLimitDecimalShouldReturnTheCorrectUsers() throws Exception {
        this.mvc.perform(get("/users/search/findByCreditLimitLessThanEqual?creditLimit=54.50"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.users[0].creditLimit").value(54))
                .andExpect(jsonPath("$._embedded.users[1].creditLimit").value(54.5))
                .andExpect(jsonPath("$._embedded.users.length()").value(2));

    }

    @Test
    public void getUsersWithCreditLimitIntegerShouldReturnTheCorrectUsers() throws Exception {
        this.mvc.perform(get("/users/search/findByCreditLimitLessThanEqual?creditLimit=54"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.users[0].creditLimit").value(54))
                .andExpect(jsonPath("$._embedded.users.length()").value(1));

    }
}
