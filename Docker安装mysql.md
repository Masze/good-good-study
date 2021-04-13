# Docker安装MySQL 8.0并挂载数据及配置文件

## 安装部署环境

- CentOS 7.5 64位
- Docker 20.10.5
- MySQL latest

## 下载镜像

```bash
# docker从仓库中拉取mysql镜像，如果没加标签的话，默认获取最新的版本
Docker pull mysql
```

## 挂载数据卷以及配置文件

Docker容器原则上是短暂的，如果容器被删除或损坏，任何数据或配置都将丢失。因为，我们需要将 Docker 内的 MySQL 数据文件以及配置文件转移到宿主机的指定路径。

每个人部署的 MySQL 内，可能文件路径不一致。我们可以先创建个测试的 MySQL 容器，然后再根据查找出的文件具体路径位置，重新创建我们符合我们需求的 MySQL 容器，命令如下：

```bash
docker run --name mysqltest \
-p 3307:3306 -e MYSQL_ROOT_PASSWORD=root \
-d mysql
```

### 进入Docker容器内

```bash
docker exec -it mysqltest bash
```

### 确定Docker内 MySQL 文件相关路径

根据[官网说明](https://dev.mysql.com/doc/refman/8.0/en/docker-mysql-more-topics.html#docker-persisting-data-configuration)：如果要挂载 MySQL 配置文件的话，我们必须在物理机上存在着该配置文件。

```bash
# 查找Docker内，MySQL配置文件my.cnf的位置
mysql --help | grep my.cnf
# 显示如下,意思是路径按优先排序，会是在以下路径里：
order of preference, my.cnf, $MYSQL_TCP_PORT,
/etc/my.cnf /etc/mysql/my.cnf ~/.my.cnf
# 配置文件的路径不一定都一样，有些博客介绍的位置是在/etc/my.cnf。而本人Ubuntu系统上，实际存在位置是在/etc/mysql/my.cnf
```

查找数据文件位置

用于在容器上运行**docker inspect**命令的JSON输出具有一个 `Mount`密钥，其值提供了有关数据目录卷的信息：

```bash
docker inspect mysqltest 

...
 "Mounts": [
            {
                "Type": "volume",
                "Name": "4f2d463cfc4bdd4baebcb098c97d7da3337195ed2c6572bc0b89f7e845d27652",
                "Source": "/var/lib/docker/volumes/4f2d463cfc4bdd4baebcb098c97d7da3337195ed2c6572bc0b89f7e845d27652/_data",
                "Destination": "/var/lib/mysql",
                "Driver": "local",
                "Mode": "",
                "RW": true,
                "Propagation": ""
            }
        ],
...
```

输出显示源文件夹：/var/lib/docker/volumes，表示已安装在 `/var/lib/mysql`容器内的服务器数据目录中。

### 创建本地路径并挂载Docker内数据

接下来，我们需要在物理机上，创建指定好一个数据和配置文件的挂载路径。

```bash
mkdir -p /home/docker/mysql/conf && mkdir -p /home/docker/mysql/datadir
```

创建好本地的挂载数据路径后，我们再将测试容器里 MySQL 的配置文件复制到该路径。日后需改配置，直接在挂载路径的配置文件上修改即可。

```bash
docker cp mysqltest:/etc/mysql/my.cnf /home/docker/mysql/conf
```

## 创建 MySQL 容器并启动

解决了配置文件的问题，就可以根据需求创建我们的 MySQL 容器并挂载数据了。

```shell
docker run --name mysql \
-p 3306:3306 -e MYSQL_ROOT_PASSWORD=root \
--mount type=bind,src=/home/docker/mysql/conf/my.cnf,dst=/etc/mysql/my.cnf \
--mount type=bind,src=/home/docker/mysql/datadir,dst=/var/lib/mysql \
--restart=on-failure:3 \
-d mysql
```

- **--name**：为容器指定一个名字
- **-p**：指定端口映射，格式为：**主机(宿主)端口:容器端口**
- **-e**：username="xxx"，设置环境变量
- **--restart=on-failure:3**：是指容器在未来出现异常退出（退出码非0）的情况下循环重启3次
- **--mount**：绑定挂载
- **-d**：后台运行容器，并返回容器 id

> 网上许多人的博客写的挂载 MySQL数据卷方法，采用的是`--volume`，但是在最新的MySQL官方Docker搭建MySQL文档中，建议我们大家使用`--mount`，因为研究表明它更易于使用。故我们根据[官网推荐](https://docs.docker.com/storage/bind-mounts/)的方法，这里也采用`--mount`挂载。

## 查看在运行的容器

```shell
docker ps 
```

```shell
# docker ps
CONTAINER ID   IMAGE     COMMAND                  CREATED          STATUS          PORTS                               NAMES
6d054c3be59f   mysql     "docker-entrypoint.s…"   11 minutes ago   Up 11 minutes   0.0.0.0:3306->3306/tcp, 33060/tcp   mysql

```


