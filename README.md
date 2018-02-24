# 篮球借还系统



##Api接口

1. 用户登录
* 描述：控制用户登录的接口，使用md5加密方法
* URL：
* HTTP请求方式： POST
* 请求参数：
    {
        user:xxx,
        password:xxx
    }
* 参数说明：
    user:用户名
    password：密码
* 返回参数：
{
    data:0
}
* 参数说明：
    data：0为登录成功，1为不存在当前用户，2为用户密码不正确

2. 用户注册
* 描述：用户注册接口，使用md5加密方法
* URL：
* HTTP请求方式： POST
* 请求参数：
    {
        user:xxx,
        password:xxx
    }
* 参数说明：
    user:用户名
    password：密码
* 返回参数：
{
    data:0
}
* 参数说明：
    data：0为注册成功，1为注册失败