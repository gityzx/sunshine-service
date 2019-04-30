
## 详细的配置:
```$xslt

############### Filebeat #############
filebeat:
  # List of prospectors to fetch data.
  prospectors:
    -
      # paths指定要监控的日志（这是默认的）（自行可以修改）(比如我放在/home/hadoop/app.log里）
      paths:
        - /var/log/*.log

      #指定被监控的文件的编码类型,使用plain和utf-8都是可以处理中文日志的。
      # Some sample encodings:
      #   plain, utf-8, utf-16be-bom, utf-16be, utf-16le, big5, gb18030, gbk,
      #    hz-gb-2312, euc-kr, euc-jp, iso-2022-jp, shift-jis, ...
      encoding: plain

      #指定文件的输入类型log(默认)或者stdin。
      input_type: log

      # 在输入中排除符合正则表达式列表的那些行
      exclude_lines: ["^DBG"]

      # 包含输入中符合正则表达式列表的那些行（默认包含所有行），include_lines执行完毕之后会执行exclude_lines
      include_lines: ["^ERR", "^WARN"]

      # 忽略掉符合正则表达式列表的文件默认为每一个符合paths定义的文件都创建一个harvester。
      exclude_files: [".gz$"]

      # 向输出的每一条日志添加额外的信息，比如“level:debug”，方便后续对日志进行分组统计。
      # 默认情况下，会在输出信息的fields子目录下以指定的新增fields建立子目录，例如fields.level
      # 这个得意思就是会在es中多添加一个字段，格式为 "filelds":{"level":"debug"}
      fields:
        level: debug
        review: 1

      # 如果该选项设置为true则新增fields成为顶级目录而不是将其放在fields目录下。自定义的field会覆盖filebeat默认的field。
      fields_under_root: false

      # 可以指定Filebeat忽略指定时间段以外修改的日志内容，比如2h（两个小时）或者5m(5分钟)。
      ignore_older: 0

      # 如果一个文件在某个时间段内没有发生过更新,则关闭监控的文件handle。默认1h,change只会在下一次scan才会被发现
      close_older: 1h

      # 设定Elasticsearch输出时的document的type字段,也可以用来给日志进行分类。Default: log
      document_type: log

      # Filebeat以多快的频率去prospector指定的目录下面检测文件更新比如是否有新增文件
      # 如果设置为0s则Filebeat会尽可能快地感知更新占用的CPU会变高。默认是10s。
      scan_frequency: 10s

      # 每个harvester监控文件时使用的buffer的大小。
      harvester_buffer_size: 16384

      # 日志文件中增加一行算一个日志事件max_bytes限制在一次日志事件中最多上传的字节数多出的字节会被丢弃。The default is 10MB.
      max_bytes: 10485760

      # 适用于日志中每一条日志占据多行的情况,比如各种语言的报错信息调用栈。这个配置的下面包含如下配置
      multiline:
        pattern: ^\[      # 多行日志开始的那一行匹配的pattern
        negate: false     #  是否需要对pattern条件转置使用，不翻转设为true，反转设置为false。  【建议设置为true】   
        match: after      #  匹配pattern后，与前面（before）还是后面（after）的内容合并为一条日志
        max_lines: 500    # 合并的最多行数（包含匹配pattern的那一行）
        timeout: 5s       # 到了timeout之后，即使没有匹配一个新的pattern（发生一个新的事件），也把已经匹配的日志事件发送出去
     

      # 如果设置为true，Filebeat从文件尾开始监控文件新增内容，把新增的每一行文件作为一个事件依次发送，而不是从文件开始处重新发送所有内容
      tail_files: false    

      #Filebeat检测到某个文件到了EOF（文件结尾）之后，每次等待多久再去检测文件是否有更新，默认为1s
      backoff: 1s  

      #Filebeat检测到某个文件到了EOF之后，等待检测文件更新的最大时间，默认是10秒      
      max_backoff: 10s 


      # 定义到达max_backoff的速度,默认因子是2,到达max_backoff后变成每次等待max_backoff那么长的时间才backoff一次.直到文件有更新才会重置为backoff。
      # 根据现在的默认配置是这样的，每隔1s检测一下文件变化，如果连续检测两次之后文件还没有变化，下一次检测间隔时间变为10s
      backoff_factor: 2

      # 这个选项关闭一个文件,当文件名称的变化。
      # 该配置选项建议只在windows。
      force_close_files: false


    # Additional prospector
-
  # Configuration to use stdin input
  input_type: stdin

  # spooler的大小spooler中的事件数量超过这个阈值的时候会清空发送出去不论是否到达超时时间。
  spool_size: 2048

  # 是否采用异步发送模式(实验!)
  publish_async: false

  # spooler的超时时间如果到了超时时间spooler也会清空发送出去不论是否到达容量的阈值。
  idle_timeout: 5s

  # 记录filebeat处理日志文件的位置的文件
  registry_file: /var/lib/filebeat/registry

  # 如果要在本配置文件中引入其他位置的配置文件可以写在这里需要写完整路径但是只处理prospector的部分。
  config_dir:


############################# Output ##########################################

  #################################     Output: Elasticsearch.     ###################################################
  ###      （这是默认的，filebeat收集后放到es里）（自行可以修改，比如我有时候想filebeat收集后，然后到redis，再到es，就可以注销这行）
  ####################################################################################################################  
  output.elasticsearch
    hosts: ["localhost:9200"]     # IPv6 addresses should always be defined as: https://[2001:db8::1]:9200
    compression_level: 0          # Set gzip compression level.
    
    # 输出认证.
    protocol: "https"     
    username: "admin"
    password: "s3cr3t"

     #字典中的HTTP参数通过url索引操作。
    parameters:
      param1: value1
      param2: value2
    # 启动进程数.
    worker: 1
    # 输出数据到指定index defaultis "filebeat"  可以使用变量[filebeat-]YYYY.MM.DDkeys.
    index: "filebeat-%{+yyyy.MM.dd}"
    # 一个模板用于设置在Elasticsearch映射默认模板加载是禁用的,没有加载模板这些设置可以调整或者覆盖现有的加载自己的模板
    template.enabled: true
    # Template name. By default the templatename is filebeat.
    template.name: "filebeat"
    # Path to template file
    template.path: "${path.config}/filebeat.template.json"
    # Overwrite existing template
    template.overwrite: false
    # If set to true, filebeat checks theElasticsearch version at connect time, and if it
    # is 2.x, it loads the file specified bythe template.versions.2x.path setting. The
    # default is true.
    template.versions.2x.enabled: true
    # Path to the Elasticsearch 2.x version ofthe template file.
    template.versions.2x.path: "${path.config}/filebeat.template-es2x.json"
    # 发送重试的次数取决于max_retries的设置默认为3
    max_retries: 3
    # 单个elasticsearch批量API索引请求的最大事件数。默认是50。
    bulk_max_size: 50
    # elasticsearch请求超时事件。默认90秒.
    timeout: 90
    # 新事件两个批量API索引请求之间需要等待的秒数。如果bulk_max_size在该值之前到达额外的批量索引请求生效。
    flush_interval: 1

    # 使用https的SSL配置。默认true。
    #Use SSL settings for HTTPS. Default is true.
    ssl.enabled: true
    # Configure SSL verification mode. If`none` is configured, all server hosts
    # and certificates will be accepted. In thismode, SSL based connections are
    # susceptible to man-in-the-middle attacks.Use only for testing. Default is
    # `full`.
    ssl.verification_mode: full
    # List of supported/valid TLS versions. Bydefault all TLS versions 1.0 up to
    # 1.2 are enabled.
    ssl.supported_protocols: [TLSv1.0,TLSv1.1, TLSv1.2]
    # SSL configuration. By default is off.
    # List of root certificates for HTTPSserver verifications
    ssl.certificate_authorities: ["/etc/pki/root/ca.pem"]
    # Certificate for SSL client authentication
    ssl.certificate: "/etc/pki/client/cert.pem"
    # Client Certificate Key
    ssl.key: "/etc/pki/client/cert.key"
    # Optional passphrase for decrypting theCertificate Key.
    ssl.key_passphrase: ''
    # Configure cipher suites to beused for SSL connections
    ssl.cipher_suites: []
    # Configure curve types for ECDHE basedcipher suites
    ssl.curve_types: []





  #################################     Output: Logstash.     ###################################################
  ###      发送数据到logstash单个实例数据可以输出到elasticsearch或者logstash选择其中一种注释掉另外一组输出配置。
  ####################################################################################################################
  output.logstash:
    # Logstash 主机地址
    hosts: ["localhost:5044"]
    # 配置每个主机发布事件的worker数量。在负载均衡模式下最好启用。
    worker: 1
    # #发送数据压缩级别
    compression_level: 3
    # 如果设置为TRUE和配置了多台logstash主机输出插件将负载均衡的发布事件到所有logstash主机。
    #如果设置为false输出插件发送所有事件到随机的一台主机上如果选择的不可达将切换到另一台主机。默认是false。
    loadbalance: true
    # 输出数据到指定index defaultis "filebeat"  可以使用变量[filebeat-]YYYY.MM.DDkeys.
    index: filebeat
    # Number of batches to be sendasynchronously to logstash while processing new batches.
    pipelining: 0
    # SOCKS5 proxy server URL

    proxy_url: socks5://user:password@socks5-server:2233
    # Resolve names locally when using a proxy server. Defaults to false.
    proxy_use_local_resolver: false

    # 使用https的SSL配置。默认true。
    #Use SSL settings for HTTPS. Default is true.
    ssl.enabled: true
    # Configure SSL verification mode. If`none` is configured, all server hosts
    # and certificates will be accepted. In thismode, SSL based connections are
    # susceptible to man-in-the-middle attacks.Use only for testing. Default is
    # `full`.
    ssl.verification_mode: full
    # List of supported/valid TLS versions. Bydefault all TLS versions 1.0 up to
    # 1.2 are enabled.
    ssl.supported_protocols: [TLSv1.0,TLSv1.1, TLSv1.2]
    # SSL configuration. By default is off.
    # List of root certificates for HTTPSserver verifications
    ssl.certificate_authorities: ["/etc/pki/root/ca.pem"]
    # Certificate for SSL client authentication
    ssl.certificate: "/etc/pki/client/cert.pem"
    # Client Certificate Key
    ssl.key: "/etc/pki/client/cert.key"
    # Optional passphrase for decrypting theCertificate Key.
    ssl.key_passphrase: ''
    # Configure cipher suites to beused for SSL connections
    ssl.cipher_suites: []
    # Configure curve types for ECDHE basedcipher suites
    ssl.curve_types: []
    ### Console output 标准输出JSON 格式。
    console:
       #如果设置为TRUE事件将很友好的格式化标准输出。默认false。
       pretty: false

 


  #################################     Output: File.     ###################################################
  ###      
  ####################################################################################################################
  output.file:
    # Boolean flag to enable ordisable the output module.
    enabled: true
    # Path to the directorywhere to save the generated files. The option is
    # mandatory.
    path: "/tmp/filebeat"
    # Name of the generatedfiles. The default is `filebeat` and it generates
    # files: `filebeat`,`filebeat.1`, `filebeat.2`, etc.
    filename: filebeat
    # Maximum size in kilobytesof each file. When this size is reached, and on
    # every filebeat restart,the files are rotated. The default value is 10240
    # kB.
    rotate_every_kb: 10000
    # Maximum number of filesunder path. When this number of files is reached,
    # the oldest file is deletedand the rest are shifted from last to first. The
    # default is 7 files.
    number_of_files: 7

 


```


## logstash实例:
```$xslt

filebeat:
  prospectors:
    - input_type: log
      paths:
        - /data/logs/nginx/*.log
      input_type: log
      document_type: nginx
      close_inactive: 1m
      scan_frequency: 5s
      fields:
        nginx_id: web-nodejs
      fields_under_root: true
      close_removed: true
      tail_files: true
      tags: 'web-nginx'
  spool_size: 1024
  idle_timeout: 5s
  registry_file: /var/lib/filebeat/registry
output:
  logstash:
    enabled: true
    hosts: ["192.168.6.108:5044"]
    worker: 4
    bulk_max_size: 1024
    compression_level: 6
    loadbalance: false
    index: filebeat
    backoff.max: 120s
```


## 参考:
https://yq.aliyun.com/ziliao/313153?spm=a2c4e.11155472.blogcont.17.6a4f4e8eqrWt3W