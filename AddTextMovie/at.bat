javac src/*.java
echo Main-Class: AddText> manifest
jar cmf manifest AddText.jar -C src AddText.class
java -jar AddText.jar AddText