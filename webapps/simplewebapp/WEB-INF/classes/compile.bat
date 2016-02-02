javac -cp .;"..\..\..\..\lib\*" *.java
javac -cp .;"..\..\..\..\lib\*" simplewebapp\*.java
jar vcf ..\lib\simplewebapp.jar simplewebapp\*.class
exit