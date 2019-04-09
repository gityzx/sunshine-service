print("----------------函数 type 能够返回一个值或一个变量所属的类型。-----------------")
print(type("hello world")) -->output:string
print(type(print))         -->output:function
print(type(true))          -->output:boolean
print(type(360.0))         -->output:number
print(type(nil))           -->output:nil===


print("----------------nil 是一种类型，Lua 将 nil 用于表示“无效值”。一个变量在第一次赋值前的默认值是 nil，将 nil 赋予给一个全局变量就等同于删除它。-----------------")

local num
print(num)        -->output:nil

num = 100
print(num)        -->output:100


print("----------------布尔类型，可选值 true/false；Lua 中 nil 和 false 为“假”，其它所有值均为“真”。比如 0 和空字符串就是“真”；C 或者 Perl 程序员或许会对此感到惊讶。-----------------")
local a = true
local b = 0
local c = nil
local d = false;
if a then
    print("a")        -->output:a
else
    print("not a")    --这个没有执行
end

if b then
    print("b")        -->output:b
else
    print("not b")    --这个没有执行
end

if c then
    print("c")        --这个没有执行
else
    print("not c")    -->output:not c
end

if d then
	print("d");
else
	print("not d");
end



print("----------------number（数字）Number 类型用于表示实数，和 C/C++ 里面的 double 类型很类似。可以使用数学函数 math.floor（向下取整）和 math.ceil（向上取整）进行取整操作。-----------------")

local order = 3.99
local score = 98.01
print(math.floor(order))
print(math.ceil(score))



print("----------------string（字符串） Lua 中有三种方式表示字符串:-----------------")

local str1 = 'hello world'
local str2 = "hello lua"
local str3 = [["add\name",'hello']]
local str4 = [=[string have a [[]].]=]
local str5 = [=====['"fsdf'""]=====]

print(str1)    -->output:hello world
print(str2)    -->output:hello lua
print(str3)    -->output:"add\name",'hello'
print(str4)    -->output:string have a [[]].
print(str5)    -->output:第 n 级正长括号



print("----------------Table 类型实现了一种抽象的“关联数组”。“关联数组”是一种具有特殊索引方式的数组，索引通常是字符串（string）或者 number 类型，但也可以是除 nil 以外的任意类型的值。-----------------")

local corp = {
    web = "www.google.com",             --索引为字符串，key = "web", value = "www.google.com"
    telephone = "12345678",             --索引为字符串
    staff = {"Jack", "Scott", "Gary"},  --索引为字符串，值也是一个表
    100876,              				--相当于 [1] = 100876，此时索引为数字,   key = 1, value = 100876
    100191,              				--相当于 [2] = 100191，此时索引为数字,   key = 2, value = 100191
    [10] = 360,          				--直接把数字索引给出
    ["city"] = "Beijing" 				--索引为字符串
}

print(corp.web)               -->output:www.google.com
print(corp["telephone"])      -->output:12345678
print(corp[2])                -->output:100191
print(corp["city"])           -->output:"Beijing"
print(corp.staff[1])          -->output:Jack
print(corp[10])               -->output:360


print("----------------function (函数)  在 Lua 中，函数 也是一种数据类型，函数可以存储在变量中，可以通过参数传递给其他函数，还可以作为其他函数的返回值。-----------------")

local function add()
	-- body
	print("in the function")
	local x = 10
	local y = 20
	return x+y
end

local a = add  --把函数赋给变量
print(a())



print("----------------算术运算符-----------------")

print(1 + 2)       -->打印 3
print(5 / 10)      -->打印 0.5。 这是Lua不同于c语言的
print(5.0 / 10)    -->打印 0.5。 浮点数相除的结果是浮点数
-- print(10 / 0)   -->注意除数不能为0，计算的结果会出错
print(2 ^ 10)      -->打印 1024。 求2的10次方

local num = 1357
print(num % 2)       -->打印 1
print((num % 2) == 1) -->打印 true。 判断num是否为奇数
print((num % 5) == 0)  -->打印 false。判断num是否能被5整数


print("----------------关系运算符-----------------")

print(1 < 2)    -->打印 true
print(1 == 2)   -->打印 false
print(1 ~= 2)   -->打印 true
local a, b = true, false
print(a == b)  -->打印 false


local a = { x = 1, y = 0}
local b = { x = 1, y = 0}
if a == b then
  print("a==b")
else
  print("a~=b")
end

print("----------------逻辑运算符-----------------")

local c = nil
local d = 0
local e = 100
print(c and d)  -->打印 nil
print(c and e)  -->打印 nil
print(d and e)  -->打印 100
print(c or d)   -->打印 0
print(c or e)   -->打印 100
print(not c)    -->打印 true
print(not d)    -->打印 false

print("----------------字符串连接-----------------")

print("Hello " .. "World")    -->打印 Hello World
print(0 .. 1)                 -->打印 01

str1 = string.format("%s-%s","hello","world")
print(str1)              -->打印 hello-world

str2 = string.format("%d-%s-%.2f",123,"world",1.21)
print(str2)              -->打印 123-world-1.21


local t = {1, 3, 5, 8, 11, 18, 21}

local i
for i, v in ipairs(t) do
    if 11 == v then
        print("index[" .. i .. "] have right value[11]")
        break
    end
end






  local revDays = {
    ["Sunday"] = 1,
    ["Monday"] = 2,
    ["Tuesday"] = 3,
    ["Wednesday"] = 4,
    ["Thursday"] = 5,
    ["Friday"] = 6,
    ["Saturday"] = 7
  }
  local x = "Tuesday"
print(revDays[x])  -->3



print("----------------函数定义-----------------")






print("----------------require 函数-----------------")

local t1 = require("tools")
local t2 = require("t1")
t1.say()
t2.say()



print("----------------文件操作:io.input-----------------")

file = io.input("/Users/wecash/Desktop/primeloan-web-api.csv")
repeat
    line = io.read()
    if nil== line then
        break
    end

    print(line)
until(false)

io.close(file)


print("----------------文件操作:io.open-----------------")

file = io.open("/Users/wecash/Desktop/primeloan-web-api.csv")
for line in file:lines() do
    print(line)
end
io.close(file)






















