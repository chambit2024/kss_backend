FROM bellsoft/liberica-openjdk-alpine:17 AS builder

CMD ["./gradlew", "clean"]

VOLUME /tmp

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +x ./gradlew

#build/libs 디렉토리에 JAR 파일 생성
RUN ./gradlew bootJar

FROM bellsoft/liberica-openjdk-alpine:17
COPY --from=builder build/libs/*.jar app.jar

EXPOSE 8080

# bootJar을 통해 생성된 이미지를 최종 이미지로 복사
ENTRYPOINT ["java", "-jar", "/app.jar"]