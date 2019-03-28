package com.maoniunet.constant;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

public class Constant {
    // 多个连续空格
    public static final String SPLIT_SPACE = "\\s+";
    // 逗号跟0个以上空格
    public static final String SPLIT_COMMA = ",\\s*";
    // 逗号跟着0个以上空格或只有空格
    public static final String SPLIT_COMMA_SPACE = ",\\s*|\\s+";
    // 分号跟0个以上空格
    public static final String SPLIT_SEMICOLON = ";\\s*";

    public static final String SPLIT_CTOKEN = "ctoken=([^&]*)";
    public static final String SPLIT_X_LID = "x_lid=([^&]*)";
    public static final String SPLIT_DASH = "-";
    public static final String SPLIT_NEWLINE = "\n";

    public static final Joiner COMMA_JOINER = Joiner.on(",");
    public static final Joiner SPACE_JOINER = Joiner.on(" ");

    public static final Splitter SPACE_SPLITTER = Splitter.onPattern(Constant.SPLIT_SPACE);
    public static final Splitter COMMA_SPLITTER = Splitter.onPattern(Constant.SPLIT_COMMA);

    public static final int SORT_DEFAULT = 99999;
    public static final int SORT_SEQ_DEFAULT = 0;

    public static final String CURRENT_USER = "CURRENT_USER";

    public static final String CURRENT_HTTP_SESSION = "CURRENT_HTTP_SESSION";

    public static final String IGNORE_SESSION = "IGNORE_SESSION";

    public static final String XLSX_CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    public static final String OTHER_KEYWORD_SPLITTER = " , ";

    public static final int RANK_LIMIT = 5000;

    public static final String IN_PROMOTION = "in_promotion";
    public static final String STOPPED = "stopped";

    public static final String MAGIC = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.";
}
