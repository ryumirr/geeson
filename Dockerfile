# syntax=docker/dockerfile:1
FROM amazoncorretto:21

# 앱 작업 디렉토리 설정
WORKDIR /app

# 디버깅: JAR_FILE 값 확인
ARG JAR_FILE=${JAR_DIR}/${JAR_FILE}
COPY ${JAR_FILE} app.jar

# 포트 오픈 (Spring Boot + gRPC)
EXPOSE 8080 6565

# 앱 실행
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=stage"]
