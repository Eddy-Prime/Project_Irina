package be.ucll.se.courses.backend.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.fasterxml.jackson.databind.ObjectMapper;

import be.ucll.se.courses.backend.repository.DbInitializer;
import be.ucll.se.courses.backend.service.JwtService;
import be.ucll.se.courses.backend.model.Role;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@Sql("classpath:schema.sql")
public class ScheduleIntegrationTest {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private DbInitializer dbInitializer;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtService jwtService;

    private String adminToken;
    private String lecturerToken;
    private String studentToken;

    @BeforeEach
    void setup() {
        dbInitializer.clearAll();
        dbInitializer.init();
        adminToken = "Bearer " + jwtService.generateToken("admin", Role.ADMIN);
        lecturerToken = "Bearer " + jwtService.generateToken("johanp", Role.LECTURER);
        studentToken = "Bearer " + jwtService.generateToken("lindas", Role.STUDENT);
    }

    // @Test
    // void testGetSchedules_asAdmin_shouldReturnAllSchedules() {
    //     webTestClient.get().uri("/schedules")
    //     .header("Authorization", adminToken)
    //     .exchange()
    //     .expectStatus().isOk()
    //     .expectBody()
    //     .json("[\r\n" + //
    //                     "  {\r\n" + //
    //                     "    \"id\": 1,\r\n" + //
    //                     "    \"start\": \"2025-03-15T07:30:00Z\",\r\n" + //
    //                     "    \"end\": \"2025-03-15T09:30:00Z\",\r\n" + //
    //                     "    \"course\": {\r\n" + //
    //                     "      \"id\": 1,\r\n" + //
    //                     "      \"name\": \"Full-stack development\",\r\n" + //
    //                     "      \"description\": \"Learn how to build a full stack web application.\",\r\n" + //
    //                     "      \"phase\": 2,\r\n" + //
    //                     "      \"credits\": 6\r\n" + //
    //                     "    },\r\n" + //
    //                     "    \"lecturer\": {\r\n" + //
    //                     "      \"id\": 1,\r\n" + //
    //                     "      \"expertise\": \"Full-stack development, Front-end development\",\r\n" + //
    //                     "      \"user\": {\r\n" + //
    //                     "        \"id\": 2,\r\n" + //
    //                     "        \"username\": \"johanp\",\r\n" + //
    //                     "        \"firstName\": \"Johan\",\r\n" + //
    //                     "        \"lastName\": \"Pieck\",\r\n" + //
    //                     "        \"email\": \"johan.pieck@ucll.be\",\r\n" + //
    //                     "        \"role\": \"lecturer\"\r\n" + //
    //                     "      },\r\n" + //
    //                     "      \"courses\": [\r\n" + //
    //                     "        {\r\n" + //
    //                     "          \"id\": 3,\r\n" + //
    //                     "          \"name\": \"Front-End Development\",\r\n" + //
    //                     "          \"description\": \"Learn how to build a front-end web application.\",\r\n" + //
    //                     "          \"phase\": 1,\r\n" + //
    //                     "          \"credits\": 6\r\n" + //
    //                     "        },\r\n" + //
    //                     "        {\r\n" + //
    //                     "          \"id\": 1,\r\n" + //
    //                     "          \"name\": \"Full-stack development\",\r\n" + //
    //                     "          \"description\": \"Learn how to build a full stack web application.\",\r\n" + //
    //                     "          \"phase\": 2,\r\n" + //
    //                     "          \"credits\": 6\r\n" + //
    //                     "        }\r\n" + //
    //                     "      ]\r\n" + //
    //                     "    },\r\n" + //
    //                     "    \"students\": [\r\n" + //
    //                     "      {\r\n" + //
    //                     "        \"id\": 1,\r\n" + //
    //                     "        \"user\": {\r\n" + //
    //                     "          \"id\": 5,\r\n" + //
    //                     "          \"username\": \"peterp\",\r\n" + //
    //                     "          \"firstName\": \"Peter\",\r\n" + //
    //                     "          \"lastName\": \"Parker\",\r\n" + //
    //                     "          \"email\": \"peter.parker@student.ucll.be\",\r\n" + //
    //                     "          \"role\": \"student\"\r\n" + //
    //                     "        },\r\n" + //
    //                     "        \"studentnumber\": \"r0785023\"\r\n" + //
    //                     "      }\r\n" + //
    //                     "    ]\r\n" + //
    //                     "  },\r\n" + //
    //                     "  {\r\n" + //
    //                     "    \"id\": 2,\r\n" + //
    //                     "    \"start\": \"2025-03-15T12:30:00Z\",\r\n" + //
    //                     "    \"end\": \"2025-03-15T14:30:00Z\",\r\n" + //
    //                     "    \"course\": {\r\n" + //
    //                     "      \"id\": 1,\r\n" + //
    //                     "      \"name\": \"Full-stack development\",\r\n" + //
    //                     "      \"description\": \"Learn how to build a full stack web application.\",\r\n" + //
    //                     "      \"phase\": 2,\r\n" + //
    //                     "      \"credits\": 6\r\n" + //
    //                     "    },\r\n" + //
    //                     "    \"lecturer\": {\r\n" + //
    //                     "      \"id\": 2,\r\n" + //
    //                     "      \"expertise\": \"Software Engineering, Back-End Development\",\r\n" + //
    //                     "      \"user\": {\r\n" + //
    //                     "        \"id\": 3,\r\n" + //
    //                     "        \"username\": \"elkes\",\r\n" + //
    //                     "        \"firstName\": \"Elke\",\r\n" + //
    //                     "        \"lastName\": \"Steegmans\",\r\n" + //
    //                     "        \"email\": \"elke.steegmans@ucll.be\",\r\n" + //
    //                     "        \"role\": \"lecturer\"\r\n" + //
    //                     "      },\r\n" + //
    //                     "      \"courses\": [\r\n" + //
    //                     "        {\r\n" + //
    //                     "          \"id\": 1,\r\n" + //
    //                     "          \"name\": \"Full-stack development\",\r\n" + //
    //                     "          \"description\": \"Learn how to build a full stack web application.\",\r\n" + //
    //                     "          \"phase\": 2,\r\n" + //
    //                     "          \"credits\": 6\r\n" + //
    //                     "        },\r\n" + //
    //                     "        {\r\n" + //
    //                     "          \"id\": 2,\r\n" + //
    //                     "          \"name\": \"Software Engineering\",\r\n" + //
    //                     "          \"description\": \"Learn how to build and deploy a software application.\",\r\n" + //
    //                     "          \"phase\": 2,\r\n" + //
    //                     "          \"credits\": 6\r\n" + //
    //                     "        }\r\n" + //
    //                     "      ]\r\n" + //
    //                     "    },\r\n" + //
    //                     "    \"students\": [\r\n" + //
    //                     "      {\r\n" + //
    //                     "        \"id\": 2,\r\n" + //
    //                     "        \"user\": {\r\n" + //
    //                     "          \"id\": 6,\r\n" + //
    //                     "          \"username\": \"bruceb\",\r\n" + //
    //                     "          \"firstName\": \"Bruce\",\r\n" + //
    //                     "          \"lastName\": \"Banner\",\r\n" + //
    //                     "          \"email\": \"bruce.banner@student.ucll.be\",\r\n" + //
    //                     "          \"role\": \"student\"\r\n" + //
    //                     "        },\r\n" + //
    //                     "        \"studentnumber\": \"r0785024\"\r\n" + //
    //                     "      }\r\n" + //
    //                     "    ]\r\n" + //
    //                     "  },\r\n" + //
    //                     "  {\r\n" + //
    //                     "    \"id\": 3,\r\n" + //
    //                     "    \"start\": \"2025-03-15T12:30:00Z\",\r\n" + //
    //                     "    \"end\": \"2025-03-15T14:30:00Z\",\r\n" + //
    //                     "    \"course\": {\r\n" + //
    //                     "      \"id\": 2,\r\n" + //
    //                     "      \"name\": \"Software Engineering\",\r\n" + //
    //                     "      \"description\": \"Learn how to build and deploy a software application.\",\r\n" + //
    //                     "      \"phase\": 2,\r\n" + //
    //                     "      \"credits\": 6\r\n" + //
    //                     "    },\r\n" + //
    //                     "    \"lecturer\": {\r\n" + //
    //                     "      \"id\": 2,\r\n" + //
    //                     "      \"expertise\": \"Software Engineering, Back-End Development\",\r\n" + //
    //                     "      \"user\": {\r\n" + //
    //                     "        \"id\": 3,\r\n" + //
    //                     "        \"username\": \"elkes\",\r\n" + //
    //                     "        \"firstName\": \"Elke\",\r\n" + //
    //                     "        \"lastName\": \"Steegmans\",\r\n" + //
    //                     "        \"email\": \"elke.steegmans@ucll.be\",\r\n" + //
    //                     "        \"role\": \"lecturer\"\r\n" + //
    //                     "      },\r\n" + //
    //                     "      \"courses\": [\r\n" + //
    //                     "        {\r\n" + //
    //                     "          \"id\": 1,\r\n" + //
    //                     "          \"name\": \"Full-stack development\",\r\n" + //
    //                     "          \"description\": \"Learn how to build a full stack web application.\",\r\n" + //
    //                     "          \"phase\": 2,\r\n" + //
    //                     "          \"credits\": 6\r\n" + //
    //                     "        },\r\n" + //
    //                     "        {\r\n" + //
    //                     "          \"id\": 2,\r\n" + //
    //                     "          \"name\": \"Software Engineering\",\r\n" + //
    //                     "          \"description\": \"Learn how to build and deploy a software application.\",\r\n" + //
    //                     "          \"phase\": 2,\r\n" + //
    //                     "          \"credits\": 6\r\n" + //
    //                     "        }\r\n" + //
    //                     "      ]\r\n" + //
    //                     "    },\r\n" + //
    //                     "    \"students\": [\r\n" + //
    //                     "      {\r\n" + //
    //                     "        \"id\": 4,\r\n" + //
    //                     "        \"user\": {\r\n" + //
    //                     "          \"id\": 8,\r\n" + //
    //                     "          \"username\": \"michaelm\",\r\n" + //
    //                     "          \"firstName\": \"Michael\",\r\n" + //
    //                     "          \"lastName\": \"Miller\",\r\n" + //
    //                     "          \"email\": \"michael.miller@student.ucll.be\",\r\n" + //
    //                     "          \"role\": \"student\"\r\n" + //
    //                     "        },\r\n" + //
    //                     "        \"studentnumber\": \"r0785026\"\r\n" + //
    //                     "      }\r\n" + //
    //                     "    ]\r\n" + //
    //                     "  },\r\n" + //
    //                     "  {\r\n" + //
    //                     "    \"id\": 4,\r\n" + //
    //                     "    \"start\": \"2025-03-15T09:45:00Z\",\r\n" + //
    //                     "    \"end\": \"2025-03-15T11:45:00Z\",\r\n" + //
    //                     "    \"course\": {\r\n" + //
    //                     "      \"id\": 4,\r\n" + //
    //                     "      \"name\": \"Back-end Development\",\r\n" + //
    //                     "      \"description\": \"Learn how to build a REST-API in a back-end application.\",\r\n" + //
    //                     "      \"phase\": 1,\r\n" + //
    //                     "      \"credits\": 6\r\n" + //
    //                     "    },\r\n" + //
    //                     "    \"lecturer\": {\r\n" + //
    //                     "      \"id\": 3,\r\n" + //
    //                     "      \"expertise\": \"Full-Stack development, Back-end Development\",\r\n" + //
    //                     "      \"user\": {\r\n" + //
    //                     "        \"id\": 4,\r\n" + //
    //                     "        \"username\": \"greetjej\",\r\n" + //
    //                     "        \"firstName\": \"Greetje\",\r\n" + //
    //                     "        \"lastName\": \"Jongen\",\r\n" + //
    //                     "        \"email\": \"greetje.jongen@ucll.be\",\r\n" + //
    //                     "        \"role\": \"lecturer\"\r\n" + //
    //                     "      },\r\n" + //
    //                     "      \"courses\": [\r\n" + //
    //                     "        {\r\n" + //
    //                     "          \"id\": 1,\r\n" + //
    //                     "          \"name\": \"Full-stack development\",\r\n" + //
    //                     "          \"description\": \"Learn how to build a full stack web application.\",\r\n" + //
    //                     "          \"phase\": 2,\r\n" + //
    //                     "          \"credits\": 6\r\n" + //
    //                     "        },\r\n" + //
    //                     "        {\r\n" + //
    //                     "          \"id\": 4,\r\n" + //
    //                     "          \"name\": \"Back-end Development\",\r\n" + //
    //                     "          \"description\": \"Learn how to build a REST-API in a back-end application.\",\r\n" + //
    //                     "          \"phase\": 1,\r\n" + //
    //                     "          \"credits\": 6\r\n" + //
    //                     "        }\r\n" + //
    //                     "      ]\r\n" + //
    //                     "    },\r\n" + //
    //                     "    \"students\": [\r\n" + //
    //                     "      {\r\n" + //
    //                     "        \"id\": 5,\r\n" + //
    //                     "        \"user\": {\r\n" + //
    //                     "          \"id\": 9,\r\n" + //
    //                     "          \"username\": \"lindas\",\r\n" + //
    //                     "          \"firstName\": \"Linda\",\r\n" + //
    //                     "          \"lastName\": \"Lawson\",\r\n" + //
    //                     "          \"email\": \"linda.lawson@student.ucll.be\",\r\n" + //
    //                     "          \"role\": \"student\"\r\n" + //
    //                     "        },\r\n" + //
    //                     "        \"studentnumber\": \"r0785027\"\r\n" + //
    //                     "      }\r\n" + //
    //                     "    ]\r\n" + //
    //                     "  }\r\n" + //
    //                     "]");
    // }

