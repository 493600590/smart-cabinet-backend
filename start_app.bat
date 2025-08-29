@echo off
echo ========================================
echo 启动无人柜系统后端服务
echo ========================================

echo.
echo 正在启动应用程序...
echo 端口: 8000
echo 上下文路径: /api
echo.

if exist "target\smart-cabinet-backend-1.0.0.jar" (
    echo 找到jar文件，正在启动...
    java -jar target\smart-cabinet-backend-1.0.0.jar
) else (
    echo 错误: 找不到jar文件
    echo 请先编译项目
    pause
    exit /b 1
)

pause 