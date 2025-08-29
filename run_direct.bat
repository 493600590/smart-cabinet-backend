@echo off
echo ========================================
echo 直接从classes目录启动应用程序
echo ========================================

echo.
echo 正在启动应用程序...
echo 端口: 8000
echo 上下文路径: /api
echo.

if exist "target\classes" (
    echo 找到编译后的classes目录，正在启动...
    
    REM 设置classpath，包含所有必要的依赖
    set CLASSPATH=target\classes
    
    REM 启动Spring Boot应用
    java -cp "%CLASSPATH%" com.smartcabinet.SmartCabinetApplication
) else (
    echo 错误: 找不到编译后的classes目录
    echo 请先编译项目
    pause
    exit /b 1
)

pause 