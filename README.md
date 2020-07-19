# objectBox dao层
Add it in your root build.gradle at the end of repositories:
	allprojects {
		repositories {
			...
			maven { url 'https://www.jitpack.io' }
		}
	}
Copy
Step 2. Add the dependency
Subproject
	dependencies {
	        implementation 'com.github.NBXXF.xxf_database:xxf_objectbox:1.0.1'
	        implementation 'io.reactivex.rxjava2:rxjava:2.2.12'
	}
Copy
Share this release:
step3: rootProject buildGradle
    classpath "io.objectbox:objectbox-gradle-plugin:3.0.0-alpha2"
step4:app module or lib module add
apply plugin: 'io.objectbox' // apply last