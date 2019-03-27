#### 问题一：警告提示
```aidl

elasticsearch 5.0 安装过程中遇到了一些问题，通过查找资料几乎都解决掉了，这里简单记录一下 ，供以后查阅参考，也希望可以帮助遇到同样问题的你。



[2016-11-06T16:27:21,712][WARN ][o.e.b.JNANatives ] unable to install syscall filter: 

java.lang.UnsupportedOperationException: seccomp unavailable: requires kernel 3.5+ with CONFIG_SECCOMP and CONFIG_SECCOMP_FILTER compiled in
at org.elasticsearch.bootstrap.Seccomp.linuxImpl(Seccomp.java:349) ~[elasticsearch-5.0.0.jar:5.0.0]
at org.elasticsearch.bootstrap.Seccomp.init(Seccomp.java:630) ~[elasticsearch-5.0.0.jar:5.0.0]

报了一大串错误，其实只是一个警告。

解决：使用心得linux版本，就不会出现此类问题了。

 ```
 


#### 问题二：ERROR: bootstrap checks failed
```aidl

max file descriptors [4096] for elasticsearch process likely too low, increase to at least [65536]
max number of threads [1024] for user [lishang] likely too low, increase to at least [2048]

解决：切换到root用户，编辑limits.conf 添加类似如下内容

vi /etc/security/limits.conf 

添加如下内容:

* soft nofile 65536

* hard nofile 131072

* soft nproc 2048

* hard nproc 4096


注意: 此文件修改后需要重新登录用户，切换到es的用户,才会生效。

 ```

#### 问题三：max number of threads [1024] for user [lish] likely too low, increase to at least [2048]


```aidl


解决：切换到root用户，进入limits.d目录下修改配置文件。

vi /etc/security/limits.d/90-nproc.conf 

修改如下内容：

* soft nproc 1024

#修改为

* soft nproc 2048


注意: 此文件修改后需要重新登录用户，切换到es的用户,才会生效。


 ```
 
 

#### 问题四：max virtual memory areas vm.max_map_count [65530] likely too low, increase to at least [262144]

```aidl


解决：切换到root用户修改配置sysctl.conf

vi /etc/sysctl.conf 

添加下面配置：

vm.max_map_count=655360

并执行命令：

sysctl -p

注意: 此文件修改后需要重新登录用户，切换到es的用户,才会生效。


```


 

#### 问题五：max file descriptors [4096] for elasticsearch process likely too low, increase to at least [65536]

```aidl

解决：修改切换到root用户修改配置limits.conf 添加下面两行

命令:vi /etc/security/limits.conf

*        hard    nofile           65536
*        soft    nofile           65536

注意: 此文件修改后需要重新登录用户，切换到es的用户,才会生效。

 

然后，重新启动elasticsearch，即可启动成功。
```
#### 六、后台运行
```aidl


最后还有一个小问题，如果你在服务器上安装Elasticsearch，而你想在本地机器上进行开发，这时候，你很可能需要在关闭终端的时候，让Elasticsearch继续保持运行。最简单的方法就是使用nohup。先按Ctrl + C，停止当前运行的Elasticsearch，改用下面的命令运行Elasticsearch

nohup./bin/elasticsearch&

这样，你就可以放心地关闭服务器终端，而不用担心Elasticsearch也跟着关闭了。

业务驱动技术，技术是手段，业务是目的。
```


#### 七、 org.elasticsearch.bootstrap.StartupException: BindTransportException[Failed to bind to [9310]]; nested: BindException[无法指定被请求的地址];

