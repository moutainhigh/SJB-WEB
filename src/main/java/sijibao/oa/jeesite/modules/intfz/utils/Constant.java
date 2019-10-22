package sijibao.oa.jeesite.modules.intfz.utils;

public interface Constant {
	/**
	 * 单据审批状态
	 */
	String expense_save = "4";//单据保存
	String expense_approve = "2";//审批中
    String expense_back = "3";//单据被驳回
    String expense_approve_end = "1";//审批结束
	String expense_delete = "0";//已删除
	
	/**
	 * 单据是否删除
	 */
	/*****未删除*****/
	String expense_delete_status_zero = "0";
	
	/*****已删除*****/
	String expense_delete_status_one = "1";
	
	/**
	 * 报销操作状态
	 */
	String expense_yes = "yes";
	String expense_no = "no";
	
	/**
	 * 报销控制标准类别
	 */
	/********岗位*********/
	String OA_STANDSTYPE_POST = "1";
	/********交通方式*********/
	String OA_STANDSTYPE_TRANSMODE = "2";
	/********城市类别*********/
	String OA_STANDSTYPE_CITYTYPE = "3";
	/********金额*********/
	String OA_STANDSTYPE_AMOUNT = "4";
	
	/**
	 * 控制标准金额单位
	 */
	/*********元/天*********/
	String 	OA_AMOUNTUNIT_ONE = "1";
	/*********元/月*********/
	String 	OA_AMOUNTUNIT_TWO = "2";
	/*********元/年**********/
	String OA_AMOUNTUNIT_THREE = "3";
	/*********元/2人/天**********/
	String OA_AMOUNTUNIT_FOUR = "4";
	/*********元/人/天**********/
	String OA_AMOUNTUNIT_FIVE = "5";
	
	/**
	 * OA城市类别
	 */
	/*********其他***********/
	String OA_CITY_TYPE_ZERO  = "0";
	/*********一类城市***********/
	String OA_CITY_TYPE_ONE  = "1";
	/*********二类城市***********/
	String OA_CITY_TYPE_TWO  = "2";
	/*********三类城市***********/
	String OA_CITY_TYPE_THREE  = "3";
	
	/**
	 * 流程节点名称(用于判断该环节是否可以进行指派)
	 */
	String DEMAND_MAIN_NODE = "main_node";
	String DEMAND_CHIILD_NODE1 = "child1_node";
	String DEMAND_CHIILD_NODE2 = "child2_node";
	String DEMAIND_CHILD_NODE3 = "child3_node1";
	
	String NO_NODE = "no_node";
	
	/**
	 * 默认退回环节
	 */
	String DEFAULT_NOE_MODIFY = "modify";
	/**
	 * 动态设置执行者节点
	 */
	String DEMAND_ASSIGNEE_NODE = "child3_";
	
	/**
	 * 单据类型
	 */
	/***********报销单据类型**************/
	String BILL_TYPE_EXPENSE = "expense";
	/***********需求申请单类型*************/
	String BILL_TYPE_DEMAND = "demand";
	
	/*************最末级受理人流程节点************/
	String LAST_NODE_KEY = "child3_node1";
	
	/***********是否是最末级受理人***********/
	/*************是*************/
	String IS_LAST = "1";
	String IS_NOT_LAST = "0";
	
	/***************需求单据类型*****************/
	/**
	 * 市场提交申请
	 */
	String DEMAND_MANAGEMENT_MARKET = "1";
	String DEMAND_MANAGEMENT_MARKET_PROC = "demand_proc"; 
	/**
	 * 实施发起需求
	 */
	String DEMAND_MANAGEMENT_IMPLEMENT = "2";
	String DEMAND_MANAGEMENT_IMPLEMENT_PROC = "implement_proc";
	/**
	 * 报销申请
	 */
	String EXPENSE_FLOW_APPLY = "3";
	String EXPENSE_FLOW_APPLY_PROC = "expense_proc";
	
