javac src/*.java
echo Main-Class: ImageFileDemo> manifest
cd src
jar cmf ..\manifest ..\ImageFileDemo.jar *.class
cd ..
java -jar ImageFileDemo.jar ImageFileDemo