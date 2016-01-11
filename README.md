# SpringSecurity
Sample project created.
This sample project is created to study Spring Security. Currently this project has one sample controller.

# Things learned:
1. After creating the project using maven spring webapp, run the command to make the project recognisable as web project by eclipse
mvn eclipse:eclipse -Dwtpversion=2.0
2. Once the above command is run, right click on the project -> maven -> update project.
3. Now your project will be ready to deploy in tomcat in eclipse.
4. There is a difference between / and /* in web.xml for dispatcher servlet. If the application has only REST API then it doesn't
matter which one you use. But if we have views then better use '/'. Otherwise it is not working. The reason and posible solution is explained here http://stackoverflow.com/questions/17897790/no-mapping-found-for-http-request-with-uri-spring-mvc and here http://stackoverflow.com/questions/1266303/no-mapping-found-for-http-request-with-uri-web-inf-pages-apiform-jsp. Also another fix is to add <mvc:default-servlet-handler/> to servlet-context.xml.
