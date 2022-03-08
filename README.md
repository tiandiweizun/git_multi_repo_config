<h1>Git多仓库配置</h1>

<b>目录</b>

- <a href="#安装">1. 安装</a>
- <a href="#源码编译">&nbsp;&nbsp;1.1 源码编译</a>
- <a href="#直接下载">&nbsp;&nbsp;1.2 直接下载</a>
- <a href="#配置说明">2. 配置说明</a>

<a name=安装> <h2>1. 安装</h2> </a>
<a name=源码编译> <h4>1.1 源码编译</h4> </a>

**要求:**
```
JDK >= 11   #使用命令行编译需要注意JAVA_HOME的设置，如果使用IDEA，需要在设置(Settings)内部配置
intellij >= 2020.1.3 
```

更早版本需要修改build.gradle
```groovy
plugins {
    id 'java'
    // 需要把这里的xxx替换成符合要求的版本
    id 'org.jetbrains.intellij' version 'xxx'
}
```
**步骤:**
1. `git clone https://github.com/tiandiweizun/git_multi_repo_config.git`
2. `cd git_multi_repo_config`
3. (windows) &nbsp;`.\gradlew.bat jar`  &nbsp;&nbsp;(linux) &nbsp;`./gradlew jar` 
5. 编译好的jar包位于 `./build/libs`

<a name=直接下载> <h4>1.2 直接下载</h4> </a>
点击下载[git_multi_repo_config-0.1.jar](https://github.com/tiandiweizun/git_multi_repo_config/releases/download/v0.1/git_multi_repo_config-0.1.jar)

**把上述编译好的或者下载的jar包拖拽到JetBrains产品（或者依次点击 文件-->设置-->插件-->从本地安装），重启即可**

<a name=配置说明> <h2>2. 配置说明</h2> </a>
配备页面包含3个字段，分别是repository,name,email，规则是当前项目的远程仓库的url包含repository时，就以repository对应的name和email去提交。大部分人可能是github一个，公司一个，所以github的repository写github，公司的repository写公司的仓库域名即可；如果不同仓库的name和email不一样，那么repository就应该写项目名称。为了避免冲突，地址越详细的应该写在前面。

<hr>

**TODO**
1. 如果没有设置远程仓库，支持以本地的文件路径进行处理
2. 动态支持添加更多的repository,name,email配置
3. 支持配置其他的字段，比如ssh key等
