@echo off
echo.
echo ******************************

:: Derby information
:: http://docs.oracle.com/javadb/10.10.1.2/getstart/getstartderby.pdf
:: http://docs.oracle.com/javadb/

:: https://www.codatlas.com/github.com/apache/derby/HEAD/java/demo/toursdb/ToursDB_schema.sql?line=12
:: compare with blog.war/portal.sql

:: Run THIS setenv.bat,
:: then run the derby networked server from the directory containing the (portal) db directory:
:: java -jar "%DERBY_HOME%\lib\derbyrun.jar" server start
:: In a new terminal, run this setenv.bat again and then run:
:: ij
:: CONNECT 'jdbc:derby://localhost:1527/portal;create=true';
:: > run 'derbyportal.sql';
:: which will create the tables for the "blog.war" jsp blog application

FOR %%A IN ("C:\Program Files\Java\jdk1.8.0*") DO (
 echo %%A
 set JAVA_HOME=%%A
 goto done
)
:done

if not exist "%JAVA_HOME%" echo %JAVA_HOME% does not exist. Need Java 8& goto done

set DERBY_HOME=%JAVA_HOME%\db

set PATH=%JAVA_HOME%\bin;%DERBY_HOME%\bin;%PATH%

echo Using JAVA_HOME: %JAVA_HOME%
echo and DERBY_HOME: %DERBY_HOME%

:done
echo ******************************
echo.

startNetworkServer