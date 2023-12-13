package nextflow.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project

class NextflowPlugin implements Plugin<Project>{

    @Override
    void apply(Project target) {
        NextflowPluginExtension nextflowPluginExtension = target.extensions.create('nextflowPlugin',NextflowPluginExtension)
        target.tasks.register('zipPlugin', ZipPluginTask,{

        })
        target.tasks.register('unzipPlugin', UnzipPluginTask,{

        })
        target.tasks.register('jsonPlugin', JsonPluginTask, {
            downloadUrl = nextflowPluginExtension.downloadUrl
        })
        target.tasks.register('generateIdx', GenerateIdxTask, {
            extensionPoints = nextflowPluginExtension.extensionPoints
        })
        target.tasks.findByName("processResources").dependsOn(target.tasks.findByName("generateIdx"))
    }
}
