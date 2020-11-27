package cn.com.partical.development.system.developmentservice.common.constant;

/**
 * @author 旺仔
 * @version 1.0
 * @date 2020/11/23 13:59
 */
public interface ITeamConstant {

    /*************** 团队成员类型 管理员***************/
    int TEAM_MEMBER_TYPE_ADMINISTRATOR = 0;

    /**
     * 普通成员
     */
    int TEAM_MEMBER_TYPE_GENERAL = 1;

    /**
     * 创建者
     */
    int TEAM_MEMBER_TYPE_CREATOR = 2;

    /*************** 团队成员移除状态 将用户从我的当前团队中移除***************/
    int TEAM_MEMBER_REMOVE_STATE_CURRENT = 0;

    /**
     * 将用户从我的所有团队中移除
     */
    int TEAM_MEMBER_REMOVE_STATE_ALL = 1;
}
