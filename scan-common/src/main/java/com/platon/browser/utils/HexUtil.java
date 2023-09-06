package com.platon.browser.utils;

import org.bouncycastle.util.encoders.Hex;

/**
 * @Auther: Chendongming
 * @Date: 2019/8/15 15:36
 * @Description:
 */
public class HexUtil {
    private HexUtil(){}
    /**
     * 为十六进制字符串添加"0x"前缀
     * @param hexVal
     * @return
     */
    public static String prefix(String hexVal){
        if(hexVal.startsWith("0x")) return hexVal;
        return "0x"+hexVal;
    }

    /**
     * 后面拼接字符串
     * @method String val
     * @param val
     * @return
     */
    public static String append(String val){
    	StringBuffer sBuffer = new StringBuffer("\t");
        sBuffer.append(val);
        return sBuffer.append("\t").toString();
    }

    public static byte[] decode(String hexValue){
        if(hexValue.startsWith("0x") || hexValue.startsWith("0X")){
            return Hex.decode(hexValue.substring(2));
        }else{
            return Hex.decode(hexValue);
        }
    }


    //比如：在应用链中，内置staking合约的方法：StakeStateSync(uint256,bytes[])，经过hash后前4个字节转为16进制字符串后是：0xbb02fc89（对应uint32=3137535113）
    //那么JAVA在获取交易的input前4个字节后，也要能转成无符号的long=3137535113
    //go中虽然出现了BigEndian，但是这里直接转就OK
    public static long toUnsignedLong(String hexValue){
        if(hexValue.startsWith("0x") || hexValue.startsWith("0X")){
            return  Long.parseUnsignedLong(hexValue.substring(2), 16);
        }else{
            return  Long.parseUnsignedLong(hexValue, 16);
        }
    }

    static long bytes2long(byte[] bs)  throws Exception {
        int bytes = bs.length;
        if(bytes > 1) {
            if((bytes % 2) != 0 || bytes > 8) {
                throw new Exception("not support");
            }}
        switch(bytes) {
            case 0:
                return 0;
            case 1:
                return (long)((bs[0] & 0xff));
            case 2:
                return (long)((bs[0] & 0xff) <<8 | (bs[1] & 0xff));
            case 4:
                return (long)((bs[0] & 0xffL) <<24 | (bs[1] & 0xffL) << 16 | (bs[2] & 0xffL) <<8 | (bs[3] & 0xffL));
            case 8:
                return (long)((bs[0] & 0xffL) <<56 | (bs[1] & 0xffL) << 48 | (bs[2] & 0xffL) <<40 | (bs[3] & 0xffL)<<32 |
                        (bs[4] & 0xffL) <<24 | (bs[5] & 0xffL) << 16 | (bs[6] & 0xffL) <<8 | (bs[7] & 0xffL));
            default:
                throw new Exception("not support");
        }
        //return 0;
    }
    static long convertToLong(byte[] bytes)
    {
        long value = 0l;

        // Iterating through for loop
        for (byte b : bytes) {
            // Shifting previous value 8 bits to right and
            // add it with next value
            value = (value << 8) + (b & 255);
        }

        return value;
    }

    public static void main(String[] args) throws Exception {
        String input4Bytes = "0xbb02fc89";
        System.out.println("method ID:" + toUnsignedLong(input4Bytes)); //3137535113

        byte[] inputBytes = HexUtil.decode(input4Bytes);

        System.out.println("method ID1:" + bytes2long(inputBytes)); //3137535113
        System.out.println("method ID2:" + convertToLong(inputBytes)); //3137535113

        // 这个为什么不对？
        // System.out.println("method ID3:" + Conversion.byteArrayToLong(inputBytes, 0, 0 ,0, 4));
    }
}
