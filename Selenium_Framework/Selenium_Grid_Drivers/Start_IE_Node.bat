cd C:\Selenium_Grid 
start java -Dwebdriver.ie.driver=C:\Selenium_Grid\IEDriverServer_x64_2.53.1\IEDriverServer.exe  -jar C:\Selenium_Grid\selenium-server-standalone-3.0.1.jar  -role webdriver -hub http://localhost:4444/grid/register -browser browserName=ie,platform=WIN7,maxSession=5 -port 5558
