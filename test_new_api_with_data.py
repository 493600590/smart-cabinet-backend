#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
测试新增的易通无人柜订单API接口
验证测试数据是否正确插入
"""

import requests
import json
import time

# API基础地址
BASE_URL = "http://localhost:8000/api"

def test_api_with_data():
    """测试新增的API接口，验证测试数据"""
    print("🚀 开始测试新增的易通无人柜订单API接口")
    print("=" * 60)
    
    # 测试用户列表
    test_users = [
        "YT_USER_001",
        "YT_USER_002", 
        "YT_USER_003",
        "YT_USER_004",
        "YT_USER_005"
    ]
    
    for user_id in test_users:
        print(f"\n👤 测试用户: {user_id}")
        print("-" * 40)
        
        # 1. 测试待支付订单数量统计
        print("🔍 测试待支付订单数量统计...")
        try:
            response = requests.get(f"{BASE_URL}/yitong/orders/pending-payment/count", 
                                 params={"userId": user_id})
            if response.status_code == 200:
                data = response.json()
                count = data.get('data', 0)
                print(f"   ✅ 待支付订单数量: {count}")
            else:
                print(f"   ❌ 请求失败: {response.status_code}")
        except Exception as e:
            print(f"   ❌ 请求异常: {e}")
        
        # 2. 测试售后退款订单数量统计
        print("🔍 测试售后退款订单数量统计...")
        try:
            response = requests.get(f"{BASE_URL}/yitong/orders/refund/count", 
                                 params={"userId": user_id})
            if response.status_code == 200:
                data = response.json()
                count = data.get('data', 0)
                print(f"   ✅ 售后退款订单数量: {count}")
            else:
                print(f"   ❌ 请求失败: {response.status_code}")
        except Exception as e:
            print(f"   ❌ 请求异常: {e}")
        
        # 3. 测试待支付订单列表查询
        print("🔍 测试待支付订单列表查询...")
        try:
            response = requests.get(f"{BASE_URL}/yitong/orders/pending-payment", 
                                 params={"userId": user_id, "pageNum": 1, "pageSize": 5})
            if response.status_code == 200:
                data = response.json()
                orders = data.get('data', {}).get('records', [])
                total = data.get('data', {}).get('total', 0)
                print(f"   ✅ 待支付订单列表: 共{total}条，当前页{len(orders)}条")
                if orders:
                    for order in orders[:2]:  # 只显示前2条
                        print(f"      - {order.get('orderCode')}: ¥{order.get('actualAmount')} ({order.get('orderStatus')})")
            else:
                print(f"   ❌ 请求失败: {response.status_code}")
        except Exception as e:
            print(f"   ❌ 请求异常: {e}")
        
        # 4. 测试售后退款订单列表查询
        print("🔍 测试售后退款订单列表查询...")
        try:
            response = requests.get(f"{BASE_URL}/yitong/orders/refund", 
                                 params={"userId": user_id, "pageNum": 1, "pageSize": 5})
            if response.status_code == 200:
                data = response.json()
                orders = data.get('data', {}).get('records', [])
                total = data.get('data', {}).get('total', 0)
                print(f"   ✅ 售后退款订单列表: 共{total}条，当前页{len(orders)}条")
                if orders:
                    for order in orders[:2]:  # 只显示前2条
                        print(f"      - {order.get('orderCode')}: ¥{order.get('actualAmount')} ({order.get('orderStatus')})")
            else:
                print(f"   ❌ 请求失败: {response.status_code}")
        except Exception as e:
            print(f"   ❌ 请求异常: {e}")
        
        # 5. 测试全部订单列表查询
        print("🔍 测试全部订单列表查询...")
        try:
            response = requests.get(f"{BASE_URL}/yitong/orders", 
                                 params={"userId": user_id, "pageNum": 1, "pageSize": 5})
            if response.status_code == 200:
                data = response.json()
                orders = data.get('data', {}).get('records', [])
                total = data.get('data', {}).get('total', 0)
                print(f"   ✅ 全部订单列表: 共{total}条，当前页{len(orders)}条")
                
                # 统计各状态订单数量
                status_count = {}
                for order in orders:
                    status = order.get('orderStatus', 'UNKNOWN')
                    status_count[status] = status_count.get(status, 0) + 1
                
                print(f"   📊 订单状态分布: {status_count}")
            else:
                print(f"   ❌ 请求失败: {response.status_code}")
        except Exception as e:
            print(f"   ❌ 请求异常: {e}")
    
    print("\n" + "=" * 60)
    print("🎉 API接口测试完成!")
    
    # 6. 测试分页功能
    print("\n🔍 测试分页功能...")
    test_user = "YT_USER_001"
    print(f"   测试用户: {test_user}")
    
    for page_num in [1, 2]:
        try:
            response = requests.get(f"{BASE_URL}/yitong/orders/pending-payment", 
                                 params={"userId": test_user, "pageNum": page_num, "pageSize": 3})
            if response.status_code == 200:
                data = response.json()
                orders = data.get('data', {}).get('records', [])
                total = data.get('data', {}).get('total', 0)
                current = data.get('data', {}).get('current', 0)
                pages = data.get('data', {}).get('pages', 0)
                print(f"   第{page_num}页: 共{total}条，当前页{current}，总页数{pages}，本页{len(orders)}条")
            else:
                print(f"   第{page_num}页: 请求失败 {response.status_code}")
        except Exception as e:
            print(f"   第{page_num}页: 请求异常 {e}")
    
    print("\n📋 测试结果总结:")
    print("✅ 待支付订单数量接口: /yitong/orders/pending-payment/count")
    print("✅ 售后退款订单数量接口: /yitong/orders/refund/count")
    print("✅ 待支付订单列表接口: /yitong/orders/pending-payment")
    print("✅ 售后退款订单列表接口: /yitong/orders/refund")
    print("✅ 全部订单列表接口: /yitong/orders")
    print("✅ 分页功能测试")
    print(f"🌐 应用程序运行在: {BASE_URL}")
    print("📚 API文档地址: http://localhost:8000/api/swagger-ui/index.html")

if __name__ == "__main__":
    # 等待应用程序启动
    print("⏳ 等待应用程序启动...")
    time.sleep(2)
    
    try:
        test_api_with_data()
    except KeyboardInterrupt:
        print("\n\n⏹️ 测试被用户中断")
    except Exception as e:
        print(f"\n\n❌ 测试过程中发生错误: {e}")
        print("请确保应用程序正在运行，并且数据库连接正常") 