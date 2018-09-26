package com.byrsh.mybatisgeneratorcomment.generator;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.Properties;

/**
 * @Author: yangrusheng
 * @Description: 自定义生成注释类
 * @Date: Created in 16:13 2018/9/26
 * @Modified By:
 */
public class CustomCommentGenerator extends DefaultCommentGenerator {

    /**
     * 是否抑制自定义的注释
     */
    private boolean suppressCustomComments = false;

    /**
     * 是否抑制默认的注释, 把该值看作是对父类DefaultCommentGenerator的控制，相当于父类DefaultCommentGenerator中的
     * suppressAllComments域。
     */
    private boolean suppressDefaultComments = false;

    /**
     * 是否抑制所有的注释，把suppressAllComments看作是对所有注释类的控制，注意是和父类DefaultCommentGenerator中的
     * suppressAllComments域的区别。
     */
    private boolean suppressAllComments = false;

    @Override
    public void addConfigurationProperties(Properties properties) {
        this.suppressCustomComments = StringUtility.isTrue(properties.getProperty("suppressCustomComments"));
        this.suppressAllComments = StringUtility.isTrue(properties.getProperty("suppressAllComments"));
        this.suppressDefaultComments = StringUtility.isTrue(properties.getProperty("suppressDefaultComments"));
        if (this.suppressDefaultComments) {
            properties.setProperty("suppressAllComments","true");
        }
        super.addConfigurationProperties(properties);
    }

    @Override
    public void addComment(XmlElement xmlElement) {
        //判断是否抑制所有注释
        if (!this.suppressAllComments) {
            //判断是否抑制默认的注释
            if (!this.suppressDefaultComments) {
                super.addComment(xmlElement);
            }
            //判断是否抑制自定义注释
            if (!this.suppressCustomComments) {
                addMapperXmlComment(xmlElement);

            }
        }
    }



    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable,
                                IntrospectedColumn introspectedColumn) {
        //判断是否抑制所有注释
        if (!this.suppressAllComments) {
            //判断是否抑制默认的注释
            if (!this.suppressDefaultComments) {
                super.addFieldComment(field, introspectedTable, introspectedColumn);
            }
            //判断是否抑制自定义注释
            if (!this.suppressCustomComments) {
                if (introspectedColumn.getRemarks() != null && !introspectedColumn.getRemarks().equals("")) {
                    field.addJavaDocLine("/**");
                    field.addJavaDocLine(" * " + introspectedColumn.getRemarks());
                    field.addJavaDocLine(" */");
                }
            }
        }
    }



    private void addMapperXmlComment(XmlElement xmlElement) {
        xmlElement.addElement(new TextElement("<!--"));

        if (xmlElement.getName().equals(""))

        xmlElement.addElement(new TextElement("-->"));
    }

}
