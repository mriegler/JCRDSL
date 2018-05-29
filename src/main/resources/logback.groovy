appender("FILE", FileAppender) {
    file = "testFile.log"
    append = true
    encoder(PatternLayoutEncoder) {
        pattern = "%level %logger - %msg%n"
    }
}

appender("CONSOLE", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"
    }
}

root(WARN, ["CONSOLE"])
logger("com.github.mriegler", DEBUG, ["CONSOLE"])