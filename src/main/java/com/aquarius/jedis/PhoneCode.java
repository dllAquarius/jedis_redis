package com.aquarius.jedis;

import redis.clients.jedis.Jedis;

import java.util.Random;

/**
 * @Auther: dllAquarius
 * @Date: 2021/12/24
 * @Description:
 */
public class PhoneCode {

    public static void main(String[] args) {
//        verifyCode("13564521354");
        getVCode("13564521354","050805");
    }

    // 3 验证码校验
    public static void getVCode(String phone, String code) {
        // 从redis中获取验证码
        // 连接redis
        Jedis jedis = new Jedis("192.168.0.108", 6379);
        // 验证码key
        String codeKey = "VerifyCode" + phone + ":code";
        String redisCode = jedis.get(codeKey);
        if (redisCode!=null&&redisCode.equals(code)) {
            System.out.println("成功");
        } else {
            System.out.println("失败");
        }

        jedis.close();
    }

    // 2 每个手机每天只能发送三次，验证码放到redis中，设置过期时间10分钟
    public static void verifyCode(String phone) {
        // 连接redis
        Jedis jedis = new Jedis("192.168.0.108", 6379);

        // 拼接key
        // 手机发送次数key
        String countKey = "VerifyCode" + phone + ":count";
        // 验证码key
        String codeKey = "VerifyCode" + phone + ":code";

        // 每个手机每天只能发送三次
        String count = jedis.get(countKey);
        if (count == null) {
            // 没有发送次数，第一次发送
            // 设置发发送次数为1
            jedis.setex(countKey, 24 * 60 * 60, "1");
        } else if (Integer.parseInt(count) <= 2) {
            // 发送次数+1
            jedis.incr(countKey);
        } else if (Integer.parseInt(count) > 2) {
            // 发送三次，不能在发送
            System.out.println("今天的发送次数已经超过了三次");
            jedis.close();
            return;
        }

        // 发送的验证码要放到redis里面去
        String vCode = getCode();
        jedis.setex(codeKey, 10*60, vCode);

        jedis.close();
    }

    // 1 生成6位数字验证码
    public static String getCode() {

        Random random = new Random();
        String code = "";
        for (int a = 0; a < 6; a++) {
            int i = random.nextInt(10);
            code += i;
        }

        return code;
    }
}
