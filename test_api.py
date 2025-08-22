import requests
import json

# 测试订单API接口
base_url = "http://localhost:8080/api"

def test_get_user_orders():
    """测试获取用户订单列表"""
    url = f"{base_url}/yitong/orders"
    params = {
        "userId": "USER001",
        "pageNum": 1,
        "pageSize": 10
    }
    
    try:
        response = requests.get(url, params=params)
        print(f"状态码: {response.status_code}")
        print(f"响应内容: {response.text}")
        
        if response.status_code == 200:
            data = response.json()
            print(f"成功获取订单数据: {json.dumps(data, indent=2, ensure_ascii=False)}")
        else:
            print(f"请求失败: {response.text}")
            
    except requests.exceptions.ConnectionError:
        print("连接失败，请检查服务是否启动")
    except Exception as e:
        print(f"请求异常: {e}")

def test_get_order_detail():
    """测试获取订单详情"""
    url = f"{base_url}/yitong/orders/detail/ORD001"
    
    try:
        response = requests.get(url)
        print(f"\n订单详情状态码: {response.status_code}")
        print(f"订单详情响应: {response.text}")
        
        if response.status_code == 200:
            data = response.json()
            print(f"成功获取订单详情: {json.dumps(data, indent=2, ensure_ascii=False)}")
        else:
            print(f"获取订单详情失败: {response.text}")
            
    except requests.exceptions.ConnectionError:
        print("连接失败，请检查服务是否启动")
    except Exception as e:
        print(f"请求异常: {e}")

if __name__ == "__main__":
    print("开始测试易通订单API接口...")
    print("=" * 50)
    
    test_get_user_orders()
    test_get_order_detail() 