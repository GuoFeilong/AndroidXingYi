# AndroidXingYi
Android开发框架
#结构预览


***
	Project
	 |
	 |
	 |
	 |
	 |_______Trunk业务线____MVP分层
	 |
	 |
	 |_______PaymentLib(最终以AAR提供给Trunk)
	 |
	 |
	 |_______CommonLib(最终以AAR提供给Trunk)
	              |
	              |
	      _________________
	     |        |        |
	     UI       NET     依赖	     
	     |        |         |
	  _______ RX + Retrofit ___________________
     |       |             |     |    |       | 
    Xrecle  SwapBack      Glide 注解  6.0权限 Logger  
    
    
    






***
#Project目录下的
###1. gradle配置多渠道打包
###2. 集成内存泄漏分析leakcanary
###3. gradle配置全局的API访问地址
###4. gradle配置动态的版本名字和版本号
###5. 签名文件的生成和ReadMe
###6. 全局Debug开关透传Lib中的logger
###7. 集成crash分析sdk


***

#JAVA代码
###1. MVP分层
###2. UI-->Activity-->Fragment-->CustomView-->Adpaters
###3. 业务模型-->业务接口-->View层接口
###4. 通用标题加底部栏目的activity抽取,支持设置滑动关闭
###5. 通用fragment抽取
###6. ButterKnife的基类只要绑定一次即可
###7. MVP分层以及本地数据MVP和网络MVP实例代码

***

#CommonLib通用类库
###1. 下拉刷新XReclerView
###2. ButterKife注解
###3. Android L新控件和风格
###4. 网络层封装Retrofit + RxJAVA
###5. 通用的SwapBack继承,重写setContentView,处理统一错误页面
###6. 通用的fragment基础,正确的传参以及fragment的生命周期问题
###7. 图片处理Glide
###8. Android6.0权限适配<考虑RxPermission>
###9. 通用的LOG工具类,根据debug类型控制输出,通用的request和response加入log控制方便查看请求


#工具类系列:
###1. 线程工具类
###2. 屏幕工具类,尺寸转换等
###3. 联系人工具类
###4. MD5校验工具类
###5. 文件操作工具类
###6. SP操作工具类
###7. 版本更新工具类
###8. 网络监控工具类
###9. 字符串操作工具类拼接等
###10. Android软键盘工具类
###11. 定位工具类
###12. Android设备信息系统信息工具类
###13. CollectionUtil集合工具类

***

#代码规范类:
###1. 驼峰命名规则
###2. xml中layout规范
###3. drawable中shape规范,icon命名规范
###4. 代码异常捕捉以及遍历规范,对象判空使用

***
###预览图:
![这里写图片描述](http://img.blog.csdn.net/20161113231709672)

图中展示的上部分标题栏和下面底部TAB栏目全部可以自定义配置,
具体根据需求修改代码即可,所有业务代码全部在presenter里面

![这里写图片描述](http://img.blog.csdn.net/20161113232029948)

###以后根据自己的业务线增加对应的presenter即可,方便和activity解耦












                                 
***
#*源代码下载地址*:[https://github.com/GuoFeilong/AndroidXingYi](https://github.com/GuoFeilong/AndroidXingYi)

#请多多star谢谢!!