package com.feng.baseframework.service;

import com.feng.baseframework.test.BaseFrameworkApplicationTest;
import com.feng.baseframework.util.StringUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class RedisServiceTest extends BaseFrameworkApplicationTest {

    @Autowired
    private RedisService redisService;

    @Test
    public void remove() {
    }

    @Test
    public void setTimeOut() {
        redisService.set("test","test",1*60*1000l);
        try {
            Thread.sleep(30*1000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        redisService.set("test","test",1*60*1000l);
        try {
            Thread.sleep(40*1000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(redisService.get("test"));
    }

    @Test
    public void set1() {
    }

    @Test
    public void get() {
    }

    @Test
    public void multiSetList() {
        redisService.changeDb(1);
        String listName = "listTest";
        Collection<String> listData = new ArrayList<>();
        listData.add("list1");
        listData.add("list2");
        listData.add("list3");
        listData.add("list4");
        listData.add("list5");
        listData.add("list6");

        redisService.multiSetList(listName,listData);
    }

    @Test
    public void getRightListMultValueAfterDel() {
        multiSetList();
        redisService.changeDb(1);
        String listName = "listTest";
        List<Object> data =redisService.getRightListMultValueAfterDel(listName,8);
        if(data == null){
            return;
        }
        for (Object obj : data){
            System.out.println(obj);
        }
    }

    @Test
    public void getLeftListMultValueAfterDel() {
        multiSetList();
        redisService.changeDb(1);
        String listName = "listTest";
        List<Object> data =redisService.getLeftListMultValueAfterDel(listName,8);
        if(data == null){
            return;
        }
        for (Object obj : data){
            System.out.println(obj);
        }
    }

    @Test
    public void getHashMultValue() {
        String hashName = "hashTest";
        Set<String> keys = new HashSet<>();
        for(int i=0;i<100;i++){
            keys.add(String.valueOf(i));
            if(i==97){
                keys.add(String.valueOf(10001));
            }
        }
        keys.add(String.valueOf(10000));
        List<Object> data =redisService.getHashMultValue(hashName,keys,false);
        for(Object obj : data){
            if(obj instanceof Map){
                System.out.println("data is map!");
            }
            if(obj instanceof String){
                System.out.println(obj);
            }
        }
    }

    @Test
    public void getHashMultValue2() {
        Set<String> keys = new HashSet<>();
        keys.add("");
        List<Object> bizLogStr = redisService.getHashMultValue("BizzLogInLog",keys,false);
    }

    @Test
    public void setHashMultValue() throws JSONException {
        String hashName = "hashTest";
        Map<String,String> data = new HashMap<>();
        JSONObject jsonObject = null;
        for(int i=0;i<1000;i++){
            jsonObject = new JSONObject();
            jsonObject.put("id",StringUtil.generateUUID());
            jsonObject.put("name","name"+i);
            jsonObject.put("age","age"+i);
            jsonObject.put("sex","sex"+i);
            data.put(String.valueOf(i),jsonObject.toString());
        }

        redisService.setHashMultValue(hashName,data);
    }

    @Test
    public void sendLogonToRedis() throws JSONException {
        //language=JSON
        String jsonObject = "{\n" +
                "  \"LinkSession\":{\n" +
                "    \"linkSessionID\": \"10776909922302503157\",\n" +
                "    \"DBID\": 31,\n" +
                "    \"dbStatus\": \"1\",\n" +
                "    \"serverHostName\": \"os\",\n" +
                "    \"dbServerName\": \"orcl\",\n" +
                "    \"serverIP\": \"192.168.202.3\",\n" +
                "    \"serverPort\": 1521,\n" +
                "    \"dataType\": \"1\",\n" +
                "    \"fromAddress\": \"172.19.1.7\",\n" +
                "    \"fromType\": \"1\",\n" +
                "    \"remoteClientProtocol\": \"SSH\",\n" +
                "    \"remoteClientIP\": \"192.168.61.17\",\n" +
                "    \"remoteClientAPPName\": \"SSH\",\n" +
                "    \"remoteClientMac\": \"\",\n" +
                "    \"remoteClientHost\": \"DELL\",\n" +
                "    \"clientHostName\": \"DELL\",\n" +
                "    \"clientOSName\": \"WIN7\",\n" +
                "    \"clientOSUser\": \"LJH\",\n" +
                "    \"clientAppName\": \"SQLPLUS\",\n" +
                "    \"clientMAC\": \"08-62-66-7F-AC-65\",\n" +
                "    \"clientIP\": \"192.168.61.225\",\n" +
                "    \"clientPort\": 60220,\n" +
                "    \"userSession\": {\n" +
                "      \"dbInstanceName\": \"orcl\",\n" +
                "      \"defaultDB\": \"DEPT\",\n" +
                "      \"defaultSchema\": \"HR\",\n" +
                "      \"dbUserName\": \"asset\"\n" +
                "    },\n" +
                "    \"token\": \"\",\n" +
                "    \"eUser\": \"\",\n" +
                "    \"eUserID\": \"\",\n" +
                "    \"eUserName\": \"\",\n" +
                "    \"certName\": \"\",\n" +
                "    \"appHash\": \"\",\n" +
                "    \"appPath\": \"\",\n" +
                "    \"appUser\": \"\",\n" +
                "    \"logInTimeStamp\": 139674484032992,\n" +
                "    \"isLogInSuccess\": true,\n" +
                "    \"logOutTimeStamp_\": 0\n" +
                "  },\n" +
                "  \"SQLSession\":{\n" +
                "    \"linkSessionID\":\"10776909922302503157\",\n" +
                "    \"SQLSessionID\":\"1534149565350659\",\n" +
                "    \"SQLID\":\"155553235\",\n" +
                "    \"bindVarList\":[\"a\",\"b\",\"c\"],\n" +
                "    \"reqTimeStamp\":139674484032992,\n" +
                "    \"resTimeStamp\":139674484062992,\n" +
                "    \"resRow\":10,\n" +
                "    \"errorCode\":\"0\"\n" +
                "  },\n" +
                "  \"SQLResource\":{\n" +
                "    \"SQLID\":\"155553235\",\n" +
                "    \"SQLParserType\":\"1\",\n" +
                "    \"SQLType\":\"SELECT\",\n" +
                "    \"SQLOriginal\":\"SELECT col1,col2,col3 from tab where col4='a' and col5='b' and col6='c'\",\n" +
                "    \"SQLNormalization\":\"SELECT col1,col2,col3 from tab where col4=? and col5=? and col6=?\",\n" +
                "    \"readTableColumn\":[{\n" +
                "      \"database\":\"\",\n" +
                "      \"schema\":\"\",\n" +
                "      \"table\":\"\",\n" +
                "      \"column\":\"\"\n" +
                "    }],\n" +
                "    \"writeTableColumn\":[{\n" +
                "      \"database\":\"\",\n" +
                "      \"schema\":\"\",\n" +
                "      \"table\":\"\",\n" +
                "      \"column\":\"\"\n" +
                "    }]\n" +
                "  },\n" +
                "  \"BizzLog\":{\n" +
                "    \"linkSessionID\":\"\",\n" +
                "    \"SQLSessionID\":\"1534149565350659\",\n" +
                "    \"token\":\"\",\n" +
                "    \"eUser\":\"\",\n" +
                "    \"eUserID\":\"\",\n" +
                "    \"eUserName\":\"\",\n" +
                "    \"certName\":\"\",\n" +
                "    \"dbWorkMode\":\"1\",\n" +
                "    \"content\":{\n" +
                "      \"accessControl\":{\n" +
                "        \"ruleName\":\"SPS_IPADDRESS_LOGON1\",\n" +
                "        \"actionLevel\":\"1\",\n" +
                "        \"auditLevel\":\"1\",\n" +
                "        \"objectOwner\":\"asset\",\n" +
                "        \"objectName\":\"EMPTY_COLUMN\",\n" +
                "        \"objectType\":\"TABLE\"\n" +
                "      },\n" +
                "      \"dataMask\":{\n" +
                "        \"maskRuleName\":\"maskRuleName1\"\n" +
                "      },\n" +
                "      \"riskEngine\":[\n" +
                "        {\n" +
                "          \"ruleName\":\"riskRuleName1\",\n" +
                "          \"actionLevel\":\"2\",\n" +
                "          \"auditLevel\":\"1\",\n" +
                "          \"riskClass\":202,\n" +
                "          \"tag\":[\"q\",\"b\"],\n" +
                "          \"matched\":[\"dsfsd\",\"dsgdsf\"]\n" +
                "        },\n" +
                "        {\n" +
                "          \"ruleName\":\"riskRuleName2\",\n" +
                "          \"actionLevel\":\"3\",\n" +
                "          \"auditLevel\":\"2\",\n" +
                "          \"riskClass\":203,\n" +
                "          \"tag\":[\"q2\",\"b2\"],\n" +
                "          \"matched\":[\"dsfsd2\",\"dsgdsf2\"]\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  },\n" +
                "  \"BizzLogInLog\":{\n" +
                "    \"linkSessionID\":\"10776909922302503157\",\n" +
                "    \"SQLSessionID\":\"\",\n" +
                "    \"token\":\"\",\n" +
                "    \"eUser\":\"\",\n" +
                "    \"eUserID\":\"\",\n" +
                "    \"eUserName\":\"\",\n" +
                "    \"certName\":\"\",\n" +
                "    \"dbWorkMode\":\"1\",\n" +
                "    \"content\":{\n" +
                "      \"accessControl\":{\n" +
                "        \"ruleName\":\"SPS_IPADDRESS_LOGON\",\n" +
                "        \"actionLevel\":\"1\",\n" +
                "        \"auditLevel\":\"1\",\n" +
                "        \"objectOwner\":\"\",\n" +
                "        \"objectName\":\"\",\n" +
                "        \"objectType\":\"\",\n" +
                "        \"lockDestination\":{\n" +
                "          \"id\": 1,\n" +
                "          \"ip_address\": \"192.168.61.15\",\n" +
                "          \"host\": \"CPT\",\n" +
                "          \"flcnt\": 1,\n" +
                "          \"locked_date\": \"2018-09-03 16:55:25\",\n" +
                "          \"dbid\": 1\n" +
                "        }\n" +
                "      },\n" +
                "      \"dataMask\":{\n" +
                "        \"maskRuleName\":\"maskRuleName1\"\n" +
                "      },\n" +
                "      \"riskEngine\":[\n" +
                "        {\n" +
                "          \"ruleName\":\"riskRuleName1\",\n" +
                "          \"actionLevel\":\"2\",\n" +
                "          \"auditLevel\":\"1\",\n" +
                "          \"tag\":[\"q\",\"b\"],\n" +
                "          \"matched\":[\"dsfsd\",\"dsgdsf\"]\n" +
                "        },\n" +
                "        {\n" +
                "          \"ruleName\":\"riskRuleName2\",\n" +
                "          \"actionLevel\":\"3\",\n" +
                "          \"auditLevel\":\"2\",\n" +
                "          \"riskClass\":203,\n" +
                "          \"tag\":[\"q2\",\"b2\"],\n" +
                "          \"matched\":[\"dsfsd2\",\"dsgdsf2\"]\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  }\n" +
                "}";
        JSONObject data = new JSONObject(jsonObject);
        String linkSessionId = StringUtil.generateUUID();
        String sqlSessionId = StringUtil.generateUUID();
        String sqlId = StringUtil.generateUUID();
        JSONObject linkSession = data.getJSONObject("LinkSession");
        JSONObject bizzLogInLog = data.getJSONObject("BizzLogInLog");
        linkSession.put("linkSessionID",linkSessionId);
        bizzLogInLog.put("linkSessionID",linkSessionId);

        JSONObject sqlSession = data.getJSONObject("SQLSession");
        JSONObject sqlResource = data.getJSONObject("SQLResource");
        JSONObject bizzLog = data.getJSONObject("BizzLog");
        sqlSession.put("SQLSessionID",sqlSessionId);
        sqlSession.put("SQLID",sqlId);
        sqlSession.put("linkSessionID",linkSessionId);
        sqlResource.put("SQLID",sqlId);
        bizzLog.put("SQLSessionID",sqlSessionId);

        System.out.println(data.toString());
        System.out.println("linkSession:"+linkSession.toString());
        System.out.println("bizzLogInLog:"+bizzLogInLog.toString());
        System.out.println("sqlSession:"+sqlSession.toString());
        System.out.println("sqlResource:"+sqlResource.toString());
        System.out.println("bizzLog:"+bizzLog.toString());

        String logonHashKey = "LinkSession";
        String logonBizlogHashKey = "BizzLogInLog";
        String logonTopic = "LinkSession";

        String accessListKey = "SQLSession";
        String accessReourceKey = "SQLResource";
        String accessBizlogHashKey = "BizzLog";
        String accessTopic = "SQLSession";

        //发送登陆审计
        Map<String,String> logonBizzlogHashData = new HashMap<>();
        logonBizzlogHashData.put(linkSessionId,bizzLogInLog.toString());

        Map<String,String> logonHashData = new HashMap<>();
        logonHashData.put(linkSessionId,linkSession.toString());

        redisService.setHashMultValue(logonBizlogHashKey,logonBizzlogHashData);
        redisService.setHashMultValue(logonHashKey,logonHashData);

        //通知
        redisService.convertAndSend(logonTopic, linkSessionId);

        //发送访问审计
        Map<String,String> accessBizzlogHashData = new HashMap<>();
        accessBizzlogHashData.put(sqlSessionId,bizzLog.toString());

        List<String> accessListData = new ArrayList<>();
        accessListData.add(sqlSession.toString());

        Map<String,String> sqlResourceHashData = new HashMap<>();
        sqlResourceHashData.put(sqlId,sqlResource.toString());

        redisService.multiSetList(accessListKey,accessListData);
        redisService.setHashMultValue(accessBizlogHashKey,accessBizzlogHashData);
        redisService.setHashMultValue(accessReourceKey,sqlResourceHashData);

        //通知
        redisService.convertAndSend(accessTopic,sqlSessionId);
    }

    @Test
    public void convertAndSend() {
        String logonHashKey = "LinkSession";
        List<String> messages = new ArrayList<>();
        messages.add(StringUtil.generateUUID());
        messages.add(StringUtil.generateUUID());
        messages.add(StringUtil.generateUUID());
        messages.add(StringUtil.generateUUID());

        redisService.convertAndSend(logonHashKey,messages);
    }
}