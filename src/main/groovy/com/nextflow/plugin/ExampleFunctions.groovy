package com.nextflow.plugin

import groovy.transform.CompileStatic
import nextflow.plugin.extension.Function
import nextflow.plugin.extension.PluginExtensionPoint
import nextflow.Session

@CompileStatic
class ExampleFunctions extends PluginExtensionPoint{

    private Session session

    @Override
    protected void init(Session session) {
        this.session = session
    }


    /*
     *  Nextflow Function example
     * Generate a random string
     *
     * Using @Function annotation we allow this function can be imported from the pipeline script
     */
    @Function
    String randomString(int length=9){
        new Random().with {(1..length).collect {(('a'..'z')).join(null)[ nextInt((('a'..'z')).join(null).length())]}.join(null)}
    }

}
