configure tomcat
----------------

[Tomcat 8.5.x](http://tomcat.apache.org/download-80.cgi)

用 eclipse 或者 idea 编译项目，生成目录

     |-----sflow-monitor  
         |----**.jsp
         |----**.jsp
         |----WEB-INF
             |----web.xml      
             |----classes   
             |----lib

 把生成目录sflow-monitor拷贝到 ： apache-tomcat-8.5.x/webapps/
 
 启动 tomcat : ./apache-tomcat-8.5.x/bin/startup.bat
  
 停止 tomcat ： ./apache-tomcat-8.5.x/bin/shutdown.bat
 
 注意 ： 
 
 * 编译的 JDK 版本最好和服务器的一致, 统一用 openjdk 1.8.x
 * 运行一次测试脚本，让 tomcat 把 jsp 编译一遍
      
      cd sflow-monitor/test
      python3 run_all.py
 
 
 此时服务地址 ： http://localhost:8080/sflow-monitor/api
 
 
 
 