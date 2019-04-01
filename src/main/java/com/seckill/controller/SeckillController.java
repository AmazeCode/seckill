package com.seckill.controller;

import com.seckill.dto.ExposerDto;
import com.seckill.dto.SeckillExecutionDto;
import com.seckill.dto.SeckillResultDto;
import com.seckill.enums.SeckillStateEnum;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.model.Seckill;
import com.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@Controller//@Service @Component 放入
@RequestMapping("/seckill") //url:/模块/资源/{id}/细分  /seckill/list
public class SeckillController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * SERVICE
     */
    @Autowired
    private SeckillService seckillService;

    @RequestMapping(value = "/list",method = {RequestMethod.GET,RequestMethod.POST})
    public String list(Model model){
        //获取列表页
        List<Seckill> list = seckillService.getSeckillList();
        //list.jsp + model = ModelAndView
        model.addAttribute("list",list);
        return "list";
    }

    @RequestMapping(value = "/{seckillId}/detail",method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId,Model model){

        if(seckillId == null){
            return "redirect:/seckill/list";
        }
        Seckill seckill = seckillService.getById(seckillId);
        if(seckill == null){
            return "forward:/seckill/list";
        }
        model.addAttribute("seckill",seckill);
        return "/detail";
    }

    //ajax json
    @RequestMapping(value = "/{seckillId}/exposer",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public SeckillResultDto<ExposerDto> exposer(Long seckillId){

        SeckillResultDto<ExposerDto> resultDto;
        try{
            ExposerDto exposerDto = seckillService.exportSeckillUrl(seckillId);
            resultDto = new SeckillResultDto<ExposerDto>(true,exposerDto);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            resultDto = new SeckillResultDto<ExposerDto>(false,e.getMessage());
        }

        return resultDto;
    }

    @RequestMapping(value = "/{seckillId}/{md5}/execution",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResultDto<SeckillExecutionDto> execute(
            @PathVariable("seckillId") Long seckillId,
            @PathVariable("md5") String md5,
            @CookieValue(value = "killPhone",required = false) Long phone
    ){
        //springmv valid
        if(phone == null){
            return new SeckillResultDto<SeckillExecutionDto>(false,"未注册");
        }
        SeckillResultDto<ExposerDto> resultDto;
        try{
            SeckillExecutionDto executionDto = seckillService.executeSeckill(seckillId,phone,md5);
            return new SeckillResultDto<SeckillExecutionDto>(true,executionDto);
        }catch (RepeatKillException e){
            SeckillExecutionDto seckillExecutionDto = new SeckillExecutionDto(seckillId, SeckillStateEnum.REPEAT_KILL);
            return new SeckillResultDto<SeckillExecutionDto>(false,seckillExecutionDto);
        }catch (SeckillCloseException e){
            SeckillExecutionDto seckillExecutionDto = new SeckillExecutionDto(seckillId, SeckillStateEnum.END);
            return new SeckillResultDto<SeckillExecutionDto>(false,seckillExecutionDto);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            SeckillExecutionDto seckillExecutionDto = new SeckillExecutionDto(seckillId, SeckillStateEnum.INNER_ERROT);
            return new SeckillResultDto<SeckillExecutionDto>(false,seckillExecutionDto);
        }
    }

    @RequestMapping(value = "/time/now",method = RequestMethod.GET)
    public SeckillResultDto<Long> time(){
        Date now = new Date();
        return new SeckillResultDto<Long>(true,now.getTime());
    }
}
