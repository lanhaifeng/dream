/**
 * 版权所有：美创科技
 * 项目名称: soc
 * 创建者: mark
 * 创建日期: 2019/4/16
 * 文件说明:
 * 最近修改者：mark
 * 最近修改日期：2019/4/16
 */
package com.feng.baseframework.util;

import org.apache.log4j.Logger;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShellUtils {

    private static final Logger logger = Logger.getLogger(ShellUtils.class);


    public static final int ERR_GOT_EXCEPTION = 99999;

    /**
     * 数组元素中不为""即为执行结果
     * @param cmd
     * @return
     */
    public static String[] execShell(String cmd){
        logger.info("execute shell cmd:" + cmd);
        String results[] = new String[3];
        String cmds[] = new String[]{"/bin/sh","-c",cmd};
        Process process = null;
        BufferedReader br = null;
        BufferedReader brErr = null;
        StringBuilder result = new StringBuilder();
        StringBuilder resultErr = new StringBuilder();
        try {
            process = Runtime.getRuntime().exec(cmds);
            //阻塞，等待执行完成
            process.waitFor();

            int exitValue = process.exitValue();
            logger.info("cmd: " + cmd + ", exitValue: " + exitValue);
            results[0] = String.valueOf(exitValue);

            br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            brErr = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            String line = null;
            while ((line = br.readLine()) != null) {
                result.append(line).append('\n');
            }
            while ((line = brErr.readLine()) != null) {
                resultErr.append(line).append('\n');
            }

            results[1] = result.toString();
            results[2] = resultErr.toString();
        } catch (Exception e) {
            logger.error("Execute failed due exception:" + e.getMessage());
            results[0] = String.valueOf(ERR_GOT_EXCEPTION);
            results[2] = e.getMessage();
            return results;
        } finally {
            closeStream(br);
            closeStream(brErr);
            if(process != null){
                process.destroy();
            }
        }
        return results;
    }

    private static void closeStream(Closeable stream){
        if(stream != null){
            try {
                stream.close();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }
}
