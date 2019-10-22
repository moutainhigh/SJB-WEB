package sijibao.oa.jeesite.modules.intfz.response.intfzwebresp.company;

import java.io.Serializable;

/**
 * 企业管理-详情返回对象
 * @author wanxb
 *
 */
public class MainCompanyResponse implements Serializable {
	private static final long serialVersionUID = 6865839208041086332L;

	/**
	 * 企业code
	 */
	private String companyCode;
	/**
	 * 企业名称
	 */
	private String companyName;
	/**
	 * 公司介绍
	 */
	private String companyDesc;
	/**
	 * 经营范围
	 */
	private String companyScope;
	/**
	 * 企业联系人
	 */
	private String companyLinker;
	/**
	 * 企业联系电话
	 */
	private String companyPhone;
	/**
	 * 企业logo
	 */
	private String companyLogoURL;
	/**
	 * 企业持有者code
	 */
	private String holderCode;
	/**
	 * 持有者名称
	 */
	private String holderName;
	/**
	 * 创建时间
	 */
	private long createDate;
	/**
	 * 企业别名<br>
	 * 用于开票，可以为空，如果为空开票名称取companyName
	 */
	private String companyAlias;

	/**
	 * 企业法人姓名
	 */
	private String linker;
	/**
	 * 企业法人身份证
	 */
	private String idCode;
	/**
	 * 企业法人身份证正面照（带头像的一面）
	 */
	private String idcardFrontPhoto;
	/**
	 * 企业法人身份证反面图片
	 */
	private String idcardBackPhoto;
	/**
	 * 身份证认证状态
	 */
	private IdCodeState idCodeState;
	/**
	 * 企业营业执照图片
	 */
	private String licenceUrl;
	/**
	 * 其它图片资料，多个用，隔开
	 */
	private String otherUrl;
	/**
	 * 认证状态
	 */
	private CertifyState certifyState;
	/**
	 * 营业执照编码
	 */
	private String licenseCode;
	 /**
     * 注册地址
     */
    private String companyAddr;
    /**
     * 区域code
     */
    private String regionCode;

	/**
	 * 企业认证状态
	 * 
	 * @author siwen
	 *
	 */
	public enum CertifyState implements Serializable
	{
		CERTIFY_STATE_NO(1, "未认证"), CERTIFY_STATE_WAIT(2,
				"等待审核"), CERTIFY_STATE_UNPASS(3, "审核未通过"), CERTIFY_STATE_YSE(4,
						"认证通过"), CERTIFY_STATE_CANCEL(8, "认证取消");

		private int value;
		private String desc;

		private CertifyState(int value, String desc)
		{
			this.value = value;
			this.desc = desc;
		}

		public static CertifyState valueOf(int value)
		{
			switch (value)
			{
			case 1:
				return CERTIFY_STATE_NO;
			case 2:
				return CERTIFY_STATE_WAIT;
			case 3:
				return CERTIFY_STATE_UNPASS;
			case 4:
				return CERTIFY_STATE_YSE;
			case 8:
				return CERTIFY_STATE_CANCEL;
			default:
				return null;
			}
		}

		public int getValue()
		{
			return this.value;
		}

		public int value()
		{
			return this.value;
		}

		public String getDesc()
		{
			return this.desc;
		}
	}

	/**
	 * 企业认证中身份证认证枚举
	 * 
	 * @author siwen
	 *
	 */
	public enum IdCodeState implements Serializable
	{

		INIT(0, "初始化"),

		ING(1, "等待身份认证"),

		INVALID(2, "无效身份证"),

		SUCCESS(3, "成功"),

		UN_MATCHING(4, "身份证与姓名不匹配");

		private int value;
		private String desc;

		public static IdCodeState valueOf(int value)
		{
			switch (value)
			{
			case 1:
				return ING;
			case 2:
				return INVALID;
			case 3:
				return SUCCESS;
			case 4:
				return UN_MATCHING;
			default:
				return null;
			}
		}

		private IdCodeState(int value, String desc)
		{
			this.value = value;
			this.desc = desc;
		}

		public int getValue()
		{
			return this.value;
		}

		public int value()
		{
			return this.value;
		}

		public String getDesc()
		{
			return this.desc;
		}
	}

	public boolean idcodePass()
	{
		return this.idCodeState == IdCodeState.SUCCESS;
	}

	public boolean certifyPass()
	{
		return this.certifyState == CertifyState.CERTIFY_STATE_YSE;
	}

	public String getCompanyCode()
	{
		return companyCode;
	}

	public void setCompanyCode(String companyCode)
	{
		this.companyCode = companyCode;
	}

