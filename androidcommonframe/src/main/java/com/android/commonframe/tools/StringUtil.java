package com.android.commonframe.tools;

import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类,字符串连接判空等
 * Created by feilong.guo on 16/11/11.
 */
public class StringUtil {
    private static final String SEAT_TYPE_0 = "0";
    private static final String SEAT_TYPE_1 = "1";
    private static final String SEAT_TYPE_2 = "2";
    private static final String SEAT_TYPE_3 = "3";
    private static final int ELEVEN_PHONE_NUMBER = 11;


    /**
     * Method to reserve price to two decimal places.
     * For example, if price equals "522.1523453",it'll return "522.15".
     *
     * @param price
     * @return
     */
    public static String parsePrice(String price) {
        return new DecimalFormat("#.00").format(Double.valueOf(price));
    }

    /**
     * Make s to lower case letter
     * For example "Center" returns "center"
     *
     * @return
     */
    public static String toLowerCase(String s) {
        if (isEmpty(s)) {
            return "";
        }
        return s.toLowerCase();
    }

    /**
     * Make s to upper case letter
     * For example "upper" returns "UPPER"
     *
     * @return
     */
    public static String toUpperCase(String s) {
        if (isEmpty(s)) {
            return "";
        }
        return s.toUpperCase();
    }

    public static boolean isChinesePhone(String phoneNumber) {
        return (!phoneNumber.isEmpty()) && (phoneNumber.length() == ELEVEN_PHONE_NUMBER);
    }

    public static boolean isEmail(String email) {
        String emailPattern = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$";
        return email.trim().matches(emailPattern);
    }

    public static boolean isNickNameInputType(String inputText) {
        String inputTypePattern = "([_A-Za-z0-9\\u4e00-\\u9fa5]{4,20})|([\\u4e00-\\u9fa5_]{2,10})|(([\\u4E00-\\u9FA5])([_A-Za-z0-9]{2,20}))|(([_A-Za-z0-9]{1,2})([\\u4E00-\\u9FA5]))|(([\\u4E00-\\u9FA5]{2,2})([_A-Za-z0-9]))";
        return inputText.trim().matches(inputTypePattern);
    }

    public static boolean isNameInputType(String inputText) {
        String inputTypePattern = "^[\\u4E00-\\u9FA5a-zA-Z][\\u4E00-\\u9FA5a-zA-Z. ]{1,19}";
        return inputText.trim().matches(inputTypePattern);
    }

    public static boolean isPhoneNumber(String phoneNumber) {
        String phonePattern = "^[1][358][0-9]{9}$";
        return phoneNumber.trim().matches(phonePattern);
    }

    public static boolean isChineseName(String chineseName) {
        String chinesePattern = "^[\\u4e00-\\u9fa5_a-zA-Z]+$";
        return chineseName.trim().matches(chinesePattern);
    }

    public static boolean isCnAndEnName(String chineseName) {
        String chinesePattern = "^[A-Za-z\\u4e00-\\u9fa5]+$";
        return chineseName.trim().matches(chinesePattern);
    }


    /**
     * True is minLength false is maxLength
     *
     * @param which  judge what
     * @param length length
     * @param arg    string param
     * @return is legal
     */
    public static boolean judgeLength(boolean which, int length, String arg) {
        if (which) {
            return judgeLength(length, 0, arg);
        } else {
            return judgeLength(0, length, arg);
        }
    }

    public static boolean judgeLength(int minLength, int maxLength, String arg) {
        boolean ret = false;
        if (minLength <= 0) {
            if (maxLength <= 0) {
                throw new IllegalArgumentException("Max Length must larger than zero !");
            } else {
                ret = arg.trim().length() > maxLength;
            }
        } else if (maxLength <= 0) {
            ret = arg.trim().length() < minLength;
        } else {
            ret = arg.trim().length() >= minLength && arg.trim().length() <= maxLength;
        }
        return ret;
    }

    /**
     * Judge the String is a number
     *
     * @param arg String
     * @return true is a number / false is not a number
     */
    public static boolean isNumber(String arg) {
        return arg.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
    }

    public static boolean isEnglishCharacter(String s) {
        return s.trim().matches("^[A-Za-z][A-Za-z\\s]*[A-Za-z]$");
    }

    public static boolean isEnglishName(String s) {
        return s.trim().matches("^[a-zA-Z][a-zA-Z .]*");
    }

    /**
     * Translate String to int safety
     *
     * @param arg        String resource to translate
     * @param defaultNum default number if error format
     * @return int (right result) or default number (error)
     */
    public static int parseInteger(String arg, int defaultNum) {
        int ret = defaultNum;
        if (isNumber(arg)) {
            ret = Integer.parseInt(arg);
        } else {
            LogUtil.d("<========NumberFormatException ! =====>");
        }
        return ret;
    }


