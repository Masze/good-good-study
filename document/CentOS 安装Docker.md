# CentOS 安装Docker

## 卸载旧版本

较旧的Docker版本称为`docker`或`docker-engine`。如果已安装这些程序，请卸载它们以及相关的依赖项。

## 安装Docker Engine-Community

###  安装所需的软件包

```sh
$ sudo yum update
$ sudo yum install -y yum-utils
```

###  设置存储库

```sh
$ sudo yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo
```

###  安装docker-ce

最新版本的**Docker Engine-Community**和**containerd**

```sh
$ sudo yum install docker-ce docker-ce-cli containerd.io
```
###  将当前用户加入 docker 组

```sh
# 添加docker用户组
$ sudo groupadd docker
# 将当前用户加入docker组
$ sudo gpasswd -a ${USER} docker

```

## 启动docker service
```sh
#启动
$ sudo systemctl start  docker
#开机自启
$ sudo systemctl enable docker
#测试
$ docker version
````

## 设置国内镜像仓库加速服务
```sh
$  vim /etc/docker/daemon.json

# 修改
{
  "registry-mirrors": ["https://registry.docker-cn.com"]
}
```

## 删除Docker Engine

```sh
# 卸载Docker package
$ sudo yum remove docker-ce
# 主机上的映像，容器，卷或自定义配置文件不会自动删除。要删除所有图像，容器和卷
$ sudo rm -rf /var/lib/docker

```