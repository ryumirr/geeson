@echo off
setlocal

:: ================================
:: 인자 확인
:: ================================
if "%~1"=="" (
    echo ❌ 사용법: build.bat [JAR 경로] [이미지 이름] [태그]
    echo 예:        build.bat api\build\libs\api-0.0.1-SNAPSHOT.jar my-spring-api latest
    exit /b 1
)

if "%~2"=="" (
    echo ❌ 이미지 이름이 필요합니다.
    exit /b 1
)

if "%~3"=="" (
    echo ❌ 태그 이름이 필요합니다.
    exit /b 1
)

:: ================================
:: 인자 설정
:: ================================
set JAR_PATH=%~1
set IMAGE_NAME=%~2
set TAG=%~3
set CONTAINER_NAME=%IMAGE_NAME%-container

:: JAR 파일 이름 추출
for %%F in (%JAR_PATH%) do set JAR_NAME=%%~nxF

:: ================================
:: Docker 이미지 빌드
:: ================================
echo 📦 Docker 이미지 빌드 중...
docker build --build-arg JAR_FILE=%JAR_PATH% -t %IMAGE_NAME%:%TAG% .

if %errorlevel% neq 0 (
    echo ❌ Docker build 실패.
    exit /b %errorlevel%
)

:: ================================
:: 기존 컨테이너 제거
:: ================================
echo 🧹 기존 컨테이너 제거 중...
docker rm -f %CONTAINER_NAME% 2>nul

:: ================================
:: 컨테이너 실행
:: ================================
echo 🚀 컨테이너 실행 중...
docker run -d --name %CONTAINER_NAME% -p 8080:8080 %IMAGE_NAME%:%TAG%

echo ✅ Docker container '%CONTAINER_NAME%' is running with image '%IMAGE_NAME%:%TAG%' on port 8080.

endlocal
pause