```aidl

解决方法：

这两个IP都用内网IP,而不是外网IP
network.host: 192.168.1.168
discovery.zen.ping.unicast.hosts: ["192.168.1.168:9310","192.168.1.168:9320","192.168.1.168:9330"]


[2019-01-09T22:30:09,418][WARN ][o.e.b.ElasticsearchUncaughtExceptionHandler] [es1] uncaught exception in thread [main]
org.elasticsearch.bootstrap.StartupException: BindTransportException[Failed to bind to [9310]]; nested: BindException[无法指定被请求的地址];
        at org.elasticsearch.bootstrap.Elasticsearch.init(Elasticsearch.java:125) ~[elasticsearch-6.2.4.jar:6.2.4]
        at org.elasticsearch.bootstrap.Elasticsearch.execute(Elasticsearch.java:112) ~[elasticsearch-6.2.4.jar:6.2.4]
        at org.elasticsearch.cli.EnvironmentAwareCommand.execute(EnvironmentAwareCommand.java:86) ~[elasticsearch-6.2.4.jar:6.2.4]
        at org.elasticsearch.cli.Command.mainWithoutErrorHandling(Command.java:124) ~[elasticsearch-cli-6.2.4.jar:6.2.4]
        at org.elasticsearch.cli.Command.main(Command.java:90) ~[elasticsearch-cli-6.2.4.jar:6.2.4]
        at org.elasticsearch.bootstrap.Elasticsearch.main(Elasticsearch.java:92) ~[elasticsearch-6.2.4.jar:6.2.4]
        at org.elasticsearch.bootstrap.Elasticsearch.main(Elasticsearch.java:85) ~[elasticsearch-6.2.4.jar:6.2.4]
Caused by: org.elasticsearch.transport.BindTransportException: Failed to bind to [9310]
        at org.elasticsearch.transport.TcpTransport.bindToPort(TcpTransport.java:794) ~[elasticsearch-6.2.4.jar:6.2.4]
        at org.elasticsearch.transport.TcpTransport.bindServer(TcpTransport.java:759) ~[elasticsearch-6.2.4.jar:6.2.4]
        at org.elasticsearch.transport.netty4.Netty4Transport.doStart(Netty4Transport.java:135) ~[?:?]
        at org.elasticsearch.common.component.AbstractLifecycleComponent.start(AbstractLifecycleComponent.java:66) ~[elasticsearch-6.2.4.jar:6.2.4]
        at org.elasticsearch.transport.TransportService.doStart(TransportService.java:213) ~[elasticsearch-6.2.4.jar:6.2.4]
        at org.elasticsearch.common.component.AbstractLifecycleComponent.start(AbstractLifecycleComponent.java:66) ~[elasticsearch-6.2.4.jar:6.2.4]
        at org.elasticsearch.node.Node.start(Node.java:623) ~[elasticsearch-6.2.4.jar:6.2.4]
        at org.elasticsearch.bootstrap.Bootstrap.start(Bootstrap.java:262) ~[elasticsearch-6.2.4.jar:6.2.4]
        at org.elasticsearch.bootstrap.Bootstrap.init(Bootstrap.java:332) ~[elasticsearch-6.2.4.jar:6.2.4]
        at org.elasticsearch.bootstrap.Elasticsearch.init(Elasticsearch.java:121) ~[elasticsearch-6.2.4.jar:6.2.4]
        ... 6 more
Caused by: java.net.BindException: 无法指定被请求的地址
        at sun.nio.ch.Net.bind0(Native Method) ~[?:?]
        at sun.nio.ch.Net.bind(Net.java:433) ~[?:?]
        at sun.nio.ch.Net.bind(Net.java:425) ~[?:?]
        at sun.nio.ch.ServerSocketChannelImpl.bind(ServerSocketChannelImpl.java:223) ~[?:?]
        at io.netty.channel.socket.nio.NioServerSocketChannel.doBind(NioServerSocketChannel.java:128) ~[?:?]
        at io.netty.channel.AbstractChannel$AbstractUnsafe.bind(AbstractChannel.java:558) ~[?:?]
        at io.netty.channel.DefaultChannelPipeline$HeadContext.bind(DefaultChannelPipeline.java:1283) ~[?:?]
        at io.netty.channel.AbstractChannelHandlerContext.invokeBind(AbstractChannelHandlerContext.java:501) ~[?:?]
        at io.netty.channel.AbstractChannelHandlerContext.bind(AbstractChannelHandlerContext.java:486) ~[?:?]
        at io.netty.channel.DefaultChannelPipeline.bind(DefaultChannelPipeline.java:989) ~[?:?]
        at io.netty.channel.AbstractChannel.bind(AbstractChannel.java:254) ~[?:?]
        at io.netty.bootstrap.AbstractBootstrap$2.run(AbstractBootstrap.java:365) ~[?:?]
        at io.netty.util.concurrent.AbstractEventExecutor.safeExecute(AbstractEventExecutor.java:163) ~[?:?]
        at io.netty.util.concurrent.SingleThreadEventExecutor.runAllTasks(SingleThreadEventExecutor.java:403) ~[?:?]
        at io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:463) ~[?:?]
        at io.netty.util.concurrent.SingleThreadEventExecutor$5.run(SingleThreadEventExecutor.java:858) ~[?:?]
        at java.lang.Thread.run(Thread.java:748) [?:1.8.0_191]

```



## 参考:

http://www.cnblogs.com/sloveling/p/elasticsearch.html

https://blog.csdn.net/qq_32690999/article/details/78505533