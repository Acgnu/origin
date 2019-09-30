package com.acgnu.origin.tencentapi;

import com.acgnu.origin.common.RequestUtil;
import com.acgnu.origin.cryptor.HmacSHA1;
import com.acgnu.origin.exception.BizException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.Optional;
import java.util.TreeMap;

/**
 * 腾讯云解析
 */
@Component
public class TenCloudAnalytic {
    @Autowired
    private RestTemplate restTemplate;

    private static final String PRIV_DIMAIN = "bilib.cn";
    private static final String PRIV_SUB_DIMAIN = "acgnu";
    private static final String CNS_DOMAIN = "cns.api.qcloud.com";
    private static final String CVM_REQUEST_ADDR = "/v2/index.php";
    private static final String PROTOCOL = "https://";
    private static final String SECRET_ID = "AKIDj6HnEE9Fl48wV4wZpHbmNuBGpSkpDCYC";
    private static final String SECRET_KEY = "YpeFZCYqthEdm25qPlqobT5dy2qNcdgS";

    public void testDelDnsRecord(){
        delDNSRecord("");
    }

    public void testAddDnsRecord(){
        addDNSRecord("_acme-challenge." + PRIV_SUB_DIMAIN, "u-yJowRuRK5FQzHym5Bc5iGbt1uNKegGdzjqwpWsZWs", "TXT");
    }

    public void testAddSubDomain(){
        addSubDomain("");
    }

    public void testModifyDNS(String ip){
        modifyDNS(ip, "416075548");
    }

    private void delDNSRecord(String recordId){
        TreeMap<String, String> urlArgs = new TreeMap<>();
        putUrlArg(urlArgs,"domain", PRIV_DIMAIN);
        putUrlArg(urlArgs,"recordId", recordId);
//        String delResult = createTenAccess("RecordDelete", CVM_REQUEST_ADDR, urlArgs);
//        System.out.println(delResult);
    }

    private void addSubDomain(String subDomain){
        TreeMap<String, String> urlArgs = new TreeMap<>();
        putUrlArg(urlArgs,"domain", subDomain);
//        String createResult = createTenAccess("DomainCreate", CVM_REQUEST_ADDR, urlArgs);
//        System.out.println(createResult);
    }

    private void addDNSRecord(String newSubDomain, String newValue, String type){
        TreeMap<String, String> urlArgs = new TreeMap<>();
        putUrlArg(urlArgs,"domain", PRIV_DIMAIN);
        putUrlArg(urlArgs,"subDomain", newSubDomain);
        putUrlArg(urlArgs,"recordType", type);
        putUrlArg(urlArgs,"recordLine", "默认");
        putUrlArg(urlArgs,"value", newValue);
//        String createResult = createTenAccess("RecordCreate", CVM_REQUEST_ADDR, urlArgs);
//        System.out.println(createResult);
    }

    /**
     * 创建一个腾讯云连接
     * @param action
     * @param apiAddr
     * @param urlArgs
     * @return 接口返回
     */
    private JSONObject createTenAccess(String action, String apiAddr, TreeMap<String, String> urlArgs){
        urlArgs = Optional.ofNullable(urlArgs).orElseGet(TreeMap::new);
        var nonce = RandomString.make(6);
        putUrlArg(urlArgs,"Action", action);
        putUrlArg(urlArgs,"Timestamp", System.currentTimeMillis() / 1000 + "");
        putUrlArg(urlArgs,"Nonce", nonce);
        putUrlArg(urlArgs,"SecretId", SECRET_ID);

        var signature = createSignature("POST",  apiAddr, urlArgs);
        putUrlArg(urlArgs,"Signature", signature);
        restTemplate.getMessageConverters().add(new FastJsonHttpMessageConverter());
        return restTemplate.postForObject(PROTOCOL + CNS_DOMAIN + apiAddr, convertMap(urlArgs), JSONObject.class);
    }

