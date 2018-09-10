# Request Header汇总：
## Accept

作用： 浏览器端可以接受的媒体类型,

例如：  Accept: text/html  代表浏览器可以接受服务器回发的类型为 text/html  也就是我们常说的html文档,

如果服务器无法返回text/html类型的数据,服务器应该返回一个406错误(non acceptable)

通配符 * 代表任意类型

例如  Accept: */*  代表浏览器可以处理所有类型,(一般浏览器发给服务器都是发这个)

 

## Accept-Encoding：

作用： 浏览器申明自己接收的编码方法，通常指定压缩方法，是否支持压缩，支持什么压缩方法（gzip，deflate），（注意：这不是只字符编码）;

例如： Accept-Encoding: zh-CN,zh;q=0.8

 

## Accept-Language

作用： 浏览器申明自己接收的语言。 

语言跟字符集的区别：中文是语言，中文有多种字符集，比如big5，gb2312，gbk等等；

例如： Accept-Language: en-us

 

## Connection

例如：　Connection: keep-alive   当一个网页打开完成后，客户端和服务器之间用于传输HTTP数据的TCP连接不会关闭，如果客户端再次访问这个服务器上的网页，会继续使用这一条已经建立的连接

例如：  Connection: close  代表一个Request完成后，客户端和服务器之间用于传输HTTP数据的TCP连接会关闭， 当客户端再次发送Request，需要重新建立TCP连接。

 

## Host[目标服务器域名]

（发送请求时，该报头域是必需的）
作用: 请求报头域主要用于指定被请求资源的Internet主机和端口号，它通常从HTTP URL中提取出来的

例如: 我们在浏览器中输入：http://www.hzau.edu.cn

浏览器发送的请求消息中，就会包含Host请求报头域，如下：

Host：www.hzau.edu.cn

此处使用缺省端口号80，若指定了端口号，则变成：Host：指定端口号

 

## Referer [当前服务器域名]

当浏览器向web服务器发送请求的时候，一般会带上Referer，告诉服务器我是从哪个页面链接过来的，服务器籍此可以获得一些信息用于处理。

比如从我主页上链接到一个朋友那里，他的服务器就能够从HTTP Referer中统计出每天有多少用户点击我主页上的链接访问他的网站。



## Origin [原始服务器域名]

为了防止CSRF的攻击，我们建议修改浏览器在发送POST请求的时候加上一个Origin字段，这个Origin字段主要是用来标识出最初请求是从哪里发起的。如果浏览器不能确定源在哪里，那么在发送的请求里面Origin字段的值就为空。

隐私方面：这种Origin字段的方式比Referer更人性化，因为它尊重了用户的隐私。

1、Origin字段里只包含是谁发起的请求，并没有其他信息 (通常情况下是方案，主机和活动文档URL的端口)。跟Referer不一样的是，Origin字段并没有包含涉及到用户隐私的URL路径和请求内容，这个尤其重要。

2、Origin字段只存在于POST请求，而Referer则存在于所有类型的请求。

随便点击一个超链接（比如从搜索列表里或者企业intranet），并不会发送Origin字段，这样可以防止敏感信息的以外泄露。

在应对隐私问题方面，Origin字段的方法可能更能迎合用户的口味。

服务端要做的：用Origin字段的方法来防御CSRF攻击的时候，网站需要做到以下几点：

1、在所有能改变状态的请求里，包括登陆请求，都必须使用POST方法。对于一些特定的能改变状态的GET请求必须要拒绝，这是为了对抗上文中提到过的论坛张贴的那种危害类型。

2、对于那些有Origin字段但是值并不是我们希望的（包括值为空）请求，服务器要一律拒绝。比如，服务器可以拒绝一切Origin字段为外站的请求。

安全性分析：虽然Origin字段的设计非常简单，但是用它来防御CSRF攻击可以起到很好的作用。

1、去掉Origin字段。由于支持这种方法的浏览器在每次POST请求的时候都会带上源header，那么网站就可以通过查看是否存在这种Origin字段来确定请求是否是由支持这种方法的浏览器发起的。这种设计能有效防止攻击者将一个支持这种方法的浏览器改变成不支持这种方法的浏览器，因为即使你改变浏览器去掉了Origin字段，Origin字段还是存在，只不过值变为空了。这跟Referer很不一样，因为Referer 只要是在请求里去掉了，那服务器就探测不到了。

2、DNS重新绑定。在现有的浏览器里面，对于同站的XMLHttpRequests，Origin字段可以被伪造。只依赖网络连接进行身份验证的网站应当使用在第2章里提到的DNS重新绑定的方法，比如验证header里的Host字段。在使用Origin字段来防御CSRF攻击的时候，也需要用到DNS重新绑定的方法，他们是相辅相成的。当然对于在第四章里提到的CSRF防御方法，也需要用到DNS重新绑定的方法。

3、插件。如果网站根据crossdomain.xml准备接受一个跨站HTTP请求的时候，攻击者可以在请求里用Flash Player来设置Origin字段。在处理跨站请求的时候，token验证的方法处理的不好，因为token会暴露。为了应对这些攻击，网站不应当接受不可信来源的跨站请求。

4、应用。Origin字段跟以下四个用来确定请求来源的建议非常类似。Origin字段以下四个建议的基础上统一并改进了，目前已经有几个组织采用了Origin字段的方法建议。



? Cross-Site XMLHttp Request。Cross-Site XMLHttp Request的方法规定了一个Access-Control-Origin 字段，用来确定请求来源。这个字段存在于所有的HTTP方法，但是它只在XMLHttpRequests请求的时候才会带上。我们对Origin字段的设想就是来源于这个建议，而且Cross-Site XMLHttp Request工作组已经接受我们的建议愿意将字段统一命名为Origin。

?XDomainRequest。在Internet Explorer 8 Beta 1里有XDomainRequest的API，它在发送HTTP请求的时候将Referer里的路径和请求内容删掉了。被缩减后的Referer字段可以标识请求的来源。我们的实验结果表明这种删减的Referer字段经常会被拒绝，而我们的Origin字段却不会。微软已经发表声明将会采用我们的建议将XDomainRequest里的删减Referer更改为Origin字段。

? JSONRequest。在JSONRequest这种设计里，包含有一个Domain字段用来标识发起请求的主机名。相比之下，我们的Origin字段方法不仅包含有主机，还包含请求的方案和端口。JSONRequest规范的设计者已经接受我们的建议愿意将Domain字段更改为Origin字段，以用来防止网络攻击。

? Cross-Document Messaging。在HTML5规范里提出了一个建议，就是建立一个新的浏览器API，用来验证客户端在HTML文件之间链接。这种设计里面包含一个不能被覆盖的origin属性，如果不是在客户端的话，在服务端验证这种origin属性的过程与我们验证origin字段的过程其实是一样的。

具体实施：我们在服务器和浏览器端都实现了利用origin字段的方法来防止CSRF攻击。在浏览器端我们的实现origin字段方式是，在WebKit添加一个8行代码的补丁，Safari里有我们的开源组件，Firefox里有一个466行代码的插件。在服务器端我们实现origin字段的方式是，在ModSecurity应用防火墙里我们只用3行代码，在Apache里添加一个应用防火墙语言（见图4）。这些规则在POST请求里能验证Host字段和具有合法值的origin字段。在实现这些规则来防御CSRF攻击的时候，网站并不需要做出什么改变，而且这些规则还能确保GET请求没有任何攻击性(前提是浏览器端已经实现了origin字段方法)。


 

## User-Agent

作用：告诉HTTP服务器， 客户端使用的操作系统和浏览器的名称和版本.

我们上网登陆论坛的时候，往往会看到一些欢迎信息，其中列出了你的操作系统的名称和版本，你所使用的浏览器的名称和版本，这往往让很多人感到很神奇，实际上，服务器应用程序就是从User-Agent这个请求报头域中获取到这些信息User-Agent请求报头域允许客户端将它的操作系统、浏览器和其它属性告诉服务器。

例如： User-Agent: Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; CIBA; .NET CLR 2.0.50727; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; .NET4.0C; InfoPath.2; .NET4.0E)

 

 

另外，访问一些其他的URL的时候，request 的Header里面一些其他的字段，下面来进行列举：

比如我在登录一个需要用户名密码登录的网站时Request Header里面就有上面还没有提到过的字段。








## Cache-Control

我们网页的缓存控制是由HTTP头中的“Cache-control”来实现的，常见值有private、no-cache、max-age、must-revalidate等，默认为private。这几种值的作用是根据重新查看某一页面时不同的方式来区分的：

###（1）、打开新窗口

值为private、no-cache、must-revalidate，那么打开新窗口访问时都会重新访问服务器。而如果指定了max-age值（单位为秒），那么在此值内的时间里就不会重新访问服务器，例如：

Cache-control: max-age=5(表示当访问此网页后的5秒内再次访问不会去服务器)

###（2）、在地址栏回车

值为private或must-revalidate则只有第一次访问时会访问服务器，以后就不再访问。

值为no-cache，那么每次都会访问。

值为max-age，则在过期之前不会重复访问。

###（3）、按后退按扭

值为private、must-revalidate、max-age，则不会重访问，

值为no-cache，则每次都重复访问

###（4）、按刷新按扭

无论为何值，都会重复访问


## Cookie

Cookie是用来存储一些用户信息以便让服务器辨别用户身份的（大多数需要登录的网站上面会比较常见），比如cookie会存储一些用户的用户名和密码，当用户登录后就会在客户端产生一个cookie来存储相关信息，这样浏览器通过读取cookie的信息去服务器上验证并通过后会判定你是合法用户，从而允许查看相应网页。当然cookie里面的数据不仅仅是上述范围，还有很多信息可以存储是cookie里面，比如sessionid等。

 

## If-Modified-Since

作用： 把浏览器端缓存页面的最后修改时间发送到服务器去，服务器会把这个时间与服务器上实际文件的最后修改时间进行对比。如果时间一致，那么返回304，客户端就直接使用本地缓存文件。如果时间不一致，就会返回200和新的文件内容。客户端接到之后，会丢弃旧文件，把新文件缓存起来，并显示在浏览器中.

例如：Mon, 17 Aug 2015 12:03:33 GMT

 

## If-None-Match

作用: If-None-Match和ETag一起工作，工作原理是在HTTP Response中添加ETag信息。 当用户再次请求该资源时，将在HTTP Request 中加入If-None-Match信息(ETag的值)。如果服务器验证资源的ETag没有改变（该资源没有更新），将返回一个304状态告诉客户端使用本地缓存文件。否则将返回200状态和新的资源和Etag.  使用这样的机制将提高网站的性能

例如: If-None-Match: W/"3119-1437038474000"





参考文档
https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Headers/Origin
https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Headers/Referer
http://blog.sina.com.cn/s/blog_625f850801015tik.html
http://blog.csdn.net/yelin042/article/details/74382061


