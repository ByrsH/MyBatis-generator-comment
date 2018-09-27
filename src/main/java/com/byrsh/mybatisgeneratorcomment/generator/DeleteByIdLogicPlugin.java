package com.byrsh.mybatisgeneratorcomment.generator;

import java.util.List;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

/**
 * @Author: yangrusheng
 * @Description: 逻辑删除插件
 * @Date: Created in 15:13 2018/9/27
 * @Modified By:
 */

public class DeleteByIdLogicPlugin extends PluginAdapter {

    public boolean validate(List<String> warnings) {
        return true;
    }

    /**
     * 客户端接口添加eleteByIdLogic方法
     * @param interfaze
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        interfaze.addMethod(generateDeleteByIdLogic(introspectedTable));
        return true;
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {

        //数据库表名
        String tableName = introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime();

        XmlElement parentElement = document.getRootElement();

        //逻辑删除sql语句，设置deleted字段值
        XmlElement deleteLogicByIdElement = new XmlElement("update");
        deleteLogicByIdElement.addAttribute(new Attribute("id", "deleteByIdLogic"));

        deleteLogicByIdElement.addElement(
                new TextElement(
                        "update " + tableName + " set `delete`=#{delete,jdbcType=INTEGER} where "
                                + "id=#{id,jdbcType=BIGINT}"
                ));

        parentElement.addElement(deleteLogicByIdElement);

        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }

    private Method generateDeleteByIdLogic(IntrospectedTable introspectedTable) {
        Method newMethod = new Method("deleteLogicById");
        newMethod.setVisibility(JavaVisibility.PUBLIC);
        newMethod.setReturnType(FullyQualifiedJavaType.getIntInstance());
        newMethod.addParameter(new Parameter(FullyQualifiedJavaType.getIntInstance(), "delete",
                "@Param(\"delete\")"));
        newMethod.addParameter(new Parameter(new FullyQualifiedJavaType("Long"), "id",
                "@Param(\"id\")"));

        context.getCommentGenerator().addGeneralMethodComment(newMethod,
                introspectedTable);
        return newMethod;
    }

}