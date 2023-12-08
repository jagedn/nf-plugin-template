package nextflow.plugins

import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.TaskAction


class GenerateIdxTask extends DefaultTask{


    @OutputFile
    final abstract RegularFileProperty outputFile =
            project.objects.fileProperty().convention(project.layout.buildDirectory.file(
                    "resources/main/META-INF/extensions.idx"))

    GenerateIdxTask() {
        setGroup('nextflow')
    }

    @TaskAction
    def runTask() {
        def matcher = new SourcesMatcher(project)
        def extensionsClassName = matcher.pluginExtensions
        def output = outputFile.get().asFile
        output.text = extensionsClassName.join('\n')
    }

}
