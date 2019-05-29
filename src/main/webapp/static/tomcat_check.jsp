<%@ page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8"%>
<html>
<head>
<meta name="Author" content="JoBin">
<title>Dbpool check</title>
<style>
a   {text-decoration:none}
a:visited    { color: blue }
a:hover { color: red;text-decoration:underline}
html    { line-height: 120% }
</style>
</head>
<body>
<%


%>
tomcat环境关键指标检测
<table border="1" width="50%" cellspacing="0" cellpadding="2">
<tr>
<td>当前活动线程数（建议不超过800）</td>
<td><%out.println(getActiveThread());%></td>
</tr>
<tr>
<td>当前HTTP并发线程数（建议不超过20）</td>
<td><%out.println(getCuncurrentThread());%></td>
</tr>
<tr>
<td>当前JAVA使用堆内存（建议不超过2500）</td>
<td><%out.println(getMemory());%></td>
</tr>
</table>
</body>
</html>
<%!
        public static long  getMemory(){
                System.gc();
                Runtime runtime = Runtime.getRuntime();
                long totalMemery = runtime.totalMemory();
                long usedMemory = runtime.totalMemory()-runtime.freeMemory();
                return usedMemory/(1024*1024);
        }

        public static int getActiveThread(){
                                                ThreadGroup parentThread;
            for (parentThread = Thread.currentThread().getThreadGroup(); parentThread.getParent()!= null; parentThread = parentThread.getParent());
            int totalThread = parentThread.activeCount();
            return totalThread;
        }

 public static int getCuncurrentThread(){
                int total = 0;
                java.util.Map  map = Thread.getAllStackTraces();
                java.util.Set set = map.keySet();
                java.util.Iterator items = null;
                StackTraceElement[] e = null;
                Thread t = null;
                items = set.iterator();
        while(items.hasNext()){
         t= (Thread) items.next();
                e =(StackTraceElement[]) map.get(t);
                if(e.length > 20){
                        total++;
                }
        }
        return total;
 }


%>