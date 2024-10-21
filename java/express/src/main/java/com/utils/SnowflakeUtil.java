package com.utils;

/**
 * @Author：
 * @Package：com.utils
 * @Project：transport_system
 * @name：SnowflakeUtil
 * @Date：2024/9/29 17:13
 * @Description:
 */

import lombok.extern.slf4j.Slf4j;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Random;

/**
 * 雪花算法工具类(15位)
 *
 * @author mahe
 * @date 2024/07/18
 * 1000000000000000000000100000011011011110         010         00000
 * {           40位时间戳相减               }      {3位机器码}    {5位毫秒序列}
 * 当前时间-初始时间二进制最大40位
 */
@Slf4j
public class SnowflakeUtil {
    /**
     * 时间部分所占长度
     */
    private static final int TIME_LEN = 41;
    /**
     * 数据中心id所占长度
     */
    private static final int DATA_LEN = 1;
    /**
     * 机器id所占长度
     */
    private static final int WORK_LEN = 3;
    /**
     * 毫秒内序列所占长度
     */
    private static final int SEQ_LEN = 5;
    /**
     * 定义起始时间 2006-03-13 19:03:37
     */
    private static final long START_TIME = 1142247817384L;
    /**
     * 上次生成id的时间戳
     */
    private static long LAST_TIME_STAMP = -1L;
    /**
     * 时间部分向左移动的位数 9
     */
    private static final int TIME_LEFT_BIT = 8;

    //private static final int TIME_LEFT_BIT = 64 - 1 - TIME_LEN;

    /**
     * 自动获取数据中心id（可以手动定义0-31之间的任意数）
     */
    private static final long DATA_ID = getDataId();
    /**
     * 自动获取机器id（可以手动定义0-31之间的任意数）
     */
    private static final long WORK_ID = getWorkId();
    /**
     * 数据中心id最大值 31
     */
    private static final int DATA_MAX_NUM = ~(-1 << DATA_LEN);
    /**
     * 机器id最大值 7
     */
    private static final int WORK_MAX_NUM = ~(-1 << WORK_LEN);
    /**
     * 随机获取数据中心id的参数 32
     */
    private static final int DATA_RANDOM = DATA_MAX_NUM + 1;
    /**
     * 随机获取机器id的参数 32
     */
    private static final int WORK_RANDOM = WORK_MAX_NUM + 1;
    /**
     * 数据中心id左位移数 17
     */
    private static final int DATA_LEFT_BIT = TIME_LEFT_BIT - DATA_LEN;
    /**
     * 机器id左位移数 12
     */
    private static final int WORK_LEFT_BIT = DATA_LEFT_BIT - WORK_LEN;
    /**
     * 上一次毫秒内序列值
     */
    private static long LAST_SEQ = 0L;
    /**
     * 毫秒内序列的最大值 4095
     */
    private static final long SEQ_MAX_NUM = ~(-1 << SEQ_LEN);

    /**
     * 获取雪花算法Id
     *
     * @return 15位雪花算法Id
     */
    public synchronized static long getUniqueId() {
        long now = System.currentTimeMillis();

        // 如果当前时间小于上次ID生成的时间，说明系统回退过抛出异常
        if (now < LAST_TIME_STAMP) {
            log.info("系统时间异常，请检查！");
            throw new RuntimeException("系统时间异常！");
        }

        if (now == LAST_TIME_STAMP) {
            LAST_SEQ = (LAST_SEQ + 1) & SEQ_MAX_NUM;
            if (LAST_SEQ == 0) {
                now = nextMillis(LAST_TIME_STAMP);
            }
        } else {
            LAST_SEQ = 0;
        }

        LAST_TIME_STAMP = now;
        return ((now - START_TIME) << TIME_LEFT_BIT) | (WORK_ID << 5) | LAST_SEQ;
    }

    private static long nextMillis(Long lastMillis) {
        long now = System.currentTimeMillis();
        while (now <= lastMillis) {
            now = System.currentTimeMillis();
        }
        return now;
    }

    @SuppressWarnings("all")
    private static int getDataId() {
        try {
            return getHostId(Inet4Address.getLocalHost().getHostName(), DATA_MAX_NUM);
        } catch (UnknownHostException e) {

            return new Random().nextInt(DATA_RANDOM);
        }
    }

    @SuppressWarnings("all")
    private static int getWorkId() {
        try {
            return getHostId(Inet4Address.getLocalHost().getHostAddress(), WORK_MAX_NUM);
        } catch (UnknownHostException e) {
            return new Random().nextInt(WORK_RANDOM);
        }
    }

    private static int getHostId(String str, int max) {
        byte[] bytes = str.getBytes();
        int sums = 0;
        for (int b : bytes) {
            sums += b;
        }
        return sums % (max + 1);
    }

    public static void main(String[] args) {

        //for (int i = 0; i < 10; i++) {
        //    new Thread(() -> {
        //        long id = getUniqueId();
        //        System.out.println("-------------");
        //        System.out.println(id);
        //        System.out.println("id二进制：" + Long.toBinaryString(id) + ",长度：" + Long.toBinaryString(id).length());
        //        System.out.println("总位数为：" + String.valueOf(id).length());
        //    }).start();
        //}
    }
}