    /**
     * Add space into bank card number
     *
     * @param mEditText
     */
    public static void bankCardNumAddSpace(final EditText mEditText) {
        mEditText.addTextChangedListener(new TextWatcher() {
            int beforeTextLength = 0;
            int onTextLength = 0;
            boolean isChanged = false;

            int location = 0;
            int spaceNumber = 0;
            private char[] tempChar;
            private StringBuffer buffer = new StringBuffer();

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                beforeTextLength = s.length();
                if (buffer.length() > 0) {
                    buffer.delete(0, buffer.length());
                }
                spaceNumber = 0;
                for (int i = 0; i < s.length(); i++) {
                    if (s.charAt(i) == ' ') {
                        spaceNumber++;
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                onTextLength = s.length();
                buffer.append(s.toString());
                if (onTextLength == beforeTextLength || onTextLength <= 3 || isChanged) {
                    isChanged = false;
                    return;
                }
                isChanged = true;
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isChanged) {
                    location = mEditText.getSelectionEnd();
                    int index = 0;
                    while (index < buffer.length()) {
                        if (buffer.charAt(index) == ' ') {
                            buffer.deleteCharAt(index);
                        } else {
                            index++;
                        }
                    }

                    index = 0;
                    int spaceNumberSEC = 0;
                    while (index < buffer.length()) {
                        if ((index == 4 || index == 9 || index == 14 || index == 19)) {
                            buffer.insert(index, ' ');
                            spaceNumberSEC++;
                        }
                        index++;
                    }

                    if (spaceNumberSEC > spaceNumber) {
                        location += (spaceNumberSEC - spaceNumber);
                    }

                    tempChar = new char[buffer.length()];
                    buffer.getChars(0, buffer.length(), tempChar, 0);
                    String str = buffer.toString();
                    if (location > str.length()) {
                        location = str.length();
                    } else if (location < 0) {
                        location = 0;
                    }

                    mEditText.setText(str);
                    Editable editable = mEditText.getText();
                    Selection.setSelection(editable, location);
                    isChanged = false;
                }
            }
        });
    }


    /**
     * BigDecimal :performs basic arithmetic operation on decimal
     *
     * @param v1
     * @param v2
     * @return
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    public static String getNotiPercent(long progress, long max) {
        int rate;
        if (progress <= 0 || max <= 0) {
            rate = 0;
        } else if (progress > max) {
            rate = 100;
        } else {
            rate = (int) ((double) progress / max * 100);
        }
        return new StringBuilder(16).append(rate).append("%").toString();
    }

    /**
     * make keyword highLight
     *
     * @param color
     * @param text
     * @param keyword
     * @return
     */
    public static SpannableString highLightKeyWord(int color, String text, String keyword) {
        SpannableString s = new SpannableString(text);
        String tempText = text.trim().toLowerCase();
        if (tempText.contains(keyword)) {
            Pattern p = Pattern.compile(keyword);
            Matcher m = p.matcher(tempText);
            while (m.find()) {
                int start = m.start();
                int end = m.end();
                s.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return s;
    }

    /**
     * Set different style for string text.
     */
    public static SpannableStringBuilder setTextStyle(int textColor, int textSize, String headerString, String allString) {
        SpannableStringBuilder style = new SpannableStringBuilder(allString);
        style.setSpan(new ForegroundColorSpan(textColor), headerString.length(), allString.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        style.setSpan(new AbsoluteSizeSpan(textSize, true), headerString.length(), allString.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        return style;
    }

    public static String handldPrice(String prcie) {
        String suffix = "";
        if (!StringUtil.isEmpty(prcie)) {
            if (prcie.contains(".")) {
                suffix = prcie.substring(prcie.lastIndexOf(".") + 1, prcie.length());
                if (suffix.equalsIgnoreCase("00") || suffix.equalsIgnoreCase("0")) {
                    prcie = prcie.substring(0, prcie.lastIndexOf("."));
                }
            }
        }
        return prcie;
    }

    /**
     * @param name
     * @return
     * @throws Exception
     */
    public static int getStringLengthWithChinese(String name) {
        if (!TextUtils.isEmpty(name)) {
            int len = 0;
            int j = 0;
            byte[] byteSize = new byte[0];
            try {
                byteSize = name.getBytes("UTF-8");
                while (true) {
                    short tmpst = (short) (byteSize[j] & 0xF0);
                    if (tmpst >= 0xB0) {
                        if (tmpst < 0xC0) {
                            j += 2;
                            len += 2;
                        } else if ((tmpst == 0xC0) || (tmpst == 0xD0)) {
                            j += 2;
                            len += 2;
                        } else if (tmpst == 0xE0) {
                            j += 3;
                            len += 2;
                        } else if (tmpst == 0xF0) {
                            short tmpst0 = (short) (((short) byteSize[j]) & 0x0F);
                            if (tmpst0 == 0) {
                                j += 4;
                                len += 2;
                            } else if ((tmpst0 > 0) && (tmpst0 < 12)) {
                                j += 5;
                                len += 2;
                            } else if (tmpst0 > 11) {
                                j += 6;
                                len += 2;
                            }
                        }
                    } else {
                        j += 1;
                        len += 1;
                    }
                    if (j > byteSize.length - 1) {
                        break;
                    }
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return len;
        }
        return 0;
    }

    private StringUtil() {
    }

    /**
     * Get target String
     *
     * @param mParams the String need to append
     * @return String
     */
    public static String concatString(String... mParams) {
        StringBuilder builder = new StringBuilder();
        if (mParams.length > 0) {
            for (String mParam : mParams) {
                if (!isEmpty(mParam))
                    builder.append(mParam);
                else
                    builder.append("");
            }
        }
        return builder.toString();
    }

    public static boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }
}
