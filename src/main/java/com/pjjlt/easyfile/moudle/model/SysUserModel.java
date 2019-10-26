package com.pjjlt.easyfile.moudle.model;

import cn.hutool.core.lang.Validator;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.pjjlt.easyfile.moudle.entity.SysUser;
import com.pjjlt.easyfile.security.SecurityUserUtil;
import com.pjjlt.easyfile.util.CipherUtil;
import com.pjjlt.easyfile.util.VerifyUtil;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;
import java.util.Objects;

@Data
@Accessors(chain = true)
public class SysUserModel extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 登录账号
     */
    private String loginName;
    /**
     * 用户昵称
     */
    private String userName;
    /**
     * 用户类型(1为管理员)
     */
    private Boolean userType;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 头像路径
     */
    private String headImgUrl;
    /**
     * 密码
     */
    private String password;
    /**
     * 帐号状态（0正常 1停用）
     */
    private Boolean status;
    /**
     * 备注
     */
    private String remark;

    /**
     * 项目id
     * */
    private Long projectId;

    /**
     * 二次输入密码
     */
    private String passwordAgain;

    @Value("${easy.default-head-img}")
    private String defaultHeadImg;

    /**
     * 预处理
     * */
    public void pre(){
        if (Objects.isNull(this.userName)){
            this.setUserName(this.loginName);
        }
        this.setPassword(CipherUtil.encryptByAES(this.password));
        if (Objects.isNull(this.headImgUrl)){
            this.setHeadImgUrl(defaultHeadImg);
        }
        if (Objects.isNull(this.userType)){
            this.setUserType(false);
        }
    }

    /**
     *  新增验参
     * */
    public boolean checkCreate() {
        //检查数据是否为空
        boolean check = VerifyUtil.hasNull(this.loginName,this.email,this.password);
        if (check){
            return true;
        }
        //检查数据格式，loginName汉字、英文、数字、下划线   email 邮箱格式 password 6~12位，英文、数字、下划线
        if (!Validator.isGeneralWithChinese(this.loginName)){
            return true;
        }
        if (!Validator.isEmail(this.email)){
            return true;
        }
        if (!Validator.isGeneral(this.password,6,12)){
            return true;
        }
        //如果phone不为空，检验之
        if (Objects.nonNull(this.phone) && !Validator.isMobile(this.phone)){
            return true;
        }
        return false;
    }

    /**
     *  登录验参
     * */
    public boolean checkLogin() {
        boolean check = VerifyUtil.hasNull(this.password);
        if (check){
            return true;
        }
        check = VerifyUtil.allNull(this.loginName,this.email);
        if (check){
            return true;
        }
        return false;
    }

    /**
     * 获取列表检查参数
     * 如果当前用户不是管理员（userType），则必传projectId
     * */
    public boolean checkList() {
        return super.checkProductId(this.projectId);
    }

    /**
     * 获取用户信息验参
     * */
    public boolean checkGetOne() {
        return Objects.isNull(this.id);
    }

    public boolean checkUpdate() {
        //检查数据是否为空
        boolean check = VerifyUtil.hasNull(this.userName,this.email);
        if (check){
            return true;
        }
        if (!Validator.isEmail(this.email)){
            return true;
        }
        //如果phone不为空，检验之
        if (Objects.nonNull(this.phone) && !Validator.isMobile(this.phone)){
            return true;
        }
        if (!Validator.isGeneralWithChinese(this.userName)){
            return true;
        }
        return Objects.isNull(this.id);
    }

    public boolean checkPassword() {
        //检查数据是否为空
        boolean check = VerifyUtil.hasNull(this.password,this.passwordAgain);
        if (check){
            return true;
        }
//        if (!this.password.equals(this.passwordAgain)){
//            return true;
//        }
        return Objects.isNull(this.id);
    }

    public static void main(String[] args) throws Exception {
        String securityKey = "AcCD123456AdCD123456AfCD12345600";//必须128位 (16进制32位*4 = 128比特位)
        String iv = "p0j0j0ltEasyFile";//不知道为什么一定要16位
        //CBC模式必须用偏移量
        AES aes = new AES(Mode.CBC, Padding.PKCS5Padding,securityKey.getBytes(),iv.getBytes());
        String data = "哈哈";
        String encryptStr = aes.encryptHex(data);
        String decryptStr = aes.decryptStr(encryptStr);
        System.out.println(data);
        System.out.println(encryptStr);
        System.out.println(decryptStr);
    }

}
