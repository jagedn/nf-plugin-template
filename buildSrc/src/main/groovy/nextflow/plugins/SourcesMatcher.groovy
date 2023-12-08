package nextflow.plugins

import org.gradle.api.Project
import org.gradle.api.file.SourceDirectorySet
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer

class SourcesMatcher {

    Project project
    SourcesMatcher(Project project){
        this.project = project
    }


    String getPluginClassName(){
        def sourceSets = project.extensions.getByType(SourceSetContainer)
        def mainSourceSet = sourceSets.findByName(SourceSet.MAIN_SOURCE_SET_NAME)
        def sources = mainSourceSet.allSource
        def root = project.projectDir

        def matcher = sources.find{
            def source = it.text
            def matcher = source =~ /class (\w+) extends BasePlugin/
            if( matcher.size() != 1 ){
                return null
            }
            it
        }
        if( !matcher )
            return null

        def source = matcher.toString() - "$root.absolutePath/src/main/"
        return source.split('\\.').dropRight(1).join().split(File.separator).drop(1).join('.')
    }

    List<String> getPluginExtensions(){
        def sourceSets = project.extensions.getByType(SourceSetContainer)
        def mainSourceSet = sourceSets.findByName(SourceSet.MAIN_SOURCE_SET_NAME)
        def sources = mainSourceSet.allSource
        def root = project.projectDir

        def matcher = sources.findAll{
            def source = it.text
            def matcher = source =~ /class (\w+) extends PluginExtensionPoint/
            if( matcher.size() != 1 ){
                return null
            }
            it
        }
        matcher.collect { file ->
            def source = file.toString() - "$root.absolutePath/src/main/"
            return source.split('\\.').dropRight(1).join().split(File.separator).drop(1).join('.')
        }
    }

}
