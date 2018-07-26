package com.books.config;

import com.books.entities.Book;
import com.books.entities.Person;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.core.IsEqual.equalTo;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="file:src/main/webapp/WEB-INF/applicationContext.xml")
public class BookRestControllerTest {
    @Autowired
    private BookRestController controllerTest;

    @Before
    public void prepareTest() {
        RestAssuredMockMvc.standaloneSetup(controllerTest);
    }

    @After
    public void endTest() {
        RestAssuredMockMvc.reset();
    }

    @Test
    public void getBook() {
        given().
                when().
                get("/rest/books/2").
                then().
                statusCode(200).
                body("id", equalTo(2));
    }

    @Test
    public void getBooks() {
        given().
                when().
                get("/rest/books").
                then().
                statusCode(200);
    }

    @Test
    public void addBook() {
        MockMvcRequestSpecification request = RestAssuredMockMvc.given();
        Book testBook = new Book();
        Person p = new Person(1, "1", "2");
        testBook.setName("test");
        request.body(p);
        request.contentType("application/json");
        MockMvcResponse response = request.post("/rest/books/new").andReturn();
        Assert.assertEquals(HttpStatus.CREATED.value(), response.statusCode());
    }

    @Test
    public void removeBook() {
        MockMvcRequestSpecification request = RestAssuredMockMvc.given();
        MockMvcResponse response = request.delete("/rest/books/1").andReturn();
        Assert.assertEquals(HttpStatus.OK.value(), response.statusCode());

    }
}