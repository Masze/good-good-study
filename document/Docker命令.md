# 开发者必备Docker命令

## Docker 镜像常用命令


```bash
docker search java

docker pull java:8

docker pull nginx:1.17.0

docker images

docker rmi java:8

docker rmi -f java:8
```

- 删除所有没有引用的镜像

```bash
docker rmi `docker images | grep none | awk '{print $3}'`
```

- 强制删除所有镜像

```bash
docker rmi -f $(docker images)
```

## Docker 容器常用命令

### 新建并启动容器

```bash
docker run -p 80:80 --name nginx -d nginx:1.17.0
```

- -d选项：表示后台运行
- --name选项：指定运行后容器的名字为nginx,之后可以通过名字来操作容器
- -p选项：指定端口映射，格式为：hostPort:containerPort

### 列出容器

```bash
docker ps

docker ps -a
```

### 停止容器
```bash
# $ContainerName及$ContainerId可以用docker ps命令查询出来
docker stop $ContainerName(或者$ContainerId)

docker stop nginx
#或者
docker stop c5f5d5125587
```

### 强制停止容器

```bash
docker kill $ContainerName(或者$ContainerId)
```

### 启动已停止的容器

```bash
docker start $ContainerName(或者$ContainerId)
```

### 进入容器
- 先查询出容器的pid：

```bash
docker inspect --format "{{.State.Pid}}" $ContainerName(或者$ContainerId)

nsenter --target "$pid" --mount --uts --ipc --net --pid
```

### 删除容器

- 删除指定容器：

```bash
docker rm $ContainerName(或者$ContainerId)
```

- 按名称删除容器
```bash
docker rm `docker ps -a | grep mall-* | awk '{print $1}'`
```

- 强制删除所有容器；

```bash
docker rm -f $(docker ps -a -q)
```

### 查看容器的日志
- 查看当前全部日志

```bash
docker logs $ContainerName(或者$ContainerId)
```

- 动态查看日志

```bash
docker logs $ContainerName(或者$ContainerId) -f
```

### 查看容器的IP地址

```bash
docker inspect --format '{{ .NetworkSettings.IPAddress }}' $ContainerName(或者$ContainerId)
```

### 修改容器的启动方式

```bash
docker container update --restart=always $ContainerName
```

### 同步宿主机时间到容器

```bash
docker cp /etc/localtime $ContainerName(或者$ContainerId):/etc/
```

### 指定容器时区

```bash
docker run -p 80:80 --name nginx \
-e TZ="Asia/Shanghai" \
-d nginx:1.17.0
```

### 在宿主机查看docker使用cpu、内存、网络、io情况
- 查看指定容器情况：

```bash
docker stats $ContainerName(或者$ContainerId)
```
- 查看所有容器情况：

```bash
docker stats -a
```

### 查看Docker磁盘使用情况
```bash
docker system df
```

### 进入Docker容器内部的bash
```bash
docker exec -it $ContainerName /bin/bash
```

### 使用root帐号进入Docker容器内部
```bash
docker exec -it --user root $ContainerName /bin/bash
```

### Docker创建外部网络

```bash
docker network create -d bridge my-bridge-network
```

## 修改Docker镜像的存放位置

- 查看Docker镜像的存放位置：

```bash
docker info | grep "Docker Root Dir"
```

- 关闭Docker服务：

```bash
systemctl stop docker
```

- 移动目录到目标路径：

```bash
mv /var/lib/docker /mydata/docker
```

- 建立软连接：

```bash
ln -s /mydata/docker /var/lib/docker
```
