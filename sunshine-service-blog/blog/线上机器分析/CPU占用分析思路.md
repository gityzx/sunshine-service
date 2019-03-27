
# 一.查看文件大小 : du -h -s *
```aidl
app@stage-consumer05cn-t001:~/services/tomcat-wecash-primeloan-8280/logs$ du -h -s *
4.0K	catalina.2018-12-30.out
2.7M	catalina.2019-01-02.out
21M	catalina.2019-01-03.out
11M	catalina.2019-01-04.out
68K	catalina.2019-01-06.out
2.2M	catalina.2019-01-07.out
5.8M	catalina.2019-01-08.out
6.3M	catalina.2019-01-09.out
7.9M	catalina.2019-01-10.out
16K	juli.2018-04-02.log
44K	juli.2018-04-19.log
72K	juli.2018-05-02.log
144K	juli.2018-05-09.log
4.0K	juli.2018-05-10.log
4.0K	juli.2018-05-12.log
4.0K	juli.2018-05-13.log
4.0K	juli.2018-05-25.log
```




# 二.CPU占用分析
#### 定位PID
ps -ef |grep 8090

#### top命令
在linux环境下，可以通过top命令查看各个进程的cpu使用情况，默认按cpu使用率排序

#### top -Hp 'PID'
通过top -Hp PID 可以查看该进程下各个线程的cpu使用情况

#### jstack命令查看当前java进程的堆栈状态
通过top命令定位到cpu占用率较高的线程之后，继续使用jstack pid命令查看当前java进程的堆栈状态
jstack -l -m PID >> 123.txt

他们的含义如下：

-l long listings，会打印出额(防盗连接：本文首发自http://www.cnblogs.com/jilodream/ )外的锁信息，在发生死锁时可以用jstack -l pid来观察锁持有情况
-m mixed mode，不仅会输出Java堆栈信息，还会输出C/C++堆栈信息（比如Native方法）



jstack命令生成的thread dump信息包含了JVM中所有存活的线程，为了分析指定线程，必须找出对应线程的调用栈，应该如何找？

在top命令中，已经获取到了占用cpu资源较高的线程pid，将该pid转成16进制的值，在thread dump中每个线程都有一个nid，找到对应的nid即可；隔段时间再执行一次stack命令获取thread dump，区分两份dump是否有差别，在nid=0x246c的线程调用栈中，发现该线程一直在执行JstackCase类第33行的calculate方法，得到这个信息，就可以检查对应的代码是否有问题。

通过thread dump分析线程状态
除了上述的分析，大多数情况下会基于thead dump分析当前各个线程的运行情况，如是否存在死锁、是否存在一个线程长时间持有锁不放等等。

在dump中，线程一般存在如下几种状态：
1、RUNNABLE，线程处于执行中
2、BLOCKED，线程被阻塞
3、WAITING，线程正在等待



#### 如果连接不上,则看《jmap、jstack、jps无法连接jvm解决办法》
```aidl
app@stage-consumer17cn-t001:~/services/tomcat-wecash-primeloan-8280/logs$ jstack -l 31239 >>31239.sql
31239: Unable to open socket file: target process not responding or HotSpot VM not loaded
The -F option can be used when the target process is not responding
```


#### 参考
如何使用jstack分析线程状态:https://www.cnblogs.com/wuchanming/p/7766994.html
jmap、jstack、jps无法连接jvm解决办法:http://blog.51cto.com/zhangshaoxiong/1310166
