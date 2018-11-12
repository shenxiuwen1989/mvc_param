




1.
@RequestParam
用来处理Content-Type: 为 application/x-www-form-urlencoded或者form-data编码的内容。（Http协议中，如果不指定Content-Type，则默认传递的参数就是application/x-www-form-urlencoded类型）
RequestParam可以接受简单类型的属性，也可以接受对象类型。 
实质是将Request.getParameter() 中的Key-Value参数Map利用Spring的转化机制ConversionService配置，转化成参数接收对象或字段。
在Content-Type: application/x-www-form-urlencoded的请求中， 
get 方式中queryString的值，和post方式中 body data的值都会被Servlet接受到并转化到Request.getParameter()参数集中，所以@RequestParam可以获取的到。
@RequestBody
处理HttpEntity传递过来的数据，一般用来处理非Content-Type: application/x-www-form-urlencoded编码格式的数据。
GET请求中，因为没有HttpEntity，所以@RequestBody并不适用。
POST请求中，通过HttpEntity传递的参数，必须要在请求头中声明数据的类型Content-Type，SpringMVC通过使用HandlerAdapter 配置的HttpMessageConverters来解析HttpEntity中的数据，然后绑定到相应的bean上。

2.Dozer

3.加解密工具和解释

4.返回参数示例 json.java

5.apache常见工具类

6.freemarker解析 FreemarkerController


7.spring boot 获取 application.properties/application.yml的方式
    1.根据环境获取
    String ip = ApplicationContextUtil.context.getEnvironment().getProperty("spring.data.redis.ip");
    2. @Component + @Value
    3.

8.@PostConstruct和@Autowired和Constructor执行顺序
    Constructor >> @Autowired >> @PostConstruct
     
9.远程调用
    1.JDK中url
    2.httpClient
    3.HttpClient4.2 Fluent
        https://blog.csdn.net/vector_yi/article/details/24298629
        
10.初始化启动
    Application中main方法
    public static void main(String[] args) {
            ApplicationContextUtil.context = SpringApplication.run(Application.class);
            //
            Local.init();
    }        

