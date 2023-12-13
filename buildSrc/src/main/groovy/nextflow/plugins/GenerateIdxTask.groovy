package nextflow.plugins

import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction


abstract class GenerateIdxTask extends DefaultTask{

    @Internal
    abstract ListProperty<String> extensionPoints

    @OutputFile
    final abstract RegularFileProperty outputFile =
            project.objects.fileProperty().convention(project.layout.buildDirectory.file(
                    "resources/main/META-INF/extensions.idx"))

    GenerateIdxTask() {
        setGroup('nextflow')
    }

    @TaskAction
    def runTask() {
        def output = outputFile.get().asFile

        if( extensionPoints.getOrElse([]).size() ){
            output.text = extensionPoints.getOrElse([]).join('\n')
            return
        }

        def matcher = new SourcesMatcher(project)
        def extensionsClassName = matcher.pluginExtensions
        def traceClassName = matcher.traceObservers
        def all = extensionsClassName+traceClassName
        output.text = all.join('\n')
    }

}
