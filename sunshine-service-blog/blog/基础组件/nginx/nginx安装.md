mac安装nginx


brew安装
ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"  
brew install nginx 
http://localhost:8088


cd /usr/local/etc/nginx/servers/

安装目录: /usr/local/Cellar/nginx/1.15.10/html
编辑配置: vi /usr/local/etc/nginx/nginx.conf
启动命令: brew services start nginx
重启命令: brew services restart nginx



参考资料:
https://www.jianshu.com/p/6c7cb820a020
https://blog.csdn.net/xiliuhu/article/details/81805070