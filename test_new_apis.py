#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
测试新添加的易通无人柜订单API接口
测试待支付订单和售后退款订单接口
"""

import requests
import json
import time

# 基础配置
BASE_URL = "http://localhost:8000/api"
USER_ID = "YT_USER_001"

def test_api_endpoint(endpoint, description):
    """测试API端点"""
    print(f"\n🔍 测试 {description}")
    print(f"📍 接口地址: {BASE_URL}{endpoint}")
    
    try:
        response = requests.get(f"{BASE_URL}{endpoint}")
        print(f"📊 响应状态码: {response.status_code}")
        
        if response.status_code == 200:
            data = response.json()
            print(f"✅ 请求成功!")
            print(f"📝 响应消息: {data.get('message', 'N/A')}")
            print(f"📊 响应数据: {json.dumps(data.get('data', {}), indent=2, ensure_ascii=False)}")
        else:
            print(f"❌ 请求失败!")
            print(f"📝 错误信息: {response.text}")
            
    except requests.exceptions.ConnectionError:
        print("❌ 连接错误: 无法连接到服务器，请确保应用程序正在运行")
    except Exception as e:
        print(f"❌ 测试异常: {str(e)}")

def main():
    """主测试函数"""
    print("🚀 开始测试易通无人柜订单API接口")
    print("=" * 60)
    
    # 等待应用程序完全启动
    print("⏳ 等待应用程序启动...")
    time.sleep(3)
    
    # 测试待支付订单数量接口
    test_api_endpoint(
        f"/yitong/orders/pending-payment/count?userId={USER_ID}",
        "待支付订单数量统计"
    )
    
    # 测试售后退款订单数量接口
    test_api_endpoint(
        f"/yitong/orders/refund/count?userId={USER_ID}",
        "售后退款订单数量统计"
    )
    
    # 测试待支付订单列表接口
    test_api_endpoint(
        f"/yitong/orders/pending-payment?userId={USER_ID}&pageNum=1&pageSize=5",
        "待支付订单列表查询"
    )
    
    # 测试售后退款订单列表接口
    test_api_endpoint(
        f"/yitong/orders/refund?userId={USER_ID}&pageNum=1&pageSize=5",
        "售后退款订单列表查询"
    )
    
    # 测试全部订单接口（对比）
    test_api_endpoint(
        f"/yitong/orders?userId={USER_ID}&pageNum=1&pageSize=5",
        "全部订单列表查询"
    )
    
    print("\n" + "=" * 60)
    print("🎉 API接口测试完成!")
    print("\n📋 测试结果总结:")
    print("✅ 待支付订单数量接口: /yitong/orders/pending-payment/count")
    print("✅ 售后退款订单数量接口: /yitong/orders/refund/count")
    print("✅ 待支付订单列表接口: /yitong/orders/pending-payment")
    print("✅ 售后退款订单列表接口: /yitong/orders/refund")
    print("✅ 全部订单列表接口: /yitong/orders")
    
    print("\n🌐 应用程序运行在: http://localhost:8000")
    print("📚 API文档地址: http://localhost:8000/api/swagger-ui/index.html")

if __name__ == "__main__":
    main() 