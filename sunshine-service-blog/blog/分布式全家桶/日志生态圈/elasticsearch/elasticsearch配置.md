
## 详细的配置:
```$xslt

elasticsearch-.yml（中文配置详解）

# ======================== Elasticsearch Configuration =========================
#
# NOTE: Elasticsearch comes with reasonable defaults for most settings.
# Before you set out to tweak and tune the configuration, make sure you
# understand what are you trying to accomplish and the consequences.
#
# The primary way of configuring a node is via this file. This template lists
# the most important settings you may want to configure for a production cluster.
#
# Please see the documentation for further information on configuration options:
# <http://www.elastic.co/guide/en/elasticsearch/reference/current/setup-configuration.html>
#
# ---------------------------------- Cluster -----------------------------------
#
# Use a descriptive name for your cluster:
# 集群名称，默认是elasticsearch
# cluster.name: my-application
#
# ------------------------------------ Node ------------------------------------
#
# Use a descriptive name for the node:
# 节点名称，默认从elasticsearch-2.4.3/lib/elasticsearch-2.4.3.jar!config/names.txt中随机选择一个名称
# node.name: node-1
#
# Add custom attributes to the node:
# 
# node.rack: r1
#
# ----------------------------------- Paths ------------------------------------
#
# Path to directory where to store the data (separate multiple locations by comma):
# 可以指定es的数据存储目录，默认存储在es_home/data目录下
# path.data: /path/to/data
#
# Path to log files:
# 可以指定es的日志存储目录，默认存储在es_home/logs目录下
# path.logs: /path/to/logs
#
# ----------------------------------- Memory -----------------------------------
#
# Lock the memory on startup:
# 锁定物理内存地址，防止elasticsearch内存被交换出去,也就是避免es使用swap交换分区
# bootstrap.memory_lock: true
#
#
#
# 确保ES_HEAP_SIZE参数设置为系统可用内存的一半左右
# Make sure that the `ES_HEAP_SIZE` environment variable is set to about half the memory
# available on the system and that the owner of the process is allowed to use this limit.
# 
# 当系统进行内存交换的时候，es的性能很差
# Elasticsearch performs poorly when the system is swapping the memory.
#
# ---------------------------------- Network -----------------------------------
#
#
# 为es设置ip绑定，默认是127.0.0.1，也就是默认只能通过127.0.0.1 或者localhost才能访问
# es1.x版本默认绑定的是0.0.0.0 所以不需要配置，但是es2.x版本默认绑定的是127.0.0.1，需要配置
# Set the bind address to a specific IP (IPv4 or IPv6):
#
# network.host: 192.168.0.1
#
#
# 为es设置自定义端口，默认是9200
# 注意：在同一个服务器中启动多个es节点的话，默认监听的端口号会自动加1：例如：9200，9201，9202...
# Set a custom port for HTTP:
#
# http.port: 9200
#
# For more information, see the documentation at:
# <http://www.elastic.co/guide/en/elasticsearch/reference/current/modules-network.html>
#
# --------------------------------- Discovery ----------------------------------
#
# 当启动新节点时，通过这个ip列表进行节点发现，组建集群
# 默认节点列表：
# 127.0.0.1，表示ipv4的回环地址。
#	[::1]，表示ipv6的回环地址
#
# 在es1.x中默认使用的是组播(multicast)协议，默认会自动发现同一网段的es节点组建集群，
# 在es2.x中默认使用的是单播(unicast)协议，想要组建集群的话就需要在这指定要发现的节点信息了。
# 注意：如果是发现其他服务器中的es服务，可以不指定端口[默认9300]，如果是发现同一个服务器中的es服务，就需要指定端口了。
# Pass an initial list of hosts to perform discovery when new node is started:
# 
# The default list of hosts is ["127.0.0.1", "[::1]"]
#
# discovery.zen.ping.unicast.hosts: ["host1", "host2"]
#
#
#
#
# 通过配置这个参数来防止集群脑裂现象 (集群总节点数量/2)+1
# Prevent the "split brain" by configuring the majority of nodes (total number of nodes / 2 + 1):
#
# discovery.zen.minimum_master_nodes: 3
#
# For more information, see the documentation at:
# <http://www.elastic.co/guide/en/elasticsearch/reference/current/modules-discovery.html>
#
# ---------------------------------- Gateway -----------------------------------
#
# Block initial recovery after a full cluster restart until N nodes are started:
# 一个集群中的N个节点启动后,才允许进行数据恢复处理，默认是1
# gateway.recover_after_nodes: 3
#
# For more information, see the documentation at:
# <http://www.elastic.co/guide/en/elasticsearch/reference/current/modules-gateway.html>
#
# ---------------------------------- Various -----------------------------------
# 在一台服务器上禁止启动多个es服务
# Disable starting multiple nodes on a single system:
#
# node.max_local_storage_nodes: 1
#
# 设置是否可以通过正则或者_all删除或者关闭索引库，默认true表示必须需要显式指定索引库名称
# 生产环境建议设置为true，删除索引库的时候必须显式指定，否则可能会误删索引库中的索引库。
# Require explicit names when deleting indices:
#
# action.destructive_requires_name: true


```

