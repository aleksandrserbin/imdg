package com.imdg.core.configuration

import java.io.{File, FileInputStream, FileNotFoundException}

import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor


class YamlConfigurationSource {

  def initConfig() = {
    val streamOption = loadFile()
    if (streamOption.nonEmpty) {
      val inputStream = streamOption.get

      val yaml = new Yaml(new Constructor(classOf[Configuration]))
      val configuration = yaml.load(inputStream).asInstanceOf[Configuration]
      Configuration.configuration = configuration
    }
  }


  def loadFile() : Option[FileInputStream] = {
    try {
      val jarPath : File = new File(classOf[YamlConfigurationSource]
                                  .getProtectionDomain.getCodeSource.getLocation.getPath)
      val parentPath = jarPath.getParentFile.getAbsolutePath
      Some(new FileInputStream(parentPath + "/config.yaml"))
    } catch {
      case e : FileNotFoundException =>
        print("No config file found")
        None
      case e : Throwable =>
        print("Unexpected exception:")
        e.printStackTrace()
        print("Continuing")
        None
    }
  }




}
