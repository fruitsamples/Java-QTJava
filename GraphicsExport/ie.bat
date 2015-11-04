javac src/*.java
echo Main-Class: GraphicsExport> manifest
cd src
jar cmf ..\manifest ..\GraphicsExport.jar *.class
cd ..
java -jar GraphicsExport.jar GraphicsExport