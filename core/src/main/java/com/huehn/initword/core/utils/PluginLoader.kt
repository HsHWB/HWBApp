package com.huehn.initword.core.utils

import android.content.Context
import com.huehn.initword.core.utils.File.GetPathUtils
import com.huehn.initword.core.utils.Log.LogManager
import dalvik.system.DexClassLoader
import java.io.File
import java.lang.reflect.Array.newInstance as new
import java.lang.reflect.Field

object PluginLoader {

   const val TAG = "PluginLoader"

   fun loadPlugin(pluginName: String, context: Context){
      var path = PluginMap.getValue(pluginName)
      LogManager.d("huehn loadPlugin path : $path")
      var file: File = File(path)
      LogManager.d("huehn loadPlugin name : " + file.exists())
      kotlin.runCatching {

         //1、反射BaseDexClassLoader 的DexPathList，因为findClass里面就是调用了的DexPathList的findClass
         //2、反射DexPathList 里面的Element[]
         //3、将宿主和插件的dex都放到第2步的宿主里面去
         //4、将第3步的数组，设置进DexPathList

         //反射DexPathList
         var baseDexClazz : Class<*> = Class.forName("dalvik.system.BaseDexClassLoader")
         var dexPathListField : Field = baseDexClazz.getDeclaredField("pathList")
         dexPathListField.isAccessible = true

         //反射DexPathList里面的成员变量dexElements
         var dexPathClazz : Class<*> = Class.forName("dalvik.system.DexPathList")
         var dexElementsField : Field = dexPathClazz.getDeclaredField("dexElements")
         dexElementsField.isAccessible = true

         //宿主的classLoader
         var hostClassLoader = context.classLoader
         //插件的classLoader
         var pluginClassLoader : DexClassLoader = DexClassLoader(path, null, null, hostClassLoader)

         //获取宿主和插件的DexPathList
         var hostDexPathList = dexPathListField.get(hostClassLoader)
         var pluginDexPathList = dexPathListField.get(pluginClassLoader)

         //获取宿主和插件的DexPathList里面的Element[]
         var hostDexElements = dexElementsField.get(hostDexPathList)
         var pluginDexElements = dexElementsField.get(pluginDexPathList)

         //合并成一个新的数组，赋值给宿主的Element[]
         if (hostDexElements is Array<*> && pluginDexElements is Array<*>) {
            val newElements = new(hostDexElements.javaClass.getComponentType(), hostDexElements.size + pluginDexElements.size) as Array<Any>

            System.arraycopy(hostDexElements, 0, newElements, 0, hostDexElements.size)
            System.arraycopy(pluginDexElements, 0, newElements, hostDexElements.size, pluginDexElements.size)
            LogManager.d("huehn loadPlugin size : ${newElements.size}")

            //把新数组放到宿主里面去
            dexElementsField.isAccessible = true
            dexElementsField.set(hostDexPathList, newElements)
         }
      }.onFailure {
         it.printStackTrace()
      }

   }

   fun getHostElements(context: Context) : Any {
      //反射DexPathList
      var baseDexClazz : Class<*> = Class.forName("dalvik.system.BaseDexClassLoader")
      var dexPathListField : Field = baseDexClazz.getDeclaredField("pathList")
      dexPathListField.isAccessible = true

      //反射DexPathList里面的成员变量dexElements
      var dexPathClazz : Class<*> = Class.forName("dalvik.system.DexPathList")
      var dexElementsField : Field = dexPathClazz.getDeclaredField("dexElements")
      dexElementsField.isAccessible = true

      //宿主的classLoader
      var hostClassLoader = context.classLoader

      //获取宿主和插件的DexPathList
      var hostDexPathList = dexPathListField.get(hostClassLoader)

      //获取宿主和插件的DexPathList里面的Element[]
      var hostDexElements = dexElementsField.get(hostDexPathList)

      return hostDexElements

   }


   enum class PluginMap(var pluginName: String, var pluginPath: String) {

      SMALL_APP("small", GetPathUtils.getAppExternalAbsolutePath() + "/Download/plugin.apk"),
      NIMO_APP("nimo", GetPathUtils.getAppExternalAbsolutePath() + "/plugin.apk");


      companion object {
         fun getValue(key: String): String {
            var pluginMap : Array<PluginMap> = PluginMap.values()
            pluginMap.forEach {
               it.takeIf {
                  it.pluginName == key
               }?.let {
                  return it.pluginPath
               }
            }
            return ""
         }
      }
   }
}