	/**
	 * 接待申请
	 */
	String RECP_FLOW_APPLY = "4";
	String RECP_FLOW_APPLY_PROC = "recp_proc";
	
	/**
	 * 出差申请
	 */
	String TRAVEL_FLOW_APPLY = "5";
	String TRAVEL_FLOW_APPLY_PROC = "travel_proc";
	
	/**
	 * 资源申请
	 */
	String RESOURCES_APPLY_FLOW_APPLY = "6";
	String RESOURCES_APPLY_FLOW_APPLY_PROC = "resources_apply_proc";
	
	/**
	 * 资源办理
	 */
	String RESOURCES_HANDLE_FLOW_APPLY = "7";
	String RESOURCES_HANDLE_FLOW_APPLY_PROC = "resources_handle_proc";
	
	/**
	 * 开户申请
	 */
	String  OPEN_ACCOUNT_FLOW_APPLY = "8";
	String 	OPEN_ACCOUNT_FLOW_APPLY_PROC = "oa_open_account_flow_proc";
	
	/**
	 * 合同管理流程
	 */
	String CONTRACT_FLOW_APPLY = "9";
	String CONTRACT_FLOW_APPLY_PROC = "contract_proc";


	/**
	 * 立项申请流程
	 */
	String PROJECT_APPROVAL_FLOW_APPLY = "11";
	String PROJECT_APPROVAL_FLOW_APPLY_PROC = "project_approval_proc";
	
	/**
	 * 提前打款处理状态--已处理
	 */
	String BRING_REMIT_STATUS = "1";
	
	/**
	 * 招待申请参数--接待申请流程
	 */
	String RECP_PARAM_TYPE_01 = "01";
	/**
	 * 招待申请参数--报销申请流程
	 */
	String RECP_PARAM_TYPE_02 = "02";
	
	/**************项目状态*************/
	/**
	 * 项目状态0待上线
	 */
	String PEOJECT_STATE_ZERO = "0";
	/**
	 * 项目状态1已上线
	 */
	String PEOJECT_STATE_ONE = "1";
	/**
	 * 项目状态2已关闭
	 */
	String PEOJECT_STATE_TWO = "2";
	
	/**************报销类型**************/
	/**
	 * 普通报销
	 */
	String OA_EXPENSE_TYPE_ONE = "1";
	/**
	 * 接待报销
	 */
	String OA_EXPENSE_TYPE_TWO = "2";
	/**
	 * 出差报销
	 */
	String OA_EXPENSE_TYPE_THREE = "3";
	
	
	/****************资源办理类型****************/
	/**
	 * 主动发起
	 */
	String HANDLE_TYPE_ONE = "1";
	/**
	 * 被动发起
	 */
	String HANDLE_TYPE_TWO = "2";	
	
	/**
	 * app端接口调用
	 */
	String CLIENT_TYPE_APP = "1";
	/**
	 * web端接口调用
	 */
	String CLIENT_TYPE_WEB = "2";
	
	/*****************合同管理流程***************/
	/**
	 * 财务用印流程节点
	 */
	String CONTRACT_FLOW_NODE_PRINTING = "printing";
	
	/**
	 * 法务二审流程节点
	 */
	String CONTRACT_FLOW_NODE_SECONDLEGAL = "secondLegal";
	
	/******************是/否****************/
	/**
	 * 是：1
	 */
	String YES_NO_YES = "是";
	/**
	 * 否：0
	 */
	String YES_NO_NO = "否";
	
	/*********************客户类别*****************/
	/**
	 * A重点客户
	 */
	String CUST_STAGE_A = "A";
	/**
	 * B上线客户
	 */
	String CUST_STAGE_B = "B";
	/**
	 * C签约客户
	 */
	String CUST_STAGE_C = "C";
	/**
	 * D高意向客户
	 */
	String CUST_STAGE_D = "D";
	/**
	 * E高质量客户
	 */
	String CUST_STAGE_E = "E";
	/**
	 * F普通客户
	 */
	String CUST_STAGE_F = "F";
	/**
	 * G其他来源
	 */
	String CUST_STAGE_G = "G";
	
