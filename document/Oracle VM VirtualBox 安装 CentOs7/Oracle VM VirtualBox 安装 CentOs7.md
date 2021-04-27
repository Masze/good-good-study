# Oracle VM VirtualBox 安装 CentOs7

## 一.准备
### 1. Oracle VM VirtualBox
[下载地址]: https://www.virtualbox.org/wiki/Downloads

安装略
### 2. CentOS7
[ 下载地址]:https://www.centos.org/download/

## 二.新建虚拟机

### 1. 新建虚拟机
选择`新建`
![图1](.\img\图1.png)

### 2. 配置虚拟机信息
选择`专家模式`，填写相关信息，完成后点击`创建`
![图2](.\img\图2.png)

### 3. 创建硬盘
其中文件大小是硬盘的上限，完成后点击`创建`
![图3](.\img\图3.png)

### 4. 设置网络
创建完成后，回到管理页面依次点击`设置`-`网络`，选择`网卡2`,
设置host-only网络（与主机通信）
![图4](.\img\图4.png)

## 三.安装OS
### 1. 启动
  点击`启动`，打开虚拟机,选择已下载的OS文件设置为启动盘。
  ![图5](.\img\图5.png)

### 2. Install CentOS 7
  ![图6](.\img\图6.png)

### 3. 选择语言
  ![图7](.\img\图7.png)

### 4. 软件选择

  选择`带GUI的服务器`
  ![图8](.\img\图8.png)

### 5. 连接网络

  `网络` 将两个网络都打开,其他配置项的默认即可
  ![图9](.\img\图9.png)

### 6. 配置

  点击 `开始安装`,设置`ROOT密码`和`创建用户`
  ![图10](.\img\图10.png)
  创建用户
  ![图11](.\img\图11.png)

  等待系统安装完毕,重启后进入系统
  ![图12](.\img\图12.png)

### 7. 接受LICENSE许可
  ![图13](.\img\图13.png)

### 8. 登录

  完成配置后,进入登录页面,输入用户名密码登录
  ![图14](.\img\图14.png)
## 四.CentOS系统配置
### 1. 登录后
  按需设置gnome,输入法设置为中文时选择 `汉语(Intelligent Pinyin)`
![图15](.\img\图15.png)
### 2. 网络设置
1. 输入`ip addr` 测试网络
![图16](.\img\图16.png)
`enp0s3` 为第一张网卡,`enp0s8`为第二张.其中`enp0s8`没有ip地址,这里需要设置下,才能与宿主机通信.
2. 修改网卡配置文件
```
#登录root账号
su root 
#修改 网卡配置文件
vi /etc/sysconfig/network-scripts/ifcfg-enp0s8
#将文件中 `ONBOOT="no"` 修改为 `ONBOOT="yes"` 
```
3. 重启网络
    输入 `service network restart` 重启网络即可
4. 检查网络
	我这里虚拟机ip为`192.168.56.105`,宿主机ip为`192.168.56.1`
	检查防火墙,相互ping同即可
## 五.其他配置及注意项
### 1. 开放端口
```
# 打开80端口
iptables -I INPUT -p tcp -m state --state NEW -m tcp --dport 80 -j ACCEPT
# 保存以生效
service iptables save
```
### 2. 用户不在sudoers文件中
```
# 切换root用户
su root 
# 查看/etc/sudoers权限
ls -all /etc/sudoers
# 更改权限为777
chmod 777 /etc/sudoers
# 编辑/etc/sudoers
vi /etc/sudoers
# 在 root ALL=(ALL) ALL 下面添加一行##miku 为需要添加的用户
miku ALL=(ALL) ALL
# 保存退出
Esc :wq
#把/etc/sudoers权限改回440
chmod 440 /etc/sudoers
```
### 3. service iptables save 失败
```
# 关闭防火墙
systemctl stop firewalld 
# 安装或更新服务
yum install iptables-services 
# 启动iptables
systemctl enable iptables 
# 打开iptables 
systemctl start iptables 
# 保存iptables服务
service iptables save
# 重启iptables服务
service iptables restart
```