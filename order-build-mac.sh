#!/bin/bash

# ================================
# 인자 확인
# ================================
if [ -z "$1" ]; then
  echo ":x: 태그 이름이 필요합니다."
  exit 1
fi

# ================================
# 인자 설정
# ================================
JAR_PATH=/api/order-api/build/libs/order-api-0.0.1.jar
IMAGE_NAME=jeongjin984/order-api
TAG=$1
CONTAINER_NAME="${IMAGE_NAME}-container"

# JAR 파일 이름 추출
JAR_NAME=$(basename "$JAR_PATH")

# ================================
# Gradle 빌드
# ================================
echo ":hammer: Gradle 빌드 중..."
./gradlew clean :api:order-api:build -x test --refresh-dependencies
if [ $? -ne 0 ]; then
  echo ":x: Gradle build 실패."
  exit 1
fi

# ================================
# Docker 이미지 빌드 (Mac용, 로컬 아키텍처)
# ================================
echo ":whale: Mac용 Docker 이미지 빌드 중..."
docker build \
  --build-arg JAR_FILE="$JAR_PATH" \
  -t "$IMAGE_NAME:$TAG" .

if [ $? -ne 0 ]; then
  echo ":x: Docker build 실패."
  exit 1
fi

read -p ":white_check_mark: 완료! Mac용 로컬 Docker 이미지가 생성되었습니다. 엔터를 눌러 종료합니다..."
