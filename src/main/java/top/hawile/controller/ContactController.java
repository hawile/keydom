package top.hawile.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.hawile.entity.Contact;
import top.hawile.service.ContactService;
import top.hawile.service.LogService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/contact")
public class ContactController {

    @Resource
    private ContactService contactService;
    @Resource
    private LogService logService;

    @RequestMapping()
    //设置厂商联系方式列表所需内容
    public String contact(Model model, HttpSession session){
        //将登录用户信息传入model
        model.addAttribute("user",session.getAttribute("user"));
        //将权限信息存入model
        model.addAttribute("role", session.getAttribute("role"));
        //将操作写入日志
        logService.log("查看[ 厂商信息 ]","成功");
        return "page/contact";
    }

    @ResponseBody
    @RequestMapping("/list")
    //获取厂商联系方式列表
    public Map<String,Object> list(int page, int limit){
        //设置分页格式
        PageHelper.startPage(page,limit);
        //获取账号信息列表
        List<Contact> list = contactService.list();
        //根据分页格式分页表格列表
        PageInfo<Contact> rolePageInfo = new PageInfo<>(list);
        //新建Map对象
        Map<String,Object> map = new HashMap<>();
        //设置Json格式
        map.put("code", 0);
        map.put("msg", "");
        map.put("count",rolePageInfo.getTotal());
        map.put("data",rolePageInfo.getList());
        return map;
    }

    @ResponseBody
    @RequestMapping("/insert")
    //添加厂商联系人信息
    public int insert(Contact contact){
        //封装当前系统时间到对象
        contact.setUpdateTime(new Timestamp(new Date().getTime()));
        //封装当前系统时间到对象中
        contact.setUpdateTime(new Timestamp(new Date().getTime()));
        //执行添加厂商联系方式到数据库操作
        int state = contactService.insert(contact);
        //判断是否添加成功
        if(state == 1){
            //将操作写入日志
            logService.log("添加[ "+contact.getTradeName()+" ]厂商[ "
                    +contact.getContacts()+" ]联系人信息","成功");
            return 1;
        }
        //将操作写入日志
        logService.log("添加[ "+contact.getTradeName()+" ]厂商[ "
                +contact.getContacts()+" ]联系人信息","失败");
        return 0;
    }

    @ResponseBody
    @RequestMapping("/update")
    //修改厂商联系人信息
    public int update(Contact contact){
        //封装当前系统时间到对象中
        contact.setUpdateTime(new Timestamp(new Date().getTime()));
        //执行修改厂商联系方式到数据库操作
        int state = contactService.update(contact);
        //判断是否修改成功
        if(state == 1){
            //将操作写入日志
            logService.log("修改[ "+contact.getTradeName()+" ]厂商[ "
                    +contact.getContacts()+" ]联系人信息","成功");
            return 1;
        }
        //将操作写入日志
        logService.log("修改[ "+contact.getTradeName()+" ]厂商[ "
                +contact.getContacts()+" ]联系人信息","失败");
        return 0;
    }

    @ResponseBody
    @RequestMapping("/delete")
    //删除厂商联系人信息
    public int delete(Integer id, String tradeName, String contacts){
        //执行删除厂商联系方式到数据库操作
        int state = contactService.delete(id);
        //判断是否删除成功
        if(state == 1){
            //将操作写入日志
            logService.log("删除[ "+tradeName+" ]厂商[ "
                    +contacts+" ]联系人信息","成功");
            return 1;
        }
        //将操作写入日志
        logService.log("删除[ "+tradeName+" ]厂商[ "
                +contacts+" ]联系人信息","失败");
        return 0;
    }

    @ResponseBody
    @GetMapping("/exportExcel")
    //导出Excel列表
    public void exportExcel(String exportId) throws Exception {
        contactService.exportExcel(exportId);
        logService.log("导出公司联系人信息列表","成功");
    }
}
