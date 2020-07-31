package com.cmlx.commons.pool

import java.sql.{Connection, DriverManager, ResultSet}

import org.apache.commons.pool2.impl.DefaultPooledObject
import org.apache.commons.pool2.{BasePooledObjectFactory, PooledObject}


// 创建用于处理MySQL查询结果的类的抽象接口
/**
  * trait相当于Java的接口，实际上它比接口功能强大
  * 与接口不同，它还可以定义属性和方法的实现
  * 一般情况下Scala的只能够继承单一父类，但是如果是Trait（特征）的话就可以继承多个，从结果来看就是实现了多重继承
  * Scala Trait 更像Java的抽象类
  */
trait QueryCallback {
  def process(rs: ResultSet)
}

/**
  * MySQL客户端代理对象
  *
  * @param jdbcUrl
  * @param jdbcUser
  * @param jdbcPassword
  * @param client 默认客户端实现
  */
case class MySqlProxy(jdbcUrl: String, jdbcUser: String, jdbcPassword: String, client: Option[Connection] = None) {

  // 获取客户端连接对象
  private val mysqlClient = client getOrElse {
    DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword)
  }

  // 关闭MySQL客户端
  def shutdown(): Unit = mysqlClient.close()


}


/**
  * @Desc 将MySqlProxy实例视为对象，MySqlProxy实例的创建使用对象池进行维护
  * @Author cmlx
  * @Date 2020-7-31 0031 16:42
  */

/**
  * 创建自定义工厂类，继承BasePooledObjectFactory工厂类，负责对象的创建、包装和销毁
  *
  * @param jdbcUrl
  * @param jdbcUser
  * @param jdbcPassword
  * @param client
  */
class PooledMySqlClientFactory(jdbcUrl: String, jdbcUser: String, jdbcPassword: String, client: Option[Connection] = None) extends BasePooledObjectFactory[MySqlProxy] with Serializable {

  // 用于池来创建对象
  override def create(): MySqlProxy = MySqlProxy(jdbcUrl, jdbcUser, jdbcPassword, client)

  // 用于池来包装对象
  override def wrap(obj: MySqlProxy): PooledObject[MySqlProxy] = new DefaultPooledObject(obj)

  // 用于池来销毁对象
  override def destroyObject(p: PooledObject[MySqlProxy]): Unit = {
    p.getObject.shutdown()
    super.destroyObject(p)
  }
}


/**
  * 创建MySQL池工具类
  */
object CreateMySqlPool {

  // 加载JDBC驱动，只需要一次
  Class.forName("com.mysql.jdbc.Driver")




}































