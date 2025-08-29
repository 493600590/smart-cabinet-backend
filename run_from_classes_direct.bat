@echo off
echo ========================================
echo 无人柜系统后端服务 - 从classes目录启动
echo ========================================

echo.
echo [1/3] 检查Java环境...
java -version
if %errorlevel% neq 0 (
    echo 错误: Java未安装或不在PATH中
    pause
    exit /b 1
)

echo.
echo [2/3] 设置classpath...
set CLASSPATH=target\classes;target\dependency\*

echo.
echo [3/3] 启动应用程序...
echo 使用classpath: %CLASSPATH%
echo 启动主类: com.smartcabinet.SmartCabinetApplication
echo.

java -cp "%CLASSPATH%" com.smartcabinet.SmartCabinetApplication

pause 