package nextflow.plugins


import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.bundling.Jar


class ZipPluginTask extends Jar{

    @OutputFile
    final abstract RegularFileProperty outputFile =
            project.objects.fileProperty().convention(project.layout.buildDirectory.file(
                    "plugin/${project.name}-${project.version}.zip"))

    ZipPluginTask() {
        setGroup('nextflow')

        dependsOn(project.tasks.findByName('build'))
        dependsOn(project.tasks.generateIdx)

        into( 'classes',{
            with project.tasks.findByName('jar')
        })

        into('lib',{
            from(project.configurations.find {it.name=='runtimeClasspath'})
        })

        manifest {
            Jar jar = project.tasks.findByName('jar') as Jar
            from(jar.manifest)
        }

        archiveExtension.set('zip')
        setPreserveFileTimestamps(false)
        setReproducibleFileOrder(true)

        def directory = project.objects.fileProperty().convention(
                project.layout.buildDirectory.file("plugin"))
        getDestinationDirectory().set(directory.get().asFile)
    }

}