    @Test
    void testGetSchedules_asStudent_shouldReturnEmptyList() {
        webTestClient.get().uri("/schedules")
                .header("Authorization", studentToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .json("[]");
    }

    @Test
    void testCreateSchedule_asAdmin_shouldReturnCreatedSchedule() {

        webTestClient.post().uri("/schedules")
                .header("Authorization", adminToken)
                .header("Content-Type", "application/json")
                .bodyValue("{\r\n" + //
                        "  \"start\": \"2025-03-12T16:30:17.253Z\",\r\n" + //
                        "  \"end\": \"2025-03-12T16:30:17.253Z\",\r\n" + //
                        "  \"course\": {\r\n" + //
                        "    \"id\": 2\r\n" + //
                        "  },\r\n" + //
                        "  \"lecturer\": {\r\n" + //
                        "    \"id\": 1\r\n" + //
                        "  }\r\n" + //
                        "}")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .json("{\r\n" + //
                        "  \"id\": 5,\r\n" + //
                        "  \"start\": \"2025-03-12T16:30:17.253Z\",\r\n" + //
                        "  \"end\": \"2025-03-12T16:30:17.253Z\",\r\n" + //
                        "  \"course\": {\r\n" + //
                        "    \"id\": 2,\r\n" + //
                        "    \"name\": \"Software Engineering\",\r\n" + //
                        "    \"description\": \"Learn how to build and deploy a software application.\",\r\n" + //
                        "    \"phase\": 2,\r\n" + //
                        "    \"credits\": 6\r\n" + //
                        "  },\r\n" + //
                        "  \"lecturer\": {\r\n" + //
                        "    \"id\": 1,\r\n" + //
                        "    \"expertise\": \"Full-stack development, Front-end development\",\r\n" + //
                        "    \"user\": {\r\n" + //
                        "      \"id\": 2,\r\n" + //
                        "      \"username\": \"johanp\",\r\n" + //
                        "      \"firstName\": \"Johan\",\r\n" + //
                        "      \"lastName\": \"Pieck\",\r\n" + //
                        "      \"email\": \"johan.pieck@ucll.be\",\r\n" + //
                        "      \"role\": \"lecturer\"\r\n" + //
                        "    },\r\n" + //
                        "    \"courses\": [\r\n" + //
                        "      {\r\n" + //
                        "        \"id\": 3,\r\n" + //
                        "        \"name\": \"Front-End Development\",\r\n" + //
                        "        \"description\": \"Learn how to build a front-end web application.\",\r\n" + //
                        "        \"phase\": 1,\r\n" + //
                        "        \"credits\": 6\r\n" + //
                        "      },\r\n" + //
                        "      {\r\n" + //
                        "        \"id\": 1,\r\n" + //
                        "        \"name\": \"Full-stack development\",\r\n" + //
                        "        \"description\": \"Learn how to build a full stack web application.\",\r\n" + //
                        "        \"phase\": 2,\r\n" + //
                        "        \"credits\": 6\r\n" + //
                        "      }\r\n" + //
                        "    ]\r\n" + //
                        "  },\r\n" + //
                        "  \"students\": []\r\n" + //
                        "}");
    }

    
}
