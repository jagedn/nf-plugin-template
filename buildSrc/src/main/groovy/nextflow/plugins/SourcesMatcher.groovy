package nextflow.plugins

import org.gradle.api.Project
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer

class SourcesMatcher {

    Project project
    SourcesMatcher(Project project){
        this.project = project
    }


    String getPluginClassName(){
        return findSources(/class (\w+) extends BasePlugin/).first()
    }

    List<String> getPluginExtensions(){
        return findSources(/class (\w+) extends PluginExtensionPoint/)
    }

    List<String> getTraceObservers(){
        return findSources(/class (\w+) implements TraceObserverFactory/)
    }

    List<String> findSources( def regexp ){
        def sourceSets = project.extensions.getByType(SourceSetContainer)
        def mainSourceSet = sourceSets.findByName(SourceSet.MAIN_SOURCE_SET_NAME)
        def sources = mainSourceSet.allSource
        def root = project.projectDir

        def matcher = sources.findAll{
            def source = it.text
            def matcher = source =~ regexp
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
