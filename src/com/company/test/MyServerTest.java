package com.company.test;

import com.company.core.MyServer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class MyServerTest{

    @Test
    void getFilePath() {
        Assertions.assertEquals(MyServer.getFilePath("./src/com/company/resources/index.html"), MyServer.getFilePath("./src/com/company/resources/index.html"));
    }

    @Test
    void guessContentType() throws IOException {
        Path filePath = Paths.get("./src/com/company/resources/index.html");
        assertEquals(MyServer.guessContentType(filePath), MyServer.guessContentType(filePath));
    }
}