package cn.com.partical.development.system.developmentservice.common.constant;

/**
 * @author 旺仔
 * @version 1.0
 * @date 2020/11/24 14:35
 */
public interface IProjectConstant {
    /********************* 项目成员类型 创建者***********************/
    int PROJECT_MEMBER_TYPE_CREATE = 0;

    /**
     * 管理员
     */
    int PROJECT_MEMBER_TYPE_ADMINISTRATOR = 1;

    /**
     * 编辑员
     */
    int PROJECT_MEMBER_TYPE_EDITOR = 2;

    /**
     * 阅读员
     */
    int PROJECT_MEMBER_TYPE_READ = 3;


    /********************* 项目成员身份类型 成员***********************/

    int PROJECT_MEMBER_IDENTITY_TYPE_MEMBER = 0;

    /**
     * 移除
     */
    int PROJECT_MEMBER_IDENTITY_TYPE_REMOVE = 1;

    /********************* 项目开放类型 私有***********************/
    int PROJECT_DISPARK_TYPE_PRIVATE = 0;

    /**
     * 团队可见
     */
    int PROJECT_DISPARK_TYPE_TEAM_MEMBER = 1;

    /**
     * 项目成员可见
     */
    int PROJECT_DISPARK_TYPE_PROJECT_MEMBER = 2;

    /**
     * 企业内部可见
     */
    int PROJECT_DISPARK_TYPE_COMPANY = 3;

    /**
     * 公开
     */
    int PROJECT_DISPARK_TYPE_PUBLIC = 4;

    /********************* 文档类型 HTTP文档***********************/
    int DOCUMENT_TYPE_HTTP = 0;

    /**
     * markdown类型
     */
    int DOCUMENT_TYPE_MARKDOWN = 1;

    /********************* 操作类型 1 目录 2 文档 ***********************/
    int OPERATION_TYPE_CATALOG = 1;

    int OPERATION_TYPE_DOCUMENT = 2;
}
