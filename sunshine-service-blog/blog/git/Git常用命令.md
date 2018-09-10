# 1. GIT分支图

```
1.下边的命令可以打印出Git的分支图
git log --graph --all --pretty=format:'%Cred%h%Creset -%C(yellow)%d%Creset %s %Cgreen(%cr) %C(bold blue)<%an>%Creset' --abbrev-commit --date=relative


2.或者通过下列命令在git config文件里面设置别名。
git config --global alias.lg "log --graph --all --pretty=format:'%Cred%h%Creset -%C(yellow)%d%Creset %s %Cgreen(%cr) %C(bold blue)<%an>%Creset' --abbrev-commit --date=relative"
然后通过命令：git lg来列出分支图


3.使用Git log命令
git log --graph --decorate --oneline --simplify-by-decoration --all



说明：
--decorate 标记会让git log显示每个commit的引用(如:分支、tag等) 
--oneline 一行显示
--simplify-by-decoration 只显示被branch或tag引用的commit
--all 表示显示所有的branch，这里也可以选择，比如我指向显示分支ABC的关系，则将--all替换为branchA branchB branchC
```



# 2.GIT分支
```
1.查看本地分支
git branch

2.查看全部分支（本地和远程）
git branch -a

3.查看远程分支
git branch -r

4.切换分支
git checkout dev

5.创建并切换
git checkout -b remotes/origin/todo  远程分支

6.删除分支：
git push origin --delete dubbo-dev
```




# 3.合并代码

```
git clone ssh://git@gitlab.vdian.net:60022/wdseller_bussbase/wd-crm.git

git checkout dev

git checkout yzx_dev

git branch -a （当前分支为yzx_dev）

从yzx_dev分支上拉取新分支dubbo_dev
git checkout -b dubbo_dev

把dev分支merge进当前的分支(当前分支为dubbo_dev).
git merge dev

```

# 4.git回滚版本：
```
git log 

git reset --hard e377f60e28c8b84158

git push -f 

```


# 5.删除github中的文件
```
git clone ssh://git@gitlab.vdian.net:60022/wdseller_bussbase/wd-crm.git

cd wd-crm/

git branch -a

git checkout dubbo_dev

git rm wd-crm-modules.iml

git commit -m "del iml file"

git push
```

# 6.拉取分支
```
git checkout -b dev-item-yzx 远程分支

git push origin dev-item-yzx 提交分支
```



# 7.本地maven项目提交到git
```
0、判断ssh是否有效
    ssh -T git@github.com

1、（先进入项目文件夹:/Users/yzx/Yzx/Workspace/sunshine-module-server ）通过命令 git init 把这个目录变成git可以管理的仓库
    git init 

2、把文件添加到版本库中，使用命令 git add .添加到暂存区里面去，不要忘记后面的小数点“.”，意为添加文件夹下的所有文件
    git add . 

3、用命令 git commit告诉Git，把文件提交到仓库。引号内为提交说明
    git commit -m ‘first commit’ 

4、关联到远程库
   git remote add origin 你的远程库地址 
   如： git remote add origin ssh://git@gitlab.vdian.net:60022/wdseller_bussbase/common-data-server.git
       git remote add origin https://github.com/cade8800/ionic-demo.git 

5、获取远程库与本地同步合并（如果远程库不为空必须做这一步，否则后面的提交会失败）
   git pull –r origin master 

6、把本地库的内容推送到远程，使用 git push命令，实际上是把当前分支master推送到远程。执行此命令后会要求输入用户名、密码，验证通过后即开始上传。
   git push -u origin master 


1.判断ssh是否有效:ssh -T git@github.com

2.先在github上Create a new repository: sunshine-module-server

3.在Mac的/Users/yzx/Yzx/GitWorkspace中通过idea创建一个maven项目: sunshine-module-server

4.cd /Users/yzx/Yzx/GitWorkspace/sunshine-module-server,将项目推送到github上
git 命令:
git init
git add .
git commit -m "first commit"
git remote add origin git@github.com:gityzx/sunshine-module-server.git
git push -u origin master

5.在idea中可以看到git的命令，不过建设通过手动git.



或者:
git init
git add README.md
git commit -m "first commit"
git remote add origin git@github.com:gityzx/maven.git
git push -u origin master

```








