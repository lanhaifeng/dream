#### groovy与java混合编译
1. 由于的groovy中使用的jdk tools.jar包
    ```
        <dependency>
            <groupId>com.sun</groupId>
            <artifactId>tools</artifactId>
            <version>1.8</version>
            <scope>system</scope>
            <systemPath>${java.home}/../lib/tools.jar</systemPath>
        </dependency>
    ```
    在项目的全局依赖中加入对于tools.jar的依赖，否则编译会报错

2. 添加编译插件
    gmaven-plugin
    ```
        <plugin>
            <groupId>org.codehaus.groovy.maven</groupId>
            <artifactId>gmaven-plugin</artifactId>
            <version>1.0-rc-4</version>
            <executions>
                <execution>
                    <phase>compile</phase>
                    <goals>
                        <goal>compile</goal>
                    </goals>
                </execution>
            </executions>
            <configuration>
                <sources>
                    <source>
                        <directory>src/main</directory>
                        <includes>
                            <include>**/*.groovy</include>
                        </includes>
                    </source>
                </sources>
                <stacktrace>true</stacktrace>
            </configuration>
        </plugin>
    ```
    gmavenplus-plugin
    ```
        <plugin>
            <groupId>org.codehaus.gmavenplus</groupId>
            <artifactId>gmavenplus-plugin</artifactId>
            <version>1.12.1</version>
            <executions>
                <execution>
                    <goals>
                        <goal>compile</goal>
                    </goals>
                </execution>
            </executions>
            <configuration>
                <sources>
                    <source>
                        <directory>src/main</directory>
                        <includes>
                            <include>**/*.groovy</include>
                        </includes>
                    </source>
                </sources>
            </configuration>
        </plugin>
    ```

3. 配置运行
    gmaven-plugin
    ```
    groovy:compile compile
    ```
    gmavenplus-plugin
    ```
        gplus:compile compile
    ```
    或者为gmavenplus-plugin绑定生命周期到generate-sources这样，执行compile时，
    能保证它的compile在maven-compiler-plugin的前边运行，这样可以直接使用compile即可
    ```
    compile
    ```