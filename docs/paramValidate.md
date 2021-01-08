1. ValidateUtils
2. 扩展validation-api注解，解决不同情形校验：如集合和自定义方法
3. 结合AOP，实现service参数列表校验
4. validation框架支持嵌套验证，即验证对象中的对象   
    除了外层使用@Valid或@Validated，还是需要在属性上加上@Valid注解，见[@Validated和@Valid区别](wiz://open_document?guid=1de1f05e-bf29-488f-a18f-3762a151bb49&kbguid=&private_kbguid=20266744-3f73-4d15-9eac-279901c14de8)
5. validation框架注解支持在方法上使用，但是只能用于getter方法上才起作用