package com.cmlx.commons.config

import org.apache.commons.configuration2.PropertiesConfiguration
import org.apache.commons.configuration2.FileBasedConfiguration
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder
import org.apache.commons.configuration2.builder.fluent.Parameters

/**
  * @Desc 配置信息工具类【friend.properties】
  * @Author cmlx
  * @Date 2020-7-31 0031 15:29
  */
object ConfigurationManager {

  // 创建用于初始化配置生成器的参数对象
  private val params = new Parameters()
  //FileBasedConfigurationBuilder:产生一个传入的类的实例对象
  //FileBasedConfiguration:融合FileBased与Configuration的接口
  //PropertiesConfiguration:从一个或者多个文件读取配置的标准配置加载器
  //Configure():通过params实例初始化配置生成器
  //向FileBaseConfigurationBuilder()中传入一个标准配置加载器类，生成一个加载器的实例对象，然后通过param参数对其初始化
  private val builder = new FileBasedConfigurationBuilder[FileBasedConfiguration](classOf[PropertiesConfiguration])
    .configure(params.properties().setFileName("friend.properties"))

  //通过getConfiguration获取配置对象
  val config = builder.getConfiguration()

}
