buildscript {

    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        this dependents Dependencies.projectDependencies
        this dependent Dependencies.projectHilt
    }
}
tasks.register("clean", Delete::class){
    delete(rootProject.buildDir)
}