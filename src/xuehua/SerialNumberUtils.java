package xuehua;

import java.net.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;

/**
 * 雪花算法
 *
 * @author ：HUANG ZHI XUE
 * @date ：Create in 2020-11-04
 */
//@Slf4j
public class SerialNumberUtils {
    /**
     * 起始的时间戳
     */
    private final static long START_STAMP = 1583401777983L;

    /**
     * 每一部分占用的位数 序列号 机器标识 数据中心
     */
    private final static long SEQUENCE_BIT = 12;
    private final static long MACHINE_BIT = 8;
    private final static long DATA_CENTER_BIT = 2;

    /**
     * 每一部分的最大值
     */
    private final static long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);

    /**
     * 每一部分向左的位移
     */
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long DATA_CENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static long TIME_STAMP_LEFT = DATA_CENTER_LEFT + DATA_CENTER_BIT;
    /**
     * 数据中心 机器标识 序列号 上一次时间戳
     */
    private final static long DATA_CENTER_ID = 1;
    private static long address;
    private long sequence = 0L;
    private long lastStamp = -1L;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    static {
        InetAddress localIp = getLocalIp();
        address = localIp.getAddress()[3] & 0xff;
        //log.info("当前系统的 address 为: {}", address);
    }

    /**
     * 产生下一个ID
     *
     * @return
     */
    private synchronized long nextId() {
        long currStamp = getNewStamp();
        if (currStamp < lastStamp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }

        if (currStamp == lastStamp) {
            // 相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            // 同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currStamp = getNextMill();
            }
        } else {
            // 不同毫秒内，序列号置为0
            sequence = 0L;
        }

        lastStamp = currStamp;
        // 时间戳部分 数据中心部分 机器标识部分 序列号部分
        return (currStamp - START_STAMP) << TIME_STAMP_LEFT | DATA_CENTER_ID << DATA_CENTER_LEFT
                | address << MACHINE_LEFT | sequence;
    }

    private long getNextMill() {
        long mill = getNewStamp();
        while (mill <= lastStamp) {
            mill = getNewStamp();
        }
        return mill;
    }

    private long getNewStamp() {
        return System.currentTimeMillis();
    }


    private SerialNumberUtils() {
        // 私有化构造
    }


    private volatile static SerialNumberUtils serialNumberUtils;


    private static SerialNumberUtils getInstance() {

        if (serialNumberUtils == null) {
            synchronized (SerialNumberUtils.class) {
                if (serialNumberUtils == null) {
                    serialNumberUtils = new SerialNumberUtils();
                }
            }
        }

        return serialNumberUtils;
    }


    public static String getSerialNumber() {

        String localDate = LocalDate.now().format(DATE_TIME_FORMATTER);

        return localDate + getInstance().nextId();
    }


    public static InetAddress getLocalIp() {
        try {
            for (Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces(); e.hasMoreElements(); ) {
                NetworkInterface item = e.nextElement();
                for (InterfaceAddress address : item.getInterfaceAddresses()) {
                    if (item.isLoopback() || !item.isUp()) {
                        continue;
                    }
                    if (address.getAddress() instanceof Inet4Address) {
                        return address.getAddress();
                    }
                }
            }
            return InetAddress.getLocalHost();
        } catch (SocketException | UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {
        String serialNumber = SerialNumberUtils.getSerialNumber();
        System.out.println("serialNumber = " + serialNumber);

        System.out.println("serialNumber.length() = " + serialNumber.length());
    }
}
