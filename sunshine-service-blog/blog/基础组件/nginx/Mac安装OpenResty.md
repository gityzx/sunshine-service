

brew install openresty/brew/openresty



==> ./configure 
--prefix=/usr/local/Cellar/openresty/1.13.6.2 
--pid-path=/usr/local/var/run/openresty.pid 
--lock-path=/usr/local/var/run/openresty.lock 
--conf-path=/usr/local/etc/openresty/nginx.conf 
--ht

安装目录: cd /usr/local/Cellar/openresty/1.13.6.2
编辑配置: vi /usr/local/etc/openresty/nginx.conf





启动:
/usr/local/Cellar/openresty/1.13.6.2/nginx/sbin/nginx -c /usr/local/etc/openresty/nginx.conf
-c参数指定了要加载的nginx配置文件路径

重启:
/usr/local/Cellar/openresty/1.13.6.2/nginx/sbin/nginx -s reload

拷贝conf文件
cp /Users/wecash/Yzx/gitspace/sunshine-service/sunshine-service-blog/nginx/nginx.conf /usr/local/etc/openresty/

访问:http://localhost:8087/
 
参考资料:
http://openresty.org/cn/installation.html
https://www.cnblogs.com/fhen/p/5896105.html