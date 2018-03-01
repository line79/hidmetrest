@REM produkcija
set serverName=openshift

:begin

call mvn clean
echo Exit Code = %ERRORLEVEL%
echo.
if not "%ERRORLEVEL%" == "0" goto :end

call mvn compile
echo Exit Code = %ERRORLEVEL%
echo.
if not "%ERRORLEVEL%" == "0" goto :end

call mvn test-compile
echo Exit Code = %ERRORLEVEL%
echo.
if not "%ERRORLEVEL%" == "0" goto :end

call mvn package -DskipTests -DserverName=%serverName%
echo Exit Code = %ERRORLEVEL%
echo.

:end

@PAUSE