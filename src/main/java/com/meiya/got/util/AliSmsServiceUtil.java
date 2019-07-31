package com.meiya.got.util;

import com.aliyuncs.*;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

public class AliSmsServiceUtil {


    //产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";

    // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    static final String accessKeyId = "LTAI2JX2Hm8E9qFZ";  // TODO 修改成自己的
    static final String accessKeySecret = "qYI5sDTxMPj7poFXe3MLMnS2KdkHHK";   // TODO 修改成自己的



    public static CommonResponse  send(String phone,String key){
//第二个参数为自己独有的accessKeyid，第三个参数为自己独有的accessKeySecret
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou",
                accessKeyId,accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();//组装请求对象
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);//设置post提交
        request.setDomain("dysmsapi.aliyuncs.com");//短信API产品域名（接口地址固定，无需修改）
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "美亚夏令营");//短信签名
        request.putQueryParameter("TemplateCode", "SMS_171111987");
        request.putQueryParameter("TemplateParam", "{code:"+key+"}");
        request.putQueryParameter("BizId", "123456");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return response;
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }




    //以下为测试代码，随机生成验证码
    private static int newcode;
    public static int getNewcode() {
        return newcode;
    }
    public static void setNewcode(){
        newcode = (int)(Math.random()*9999)+100;  //每次调用生成一位四位数的随机数
    }
    public static void main(String[] args) throws ClientException, InterruptedException {
        setNewcode();
        String code = Integer.toString(getNewcode());
        System.out.println("发送的验证码为："+code);
        //发短信
        CommonResponse response = AliSmsServiceUtil.send("15710696028", code);// TODO 填写你需要测试的手机号码
        System.out.println("短信接口返回的数据----------------");
        System.out.println(response.getData());


       /* 不删 留着 以后可能有用
        System.out.println("  ==============================================  ");
        Thread.sleep(3000L);
        //查明细
        if(response.getCode() != null && response.getCode().equals("OK")) {
            QuerySendDetailsResponse querySendDetailsResponse = querySendDetails(response.getBizId());
            System.out.println("短信明细查询接口返回数据----------------");
            System.out.println("Code=" + querySendDetailsResponse.getCode());
            System.out.println("Message=" + querySendDetailsResponse.getMessage());
            int i = 0;
            for(QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO : querySendDetailsResponse.getSmsSendDetailDTOs())
            {
                System.out.println("SmsSendDetailDTO["+i+"]:");
                System.out.println("Content=" + smsSendDetailDTO.getContent());
                System.out.println("ErrCode=" + smsSendDetailDTO.getErrCode());
                System.out.println("OutId=" + smsSendDetailDTO.getOutId());
                System.out.println("PhoneNum=" + smsSendDetailDTO.getPhoneNum());
                System.out.println("ReceiveDate=" + smsSendDetailDTO.getReceiveDate());
                System.out.println("SendDate=" + smsSendDetailDTO.getSendDate());
                System.out.println("SendStatus=" + smsSendDetailDTO.getSendStatus());
                System.out.println("Template=" + smsSendDetailDTO.getTemplateCode());
            }
            System.out.println("TotalCount=" + querySendDetailsResponse.getTotalCount());
            System.out.println("RequestId=" + querySendDetailsResponse.getRequestId());
        }*/

    }


}
