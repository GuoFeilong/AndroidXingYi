package com.android.commonframe.tools;

import java.security.Key;

import javax.crypto.Cipher;

/**
 * DES  To decrypt the string encryption operation
 */
public class DESUtil {

    private static String strDefaultKey = "asia_travel_des";

    /**
     * encryption tools
     */
    private Cipher encryptCipher = null;

    /**
     * decrypt tools
     */
    private Cipher decryptCipher = null;

    /**
     * @throws Exception
     */
    public DESUtil() throws Exception {
        this(strDefaultKey);
    }

    /**
     * @param strKey
     * @throws Exception
     */
    public DESUtil(String strKey) throws Exception {
        Key key = getKey(strKey.getBytes());
        encryptCipher = Cipher.getInstance("DES");
        encryptCipher.init(Cipher.ENCRYPT_MODE, key);
        decryptCipher = Cipher.getInstance("DES");
        decryptCipher.init(Cipher.DECRYPT_MODE, key);
    }

    /**
     * Strings that will be converted to hexadecimal byte array， for example：byte[]{8,18} convert ：0813， android public static byte[]
     * hexStr2ByteArr(String strIn) Are reversible conversion process
     *
     * @param arrB
     * @return
     * @throws Exception
     */
    public static String byteArr2HexStr(byte[] arrB) throws Exception {
        int iLen = arrB.length;
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = arrB[i];
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }

    /**
     * @param strIn
     * @return
     * @throws Exception
     */
    public static byte[] hexStr2ByteArr(String strIn) throws Exception {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;

        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }

    /**
     * @param arrB
     * @return
     * @throws Exception
     */
    public byte[] encrypt(byte[] arrB) throws Exception {
        return encryptCipher.doFinal(arrB);
    }

    /**
     * @param strIn
     * @return
     * @throws Exception
     */
    public String encrypt(String strIn) throws Exception {
        return byteArr2HexStr(encrypt(strIn.getBytes()));
    }

    /**
     * @param arrB
     * @return
     * @throws Exception
     */
    public byte[] decrypt(byte[] arrB) throws Exception {
        return decryptCipher.doFinal(arrB);
    }

    /**
     * @param strIn
     * @return
     * @throws Exception
     */
    public String decrypt(String strIn) throws Exception {
        return new String(decrypt(hexStr2ByteArr(strIn)));
    }

    /**
     * @param arrBTmp
     * @return
     * @throws Exception
     */
    private Key getKey(byte[] arrBTmp) throws Exception {
        byte[] arrB = new byte[8];

        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }

        Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");

        return key;
    }
}