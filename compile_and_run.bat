@echo off
echo ========================================
echo 无人柜系统后端服务编译和启动脚本
echo ========================================

echo.
echo [1/4] 检查Java环境...
java -version
if %errorlevel% neq 0 (
    echo 错误: Java未安装或不在PATH中
    pause
    exit /b 1
)

echo.
echo [2/4] 检查Maven环境...
mvn --version >nul 2>&1
if %errorlevel% neq 0 (
    echo 警告: Maven未安装，尝试使用现有jar文件
    goto run_existing_jar
)

echo.
echo [3/4] 使用Maven编译项目...
mvn clean compile package -DskipTests
if %errorlevel% neq 0 (
    echo 错误: Maven编译失败
    pause
    exit /b 1
)

:run_existing_jar
echo.
echo [4/4] 启动应用程序...
if exist "target\smart-cabinet-backend-1.0.0.jar" (
    echo 找到已编译的jar文件，正在启动...
    java -jar target\smart-cabinet-backend-1.0.0.jar
) else (
    echo 错误: 找不到可执行的jar文件
    echo 请先使用Maven编译项目: mvn clean package
    pause
    exit /b 1
)

pause 