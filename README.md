# Nextflow Plugin template

A template for Nextflow plugin

This template can be util when you want to create (and publish) a plugin.

## Instructions

### download

Download and unzip this repository (no clone, only download) in a directory with the name of your new plugin, i.e.
`nf-my-awesome-plugin`

### config

In this new repository adjust these files:

```
.nf-plugin.gradle

nextflowPlugin{
    githubOrganization = 'YOUR_GITHUB_ACCOUNT'    
}
```

`githubOrganization` is used in case you will publish in GitHub your plugin.


### dependencies

Also in this file add your dependencies 

For example:

```
.nf-plugin.gradle

dependencies {
    implementation 'org.apache.poi:poi:5.2.4'
    implementation 'org.apache.poi:poi-ooxml:5.2.4'    
}
```

### source

Rename `src/main/groovy/com/nextflow/plugin` directory and `ExamplePlugin.groovy` file with the package and name of your plugin

## Build

And "this is all". You can build the plugin running `./gradlew build` for example

## Nextflow Tasks

In order to generate the required artifacts by Nextflow you can run `./gradlew jsonPlugin`

This task will generate the `zip` and `json` files required by Nextflow

