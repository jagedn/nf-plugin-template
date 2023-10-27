package nextflow.plugins

import groovy.json.JsonOutput
import org.apache.commons.codec.digest.DigestUtils
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.bundling.Jar

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

abstract class JsonPluginTask extends DefaultTask{

    @Internal
    abstract Property<String> getDownloadUrl()

    @Internal
    final abstract RegularFileProperty zipFile =
            project.objects.fileProperty().convention(project.layout.buildDirectory.file(
                    "plugin/${project.name}-${project.version}.zip"))

    @OutputFile
    final abstract RegularFileProperty outputFile =
            project.objects.fileProperty().convention(project.layout.buildDirectory.file(
                    "plugin/${project.name}-${project.version}-meta.json"))


    JsonPluginTask(){
        setGroup('nextflow')
        dependsOn 'zipPlugin'
    }


    protected String resolveURL(){
        "${downloadUrl.get()}/${project.version}/${project.name}-${project.version}.zip"
    }

    protected static String now() {
        OffsetDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
    }

    protected static String computeSha512(File file) {
        if( !file.exists() )
            throw new GradleException("Missing file: $file -- cannot compute SHA-512")
        return DigestUtils.sha512Hex(file.bytes)
    }

    @TaskAction
    def runTask() {
        def jarTask = project.tasks.findByName('jar') as Jar
        def manifest = jarTask.manifest
        def json = [
                version: "$project.version",
                date: now(),
                url: resolveURL(),
                requires: manifest.attributes['Plugin-Requires'],
                sha512sum: computeSha512(zipFile.get().asFile)
        ]
        outputFile.get().asFile.text = JsonOutput.prettyPrint(JsonOutput.toJson(json))
    }

}
