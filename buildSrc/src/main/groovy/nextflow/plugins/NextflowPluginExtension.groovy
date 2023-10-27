package nextflow.plugins

import org.gradle.api.Project
import org.gradle.api.provider.Property

class NextflowPluginExtension {

    final Property<String> downloadUrl

    final Property<String> nextflowVersion

    final Property<String> githubOrganization

    final Property<String> pluginClassName

    final private Project project

    NextflowPluginExtension(Project project){
        this.project = project
        nextflowVersion = project.objects.property(String)
        downloadUrl = project.objects.property(String)
        githubOrganization = project.objects.property(String)
        pluginClassName = project.objects.property(String)
    }

    void setNextflowVersion(String nextflowVersion){
        this.nextflowVersion.set(nextflowVersion)
    }

    String getNextflowVersion(){
        this.nextflowVersion.getOrElse("23.04.0")
    }

    void setDownloadUrl(String downloadUrl){
        this.downloadUrl.set(downloadUrl)
    }

    String getDownloadUrl() {
        return downloadUrl.getOrElse("https://github.com/${getGithubOrganization()}/${project.name}/releases/download")
    }

    String getGithubOrganization() {
        return githubOrganization.get()
    }

    void setGithubOrganization(String githubOrganization){
        this.githubOrganization.set(githubOrganization)
    }

    String getPluginClassName() {
        return pluginClassName.get()
    }

    void setPluginClassName(String pluginClassName){
        this.pluginClassName.set(pluginClassName)
    }

}
