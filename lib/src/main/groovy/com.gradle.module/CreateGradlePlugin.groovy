package com.gradle.module;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
/**
 * Created by chenwenlu on 2016/4/14.
 */
public class CreateGradlePlugin implements Plugin<Project>{
    private CreateModule createModule;
    @Override
    void apply(Project project) {
        project.extensions.create("moduleConfig",GradlePluginExtensions)
         GradlePluginExtensions gp=project.extensions.getByName("moduleConfig");
        gp.rootProjectPath=project.rootDir.absolutePath
      //  println(gp.rootProjectPath)
      //  println(gp.moduleName)
        project.task("create")<<{
            if(createModule==null){
                createModule=new CreateModule(gp);
            }
            createModule.create();
        }
    }
}
