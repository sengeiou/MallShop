package com.mike.baselib.utils;

/**
 * 格式化核心逻辑来源于JSON格式化 @link {http://www.bejson.com/}
 *
 * @author joker.yyc
 */
public class JsonFormatUtils {

    /**
     * 对json字符串进行格式化
     *
     * @param content        要格式化的json字符串
     * @param indent         是否进行缩进(false时压缩为一行)
     * @param colonWithSpace key冒号后是否加入空格
     * @return
     */
    public static String toFormat(String content, boolean indent, boolean colonWithSpace) {
        if (content == null) return null;
        StringBuilder sb = new StringBuilder();
        int count = 0;
        boolean inString = false;
        String tab = "\t";
        for (int i = 0; i < content.length(); i++) {
            char ch = content.charAt(i);
            switch (ch) {
                case '{':
                case '[':
                    sb.append(ch);
                    if (!inString) {
                        if (indent) {
                            sb.append("\n");
                            count++;
                            for (int j = 0; j < count; j++) {
                                sb.append(tab);
                            }
                        }

                    }
                    break;
                case '\uFEFF': //非法字符
                    if (inString) sb.append(ch);
                    break;
                case '}':
                case ']':
                    if (!inString) {
                        if (indent) {
                            count--;
                            sb.append("\n");
                            for (int j = 0; j < count; j++) {
                                sb.append(tab);
                            }
                        }

                        sb.append(ch);
                    } else {
                        sb.append(ch);
                    }
                    break;
                case ',':
                    sb.append(ch);
                    if (!inString) {
                        if (indent) {
                            sb.append("\n");
                            for (int j = 0; j < count; j++) {
                                sb.append(tab);
                            }
                        } else {
                            if (colonWithSpace) {
                                sb.append(' ');
                            }
                        }
                    }
                    break;
                case ':':
                    sb.append(ch);

                    if (!inString) {
                        if (colonWithSpace) {  //key名称冒号后加空格
                            sb.append(' ');
                        }
                    }
                    break;
                case ' ':
                case '\n':
                case '\t':
                    if (inString) {
                        sb.append(ch);
                    }
                    break;
                case '"':
                    if (i > 0 && content.charAt(i - 1) != '\\') {
                        inString = !inString;
                    }
                    sb.append(ch);
                    break;
                default:
                    sb.append(ch);
                    break;
            }
        }
        return sb.toString();
    }
}