    /**
     * 修改DNS解析地址
     * @param newIp  新的ip地址
     * @param recordId  需要修改的记录id
     * @return  接口调用结果
     */
    private void modifyDNS(String newIp, String recordId){
        TreeMap<String, String> urlArgs = new TreeMap<>();
        putUrlArg(urlArgs,"domain", PRIV_DIMAIN);
        putUrlArg(urlArgs,"recordId", recordId);
        putUrlArg(urlArgs,"subDomain", PRIV_SUB_DIMAIN);
        putUrlArg(urlArgs,"recordType", "A");
        putUrlArg(urlArgs,"recordLine", "默认");
        putUrlArg(urlArgs,"value", newIp);
//        putUrlArg(urlArgs,"ttl", "10");
//        putUrlArg(urlArgs,"mx", "1");
//        String recordModify = createTenAccess("RecordModify", CVM_REQUEST_ADDR, urlArgs);
    }

    /**
     * 生成签名字符串
     * @param method
     * @param apiAddr
     * @param treeMap
     * @return
     */
    private static String createSignature(String method, String apiAddr, TreeMap<String, String> treeMap){
        //step.1 sort ,tree map does'n t need sort
        //step 2 create url parameter
        String paramStr = RequestUtil.concatQueryString(treeMap);
        String accessToSign = method + CNS_DOMAIN + apiAddr + "?" + paramStr;
        return HmacSHA1.genHMAC(accessToSign, SECRET_KEY);
    }