	/*********************查询主、子客户*****************/
	/**
	 * 主客户
	 */
	String CUST_TYPE_MAIN = "1";
	/**
	 * 子客户
	 */
	String CUST_TYPE_CHILD = "2";
	
	
	/*************************员工动作************************/
	/**
	 * 出发（上项目）
	 */
	String EMPLOYEE_ACTION_SETOUT = "1";
	
	/**
	 * 到达项目
	 */
	String EMPLOYEE_ACTION_ARRIVE = "2";
	
	/**
	 * 撤离(原地待命)
	 */
	String EMPLOYEE_ACTION_EVINSITU = "3";
	
	/**
	 * 撤离（到基地待命）
	 */
	String EMPLOYEE_ACTION_EVBASE = "4";
	
	/**
	 * 到达基地
	 */
	String EMPLOYEE_ACTION_ARRIVEBASE = "5";
	
	/****************************员工状态****************************/
	/**
	 * 项目上
	 */
	String EMPLOYEE_STATUS_IN = "1";
	
	/**
	 * 待命中
	 */
	String EMPLOYEE_STATUS_OUT = "2";
	
	
	/*********************合同方数据来源****************/
	/**
	 * 客户信息
	 */
	String CONTRACT_DATASOURCE_CUST = "1";
	/**
	 * 合同信息
	 */
	String CONTRACT_DATASOURCE_CONTRACT = "2";
	/**
	 * 非系统数据
	 */
	String CONTRACT_DATASOURCE_NO = "3";
	
	/**
	 * appCode
	 */
//	String APP_CODE = "200301";
//	String APP_CODE = DictUtils.getDictLabel("1", "app_code", "");
	
	
	/********************合同方类型********************/


	/**部门状态*/
	String OFFICE_USABLE = "1";//启用
	String OFFICE_DISABLE = "0";//停用

	/**员工状态*/
	String USER_WORKING = "1";//在职
    String USER_QUITING = "2";//离职未完结
    String USER_QUITED = "3";//离职


	/**项目类型0公司项目1市场项目*/
	String PROJECT_TYPE_MARKET = "1";//市场项目
	String PROJECT_TYPE_COMPANY = "0";//公司项目

	/**
	 * message小铃铛的billtype
	 * 1市场，2实施，3报销，4接待，5出差，6资源，7资源办理，8开户，9合同,10合同归档,11立项申请,12项目
	 */
	String MESSAGE_BILL_TYPE_MARKET = "1";//market市场报销流程
	String MESSAGE_BILL_TYPE_IMPLY = "2";//实施报销流程
	String MESSAGE_BILL_TYPE_EXPENSE = "3";//报销流程
	String MESSAGE_BILL_TYPE_RECP = "4";//接待流程
	String MESSAGE_BILL_TYPE_TRAVEL = "5";//出差流程
	String MESSAGE_BILL_TYPE_RESOUR = "6";//资源流程
	String MESSAGE_BILL_TYPE_RESOURAPPLY = "7";//资源办理流程
	String MESSAGE_BILL_TYPE_OPENACCOUNT = "8";//开户流程
	String MESSAGE_BILL_TYPE_CONTRACT = "9";//合同流程
	String MESSAGE_BILL_TYPE_CONTRACT_HIS = "10";//合同归档
	String MESSAGE_BILL_TYPE_PROJECT_APPROVAL = "11";//立项申请
	String MESSAGE_BILL_TYPE_PROJECT = "12";//项目

	/**
	 * 天数计算方式
	 */
	String DAY_CALCULATION_ROUTINE = "1";//常规计算方式
	String DAY_CALCULATION_ACCOMMODATION = "1";//住宿计算方式

}
