/* Copyright 2017 吴烜 (Xuan Wu)
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.example.lint.checks;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * 字库基于原项目https://code.google.com/archive/p/java-zhconverter/, 据项目描述来源于MediaWiki.
 * <p>转换规则很简单, 完全不进行分词.
 * <p>如果in不是单字, 如果在对应表中有完全匹配, 就返回对应的文本; 不然就逐字按照单字转换.
 */
public class ChangeText {


  private final static ChangeText changeText = new ChangeText();

  public ResourceBundle bundle = null;
  
  public static ChangeText getInstance() {

    return changeText;
  }
  
  private ChangeText() {

    StringBuilder builder=new StringBuilder();
    builder.append(Text.text1);
    builder.append(Text.text2);
    builder.append(Text.text3);
    builder.append(Text.text4);
    builder.append(Text.text5);
    builder.append(Text.text6);
    builder.append(Text.text7);
    builder.append(Text.text8);
    builder.append(Text.text9);


    try {
      InputStream is =new ByteArrayInputStream(builder.toString().getBytes("UTF-8"));
      this.bundle  = new PropertyResourceBundle(is);
    } catch (Exception e) {
      e.printStackTrace();
    }


  }


  /**
   * 不进行分词. 如果长度大于1, 寻找匹配的短语. 如没有, 按字寻找对应字后组合.
   * @param in 任意长度
   * @return 转换后的文本
   * @throws IllegalArgumentException 文本为null时
   */
  public String conversion(String in) {
    if (in == null) {
        return null;
    }

    StringBuilder builder = new StringBuilder();

    if (in.length() > 1 && bundle.containsKey(in)) {
      return bundle.getString(in);
    }

    for (char c : in.toCharArray()){
      String s = String.valueOf(c);

      // 如有多个对应字符, 暂时用第一个; 如果没有对应字符, 保留原字符
      builder.append(bundle.containsKey(s) ? bundle.getString(s).charAt(0) : s);
    }
    return builder.toString();
  }
}
