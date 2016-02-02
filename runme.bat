cd webapps\simplewebapp\WEB-INF\classes

start /wait compile.bat

cd ../../../../

start setenvwithnetworkserver.bat

java -jar start.jar