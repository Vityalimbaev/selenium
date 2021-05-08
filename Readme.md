# Сборочный процесс
Для сборки требуется <b>Maven</b> !!! <br> 
В корне проекта выполнить команду: <i>mvn package</i> <br>
По окончанию сборки, билд будет находиться в корне проекта: <br>
<b> target/Project-1.0.jar</b>

# Запуск
Для запуска приложения требуеся выполнить след. комманду в корне проекта: <br>
<b>java -DisRemote=true -Dmode=dev -jar target/Project-1.0.jar</b>

# Переменные

### -DisRemote

* <b>true</b> - запускать тесты через удаленный сервис https://crossbrowsertesting.com/ <br>
* <b>false</b> - запускать локально

### -Dmode
* <b>dev</b> - запускает тесты для <i> DEV </i> приложений
* <b>prod</b> - запускает тесты для <i> PROD </i> приложений

# Отчёт
По заввершению выполнения программы будет сгенерирован отчёт в корне проекта: <br>
<b> test-output/emailable-report.html </b>

# Доступ к Jenkins
* Ресурс: http://jenkins.dev.alfatell.ru/
* Логин: admin
* Пароль: Safe4root123
