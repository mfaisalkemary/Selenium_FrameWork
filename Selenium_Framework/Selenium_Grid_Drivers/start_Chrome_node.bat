cd C:\Selenium_Grid 

start java -Dwebdriver.chrome.driver=C:\Selenium_Grid\Chrome\chromedriver.exe -jar C:\Selenium_Grid\selenium-server-standalone-3.0.1.jar  -role node -hub  http://localhost:4444/grid/register -browser browserName=chrome,platform=WIN7 -port 5557