	public String getCompanyName()
	{
		return companyName;
	}

	public void setCompanyName(String companyName)
	{
		this.companyName = companyName;
	}

	public String getCompanyDesc()
	{
		return companyDesc;
	}

	public void setCompanyDesc(String companyDesc)
	{
		this.companyDesc = companyDesc;
	}

	public String getCompanyScope()
	{
		return companyScope;
	}

	public void setCompanyScope(String companyScope)
	{
		this.companyScope = companyScope;
	}

	public String getCompanyLinker()
	{
		return companyLinker;
	}

	public void setCompanyLinker(String companyLinker)
	{
		this.companyLinker = companyLinker;
	}

	public String getCompanyPhone()
	{
		return companyPhone;
	}

	public void setCompanyPhone(String companyPhone)
	{
		this.companyPhone = companyPhone;
	}

	public String getCompanyLogoURL()
	{
		return companyLogoURL;
	}

	public void setCompanyLogoURL(String companyLogoURL)
	{
		this.companyLogoURL = companyLogoURL;
	}

	public String getHolderCode()
	{
		return holderCode;
	}

	public void setHolderCode(String holderCode)
	{
		this.holderCode = holderCode;
	}

	public String getHolderName()
	{
		return holderName;
	}

	public void setHolderName(String holderName)
	{
		this.holderName = holderName;
	}

	public long getCreateDate()
	{
		return createDate;
	}

	public void setCreateDate(long createDate)
	{
		this.createDate = createDate;
	}

	public String getCompanyAlias()
	{
		return companyAlias;
	}

	public void setCompanyAlias(String companyAlias)
	{
		this.companyAlias = companyAlias;
	}

	public String getLinker()
	{
		return linker;
	}

	public void setLinker(String linker)
	{
		this.linker = linker;
	}

	public String getIdCode()
	{
		return idCode;
	}

	public void setIdCode(String idCode)
	{
		this.idCode = idCode;
	}

	public String getIdcardFrontPhoto()
	{
		return idcardFrontPhoto;
	}

	public void setIdcardFrontPhoto(String idcardFrontPhoto)
	{
		this.idcardFrontPhoto = idcardFrontPhoto;
	}

	public String getIdcardBackPhoto()
	{
		return idcardBackPhoto;
	}

	public void setIdcardBackPhoto(String idcardBackPhoto)
	{
		this.idcardBackPhoto = idcardBackPhoto;
	}

	public IdCodeState getIdCodeState()
	{
		return idCodeState;
	}

	public void setIdCodeState(IdCodeState idCodeState)
	{
		this.idCodeState = idCodeState;
	}

	public String getLicenceUrl()
	{
		return licenceUrl;
	}

	public void setLicenceUrl(String licenceUrl)
	{
		this.licenceUrl = licenceUrl;
	}

	public String getOtherUrl()
	{
		return otherUrl;
	}

	public void setOtherUrl(String otherUrl)
	{
		this.otherUrl = otherUrl;
	}

	public CertifyState getCertifyState()
	{
		return certifyState;
	}

	public void setCertifyState(CertifyState certifyState)
	{
		this.certifyState = certifyState;
	}

	public String getLicenseCode()
	{
		return licenseCode;
	}

	public void setLicenseCode(String licenseCode)
	{
		this.licenseCode = licenseCode;
	}

    public String getCompanyAddr()
    {
        return companyAddr;
    }

    public void setCompanyAddr(String companyAddr)
    {
        this.companyAddr = companyAddr;
    }

    public String getRegionCode()
    {
        return regionCode;
    }

    public void setRegionCode(String regionCode)
    {
        this.regionCode = regionCode;
    }

    @Override
    public String toString()
    {
        return "CompanyResponse [companyCode=" + companyCode + ", companyName="
                + companyName + ", companyDesc=" + companyDesc
                + ", companyScope=" + companyScope + ", companyLinker="
                + companyLinker + ", companyPhone=" + companyPhone
                + ", companyLogoURL=" + companyLogoURL + ", holderCode="
                + holderCode + ", holderName=" + holderName + ", createDate="
                + createDate + ", companyAlias=" + companyAlias + ", linker="
                + linker + ", idCode=" + idCode + ", idcardFrontPhoto="
                + idcardFrontPhoto + ", idcardBackPhoto=" + idcardBackPhoto
                + ", idCodeState=" + idCodeState + ", licenceUrl=" + licenceUrl
                + ", otherUrl=" + otherUrl + ", certifyState=" + certifyState
                + ", licenseCode=" + licenseCode + ", companyAddr="
                + companyAddr + ", regionCode=" + regionCode + "]";
    }

	

	
	
	
}
