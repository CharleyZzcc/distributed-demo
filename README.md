# distributed-demo
分布式小demo

## 目的 
主要测试基于redis的分布式session和分布式锁

## 附加
使用aop来解决登录权限校验；测试基于redis的缓存

## 使用概要
1.事先安装好redis，并在application.yml中配置用户名和密码（如果有的话）；<br>
2.因为只是简单测试，没有实际页面，所有请求都是直接输出字符串到浏览器；<br>
3.没有使用数据库，测试登录所需的账号密码在“com.lzc.demo.sys.user.service.impl.UserServiceImpl.java”中；<br>
4.访问路径：<br>
&emsp;1)假装访问登录页<br>
&emsp;&emsp;http://localhost:端口/distributed-demo/user/loginPage<br>
&emsp;2)测试登录，为了方便，直接用get请求（分布式session部分）；成功的话，将跳转到首页<br>
&emsp;&emsp;http://localhost:端口/distributed-demo/user/login?username=admin&password=admin123<br>
&emsp;3)假装是首页，输出“欢迎访问首页，当前登录用户：admin”；没有登录将会被拦截，跳转到“登录页”<br>
&emsp;&emsp;http://localhost:端口/distributed-demo/index/index<br>
&emsp;4)秒杀服务-查询<br>
&emsp;&emsp;http://localhost:端口/distributed-demo/kill/query/p123456<br>
&emsp;5)秒杀服务-下单，使用到分布式锁；有提供其它实现（基于synchronized和Lock，便于比较）<br>
&emsp;&emsp;http://localhost:端口/distributed-demo/kill/shopping/p123456<br>
&emsp;6)redis缓存的获取、更新和清除<br>
&emsp;&emsp;http://localhost:端口/distributed-demo/cache/get<br>
&emsp;&emsp;http://localhost:端口/distributed-demo/cache/update<br>
&emsp;&emsp;http://localhost:端口/distributed-demo/cache/clear<br>