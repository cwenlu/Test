# Test
学习gradle插件编写的一个自动创建gradle module的插件
dependencies {
        classpath 'com.gradle.module:lib:1.0.0'
    }
    
apply plugin: 'gradle-module'

moduleConfig{
//创建的插件module的名字
    moduleName='haha'
    //包名
    packageName='com.gradle.module'
    //插件民字（不是必须）
    pluginName='plugin'
}