## 参考:
中文配置详解:https://www.cnblogs.com/zlslch/p/6419948.html

elasticsearch.yml配置详解:https://blog.csdn.net/Ka_Ka314/article/details/79284242












#IP绑定的有问题，
```aidl

org.elasticsearch.transport.BindTransportException: Failed to bind to [9310]
        at org.elasticsearch.transport.TcpTransport.bindToPort(TcpTransport.java:794) ~[elasticsearch-6.2.4.jar:6.2.4]
        at org.elasticsearch.transport.TcpTransport.bindServer(TcpTransport.java:759) ~[elasticsearch-6.2.4.jar:6.2.4]
        at org.elasticsearch.transport.netty4.Netty4Transport.doStart(Netty4Transport.java:135) ~[?:?]
        at org.elasticsearch.common.component.AbstractLifecycleComponent.start(AbstractLifecycleComponent.java:66) ~[elasticsearch-6.2.4.jar:6.2.4]
        at org.elasticsearch.transport.TransportService.doStart(TransportService.java:213) ~[elasticsearch-6.2.4.jar:6.2.4]
        at org.elasticsearch.common.component.AbstractLifecycleComponent.start(AbstractLifecycleComponent.java:66) ~[elasticsearch-6.2.4.jar:6.2.4]
        at org.elasticsearch.node.Node.start(Node.java:623) ~[elasticsearch-6.2.4.jar:6.2.4]
        at org.elasticsearch.bootstrap.Bootstrap.start(Bootstrap.java:262) ~[elasticsearch-6.2.4.jar:6.2.4]
        at org.elasticsearch.bootstrap.Bootstrap.init(Bootstrap.java:332) [elasticsearch-6.2.4.jar:6.2.4]
        at org.elasticsearch.bootstrap.Elasticsearch.init(Elasticsearch.java:121) [elasticsearch-6.2.4.jar:6.2.4]
        at org.elasticsearch.bootstrap.Elasticsearch.execute(Elasticsearch.java:112) [elasticsearch-6.2.4.jar:6.2.4]
        at org.elasticsearch.cli.EnvironmentAwareCommand.execute(EnvironmentAwareCommand.java:86) [elasticsearch-6.2.4.jar:6.2.4]
        at org.elasticsearch.cli.Command.mainWithoutErrorHandling(Command.java:124) [elasticsearch-cli-6.2.4.jar:6.2.4]
        at org.elasticsearch.cli.Command.main(Command.java:90) [elasticsearch-cli-6.2.4.jar:6.2.4]
        at org.elasticsearch.bootstrap.Elasticsearch.main(Elasticsearch.java:92) [elasticsearch-6.2.4.jar:6.2.4]
        at org.elasticsearch.bootstrap.Elasticsearch.main(Elasticsearch.java:85) [elasticsearch-6.2.4.jar:6.2.4]
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
        at java.lang.Thread.run(Thread.java:748) ~[?:1.8.0_191]
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
[2019-01-09T22:30:10,327][INFO ][o.e.n.Node               ] [es1] stopping ...
[2019-01-09T22:30:10,328][INFO ][o.e.n.Node               ] [es1] stopped
[2019-01-09T22:30:10,329][INFO ][o.e.n.Node               ] [es1] closing ...
[2019-01-09T22:30:10,335][INFO ][o.e.n.Node               ] [es1] closed
```





# 解决方案


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