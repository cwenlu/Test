package com.gradle.module

import org.codehaus.groovy.control.messages.ExceptionMessage;
/**
 * Created by chenwenlu on 2016/4/14.
 */
public class CreateModule {
    GradlePluginExtensions gp;

    CreateModule(GradlePluginExtensions gp) {
        this.gp = gp
        if(gp==null){
            throw RuntimeException('没有配置相关参数')
        }
        if(gp.rootProjectPath==null || gp.moduleName==null || gp.packageName==null){
            throw RuntimeException('相关参数没有配置完全')
        }
    }

    public void create(){
        createBase()
    }

    private void createBase(){
        //基础目录机构
        def rootPath=gp.rootProjectPath+'/'+gp.moduleName
        File fileLib=new File(rootPath+'/libs');
        File fileSrcMain=new File(rootPath+'/src/main/groovy/'+gp.packageName);
        File fileSrcRes=new File(rootPath+'/src/main/resources/META-INF.gradle-plugins')
        fileLib.mkdirs()
        fileSrcMain.mkdirs()
        fileSrcRes.mkdirs()

        //配置文件
        if(gp.pluginName!=null){
            File properties=new File(fileSrcRes.getAbsolutePath()+'/'+gp.pluginName+'.properties')
            properties.createNewFile()
            properties.withWriter {
                ww->
                    ww.write('#implementation-class=')
            }

        }

        //build文件
        File build=new File(rootPath+'/build.gradle')
        build.createNewFile()
        build.withWriter {
            ww->
                ww.write('''apply plugin: 'java\'
apply plugin: 'groovy\'
dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    compile gradleApi()
    compile localGroovy()
}''')
        }

        //添加到主项目
        File setting=new File(gp.rootProjectPath+'/settings.gradle')
        String s
        setting.withReader{
           reader->
                s=reader.readLine()

        }
        String m=", ':"+gp.moduleName+"'"
        if(!s.contains(m)){
            setting.withWriter {
                ww->
                    ww.write(s+m)

            }
        }

    }

}
