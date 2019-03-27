
## 单机多节点 es1,es2,es3:
```$xslt
[pangolder@13141308437 config]$ cat elasticsearch.yml 
#允许跨域访问，为了配合elasticsearch-head可视化ES界面
http.cors.enabled: true
#允许所有地址跨域访问           
http.cors.allow-origin: "*"



#它指代的是集群的名字，一个集群的名字必须唯一，节点根据集群名字加入到集群中
cluster.name: pangold_es



#true/false 是否是集群中的主节点。
node.master: true
#节点名称，可以是自定义的方便分辨的名字，记住master也是一个节点。eg:es1,es2,es3                  
node.name: es3



#设置network.bind_host 和 publish_host的默认值          
network.host: 192.168.1.168
#HttpRest 的接口，这个接口可以让你在浏览器访问
http.port: 9230


#设置索引数据的存储路径，默认是es根目录下的data文件夹，可以设置多个存储路径，用逗号隔开，例：path.data: /path/to/data1,/path/to/data2
path.data: /home/pangolder/pangold/elasticsearch/es3/data
#设置日志文件的存储路径，默认是es根目录下的logs文件夹
path.logs: /home/pangolder/pangold/elasticsearch/es3/logs



#节点通信端口，由于ES各节点之间互相访问，默认9300，单机多节点建议指定
transport.tcp.port: 9330
#其他节点的地址端口号，注意端口号为 节点通信端口，不要配置成web发布端口了
discovery.zen.ping.unicast.hosts: ["192.168.1.168:9310","192.168.1.168:9320","192.168.1.168:9330"]
#设置集群中自动发现其他节点时ping的超时时间，默认3s，网络比较差可以提高此 值，以防止发现其他节点时出错，发生脑裂。            
discovery.zen.ping_timeout: 120s
#规则为n/2+1，告诉该节点，其他可以作为主节点的个数
discovery.zen.minimum_master_nodes: 2


```
## elasticsearch-head安装
```aidl

```




## 参考
ElasticSearch 安装 (单机单节点/单机多节点): https://www.jianshu.com/p/ce976b481623
elasticsearch 5.X下配置单机多节点: https://blog.csdn.net/u012375924/article/details/78115801

elasticsearch-head安装:https://www.cnblogs.com/miao-zp/p/6006595.html