    /**
     * 查询域名解析记录
     * @param domain  主域名
     * @param subDomain  子域名
     * @param recordType  记录值类型  A、CNAME、MX、NS, TXT
     * @return
     */
    public JSONObject queryRecordList(String domain, String subDomain, String recordType){
//        {"code":0,"message":"","codeDesc":"Success","data":{"domain":{"id":"38961181","name":"bilib.cn","punycode":"bilib.cn","grade":"DP_Free","owner":"562628944@qq.com","ext_status":"","ttl":600,"min_ttl":600,"dnspod_ns":["f1g1ns1.dnspod.net","f1g1ns2.dnspod.net"],"status":"enable","q_project_id":0},"info":{"sub_domains":"11","record_total":"11","records_num":"11"},"records":[{"id":416075548,"ttl":600,"value":"106.52.125.163","enabled":1,"status":"enabled","updated_on":"2019-08-29 11:40:12","q_project_id":0,"name":"*.acgnu","line":"\u9ed8\u8ba4","line_id":"0","type":"A","remark":"","mx":0},{"id":194525782,"ttl":86400,"value":"f1g1ns1.dnspod.net.","enabled":1,"status":"enabled","updated_on":"2016-06-01 22:22:37","q_project_id":0,"name":"@","line":"\u9ed8\u8ba4","line_id":"0","type":"NS","remark":"","mx":0,"hold":"hold"},{"id":194525783,"ttl":86400,"value":"f1g1ns2.dnspod.net.","enabled":1,"status":"enabled","updated_on":"2016-06-01 22:22:37","q_project_id":0,"name":"@","line":"\u9ed8\u8ba4","line_id":"0","type":"NS","remark":"","mx":0,"hold":"hold"},{"id":451622011,"ttl":600,"value":"106.52.125.163","enabled":1,"status":"enabled","updated_on":"2019-08-29 11:50:05","q_project_id":0,"name":"acgnu","line":"\u9ed8\u8ba4","line_id":"0","type":"A","remark":"","mx":0},{"id":353018294,"ttl":600,"value":"182.254.222.133","enabled":1,"status":"enabled","updated_on":"2018-04-02 16:01:20","q_project_id":0,"name":"api","line":"\u9ed8\u8ba4","line_id":"0","type":"A","remark":"","mx":0},{"id":355633974,"ttl":600,"value":"182.254.222.133","enabled":1,"status":"enabled","updated_on":"2018-04-17 19:31:07","q_project_id":0,"name":"as","line":"\u9ed8\u8ba4","line_id":"0","type":"A","remark":"","mx":0},{"id":451833676,"ttl":600,"value":"2019082800000010612bo6ak2txgodcz0j2grjrlx7jgias2fe4x41r8s3ezc1w8","enabled":1,"status":"enabled","updated_on":"2019-08-29 23:24:28","q_project_id":0,"name":"go.acgnu","line":"\u9ed8\u8ba4","line_id":"0","type":"TXT","remark":"","mx":0},{"id":451679915,"ttl":600,"value":"rhextj.coding.io.","enabled":1,"status":"enabled","updated_on":"2019-08-29 15:04:40","q_project_id":0,"name":"www","line":"\u9ed8\u8ba4","line_id":"0","type":"CNAME","remark":"","mx":0},{"id":352303880,"ttl":600,"value":"201803280351361tjv4cnn8160jwsjyhwrba1y1zlillf8fu5vvdv9u6zfjn8d2f","enabled":1,"status":"enabled","updated_on":"2018-03-29 11:51:36","q_project_id":0,"name":"_dnsauth","line":"\u9ed8\u8ba4","line_id":"0","type":"TXT","remark":"","mx":0},{"id":409529190,"ttl":600,"value":"201903020258513hmmxewy1eby1yti73kcel9v47ql7yv3s91dhlk1u1r8iu76hu","enabled":1,"status":"enabled","updated_on":"2019-03-03 10:58:51","q_project_id":0,"name":"_dnsauth","line":"\u9ed8\u8ba4","line_id":"0","type":"TXT","remark":"","mx":0},{"id":451833873,"ttl":600,"value":"2019082800000010612bo6ak2txgodcz0j2grjrlx7jgias2fe4x41r8s3ezc1w8","enabled":1,"status":"enabled","updated_on":"2019-08-29 23:25:18","q_project_id":0,"name":"_dnsauth.go.acgnu","line":"\u9ed8\u8ba4","line_id":"0","type":"TXT","remark":"","mx":0}]}}
        var urlArgs = new TreeMap<String , String>();
        putUrlArg(urlArgs,"domain", Optional.ofNullable(domain).orElse(PRIV_DIMAIN));
        putUrlArg(urlArgs,"subDomain", subDomain);
        putUrlArg(urlArgs,"recordType", recordType);
        return createTenAccess("RecordList", CVM_REQUEST_ADDR, urlArgs);
    }

    /**
     * 获取客户端外网ip
     */
//    private String getClientIP(){
////        return "106.52.125.163";
//        String gatway = "http://www.yxxrui.cn/yxxrui_cabangs_api/myip.ashx";
//        String clientIp = PrcUtils.doHttpGet(gatway);
//        System.out.println(clientIp);
//        return clientIp;
//    }

    /**
     * 将k-v参数转换成rest template能够使用的类型
     * @param treeMap
     * @return
     */
    public MultiValueMap convertMap(TreeMap<String, String> treeMap){
        var multiValMap = new LinkedMultiValueMap<String, String>();
        treeMap.forEach((k, v) -> multiValMap.put(k, Collections.singletonList(v)));
        return multiValMap;
    }

    public JSONObject analyseAndGetData(JSONObject fullresult, String dataKey) throws RuntimeException{
        if (0 == fullresult.getInteger("code") && "success".equalsIgnoreCase(fullresult.getString("codeDesc"))) {
            return fullresult.getJSONObject(Optional.ofNullable(dataKey).orElse("data"));
        }
        throw new BizException(MessageFormat.format("{0}({1}) : {2}", fullresult.getString("codeDesc"), fullresult.getInteger("code"), fullresult.getString("message")));
    }

    private void putUrlArg(TreeMap<String, String> treeMap, String key, String value) {
        if (StringUtils.isEmpty(value)) {
            return;
        }
        treeMap.put(key, value);
    }
}
