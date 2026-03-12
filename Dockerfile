# syntax=docker/dockerfile:1
# 사용하던 jdk의 버전 호환이 안되므로 다른 버전으로 변경 
FROM eclipse-temurin:21-jdk

# flentd 설정
RUN mkdir /var/log/app
ENV FLUENTD_HOST=fluentd
ENV FLUENTD_PORT=24224

# 앱 작업 디렉토리 설정
WORKDIR /app

# 디버깅: JAR_FILE 값 확인
ARG JAR_FILE=${JAR_DIR}/${JAR_FILE}
COPY ${JAR_FILE} app.jar

# 포트 오픈 (Spring Boot + gRPC)
EXPOSE 8080 6565

# 앱 실행
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=local"]

