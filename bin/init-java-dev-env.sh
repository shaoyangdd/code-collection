#!bin/bash
#cent os 系统环境（JAVA开发环境）初始化脚本
#安装JDK
echo "创建jdk安装目录/usr/java..."
mkdir /usr/java
echo "进入jdk安装目录/usr/java..."
cd /usr/java
echo "开始下载jdk1.8安装包..."
wget --no-check-certificate --no-cookies --header "Cookie: oraclelicense=accept-securebackup-cookie"  http://download.oracle.com/otn-pub/java/jdk/8u161-b12/2f38c3b165be4555a1fa6e98c45e0808/jdk-8u161-linux-i586.tar.gz
echo "重命名jdk1.8安装包..."
mv jdk-8u161-linux-i586.tar.gz* jdk-8u161-linux-i586.tar.gz
echo "开始解压jdk1.8安装包..."
tar -zxvf jdk-8u161-linux-i586.tar.gz
echo "配置JDK环境变量..."
cat >>/etc/profile << EOF
# jdk1.8
JAVA_HOME=/usr/java/jdk1.8.0_161
JRE_HOME=/usr/java/jdk1.8.0_161/jre
CLASS_PATH=.:\$JAVA_HOME/lib/dt.jar:\$JAVA_HOME/lib/tools.jar:\$JRE_HOME/lib
PATH=\$PATH:\$JAVA_HOME/bin:\$JRE_HOME/bin
export JAVA_HOME JRE_HOME CLASS_PATH PATH
EOF
#JDK 1.8永久生效
echo "JDK 1.8永久生效..."
source /etc/profile
echo "JDK 1.8安装完成测试..."
java -version

#安装VSFTP
echo "开始安装VSFTPD..."
yum install vsftpd
echo "启动VSFTPD..."
service vsftpd start
echo "开机启动VSFTPD..."
chkconfig vsftpd on

#关闭防火墙
echo "关闭防火墙..."
service iptables stop
echo "开机关闭防火墙..."
chkconfig iptables off



