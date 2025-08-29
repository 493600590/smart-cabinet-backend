# Update jar file with new class files
Write-Host "Updating jar file..." -ForegroundColor Green

# Define classes to update
$classesToUpdate = @(
    "com\smartcabinet\controller\YitongOrderController.class",
    "com\smartcabinet\common\result\Result.class",
    "com\smartcabinet\common\result\ResultCode.class",
    "com\smartcabinet\mapper\YitongOrderMapper.class",
    "com\smartcabinet\mapper\YitongOrderItemMapper.class",
    "com\smartcabinet\entity\YitongOrder.class",
    "com\smartcabinet\entity\YitongOrderItem.class"
)

# Update each class file
foreach ($classFile in $classesToUpdate) {
    $fullPath = "target\classes\$classFile"
    if (Test-Path $fullPath) {
        Write-Host "Updating: $classFile" -ForegroundColor Yellow
        jar --update --file target\smart-cabinet-backend-1.0.0.jar -C target\classes $classFile
        if ($LASTEXITCODE -eq 0) {
            Write-Host "Success: $classFile" -ForegroundColor Green
        } else {
            Write-Host "Failed: $classFile" -ForegroundColor Red
        }
    } else {
        Write-Host "File not found: $fullPath" -ForegroundColor Red
    }
}

Write-Host "Jar file update completed!" -ForegroundColor Green
Write-Host "Now you can start the application" -ForegroundColor Cyan 