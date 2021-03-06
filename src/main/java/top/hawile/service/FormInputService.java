package top.hawile.service;

import top.hawile.entity.*;
import top.hawile.entity.form.*;

import java.util.List;
import java.util.Map;

public interface FormInputService {

    //获取填写表单信息列表
    List<FormInput> list();

    //根据ID获取填写表单信息
    FormInput selectId(Integer id);

    //添加填写表单信息
    int insert(FormInput formInput);

    //修改填写表单信息
    int update(FormInput formInput);

    //删除填写表单信息
    int delete(Integer id);

    //IT设备安装及变更申请表
    String form00004(Form00004 form) throws Exception;

    //IT账号开通及变更申请表
    String form00008(Form00008 form) throws Exception;

    //逻辑安全内部审计、审查报告
    String form00018(Form00018 form) throws Exception;

    //机房进出申请表
    String form00203(Form00203 form) throws Exception;

    //密钥人员任命/终止表
    String form00021(Form00021 form) throws Exception;

    //Form00018自动填写内容
    Map<String,Object> getFrom00018Content(String type, String cfgPath) throws Exception;
}
