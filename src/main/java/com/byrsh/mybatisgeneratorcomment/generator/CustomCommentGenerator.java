package com.byrsh.mybatisgeneratorcomment.generator;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.List;
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
                addCustomMapperXmlComment(xmlElement);
            }
        }
    }

    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
        //判断是否抑制所有注释
        if (!this.suppressAllComments) {
            //判断是否抑制默认的注释
            if (!this.suppressDefaultComments) {
                super.addGeneralMethodComment(method, introspectedTable);
            }
            //判断是否抑制自定义注释
            if (!this.suppressCustomComments) {
                addCustomMethodComment(method);

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



    private void addCustomMapperXmlComment(XmlElement xmlElement) {
        xmlElement.addElement(new TextElement("<!--"));
        List<Attribute> attributes = xmlElement.getAttributes();
        //获得每个标签id的value，然后根据value值添加相应备注
        String value = attributes.get(0).getValue();
        if (value.equals("BaseResultMap")) {
            xmlElement.addElement(new TextElement("resultMap，数据库字段与类域之间的映射"));
        } else if (value.equals("Example_Where_Clause")) {
            xmlElement.addElement(new TextElement(value + "，含有ByExample结尾方法的"
                    + "sql where子句"));
        } else if (value.equals("Update_By_Example_Where_Clause")) {
            xmlElement.addElement(new TextElement(value + "，更新方法中的 sql where子句"));
        } else if (value.equals("Base_Column_List")) {
            xmlElement.addElement(new TextElement(value + "，基本的数据库表字段"));
        } else if (value.equals("selectByExample")) {
            xmlElement.addElement(new TextElement(value + "，根据example查询"));
        } else if (value.equals("selectByPrimaryKey")) {
            xmlElement.addElement(new TextElement(value + "，根据数据库ID查询"));
        } else if (value.equals("deleteByPrimaryKey")) {
            xmlElement.addElement(new TextElement(value + "，根据数据库ID删除，注意是物理删除"));
        } else if (value.equals("deleteByExample")) {
            xmlElement.addElement(new TextElement(value + "，根据example删除，注意是物理删除"));
        } else if (value.equals("insert")) {
            xmlElement.addElement(new TextElement(value + "，插入一条记录"));
        } else if (value.equals("insertSelective")) {
            xmlElement.addElement(new TextElement(value + "，插入一条记录，和insert的区别是当某个字段的值"
                    + "是null时，便没有该字段，其值将是表设置的默认值"));
        } else if (value.equals("countByExample")) {
            xmlElement.addElement(new TextElement(value + "，计算像example这样的记录数量"));
        } else if (value.equals("updateByExampleSelective")) {
            xmlElement.addElement(new TextElement(value + "，根据example选择性的更新，若字段的值为null，"
                    + "则该字段不更新"));
        } else if (value.equals("updateByExample")) {
            xmlElement.addElement(new TextElement(value + "，根据example更新"));
        } else if (value.equals("updateByPrimaryKeySelective")) {
            xmlElement.addElement(new TextElement(value + "，根据数据库ID选择性更新"));
        } else if (value.equals("updateByPrimaryKey")) {
            xmlElement.addElement(new TextElement(value + "，根据数据库ID更新"));
        }
        xmlElement.addElement(new TextElement("-->"));
    }

    private void addCustomMethodComment(Method method) {
        method.addJavaDocLine("/**");
        String methodName = method.getName();
        if (methodName.equals("selectByExample")) {
            method.addJavaDocLine(" * 根据example查询");
        } else if (methodName.equals("selectByPrimaryKey")) {
            method.addJavaDocLine(" * 根据数据库ID查询");
        } else if (methodName.equals("deleteByPrimaryKey")) {
            method.addJavaDocLine(" * 根据数据库ID删除，注意是物理删除");
        } else if (methodName.equals("deleteByExample")) {
            method.addJavaDocLine(" * 根据example删除，注意是物理删除");
        } else if (methodName.equals("insert")) {
            method.addJavaDocLine(" * 插入一条记录");
        } else if (methodName.equals("insertSelective")) {
            method.addJavaDocLine(" * 插入一条记录，和insert的区别是当某个字段的值"
                    + "是null时，便没有该字段，其值将是表设置的默认值");
        } else if (methodName.equals("countByExample")) {
            method.addJavaDocLine(" * 计算像example这样的记录数量");
        } else if (methodName.equals("updateByExampleSelective")) {
            method.addJavaDocLine(" * 根据example选择性的更新，若字段的值为null，"
                    + "则该字段不更新");
        } else if (methodName.equals("updateByExample")) {
            method.addJavaDocLine(" * 根据example更新");
        } else if (methodName.equals("updateByPrimaryKeySelective")) {
            method.addJavaDocLine(" * 根据数据库ID选择性更新");
        } else if (methodName.equals("updateByPrimaryKey")) {
            method.addJavaDocLine(" * 根据数据库ID更新");
        }

        method.addJavaDocLine(" */");
    }

}
